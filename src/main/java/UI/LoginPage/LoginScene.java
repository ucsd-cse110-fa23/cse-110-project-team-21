package UI.LoginPage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import UI.DetailPage.DetailScene;
import UI.MainPage.Main;
import UI.MainPage.MainScene;
import UI.NewRecipePage.VInputScene;
import UI.SignUpPage.SignUpScene;
import RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class LoginScene extends BorderPane{
    private TextField usernameField;
    private PasswordField passwordField;
    private CheckBox autoLoginCheckBox;
    private LoginFooter footer = new LoginFooter();
    private LoginHeader header = new LoginHeader();

    public LoginScene()
      {
        System.out.println("LoginScene");

        usernameField = new TextField();
        passwordField = new PasswordField();
        autoLoginCheckBox = new CheckBox("Auto-login");


        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        GridPane formLayout = new GridPane();
        formLayout.setVgap(10);
        formLayout.setHgap(10);
        formLayout.setPadding(new Insets(20, 20, 20, 20));

        // Adding components to the layout
        formLayout.add(usernameLabel, 0, 0);
        formLayout.add(usernameField, 1, 0);
        formLayout.add(passwordLabel, 0, 1);
        formLayout.add(passwordField, 1, 1);
        formLayout.add(autoLoginCheckBox, 0, 4); 

        // Buttons layout

        // Central layout that includes form and buttons
        VBox centralLayout = new VBox(20);
        centralLayout.getChildren().addAll(formLayout);
        centralLayout.setAlignment(Pos.CENTER);

        // Set the central layout in the center of the BorderPane
        this.setPadding(new Insets(20)); // Optional padding for BorderPane

        this.setTop(header);
        this.setCenter(centralLayout);
        this.setBottom(footer);
        addListeners();  
      }

      public void addListeners()
      {
        Button loginButton = footer.getLoginButton();
        Button signupButton = footer.getSignupButton();
        loginButton.setOnAction(e -> {
          MainScene root = new MainScene();
          Main.sceneManager.ChangeScene(root);
        });

        signupButton.setOnAction(e -> {
          SignUpScene signUpScene = new SignUpScene();
          Main.sceneManager.ChangeScene(signUpScene);
        });
      }

}


class LoginHeader extends HBox{
    private Label headerLabel;

    public LoginHeader()
    {
      headerLabel = new Label("Login Up");
      this.getChildren().add(headerLabel);
    }
}

class LoginFooter extends HBox{
    public Object getSignupButton;
    private Button loginButton;
    private Button signupButton;
    public LoginFooter() {
        loginButton = new Button("Login");
        signupButton = new Button("Sign Up");
        this.getChildren().addAll(loginButton, signupButton);
    }

    public Button getLoginButton() {
      return loginButton;
    }

    public Button getSignupButton() {
      return signupButton;
    }
}
