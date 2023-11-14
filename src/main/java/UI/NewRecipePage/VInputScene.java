package UI.NewRecipePage;

import javafx.scene.layout.*;


public class VInputScene extends BorderPane {
    // this class specifies the layout for the new recipe input screen UI

    private VInputHeader header;
    private VInputCenterScreen centerScreen;
    private VInputFooter footer;
    // private ScrollPane scrollPane;

    public VInputScene(){
        // Initialise the header Object
        header = new VInputHeader();
        // Initialise the CenterScreen Object
        centerScreen = new VInputCenterScreen();
        // Initialise the Footer Object
        footer = new VInputFooter(centerScreen);

        // scrollPane = new ScrollPane(centerScreen);
        // scrollPane.setFitToWidth(isCache());
        // scrollPane.setFitToHeight(isCache());
        
        // Add header to the top of the BorderPane
        VInputScene.this.setTop(header);
        VInputScene.this.setCenter(centerScreen);
        VInputScene.this.setBottom(footer);   
    }    
}
