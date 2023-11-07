package main.java.MainPage;

import DetailPage.DetailScene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;



public class MainPageRecipeCard extends HBox {

    private Button clickTitle;
    private DetailScene details;


    public MainPageRecipeCard(String title) {
        this.setPrefSize(500, 20); // sets size of Recipe
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of RecipeCard

        clickTitle = new Button(title);
        clickTitle.setPrefSize(500, 20);
        clickTitle.setPrefHeight(Double.MAX_VALUE);
        clickTitle.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button
        this.getChildren().add(clickTitle);

        details = new DetailScene(Main.recipeManager.getRecipe(title));

        clickTitle.setOnAction(e -> {
            //DetailScene details = new DetailScene(recipeManager.getRecipe(title));        //we must create a new detail scene for each recipe that we click on
            //System.out.println("This is the main page");
            Main.sceneManager.ChangeScene(details);
        });
    }

    public Button getTitleDetailsButton() {
        return this.clickTitle;
    }
}