/**
 * Quick Java Concept
 * 
 * Created by Edwin Marquez.
 * edwinsf.dev
 *
 */

package dev.edwinsf.quickjavaconcept.logger;

public class Logger {

  public enum LoggingLevel {
    NONE,
    ERROR,
    WARNING,
    INFORMATION,
    DEBUG
  }

  private static Logger instance = null;
  private static LoggingLevel internalloggingLevel = LoggingLevel.DEBUG;

  public static Logger getInstance() {
    if (instance == null)
      instance = new Logger();
    return instance;
  }

  public static void setLoggingLevel(LoggingLevel loggingLevel){
    internalloggingLevel = loggingLevel;
  }

  public static void d(String header, String message) {
    int compare = internalloggingLevel.compareTo(LoggingLevel.DEBUG);
    if (compare >= 0) {
      System.out.println(createMessageString(header, message, System.currentTimeMillis()));
    }
  }

  public static void w(String header, String message) {
    int compare = internalloggingLevel.compareTo(LoggingLevel.WARNING);
    if (compare >= 0) {
      System.out.println(createMessageString(header, message, System.currentTimeMillis()));
    }
  }
  
  public static void e(String header, String message) {
    int compare = internalloggingLevel.compareTo(LoggingLevel.ERROR);
    if (compare >= 0) {
      System.out.println(createMessageString(header, message, System.currentTimeMillis()));
    }
  }
  
  public static void i(String header, String message) {
    int compare = internalloggingLevel.compareTo(LoggingLevel.INFORMATION);
    if (compare >= 0) {
      System.out.println(createMessageString(header, message, System.currentTimeMillis()));
    }
  }

  private Logger() {}

  private static String createMessageString(String header, String message, Long timeMillis) {
    return header + " | " + message + " | " + System.currentTimeMillis();
  }
}