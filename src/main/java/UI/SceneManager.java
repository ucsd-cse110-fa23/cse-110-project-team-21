package UI;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class SceneManager{
    // this class manages switching between scenes in the app

    private Stage mainStage;
    public SceneManager(Stage mainStage){
        this.mainStage = mainStage;
    }

    public void ChangeScene(BorderPane destination){
        mainStage.getScene().setRoot(destination);
    }
}