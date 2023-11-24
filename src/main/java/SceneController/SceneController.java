package SceneController;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class SceneController{

    // This is the class for managing the scenes of the application
    // mainStage is always the main window of the application, the home page
    // And then transition to other pages happens, ChangeScene() is called.
    private Stage mainStage;
    public SceneController(Stage mainStage){
        this.mainStage = mainStage;
    }

    public void ChangeScene(BorderPane destination){
        mainStage.getScene().setRoot(destination);
    }
}