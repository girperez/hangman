/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmangerryl;

import FileLibraryHangman.HangmanCategory;
import FileLibraryHangman.ProcessFiles;
import admin.AdminMenu;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.util.Duration;
import maingame.Gameboard;
import maingame.Players;

/**
 *
 * @author Gerryl
 */
public class FXMLDocumentController implements Initializable {
    
    private FadeTransition NewGamebtn;
    private ProcessFiles readCategories;
    private ProcessFiles readImagesFiles;
    private FadeTransition mainmenufadeout;
    private FadeTransition mainmenufadein;
    private AdminMenu admin;
    private Gameboard player1;
    private Gameboard player2;
    private Players thread;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            readCategories = new ProcessFiles(new File("categoryFile.dat"));
            readImagesFiles = new ProcessFiles(new File("imageFile.dat"));
            mainmenufadeout = new FadeTransition(Duration.seconds(1), mainmenuPane);
            mainmenufadein = new FadeTransition(Duration.seconds(1), mainmenuPane);
            admin = new AdminMenu(readImagesFiles, readCategories);
            
            admin.getLogoutBtn().setOnMouseClicked(((Evm) -> {
                admin.setVisible(false);
                admin.setDisable(true);
                mainmenuPane.setDisable(false);
                mainmenuPane.setVisible(true);
                mainmenufadein.setToValue(1);
                mainmenufadein.play();
            }));
            
            rootPane.getChildren().add(admin);
            
        } catch (Exception ex) {
            System.out.println("Problem initializing application: " + ex);
        }
    }

    @FXML
    AnchorPane rootPane;
    
    @FXML
    Pane mainmenuPane;
    @FXML
    Pane startgame;
    
    @FXML
    BorderPane MainMenuBorder;
    
    @FXML
    VBox MenuVbox;
    @FXML
    VBox CatVbox;
    
    
    @FXML
    Button ArrowButn;
    @FXML
    Button NewGameButn;
    @FXML
    Button EasyButn;
    @FXML
    Button MediumButn;
    @FXML
    Button HardButn;
    @FXML
    Button MainMenuButn;
    @FXML
    Button ExitButn;
    @FXML
    Button AdminBtn;

    @FXML
    Label dificultyLabel;
    
    
    private void StartGame(String Difficulty, int players, ArrayList<String> words){
        mainmenufadeout.setToValue(0);
        mainmenufadeout.setCycleCount(1);
        mainmenufadeout.play();
        mainmenufadeout.setOnFinished((EvtMs) -> {
            System.out.println("Main menu fade event finished "+EvtMs.getSource());
            for (int i = 0; i < rootPane.getChildren().size(); i++) {
                Node get = rootPane.getChildren().get(i);
                if(get instanceof Gameboard){
                    rootPane.getChildren().remove(i);
                }
            }

            switch(players){
                case 1:
                    player1 = new Gameboard(readImagesFiles.getImageList(),words,Difficulty);
                    player1.getQuitgame().setOnMouseClicked((event) -> {
                        try {
                            player1.getPopupStage().close();
                            Platform.exit();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    });
                    
                    
                    thread = new Players(player1);
                    thread.start();
                    thread.setOnSucceeded((event) -> {
                        thread.reset();
                        FadeTransition pop = player1.showPopup("nextgame");
                        player1.getNextgame().setOnMouseClicked((event1) -> {
                            pop.setToValue(0);
                            pop.playFromStart();
                            player1.getPopupStage().close();
                            player1.nextGame();
                            thread.start();
                        });
                    });
                    
                    thread.setOnCancelled((event) -> {
                        thread.reset();
                    });
                    
                    rootPane.getChildren().add(player1);
                    
                break;
                
                case 2:
                    ArrayList<String> word1 = new ArrayList<>();
                    word1.addAll(words);
                    ArrayList<String> word2 = new ArrayList<>();
                    word2.addAll(words);
                    
                    
                    player1 = new Gameboard(readImagesFiles.getImageList(),words,Difficulty);
                    player1.setLayoutX(0);
                    player1.getQuitgame().setOnMouseClicked((event) -> {
                        try {
                            player1.getPopupStage().close();
                            reload();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    });
                    
                    
                    player2 = new Gameboard(readImagesFiles.getImageList(),words,Difficulty);
                    player2.setLayoutX(910);
                    player2.getQuitgame().setOnMouseClicked((event) -> {
                        try {
                            player2.getPopupStage().close();
                            Platform.exit();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    });
                    
                    thread = new Players(player1, player2);
                    thread.setOnSucceeded((event) -> {
                        thread.reset();
                        FadeTransition pop = player2.showPopup("nextgame");
                        player2.getNextgame().setOnMouseClicked((event1) -> {
                            pop.setToValue(0);
                            pop.playFromStart();
                            player2.getPopupStage().close();
                            player1.nextGame();
                            player2.nextGame();
                            thread.start();
                        });
                        
                    });
                    
                    
                    HangManGerryl.getpStage().setMinWidth(1820);
                    HangManGerryl.getpStage().setMaxWidth(1820);
                    HangManGerryl.getpStage().setX(50);
                    rootPane.setScaleX(0);
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), rootPane);
                    scaleTransition.setDelay(Duration.millis(500));
                    scaleTransition.setFromX(0);
                    scaleTransition.setToX(1);
                    scaleTransition.play();
                    scaleTransition.setOnFinished((event) -> {
                        thread.start();
                    });
                    rootPane.getChildren().addAll(player1, player2);

                break;

            }
        });

       
        mainmenuPane.setDisable(true);
    }
    
    private void RevealcategoriesBox(String Difficulty){
        
        for(HangmanCategory readCategory: readCategories.getCategoryList()){
            Button catbtn = new Button(readCategory.getCategoryName());
            catbtn.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            catbtn.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
            catbtn.setOnMouseClicked(((event) -> {
                Button playerButton1 = new Button("1 player");
                playerButton1.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 18));
                playerButton1.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
                playerButton1.setOnMouseClicked((eventM) -> {
                    StartGame(Difficulty, 1, readCategory.getItems());
                });
                
                Button playerButton2 = new Button("2 Players");
                playerButton2.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 18));
                playerButton2.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
                playerButton2.setOnMouseClicked((eventM) -> {
                    StartGame(Difficulty, 2, readCategory.getItems());
                });
                
                CatVbox.getChildren().clear();
                CatVbox.getChildren().addAll(playerButton1, playerButton2);
                
            }));
            
            CatVbox.getChildren().add(catbtn);
        }
        
        CatVbox.setDisable(false);
        Timeline timeline = new Timeline();
        KeyFrame revealpaneFrame = new KeyFrame(Duration.millis(500), new KeyValue(CatVbox.opacityProperty(), 1, Interpolator.EASE_BOTH));
        KeyFrame moveoutFrame = new KeyFrame(Duration.millis(200), new KeyValue(CatVbox.translateXProperty(),0, Interpolator.EASE_BOTH));
        timeline.getKeyFrames().addAll(revealpaneFrame,moveoutFrame);
        timeline.play();
        CatVbox.setVisible(true);
    }
    
    private void reload()throws Exception{
        
        Stage reload = HangManGerryl.getpStage();
        reload.setMaxWidth(920);
        reload.setMinWidth(910);
        
        for (int i = 0; i < rootPane.getChildren().size(); i++) {
            Node get = rootPane.getChildren().get(i);
            if(get instanceof Gameboard){
                rootPane.getChildren().remove(i);
            }
        }
        
        for (int i = 0; i < CatVbox.getChildren().size(); i++) {
            Node get = CatVbox.getChildren().get(i);
            if(get instanceof Button){
                CatVbox.getChildren().remove(i);
            }
        }
        
        mainmenuPane.setDisable(false);
        mainmenuPane.setVisible(true);
        mainmenufadein.setToValue(1);
        mainmenufadein.play();
        

        try {
            player1 = null;
            player2 = null;
            
        } catch (Exception e) {
            System.out.println(e);
        }
            
//        HangManGerryl.getpStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
//        HangManGerryl.getpStage().setMinWidth(910);
    }
    
    @FXML
    private void AdminBtnClicked(ActionEvent event){
        try {

            mainmenufadeout.setToValue(0);
            mainmenufadeout.setOnFinished((EvtMe) -> {
                try {
                    System.out.println("Main menu fade event finished "+EvtMe.getSource());
                    mainmenuPane.setDisable(true);
                    mainmenuPane.setVisible(false);
                    admin.setDisable(false);
                    admin.setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            mainmenufadeout.play(); 
            
        } catch (Exception ex) {
            System.out.println("File Not found");
        }
    }
    
    
    
    @FXML
    private void ArrowButnClicked(ActionEvent event){
        
        String arrowOrientation = ArrowButn.getText();
        NewGameButn.setDisable(false);
        if(arrowOrientation.equals(">")){
            Timeline timelineArrowOpen = new Timeline();
            KeyFrame arrowMove = new KeyFrame(Duration.millis(300), new KeyValue(ArrowButn.translateXProperty(),-165, Interpolator.EASE_BOTH));
            KeyFrame VboxMove = new KeyFrame(Duration.millis(300), new KeyValue(MenuVbox.prefWidthProperty(),190, Interpolator.EASE_BOTH));
            timelineArrowOpen.getKeyFrames().addAll(arrowMove,VboxMove);
            timelineArrowOpen.play();
            timelineArrowOpen.setOnFinished((eve) -> {
                ArrowButn.setText("<");
                AdminBtn.setOpacity(1);
            });
            
            NewGamebtn = new FadeTransition(Duration.millis(500),NewGameButn);
            NewGameButn.setVisible(true);
            NewGamebtn.setDelay(Duration.millis(500));
            NewGamebtn.setFromValue(0);
            NewGamebtn.setToValue(1);
            NewGamebtn.setAutoReverse(true);
            NewGamebtn.setCycleCount(Timeline.INDEFINITE);
            NewGamebtn.play();
            
            
            
        }else if (arrowOrientation.equals("<")) {
            Timeline timelineArrowClose = new Timeline();
            KeyFrame arrowMove = new KeyFrame(Duration.millis(500), new KeyValue(ArrowButn.translateXProperty(),-230, Interpolator.EASE_BOTH));
            KeyFrame VboxMove = new KeyFrame(Duration.millis(500), new KeyValue(MenuVbox.prefWidthProperty(),60, Interpolator.EASE_BOTH));
            timelineArrowClose.getKeyFrames().addAll(arrowMove,VboxMove);
            timelineArrowClose.play();
            timelineArrowClose.setOnFinished((vnt) -> {
                ArrowButn.setText(">");
            });
            
            NewGamebtn.stop();
            NewGamebtn = new FadeTransition(Duration.millis(100),NewGameButn);
            NewGamebtn.setToValue(0);
            NewGamebtn.play();
            
            NewGamebtn.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                   NewGameButn.setVisible(false);
                   NewGamebtn.stop();
                   AdminBtn.setOpacity(0);
                }
            });
        }
    }
    
    @FXML
    private void NewGameButnClicked(ActionEvent event){
        FadeTransition fadelabel = new FadeTransition(Duration.millis(300), dificultyLabel);
        FadeTransition fadeEasyButn = new FadeTransition(Duration.millis(500), EasyButn);
        FadeTransition fadeMeduimButn = new FadeTransition(Duration.millis(700), MediumButn);
        FadeTransition fadeHardButn = new FadeTransition(Duration.millis(900), HardButn);
        
        Timeline timeline = new Timeline();
        KeyFrame moveoutFrame = new KeyFrame(Duration.millis(200), new KeyValue(AdminBtn.translateYProperty(), 80, Interpolator.EASE_BOTH));
        timeline.getKeyFrames().addAll(moveoutFrame);
        timeline.play();
        
        fadelabel.setToValue(1);
        fadelabel.setCycleCount(1);
        fadelabel.play();
        
        fadeEasyButn.setToValue(1);
        fadeEasyButn.setCycleCount(1);
        fadeEasyButn.play();
        fadeEasyButn.setOnFinished(evtE->EasyButn.setDisable(false));
        
        fadeMeduimButn.setToValue(1);
        fadeMeduimButn.setCycleCount(1);
        fadeMeduimButn.play();
        fadeMeduimButn.setOnFinished(evtM->MediumButn.setDisable(false));
        
        fadeHardButn.setToValue(1);
        fadeHardButn.setCycleCount(1);
        fadeHardButn.play();
        fadeHardButn.setOnFinished(evtH->HardButn.setDisable(false));
        
        NewGameButn.setDisable(true);
        NewGamebtn.stop();
    }
    
    @FXML
    private void EasyClicked(ActionEvent event){
        
        System.out.println("Easy Button Clicked");
        RevealcategoriesBox(EasyButn.getText());
    }
    
    @FXML
    private void MediumClicked(ActionEvent event){
        
        System.out.println("Medium Button clicked");
        RevealcategoriesBox(MediumButn.getText());
    }
    
    @FXML
    private void HardClicked(ActionEvent event){
        
        System.out.println("Hard Button Clicked");
        RevealcategoriesBox(HardButn.getText());
        
    }
}
