package SharePage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

public class ShareModel {

    /*
     * This method generates or retrieves a shareable URL for a given recipe.
     * If a URL for this recipe already exists in the system, it will return that URL.
     * Otherwise, it will generate a new URL.
     */
    public String getShareableUrl(String recipeTitle) {
        try {
            String urlString = "http://localhost:8100/share"; // URL to your sharing service
            if (recipeTitle != null) {
                urlString += "?title=" + recipeTitle; // Append the recipe title as a query parameter
            }

            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response; // The response is expected to be the shareable URL
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }
}