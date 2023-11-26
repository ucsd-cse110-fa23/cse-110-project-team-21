package GPTPage;

import java.io.File;

import java.util.ArrayList;

import RecipeManager.RecipeModel;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class GPTHeader extends HBox {
    public Text titleText;
    public GPTHeader(String titleInput) {
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



class GPTDescription extends FlowPane {
    Text description;
    ImageView previewImage;
    HBox previewImageRegion;

    public GPTDescription(RecipeModel recipe) {
        
        Image img = new Image(new File(recipe.getPreviewImgPath()).toURI().toString());
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
}



class GPTFooter extends HBox{
    private Button saveButton;
    private Button dontSaveButton;
    
    public GPTFooter() {
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


public class GPTView extends BorderPane{
    private GPTHeader header;
    private GPTFooter footer;
    private ScrollPane scrollPane;
    private GPTDescription desc;
    private RecipeModel recipe;
    public GPTController GPTcontroller;
    ArrayList<String> recordingResult;


    // Constructor, Note: the recordingResult is the result from the recording page
    // You must have the recordingResult in order create this page!
    // Calling the OpenAI will be done in the Model
    public GPTView(ArrayList<String> recordingResult) {
        header = new GPTHeader("New Recipe");
        footer = new GPTFooter();
        desc = new GPTDescription(new RecipeModel("Title", "Description"));
        scrollPane = new ScrollPane(desc);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        this.recordingResult = recordingResult;

        // Creating the controller and activating it. Passing the view (this) to help the controller to access.
        GPTcontroller = new GPTController(this);
        GPTcontroller.activate(recordingResult);
        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);
    }

    public GPTHeader getHeader() {
        return this.header;
    }

    public GPTFooter getFooter() {
        return this.footer;
    }

    public GPTDescription getDesc() {
        return this.desc;
    }

    public RecipeModel getRecipe() {
        return this.recipe;
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }
}
