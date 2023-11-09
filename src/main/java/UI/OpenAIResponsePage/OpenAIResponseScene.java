package UI.OpenAIResponsePage;


import javafx.scene.control.Button;
import javafx.scene.layout.*;
import UI.MainPage.Main;
import UI.MainPage.MainPageMainScene;
import AIControllers.OpenAIResponseController;
import RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.*;


class OpenAIResponsePageHeader extends HBox {
    public Text titleText;
    public OpenAIResponsePageHeader(String titleInput) {
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

class OpenAIResponsePageFooter extends HBox{
    private Button saveButton;
    private Button dontSaveButton;
    
    public OpenAIResponsePageFooter() {
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

class OpenAIResponsePageDescription extends FlowPane {
    Text description;

    public OpenAIResponsePageDescription(Recipe recipe) {
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
            Main.root.update();
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
