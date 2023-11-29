package RecipeManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RecipeManagerModel {
    // this file manages the list of recipes in memory and reads/writes from the database (csv file)

    final String DATABASE_FILE = "recipes.csv";
    private ArrayList<RecipeModel> recipes;
    private int nextIndex;

    public RecipeManagerModel() {
        try {
            recipes = loadRecipes();
        } catch (Exception e) {
            recipes = new ArrayList<RecipeModel>();
        }
    }

    public void removeAllRecipe(){
        // removes all recipes in list then rewrites this to database
        recipes.clear();
        try {
            updateRecipesToDatabase();
        } catch (Exception e) {
            System.out.println("Could not update recipe list to database.");
        }
    }

    public void addRecipe(RecipeModel r){
        // adds the specified recipe, addresses duplicate titles, then rewrites everything to database

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
        recipes.add(0, r);
        try {
            updateRecipesToDatabase();
        } catch (Exception e) {
            System.out.println("Could not save recipe to database.");
        }
    }
    public void removeRecipe(RecipeModel r) {
        // removes the specified recipe then rewrites everything to database
    
        if (recipes == null || recipes.isEmpty() || !recipes.contains(r)) {
            System.out.println("Recipe not found");
            return;
        }

        Iterator<RecipeModel> iterator = recipes.iterator();
        while (iterator.hasNext()) {
            RecipeModel recipe = iterator.next();
            if (recipe.equals(r)) {
                iterator.remove();
                break;
            }
        }
    
        try {
            updateRecipesToDatabase();
        } catch (Exception e) {
            System.out.println("Could not update recipe list to the database.");
        }
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

    public void updateRecipesToDatabase() throws IOException{
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
    }
    /**
     * Reads a CSV file and creates a new list of Recipe objects by retrieving each title (item 0) and
     * description (item 1) from it.
     * @return Arraylist of Recipe objects based on what was retrieved from the recipe/database CSV file
     * @throws IOException if there's an issue opening the recipe/database file.
     */
    private ArrayList<RecipeModel> loadRecipes() throws IOException{
        FileReader fr;
        String pathName = DATABASE_FILE;
        ArrayList<RecipeModel> retrievedDataBase = new ArrayList<RecipeModel>();

        try {
            fr = new FileReader(pathName);
        } catch (Exception e) {
            throw new IOException();
        } 
        BufferedReader br = new BufferedReader(fr);
        
        while(br.ready()){  //Get each line and make a contact out of it
            String currLine = br.readLine();    //Read a line

            String[] data = currLine.split("\\|");

            String title = data[0].substring(1, data[0].length() - 1);

            String description = data[1].substring(1, data[1].length() - 1);
            description = description.replace("\\n", "\n").replace("\\r","\r");

            RecipeModel add = new RecipeModel(title, description); //uses old contructor, I kept it because it's an old add

            retrievedDataBase.add(add); 
        }

        fr.close();
        br.close();

        return retrievedDataBase;
    }

    public int incrementNextIndex(){
        int temp = nextIndex;
        this.nextIndex++;
        return temp;
    }
}