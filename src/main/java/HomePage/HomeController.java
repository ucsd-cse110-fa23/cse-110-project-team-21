package HomePage;

import java.util.List;

import DetailPage.DetailView;
import Main.Main;
import RecipeManager.RecipeModel;
import WhisperPage.WhisperView;

public class HomeController {

    HomeView mainPageView;
    HomeModel mainPageModel = new HomeModel(this);

    public HomeController(HomeView mainPageView) {
        this.mainPageView = mainPageView;
    }

    // Binding the controller to the view
    // This function is called when the view is first loaded
    public void activate(){
        updateRecipeList();
        addListeners();
    }


    // This function updates the recipe list in the home page
    // It is called when a new recipe is added, edited, or deleted
    public void updateRecipeList(){
        this.mainPageView.recipeList.getChildren().clear();
        try{
            List<RecipeModel> newRecipeList = mainPageModel.loadFromDatabase();
            for(RecipeModel r: newRecipeList){
                MainPageRecipeCard toAdd = new MainPageRecipeCard(r.getTitle());
                this.mainPageView.recipeList.getChildren().add(toAdd);
            }
            addListeners();
        } catch (Exception e) {
            e.printStackTrace();
            this.mainPageView.showNoServerAlert();
            return ;
        }
    }


    // This function adds listeners to all the title buttons in the recipe list
    // When a title button is clicked, the DetailView for that recipe is loaded
    public void addListeners(){
        for (int i = 0; i < this.mainPageView.recipeList.getChildren().size(); i++) {
            MainPageRecipeCard card = (MainPageRecipeCard) this.mainPageView.recipeList.getChildren().get(i);
            card.getDetailButton().setOnAction(e -> {
                DetailView details = new DetailView(mainPageModel.getRecipe(card.getTitle()));
                Main.sceneManager.ChangeScene(details);
            });
        }

        this.mainPageView.getAddButton().setOnAction(e -> {
            WhisperView whisperPageView = new WhisperView();
            Main.sceneManager.ChangeScene(whisperPageView);
        });
    }
}