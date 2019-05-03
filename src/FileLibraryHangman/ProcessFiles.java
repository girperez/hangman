/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileLibraryHangman;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author Gerryl
 */
public class ProcessFiles{
    
    private ArrayList<HangmanImages> imageList;
    private ArrayList<HangmanCategory> categoryList;
    

    public ProcessFiles(File dataFile) throws Exception{
        imageList = new ArrayList<>();
        categoryList = new ArrayList<>();
        
        DataFile getdatas = new DataFile(dataFile);
        
        try {
            ArrayList<Object> objects = getdatas.ReadDataFile();
        
            for(Object obj : objects){
                if (obj instanceof HangmanImages) {
                    HangmanImages hi = (HangmanImages) obj;
                    imageList.add(hi);
                }else if (obj instanceof HangmanCategory) {
                    HangmanCategory hc = (HangmanCategory) obj;
                    categoryList.add(hc);
                }
                else{
                    throw new Exception("Data file contents not instance of HangmanImages");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    //HangmanImages methods
    public InputStream findImage(String find){
         ByteArrayInputStream byteToimage = null;

        for(HangmanImages img : imageList){
            if (img.getImageName().equals(find)) {
               byteToimage = new ByteArrayInputStream(img.getImagefl());
                
            }
        }
       return (InputStream) byteToimage;
    }
    
    public void AddImage(File imageFile) throws FileNotFoundException, IOException{
        
        if (!imageFile.getName().contains(".png")) {
            throw new FileNotFoundException("Error please make sure the image is in .png format");
        }
        
        FileInputStream inputStream = new FileInputStream(imageFile);
        byte[] imginbyte = new byte[inputStream.available()];//array of binaries.
        inputStream.read(imginbyte);
        
        HangmanImages img = new HangmanImages(imageFile.getName(), imginbyte);
        
        imageList.add(img);
    }
    
    public void DeleteImage(String imagename){
        for (int i = 0; i < imageList.size(); i++) {
            if (imageList.get(i).getImageName().equals(imagename)) {
                imageList.remove(i);
                break;
            }
        }
    }
    
    public void ChangeImageName(String imagename, String newimageName){
        for (int i = 0; i < imageList.size(); i++) {
            if (imageList.get(i).getImageName().equals(imagename)) {
                HangmanImages hi = imageList.get(i);
                hi.setImageName(newimageName);
                imageList.set(i, hi);
                break;
            }
        }
    }
    
    public void saveChangesImg(File dataFile) throws Exception{
        DataFile save = new DataFile(dataFile);
        ArrayList<Object> convert = new ArrayList<>(imageList);
        save.WriteObject(convert);
        System.out.println("Changes saved ImagesFile");
    }
    
    //HangmanCategory methods
    public HangmanCategory FindCategory(String category){
        for(HangmanCategory hc : categoryList){
            if (hc.getCategoryName().equals(category)) {
                return hc;
            }
        }
        
        return null;
    }
    
    public void AddnewCategory(String nameString, ArrayList<String> itemslist){
        HangmanCategory newcat = new HangmanCategory(nameString, itemslist);
        categoryList.add(newcat);
    }
    
    public void DeleteCategory(String categoryname){
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategoryName().equals(categoryname)) {
                categoryList.remove(i);
                break;
            }
        }
    }
    
   public void ChangeCategoryName(String categoryName, String newCategoryname){
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getCategoryName().equals(categoryName)) {
                HangmanCategory hc = categoryList.get(i);
                hc.setCategoryName(newCategoryname);
                categoryList.set(i, hc);
                break;
            }
        }
    }
    
    public void saveChangesCategory(File dataFile) throws Exception{
        DataFile save = new DataFile(dataFile);
        ArrayList<Object> convert = new ArrayList<>(categoryList);
        save.WriteObject(convert);
        System.out.println("Changes saved CategoriesFile");
    }
    //Lists Getters
    public ArrayList<HangmanImages> getImageList() {
        return imageList;
    }

    public ArrayList<HangmanCategory> getCategoryList() {
        return categoryList;
    }
}
