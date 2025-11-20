package gruppodue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private final Log logger = new Log();
  public void start() throws IOException {
    try (ServerSocket server = new ServerSocket(1234)) {
      // Va avanti fino a quando non viene fermato il server dal terminale
      while (true) {
        final Socket client = server.accept();
        logger.logMessage("INFO", "Server in ascolto...", null);
        ServerThread thread = new ServerThread(client);
        logger.logMessage("INFO", "Connessione stabilita con --> " + thread.getName(), null);
        thread.start();
      }
    } 
    catch (Exception e) {  
      logger.logMessage("ERRORE", e.getMessage(), null);
      System.exit(1);
    }
  }
  public static void main(String[] args) throws IOException {
    final Server server = new Server();
    server.start();
  }
}
