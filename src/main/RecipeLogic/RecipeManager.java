package main.RecipeLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RecipeManager {

    final String DATABASE_FILE = "recipes.csv";
    private ArrayList<Recipe> recipes;

    public RecipeManager() {
        try {
            recipes = loadRecipes();
        } catch (Exception e) {
            recipes = new ArrayList<Recipe>();
        }
    }

    public void addRecipe(Recipe r){
        recipes.add(r);
        try {
            updateRecipesToDatabase();
        } catch (Exception e) {
            System.out.println("Could not save recipe to database.");
        }
    }
    public void removeRecipe(Recipe r){
        recipes.remove(getRecipe(r.getTitle()));

        try {
            updateRecipesToDatabase();
        } catch (Exception e) {
            System.out.println("Could not update recipe list to database.");
        }
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
            Recipe r = recipes.get(i);
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
    private ArrayList<Recipe> loadRecipes() throws IOException{
        FileReader fr;
        String pathName = DATABASE_FILE;
        ArrayList<Recipe> retrievedDataBase = new ArrayList<Recipe>();

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

            Recipe add = new Recipe(title, description);

            retrievedDataBase.add(add); 
        }

        fr.close();
        br.close();

        return retrievedDataBase;
    }
}