/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maingame;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author Gerryl
 */
public class Letter_label extends Label{
    ScaleTransition removeletter;
    ScaleTransition letter_effect;
    
    public Letter_label(String letter_name){
        super(letter_name);
        this.setPrefHeight(1);
        this.prefWidth(50.00);
        this.setAlignment(Pos.CENTER);
        this.setFont(Font.font("Segoe Print",48.00));
        this.setTextAlignment(TextAlignment.CENTER);
        this.setScaleY(0);
    }
    
    public void Reveal_Alphabets(int delay){
        int delaytimer = delay+1000;
        letter_effect = new ScaleTransition(Duration.millis(200),this);
        letter_effect.setDelay(Duration.millis(delaytimer));
        letter_effect.setToY(1);
        letter_effect.setCycleCount(1);
        letter_effect.playFromStart();
    }
    
    public void Remove_Alphabet(){
        removeletter = new ScaleTransition(Duration.millis(500),this);
        removeletter.setToY(0);
        removeletter.setCycleCount(1);
        removeletter.play();
    }

    public ScaleTransition getRemoveletter() {
        return removeletter;
    }

    public ScaleTransition getLetter_effect() {
        return letter_effect;
    }
}
    
