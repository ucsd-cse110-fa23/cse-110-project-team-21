package SharePage;

import DetailPage.DetailView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

class ShareHeader extends HBox{
    private Label shareLabel;
    public ShareHeader(){
        shareLabel = new Label("Share");
        this.getChildren().add(shareLabel);
    }
    
}

class ShareFooter extends HBox{

}

public class ShareView extends BorderPane{
    private Text text = new Text("This is the URL for the current recipe: ");
    private TextField urlText = new TextField();
    private Button backButton;
    private ShareHeader header = new ShareHeader();
    private ShareFooter footer = new ShareFooter();
    public ShareController shareController;

    public ShareView(DetailView view){
        this.setTop(header);
        this.setBottom(footer);
        urlText.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 5px;");
        urlText.setPrefWidth(400);
        urlText.setEditable(false);
        backButton = new Button("Back");

        // GridPane formLayout = new GridPane();
        // formLayout.setVgap(10);
        // formLayout.setHgap(10);
        // formLayout.setPadding(new Insets(20, 20, 20, 20));

        // // Adding components to the layout
        // formLayout.add(urlText, 0, 0);

        VBox centralLayout = new VBox(20);
        centralLayout.getChildren().addAll(text, urlText, backButton);
        centralLayout.setAlignment(Pos.CENTER);

        centralLayout.setPadding(new Insets(20)); // Optional padding for BorderPane
        this.setTop(header);
        this.setCenter(centralLayout);
        this.setBottom(footer);

        // Finally, set the conrtoller for this view
        this.shareController = new ShareController(this, view);
    }

    // This is the function that display the URL on the screen
    public void setUrlText(String url) {
        urlText.setText(url);
    }

    public ShareFooter getFooter() {
        return this.footer;
    }

    public ShareHeader getHeader() {
        return this.header;
    }

    public Button getBackButton(){
        return this.backButton;
    }

}
