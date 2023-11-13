package UI.MainPage;
import javafx.scene.layout.*;



public class MainPageRecipeList extends VBox {
    // this class specifies that the main page's recipe list is a VBox

    MainPageRecipeList() {
        this.setSpacing(5); // sets spacing between Recipes
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }
}