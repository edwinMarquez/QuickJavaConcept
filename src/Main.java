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

    public String someMethod() {
      //to retrieve get parameter you can call getGETParams().
      StringBuilder params = new StringBuilder("<ul>");
      for (Map.Entry<String, String> entry : getGETParams().entrySet()) {
        params.append("<li>");
        params.append(entry.getKey() + "=" + entry.getValue());
        params.append("</li>");
      }
      params.append("</ul>");
      return "params: <br>" + params.toString();
    }

    @Override
    public String toHtml() {
      //you can call methods in you java code from html, <% someMethod() %>
      return "<html><head><title>Test title</title></head><body>Hello world</br><% someMethod() %></body></html>";
    }
  }

}
