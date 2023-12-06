package SignUpPage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
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

public class MockSignUpController {
  private UserModel userModel;
  private ArrayList<UserModel> mockDatabase;
  public MockSignUpController(ArrayList<UserModel> mockDatabase) {
    this.mockDatabase = mockDatabase;
  }
  public MockSignUpController() {
    this.mockDatabase = new ArrayList<UserModel>();
  }

  public String signUpHelper (ActionEvent e, String username, String password, String confirmedPassword, boolean isMocked) throws Exception {
    // check if anything is null
    if(username == null || password == null || confirmedPassword == null || username.equals("") || password.equals("")){
      return "Please fill out all fields";
    }
    // check if passwords match
    if(!password.equals(confirmedPassword)){
      return "Passwords do not match";
    }
    // check if username is taken
    this.userModel = new UserModel(username, password);
    //dbController.setUser(userModel);
    if(isMocked){
      for(UserModel u : mockDatabase){
        if(u.getUsername().equals(username)) return "Username already taken";
      }
    }
    // All good, go and create user.
    mockDatabase.add(this.userModel);
    return "Sign up successful";
    // If autoSave is checked, save the username and password to a file.
  }
}
