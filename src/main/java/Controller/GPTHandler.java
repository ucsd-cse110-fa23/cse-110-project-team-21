package Controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import com.sun.net.httpserver.*;
import RecipeLogic.Recipe;


public class GPTHandler implements HttpHandler {
    // a significant amount of the code in this class is taken or inspired from Lab 4

    // this class facilitates interacting with "chatGPT" (davinci-003) using the OpenAI API

    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    // Api Key Shang
    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";
    private static final int maxTokens = 300;
    

    private String mealType;
    private String ingredients;
    private Recipe recipe;
    private String prompt;

    public void handle(HttpExchange httpExchange) throws IOException{
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if(method.equals("GET")) {
                //response = this.sendRequest();
                response = handleGet(httpExchange);
                //urlThing();
                //response = this.handleGet(httpExchange);
            }
            else if(method.equals("CONNECT")) {
                response = "TODO: Connection established";
            }
            else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }
    
    public String handleGet(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String mealType = query.substring(query.indexOf("=") + 1);
            String ingredients = query.substring(query.lastIndexOf("=") + 1);
            response = sendRequest(mealType, ingredients);
        } else {
            response = "Insufficient input. Please input a meal type and your ingredients.";
        }
        return response;
        // StringBuilder htmlBuilder = new StringBuilder();
        // htmlBuilder
        // .append("<html>")
        // .append("<body>")
        // .append("<h1>")
        // .append("Recipe: ")
        // .append(response)
        // .append("this is your recipe")
        // .append("</h1>")
        // .append("</body>")
        // .append("</html>");


        // // encode HTML content
        // response = htmlBuilder.toString();
        // return response;
    }

    public String sendRequest(String mealType, String ingredients) throws IOException, InterruptedException, URISyntaxException {
        String prompt = "I am a college student with little cooking experience. In my kitchen, I have" + ingredients + ". Can you give me a " + mealType + " recipe for a meal I can make using these ingredients? Please do not include any other ingredients in the recipe as I do not have the time to purchase more ingredients. List the title of the recipe as the first line of your output.";
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
        return trimmedResponse;
    }
}