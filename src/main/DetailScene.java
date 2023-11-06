package main;

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
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.shape.Circle;
import java.io.File;

class DetailFooter extends HBox{
    private Button deleteButton;
    private Button editButton;
    
    DetailFooter() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 16 arial; -fx-pref-width: 100; -fx-pref-height: 60";

        deleteButton = new Button("Delete");
        deleteButton.setStyle(defaultButtonStyle);
        editButton = new Button("Edit");
        editButton.setStyle(defaultButtonStyle);
        this.getChildren().addAll(deleteButton, editButton);
        deleteButton.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        //editButton.setAlignment(Pos.CENTER_RIGHT);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getEditButton() {
        return editButton;
    }
}

class DetailHeader extends HBox {
    private Button backButton;

    DetailHeader(String title) {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text(title); // Text of the Header
        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setSpacing(30);
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

class Description extends FlowPane {
    Text description;

    Description(String s) {
        description = new Text(s);

        //this.setPrefWrapLength(400);
        description.setWrappingWidth(400);
        // this.setPrefWidth(200);

        this.getChildren().add(description);
    }
}

public class DetailScene extends BorderPane{

    private DetailHeader header;
    private DetailFooter footer;
    private ScrollPane scrollPane;
    private Description desc;
    private Button backButton;
    private Button editButton;
    private Button deleteButton;

    private Recipe recipe;
    
    DetailScene(Recipe r) {
        recipe = r;
        header = new DetailHeader(recipe.getTitle());
        footer = new DetailFooter();

        //System.out.println(recipe.getDescription());
        desc = new Description(recipe.getDescription());

        scrollPane = new ScrollPane(desc);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);

        backButton = header.getBackButton();
        editButton = footer.getEditButton();
        deleteButton = footer.getDeleteButton();

        addListeners();
    }

    public void addListeners() {
        backButton.setOnAction(e -> {
            Main.sceneManager.ChangeScene(Main.root);
            //do stuff
        });

        deleteButton.setOnAction(e -> {
            //stuff
        });

        editButton.setOnAction(e -> {
            //stuff
        });
    }
    
}
