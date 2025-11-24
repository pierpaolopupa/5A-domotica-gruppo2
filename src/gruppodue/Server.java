package gruppodue;

import java.net.ServerSocket;

public class Server {
  private final Logger logger = new Logger();
  /**
   * Avvia il server, ascolta per potenziali connessioni e se le trova crea un nuovo thread per quest'ultime.
   */
  private void start() {
    try (ServerSocket server = new ServerSocket(1234)) {
      while (true) {
        logger.log(LivelloLog.INFO, "Server in ascolto...", null);
        final ServerThread thread = new ServerThread(server.accept());
        logger.log(LivelloLog.INFO, "Connessione accettata con: " + thread.getName(), null);
        thread.start();
      }
    }
    catch (Exception ex) {
      System.err.println("Errore durante l'esecuzone di start() (Server.java): " + ex.getMessage());
    }
  }
  public static void main(String[] args) {
    final Server server = new Server();
    server.start();
  }
}
