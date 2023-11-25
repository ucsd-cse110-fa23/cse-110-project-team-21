package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import RecipeManager.RecipeModel;
import com.sun.net.httpserver.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.*;

import static java.util.Arrays.asList;

public class DBHandler implements HttpHandler {

    // a significant amount of the code in this class is taken or inspired from Lab 5

    // this class facilitates interacting with our MongoDB database

    String uri = "mongodb://awrussell:11432283Alex@ac-ki3nvmq-shard-00-00.cn7xges.mongodb.net:27017,ac-ki3nvmq-shard-00-01.cn7xges.mongodb.net:27017,ac-ki3nvmq-shard-00-02.cn7xges.mongodb.net:27017/?ssl=true&replicaSet=atlas-11iamj-shard-0&authSource=admin&retryWrites=true&w=majority";
    JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();

    MongoClient mongoClient;
    MongoDatabase pantrypal_db;
    MongoCollection<Document> recipeCollection;

    public DBHandler() throws FileNotFoundException {
            mongoClient = MongoClients.create(uri);
            pantrypal_db = mongoClient.getDatabase("pantrypal_db");

            // recipeCollection.drop();
    }

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
            else if(method.equals("POST")) {
                response = handlePost(httpExchange);
            }
            else if(method.equals("PUT")) {
                response = handlePut(httpExchange);
            }
            else if(method.equals("DELETE")) {
                response = handleDelete(httpExchange);
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

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            query = query.substring(query.indexOf("=") + 1);
            query = replaceUnderscore(query);
            String[] quers = query.split("&");
            MongoCollection<Document> userCollection = pantrypal_db.getCollection(quers[0]);
            

            if (quers[2].equals("1")) { // get the user to check if username taken during account creation 
                if (userCollection.countDocuments() > 0) {
                    response = "Taken";
                } else {
                    response = "Available";
                }
            } else if (quers[2].equals("2")) { // get the user to check if username/password matches during login
                System.out.println("Num docs: " + userCollection.countDocuments() + ". Input password: " + quers[1]);
                 if (userCollection.find(eq("password", quers[1])).first() == null) {
                    response = "Incorrect";
                 } else {
                    response = "Correct";
                 }
            } else if (quers[2].equals("3")) { // get all of a user's recipes
                List<Document> studentList = userCollection.find(gte("index", 0)).into(new ArrayList<>());
                response = "";
                for (Document student : studentList) {
                    response = response + student.get("title");
                    response = response + "_" + student.get("description");
                    response = response + "_" + student.get("meal_type");
                    response = response + "_" + student.get("index");
                    response = response + "&";
                }
            } else {System.out.println("Invalid input for misc param in DB GET");}
            //response = result.toString();
        } 
        System.out.println(response);
        return response;
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        //System.out.println(postData);
        String[] quers = postData.split("&");
        /*
         * String language = postData.substring(
         * 0,
         * postData.indexOf(",")
         * ), year = postData.substring(postData.indexOf(",") + 1);
         */
        String username = quers[0];
        MongoCollection<Document> userCollection = pantrypal_db.getCollection(username);
        String response = "Posted: ";
        if (quers.length==2) {
            Document user = new Document("info", username);
            user.append("password", quers[1])
                   .append("nextIndex", 0)
                   /* .append("recipes", asList())*/;
            userCollection.insertOne(user);
            response = response + "New user with username: " + username + " and password: " + quers[1];
        } else {

           //Bson update = addToSet("vendor", "C");
            //userCollection.updateOne(update);
            Document recipe = new Document("title", quers[1]);
            recipe.append("description", quers[2])
                   .append("meal_type", quers[3])
                   .append("index", Integer.parseInt(quers[4]));
            userCollection.insertOne(recipe);
            Bson filter = gte("nextIndex", 0);
            Bson updateOperation = inc("nextIndex", 1);
            UpdateResult updateResult = userCollection.updateOne(filter, updateOperation);
            //Document tempUser = userCollection.find(eq("username", username)).first();
            //List<Document> l = (tempUser.getList("recipes",Document.class));
            //l.add(recipe)
            response = response + "New recipe with title: " + quers[1] + " and description: " + quers[2];
        }

        //String response = "Posted entry {" + language + ", " + year + "}";
        System.out.println(response);
        scanner.close();
        //response = response + " : " + postData;

        return response;
    }

    private String handlePut(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        //System.out.println(postData);
        String[] quers = postData.split("&");
        String username = quers[0];
        MongoCollection<Document> userCollection = pantrypal_db.getCollection(username);
        String response = "Posted: ";

        Bson filter = eq("title", quers[1]);
        Bson updateOperation = set("description", quers[2]);
        UpdateResult updateResult = userCollection.updateOne(filter, updateOperation);

        response = response + "Updated recipe with title: " + quers[1] + " to new description: " + quers[2];

        System.out.println(response);
        scanner.close();

        return response; 
    }

    private String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "Invalid DELETE request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            query = query.substring(query.indexOf("=") + 1);
            query = replaceUnderscore(query);
            String[] quers = query.split("&");
            MongoCollection<Document> userCollection = pantrypal_db.getCollection(quers[0]);
            Bson filter = eq("title", quers[1]);
            DeleteResult result = userCollection.deleteOne(filter);
            //response = result.toString();
            response = "Deleted recipe with title: " + quers[1];
        } 
        System.out.println(response);
        return response;
    }
    private String replaceUnderscore(String underscored) {
        return underscored.replace("_", " ");
    }
}
