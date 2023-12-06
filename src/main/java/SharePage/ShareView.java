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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import Main.Main;
import server.UserModel;

class ShareHeader extends HBox{
    private Label shareLabel;
    public ShareHeader(){
        // shareLabel = new Label("Share");
        // this.getChildren().add(shareLabel);

        this.setPrefSize(500, 60); 
        this.setStyle("-fx-background-color: #F0F8FF;");
        Text titleText = new Text("Share"); 
        titleText.setWrappingWidth(350);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");

        this.getChildren().add(titleText);
        this.setSpacing(30);
    }

}

class ShareURL extends VBox {
    Text urlText;
    private Button backButton;

    public ShareURL(String url) {
        //URL Text
        this.urlText = new Text(url);
        urlText.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 5px;");
        urlText.setWrappingWidth(400);
        this.getChildren().add(urlText);

        //buttons
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 16 arial; -fx-pref-width: 100; -fx-pref-height: 60";
        backButton = new Button("Delete");
        backButton.setStyle(defaultButtonStyle);

        this.getChildren().addAll(urlText, backButton);
    }

    public Button getBackButton() {
        return backButton;
    }
}

class ShareFooter extends HBox{

}

public class ShareView extends BorderPane{
    private String url;
    private Button backButton;
    private ShareHeader header;
    private ShareURL body;
    private ShareFooter footer;
    public ShareController shareController;

    public ShareView(DetailView detailView){
        this.shareController = new ShareController(this, detailView);
        UserModel user = Main.recipeManager.getUser();
        url = Main.HOSTNAME_URL + "/recipe/?=" + user + "&" + detailView.getRecipe().getImageURL();
        header = new ShareHeader();
        footer = new ShareFooter();
        body = new ShareURL(url);

        backButton = body.getBackButton();
        //setUrlText("Test");

        // GridPane formLayout = new GridPane();
        // formLayout.setVgap(10);
        // formLayout.setHgap(10);
        // formLayout.setPadding(new Insets(20, 20, 20, 20));

        // // Adding components to the layout
        // formLayout.add(urlText, 0, 0);

        // VBox centralLayout = new VBox(20);
        // centralLayout.getChildren().addAll(text, urlText, backButton);
        // centralLayout.setAlignment(Pos.CENTER);

        //centralLayout.setPadding(new Insets(20)); // Optional padding for BorderPane
        this.setTop(header);
        this.setCenter(body);
        this.setBottom(footer);

        // Finally, set the conrtoller for this view
    }

    // This is the function that display the URL on the screen
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