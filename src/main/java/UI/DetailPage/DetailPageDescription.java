package UI.DetailPage;


import javafx.scene.layout.*;
import javafx.scene.text.*;


public class DetailPageDescription extends FlowPane {
    // this class specifies the description UI of a detail page

    Text description;

    public DetailPageDescription(String s) {
        description = new Text(s);

        //this.setPrefWrapLength(400);
        description.setWrappingWidth(400);
        // this.setPrefWidth(200);

        this.getChildren().add(description);
    }
}
