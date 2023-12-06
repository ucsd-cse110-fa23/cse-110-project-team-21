package SharePage;

import javafx.scene.layout.BorderPane;
import Main.Main;
import RecipeManager.RecipeModel;


public class MockShareView extends BorderPane{
    private String url;
 
    public MockShareView(RecipeModel detailViewRecipe, String username){
        String user = username;
        url = Main.HOSTNAME_URL + "/recipe/?=%20" + user + "&" + detailViewRecipe.getTitle().replace(" ", "%20");
    }

    // This is the function that display the URL on the screen
    public String getUrl() {
        return this.url;
    }

}