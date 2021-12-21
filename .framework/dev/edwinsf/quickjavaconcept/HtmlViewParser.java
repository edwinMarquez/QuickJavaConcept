/**
 * Quick Java Concept
 * 
 * Created by Edwin Marquez.
 * edwinsf.dev
 *
 */

package dev.edwinsf.quickjavaconcept;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.edwinsf.quickjavaconcept.logger.Logger;

public class HtmlViewParser {

  private HtmlViewParser() {
  }

  public static String parse(Class<HtmlView> htmlViewClass,HashMap<String,String> getParams) {

    try {
      HtmlView htmlViewInstance = htmlViewClass.getDeclaredConstructor().newInstance();
      InputStream resource = null;
      if (htmlViewInstance.getViewFileName() == null || htmlViewInstance.getViewFileName().isEmpty()) {
        //return (htmlViewInstance.toHtml() == null) ? "" : htmlViewInstance.toHtml();
        if (htmlViewInstance.toHtml() == null) return "";
        resource = new ByteArrayInputStream(htmlViewInstance.toHtml().getBytes(StandardCharsets.UTF_8));
      }
      else {
        resource = HtmlViewParser.class.getResourceAsStream("/" + htmlViewInstance.getViewFileName());
      }
      htmlViewInstance.setGETParams(getParams);
      if (resource == null) {
        Logger.e("HtmlViewParser", "HtmlView file not found on class path: " + htmlViewInstance.getViewFileName());
        return "404";// TODO
      }
      // File file = new File(resource);
      InputStreamReader fileReader = new InputStreamReader(resource);
      StringBuilder stringBuilder = new StringBuilder();

      int readChar = 0;

      while ((readChar = fileReader.read()) != -1) {
        if ((char) readChar == '<') {
          int validator = fileReader.read();
          if ((char) validator == '%') {
            // method.
            StringBuilder methodName = new StringBuilder();
            while ((readChar = fileReader.read()) != -1) {
              char readed = (char) readChar;
              if (readed == '%') {
                // end method.
                int endTag = fileReader.read();
                if (endTag == -1) {
                  fileReader.close();
                  Logger.e("HtmlViewParser",
                      "Error parsing htmlFile:" + htmlViewInstance.getViewFileName() + ", wrong sintax");
                } else {
                  if ((char) endTag == '>') {
                    // end tag
                    String method = methodName.toString().trim();
                    Pattern pattern = Pattern.compile("([a-zA-Z]+)\\(([a-zA-Z0-9]*)\\)");
                    Matcher matcher = pattern.matcher(method);
                    if (matcher.matches() && matcher.groupCount() >= 2) {
                      String result = (String) htmlViewInstance.getClass().getMethod(matcher.group(1))
                          .invoke(htmlViewInstance);
                      stringBuilder.append(result);
                    }
                    break;
                  } else {
                    methodName.append('%').append((char) endTag);
                  }
                }
              }
              methodName.append(readed);
            }
            if (readChar == -1) {
              fileReader.close();
            }
            continue;
          } else {
            stringBuilder.append('<').append((char) validator);
            continue;
          }
        } else {
          stringBuilder.append((char) readChar);
        }
      }

      fileReader.close();
      return stringBuilder.toString();

    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException e) {
      Logger.e("HtmlViewParser", e.getMessage());
    } catch (IOException e2) {
      Logger.e("HtmlViewParser", e2.getMessage());
    }
    return "";
  }

}