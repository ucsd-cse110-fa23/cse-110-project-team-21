package Main;

import GPTPage.GPTController;
import GPTPage.GPTModel;
import RecipeManager.RecipeManagerModel;
import SceneController.SceneController;
import HomePage.HomeView;
import WhisperPage.WhisperController;
import WhisperPage.WhisperModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class Main extends Application {
    public static SceneController sceneManager;
    public static HomeView mainView;
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
        sceneManager = new SceneController(primaryStage);
      
        // primaryStage, which is the ONLY main window of the application
        // The main window will contain the mainView, which is the home page
        // TO DO: If doing authentication, the main window should be the login page instead.
        primaryStage.setTitle("PantryPal");
        primaryStage.setScene(new Scene(mainView, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
