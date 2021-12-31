/**
 * Quick Java Concept
 * 
 * Created by Edwin Marquez. 
 * edwinsf.dev
 *
 */

package dev.edwinsf.quickjavaconcept;

import java.util.HashMap;

public abstract class HtmlView {

  private HashMap<String, String> getParams = null;
  private HashMap<String, Object> postParams = null;

  public HashMap<String, String> getGETParams() {
    return getParams;
  }

  public HashMap<String, Object> getPOSTParams() {
    return postParams;
  }

  protected void setGETParams(HashMap<String, String> getParams) {
    this.getParams = getParams;
  }
  
  protected void setPOSTParams(HashMap<String, Object> postParams) {
    this.postParams = postParams;
  }

  public abstract String getViewFileName();

  public abstract String toHtml();

}
