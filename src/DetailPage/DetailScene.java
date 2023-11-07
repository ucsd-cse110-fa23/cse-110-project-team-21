package DetailPage;


import javafx.scene.control.Button;
import javafx.scene.layout.*;
import main.java.MainPage.Main;
import main.java.RecipeLogic.Recipe;
import javafx.scene.control.ScrollPane;


public class DetailScene extends BorderPane{
    private DetailPageHeader header;
    private DetailPageFooter footer;
    private ScrollPane scrollPane;
    private DetailPageDescription desc;
    private Button backButton;
    private Button editButton;
    private Button deleteButton;

    private Recipe recipe;
    
    public DetailScene(Recipe r) {
        recipe = r;
        header = new DetailPageHeader(recipe.getTitle());
        footer = new DetailPageFooter();

        //System.out.println(recipe.getDescription());
        desc = new DetailPageDescription(recipe.getDescription());

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
