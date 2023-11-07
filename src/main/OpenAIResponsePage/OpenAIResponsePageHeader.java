package main.OpenAIResponsePage;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class OpenAIResponsePageHeader extends HBox {
    public Text titleText;
    public OpenAIResponsePageHeader(String titleInput) {
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
}