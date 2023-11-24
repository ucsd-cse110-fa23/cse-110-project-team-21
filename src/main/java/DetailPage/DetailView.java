package DetailPage;

import RecipeManager.RecipeModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ScrollPane;

class DetailPageHeader extends HBox {
    private Button backButton;
    public DetailPageHeader(String title) {
        this.setPrefSize(500, 60); 
        this.setStyle("-fx-background-color: #F0F8FF;");
        Text titleText = new Text(title); 
        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");

        this.getChildren().add(titleText);
        this.setSpacing(30);
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";
        backButton = new Button("Back"); 
        backButton.setStyle(defaultButtonStyle); 
        this.getChildren().addAll(backButton); 
        this.setAlignment(Pos.CENTER_RIGHT); 
    }

    public Button getBackButton() {
        return backButton;
    }
}


class DetailPageDescription extends FlowPane {
    Text description;

    public DetailPageDescription(String s) {
        description = new Text(s);
        description.setWrappingWidth(400);
        this.getChildren().add(description);
    }
    public void updateDescription(String newDescription) {
        this.description.setText(newDescription);
    }
}


class DetailPageFooter extends HBox{
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
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
    public Button getEditButton() {
        return editButton;
    }
}


public class DetailView extends BorderPane{
    private DetailPageHeader header;
    private DetailPageFooter footer;
    private ScrollPane scrollPane;
    private DetailPageDescription desc;
    private RecipeModel recipe;
    public DetailController controller;
    
    public DetailView(RecipeModel currRecipe) {
        recipe = currRecipe;
        header = new DetailPageHeader(recipe.getTitle());
        footer = new DetailPageFooter();
        desc = new DetailPageDescription(recipe.getDescription());
        controller = new DetailController(this);
        controller.activate();

        scrollPane = new ScrollPane(desc);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);
    }

    public Button getBackButton() {
        return header.getBackButton();
    }
    public Button getEditButton() {
        return footer.getEditButton();
    }
    public Button getDeleteButton() {
        return footer.getDeleteButton();
    }
    public RecipeModel getRecipe() {
        return recipe;
    }
    public void updateDescription(String newDescription) {
        desc.updateDescription(newDescription);
    }
}
