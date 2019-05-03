package FileLibraryHangman;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gerryl
 */
public class HangmanCategory implements Serializable{
    private static final long serialVersionUID = 2L;
    private String categoryName;
    private ArrayList<String> items;
    
    public HangmanCategory(){
    }
    
    public HangmanCategory(String categoryName, ArrayList<String> items){
        this.categoryName = categoryName;
        this.items = items;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
