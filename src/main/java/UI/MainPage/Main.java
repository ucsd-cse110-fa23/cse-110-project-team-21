package UI.MainPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.GPTController;
import Controller.WhisperController;
import RecipeLogic.RecipeManager;
import UI.SceneManager;


public class Main extends Application {
    public static SceneManager sceneManager;
    public static MainScene root;
    public static RecipeManager recipeManager;
    public static GPTController gptController;
    public static WhisperController WhisperController;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create a RecipeManager object to hold/manage stored recipe data
        recipeManager = new RecipeManager();

        // Setting the Layout of the Window- Should contain a Header, Footer and the RecipeList
        root = new MainScene();
        sceneManager = new SceneManager(primaryStage);
      
        // Set the title of the app
        primaryStage.setTitle("PantryPal");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 500, 600));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
