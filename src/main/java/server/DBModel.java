package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import RecipeManager.RecipeModel;

public class DBModel {
    public String performRequest(String method, UserModel user, RecipeModel recipe, int misc) {
        // Implement your HTTP request logic here and return the response

        try {
            String urlString = "http://localhost:8100/db";

            if (method.equals("GET") || method.equals("DELETE")) {
                // make query for get/delete
                if (method.equals("GET")) {
                    urlString += "?=" + replaceSpace(user.getUsername()) + "&"  + replaceSpace(user.getPassword()) + "&" + misc;
                } else {
                    urlString += "?=" + replaceSpace(user.getUsername()) + "&" + replaceSpace(recipe.getTitle());
                }
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                if (method.equals("POST")) {
                    if (misc==-1) {
                        out.write(user.getUsername() + "&" + user.getPassword());
                    } else {
                        out.write(user.getUsername() + "&" + recipe.getTitle() + "&" + recipe.getDescription() + "&" + recipe.getMealType() + "&" + recipe.getIndex() + "&" + misc);
                    }
                    
                } else {
                    out.write(user.getUsername() + "&" + recipe.getTitle() + "&" + recipe.getDescription());

                } 
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    private String replaceSpace(String spaced) {
        String underscored = spaced.replace(" ", "_");
        return underscored;
    }
    
}
