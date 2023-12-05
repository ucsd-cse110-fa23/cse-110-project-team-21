package RecipeManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import Main.Main;
import server.UserModel;

public class RecipeManagerModel implements IRecipeManager {
    // this file manages the list of recipes in memory and reads/writes from the database (csv file)


    private ArrayList<RecipeModel> recipes;
    private int nextIndex;
    private UserModel user;

    public RecipeManagerModel(UserModel user) {
        this.user = user;
        try {
            recipes = loadRecipes();
        } catch (Exception e) {
            recipes = new ArrayList<RecipeModel>();
        }
    }

    /*public void removeAllRecipe(){
        // removes all recipes in list then rewrites this to database
        recipes.clear();
        try {
            updateRecipesToDatabase();
        } catch (Exception e) {
            System.out.println("Could not update recipe list to database.");
        }
    } not used in real database */

    public void addRecipe(RecipeModel r){
        // adds the specified recipe, addresses duplicate titles, and adds to database

        // Method 1, replace the previous recipe
        // Recipe lastRecipe = this.getRecipe(r.getTitle());
        // if(lastRecipe != null){
        //     lastRecipe.setDescription(r.getDescription());
        // updateRecipesToDatabase();

        // ->NEW: Method 2, add a number to the end of the title

    
        RecipeModel lastRecipe = this.getRecipe(r.getTitle());
        if(lastRecipe != null){
            int i = 1;
            while(this.getRecipe(r.getTitle() + " " + Integer.toString(i)) != null){
                i = Integer.valueOf(i) + 1;
            }
            r.setTitle(r.getTitle() + " " + Integer.toString(i));
        }
        // <-End of NEW

        // add to local ArrayList
        recipes.add(0, r);

        // Database: add one recipe: needs user info, recipe info, and misc=nextIndex
        String response;
        response = Main.dbModel.performRequest("POST", user, r, incrementNextIndex());
        System.out.println("Response: " + response);
    }
    public void removeRecipe(RecipeModel r) {
        // removes the specified recipe from local and database
    
        if (recipes == null || recipes.isEmpty() || !recipes.contains(r)) {
            System.out.println("Recipe not found");
            return;
        }

        // delete recipe locally
        Iterator<RecipeModel> iterator = recipes.iterator();
        while (iterator.hasNext()) {
            RecipeModel recipe = iterator.next();
            if (recipe.equals(r)) {
                iterator.remove();
                break;
            }
        }

        // delete recipe from database
        String response = Main.dbModel.performRequest("DELETE", user, r, 0);
        System.out.println("Response: " + response);
    }

    public void editRecipe(RecipeModel r, String newDesc) {
        // edit the specified recipe locally and in database
    
        if (recipes == null || recipes.isEmpty() || !recipes.contains(r)) {
            System.out.println("Recipe not found");
            return;
        }

        // edit local recipe first
        recipes.get(recipes.indexOf(r)).setDescription(newDesc);

        // edit in DB: needs user info, recipe info, misc=0
        String response = Main.dbModel.performRequest("PUT", user, r, 0);
        System.out.println("Response: " + response);
    }



//     public void editRecipe(String title, String description){ // updates the edited recipe then rewrites everything to database
//         Recipe r = getRecipe(title);
//         r.setDescription(description);
//         try {
//             updateRecipesToDatabase();
//         } catch (Exception e) {
//             System.out.println("Could not update recipe list to database.");
//         }
//     }

    public ArrayList<RecipeModel> getList(){
        try {
            recipes = loadRecipes();
        } catch (Exception e) {
            System.out.println("Could not load recipes from database.");
            e.printStackTrace();
        }
        return recipes;
    }

    public RecipeModel getRecipe(String s){
        recipes = getList();
        for(RecipeModel r: recipes) {
            if(r.getTitle().equals(s)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Takes every Recipe in the recipes list and writes its title and description as
     * an entry in the CSV file. The CSV file is completely overwritten when this method
     * is called. Every item is separated by a "|" since commas are fairly likely to appear
     * in the recipe generation
     * @throws IOException if there's any error opening the outputfile
     */

    /*public void updateRecipesToDatabase() throws IOException{
        String pathName = DATABASE_FILE;
        File outputFile = new File(pathName);
        FileWriter fw;
        try {
            fw = new FileWriter(outputFile, false);
        } catch (Exception e) {
            throw new IOException("Could not initialize FileWriter with specified output file");
        }

        for(int i = 0; i < recipes.size(); i ++){
            RecipeModel r = recipes.get(i);
            String title = "\"" + r.getTitle() + "\"";

            String description = "\"" + r.getDescription() + "\"";
            description = description.replace("\n","\\n").replace("\r", "\\r");
            String line = title + "|" + description + "\n";
            fw.write(line);
        }

        fw.close();
    } not used in real database */

    /**
     * Reads a CSV file and creates a new list of Recipe objects by retrieving each title (item 0) and
     * description (item 1) from it.
     * @return Arraylist of Recipe objects based on what was retrieved from the recipe/database CSV file
     * @throws IOException if there's an issue opening the recipe/database file.
     */
    private ArrayList<RecipeModel> loadRecipes() throws IOException{
        // get all of a user's recipes: needs user info, misc=3
        String response = Main.dbModel.performRequest("GET", user, null, 3);
        // get a user's nextIndex: needs user info, misc=4
        this.nextIndex = Integer.parseInt(Main.dbModel.performRequest("GET", user, null, 4));
        //System.out.println("Response: " + response);
        return parseDBRecipes(response);
    }

    public int incrementNextIndex(){
        int temp = nextIndex;
        this.nextIndex++;
        return temp;
    }

    public ArrayList<RecipeModel> parseDBRecipes(String raw) {
        ArrayList<RecipeModel> recipes = new ArrayList<>();
        if (raw.equals("Empty")) {return recipes;}
        String[] rawRecipes = raw.split("&");
        for (String rawRecipe : rawRecipes) {
            String[] recipeInfo = rawRecipe.split("_");
            RecipeModel tmp = new RecipeModel(recipeInfo[0], recipeInfo[1].replace("%0A", "\n"));
            tmp.setMealType(recipeInfo[2]);
            tmp.setIndex(Integer.parseInt(recipeInfo[3]));
            tmp.setImageURL(recipeInfo[4].replace("%26", "&"));
            recipes.add(tmp);
        }
        Collections.reverse(recipes);
        return recipes;
    }
}
