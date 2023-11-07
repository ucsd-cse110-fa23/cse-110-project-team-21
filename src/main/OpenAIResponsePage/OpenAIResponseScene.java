package main.OpenAIResponsePage;


import javafx.scene.control.Button;
import javafx.scene.layout.*;
import main.MainPage.Main;
import main.RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;


public class OpenAIResponseScene extends BorderPane{

    private OpenAIResponsePageHeader header;
    private OpenAIResponsePageFooter footer;
    private ScrollPane scrollPane;
    private OpenAIResponsePageDescription desc;
    private OpenAIResponseController openAIController;
    private Recipe recipe;

    /*
     * TODO: make it so that this scene takes a recipe object as an input
     */
    public OpenAIResponseScene() {

        String mealType = "breakfast";
        String ingredients = "eggs, bacon, bread, butter, milk, cheese, salt, pepper";
        this.openAIController = new OpenAIResponseController(mealType, ingredients);


        try {
            recipe = openAIController.sendRequest();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return ;
        }


        header = new OpenAIResponsePageHeader(recipe.getTitle());
        footer = new OpenAIResponsePageFooter();
        desc = new OpenAIResponsePageDescription(recipe);
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

    public OpenAIResponsePageHeader getHeader() {
        return header;
    }

    public OpenAIResponsePageFooter getFooter() {
        return footer;
    }

    public OpenAIResponsePageDescription getDesc() {
        return desc;
    }


    public OpenAIResponseController getOpenAIController() {
        return openAIController;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
