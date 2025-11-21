package gruppodue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
  private Socket socket;
  private BufferedReader input;
  private BufferedReader serverInput;
  private DataOutputStream serverOutput;
  private final Logger logger = new Logger();
  private final JsonHandler jsonHandler = new JsonHandler();
  private final String menu = 
    """
    0: Esci
    1: Temperatura
    2: Movimento
    3: Contatto
    Scegli: """;
  public Client() {
    try {
      this.socket = new Socket("localhost", 1234);
      this.input = new BufferedReader(new InputStreamReader(System.in));
      this.serverInput = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
      this.serverOutput = new DataOutputStream(this.socket.getOutputStream());
    }
    catch (IOException ex) {
      System.err.println("Errore durante il costruttore del client (Client.java): " + ex.getMessage());
    }
  }
  public void comunica() {
    try {
      boolean condizione = true;
      while (condizione) {
        System.out.print(this.menu);
        String risposta = this.input.readLine();
        this.serverOutput.writeBytes(risposta + "\n");
        switch (risposta.trim()) {
          case "0" -> { condizione = false; }
          case "1" -> {
            logger.log(LivelloLog.RICHIESTA, "Gradi?", null);
            risposta = this.input.readLine();
            this.serverOutput.writeBytes(
              jsonHandler.creaDatiJson(
                risposta, 
                null, 
                null
              ) + "\n"
            );
          }
          case "2" -> {
            String zona = null;
            String ora = null;
            logger.log(LivelloLog.RICHIESTA, "Movimento rilevato? (true/false)", null);
            risposta = this.input.readLine();
            if (Boolean.parseBoolean(risposta)) {
              logger.log(LivelloLog.RICHIESTA, "In che zona?", null);
              zona = this.input.readLine();
              logger.log(LivelloLog.RICHIESTA, "A che ora?", null);
              ora = this.input.readLine();
            }
            this.serverOutput.writeBytes(
              jsonHandler.creaDatiJson(
                risposta, 
                zona, 
                ora
              ) + "\n"
            );
          }
          case "3" -> {
            String zona = null;
            logger.log(LivelloLog.RICHIESTA, "Contatto rilevato? (true/false)", null);
            risposta = this.input.readLine();
            if (Boolean.parseBoolean(risposta)) {
              logger.log(LivelloLog.RICHIESTA, "In che zona?", null);
              zona = this.input.readLine();
            }
            this.serverOutput.writeBytes(
              jsonHandler.creaDatiJson(
                risposta, 
                zona, 
                null
              ) + "\n"
            );
          }
        }
        this.serverOutput.flush();
        System.out.println(this.serverInput.readLine());
      }
      this.socket.close();
    }
    catch (Exception ex) {
      System.err.println("Errore durante l'esecuzione di comunica() (Client.java): " + ex.getMessage());
    }
  }
  public static void main(String[] args) {
    final Client client = new Client();
    client.comunica();
  }
}
