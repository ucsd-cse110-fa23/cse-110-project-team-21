package Controller;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class WebsiteHandler implements HttpHandler {
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
        String name = uri.getRawQuery();
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
        .append("<html>")
        .append("<body>")
        .append("<h1>")
        .append("Recipe: ")
        .append(name)
        .append("</h1>")
        .append("</body>")
        .append("</html>");


        // encode HTML content
        response = htmlBuilder.toString();
        return response;
    }
}