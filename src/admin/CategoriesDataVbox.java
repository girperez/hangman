package admin;

import FileLibraryHangman.HangmanCategory;
import FileLibraryHangman.ProcessFiles;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Duration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gerryl
 */
public class CategoriesDataVbox extends VBox{
    
    private ObservableList<HangmanCategory> categoryDatas;
    private TableView <HangmanCategory> table;
    private ProcessFiles categoryData;
    private Button revealAddbtn;
    private Button Addbtn;
    private TextField catNameTextField;
    private TextArea catNameTextArea;
    private Pane LowerAreaPane;
    private Pane UtilityPane;
    private Label catNameLabel;
    private Label catItemsLabel;

    public CategoriesDataVbox(ObservableList<HangmanCategory> categoryDatas, ProcessFiles categoryFile) {
        super();
        super.setPrefWidth(800);
        super.setPrefHeight(600);
        super.setOpacity(0);
        super.styleProperty().setValue("-fx-background-color: #fafafa;");
        
        this.categoryDatas = categoryDatas;
        this.categoryData = categoryFile;
        
        try {
            
            //table properties
            table = new TableView<HangmanCategory>(categoryDatas);
            table.setPrefHeight(300);
            
            //button to reveal add properties button
            revealAddbtn = new Button("Add Category >>");
            revealAddbtn.setPrefSize(120, 25);
            revealAddbtn.setLayoutX(14);
            revealAddbtn.setLayoutY(14);
            revealAddbtn.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            revealAddbtn.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
            revealAddbtn.setOnMouseClicked(((mouseevnt) -> {
                if (revealAddbtn.getText().contains(">>")) {
                    TransitionAnimtaion2();
                    revealAddbtn.setText(revealAddbtn.getText().replace(">>", "<<"));
                }
                else if (revealAddbtn.getText().contains("<<")) {
                    TransitionAnimation3();
                    revealAddbtn.setText(revealAddbtn.getText().replace("<<", ">>"));
                }
            }));

            //addbutton property
            Addbtn = new Button("Add");
            Addbtn.setPrefSize(80, 25);
            Addbtn.setLayoutX(14);
            Addbtn.setLayoutY(230);
            Addbtn.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            Addbtn.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
            Addbtn.setOnMouseClicked(((event3) -> {
                if (!catNameTextField.getText().equals("") && !catNameTextArea.getText().equals("")) {
                    try {
                        ArrayList<String> forgedrows = new ArrayList<>();
                        String[] rows = catNameTextArea.getText().split("\n");
                        for (int i = 0; i < rows.length; i++) {
                            String row = rows[i];
                            forgedrows.add(row);
                        }
                        categoryData.AddnewCategory(catNameTextField.getText(), forgedrows);
                        this.categoryDatas.clear();
                        this.categoryDatas.addAll(categoryData.getCategoryList());
                        catNameTextField.clear();
                        catNameTextArea.clear();
                        categoryData.saveChangesCategory(new File("categoryFile.dat"));
                    } catch (Exception ex) {
                        System.out.println("Error "+ex);
                    }
                }
            }));
            
            
            
            //Textfield properties
            catNameTextField = new TextField();
            catNameTextField.setPrefSize(155, 25);
            catNameTextField.setFont(Font.font("Segoe UI Black", FontPosture.REGULAR, 12));
            catNameTextField.setLayoutX(121);
            catNameTextField.setLayoutY(25);
            
            //TextAreaProperties
            catNameTextArea = new TextArea();
            catNameTextArea.setPrefSize(200, 200);
            catNameTextArea.setFont(Font.font("Segoe UI Black", FontPosture.REGULAR, 12));
            catNameTextArea.setLayoutX(121);
            catNameTextArea.setLayoutY(63);
            
            //Labels
            catNameLabel = new Label("Category Name: ");
            catNameLabel.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            catNameLabel.setLayoutX(20);
            catNameLabel.setLayoutY(30);
            
            catItemsLabel = new Label("Items: ");
            catItemsLabel.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            catItemsLabel.setLayoutX(20);
            catItemsLabel.setLayoutY(70);
            
            //lowerPaneArea
            LowerAreaPane = new Pane();
            UtilityPane = new Pane();
            UtilityPane.setPrefSize(335, 300);
            UtilityPane.setLayoutX(160);
            UtilityPane.setOpacity(0);
            UtilityPane.setDisable(true);
            UtilityPane.setTranslateX(-160);
            
            
            PopulateTableView();
            LowerAreaPane.getChildren().addAll(revealAddbtn,UtilityPane);
            UtilityPane.getChildren().addAll(Addbtn,catNameTextArea,catNameTextField,catItemsLabel,catNameLabel);
            super.getChildren().addAll(table,LowerAreaPane);
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void PopulateTableView(){
        
        TableColumn CategoryColumn = new TableColumn("Categories");
        CategoryColumn.setMinWidth(200);
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<HangmanCategory, String>("categoryName"));
        
        //Image Size Column
        TableColumn Items = new TableColumn("Items");
        Items.setMinWidth(1500);
        Items.setCellValueFactory(new PropertyValueFactory<HangmanCategory, String>("items"));
        
        table.getColumns().addAll(CategoryColumn,Items);
    }
    
    public void TransitionAnimation(){
        Timeline timeline = new Timeline();
        KeyFrame hideVbox = new KeyFrame(Duration.millis(0), new KeyValue(super.opacityProperty(), 0));
        KeyFrame revealVbox = new KeyFrame(Duration.millis(500), new KeyValue(super.opacityProperty(), 1, Interpolator.EASE_IN));
        timeline.getKeyFrames().addAll(hideVbox,revealVbox);
        timeline.play();
    }
    
    public void TransitionAnimtaion2(){//reveal the utility pane
        UtilityPane.setDisable(false);
        Timeline timeline = new Timeline();
        KeyFrame revealpaneFrame = new KeyFrame(Duration.millis(100), new KeyValue(UtilityPane.opacityProperty(), 1, Interpolator.EASE_BOTH));
        KeyFrame moveoutFrame = new KeyFrame(Duration.millis(200), new KeyValue(UtilityPane.translateXProperty(),0, Interpolator.EASE_BOTH));
        timeline.getKeyFrames().addAll(revealpaneFrame,moveoutFrame);
        timeline.play();
    }
    
    public void TransitionAnimation3(){//hide utilitypane
        Timeline timeline = new Timeline();
        KeyFrame hidepaneFrame = new KeyFrame(Duration.millis(100), new KeyValue(UtilityPane.opacityProperty(), 0));
        KeyFrame moveinFrame = new KeyFrame(Duration.millis(200), new KeyValue(UtilityPane.translateXProperty(),-160, Interpolator.EASE_BOTH));
        timeline.getKeyFrames().addAll(hidepaneFrame,moveinFrame);
        timeline.play();
        UtilityPane.setDisable(true);
    }   
}
