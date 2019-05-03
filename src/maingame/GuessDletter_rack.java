/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maingame;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

/**
 *
 * @author Gerryl
 */
public class GuessDletter_rack extends HBox{
    private ArrayList<GuessDLetter_label> letters_in_labels;
    private char[] wordinchars;
    private ArrayList<String> wordincharsCopy;
    
    public GuessDletter_rack(){
        super();
        this.setPrefSize(667, 100);
        this.setLayoutX(22);
        this.setLayoutY(486);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        
        try{
        wordincharsCopy = new ArrayList();
        letters_in_labels = new ArrayList<>();
        
        }catch(Exception e){
            System.out.println("Error in GuessDletter_rack() "+ e);
        }
    }
    
    public void New_word_hidden(){
        letters_in_labels.clear();
        try{
            for (int i = 0; i < wordinchars.length; i++) {
                int delay = i*100;
                GuessDLetter_label guessLabel = new GuessDLetter_label();
                guessLabel.Hide_Letter(delay);
                letters_in_labels.add(guessLabel);
            }
            
            for(GuessDLetter_label label : letters_in_labels){
               this.getChildren().add(label);
            }
        }catch(Exception e){
            System.out.println("Error New_word_hidden() "+ e);
        }
    }
    
    
    public boolean Contains_aletter(String source){
        boolean contains = false;
        try{
            for (int i = 0; i < wordinchars.length; i++) {
                String wordinchar = Character.toString(wordinchars[i]).toUpperCase();
                if (source.equalsIgnoreCase(wordinchar)) {
                    letters_in_labels.get(i).Reveal_Letter(wordinchar);
                    wordincharsCopy.add(wordinchar);
                    contains = true;
                }
            }
        
        }catch(Exception e){
            System.out.println("Error Contains_aletter(param) "+ e);
        }
        return contains;
    }
    
    public boolean AllLettersRevealed(){
        boolean reset = false;
        if (wordincharsCopy.size() == wordinchars.length) {
           reset = true;
        }
        return reset;
    }

    public void setWordinchars(char[] wordinchars) {
        this.wordinchars = null;
        this.getChildren().removeAll(letters_in_labels);
        wordincharsCopy.clear();
        letters_in_labels.clear();
        try{
            this.wordinchars = wordinchars;
            this.New_word_hidden();
        }catch(Exception e){
            System.out.println("Error setWordinchars(param) "+ e);
        }
    }

    public char[] getWordinchars() {
        return wordinchars;
    }

    public ArrayList<GuessDLetter_label> getLetters_in_labels() {
        return letters_in_labels;
    }
}
