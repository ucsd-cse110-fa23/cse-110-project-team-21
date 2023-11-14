package UI.NewRecipePage;

import java.io.*;
import java.net.URISyntaxException;
import javax.sound.sampled.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import UI.MainPage.Main;
import Controller.WhisperController;

public class VInputFooter extends VBox {
    // a significant amount of the code in this class is taken or inspired from Lab 5

    // this class specifies the footer UI for the new recipe input page, handles the stop/start/back button behaviors (audio recording),
    // and calls Whisper on the audio inputs


    private Button speakButton;
    private Button stopButton;
    private Button backToMain;
    private boolean isRecording = false;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private Label recordingLabel;
    private VInputCenterScreen centerScreen;
    private WhisperController whisper;
    private File curr;
    public int stepCounter = 0; 

    String defaultButtonStyle = "-fx-border-color: #000000; -fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px;";
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    public VInputFooter(VInputCenterScreen centerScreen) {
        // setup UI for the footer and its buttons
        this.whisper = new WhisperController();
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
        addListener();
    }
    
    public void addListener() {
        speakButton.setOnAction(e -> {
            if(isRecording == false){
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        isRecording = true;
                        startRecording();
                    }
                });
                t.start();
            }
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
            if (isRecording == true) {
                recordingLabel.setVisible(false);
                targetDataLine.stop();
                targetDataLine.close();
            }
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
            curr = audioFile;
            recordingLabel.setVisible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stopRecording() throws IOException, URISyntaxException {
        // stop recording
        recordingLabel.setVisible(false);
        targetDataLine.stop();
        targetDataLine.close();
        centerScreen.setUpdateText();
        stepCounter++;

        // call Whisper on the recorded audio input
        if(stepCounter == 1) { // this block runs if this is the first audio input for the new recipe
            if (whisper.getResult().size()>0) {
                //System.out.println("Caught");
                whisper.getResult().clear();
            }
            //whisper.execute("Mealtype.wav");
            whisper.execute(curr);
            //System.out.println(whisper.getResult().get(0));
        }

        if (stepCounter == 2){ // this block runs if this is the second audio input for the new recipe
            //whisper.execute("Ingredients.wav");
            whisper.execute(curr);
            NewRecipeScene temp = new NewRecipeScene(whisper.getResult());
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