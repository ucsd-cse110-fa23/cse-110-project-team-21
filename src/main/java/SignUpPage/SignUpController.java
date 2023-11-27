package SignUpPage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.security.auth.spi.LoginModule;

import HomePage.HomeView;
import LoginPage.LoginView;
import SignUpPage.SignUpView;
import Main.Main;
import Server.DBController;
import Server.DBModel;
import Server.UserModel;
import javafx.scene.control.Button;

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
      // check if anything is null
      if(this.signUpView.getUsername().equals("") || this.signUpView.getPassword().equals("") || this.signUpView.getConfirmedPassword().equals("")){
        this.signUpView.showAlert("User Creation Error", "Please fill out all fields");
        return;
      }
      // check if passwords match
      if(!this.signUpView.getPassword().equals(this.signUpView.getConfirmedPassword())){
        this.signUpView.showAlert("User Creation Error", "Passwords do not match");
        return;
      }
      // check if username is taken
      userModel = new UserModel(this.signUpView.getUsername(), this.signUpView.getPassword());
      dbController.setUser(userModel);
      if(dbController.handleGetButton(e, 1).equals("Taken")){
        this.signUpView.showAlert("User Creation Error", "Username already taken");
        return;
      }

      // All good, go and create user.
      dbController.handlePostButton(e, -1);

      // If autoSave is checked, save the username and password to a file.
      boolean autoSave = this.signUpView.getCheckboxStatus();
      if (autoSave) {
        System.out.println("Saving username and password to file");
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

      // System.out.println(this.signUpView.getUsername());
      // System.out.println(this.signUpView.getPassword());
      // System.out.println(this.signUpView.getConfirmedPassword());


      // Log the user in and go to the home page.

      // TODO: Load the home page via MongoDB and save it to Main.java instance: this is for convenience in scene transitions.
      HomeView root = new HomeView();
      Main.sceneManager.ChangeScene(root);
    });
  }
}
