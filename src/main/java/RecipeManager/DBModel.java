package RecipeManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import server.UserModel;
import Main.Main;

public class DBModel {
    public String performRequest(String method, UserModel user, RecipeModel recipe, int misc) {
        // Implement your HTTP request logic here and return the response

        try {
            // String urlString = "http://localhost:8100/db";
            String urlString = Main.HOSTNAME_URL + "/db";

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
            // conn.setConnectTimeout(5000); // Set a 5-second connection timeout
            // conn.setReadTimeout(5000);    // Set a 5-second read timeout
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                if (method.equals("POST")) {
                    if (misc==-1) {
                        out.write(replaceSpace(user.getUsername()) + "&" + replaceSpace(user.getPassword()));
                    } else {
                        //System.out.println("Type: " + recipe.getMealType());
                        //System.out.println("Index: " + recipe.getIndex());
                        //System.out.println("Desc: " + recipe.getDescription());
                        out.write(replaceSpace(user.getUsername()) + "&" + replaceSpace(recipe.getTitle()) + "&" + replaceSpace(recipe.getDescription()) + "&" + replaceSpace(recipe.getMealType()) + "&" + recipe.getIndex() + "&" + misc + "&" + replaceSpace(recipe.getImageURL(urlString)));
                    }
                    
                } else {
                    out.write(replaceSpace(user.getUsername()) + "&" + replaceSpace(recipe.getTitle()) + "&" + replaceSpace(recipe.getDescription()));

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
        underscored = underscored.replace("\n", "%0A");
        underscored = underscored.replace("&", "%26");
        return underscored;
    }
    
}
