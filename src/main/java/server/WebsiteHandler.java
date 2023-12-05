package server;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.net.*;

import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import static com.mongodb.client.model.Filters.*;


public class WebsiteHandler implements HttpHandler {

    String uri = "mongodb://awrussell:11432283Alex@ac-ki3nvmq-shard-00-00.cn7xges.mongodb.net:27017,ac-ki3nvmq-shard-00-01.cn7xges.mongodb.net:27017,ac-ki3nvmq-shard-00-02.cn7xges.mongodb.net:27017/?ssl=true&replicaSet=atlas-11iamj-shard-0&authSource=admin&retryWrites=true&w=majority";
    JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();

    MongoClient mongoClient;
    MongoDatabase pantrypal_db;
    MongoCollection<Document> recipeCollection;

    public WebsiteHandler () {
        mongoClient = MongoClients.create(uri);
        pantrypal_db = mongoClient.getDatabase("pantrypal_db");
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Message Recieved";
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")) {
            response = handleGet(httpExchange);
        } else {
            System.out.println("you goofed up");
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handleGet(HttpExchange httpExchange) throws IOException{
        String response;
        URI uri = httpExchange.getRequestURI();
        System.out.println("URI: " +uri);
        String query = uri.getRawQuery();
        String recipeTitle = "trash";
        String recipeDescription = "trash";
        String imgURL = "trash";
        if (query != null) {
            query = query.substring(query.indexOf("=") + 1);
            query = replaceUnderscore(query);
            String[] quers = query.split("&");
            quers[0] = quers[0].substring(1);
            for (String quer : quers) {
                //System.out.println("Query: " + quer);
            }
            // System.out.println(quers);
            MongoCollection<Document> userCollection = pantrypal_db.getCollection(quers[0]);
        
            if (userCollection.countDocuments() > 1) {
                Document recipe = userCollection.find(eq("title", quers[1])).first();

                recipeTitle = replaceUnderscore(recipe.get("title").toString());
                recipeDescription = replaceUnderscore(recipe.get("description").toString());
                imgURL = replaceUnderscore(recipe.get("imgURL").toString());
                //System.out.println("Image URL: " + imgURL);
            }
        }
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
        .append("<html>")
        .append("<body>")
        .append("<h1>")
        .append("Recipe: ")
        .append(recipeTitle)
        .append("</h1>")
        .append("<h3>")
        .append(recipeDescription)
        .append("<img src = '" + imgURL + "' />")
        .append("</h3>")
        .append("</body>")
        .append("</html>");


        // encode HTML content
        response = htmlBuilder.toString();
        return response;
    }

private String replaceUnderscore(String underscored) {
        String temp = underscored.replace("_", " ");
        temp = temp.replace("%0A","\n");
        temp = temp.replace("%26","&");

        return temp.replace("%20", " ");

    }
}