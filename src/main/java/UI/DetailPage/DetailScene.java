package UI.DetailPage;


import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import UI.MainPage.Main;

import java.util.Optional;

import RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;

class DetailPageHeader extends HBox {
    // this class specifies the header UI (including Back Button) of a detail page

    private Button backButton;

    public DetailPageHeader(String title) {
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

class DetailPageDescription extends FlowPane {
    // this class specifies the description UI of a detail page

    Text description;

    public DetailPageDescription(String s) {
        description = new Text(s);

        //this.setPrefWrapLength(400);
        description.setWrappingWidth(400);
        // this.setPrefWidth(200);

        this.getChildren().add(description);
    }

    public void update(String s) {
        description.setText(s);
    }
}

class DetailPageFooter extends HBox{
    // this class specifies the footer UI (including buttons) of a detail page

    private Button deleteButton;
    private Button editButton;
    
    public DetailPageFooter() {
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


public class DetailScene extends BorderPane{
    // this class specifies the layout for a detail page's UI

    private DetailPageHeader header;
    private DetailPageFooter footer;
    private ScrollPane scrollPane;
    private DetailPageDescription desc;
    private Button backButton;
    private Button editButton;
    private Button deleteButton;

    private Recipe recipe;
    
    public DetailScene(Recipe r) {
        recipe = r;
        header = new DetailPageHeader(recipe.getTitle());
        footer = new DetailPageFooter();

        //System.out.println(recipe.getDescription());
        desc = new DetailPageDescription(recipe.getDescription());

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
            Main.root.update();
            Main.sceneManager.ChangeScene(Main.root);
        });

        deleteButton.setOnAction(e -> {
            // create an alert that allows user to confirm deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Recipe Confirmation");
            alert.setHeaderText("Delete Recipe Confirmation");
            alert.setContentText("Are you sure to permanently delete this recipe?");

            ButtonType buttonConfirm = new ButtonType("Confirm");
            ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonConfirm){
                Main.recipeManager.removeRecipe(recipe);
                Main.root.update();
                Main.sceneManager.ChangeScene(Main.root);
            } 
        });

        editButton.setOnAction(e -> {
            Main.sceneManager.ChangeScene(new EditPage(this, recipe));
        });
    }

    public void update(String s) {
        desc.update(s);
    }
    
}
