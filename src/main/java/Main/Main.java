package Main;

import GPTPage.GPTController;
import GPTPage.GPTModel;
import RecipeManager.RecipeManagerModel;
import SceneController.SceneController;
import HomePage.HomeView;
import LoginPage.LoginView;
import WhisperPage.WhisperController;
import WhisperPage.WhisperModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class Main extends Application {
    // Save two pages as static variables
    public static HomeView mainView;
    public static LoginView loginView;


    public static SceneController sceneManager;
    public static RecipeManagerModel recipeManager;
    public static GPTController gptController;
    public static WhisperController WhisperController;
    public static GPTModel  gptModel;
    public static WhisperModel whisperModel;


    @Override
    public void start(Stage primaryStage) throws Exception {

        //  Create a RecipeManager object to hold/manage stored recipe data
        recipeManager = new RecipeManagerModel();

        //  Setting the Layout of the Window- Should contain a Header, Footer and the RecipeList
        mainView = new HomeView();
        loginView = new LoginView();
        sceneManager = new SceneController(primaryStage);
      
        // primaryStage, which is the ONLY entry window of the application
        // The main window will contain the mainView, which is the home page
        primaryStage.setTitle("PantryPal");
        primaryStage.setScene(new Scene(mainView, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
