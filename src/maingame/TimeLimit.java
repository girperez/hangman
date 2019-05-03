/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maingame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Gerryl
 */
public class TimeLimit extends HBox{

    private final int Easy = 61;
    private final int Medium = 41;
    private final int Hard = 21;
    private int seconds = 0;
    private boolean timeFinished = false;
    private final Label timeLabel;
    private final Label timeLabeltwo;
    private final ObservableList timetrigger;
    private Timeline time;
    
    public TimeLimit() {
        super();
        super.setLayoutX(689);
        super.setLayoutY(486);
        super.setPrefWidth(200);
        super.setPrefHeight(100);
        super.setAlignment(Pos.CENTER);
        
        timetrigger = FXCollections.observableArrayList();
        
        timeLabel = new Label();
        timeLabel.setFont(Font.font("Segoe Print", 36));
        this.getChildren().add(timeLabel);
        
        timeLabeltwo = new Label();
        timeLabeltwo.setFont(Font.font("Segoe Print", 36));
        this.getChildren().add(timeLabeltwo);
        
        
        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seconds--;
                timeLabel.setText(Integer.toString(seconds));
                if (seconds<=0) {
                    time.stop();
                    timeFinished = true;
                    timetrigger.add(timeLabel);
                    SetGameoverLabel();
                    System.out.println("time finished "+timeFinished);
                }
            }
        });
        time.getKeyFrames().add(frame);
        
    }
    
    
    public boolean start(String DificultyString){
        switch(DificultyString){
            case "Easy" :
                
                seconds = Easy;
                break;
            case "Medium" :
                
                seconds = Medium;
                break;
            case "Hard" :
                
                seconds = Hard;
                break;
            default:
                System.out.println("Dificulty timer error");
                break;
        }
        timeLabel.setFont(Font.font("Segoe Print", 36));
        time.play();
        return timeFinished;
    }
    
    
    public ObservableList getTimetrigger() {
        return timetrigger;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }
    
    public void SetGameoverLabel(){
        timeLabel.setFont(Font.font("Segoe Print", 20));
        timeLabel.setText("Game Over");
    }

    public void setTimeStop() {
        time.stop();
    }
    
    public void setTimePause(){
        time.pause();
    }

    public void setContinueTime(){
        if (seconds != 0) {
            time.play();
        }
    }
    
    public Timeline getTime() {
        return time;
    }
}
