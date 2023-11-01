package RecipeMaker;

import java.util.*;

public class RecipeManager {
    private ArrayList<Recipe> recipes;

    public RecipeManager() {

    }

    public void addRecipe(Recipe r){
        recipes.add(r);
    }
    public void removeRecipe(Recipe r){
        recipes.remove(r);
    }
    public ArrayList<Recipe> getList(){
        return recipes;
    }
    public Recipe getRecipe(String s){
        for(Recipe r: recipes) {
            if(r.getTitle().equals(s)) {
                return r;
            }
        }
        return null;
    }
}
