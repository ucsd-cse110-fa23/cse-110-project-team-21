package UI.NewRecipePage;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.sound.sampled.*;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import UI.MainPage.Main;
import UI.OpenAIResponsePage.OpenAIResponseScene;
import Controller.Whisper;

public class NewRecipeFooter extends VBox {
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
    private NewRecipeCenterScreen centerScreen;
    private Whisper whisper;
    private File curr;
    public int stepCounter = 0; 

    String defaultButtonStyle = "-fx-border-color: #000000; -fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px;";
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    public NewRecipeFooter(NewRecipeCenterScreen centerScreen) {
        // setup UI for the footer and its buttons
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
            System.out.println(whisper.getResult().get(0));
            //String mealType = formatMealType("BREAKFAST!");
            String mealType = formatMealType(whisper.getResult().get(0));
            if (mealType!=null) {
                whisper.getResult().set(0, mealType);
            } else{
                invalidInput();
            }
            //System.out.println(whisper.getResult().get(0));
        }

        if (stepCounter == 2){ // this block runs if this is the second audio input for the new recipe
            //whisper.execute("Ingredients.wav");
            whisper.execute(curr);
            //String ingredients = formatIngredients("oatmeal");
            String ingredients = formatIngredients(whisper.getResult().get(1));
            if (ingredients!=null) {
                whisper.getResult().set(1, ingredients);
                OpenAIResponseScene temp = new OpenAIResponseScene(whisper.getResult());
                Main.sceneManager.ChangeScene(temp);
            } else{
                invalidInput();
            }
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
    
    private String formatMealType(String raw) {
        raw = raw.toLowerCase();
        if (raw == null || raw.length() > 11 || raw.length()<5) {
            return null;
        }
        char f = (char) (raw.charAt(0)-32);
        raw = "" + f + raw.substring(1, raw.length());
        //System.out.println(raw);
          if (raw.length()>=9 && raw.substring(0,9).equals("Breakfast")) {
            return "Breakfast";
        } else if (raw.substring(0,5).equals("Lunch")) {
            return "Lunch";
        } else if (raw.length()>=6 && raw.substring(0,6).equals("Dinner")) {
            return "Dinner";
        } else {
            return null;
        }
    }

    private String formatIngredients(String raw) {
        raw = raw.toLowerCase();
        if (raw == null || raw.length()<3 || raw.toLowerCase().contains("you")) {
            return null;
        } else {
            return raw;
        }
    }

    private void invalidInput() {
        // create an alert that allows user to confirm deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error: Invalid Input!");
            alert.setHeaderText("Error: Invalid Input!");
            alert.setContentText("Your voice input was invalid. " + 
            "Click Retry to start over voice input or click Back to go back to the main screen.");

            ButtonType buttonRetry = new ButtonType("Retry");
            ButtonType buttonBack = new ButtonType("Back");

            alert.getButtonTypes().setAll(buttonRetry, buttonBack);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonBack){
                Main.sceneManager.ChangeScene(Main.root);
            } else if (result.get() == buttonRetry) {
                // create a new scene for adding a new Recipe
                NewRecipeScene newRecipeScene = new NewRecipeScene();
                Main.sceneManager.ChangeScene(newRecipeScene);
            }
    }

}