package gruppodue;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
  final String host = "localhost";
  final int porta = 1234;
  final Log logger = new Log();
  BufferedReader serverInput;
  private void leggiMessaggiServer() throws IOException {
    String risposta;
    // leggi almeno una riga; poi consuma quelle già pronte
    risposta = serverInput.readLine();
    if (risposta != null) System.out.println(risposta);
    while (serverInput.ready() && (risposta = serverInput.readLine()) != null) {
      System.out.println(risposta);
    }
  }
  @SuppressWarnings("resource")
  public void comunica() throws IOException {
    try {
      Socket socket = new Socket(host, porta);
      DataOutputStream serverOutput = new DataOutputStream(socket.getOutputStream());
      this.serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String valore, zona, ora;
      Scanner input = new Scanner(System.in);
      GestoreJSON gestoreJSON = new GestoreJSON();
      boolean restare = true;
      while(restare) {
        System.out.print(
          """
          0 = fine
          1 = temperatura
          2 = contatto
          3 = movimento
          """ + "Scegli: "
        );
        switch(Integer.parseInt(input.nextLine())) {
          case 0 -> {
            restare = false;
            serverOutput.writeBytes(
              gestoreJSON.generaDati(
                "exit", 
                null, 
                null, 
                null, 
                null
              ) + "\n"
            );
          }
          case 1 -> {
            logger.logMessage("RICHIESTA", "Inserisci il valore", null);
            valore = input.nextLine();
            serverOutput.writeBytes(
              gestoreJSON.generaDati(
                "temperatura",
                valore.trim(),
                null,
                null,
                null
              ) + "\n"
            );
          }
          case 2 -> {
            logger.logMessage("RICHIESTA", "Contatto avvenuto? (true/false)", null);
            valore = input.nextLine();
            logger.logMessage("RICHIESTA", "Zona del contatto?", null);
            zona = input.nextLine();
            serverOutput.writeBytes(
              gestoreJSON.generaDati(
                "contatto",
                null,
                valore.trim(),
                zona,
                null
              ) + "\n"
            );
          }
          case 3 -> {
            logger.logMessage("RICHIESTA", "Movimento avvenuto? (true/false)", null);
            valore = input.nextLine();
            logger.logMessage("RICHIESTA", "Zona del movimento?", null);
            zona = input.nextLine();
            logger.logMessage("RICHIESTA", "Ora del movimento?", null);
            ora = input.nextLine();
            serverOutput.writeBytes(
              gestoreJSON.generaDati(
                "movimento",
                null,
                valore.trim(),
                zona,
                ora
              ) + "\n"
            );
          }
        }
        serverOutput.flush();
        this.leggiMessaggiServer();
      }
      input.close();
      socket.close();
    }
    catch (Exception e) {
      logger.logMessage(
        "ERRORE", 
        e.getMessage(), 
        null
      );
    }
  }
  public static void main(String[] args) throws IOException {
    final Client client = new Client(); 
    client.comunica();
  }
}
