package RecipeMaker;

import java.io.*;
import java.util.ArrayList;

/**
 * Some possible edge cases: 
 * 1. What if the recipe contains a \n, how should that be saved in the csv file?
 * 2. Very unlikely, but on the occasion that ChatGPT somehow generates any text with a "|" in it, shit's gonna hit the fan
 * 
 * 
 */
public class DatabaseManager {
    final String DATABASE_FILE = "recipes.csv";
    ArrayList<Recipe> database;
    
    DatabaseManager(){
        try {
            database = loadRecipes();
        } catch (Exception e) {
            database = new ArrayList<Recipe>();
        }
    }

    public void addRecipe(Recipe r){
        database.add(new Recipe(r.getTitle(), r.getDescription()));
    }

    public void saveRecipesToDatabase() throws IOException{
        String pathName = DATABASE_FILE;
        File outputFile = new File(pathName);
        FileWriter fw;
        try {
            fw = new FileWriter(outputFile, false);
        } catch (Exception e) {
            throw new IOException("Could not initialize FileWriter with specified output file");
        }

        for(int i = 0; i < database.size(); i ++){
            Recipe r = database.get(i);
            String title = "\"" + r.getTitle() + "\"";
            String description = "\"" + r.getDescription() + "\"";

            String line = title + "|" + description + "\n";
            fw.write(line);
        }

        fw.close();
    }

    public Recipe findRecipe(String title){
        for(int i = 0; i < database.size(); i++){
            if(database.get(i).getTitle().equals(title)){
                return database.get(i);
            }
        }

        return null;
    }

    public void removeRecipe(Recipe r){
        for(int i = 0; i < database.size(); i++){
            if(database.get(i).getTitle().equals(r.getTitle())){
                database.remove(i);
            }
        }
    }

    public ArrayList<Recipe> getDatabase(){
        return this.database;
    }

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

            Recipe add = new Recipe(title, description);

            retrievedDataBase.add(add); 
        }

        fr.close();
        br.close();
        System.out.println("LOAD");
        return retrievedDataBase;
    }
}
