/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maingame;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Gerryl
 */
public class GuessDLetter_label extends Label {
    
    private FadeTransition label_Effect;
    
    public GuessDLetter_label(){
        super();
        this.setPrefHeight(100.00);
        this.prefWidth(60.00);
        this.setAlignment(Pos.CENTER);
        this.setFont(Font.font("Segoe Print",48.00));
//        this.setPadding(new Insets(0,5,0,5));
        this.setOpacity(0);
    }
    
    public void Hide_Letter(int delay){
        String hide = "_";
        int delaytimer = 1000+delay;
        this.setText(hide);
        label_Effect = new FadeTransition(Duration.millis(300), this);
        label_Effect.setDelay(Duration.millis(delaytimer));
        label_Effect.setToValue(1);
        label_Effect.setCycleCount(1);
        label_Effect.play();
    }
    
    public void Reveal_Letter(String reveal){
        this.setOpacity(0);
        this.setText(reveal);
        label_Effect = new FadeTransition(Duration.millis(800), this);
        label_Effect.setToValue(1);
        label_Effect.setCycleCount(1);
        label_Effect.play();
    }

    
}
