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
import javafx.stage.Modality;
import javafx.scene.shape.Circle;
import java.io.File;

class ResponseHeader extends HBox {
    public Text titleText;
    ResponseHeader(String titleInput) {
        this.setPrefSize(500, 80); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.titleText = new Text(titleInput); // Text of the Header

        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setSpacing(30);

        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }
}


class ResponseFooter extends HBox{
    private Button saveButton;
    private Button dontSaveButton;
    
    ResponseFooter() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 14 arial; -fx-pref-width: 100; -fx-pref-height: 30";

        saveButton = new Button("Save");
        saveButton.setStyle(defaultButtonStyle);
        dontSaveButton = new Button("Don't Save");
        dontSaveButton.setStyle(defaultButtonStyle);
        this.getChildren().addAll(saveButton, dontSaveButton);    
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getDontSaveButton() {
        return dontSaveButton;
    }
}

class ResponseDescription extends FlowPane {
    Text description;

    ResponseDescription(Recipe recipe) {
        description = new Text(recipe.getDescription());

        //this.setPrefWrapLength(400);
        description.setWrappingWidth(400);
        // this.setPrefWidth(200);

        this.getChildren().add(description);
    }
}

public class OpenAIResponseScene extends BorderPane{

    private ResponseHeader header;
    private ResponseFooter footer;
    private ScrollPane scrollPane;
    private ResponseDescription desc;
    private OpenAIController openAIController;
    private Recipe recipe;

    /*
     * TODO: make it so that this scene takes a recipe object as an input
     */
    OpenAIResponseScene() {

        String mealType = "breakfast";
        String ingredients = "eggs, bacon, bread, butter, milk, cheese, salt, pepper";
        this.openAIController = new OpenAIController(mealType, ingredients);


        try {
            recipe = openAIController.sendRequest();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return ;
        }


        header = new ResponseHeader(recipe.getTitle());
        footer = new ResponseFooter();
        desc = new ResponseDescription(recipe);
        scrollPane = new ScrollPane(desc);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);
        addListeners();

        System.out.println(header.titleText);
    }

    

    public void addListeners() {
        Button saveButton = footer.getSaveButton();
        saveButton.setOnAction(e -> {
            //System.out.println("Save button pressed");
            Main.recipeManager.addRecipe(recipe);
            Main.sceneManager.ChangeScene(Main.root);
            //do stuff
        });

        // Delete button functionality
        Button dontSaveButton = footer.getDontSaveButton();
        dontSaveButton.setOnAction(e -> {
            System.out.println("Don't Save button pressed");
            Main.sceneManager.ChangeScene(Main.root);
            //do stuff
        });
    }
}
