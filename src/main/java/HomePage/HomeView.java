package HomePage;

import Main.Main;
import LoginPage.LoginView;
import SignUpPage.SignUpView;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;

class MainPageHeader extends VBox {
    private Button addRecipeButton;

    public MainPageHeader() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");
        Text titleText = new Text("Recipe Maker"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";
        addRecipeButton = new Button("New Recipe"); 
        addRecipeButton.setStyle(defaultButtonStyle); 

        this.getChildren().addAll(addRecipeButton); 
        this.setAlignment(Pos.CENTER);
    }

    public Button getAddRecipeButton() {
        return addRecipeButton;
    }
}


class MainPageRecipeList extends VBox {
    // Will contail a list of RecipeCards as children. Will be updated via the controller's function
    MainPageRecipeList() {
        this.setSpacing(5); 
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }
}

class MainPageRecipeCard extends HBox {
    // ClickTitle a button with the title of the recipe as the text. Each RecipeCard will be a child of RecipeList.
    private Button clickTitle;

    public MainPageRecipeCard(String title) {
        this.setPrefSize(500, 20); // sets size of Recipe
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of RecipeCard

        clickTitle = new Button(title);
        clickTitle.setPrefSize(500, 20);
        clickTitle.setPrefHeight(Double.MAX_VALUE);
        clickTitle.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button
        this.getChildren().add(clickTitle);
    }

    public Button getDetailButton() {
        return this.clickTitle;
    }

    public String getTitle() {
        return this.clickTitle.getText();
    }
}

class MainPageFooter extends HBox {
    private Button loginButton;
    private Button signUpButton;

    public MainPageFooter() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
    
        loginButton = new Button("Mock Login");
        loginButton.setPrefSize(500, 20);
        loginButton.setPrefHeight(Double.MAX_VALUE);
        loginButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        signUpButton = new Button("Mock Sign Up");
        signUpButton.setPrefSize(500, 20);
        signUpButton.setPrefHeight(Double.MAX_VALUE);
        signUpButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        loginButton.setOnAction(e -> {
            LoginView loginView = new LoginView();
            Main.sceneManager.ChangeScene(loginView);
        });

        signUpButton.setOnAction(e -> {
            SignUpView signUpView = new SignUpView();
            Main.sceneManager.ChangeScene(signUpView);
        });

        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
        // Comment this to add the login and sign up buttons to the footer
        this.getChildren().addAll(loginButton, signUpButton);
    }
}

public class HomeView extends BorderPane{
    private MainPageHeader header;
    private MainPageFooter footer;
    private ScrollPane scrollPane;
    public MainPageRecipeList recipeList;
    public HomeController homecontroller;

    public HomeView(){
        header = new MainPageHeader();
        footer = new MainPageFooter();
        recipeList = new MainPageRecipeList();
        scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToWidth(isCache());
        scrollPane.setFitToHeight(isCache());

        // Create a HomeController object to manage the home page
        // The controller will be responsible for updating the this homeView
        // when a new recipe is added, edited, or deleted, using the data from the homeModel
        homecontroller = new HomeController(this);
        homecontroller.activate();

        this.setTop(header);
        this.setCenter(recipeList);
        this.setBottom(footer);
    }

    public Button getAddButton() {
        return this.header.getAddRecipeButton();
    }

}
