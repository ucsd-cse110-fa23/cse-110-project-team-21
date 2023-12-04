package Main;

import RecipeManager.RecipeManagerModel;
import SceneController.SceneController;
import DetailPage.DallEModel;
import HomePage.HomeView;
import LoginPage.LoginView;
import NewRecipePage.NewRecipeController;
import NewRecipePage.GPTModel;
import WhisperPage.WhisperController;
import WhisperPage.WhisperModel;
import RecipeManager.DBModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URI;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.ConnectException;




public class Main extends Application {
    public static SceneController sceneManager;
    public static RecipeManagerModel recipeManager;
    public static NewRecipeController gptController;
    public static WhisperController WhisperController;
    public static GPTModel  gptModel;
    public static DallEModel dallEModel;
    public static WhisperModel whisperModel;
    public static DBModel dbModel;

    public static HomeView mainView;
    public static LoginView loginView;

    public static final String HOSTNAME_URL = "http://localhost:8100";

    @Override
    public void start(Stage primaryStage) throws Exception {
        dallEModel = new DallEModel();
        //  Create a RecipeManager object to hold/manage stored recipe data
        // Note: the ReciperManagerModel now takes in a UserModel and is initialized upon login (as is mainView)
        //recipeManager = new RecipeManagerModel(); 

        //  Setting the Layout of the Window- Should contain a Header, Footer and the RecipeList
        LoginView loginView = new LoginView();
        sceneManager = new SceneController(primaryStage);
        dbModel = new DBModel();
      
        // primaryStage, which is the ONLY main window of the application
        // The main window will contain the mainView, which is the home page
        // TO DO: If doing authentication, the main window should be the login page instead.
        primaryStage.setTitle("PantryPal");
        primaryStage.setScene(new Scene(loginView, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

        try {
            URL url = new URI(HOSTNAME_URL).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int code = conn.getResponseCode();
            System.out.println("Response code" + code);
        }
        catch (ConnectException ex) {
            //ex.printStackTrace();
            //System.out.println("Error: " + ex.getMessage());
            System.out.println("Well this was fun");
            
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
