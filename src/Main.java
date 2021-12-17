import java.io.BufferedReader;
import java.io.InputStream;
/**
 * Quick Java Concept
 * 
 * Created by Edwin Marquez. 
 * edwinsf.dev
 *
 */

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
  public static class HtmlViewText implements HtmlView {
    @Override
    public String getViewFileName() {
      return null;
    }

    @Override
    public String toHtml() {
      return "<html><head><title>Test title</title></head><body>Hello world</body></html>";
    }
  }

}
