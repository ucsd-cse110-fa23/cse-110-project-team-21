package RecipeMaker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import java.io.File;

class DetailHeader extends HBox {
    private Button backButton;

    DetailHeader() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Title"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setSpacing(200);
        //this.setAlignment(Pos.CENTER); // Align the text to the Center


        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        backButton = new Button("Back"); // text displayed on add button
        backButton.setStyle(defaultButtonStyle); // styling the button

        this.getChildren().addAll(backButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER_RIGHT); // aligning the buttons to center
    }

    public Button getBackButton() {
        return backButton;
    }
}

public class DetailScene extends BorderPane{

    private DetailHeader header;
    private Footer footer;
    private Button backButton;

    /*
     * TODO: make it so that this scene takes a recipe object as an input
     */
    DetailScene() {
        header = new DetailHeader();
        footer = new Footer();

        this.setTop(header);
        this.setBottom(footer);

        backButton = header.getBackButton();

        addListeners();
    }

    public void addListeners() {
        backButton.setOnAction(e -> {
            System.out.println("We are on the details page");
            Main.sceneManager.ChangeScene(Main.root);
            //do stuff
        });
    }
    
}
