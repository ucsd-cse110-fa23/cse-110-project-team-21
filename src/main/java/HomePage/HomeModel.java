package HomePage;

import Main.Main;
import RecipeManager.RecipeModel;

import java.util.List;

public class HomeModel {
    
    HomeController homeController;
    public HomeModel(HomeController controller) {
        this.homeController = controller;
    }

    public List<RecipeModel> loadFromDatabase(){
        return Main.recipeManager.getList();
    }

    public RecipeModel getRecipe(String title){
        return Main.recipeManager.getRecipe(title);
    }
}
