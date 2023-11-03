package RecipeMaker;
//remove the package name 
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




class RecipeCard extends HBox {

    private TextField RecipeName;
    private Label index;
    private Button deleteButton;


    RecipeCard() {
        this.setPrefSize(500, 20); // sets size of Recipe
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of Recipe

        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(40, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the Recipe
        this.getChildren().add(index); // add index label to Recipe

        RecipeName = new TextField(); // create Recipe name text field
        RecipeName.setPrefSize(380, 20); // set size of text field
        RecipeName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        RecipeName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(RecipeName); // add textlabel to Recipe

        deleteButton = new Button("Delete"); // creates a button for marking the Recipe as done
        deleteButton.setPrefSize(100, 20);
        deleteButton.setPrefHeight(Double.MAX_VALUE);
        deleteButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button
        this.getChildren().add(deleteButton);
    }

    public void setRecipeIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.RecipeName.setPromptText("Recipe " + num);
    }

    public TextField getRecipeName() {
        return this.RecipeName;
    }

    public Button getDeleteButton() {
        return this.deleteButton;
    }

    public void toggleDelete() {

    }
}

class RecipeList extends VBox {

    RecipeList() {
        this.setSpacing(5); // sets spacing between Recipes
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

    public void updateRecipeIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof RecipeCard) {
                ((RecipeCard) this.getChildren().get(i)).setRecipeIndex(index);
                index++;
            }
        }
    }
}

class Footer extends HBox {

    Footer() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
    }

}

class Header extends VBox {

    private Button addRecipeButton;
    private Button detailButton;

    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Recipe Maker"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center


        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addRecipeButton = new Button("Add Recipe"); // text displayed on add button
        addRecipeButton.setStyle(defaultButtonStyle); // styling the button
        detailButton = new Button("Details Page"); // text displayed on add button
        detailButton.setStyle(defaultButtonStyle); // styling the button

        this.getChildren().addAll(addRecipeButton, detailButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getAddRecipeButton() {
        return addRecipeButton;
    }

    public Button getDetailButton() {
        return detailButton;
    }
}

class AppFrame extends BorderPane{

    private Header header;
    private Footer footer;
    private RecipeList RecipeList;
    private ScrollPane scrollPane;
    private Button addButton;
    private Button detailButton;


    AppFrame()
    {
        // Initialise the header Object
        header = new Header();

        // Create a Recipelist Object to hold the Recipes
        RecipeList = new RecipeList();
        
        // Initialise the Footer Object
        footer = new Footer();

        scrollPane = new ScrollPane(RecipeList);
        scrollPane.setFitToWidth(isCache());
        scrollPane.setFitToHeight(isCache());


        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(RecipeList);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);

        // Initialise Button Variables through the getters in Footer
        addButton = header.getAddRecipeButton();
        detailButton = header.getDetailButton();

        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners()
    {

        // Add button functionality
        addButton.setOnAction(e -> {
            
            // Create a new Recipe
            // Recipe Recipe = new Recipe();
            // // Add Recipe to Recipelist
            // RecipeList.getChildren().add(Recipe);
            // // Add deleteButtonToggle to the Delete button
            // Button deleteButton = Recipe.getDeleteButton();
            // deleteButton.setOnAction(e1 -> {
            //     // Call toggleDone on click
            //     Recipe.toggleDelete();
            // });
            // RecipeList.updateRecipeIndices();

            // create a new scene for adding a new Recipe
            NewRecipeScene newRecipeScene = new NewRecipeScene();
            Main.sceneManager.ChangeScene(newRecipeScene);
        });

        detailButton.setOnAction(e -> {
            DetailScene details = new DetailScene();        //we must create a new detail scene for each recipe that we click on
            System.out.println("This is the main page");
            Main.sceneManager.ChangeScene(details);
        });
        
    }
}

public class Main extends Application {
    public static SceneManager sceneManager;
    public static AppFrame root;

    public static SceneManager sceneManager;
    public static AppFrame root;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the RecipeList
        root = new AppFrame();
        sceneManager = new SceneManager(primaryStage);
      
        // Set the title of the app
        primaryStage.setTitle("Recipe Maker");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 500, 600));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
