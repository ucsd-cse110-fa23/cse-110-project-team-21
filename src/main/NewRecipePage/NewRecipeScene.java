package main.NewRecipePage;

import javafx.scene.layout.*;


public class NewRecipeScene extends BorderPane {
    private NewRecipeHeader header;
    private NewRecipeCenterScreen centerScreen;
    private NewRecipeFooter footer;
    // private ScrollPane scrollPane;

    public NewRecipeScene(){
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
