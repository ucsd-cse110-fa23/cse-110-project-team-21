package LoginPage;

import HomePage.HomeView;
import SignUpPage.SignUpView;
import Main.Main;
import javafx.scene.control.Button;


public class LoginController {
  private LoginView loginView;
  private LoginModel loginModel = new LoginModel(this);

  public LoginController(LoginView loginView) {
    this.loginView = loginView;
  }

  public void activate(){
      addListeners();
  }

  public void addListeners()
  {
    Button loginButton = this.loginView.getFooter().getLoginButton();
    Button signupButton = this.loginView.getFooter().getSignupButton();
    loginButton.setOnAction(e -> {
      System.out.println(this.loginView.getUsername());
      System.out.println(this.loginView.getPassword());
      HomeView root = new HomeView();
      Main.sceneManager.ChangeScene(root);
    });

    signupButton.setOnAction(e -> {
      SignUpView signUpScene = new SignUpView();
      Main.sceneManager.ChangeScene(signUpScene);
    });
  }
}
