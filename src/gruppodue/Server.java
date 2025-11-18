package gruppodue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	@SuppressWarnings("resource")
  public static void main(String[] args) {
    try {
      ServerSocket server = new ServerSocket(1234);
      System.out.println("server in ascolto...");
      while (true) {
        Socket client = server.accept();
        System.out.println("accettata la connessione con --> " + client);
        ServerThread thread = new ServerThread(client);
        thread.start();
      }
    }
    catch (IOException e) {
      System.err.println("errore dal server --> " + e.getMessage());
    }
	}
}
