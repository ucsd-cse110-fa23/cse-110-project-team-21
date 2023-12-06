package SignUpPage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.security.auth.spi.LoginModule;

import HomePage.HomeView;
import LoginPage.LoginView;
import SignUpPage.SignUpView;
import Main.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import RecipeManager.DBController;
import RecipeManager.DBModel;
import RecipeManager.RecipeManagerModel;
import server.UserModel;

public class SignUpController {
  private SignUpView signUpView;
  private SignUpModel loginModel = new SignUpModel(this);
  private DBModel dbModel = new DBModel();
  private DBController dbController = new DBController(dbModel);
  private UserModel userModel;

  public SignUpController(SignUpView signUpView) {
    this.signUpView = signUpView;
  }

  public void activate(){
      addListeners();
  }

  public void addListeners() {
    Button backButton = this.signUpView.getFooter().getBackButton();
    backButton.setOnAction(e -> {
      LoginView loginScene = new LoginView();
      Main.sceneManager.ChangeScene(loginScene);
    });

    Button signupButton = this.signUpView.getFooter().getSignupButton();
    signupButton.setOnAction(e -> {
      try{
        String username = this.signUpView.getUsername();
        String password = this.signUpView.getPassword();
        String confirmedPassword = this.signUpView.getConfirmedPassword();
        signUpHelper(e, username, password, confirmedPassword, false);

        // System.out.println(this.signUpView.getUsername());
        // System.out.println(this.signUpView.getPassword());
        // System.out.println(this.signUpView.getConfirmedPassword());

        // Log the user in and go to the home page.
        // TODO: Load the home page via MongoDB and save it to Main.java instance: this is for convenience in scene transitions.
      } catch (Exception ex) {
        ex.printStackTrace();
        showAlert("Server Down Error", "Server could not be connected. Please try again later.");
      }
    });
  }

  public void signUpHelper (ActionEvent e, String username, String password, String confirmedPassword, boolean isMocked) throws Exception {
    // check if anything is null
    if(username == null || password == null || confirmedPassword == null || username.equals("") || password.equals("")
        /*|| username.contains("&") || username.contains("|") || username.contains(" ") || username.contains("_")
        || password.contains("&") || password.contains("|") || password.contains(" ") || password.contains("_")*/){
      showAlert("User Creation Error", "Please fill out all fields");
      return;
    }
    // check for illegal characters
    if(username.contains("&") || username.contains("|") || username.contains(" ") || username.contains("_")
        || password.contains("&") || password.contains("|") || password.contains(" ") || password.contains("_")){
      showAlert("User Creation Error", "Illegal Character: Avoid &, |, _, and spaces.");
      return;
    } 

    // check if passwords match
    if(!password.equals(confirmedPassword)){
      showAlert("User Creation Error", "Passwords do not match");
      return;
    }
    // check if username is taken
    userModel = new UserModel(username, password);
    dbController.setUser(userModel);
    if(isMocked){
      if(dbController.handleGetButtonMock(e, 1).equals("Taken")){
        return;
      }
    } else{
      if(dbController.handleGetButton(e, 1).equals("Taken")){
        showAlert("User Creation Error", "Username already taken");
        return;
      }
    }
    // All good, go and create user.
    dbController.handlePostButton(e, -1);
    // If autoSave is checked, save the username and password to a file.
    if(!isMocked){
      boolean autoSave = this.signUpView.getCheckboxStatus();
      if (autoSave) {
        String pathName = "password.txt";
        File outputFile = new File(pathName);
        FileWriter fw;
        try {
          fw = new FileWriter(outputFile, false);
          fw.write(this.signUpView.getUsername() + "\n");
          fw.write(this.signUpView.getPassword() + "\n");
          fw.close();
        } catch (IOException ex) {
          System.out.println("Could not initialize FileWriter with specified output file");
        }
      }
    }
    // In the end, if the function has not been returned by previous catch cases, go to the home page.
    if(!isMocked){
      Main.recipeManager = new RecipeManagerModel(new UserModel(username, password));
      Main.mainView = new HomeView();
      Main.sceneManager.ChangeScene(Main.mainView); 
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
