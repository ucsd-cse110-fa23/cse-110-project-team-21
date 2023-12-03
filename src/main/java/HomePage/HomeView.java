package HomePage;

import Main.Main;
import LoginPage.LoginView;
import SignUpPage.SignUpView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class MainPageHeader extends HBox {
    private Button addRecipeButton;
    private ComboBox<String> sortDropDown;
    private ComboBox<String> filterDropDown;

    public MainPageHeader() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");
        
        // Left HBox
        VBox leftBox = new VBox();
        leftBox.setAlignment(Pos.CENTER_RIGHT);
        leftBox.setSpacing(10); // Adjust as needed

        Text titleText = new Text("Recipe Maker"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        
        addRecipeButton = new Button("New Recipe");
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";
        addRecipeButton.setStyle(defaultButtonStyle);

        HBox.setHgrow(leftBox, Priority.ALWAYS);
        addRecipeButton.setAlignment(Pos.CENTER_LEFT);
        leftBox.getChildren().addAll(titleText, addRecipeButton);

        // Right VBox
        VBox rightBox = new VBox();
        // set rightbox to the further right of the header
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setSpacing(5); // Adjust as needed

        ObservableList<String> sortOptions = FXCollections.observableArrayList(
                "A-Z",
                "Z-A",
                "New-Old",
                "Old-New"
        );

        ObservableList<String> filterOptions = FXCollections.observableArrayList(
                "Breakfast",
                "Lunch",
                "Dinner",
                "AllFilter"
        );

        // Create ComboBoxes and set the items
        sortDropDown = new ComboBox<>(sortOptions);
        filterDropDown = new ComboBox<>(filterOptions);
        sortDropDown.setValue("Sort");
        filterDropDown.setValue("Filter");
        rightBox.getChildren().addAll(sortDropDown, filterDropDown);

        // Add both HBox and VBox to the MainPageHeader
        this.setSpacing(100);
        this.getChildren().addAll(leftBox, rightBox);
        this.setAlignment(Pos.CENTER_RIGHT);
    }

    public Button getAddRecipeButton() {
        return addRecipeButton;
    }

    public ComboBox<String> getSortDropDown() {
        return sortDropDown;
    }

    public ComboBox<String> getFilterDropDown() {
        return filterDropDown;
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
    private Text label;

    public MainPageRecipeCard(String title, String label) {
        this.setPrefSize(500, 40); // Adjusted the height for better visibility
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;");

        clickTitle = new Button(title);
        clickTitle.setPrefSize(300, 20); // Adjusted the width for better visibility
        clickTitle.setPrefHeight(Double.MAX_VALUE);
        clickTitle.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");

        this.label = new Text(label);
        this.label.setStyle("-fx-font-weight: bold;");

        this.getChildren().addAll(this.label, clickTitle);
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

    public MainPageHeader getHeader() {
        return header;
    }

}