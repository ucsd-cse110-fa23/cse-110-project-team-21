package DetailPage;

import java.io.File;

import RecipeManager.RecipeModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    ImageView previewImage;
    HBox previewImageRegion;

    public DetailPageDescription(String s, String previewImgPath) {

        Image img = new Image(new File(previewImgPath).toURI().toString());
        this.previewImage = new ImageView(img);
        previewImageRegion = new HBox();
        previewImageRegion.setPadding(new Insets(20,0,0,20));
        previewImageRegion.getChildren().add(previewImage);
        this.getChildren().add(previewImageRegion);
        previewImageRegion.setAlignment(Pos.CENTER);


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
    private Button urlButton;
    
    public DetailPageFooter() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 16 arial; -fx-pref-width: 100; -fx-pref-height: 60";
        deleteButton = new Button("Delete");
        deleteButton.setStyle(defaultButtonStyle);
        editButton = new Button("Edit");
        editButton.setStyle(defaultButtonStyle);
        urlButton = new Button("Share");
        urlButton.setStyle(defaultButtonStyle);
        this.getChildren().addAll(deleteButton, editButton, urlButton);
        deleteButton.setAlignment(Pos.CENTER);
        this.setSpacing(20);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
    public Button getEditButton() {
        return editButton;
    }

    public Button getURLButton() {
        return urlButton;
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
        controller = new DetailController(this);
        
        recipe = currRecipe;
        String imgPath = controller.generateImage(recipe.getTitle().replace(" ", "%20"));
        imgPath = imgPath.replace(" ", "%20");
        imgPath = imgPath.replace(":", "_");
        
        System.out.println("PRINT STATEMENT: " + imgPath);
        header = new DetailPageHeader(recipe.getTitle());
        footer = new DetailPageFooter();
        desc = new DetailPageDescription(recipe.getDescription(), imgPath);


        scrollPane = new ScrollPane(desc);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);
        controller.activate();
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
    public Button getURLButton() {
        return footer.getURLButton();
    }
    public void updateDescription(String newDescription) {
        desc.updateDescription(newDescription);
    }

}
