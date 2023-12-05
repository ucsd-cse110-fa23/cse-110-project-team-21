package SharePage;

import java.util.Optional;

import DetailPage.DetailView;
import EditPage.EditView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import Main.Main;

import DetailPage.DetailView;
import Main.Main;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShareController {

    private ShareView shareView;
    private DetailView detailView; // Reference to the DetailView

    public ShareController(ShareView shareView, DetailView detailView) {
        this.shareView = shareView;
        this.detailView = detailView; // Initialize the reference to DetailView
        addListeners();
    }

    public void addListeners() {
        shareView.getBackButton().setOnAction(event -> {
            // Switch to the ShareView scene
            Main.sceneManager.ChangeScene(detailView);
        });
    }
}