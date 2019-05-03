/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import FileLibraryHangman.HangmanCategory;
import FileLibraryHangman.HangmanImages;
import FileLibraryHangman.ProcessFiles;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

/**
 *
 * @author Gerryl
 */
public class AdminMenu extends BorderPane{
    
    private ObservableList<HangmanCategory> categoryDatas;
    private ObservableList<HangmanImages> imageDatas;
    private ProcessFiles read;
    private ProcessFiles reading;
    private CategoriesDataVbox categoriesDataVbox;
    private ImagesDataVbox imagesDataVbox;
    private final VBox leftBox;
    private final VBox rightBox;
    private final VBox centerBox;
    private  Button logoutBtn;
    
//    private VBox gameCategoriesVbox;
//    
//    private Button btnCategories;
//    private Button btnPlayers;
    
    
    
    public AdminMenu(ProcessFiles imgFile, ProcessFiles catFiles) throws FileNotFoundException, IOException, ClassNotFoundException, Exception{
        super();
        super.setPrefWidth(910);
        super.setPrefHeight(600);
        super.setVisible(false);
        super.setDisable(true);
        
        leftBox = new VBox();
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPrefHeight(600);
        leftBox.setPrefWidth(200);
        leftBox.setSpacing(30);
        
        centerBox = new VBox();
        centerBox.setPrefWidth(850);
        centerBox.setPrefHeight(600);
        
        rightBox = new VBox();
        rightBox.setPrefSize(50, 600);
        
        
        super.setLeft(leftBox);
        super.setCenter(centerBox);
        super.setRight(rightBox);
        
        
        
        
        GetLeftBox();
        
        //categories
        
        this.read = catFiles;
        categoryDatas = FXCollections.observableArrayList(read.getCategoryList());
        categoriesDataVbox = new CategoriesDataVbox(getCategoryDatas(), read);
        
        //images
        this.reading = imgFile;
        imageDatas = FXCollections.observableArrayList(reading.getImageList());
        imagesDataVbox = new ImagesDataVbox(getImageDatas(), reading);
    }
    
    
    public void GetLeftBox(){
        Button imagesBtn = new Button("Images");
        imagesBtn.setPrefSize(150, 20);
        imagesBtn.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 18));
        imagesBtn.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
        
        imagesBtn.setOnMouseClicked(((mouseEvent1) -> {
            centerBox.getChildren().clear();
            centerBox.getChildren().add(imagesDataVbox);
            imagesDataVbox.TransitionAnimation();
        }));
        
        
        
        Button categoriesBtn = new Button("Categories");
        categoriesBtn.setPrefSize(150, 20);
        categoriesBtn.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 18));
        categoriesBtn.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
        categoriesBtn.setOnMouseClicked(((mouseEvent2) -> {
            centerBox.getChildren().clear();
            centerBox.getChildren().add(categoriesDataVbox);
            categoriesDataVbox.TransitionAnimation();
        }));
        
        
        logoutBtn = new Button("Log Out");
        logoutBtn.setPrefSize(150, 20);
        logoutBtn.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 18));
        logoutBtn.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
        
        
        leftBox.getChildren().addAll(imagesBtn,categoriesBtn,logoutBtn);
    }

    public ObservableList<HangmanCategory> getCategoryDatas() {
        return categoryDatas;
    }

    public ObservableList<HangmanImages> getImageDatas() {
        return imageDatas;
    }

    public Button getLogoutBtn() {
        return logoutBtn;
    }
}
