package NewRecipePage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import RecipeManager.RecipeModel;

public class MockGPTModel {
    // a significant amount of the code in this class is taken or inspired from Lab 4

    // this class facilitates interacting with "chatGPT" (davinci-003) using the OpenAI API

    private String mealType;
    private String ingredients;
    private RecipeModel recipe;
    private String prompt;

    public void setPerameters(String mealType, String ingredients) {
        this.mealType = mealType;
        this.ingredients = ingredients;
        prompt = " I am a college student with little cooking experience. In my kitchen, I have" + ingredients + ". Can you give me a " + mealType + " recipe for a meal I can make using these ingredients? Please do not include any other ingredients in the recipe as I do not have the time to purchase more ingredients. List the title of the recipe as the first line of your output.";
    }

    public RecipeModel sendRequest(int recipeSeed) {
        String title = "";
        String description = "";
        if(recipeSeed == 0){
            title = "Title with ingredients " + ingredients;
            description = "Description with ingredients " + ingredients;
        }else{
            title = "Regenerated title with ingredients " + ingredients;
            description = "Regenerated description with ingredients " + ingredients;
        }
        // Create a request body which you will pass into request object

        recipe = new RecipeModel(title, description);
        recipe.setMealType(mealType); 

        return recipe;
    }

    public RecipeModel sendRequestMock(){
        String title = "Mock Recipe";
        String description = "Ingredients: \n" + "Mock Ingredients";
        recipe = new RecipeModel(title, description);
        return recipe;
    }

    public RecipeModel getRecipe(){
        return recipe;
    }

    public String getMealType(){
        return mealType;
    }

    public String getIngredients(){
        return ingredients;
    }
}
