package main;
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

    private Button clickTitle;
    private DetailScene details;


    RecipeCard(String title) {
        this.setPrefSize(500, 20); // sets size of Recipe
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of RecipeCard

        clickTitle = new Button(title);
        clickTitle.setPrefSize(500, 20);
        clickTitle.setPrefHeight(Double.MAX_VALUE);
        clickTitle.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button
        this.getChildren().add(clickTitle);

        details = new DetailScene(Main.recipeManager.getRecipe(title));

        clickTitle.setOnAction(e -> {
            //DetailScene details = new DetailScene(recipeManager.getRecipe(title));        //we must create a new detail scene for each recipe that we click on
            //System.out.println("This is the main page");
            Main.sceneManager.ChangeScene(details);
        });
    }

    public Button getTitleDetailsButton() {
        return this.clickTitle;
    }
}

class RecipeList extends VBox {

    RecipeList() {
        this.setSpacing(5); // sets spacing between Recipes
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
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
    //private Button detailButton;
    private Button mockAddButton;

    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Recipe Maker"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center


        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addRecipeButton = new Button("New Recipe"); // text displayed on add button
        addRecipeButton.setStyle(defaultButtonStyle); // styling the button
        /*detailButton = new Button("Details Page"); // text displayed on add button
        detailButton.setStyle(defaultButtonStyle); // styling the button */
        mockAddButton = new Button("Mock Add"); // text displayed on add button
        mockAddButton.setStyle(defaultButtonStyle); // styling the button

        this.getChildren().addAll(addRecipeButton, /*detailButton,*/ mockAddButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getAddRecipeButton() {
        return addRecipeButton;
    }
    /*public Button getDetailButton() {
        return detailButton;
    } */
    public Button getMockAddButton() {
        return mockAddButton;
    }
}

class MainScene extends BorderPane{

    private Header header;
    private Footer footer;
    private RecipeList recipeList;
    private ScrollPane scrollPane;
    private Button addButton;
    //private Button detailButton;
    private Button mockAddButton;
    private Recipe mockRecipe;


    MainScene()
    {
        // Initialise the header Object
        header = new Header();

        // Create a RecipeList Object to display the UI of saved recipe titles
        recipeList = new RecipeList();
        
        // Initialise the Footer Object
        footer = new Footer();

        scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToWidth(isCache());
        scrollPane.setFitToHeight(isCache());


        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(recipeList);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);

        // Initialise Button Variables through the getters in Footer
        addButton = header.getAddRecipeButton();
        //detailButton = header.getDetailButton();
        mockAddButton = header.getMockAddButton();

        // Call Event Listeners for the Buttons
        addListeners();

        // Create a new Recipe
        String title = "Experienced Chef's Meal: Beef and Spinach Stuffed Chicken with Cheesy Mashed Potatoes";
        String stuff =
                "\r\n" + //
                "Ingredients:\r\n" + //
                "\r\n" + //
                "Beef and Spinach Stuffed Chicken:\r\n" + //
                "\r\n" + //
                "Chicken breasts\r\n" + //
                "Beef (ground)\r\n" + //
                "Spinach\r\n" + //
                "White onion\r\n" + //
                "Salt\r\n" + //
                "Pepper\r\n" + //
                "Cheese (for stuffing)\r\n" + //
                "Mustard\r\n" + //
                "Ketchup\r\n" + //
                "Cheesy Mashed Potatoes:\r\n" + //
                "\r\n" + //
                "Potatoes\r\n" + //
                "Cheese (for mixing and topping)\r\n" + //
                "Milk\r\n" + //
                "Salt\r\n" + //
                "Pepper\r\n" + //
                "Instructions:\r\n" + //
                "\r\n" + //
                "Beef and Spinach Stuffed Chicken:\r\n" + //
                "\r\n" + //
                "Preheat your oven to 375°F (190°C).\r\n" + //
                "\r\n" + //
                "In a skillet, sauté finely chopped white onion and ground beef until the beef is browned. Add spinach and cook until wilted. Season with salt and pepper.\r\n" + //
                "\r\n" + //
                "Butterfly the chicken breasts, then stuff them with the beef and spinach mixture, along with slices of cheese. Close the chicken breasts and secure them with toothpicks.\r\n" + //
                "\r\n" + //
                "Mix mustard and ketchup together to create a glaze.\r\n" + //
                "\r\n" + //
                "Brush the chicken with the glaze and bake in the preheated oven for about 25-30 minutes or until the chicken is cooked through.\r\n" + //
                "\r\n" + //
                "Cheesy Mashed Potatoes:\r\n" + //
                "\r\n" + //
                "Peel and chop the potatoes, then boil them until tender.\r\n" + //
                "\r\n" + //
                "Mash the cooked potatoes, adding milk and cheese for creaminess and flavor. Season with salt and pepper.\r\n" + //
                "\r\n" + //
                "Top the mashed potatoes with more cheese.\r\n" + //
                "\r\n" + //
                "Serve the Beef and Spinach Stuffed Chicken with Cheesy Mashed Potatoes for a more complex and flavorful dish that showcases your culinary skills.";
        mockRecipe = new Recipe(title, stuff);
    }

    public void addListeners()
    {

        // Add button functionality
        addButton.setOnAction(e -> {
            // create a new scene for adding a new Recipe
            NewRecipeScene newRecipeScene = new NewRecipeScene();
            Main.sceneManager.ChangeScene(newRecipeScene);
        });

        mockAddButton.setOnAction(e -> {
            Main.recipeManager.addRecipe(mockRecipe);
             RecipeCard example = new RecipeCard(mockRecipe.getTitle());
            // // Add Recipe to Recipelist
             recipeList.getChildren().add(example);
             Main.recipeManager.addRecipe(mockRecipe);

            // create a new scene for adding a new Recipe
            /*NewRecipeScene newRecipeScene = new NewRecipeScene();
            Main.sceneManager.ChangeScene(newRecipeScene); */
        });

        /*detailButton.setOnAction(e -> {
            DetailScene details = new DetailScene(mockRecipe);        //we must create a new detail scene for each recipe that we click on
            System.out.println("This is the main page");
            Main.sceneManager.ChangeScene(details);
        }); */
        
    }
}

public class Main extends Application {
    public static SceneManager sceneManager;
    public static MainScene root;
    public static RecipeManager recipeManager;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create a RecipeManager object to hold/manage stored recipe data
        recipeManager = new RecipeManager();

        // Setting the Layout of the Window- Should contain a Header, Footer and the RecipeList
        root = new MainScene();
        sceneManager = new SceneManager(primaryStage);
      
        // Set the title of the app
        primaryStage.setTitle("PantryPal");
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
