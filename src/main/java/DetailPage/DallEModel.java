package DetailPage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

import Main.Main;

public class DallEModel {

    /*
     * this method takes a recipe title as a query to generate a new image, then returns the file
     * name of the generated image. If the image for this recipe already exists, it will return
     * the already existing file name.
     */
    public String performImageRequest(String query) {
        query = query.replace(" ", "%20");
        System.out.println("Query: " + query);
        File tmpDir = new File("=" + query + ".jpg");
        boolean exists = tmpDir.exists();
        if(exists) {
            //System.out.println("it already exists");
            return "="+ query + ".jpg";
        }
        try {
            //String urlString = "http://localhost:8100/dalle/";
            String urlString = Main.HOSTNAME_URL + "/dalle";
            if (query != null) {
                urlString += "?=" + query.replace(":", "_");
            }
            
            URL url = new URI(urlString).toURL();
            //System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }
}
