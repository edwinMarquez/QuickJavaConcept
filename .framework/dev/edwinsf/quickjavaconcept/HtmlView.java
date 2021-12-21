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

  public HashMap<String,String> getGETParams(){
    return getParams;
  }

  protected void setGETParams(HashMap<String,String> getParams){
    this.getParams = getParams;
  }

  public abstract String getViewFileName();

  public abstract String toHtml();

}
