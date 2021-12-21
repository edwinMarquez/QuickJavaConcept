/**
 * Quick Java Concept
 * 
 * Created by Edwin Marquez. 
 * edwinsf.dev
 *
 */

package dev.edwinsf.quickjavaconcept;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.edwinsf.quickjavaconcept.logger.Logger;

public class Server {

  private Router router = null;
  private int SERVER_PORT = 4038;
  private int RESPONSE_PORT = 4039;
  private Thread serverThread = null;

  public Server() {
    Router router = new Router();
  }

  public Server(Router router) {
    this.router = router;
  }

  public Server(int serverPort, int responsePort) {
    this.router = new Router();
    this.SERVER_PORT = serverPort;
    this.RESPONSE_PORT = responsePort;
  }

  public Server(int serverPort, int responsePort, Router router) {
    this.router = router;
    this.SERVER_PORT = serverPort;
    this.RESPONSE_PORT = responsePort;
  }

  public void start() {

    try {
      ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
      Logger.d("Server", "server started at: http://127.0.0.1:"+SERVER_PORT);
      while (true) {
        serverThread = new Thread(new ServerProcess(serverSocket));
        serverThread.start();
        try {
          serverThread.join();
        } catch (Exception e) {
          Logger.e("Exception caught at Server when joining thread", e.getMessage());
        }
      }
    } catch (IOException ioe) {
      Logger.e("Server", ioe.getMessage());;
    }

  }

  public void triggerLocalBrowser() {
    try{
      Desktop.getDesktop().browse(new URI("http://127.0.0.1:" + SERVER_PORT));
    } catch (IOException | URISyntaxException ioe) {
      Logger.e("Server", ioe.getMessage());
    }
  }

  private class ServerProcess implements Runnable {

    private ServerSocket serverSocket = null;
    private long startTime = 0L;
    private long lastAccess = 0L;

    public ServerProcess(ServerSocket serverSocket) {
      this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
      try {
        Socket socket = serverSocket.accept();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        startTime = System.currentTimeMillis();
        lastAccess = System.currentTimeMillis();
        StringBuilder clientMessage = new StringBuilder();
        String line = null;
        String GETRoute = null;
        HashMap<String, String> GETparams = new HashMap<>();
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
          if (line.startsWith("GET")) {
            Logger.d("Server", "get line: " + line);
            if (line.length() < 4) {
              break; // invalid line format
            }
            StringBuilder route = new StringBuilder();
            int index = 4;
            char processChar = line.charAt(4);
            do {
              route.append(processChar);
              index++;
              processChar = line.charAt(index);
            } while (processChar != ' ' && processChar != '?');
            if (processChar == '?') {
              Pattern pattern = Pattern.compile("([a-zA-Z0-9%+]+)=([a-zA-Z0-9%+]+)");
              Matcher matcher = pattern.matcher(line.substring(++index));
              while (matcher.find()) {
                GETparams.put(matcher.group(1), URLDecoder.decode(matcher.group(2),StandardCharsets.UTF_8));
              }
            }
            GETRoute = route.toString().trim();
            clientMessage.append(line);
          }
        }

        if(GETRoute.startsWith("/assets/")){
            byte[] asset = Server.class.getResourceAsStream(GETRoute.substring(7)).readAllBytes();
            Logger.d("Server","trying to send image: " + GETRoute.substring(7) + "of size: " + asset.length);
            socket.getOutputStream().write(("HTTP/1.0 200 OK\r\nContent-Length: "+asset.length+"\r\n\r\n").getBytes());
            socket.getOutputStream().write(asset);
            socket.getOutputStream().flush();
            socket.getOutputStream().close();
          printWriter.close();
          socket.close();
          return;
        }

        Class<HtmlView> viewClass = router.resolveRoute(GETRoute);

        if (viewClass == null) {
          // not found to handle in later release
          Logger.e("Server", "GET request: "+GETRoute +" not found on router");
          printWriter.write("HTTP/1.1 200 OK\r\n\r\n");
          printWriter.write("<html><head></head><body><h1>404</h1></body></html>");
          printWriter.flush();
          socket.close();
          return;
        }

        printWriter.write("HTTP/1.1 200 OK\r\n\r\n");
        printWriter.write(HtmlViewParser.parse(viewClass,GETparams));
        printWriter.flush();
        socket.close();
      } catch (IOException ge) {
        Logger.e("Server", ge.getMessage());
        return;
      }

    }
  }

}