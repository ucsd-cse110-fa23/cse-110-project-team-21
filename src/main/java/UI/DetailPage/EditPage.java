package UI.DetailPage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import UI.MainPage.Main;
import RecipeLogic.Recipe;
import javafx.scene.control.*;

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
        //editButton.setAlignment(Pos.CENTER_RIGHT);
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }
}

class EditPageHeader extends HBox {

    EditPageHeader(String title) {
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
}

class EditPageEditor extends FlowPane {
    TextArea desc;

    EditPageEditor(Recipe recipe) {
        //this.setPrefSize(500, 500);

        desc = new TextArea(recipe.getDescription());
        desc.setWrapText(isCache());
        desc.setMaxSize(500, 460);
        desc.setPrefSize(500, 460);
        this.getChildren().add(desc);
    }
}
public class EditPage extends BorderPane{
    private EditPageFooter footer;
    private EditPageHeader header;
    private EditPageEditor editor;
    private DetailScene scene;
    //private ScrollPane scrollPane;
    private Button cancelButton;
    private Button saveButton;

    EditPage(DetailScene detailScene, Recipe recipe) {
        footer = new EditPageFooter();
        header = new EditPageHeader(recipe.getTitle());
        editor = new EditPageEditor(recipe);

        // scrollPane = new ScrollPane(editor);
        // scrollPane.setFitToWidth(true);
        // scrollPane.setFitToHeight(true);

        this.setTop(header);
        this.setCenter(editor);
        this.setBottom(footer);

        scene = detailScene;

        cancelButton = footer.getCancelButton();
        saveButton = footer.getSaveButton();

        addListeners();
    }

    private void addListeners() {
        cancelButton.setOnAction(e -> {
            Main.sceneManager.ChangeScene(scene);
        });

        saveButton.setOnAction(e -> {
            System.out.println("TODO: backend for save editted recipe");
            Main.sceneManager.ChangeScene(scene);
        });
    }
}
