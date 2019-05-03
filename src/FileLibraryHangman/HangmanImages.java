/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileLibraryHangman;

import java.io.Serializable;

/**
 *
 * @author Gerryl
 */
public class HangmanImages implements Serializable{
    private static final long serialVersionUID = 1L;
    private String imageName;
    private byte[] imagefl;
    private int imageSize;
            
    public HangmanImages() {
    }

    public HangmanImages(String imageName, byte[] imagefl) {
        this.imageName = imageName;
        this.imagefl = imagefl;
        this.imageSize = imagefl.length;
    }

    public String getImageName() {
        return imageName;
    }

    public byte[] getImagefl() {
        return imagefl;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImageSize() {
        return imageSize;
    }
}
