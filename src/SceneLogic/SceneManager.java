package SceneLogic;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class SceneManager{
    private Stage mainStage;
    public SceneManager(Stage mainStage){
        this.mainStage = mainStage;
    }

    public void ChangeScene(BorderPane destination){
        mainStage.getScene().setRoot(destination);
    }
}