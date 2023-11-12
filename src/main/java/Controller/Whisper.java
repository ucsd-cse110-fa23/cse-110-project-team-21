package Controller;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;

import org.json.*;

public class Whisper {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";
    private static ArrayList<String> recordingResult = new ArrayList<String>();  

    // Helper method to write a parameter to the output stream in multipart form data format
    private static void writeParameterToOutputStream(
        OutputStream outputStream,
        String parameterName,
        String parameterValue,
        String boundary
        ) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
        (
            "Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n"
        ).getBytes()
        );
        outputStream.write((parameterValue + "\r\n").getBytes());
    }
    // Helper method to write a file to the output stream in multipart form data format
    private static void writeFileToOutputStream(
        OutputStream outputStream,
        File file,
        String boundary
        ) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
        (
        "Content-Disposition: form-data; name=\"file\"; filename=\"" +
        file.getName() +
        "\"\r\n"
        ).getBytes()
            );
        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());
        
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }
    // Helper method to handle a successful response
    private static void handleSuccessResponse(HttpURLConnection connection)
        throws IOException, JSONException {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
        );
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        JSONObject responseJson = new JSONObject(response.toString());


        String generatedText = responseJson.getString("text");


        // Print the transcription result
        recordingResult.add(generatedText);
    }
    // Helper method to handle an error response
    private static void handleErrorResponse(HttpURLConnection connection)
        throws IOException, JSONException {
        BufferedReader errorReader = new BufferedReader(
            new InputStreamReader(connection.getErrorStream())
        );
        String errorLine;
        StringBuilder errorResponse = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
        }
        errorReader.close();
        String errorResult = errorResponse.toString();
        System.out.println("Error Result: " + errorResult);
    }
    
    public String execute(File file) throws IOException, URISyntaxException {
      // Create file object from file path
      //File file = new File(filePath);
      String res = "";
      
      // Set up HTTP connection
      URL url = new URI(API_ENDPOINT).toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      
      
      // Set up request headers
      String boundary = "Boundary-" + System.currentTimeMillis();
      connection.setRequestProperty(
          "Content-Type",
          "multipart/form-data; boundary=" + boundary
      );
      connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
      
      
      // Set up output stream to write request body
      OutputStream outputStream = connection.getOutputStream();
      
      
      // Write model parameter to request body
      writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
      
      // If the API supports language specification, you would add it like this:
      writeParameterToOutputStream(outputStream, "language", "en", boundary);
  
      
      // Write file parameter to request body
      writeFileToOutputStream(outputStream, file, boundary);
      
      
      // Write closing boundary to request body
      outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
      
      
      // Flush and close output stream
      outputStream.flush();
      outputStream.close();
      
      
      // Get response code
      int responseCode = connection.getResponseCode();
      
      
      // Check response code and handle response accordingly
      if (responseCode == HttpURLConnection.HTTP_OK) {
          handleSuccessResponse(connection);
      } else {
          handleErrorResponse(connection);
      }
      
      
      // Disconnect connection
      connection.disconnect();
      return res;
    }
    
    public ArrayList<String> getResult() {
      return recordingResult;
    }

    public ArrayList<String> getMockBLMTResult() {
        ArrayList<String> mock = new ArrayList<String>();
        mock.add("lunch");
        return mock;
    }

    public ArrayList<String> getMockIngredientResult() {
        ArrayList<String> mock = new ArrayList<String>();
        Collections.addAll(mock, "turkey", "tomato", "bread", "mayonaise", "eggs", "syrup", "lettuce", "chicekn", "steak", "potatos", "milk");
        return mock;
    }

    public ArrayList<String> getMockResult() {
        ArrayList<String> mock = new ArrayList<String>();
        Collections.addAll(mock, "lunch", "turkey, tomato, bread, cheese, mayonaise");
        return mock;
    }
        
}