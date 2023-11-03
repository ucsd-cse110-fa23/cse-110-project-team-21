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
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.shape.Circle;
import java.io.File;

class DetailFooter extends HBox{
    private Button deleteButton;
    private Button editButton;
    
    DetailFooter() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 16 arial; -fx-pref-width: 100; -fx-pref-height: 60";

        deleteButton = new Button("Delete");
        deleteButton.setStyle(defaultButtonStyle);
        editButton = new Button("Edit");
        editButton.setStyle(defaultButtonStyle);
        this.getChildren().addAll(deleteButton, editButton);
        deleteButton.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        //editButton.setAlignment(Pos.CENTER_RIGHT);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getEditButton() {
        return editButton;
    }
}

class DetailHeader extends HBox {
    private Button backButton;

    DetailHeader(String title) {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text(title); // Text of the Header
        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setSpacing(30);
        //this.setAlignment(Pos.CENTER); // Align the text to the Center


        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        backButton = new Button("Back"); // text displayed on add button
        backButton.setStyle(defaultButtonStyle); // styling the button

        this.getChildren().addAll(backButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER_RIGHT); // aligning the buttons to center
    }

    public Button getBackButton() {
        return backButton;
    }
}

class Description extends FlowPane {
    Text description;

    Description(Recipe recipe) {
        description = new Text(recipe.getDescription());

        //this.setPrefWrapLength(400);
        description.setWrappingWidth(400);
        // this.setPrefWidth(200);

        this.getChildren().add(description);
    }
}

public class DetailScene extends BorderPane{

    private DetailHeader header;
    private DetailFooter footer;
    private ScrollPane scrollPane;
    private Description desc;
    private Button backButton;
    private Button editButton;
    private Button deleteButton;

    private Recipe recipe;
    private String title = "Experienced Chef's Meal: Beef and Spinach Stuffed Chicken with Cheesy Mashed Potatoes";
    private String stuff = "Experienced Chef's Meal: Beef and Spinach Stuffed Chicken with Cheesy Mashed Potatoes\r\n" + //
            "\r\n" + //
            "Ingredients:\r\n" + //
            "\r\n" + //
            "Beef and Spinach Stuffed Chicken:\r\n" + //
            "\r\n" + //
            "Chicken breasts\r\n" + //
            "Beef (ground)\r\n" + //
            "Spinach\r\n" + //
            "White onion\r\n" + //
            "Salt\r\n" + //
            "Pepper\r\n" + //
            "Cheese (for stuffing)\r\n" + //
            "Mustard\r\n" + //
            "Ketchup\r\n" + //
            "Cheesy Mashed Potatoes:\r\n" + //
            "\r\n" + //
            "Potatoes\r\n" + //
            "Cheese (for mixing and topping)\r\n" + //
            "Milk\r\n" + //
            "Salt\r\n" + //
            "Pepper\r\n" + //
            "Instructions:\r\n" + //
            "\r\n" + //
            "Beef and Spinach Stuffed Chicken:\r\n" + //
            "\r\n" + //
            "Preheat your oven to 375°F (190°C).\r\n" + //
            "\r\n" + //
            "In a skillet, sauté finely chopped white onion and ground beef until the beef is browned. Add spinach and cook until wilted. Season with salt and pepper.\r\n" + //
            "\r\n" + //
            "Butterfly the chicken breasts, then stuff them with the beef and spinach mixture, along with slices of cheese. Close the chicken breasts and secure them with toothpicks.\r\n" + //
            "\r\n" + //
            "Mix mustard and ketchup together to create a glaze.\r\n" + //
            "\r\n" + //
            "Brush the chicken with the glaze and bake in the preheated oven for about 25-30 minutes or until the chicken is cooked through.\r\n" + //
            "\r\n" + //
            "Cheesy Mashed Potatoes:\r\n" + //
            "\r\n" + //
            "Peel and chop the potatoes, then boil them until tender.\r\n" + //
            "\r\n" + //
            "Mash the cooked potatoes, adding milk and cheese for creaminess and flavor. Season with salt and pepper.\r\n" + //
            "\r\n" + //
            "Top the mashed potatoes with more cheese.\r\n" + //
            "\r\n" + //
            "Serve the Beef and Spinach Stuffed Chicken with Cheesy Mashed Potatoes for a more complex and flavorful dish that showcases your culinary skills.";

    /*
     * TODO: make it so that this scene takes a recipe object as an input
     */
    DetailScene() {
        header = new DetailHeader(title);
        footer = new DetailFooter();

        recipe = new Recipe("name", stuff);
        System.out.println(recipe.getDescription());
        desc = new Description(recipe);

        scrollPane = new ScrollPane(desc);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);

        backButton = header.getBackButton();
        editButton = footer.getEditButton();
        deleteButton = footer.getDeleteButton();


        addListeners();
    }

    public void addListeners() {
        backButton.setOnAction(e -> {
            System.out.println("We are on the details page");
            Main.sceneManager.ChangeScene(Main.root);
            //do stuff
        });

        deleteButton.setOnAction(e -> {
            //stuff
        });

        editButton.setOnAction(e -> {
            //stuff
        });
    }
    
}
