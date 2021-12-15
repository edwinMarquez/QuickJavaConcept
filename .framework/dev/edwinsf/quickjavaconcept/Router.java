/**
 * Quick Java Concept
 * 
 * Created by Edwin Marquez. 
 * edwinsf.dev
 *
 */

package dev.edwinsf.quickjavaconcept;

import java.util.HashMap;

public class Router {

  HashMap<String, Class> routesMap = new HashMap<>();

  public Router() {

  }
  
  public <T extends HtmlView> void registerRoute(String route, Class<T> viewClass) {
    routesMap.put(route, viewClass);
  }
  
  public Class<HtmlView> resolveRoute(String route) {
    return routesMap.get(route);
  }

}