package SignUpPage;
import javax.security.auth.spi.LoginModule;

import HomePage.HomeView;
import LoginPage.LoginView;
import SignUpPage.SignUpView;
import Main.Main;
import javafx.scene.control.Button;

public class SignUpController {
  private SignUpView signUpView;
  private SignUpModel loginModel = new SignUpModel(this);

  public SignUpController(SignUpView signUpView) {
    this.signUpView = signUpView;
  }

  public void activate(){
      addListeners();
  }

  public void addListeners() {
    Button backButton = this.signUpView.getFooter().getBackButton();
    Button signupButton = this.signUpView.getFooter().getSignupButton();
    signupButton.setOnAction(e -> {
      System.out.println(this.signUpView.getUsername());
      System.out.println(this.signUpView.getPassword());
      System.out.println(this.signUpView.getConfirmedPassword());
      HomeView root = new HomeView();
      Main.sceneManager.ChangeScene(root);
    });
    
    backButton.setOnAction(e -> {
      LoginView loginScene = new LoginView();
      Main.sceneManager.ChangeScene(loginScene);
    });
  }
}
