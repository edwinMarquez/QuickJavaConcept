/**
 * Quick Java Concept
 * 
 * Created by Edwin Marquez. 
 * edwinsf.dev
 *
 */
import java.util.Map;

import dev.edwinsf.quickjavaconcept.*;


public class Main {

  public static void main(String[] args) {

    //you can just code in java
    System.out.println("Hello world ");

    //or create your UI on html, and have a dummy server showcase it.
    Router router = new Router();
    router.registerRoute("/", HtmlViewText.class);
    Server server = new Server(router);
    server.triggerLocalBrowser();
    server.start(); //currently this is a blocking call

  }
  
  //dummy htmlView could look like this:
  public static class HtmlViewText extends HtmlView {
    @Override
    public String getViewFileName() {
      return null; //this takes precedence, if not null toHtml will be ignored
    }

    public String getParams() {
      //to retrieve get parameter you can call getGETParams().
      StringBuilder params = new StringBuilder("<ul>");
      for (Map.Entry<String, String> entry : getGETParams().entrySet()) {
        params.append("<li>");
        params.append(entry.getKey() + "=" + entry.getValue());
        params.append("</li>");
      }
      params.append("</ul>");
      return "<br>" + params.toString();
    }


    public String postParams() {
      // to retrieve post parameter you can call getPOSTParams().
      StringBuilder params = new StringBuilder("<ul>");
      for (Map.Entry<String, Object> entry : getPOSTParams().entrySet()) {
        params.append("<li>");
        params.append(entry.getKey() + "=" + entry.getValue());
        params.append("</li>");
      }
      params.append("</ul>");
      return "<br>" + params.toString();
    }

    @Override
    public String toHtml() {
      //you can call methods in you java code from html, <% someMethod() %>
      return "<html><head><title>Test title</title></head><body>Hello world</br>get params: <%getParams()%> </br>post params:</br><% postParams() %></br>"+
        "<form action=\"/?getparam1=a&getparam2=b\" method=\"post\"><label for=\"fname\">First name:</label><input type=\"text\" id=\"fname\" name=\"fname\"><br><br>"+
        "<label for=\"lname\">Last name:</label><input type=\"text\" id=\"lname\" name=\"lname\"><br><br><input type=\"submit\" value=\"Submit\">"+
        "</form></body></html>";
    }
  }

}
