package RecipeMaker;

import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

class SceneManager{
    private Stage mainStage;
    SceneManager(Stage mainStage){
        this.mainStage = mainStage;
    }

    public void ChangeScene(BorderPane destination){
        mainStage.getScene().setRoot(destination);
    }
}