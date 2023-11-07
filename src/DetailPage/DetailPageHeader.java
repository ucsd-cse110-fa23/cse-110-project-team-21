package DetailPage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import javafx.scene.text.*;


public class DetailPageHeader extends HBox {
    private Button backButton;

    public DetailPageHeader(String title) {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text(title); // Text of the Header
        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setSpacing(30);
        //this.setAlignment(Pos.CENTER); // Align the text to the Center


        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        backButton = new Button("Back"); // text displayed on add button
        backButton.setStyle(defaultButtonStyle); // styling the button

        this.getChildren().addAll(backButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER_RIGHT); // aligning the buttons to center
    }

    public Button getBackButton() {
        return backButton;
    }
}