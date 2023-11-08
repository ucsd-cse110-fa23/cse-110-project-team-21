package main.UI.MainPage;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import main.UI.NewRecipePage.NewRecipeScene;
import main.java.RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;


public class MainPageMainScene extends BorderPane{


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
            Main.recipeManager.addRecipe(mockRecipe);
            MainPageRecipeCard example = new MainPageRecipeCard(mockRecipe.getTitle());
            // // Add Recipe to Recipelist
             recipeList.getChildren().add(example);
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
        System.out.println("LOADING FROM DATABASE");
        for(Recipe r: Main.recipeManager.getList()){
            MainPageRecipeCard toAdd = new MainPageRecipeCard(r.getTitle());
            recipeList.getChildren().add(toAdd);
        }
    }
}