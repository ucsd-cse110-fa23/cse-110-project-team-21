package UI.DetailPage;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import UI.MainPage.Main;

import java.util.Optional;

import RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;


public class DetailScene extends BorderPane{
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
            // create a alert
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
