package UI.NewRecipePage;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class NewRecipeCenterScreen extends VBox {
    // this class specifies the center (text) UI for both steps of the new recipe input page 

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
        // switch to the second prompt after the first input has been accepted
        updateText.setText("Enter Ingredients: ");
    }

}
