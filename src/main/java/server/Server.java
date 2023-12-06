package server;
import com.sun.net.httpserver.*;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import RecipeManager.RecipeModel;


public class Server {


 // initialize server port and hostname
 private static final int SERVER_PORT = 8100;
 private static final String SERVER_HOSTNAME = "100.115.42.188";


 public static void main(String[] args) throws IOException {
   // create a thread pool to handle requests
   ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

   Map<String, String> data = new HashMap<>();

   // create a server
   HttpServer server = HttpServer.create(
     new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
     0
   );

  // TODO: create the context
  server.createContext("/", new RequestHandler(data));
  server.createContext("/recipe", new WebsiteHandler());
  server.createContext("/gpt", new GPTHandler());
  server.createContext("/dalle", new DallEHandler());
  //server.createContext("/Whisper", new WhisperController());
  //server.createContext("/recipe", new Database());
  server.createContext("/db", new DBHandler());

  // TODO: set the executor
  server.setExecutor(threadPoolExecutor);
  // TODO: start the server
  server.start();

   System.out.println("Server started on port " + SERVER_PORT);

   // delete any autologin info stored from a previous run of the server
   String pathName = "password.txt";
   File infoFile = new File(pathName);
   infoFile.delete();

   /*DBController test = new DBController(new DBModel());
   test.handleGetButton(null, 1);
   test.handleGetButton(null, 2);
   test.handlePostButton(null,-1, 1);
   test.handleGetButton(null, 1);
   test.handleGetButton(null, 2);
   test.handlePostButton(null,0, 1);
   test.handlePutButton(null);
   test.handlePostButton(null,0, 2);
   test.handleDeleteButton(null);
   ArrayList<RecipeModel> recipes = test.parseDBRecipes(test.handleGetButton(null, 3));
   for (RecipeModel recipe : recipes) {
    System.out.println();
    System.out.println(recipe.getTitle());
    System.out.println(recipe.getDescription());
    System.out.println(recipe.getMealType());
    System.out.println(recipe.getIndex());
   } */
  }
}
