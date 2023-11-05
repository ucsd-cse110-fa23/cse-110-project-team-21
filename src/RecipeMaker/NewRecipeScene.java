package RecipeMaker;

import java.io.*;
import javax.sound.sampled.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.*;

class NewRecipeHeader extends VBox {

    NewRecipeHeader() {
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

class NewRecipeFooter extends VBox {
    private Button speakButton;
    private Button stopButton;
    private Button backToMain;
    private boolean isRecording = false;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private Label recordingLabel;
    private NewRecipeCenterScreen centerScreen;
    public int stepCounter = 0; 

    String defaultButtonStyle = "-fx-border-color: #000000; -fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px;";
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    NewRecipeFooter(NewRecipeCenterScreen centerScreen) {
        this.centerScreen = centerScreen;
        this.setPrefSize(500, 100);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        speakButton = new Button("Speak");
        speakButton.setStyle(defaultButtonStyle);

        stopButton = new Button("Stop");
        stopButton.setStyle(defaultButtonStyle);

        backToMain = new Button("Back to Main");
        backToMain.setStyle(defaultButtonStyle);

        recordingLabel = new Label("Recording...");
        recordingLabel.setStyle(defaultLabelStyle);
        recordingLabel.setVisible(false);

        buttonBox.getChildren().addAll(speakButton, stopButton, backToMain);
        this.getChildren().addAll(buttonBox, recordingLabel);
        this.setAlignment(Pos.CENTER);
        
        audioFormat = getAudioFormat();
        addListner();
    }
    
    public void addListner() {
        speakButton.setOnAction(e -> {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    isRecording = true;
                    startRecording();
                }
            });
            t.start();
        });

        // Stop Button
        stopButton.setOnAction(e -> {
            if(isRecording == true){
                isRecording = false;
                stopRecording();
            }
        });

        // Back Button
        backToMain.setOnAction(e -> {
            Main.sceneManager.ChangeScene(Main.root);
        });
    }
    
    private void startRecording() {
        try {
            // the format of the TargetDataLine
            DataLine.Info dataLineInfo = new DataLine.Info(
                    TargetDataLine.class,
                    audioFormat);
            // the TargetDataLine used to capture audio data from the microphone
            recordingLabel.setVisible(true);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            // the AudioInputStream that will be used to write the audio data to a file
            AudioInputStream audioInputStream = new AudioInputStream(
                    targetDataLine);

            // the file that will contain the audio data
            File audioFile;
            if(stepCounter ==  0){
                audioFile = new File("Mealtype.wav");
            }else{
                audioFile = new File("Ingredients.wav");
            }
            AudioSystem.write(
                    audioInputStream,
                    AudioFileFormat.Type.WAVE,
                    audioFile);
            recordingLabel.setVisible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
        centerScreen.setUpdateText();
        stepCounter++;

        if (stepCounter == 2){
            OpenAIResponseScene temp = new OpenAIResponseScene();
            Main.sceneManager.ChangeScene(temp); 
        }
    }

    private AudioFormat getAudioFormat() {
        // the number of samples of audio per second.
        // 44100 represents the typical sample rate for CD-quality audio.
        float sampleRate = 44100;

        // the number of bits in each sample of a sound that has been digitized.
        int sampleSizeInBits = 16;

        // the number of audio channels in this format (1 for mono, 2 for stereo).
        int channels = 1;

        // whether the data is signed or unsigned.
        boolean signed = true;

        // whether the audio data is stored in big-endian or little-endian order.
        boolean bigEndian = false;

        return new AudioFormat(
                sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }    

}

class NewRecipeCenterScreen extends VBox {
    public Text updateText;

    NewRecipeCenterScreen() {
        this.setSpacing(5); // sets spacing between Recipes
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");

        updateText = new Text("Breakfast/Lunch/Dinner \n\n Please Speak when you are ready");
        updateText.setStyle("-fx-font-size: 16;");
        this.getChildren().add(updateText);
        this.setAlignment(Pos.CENTER);

    }

    public void setUpdateText() {
        updateText.setText("Enter Ingredients: ");
    }

}

public class NewRecipeScene extends BorderPane {
    private NewRecipeHeader header;
    private NewRecipeCenterScreen centerScreen;
    private NewRecipeFooter footer;
    // private ScrollPane scrollPane;

    NewRecipeScene(){
        // Initialise the header Object
        header = new NewRecipeHeader();
        // Initialise the CenterScreen Object
        centerScreen = new NewRecipeCenterScreen();
        // Initialise the Footer Object
        footer = new NewRecipeFooter(centerScreen);

        // scrollPane = new ScrollPane(centerScreen);
        // scrollPane.setFitToWidth(isCache());
        // scrollPane.setFitToHeight(isCache());
        
        // Add header to the top of the BorderPane
        NewRecipeScene.this.setTop(header);
        NewRecipeScene.this.setCenter(centerScreen);
        NewRecipeScene.this.setBottom(footer);
        
        
    }


    
}
