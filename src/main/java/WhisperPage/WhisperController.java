package WhisperPage;




import java.net.URISyntaxException;
import java.io.*;
import java.util.Optional;
import javax.sound.sampled.*;

import GPTPage.GPTView;
import Main.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class WhisperController {

    WhisperView whispeview;
    WhisperModel whispermodel;


    
    private File curr;
    // *** Number of audio inputs that have been recorded. Step = 0 : recoring meal type, Step = 1 : recording ingredients.
    private int stepCounter = 0;
    // the AudioFormat object that specifies the format in which the audio data should be recorded
    private AudioFormat audioFormat = getAudioFormat();
    private TargetDataLine targetDataLine;

    public WhisperController(WhisperView whisperPageView) {
        this.whispeview = whisperPageView;
        this.whispermodel = new WhisperModel();
    }
    
    // Binding the controller to the view
    public void activate(){
        // Using a boolean[] is for passing the OBJECT into the thread, not the primative type.
        // So when the lamda expression changes the value of the boolean, it will also be reflected here.
        boolean[] isRecording = {false};

        // BACK button logic
        this.whispeview.getFooter().getBackButton().setOnAction(e -> {
            Main.sceneManager.ChangeScene(Main.mainView);
        });

        // SPEAK button logic
        this.whispeview.getFooter().getSpeakButton().setOnAction(e -> {
            if(isRecording[0] == false){
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        whispeview.getFooter().getRecordingLabel().setVisible(true);
                        isRecording[0] = true;
                        startRecording();
                    }
                });
                t.start();
            }
        });

        // STOP button logic
        this.whispeview.getFooter().getStopButton().setOnAction(e -> {
            if(isRecording[0] == true){
                isRecording[0] = false;
                try {
                    stopRecording();
                    this.whispeview.getFooter().getRecordingLabel().setVisible(false);
                    this.whispeview.getCenterScreen().setUpdateText("Enter Ingredients:");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void startRecording() {
        try {
            // the format of the TargetDataLine
            DataLine.Info dataLineInfo = new DataLine.Info(
                    TargetDataLine.class,
                    audioFormat);
            // the TargetDataLine used to capture audio data from the microphone
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

     private void stopRecording() throws IOException, URISyntaxException {
        // stop recording
        targetDataLine.stop();
        targetDataLine.close();

        stepCounter++;

        // call Whisper on the recorded audio input
        if(stepCounter == 1) { // this block runs if this is the first audio input for the new recipe
            if (whispermodel.getResult().size()>0) {
                //System.out.println("Caught");
                whispermodel.getResult().clear();
            }
            //whisper.execute("Mealtype.wav");
            whispermodel.execute(curr);
            //System.out.println(whisper.getResult().get(0));
            System.out.println(whispermodel.getResult().get(0));
            //String mealType = formatMealType("BREAKFAST!");
            String mealType = formatMealType(whispermodel.getResult().get(0));
            if (mealType!=null) {
                whispermodel.getResult().set(0, mealType);
            } else{
                invalidInput();
            }
        }

        if (stepCounter == 2){ // this block runs if this is the second audio input for the new recipe
            //whisper.execute("Ingredients.wav");
            whispermodel.execute(curr);
            //String ingredients = formatIngredients("oatmeal");
            String ingredients = formatIngredients(whispermodel.getResult().get(1));
            if (ingredients!=null) {
                whispermodel.getResult().set(1, ingredients);
                System.out.println("Ingredients: " + whispermodel.getResult().get(1));
                GPTView GPTview= new GPTView(whispermodel.getResult());
                Main.sceneManager.ChangeScene(GPTview);
            } else{
                invalidInput();
            }
        }
    }

    // Helper functions for formatting the audio input. Filer meal type to only Breakfast, Lunch, Dinner.
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

    // Filter invalid ingredients input
    private String formatIngredients(String raw) {
        raw = raw.toLowerCase();
        if (raw == null || raw.length()<3 || raw.toLowerCase().contains("you")) {
            return null;
        } else {
            return raw;
        }
    }

    // Filter invalid ingredients input
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
            Main.sceneManager.ChangeScene(Main.mainView);
        } else if (result.get() == buttonRetry) {
            // create a new scene for adding a new Recipe
            WhisperView whisperPageView = new WhisperView();
            Main.sceneManager.ChangeScene(whisperPageView);
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