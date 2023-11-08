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
    TextField desc;

    EditPageEditor(Recipe recipe) {
        desc = new TextField();
        desc.setText(recipe.getDescription());
        this.getChildren().add(desc);
    }
}
public class EditPage extends BorderPane{
    private EditPageFooter footer;
    private EditPageHeader header;
    private EditPageEditor editor;
    private ScrollPane scrollPane;

    EditPage(Recipe recipe) {
        footer = new EditPageFooter();
        header = new EditPageHeader(recipe.getTitle());
        editor = new EditPageEditor(recipe);

        scrollPane = new ScrollPane(editor);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);
    }
}
