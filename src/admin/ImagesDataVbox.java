/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import FileLibraryHangman.HangmanImages;
import FileLibraryHangman.ProcessFiles;
import java.io.File;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Gerryl
 */
public class ImagesDataVbox extends VBox{
    
    private ProcessFiles imagesDataFile;
    private ObservableList<HangmanImages> imagesList;
    private TableView<HangmanImages> imagesTableView;
    private Button revealAddbtn;
    private Button chooseImageButton;
    private Button addbtn;
    private TextField imagenameTextField;
    private TextField fileName;
    private FileChooser fileChooser;
    private File chooseFile;
    private Pane LowerAreaPane;
    private Pane UtilityPane;
    private Label imgNameLabel;
    private Label imgItemsLabel;

    public ImagesDataVbox(ObservableList<HangmanImages> imageList, ProcessFiles processFiles) {
        super();
        super.setPrefWidth(800);
        super.setPrefHeight(600);
        super.styleProperty().setValue("-fx-background-color: #fafafa;");
        super.setOpacity(0);
        
        
        try {
            
            //initialize
            this.imagesList = imageList;
            this.imagesDataFile = processFiles;
            fileChooser = new FileChooser();

            //table
            imagesTableView = new TableView<HangmanImages>(imagesList);
            imagesTableView.setPrefHeight(300);
            
            //Buttons
            revealAddbtn = new Button("Add Image >>");
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

            addbtn = new Button("Add");
            addbtn.setPrefSize(80, 25);
            addbtn.setLayoutX(125);
            addbtn.setLayoutY(135);
            addbtn.setDisable(true);
            addbtn.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            addbtn.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
            addbtn.setOnMouseClicked(((mouseevnt1) -> {
                if (chooseFile.exists()) {
                    try {
                        imagesDataFile.AddImage(chooseFile);
                        this.imagesList.clear();
                        this.imagesList.addAll(imagesDataFile.getImageList());
                        addbtn.setDisable(true);
                        imagenameTextField.clear();
                        fileName.clear();
                        this.imagesDataFile.saveChangesImg(new File("imageFile.dat"));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }));
            
            chooseImageButton = new Button("Choose file");
            chooseImageButton.setPrefSize(140, 25);
            chooseImageButton.setLayoutX(125);
            chooseImageButton.setLayoutY(95);
            chooseImageButton.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            chooseImageButton.styleProperty().setValue("-fx-background-color: #F5F5F5; -fx-border-color: black; -fx-border-radius: 5px");
            chooseImageButton.setOnMouseClicked(((mouseevnt2) -> {
                try {
                    chooseFile = fileChooser.showOpenDialog(new Stage(StageStyle.UTILITY));
                    imagenameTextField.setText(chooseFile.getName());
                    fileName.setText(chooseFile.getPath());
                    addbtn.setDisable(false);
                } catch (Exception e) {
                    System.out.println("chooseImageButn: "+ e);
                }
                
            }));
            
            //Textfield properties
            imagenameTextField = new TextField();
            imagenameTextField.setPrefSize(155, 25);
            imagenameTextField.setFont(Font.font("Segoe UI Black", FontPosture.REGULAR, 12));
            imagenameTextField.setLayoutX(125);
            imagenameTextField.setLayoutY(25);
            
            fileName = new TextField("//");
            fileName.setPrefSize(155, 25);
            fileName.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            fileName.setLayoutX(125);
            fileName.setLayoutY(65);
            
            //Labels
            imgNameLabel = new Label("Category Name : ");
            imgNameLabel.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            imgNameLabel.setLayoutX(20);
            imgNameLabel.setLayoutY(27);
            
            imgItemsLabel = new Label("Image Path : ");
            imgItemsLabel.setFont(Font.font("Segoe UI Black", FontPosture.ITALIC, 12));
            imgItemsLabel.setLayoutX(20);
            imgItemsLabel.setLayoutY(67);
                        
            LowerAreaPane = new Pane();
            UtilityPane = new Pane();
            UtilityPane.setPrefSize(327, 300);
            UtilityPane.setLayoutX(160);
            UtilityPane.setOpacity(0);
            UtilityPane.setDisable(true);
            UtilityPane.setTranslateX(-160);
            
            PopulateTableView();
            UtilityPane.getChildren().addAll(addbtn,chooseImageButton,imagenameTextField,imgNameLabel,imgItemsLabel,fileName);
            LowerAreaPane.getChildren().addAll(revealAddbtn,UtilityPane);
            super.getChildren().addAll(imagesTableView,LowerAreaPane);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void PopulateTableView(){
        //Image Name Column
        TableColumn imageNameColumn = new TableColumn("Image Name");
        imageNameColumn.setMinWidth(200);
        imageNameColumn.setCellValueFactory(new PropertyValueFactory<HangmanImages, String>("imageName"));
        
        //Image Size Column
        TableColumn imageSizeColumn = new TableColumn("Image size");
        imageSizeColumn.setMinWidth(200);
        imageSizeColumn.setCellValueFactory(new PropertyValueFactory<HangmanImages, Integer>("imageSize"));
        
        imagesTableView.getColumns().addAll(imageNameColumn,imageSizeColumn);
    }
    
    public void TransitionAnimation(){
        Timeline timeline = new Timeline();
        KeyFrame hideVbox = new KeyFrame(Duration.millis(0), new KeyValue(super.opacityProperty(), 0));
        KeyFrame revealVbox = new KeyFrame(Duration.millis(1000), new KeyValue(super.opacityProperty(), 1, Interpolator.EASE_BOTH));
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
    
    public void setImagesList(ObservableList<HangmanImages> imagesList) {
        this.imagesList = imagesList;
    }

    public void setImagesTableView(TableView<HangmanImages> imagesTableView) {
        this.imagesTableView = imagesTableView;
    }
    
}
