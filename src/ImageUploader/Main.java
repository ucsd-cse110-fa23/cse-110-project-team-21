package ImageUploader;

// Add necessary imports
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.File;

// JavaFX Application main entry point
public class Main extends Application {

    // To display images
    private ImageView imageView = new ImageView();

    // To open a file dialog for selecting images
    private FileChooser fileChooser = new FileChooser();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /*
         * TODO1: Set a title 'Image Uploader' for the stage
         * Hint: You can do this by using the setTitle() method of the stage class
         */


        /*
         * TODO2: Create a button called ‘uploadButton’.The text on the button should say 'Upload Image'.
         * Hint: You can do this by creating an instance of the Button class and passing the text 'Upload Image' as an argument to the constructor.
         */


        // Call uploadImage method on button click
        uploadButton.setOnAction(e -> uploadImage(primaryStage));

        /*
         * TODO3: Create a vertical box layout called ‘vbox’
         * Hint: You can do this by creating an instance of the VBox class and passing uploadButton and imageView as arguments to the constructor.
         */


        /*
         * TODO4: Center align the contents of the vbox.
         * Hint: You can use the setAlignment method of the VBox class and use Pos.CENTER as an argument for center alignment.
         */


        /*
         * TODO5: Create a scene called 'scene' using the vbox. Specify the height as 200 and width as 200.
         * Hint: You can implement this by creating an instance of the Scene class and passing vbox, height and width as arguments to the constructor.
         */

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void uploadImage(Stage primaryStage) {

        // Select which extensions are allowed
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);


        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            /*
             * TODO6: Set the selected image in imageView i.e. display the image.
             * Hint: To implement this, you can use the setImage() method of ImageView and pass the selected image as an argument.
             */

            // Resize the window to fit the image
            primaryStage.setWidth(image.getWidth() + 100);
            primaryStage.setHeight(image.getHeight() + 100);
        }
    }
}
