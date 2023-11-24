package UI.SignUpPage;

import UI.LoginPage.LoginScene;
import UI.MainPage.Main;
import UI.MainPage.MainScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;

public class SignUpScene extends BorderPane{
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmedpasswordField;
    private CheckBox autoLoginCheckBox;
    private SignUpFooter footer = new SignUpFooter();
    private SignUpHeader header = new SignUpHeader();

    public SignUpScene()
    {
      System.out.println("SignUpPage");

      usernameField = new TextField();
      passwordField = new PasswordField();
      confirmedpasswordField = new PasswordField();

      autoLoginCheckBox = new CheckBox("Auto-login");

      Label usernameLabel = new Label("Username:");
      Label passwordLabel = new Label("Password:");
      Label confirmedPasswordLabel = new Label("Re-enter Password:");

      GridPane formLayout = new GridPane();
      formLayout.setVgap(10);
      formLayout.setHgap(10);
      formLayout.setPadding(new Insets(20, 20, 20, 20));

      // Adding components to the layout
      formLayout.add(usernameLabel, 0, 0);
      formLayout.add(usernameField, 1, 0);
      formLayout.add(passwordLabel, 0, 1);
      formLayout.add(passwordField, 1, 1);
      formLayout.add(confirmedPasswordLabel, 0, 2);
      formLayout.add(confirmedpasswordField, 1, 2);
      formLayout.add(autoLoginCheckBox, 0, 4);

      // Central layout that includes form
      VBox centralLayout = new VBox(20);
      centralLayout.getChildren().addAll(formLayout);
      centralLayout.setAlignment(Pos.CENTER);
      this.setPadding(new Insets(20));

      this.setTop(header);
      this.setCenter(centralLayout);
      this.setBottom(footer);
      this.addListeners();

    }

    public void addListeners() {
      Button backButton = footer.getBackButton();
      Button signupButton = footer.getSignupButton();
      signupButton.setOnAction(e -> {
        MainScene root = new MainScene();
        Main.sceneManager.ChangeScene(root);
      });
      
      backButton.setOnAction(e -> {
        LoginScene loginScene = new LoginScene();
        Main.sceneManager.ChangeScene(loginScene);
      });
    }
}

class SignUpHeader extends HBox{
    private Label headerLabel;

    public SignUpHeader()
    {
      headerLabel = new Label("Sign Up");
      this.getChildren().add(headerLabel);
    }
}

class SignUpFooter extends HBox{
    private Button signupButton;
    private Button backButton;

    public SignUpFooter()
    {
      signupButton = new Button("Sign Up");
      backButton = new Button("Back");
      this.getChildren().addAll(signupButton, backButton);
    }

    public Button getSignupButton() {
      return signupButton;
    }

    public Button getBackButton() {
      return backButton;
    }
}

