/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maingame;

import FileLibraryHangman.HangmanImages;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Gerryl
 */
public class Hang_man extends ImageView{
    
    private final ArrayList<HangmanImages> images;
    private int mistakes;

    public Hang_man(ArrayList<HangmanImages> images){
        super();
        super.setFitWidth(650);
        super.setFitHeight(391);
        super.setLayoutX(14);
        super.setLayoutY(95);
        
        mistakes = 0;
        
        this.images = images;
        
        
        
        super.setImage(new Image(findImage(0)));
    }
    
    public int recieveMistake(boolean mistake){
        if (!mistake) {
            mistakes++;
            if(mistakes < 7){
                super.setImage(new Image(findImage(mistakes)));
            }
        }
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
        super.setImage(new Image(findImage(this.mistakes)));
    }
    
    public void resetImage(){
        this.mistakes = 0;
        super.setImage(new Image(findImage(0)));
    }
    
    public InputStream findImage(int find){
        ByteArrayInputStream byteToimage = new ByteArrayInputStream(images.get(find).getImagefl());;
        return (InputStream) byteToimage;
    }
}
