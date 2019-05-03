/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maingame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventTarget;
import javafx.geometry.HPos;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 *
 * @author Gerryl
 */
public class Letter_rack extends GridPane{
    private final char[] alphabets_char;
    private ObservableList<Letter_label> alphabetsInlabel;
    private ObservableList<Letter_label> removedAlphabet;
    
    
    public Letter_rack() {
        super();
        this.setAlignment(Pos.CENTER);
        this.setNodeOrientation(NodeOrientation.INHERIT);
        this.setLayoutX(681);
        this.setLayoutY(80);
        this.setPrefWidth(200);
        this.setPrefHeight(406);
        
        alphabetsInlabel = FXCollections.observableArrayList();
        removedAlphabet = FXCollections.observableArrayList();
        
        for (int row = 0; row < 7; row++) {
                RowConstraints rows = new RowConstraints(10, 58, 58,Priority.SOMETIMES,VPos.CENTER, true);
                this.getRowConstraints().add(rows);
            }
        for (int col = 0; col < 4; col++) {
            ColumnConstraints columns = new ColumnConstraints(10, 100, 100, Priority.SOMETIMES, HPos.CENTER, true);
            this.getColumnConstraints().add(columns);
        }
        
        alphabets_char = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }
    
        
    public void imposeLetter_rack(){
        alphabetsInlabel.clear();
        removedAlphabet.clear();
        
        int get_legth = 0;
        int somenumber = 0;
        try {
            for(char a : alphabets_char){
                String converttoString = Character.toString(a);
                int delay = somenumber*50;
                Letter_label constructlabels = new Letter_label(converttoString);
                constructlabels.Reveal_Alphabets(delay);
                alphabetsInlabel.add(constructlabels);
                somenumber++;
            }
            
            for(int row=0; row<7; row++){   
                for(int col=0; col<4; col++){
                    if(get_legth<alphabetsInlabel.size()){
                        this.getChildren().remove(alphabetsInlabel.get(get_legth));
                        
                        this.add(alphabetsInlabel.get(get_legth), col, row);
                        get_legth++;
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error imposeLetter_rack()" + e); 
        }
    }
    
    public void RemoveAllLetters(){
        for (Letter_label r : alphabetsInlabel) {
            r.Remove_Alphabet();
        }
        alphabetsInlabel.clear();
    }
    
    public String RemoveLetter(String Letter){
        
        String letterString = Letter;
        
        for(Letter_label r : removedAlphabet){
            if (r.getText().equals(Letter) || Letter.toCharArray().length > 1){
                letterString = "";
            }
        }
        
        for (int i = 0; i < alphabetsInlabel.size(); i++) {
            String get = alphabetsInlabel.get(i).getText();
            if (get.equalsIgnoreCase(Letter)) {
                removedAlphabet.add(alphabetsInlabel.get(i));
                alphabetsInlabel.get(i).Remove_Alphabet();
                alphabetsInlabel.remove(i);
            }
        }
        
        return letterString;
    }
    
    public void resetletter_rack(boolean trigger){
        if(trigger){
            
            RemoveAllLetters();
            imposeLetter_rack();
        }
    }
    
    public String letterisclicked(EventTarget clicked){
        String letter = "";
        try {
                if(clicked instanceof Text){
                    
                    Text clickedLabel = (Text) clicked;
                    
                    letter = clickedLabel.getText();
                }
        } catch (Exception e) {
            System.out.println("Error letterisclicked(param) "+ e);
        }
        return RemoveLetter(letter);
    }
    
    public String letteristyped(Object keycodeEvent){
        String letter = "";
        try{
            if(keycodeEvent instanceof KeyCode){
                KeyCode keycode = (KeyCode) keycodeEvent;
                letter = keycode.getName();

            }
        }catch(Exception e){
            System.out.println("Error letteristyped(param) "+ e);
        }
        return RemoveLetter(letter);
    }

    public ObservableList<Letter_label> getAlphabetsInlabel() {
        return alphabetsInlabel;
    }
}
