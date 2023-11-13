package UI.MainPage;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import UI.NewRecipePage.NewRecipeScene;
import RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;


public class MainPageMainScene extends BorderPane{
    // this class specifies the layout for the main page UI

    private MainPageHeader header;
    private MainPageFooter footer;
    private MainPageRecipeList recipeList;
    private ScrollPane scrollPane;
    private Button addButton;
    //private Button detailButton;
    private Button mockAddButton;
    private Recipe mockRecipe;


    MainPageMainScene()
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
            NewRecipeScene newRecipeScene = new NewRecipeScene();
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
