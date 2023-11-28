package WhisperPage;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.*;


class WhisperHeader extends VBox {
    public WhisperHeader() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");
        Text titleText = new Text("Adding New Recipe"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        titleText.setTextAlignment(TextAlignment.CENTER); // Align text to the center
        titleText.setWrappingWidth(400);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
        this.getChildren().add(titleText);
    }
}

class WhisperCenterScreen extends VBox {
    public Text updateText;
    public WhisperCenterScreen() {
        this.setSpacing(5); // sets spacing between Recipes
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
        updateText = new Text("Breakfast/Lunch/Dinner \n\n Please Speak when you are ready");
        updateText.setStyle("-fx-font-size: 16;");
        this.getChildren().add(updateText);
        this.setAlignment(Pos.CENTER);
    }

    // This text should transit from "Breakfast/Lunch/Dinner" to "Ingredients". It's only called once in the controller.
    public void setUpdateText(String text) {
        updateText.setText(text);
    }
}


class WhisperFooter extends VBox {
    private Button speakButton;
    private Button stopButton;
    private Button backButton;
    private Label recordingLabel;

    String defaultButtonStyle = "-fx-border-color: #000000; -fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px;";
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    public WhisperFooter() {
        this.setPrefSize(500, 100);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        speakButton = new Button("Speak");
        speakButton.setStyle(defaultButtonStyle);
        stopButton = new Button("Stop");
        stopButton.setStyle(defaultButtonStyle);
        backButton = new Button("Back to Main");
        backButton.setStyle(defaultButtonStyle);

        // This label should be visible when the user is speaking, managed by the controller.
        recordingLabel = new Label("Recording...");
        recordingLabel.setStyle(defaultLabelStyle);
        recordingLabel.setVisible(false);

        buttonBox.getChildren().addAll(speakButton, stopButton, backButton);
        this.getChildren().addAll(buttonBox, recordingLabel);
        this.setAlignment(Pos.CENTER);
    }
    
    public Button getSpeakButton() {
        return speakButton;
    }
    public Button getStopButton() {
        return stopButton;
    }
    public Button getBackButton() {
        return backButton;
    }
    public Label getRecordingLabel() {
        return recordingLabel;
    }
}


public class WhisperView extends BorderPane {
    private WhisperHeader header;
    private WhisperCenterScreen centerScreen;
    private WhisperFooter footer;
    private WhisperController controller;

    public WhisperView(){
        header = new WhisperHeader();
        centerScreen = new WhisperCenterScreen();
        footer = new WhisperFooter();

        // Init and bind the controller to the view.
        controller = new WhisperController(this);
        controller.activate();
        WhisperView.this.setTop(header);
        WhisperView.this.setCenter(centerScreen);
        WhisperView.this.setBottom(footer);   
    }    

    public WhisperHeader getHeader() {
        return header;
    }
    public WhisperCenterScreen getCenterScreen() {
        return centerScreen;
    }
    public WhisperFooter getFooter() {
        return footer;
    }

    public void showNoServerAlert (){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Sorry :(");
        alert.setHeaderText(null);
        alert.setContentText("Sorry, the Server is not running. Please try again later.");
        alert.showAndWait();
      }
}
