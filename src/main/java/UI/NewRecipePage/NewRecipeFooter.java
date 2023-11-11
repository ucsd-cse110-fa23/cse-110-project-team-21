package UI.NewRecipePage;

import java.io.*;
import java.net.URISyntaxException;
import javax.sound.sampled.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import UI.MainPage.Main;
import UI.OpenAIResponsePage.OpenAIResponseScene;
import Controller.Whisper;

public class NewRecipeFooter extends VBox {
    private Button speakButton;
    private Button stopButton;
    private Button backToMain;
    private boolean isRecording = false;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private Label recordingLabel;
    private NewRecipeCenterScreen centerScreen;
    private Whisper whisper;
    public int stepCounter = 0; 

    String defaultButtonStyle = "-fx-border-color: #000000; -fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px;";
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    public NewRecipeFooter(NewRecipeCenterScreen centerScreen) {
        this.whisper = new Whisper();
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
                try {
                    stopRecording();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
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

            // add the audio data to the audio file
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

    private void stopRecording() throws IOException, URISyntaxException {
        targetDataLine.stop();
        targetDataLine.close();
        centerScreen.setUpdateText();
        stepCounter++;

        if(stepCounter == 1) {
            whisper.execute("Mealtype.wav");
            System.out.println(whisper.getResult().get(0));
        }

        if (stepCounter == 2){
            whisper.execute("Ingredients.wav");
            OpenAIResponseScene temp = new OpenAIResponseScene(whisper.getResult());
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