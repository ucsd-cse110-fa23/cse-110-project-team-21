package UI.MainPage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import UI.DetailPage.DetailScene;
import UI.NewRecipePage.VInputScene;
import RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;

class MainPageHeader extends VBox {
    // this class specifies the header UI (including buttons) for the main page
    
    private Button addRecipeButton;
    //private Button detailButton;
    private Button mockAddButton;

    public MainPageHeader() {
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

        this.getChildren().addAll(addRecipeButton/* , detailButton, mockAddButton*/); // adding buttons to footer
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


class MainPageRecipeList extends VBox {
    // this class specifies that the main page's recipe list is a VBox

    MainPageRecipeList() {
        this.setSpacing(5); // sets spacing between Recipes
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }
}

class MainPageRecipeCard extends HBox {
    // this class sets up the UI/Button for each recipe on the main page's list 
    
    private Button clickTitle;
    private DetailScene details;


    public MainPageRecipeCard(String title) {
        this.setPrefSize(500, 20); // sets size of Recipe
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of RecipeCard

        clickTitle = new Button(title);
        clickTitle.setPrefSize(500, 20);
        clickTitle.setPrefHeight(Double.MAX_VALUE);
        clickTitle.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button
        this.getChildren().add(clickTitle);

        //System.out.println(Main.recipeManager.getRecipe(title).getTitle() + " " + Main.recipeManager.getRecipe(title).getDescription());
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

class MainPageFooter extends HBox {
    // this class specifies the footer UI for the main page
    
    public MainPageFooter() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
    }
}

public class MainScene extends BorderPane{
    // this class specifies the layout for the main page UI

    private MainPageHeader header;
    private MainPageFooter footer;
    private MainPageRecipeList recipeList;
    private ScrollPane scrollPane;
    private Button addButton;
    //private Button detailButton;
    private Button mockAddButton;
    private Recipe mockRecipe;


    MainScene()
    {
        // Initialise the header Object
        header = new MainPageHeader();

        // Create a RecipeList Object to display the UI of saved recipe titles
        recipeList = new MainPageRecipeList();
        
        // Initialise the Footer Object
        footer = new MainPageFooter();

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

        loadFromDatabase();

    }

    public void update() {
        loadFromDatabase();
    }

    public void addListeners()
    {

        // Add button functionality
        addButton.setOnAction(e -> {
            // create a new scene for adding a new Recipe
            VInputScene newRecipeScene = new VInputScene();
            Main.sceneManager.ChangeScene(newRecipeScene);
        });

        mockAddButton.setOnAction(e -> {
            // Create a new Recipe
            String title = "Experienced Chef's Meal: Beef and Spinach Stuffed Chicken with Cheesy Mashed Potatoes";
            String stuff = "this was annoying the fuck out of me";
            mockRecipe = new Recipe(title, stuff);
            Main.recipeManager.addRecipe(mockRecipe);
            this.update();
            // MainPageRecipeCard example = new MainPageRecipeCard(mockRecipe.getTitle());
            // // // Add Recipe to Recipelist
            //  recipeList.getChildren().add(example);
             //Main.recipeManager.addRecipe(mockRecipe);

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
    /**
     * Reads the recipe list from the recipeManager in main and turns every recipe into a
     * recipe card, which gets added as a MainScene child
     */
    public void loadFromDatabase(){
        //System.out.println("LOADING FROM DATABASE");
        recipeList.getChildren().clear();
        for(Recipe r: Main.recipeManager.getList()){
            MainPageRecipeCard toAdd = new MainPageRecipeCard(r.getTitle());
            recipeList.getChildren().add(toAdd);
        }
    }
}
