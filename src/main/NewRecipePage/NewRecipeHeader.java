
package main.NewRecipePage;


import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class NewRecipeHeader extends VBox {

    public NewRecipeHeader() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");
        Text titleText = new Text("Adding New Recipe"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        titleText.setTextAlignment(TextAlignment.CENTER); // Align text to the center
        titleText.setWrappingWidth(400);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
        this.getChildren().add(titleText);
    }
}