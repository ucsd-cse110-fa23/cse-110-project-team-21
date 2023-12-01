package RecipeManager;

import java.util.ArrayList;

public interface IRecipeManager {
    void addRecipe(RecipeModel r);
    void removeRecipe(RecipeModel r);
    ArrayList<RecipeModel> getList();
    RecipeModel getRecipe(String s);
}
