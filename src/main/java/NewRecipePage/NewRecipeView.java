package NewRecipePage;

import java.io.File;

import java.util.ArrayList;

import DetailPage.DetailController;
import RecipeManager.RecipeModel;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class NewRecipeHeader extends HBox {
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

    public void setTitleText(String titleText) {
        this.titleText.setText(titleText);
    }
}



class NewRecipeDescription extends FlowPane {
    Text description;
    ImageView previewImage;
    HBox previewImageRegion;

    public NewRecipeDescription(RecipeModel recipe, String previewImgPath) {
        
        
        //Creating an ImageView (and assigning it a region) that takes in the image path generated from the DallEModel 
        Image img = new Image(new File(previewImgPath).toURI().toString());
        this.previewImage = new ImageView(img);
        previewImageRegion = new HBox();
        previewImageRegion.setPadding(new Insets(20,0,0,20));
        previewImageRegion.getChildren().add(previewImage);
        this.getChildren().add(previewImageRegion);
        previewImageRegion.setAlignment(Pos.CENTER);

        description = new Text(recipe.getDescription());
        description.setWrappingWidth(400);
        this.getChildren().add(description);
    }
    public Text getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setPreviewImagePath(String previewImgPath){
        Image img = new Image(new File(previewImgPath).toURI().toString());
        previewImage.setImage(img);
    }
}



class NewRecipeFooter extends HBox{
    private Button saveButton;
    private Button dontSaveButton;
    private Button refreshButton;
    
    public NewRecipeFooter() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 14 arial; -fx-pref-width: 100; -fx-pref-height: 30";
        saveButton = new Button("Save");
        saveButton.setStyle(defaultButtonStyle);
        dontSaveButton = new Button("Don't Save");
        dontSaveButton.setStyle(defaultButtonStyle);
        refreshButton = new Button("Refresh");
        refreshButton.setStyle(defaultButtonStyle);

        HBox topHbox = new HBox();
        HBox bottomHbox = new HBox();
        bottomHbox.getChildren().addAll(saveButton, dontSaveButton);
        bottomHbox.setAlignment(Pos.CENTER);        
        topHbox.setAlignment(Pos.CENTER);
        topHbox.getChildren().addAll(refreshButton);
        this.getChildren().addAll(topHbox, bottomHbox);    
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getDontSaveButton() {
        return dontSaveButton;
    }

    public Button getRefreshButton() {
        return refreshButton;
    }
}


public class NewRecipeView extends BorderPane{
    private static final String DEFAULT_IMAGE = "resources/previewimgs/notfound.png";

    private NewRecipeHeader header;
    private NewRecipeFooter footer;
    private ScrollPane scrollPane;
    private NewRecipeDescription desc;
    private RecipeModel recipe;
    public NewRecipeController NewRecipecontroller;
    ArrayList<String> recordingResult;


    // Constructor, Note: the recordingResult is the result from the recording page
    // You must have the recordingResult in order create this page!
    // Calling the OpenAI will be done in the Model
    public NewRecipeView(ArrayList<String> recordingResult) {
        
        NewRecipecontroller = new NewRecipeController(this); //Creating the controller. Passing the view (this) to help the controller to access.

        /**
         * The following items (header, footer, desc) must be initialized before calling the
         * NewRecipecontroller activate() method. This is because activate will pass in the generated recipe's 
         * title/description to the header, desc, etc.
         */
        header = new NewRecipeHeader("New Recipe");
        footer = new NewRecipeFooter();
        desc = new NewRecipeDescription(new RecipeModel("Title", "Description"), DEFAULT_IMAGE);
        scrollPane = new ScrollPane(desc);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        this.recordingResult = recordingResult;

        NewRecipecontroller.activate(recordingResult); //Activating controller
        
        /**
         * Only after activating the controller will we have a recipe and its title. Which is why we
         * generate an image after activating the controller, so we can feed the recipe title to the prompt.
         */
        String imgPath = NewRecipecontroller.generateImage(header.getTitleText().getText().replace(" ", "%20"));
        imgPath = imgPath.replace(" ", "%20");
        imgPath = imgPath.replace(":", "_");
        desc.setPreviewImagePath(imgPath);  //Updating the displayed image
        
        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);
    }

    public NewRecipeHeader getHeader() {
        return this.header;
    }

    public NewRecipeFooter getFooter() {
        return this.footer;
    }

    public NewRecipeDescription getDesc() {
        return this.desc;
    }

    public RecipeModel getRecipe() {
        return this.recipe;
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

}