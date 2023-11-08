package UI.OpenAIResponsePage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;



public class OpenAIResponsePageFooter extends HBox{
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
