/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maingame;

import FileLibraryHangman.HangmanImages;
import hangmangerryl.HangManGerryl;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
/**
 *
 * @author Gerryl
 */
public class Gameboard extends Pane{
    private GuessDletter_rack phantomletters;
    private Letter_rack alphabets;
    private Hang_man hangmanimage;
    private Words words;
    private TimeLimit timer;
    private Label ScoreLabel;
    private Button hintButton;
    private Button quitgame;
    private Button nextgame;
    private StackPane ShowBox;
    private Stage popupStage;
    
    private final ObservableList timetriger;
    
    private final ArrayList<HangmanImages> imagesList;
    private final ArrayList<String> wordsList;
    
    private final EventHandler<KeyEvent> keypress;
    
    private String DificultyString;
    private int HintInteger = 0;
    private int Score = 0;
    private Boolean turnFinished = false;
    private Boolean GameoverStatus = false;
    private Boolean PauseGame = false;
    private Boolean animationfinished = false;

    public Gameboard(ArrayList<HangmanImages> imagesList, ArrayList<String> wordsList, String difficulty) {
        super();
        super.setPrefHeight(910);
        super.setPrefWidth(600);
        super.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        super.setFocusTraversable(true);
        super.setDisable(true);
        
        this.DificultyString = difficulty;
        this.imagesList = imagesList;
        this.wordsList = wordsList;
        
        ScoreLabel = new Label();
        ScoreLabel.setText("Your Score: "+Integer.toString(Score));
        ScoreLabel.setFont(Font.font("Segoe Print", FontWeight.BOLD, 36));
        ScoreLabel.setAlignment(Pos.CENTER);
        ScoreLabel.setLayoutX(288);
        ScoreLabel.setLayoutY(14);
        
        hintButton = new Button("Hint: "+Integer.toString(HintInteger));
        hintButton.setPrefSize(150, 20);
        hintButton.setLayoutX(650);
        hintButton.setLayoutY(14);
        hintButton.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 18));
        hintButton.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
        hintButton.setOnMouseClicked((MouseEvent evt) -> {
            GiveHints();
            HintInteger--;
            hintButton.setText("Hint: "+Integer.toString(HintInteger));
            if(HintInteger == 0){
                hintButton.setDisable(true);
            }
        });
        
        quitgame = new Button("Quit Game");
        quitgame.setPrefSize(200, 50);
        quitgame.setTranslateY(-40);
        quitgame.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 18));
        quitgame.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 5px");
        nextgame = new Button("Next Game >>");
        ShowBox = new StackPane();
        ShowBox.setPrefSize(400, 200);
        ShowBox.setOpacity(0);
        ShowBox.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px; -fx-border-width: 5px");
        popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(HangManGerryl.getpStage());
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(ShowBox));
        
        phantomletters = new GuessDletter_rack();
        alphabets = new Letter_rack();
        hangmanimage = new Hang_man(imagesList);
        timer = new TimeLimit();
        words = new Words(wordsList);
        
        timetriger = timer.getTimetrigger();
        timetriger.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                while(c.next()){
                    if (c.wasAdded()) {
                        gameOver();
                    }
                }
            }
        });

        keypress = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                
                System.out.println(event.getCode());
                Object keycodeoObject = event.getCode();
                String letterString = alphabets.letteristyped(keycodeoObject); 
                
                validateEntry(letterString);
                
                
                if (event.getCode() == KeyCode.ESCAPE) {
                    PauseGame = true;
                    showPopup("pausemenu");
                }
                
                if(!letterString.isEmpty()){
                    
                    turnFinished = true;
                }
            }
            
        };
        super.setOnKeyPressed(keypress);
        
        EventHandler<MouseEvent> mouseclicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getTarget());
                String letterString = alphabets.letterisclicked(event.getTarget());

                validateEntry(letterString);
                
                turnFinished = true;
            }
        };
        alphabets.setOnMouseClicked(mouseclicked);
        
       
    }
    
    public void resetGameBoard(){
        
        phantomletters.setWordinchars(words.Newword());
        alphabets.imposeLetter_rack();
        hangmanimage.resetImage();
        timetriger.clear();
        
        
        switch(this.DificultyString){
            case "Easy":
                alphabets.getAlphabetsInlabel().get(alphabets.getAlphabetsInlabel().size() -1).getLetter_effect().setOnFinished(((event) -> {
                    animationfinished = true;
                    timer.start(this.DificultyString);
                    timer.setTimePause();
                    setVisibility(false);
                    GiveHints();
                    GiveHints();
                    }));
            break;
            
            case "Medium":
                alphabets.getAlphabetsInlabel().get(alphabets.getAlphabetsInlabel().size() -1).getLetter_effect().setOnFinished(((event) -> {
                    animationfinished = true;
                    timer.start(this.DificultyString);
                    timer.setTimePause();
                    setVisibility(false);
                    GiveHints();
                    }));
            break;
            
            case "Hard":
                alphabets.getAlphabetsInlabel().get(alphabets.getAlphabetsInlabel().size() -1).getLetter_effect().setOnFinished(((event) -> {
                    animationfinished = true;
                    timer.start(this.DificultyString);
                    timer.setTimePause();
                    setVisibility(false);
                }));
            break;
        }
    }
    
    
    public void startGame(){
        
        turnFinished = false;
        GameoverStatus = false;
        PauseGame = false;
        words.Resetwords();
        resetGameBoard();
        switch(this.DificultyString){
            case "Easy":
                HintInteger = 7;
                hintButton.setText("Hint: "+Integer.toString(HintInteger));
            break;
            
            case "Medium":
                HintInteger = 5;
                hintButton.setText("Hint: "+Integer.toString(HintInteger));
            break;
            
            case "Hard":
                HintInteger = 3;
                hintButton.setText("Hint: "+Integer.toString(HintInteger));
            break;
        }
        super.getChildren().addAll(phantomletters, alphabets, hangmanimage, timer, ScoreLabel, hintButton);
    }
    
    public void nextGame(){
        
        GameoverStatus = false;
        PauseGame = false;
        animationfinished = false;
        
        super.setDisable(true);
        resetGameBoard();
        HintInteger++;
        hintButton.setText("Hint: "+Integer.toString(HintInteger));
    }
    
    public void GiveHints(){
        Random ran = new Random();
        int rannum = ran.nextInt((phantomletters.getLetters_in_labels().size()) - 1);
        while (!phantomletters.getLetters_in_labels().get(rannum).getText().equals("_")) {            
            rannum = ran.nextInt((phantomletters.getLetters_in_labels().size()) - 1);
        }
        
        String removeletter = alphabets.RemoveLetter(Character.toString(phantomletters.getWordinchars()[rannum]));
        phantomletters.Contains_aletter(removeletter);
        
        if(phantomletters.AllLettersRevealed()){
            gameFinished();
        }
        
    }
    
    public void gameOver(){
        
        GameoverStatus = true;
        animationfinished = false;
        Score -= 4;
        ScoreLabel.setText("Your Score: "+Integer.toString(Score));
        alphabets.RemoveAllLetters();
        hangmanimage.setMistakes(6);
        timer.setTimeStop();
        timer.SetGameoverLabel();
        super.setDisable(true);
    }
    
    public void turnFinished(){
        timer.setTimePause();
        super.setDisable(true);
        turnFinished = true;
    }
    
    public void turnActive(){
        
        if (animationfinished) {
            timer.setContinueTime();
        }
        
        while(PauseGame) {
            timer.setTimePause();
            super.setDisable(true);
        }
        
        setVisibility(true);
        super.setDisable(false); 
        
        
    }
    
    public void gameFinished(){
        GameoverStatus = true;
        animationfinished = false;
        Score += 4;
        ScoreLabel.setText("Your Score: "+Integer.toString(Score));
        hangmanimage.setMistakes(7);
        alphabets.RemoveAllLetters();
        timer.setTimeStop();
        super.setDisable(true);
        
    }
    
    public void validateEntry(String letterString){
        
        boolean mistakes = true;
        
        if(!letterString.isEmpty()){
            mistakes = phantomletters.Contains_aletter(letterString);
            turnFinished();
        }

        if(phantomletters.AllLettersRevealed()){
            gameFinished();
        }else{
            int m = hangmanimage.recieveMistake(mistakes);
            if(m == 6){
                gameOver();
            }
        }   
    }
    
    public FadeTransition showPopup(String type){
        
        super.setDisable(true);
        timer.setTimePause();

        ShowBox.setFocusTraversable(true);
        ShowBox.requestFocus();
        popupStage.show();
        
        FadeTransition fade = new FadeTransition(Duration.millis(300), ShowBox);
        switch(type){
            case "pausemenu":
                fade.setToValue(1);
                fade.play();
                
                //Quitgame button initialized in constructor for special reason : to set the clicking event outside

                Button close = new Button("Continue");
                close.setPrefSize(200, 50);
                close.setTranslateY(40);
                close.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 18));
                close.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 5px");
                
                
                ShowBox.setOnKeyPressed((KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        fade.setToValue(0);
                        fade.playFromStart();
                        fade.setOnFinished((event) -> {
                            popupStage.close();
                            setDisable(false);
                            timer.setContinueTime();
                            PauseGame = false;
                        });
                        
                    }    
                });
                    
                close.setOnMouseClicked((MouseEvent evt) -> {
                    fade.setToValue(0);
                    fade.playFromStart();
                    fade.setOnFinished((event) -> {
                        popupStage.close();
                        setDisable(false);
                        timer.setContinueTime();
                        PauseGame = false;
                    });
                });
                
                ShowBox.getChildren().clear();
                ShowBox.getChildren().addAll(quitgame, close);
            break;
            
            case "nextgame":
                fade.setToValue(1);
                fade.play();
                //this button is also defined in the constructor;
                nextgame.setPrefSize(200, 50);
                nextgame.setTranslateY(40);
                nextgame.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 18));
                nextgame.styleProperty().setValue("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 5px");
                ShowBox.getChildren().clear();
                ShowBox.getChildren().addAll(quitgame, nextgame);
            break;
        }
        
        return fade;
    }

    public Button getQuitgame() {
        return quitgame;
    }

    public Button getNextgame() {
        return nextgame;
    }
    
    public Stage getPopupStage() {
        return popupStage;
    }

    public void setVisibility(Boolean status){
        alphabets.setVisible(status);
        phantomletters.setVisible(status);
    }

    public TimeLimit getTimer() {
        return timer;
    }

    public Boolean getTurnFinished() {
        return turnFinished;
    }

    public void setTurnFinished(Boolean turnFinished) {
        this.turnFinished = turnFinished;
    }

    public Boolean getGameoverStatus() {
        return GameoverStatus;
    }

    public Boolean getPauseGame() {
        return PauseGame;
    }
    
}
