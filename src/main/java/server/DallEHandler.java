package server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.javafx.iio.ios.IosDescriptor;
import com.sun.net.httpserver.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import RecipeManager.RecipeModel;


public class DallEHandler implements HttpHandler{
    private static final String API_ENDPOINT = "https://api.openai.com/v1/images/generations"; 
    private static final String API_KEY =
   "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";
    private static final String MODEL = "dall-e-2";


    public void handle(HttpExchange httpExchange) throws IOException{
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if(method.equals("GET")) {
                response = handleGet(httpExchange);
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

    private String handleGet(HttpExchange httpExchange) throws IOException, InterruptedException{
        String response = "Invalid GET Request";
        URI uri = httpExchange.getRequestURI();
        String prompt = uri.getRawQuery();
        System.out.println("Prompt: " + prompt);
        int n = 1;

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("n", n);
        requestBody.put("size", "256x256");


        // Create the HTTP client
        HttpClient client = HttpClient.newHttpClient();


        // Create the request object
        HttpRequest request = HttpRequest
            .newBuilder()
            .uri(URI.create(API_ENDPOINT))
            .header("Content-Type", "application/json")
            .header("Authorization", String.format("Bearer %s", API_KEY))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();


        // Send the request and receive the response
        HttpResponse<String> output = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );


        // Process the response
        String responseBody = output.body();


        JSONObject responseJson = new JSONObject(responseBody);

        // TODO: Process the response
        String generatedImageURL = responseJson.getJSONArray("data").getJSONObject(0).getString("url");
        
        System.out.println("DallE Response: ");
        System.out.println(generatedImageURL);


        // Download the Generated Image to Current Directory
        try(
            InputStream in = new URI(generatedImageURL).toURL().openStream()
        )
        {
            Files.copy(in, Paths.get(prompt + ".jpg"));
        } catch(Exception e) {
            System.out.println("Error: " + e);
        }
        
        // StringBuilder htmlBuilder = new StringBuilder();
        // htmlBuilder
        // .append("<html>")
        // .append("<body>")
        // .append("<h1>")
        // .append("Recipe: ")
        // .append("<img src='C:/Users/sammy/Desktop/CSE 110/Final Project/cse-110-project-team-21/=" + prompt + ".jpg' />")
        // .append("this is your recipe")
        // .append("</h1>")
        // .append("</body>")
        // .append("</html>");
        //response = htmlBuilder.toString();

        return generatedImageURL;
        //response = prompt + ".jpg";
        //return response;
    }
}