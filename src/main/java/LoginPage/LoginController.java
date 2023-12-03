package LoginPage;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import HomePage.HomeView;
import SignUpPage.SignUpView;
import Main.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import RecipeManager.DBController;
import RecipeManager.DBModel;
import RecipeManager.RecipeManagerModel;
import server.UserModel;



public class LoginController {
  private LoginView loginView;
  private LoginModel loginModel = new LoginModel(this);
  private DBModel dbModel = new DBModel();
  private DBController dbController = new DBController(dbModel);
  private UserModel userModel;

  public LoginController(LoginView loginView) {
    String username;
    String password;
    this.loginView = loginView;
    // In the constructor (when the controller is created), we check is there is a password.txt
    // If there is, do the auto-login automatically for the user.
    String filePath = "password.txt";
    try {
      Path path = (Path) Paths.get(filePath);
      if (!Files.exists(path)) {
        return;
      } 
      List<String> lines = Files.readAllLines(path);
      if (!lines.isEmpty() && lines.size() == 2) {
          username = lines.get(0);
          password = lines.get(1);
          ActionEvent e = null;
          // System.out.println("Auto-login with username: " + username + " and password: " + password);
          Platform.runLater(() -> {
              login(e, username, password, true, false);
          });
      }
    }
    catch (IOException e) {
      e.printStackTrace();
      showAlert("Server Down Error", "Server could not be connected. Please try again later.");
    }
  }

  public void activate(){
      addListeners();
  }

  public void addListeners() {

    Button signupButton = this.loginView.getFooter().getSignupButton();
    signupButton.setOnAction(e -> {
      SignUpView signUpScene = new SignUpView();
      Main.sceneManager.ChangeScene(signUpScene);
    });

    Button loginButton = this.loginView.getFooter().getLoginButton();
    loginButton.setOnAction(e -> {
      String username = this.loginView.getUsername();
      String password = this.loginView.getPassword();
      login(e, username, password, false, false);
    });
  }

  public void login (ActionEvent e, String username, String password, boolean autoLogin, boolean isMocked) {
      try {
        // check if null
        if(username.equals("") || password.equals("")){
          if (autoLogin) {
            showAlert("Login Error", "Your autosaved password is contaminated. Please try manual input or sign up.");
          }else{
            showAlert("Login Error", "Please fill out all fields.");
          }
          return;
        }

        // check if the username exists
        userModel = new UserModel(username, password);
        dbController.setUser(userModel);
        String response = "";
        if (isMocked){
          response = dbController.handleGetButtonMock(e, 1);
        }else{
          response = dbController.handleGetButton(e, 1);
        }
        if(response.equals("Available")){
          if(isMocked){
            throw new Exception("Username not found. Please try again.");
          }
          if(autoLogin){
            showAlert("Login Error", "Your autosaved username is wrong. Please try manual input or sign up.");
          }else{
            showAlert("Login Error", "Username not found. Please try again.");
          }
          return;
        }

        // Check is the password is correct
        if(isMocked){
          response = dbController.handleGetButtonMock(e, 2);
        }else{
          response = dbController.handleGetButton(e, 2);
        }
        if(response.equals("Incorrect")){
          if(isMocked){
            throw new Exception("Incorrect password. Please try again.");
          }
          if(autoLogin){
            showAlert("Login Error", "Your autosaved password is wrong. Please try manual input or sign up.");
          }else{
            showAlert("Login Error", "Incorrect password. Please try again.");
          }
          return;
        }
        // System.out.println(username);
        // System.out.println(password);

        // Log the user in and go to the home page.
        // TODO: Load the home page via MongoDB and save it to Main.java instance: this is for convenience in scene transitions.
        if(!isMocked){
          Main.recipeManager = new RecipeManagerModel(new UserModel(username, password));
          Main.mainView = new HomeView();
          Main.sceneManager.ChangeScene(Main.mainView);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
        showAlert("Server Down Error", "Server could not be connected. Please try again later.");
      }
  }

  public void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
