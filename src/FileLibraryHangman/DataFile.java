/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileLibraryHangman;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Gerryl
 */
public class DataFile {
    private final File DatFile;
    private RandomAccessFile dataWritter;

    public DataFile(File DatFile) throws FileNotFoundException,Exception {
        this.DatFile = DatFile;
    }
    
    public void WriteObject(ArrayList<Object> dataList) throws IOException, Exception{
        
        if (DatFile.getName().contains(".dat")) {
            dataWritter = new RandomAccessFile(this.DatFile, "rw");
        }
        
        if (dataList == null) {
            throw new Exception("Data List not found");
        }
        
        ByteArrayOutputStream tobytes = new ByteArrayOutputStream();//the Array of Object
        ObjectOutputStream fromobj = new ObjectOutputStream(tobytes);//convert method
        
        fromobj.writeObject(dataList);//from object to ByteArrayOutputStream;
       
        byte[] thebytes = tobytes.toByteArray();
             
        dataWritter.seek(0);
        dataWritter.write(thebytes);
        dataWritter.close();
    }
    
    public ArrayList<Object> ReadDataFile() throws FileNotFoundException, IOException, ClassNotFoundException{
        if (DatFile.getName().contains(".dat")) {
            dataWritter = new RandomAccessFile(this.DatFile, "r");
        }
        
        byte[] data = new byte[(int)dataWritter.length()];
        dataWritter.seek(0);
        dataWritter.read(data);
        ByteArrayInputStream toArray = new ByteArrayInputStream(data);//code the array to an inputstream
        ObjectInputStream in = new ObjectInputStream(toArray);//from input stream to object
        
        dataWritter.close();
        
        return (ArrayList<Object>) in.readObject();
    }

    
    public void CloseDataWritter() throws IOException{
        dataWritter.close();
    }
}
