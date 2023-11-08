package UI.OpenAIResponsePage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import RecipeLogic.Recipe;


public class OpenAIResponseController{

    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    // Api Key Shang
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";
    private static final int maxTokens = 300;
    

    private String mealType;
    private String ingredients;
    private Recipe recipe;
    public OpenAIResponseController(String mealType, String ingredients) {
        this.mealType = mealType;
        this.ingredients = ingredients;
    } 

    public Recipe sendRequest() throws IOException, InterruptedException, URISyntaxException {

        String prompt = " I am a college student with little cooking experience. In my kitchen, I have" + ingredients + ". Can you give me a " + mealType + " recipe for a meal I can make using these ingredients? Please do not include any other ingredients in the recipe as I do not have the time to purchase more ingredients. List the title of the recipe as the first line of your output.";

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        String requestBodyString = requestBody.toString();

        // Create the HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBodyString))
        .build();


        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );

        // Process the response
        String responseBody = response.body();
        // System.out.println(responseBody);
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");
        // System.out.println(generatedText);
        String trimmedResponse = generatedText.replaceFirst("^\\s+", "");
        String[] lines = trimmedResponse.split("\n", 2);
        String title = lines[0];
        String description = lines[1];
        recipe = new Recipe(title, description);

        /*
        if(trimmedResponse.split("Ingredients:").length != 2){
            recipe = this.sendRequest();
        }else{
            String[] lines = trimmedResponse.split("Ingredients:");
            String title = lines[0];
            String description = "Ingredients: \n" + lines[1];
            recipe = new Recipe(title, description);
        }
        */
        return recipe;
    }

    public Recipe getRecipe(){
        return recipe;
    }

    public String getMealType(){
        return mealType;
    }

    public String getIngredients(){
        return ingredients;
    }
}