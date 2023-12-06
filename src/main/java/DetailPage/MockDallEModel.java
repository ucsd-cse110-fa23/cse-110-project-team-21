package DetailPage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import Main.Main;
/**
 * I think I found a problem. If we use the query to be the URL version of the recipe title, how are we
 * finding an existing image name if they all start with "="?
 */
public class MockDallEModel {

    public String performImageRequest(String query, ArrayList<String> mockDirectory) {

        query = query.replace(" ", "%20");
        /*
        System.out.println("Query: " + query);
        File tmpDir = new File("=" + query + ".jpg");
        boolean exists = tmpDir.exists();
        */
        boolean exists = mockDirectory.contains("=" + query + ".jpg");
        if(exists) {
            return "="+ query + ".jpg [already existed]";
        }
        try {
            //String urlString = "http://localhost:8100/dalle/";
            String urlString = Main.HOSTNAME_URL + "/dalle";
            if (query != null) {
                urlString += "?=" + query.replace(":", "_");
            }
            
            URL url = new URI(urlString).toURL();
            //System.out.println(url);
            MockHttpURLConnection conn = new MockHttpURLConnection(url);
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            String in = conn.getInputStream();
            String response = in;
            
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }
    public class MockHttpURLConnection{
        URL url;
        boolean doOutput;
        String inputStream;
        public MockHttpURLConnection(URL url){
            this.url = url;
            inputStream = new String();
            doOutput = false;
        }
        public void setRequestMethod(String method){
            if(method.equals("GET")){
                String s = url.toString().replace(Main.HOSTNAME_URL + "/dalle" + "?", "");
                inputStream = s;
            }
        }
        public void setDoOutput(boolean dooutput){
            doOutput = dooutput;
        }
        public String getInputStream(){
            if(doOutput)
                return inputStream + ".jpg";
            return "URL connection cannot be used for input and/or output";
        }
    }
}
