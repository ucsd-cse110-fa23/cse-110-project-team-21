package UI.MainPage;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class MainPageHeader extends VBox {
    private Button addRecipeButton;
    //private Button detailButton;
    private Button mockAddButton;

    public MainPageHeader() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Recipe Maker"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addRecipeButton = new Button("New Recipe"); // text displayed on add button
        addRecipeButton.setStyle(defaultButtonStyle); // styling the button
        /*detailButton = new Button("Details Page"); // text displayed on add button
        detailButton.setStyle(defaultButtonStyle); // styling the button */
        mockAddButton = new Button("Mock Add"); // text displayed on add button
        mockAddButton.setStyle(defaultButtonStyle); // styling the button

        this.getChildren().addAll(addRecipeButton, /*detailButton,*/ mockAddButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getAddRecipeButton() {
        return addRecipeButton;
    }
    /*public Button getDetailButton() {
        return detailButton;
    } */
    public Button getMockAddButton() {
        return mockAddButton;
    }
}