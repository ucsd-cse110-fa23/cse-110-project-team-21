package LoginPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import Main.Main;

public class LoginView extends BorderPane {
    private TextField usernameField;
    private PasswordField passwordField;
    private CheckBox autoLoginCheckBox;
    private LoginFooter footer = new LoginFooter();
    private LoginHeader header = new LoginHeader();

    public LoginView()
    {
        LoginController loginController = new LoginController(this);
        loginController.activate();

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
        formLayout.add(autoLoginCheckBox, 0, 3);

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

        // Boolean isServer = false;
        // while(!isServer) {
        //   try{
        //     URL url = new URI(Main.HOSTNAME_URL).toURL();
        //     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //     conn.getResponseCode();
        //   }
        //   catch (Exception ex) {
        //     System.out.println("Shit went down");
        //     //loginController.showAlert("Login Error", "Please fill out all fields.");
        //     loginController.showAlert("Server Down Error", "Server could not be connected. Please try again later.");
        //   }
        // }
    }

    public LoginFooter getFooter() {
      return this.footer;
    }

    public LoginHeader getHeader() {
      return this.header;
    }

    public String getUsername() {
      return usernameField.getText();
    }

    public String getPassword() {
      return passwordField.getText();
    }

    public boolean getCheckboxStatus() {
      return autoLoginCheckBox.isSelected();
    }

} 

class LoginHeader extends HBox{
  private Label headerLabel;

  public LoginHeader()
  {
  headerLabel = new Label("Login");
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
