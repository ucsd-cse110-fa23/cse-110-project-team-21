package RecipeMaker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import java.io.File;

public class DetailScene extends BorderPane{

    private Header header;
    private Footer footer;
    private Button backButton;

    DetailScene() {
        Header header = new Header();
        Footer footer = new Footer();

        this.setTop(header);
        this.setBottom(footer);

        backButton = header.getAddRecipeButton();

        addListeners();
    }

    public void addListeners() {
        backButton.setOnAction(e -> {
            //do stuff
        })
    }
    
}
