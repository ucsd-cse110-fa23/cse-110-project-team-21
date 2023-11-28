package EditPage;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import DetailPage.DetailView;
import RecipeManager.RecipeModel;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

class EditPageFooter extends HBox{
    private Button saveButton;
    private Button cancelButton;
    
    public EditPageFooter() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 16 arial; -fx-pref-width: 100; -fx-pref-height: 60";

        cancelButton = new Button("Cancel");
        cancelButton.setStyle(defaultButtonStyle);
        saveButton = new Button("Save");
        saveButton.setStyle(defaultButtonStyle);
        this.getChildren().addAll(cancelButton, saveButton);
        cancelButton.setAlignment(Pos.CENTER);
        this.setSpacing(20);
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }
}

class EditPageHeader extends HBox {

    private String title;

    EditPageHeader(String title) {
        this.title = title;
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text(title); // Text of the Header
        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setSpacing(30);
        this.setAlignment(Pos.CENTER);
    }

    public String getTitle() {
        return title;
    }
}



class EditPageEditor extends FlowPane {
    TextArea editableDesc;

    EditPageEditor(RecipeModel recipe) {
        editableDesc = new TextArea(recipe.getDescription());
        editableDesc.setWrapText(isCache());
        editableDesc.setMaxSize(500, 460);
        editableDesc.setPrefSize(500, 460);
        this.getChildren().add(editableDesc);
    }

    public String getEditedText() {
        return editableDesc.getText();
    }
}


public class EditView extends BorderPane{
    private EditPageFooter footer;
    private EditPageHeader header;
    private EditPageEditor editor;
    private EditController editController;
    private RecipeModel recipe;
    private DetailView detailView;

    public EditView(DetailView detailView) {
        this.detailView = detailView;
        RecipeModel currRecipe = detailView.getRecipe();
        footer = new EditPageFooter();
        header = new EditPageHeader(currRecipe.getTitle());
        editor = new EditPageEditor(currRecipe);
        recipe = currRecipe;
        editController = new EditController(this);
        editController.activate();

        this.setTop(header);
        this.setCenter(editor);
        this.setBottom(footer);

    }

    public Button getCancelButton() {
        return footer.getCancelButton();
    }   

    public Button getSaveButton() {
        return footer.getSaveButton();
    }

    public String getEditedText() {
        return editor.getEditedText();
    }

    public RecipeModel getRecipe() {
        return recipe;
    }

    public DetailView getDetailView() {
        return detailView;
    }

}
