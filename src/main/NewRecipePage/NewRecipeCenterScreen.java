package main.NewRecipePage;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class NewRecipeCenterScreen extends VBox {
    public Text updateText;

    public NewRecipeCenterScreen() {
        this.setSpacing(5); // sets spacing between Recipes
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");

        updateText = new Text("Breakfast/Lunch/Dinner \n\n Please Speak when you are ready");
        updateText.setStyle("-fx-font-size: 16;");
        this.getChildren().add(updateText);
        this.setAlignment(Pos.CENTER);

    }

    public void setUpdateText() {
        updateText.setText("Enter Ingredients: ");
    }

}
