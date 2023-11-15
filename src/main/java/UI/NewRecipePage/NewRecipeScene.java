package UI.NewRecipePage;

import java.util.ArrayList;

import Controller.GPTController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import UI.MainPage.Main;
import UI.MainPage.MainScene;
import RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;

class NewRecipeHeader extends HBox {
    // this class specifies the header UI for the new recipe response page
    public Text titleText;
    public NewRecipeHeader(String titleInput) {
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

    public Text getTitleText() {
        return titleText;
    }
}

class NewRecipeDescription extends FlowPane {
    // this class specifies the description (text) UI for the new recipe response page
    Text description;

    public NewRecipeDescription(Recipe recipe) {
        description = new Text(recipe.getDescription());

        //this.setPrefWrapLength(400);
        description.setWrappingWidth(400);
        // this.setPrefWidth(200);

        this.getChildren().add(description);
    }

    public Text getDescription() {
        return description;
    }
}

class NewRecipeFooter extends HBox{
    // this class specifies the footer UI and its buttons for the new recipe response page
    private Button saveButton;
    private Button dontSaveButton;
    
    public NewRecipeFooter() {
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


public class NewRecipeScene extends BorderPane{
    // this class specifies the layout for the new recipe response screen UI and sends the recipe generation request

    private NewRecipeHeader header;
    private NewRecipeFooter footer;
    private ScrollPane scrollPane;
    private NewRecipeDescription desc;
    private GPTController openAIController;
    private Recipe recipe;

    public NewRecipeScene(ArrayList<String> recordingResult) {

        String mealType = recordingResult.get(0);
        String ingredients = recordingResult.get(1);
        this.openAIController = new GPTController();
        this.openAIController.setPerameters(mealType, ingredients);
        
        System.out.println("mealtype: " + mealType);
        System.out.println("ingredients: " + ingredients);

        // send the recipe generation request
        try {
            recipe = openAIController.sendRequest();
            recipe.setTitle(mealType+ ": " + recipe.getTitle());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return ;
        }


        header = new NewRecipeHeader(recipe.getTitle());
        footer = new NewRecipeFooter();
        desc = new NewRecipeDescription(recipe);
        scrollPane = new ScrollPane(desc);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);
        addListeners();

        //System.out.println(header.titleText);
    }

    public void addListeners() {
        Button saveButton = footer.getSaveButton();
        saveButton.setOnAction(e -> {
            //System.out.println("Save button pressed");
            Main.recipeManager.addRecipe(recipe);
            Main.root.update();
            Main.sceneManager.ChangeScene(Main.root);
        });

        // Delete button functionality
        Button dontSaveButton = footer.getDontSaveButton();
        dontSaveButton.setOnAction(e -> {
            //System.out.println("Don't Save button pressed");
            Main.sceneManager.ChangeScene(Main.root);
        });
    }

    public NewRecipeHeader getHeader() {
        return header;
    }

    public NewRecipeFooter getFooter() {
        return footer;
    }

    public NewRecipeDescription getDesc() {
        return desc;
    }


    public GPTController getOpenAIController() {
        return openAIController;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
