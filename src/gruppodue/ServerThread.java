package gruppodue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONObject;

public class ServerThread extends Thread {
  private Socket client;
  private BufferedReader clientInput;
  private DataOutputStream clientOutput;
  private final int MAX_TEMPERATURA = 35;
  private final Log logger = new Log();
  /**
   * @param _client Il thread accettato durante l'ascolto del server (Guarda Server.java per dettagli).
   * @throws IOException
   */
  public ServerThread(Socket _client) throws IOException {
    this.client = _client;
    this.clientInput = new BufferedReader(
      new InputStreamReader(this.client.getInputStream())
    );
    this.clientOutput = new DataOutputStream(this.client.getOutputStream());
  }
  @Override
  public void run() {
    try { 
      this.comunica();
      this.client.close(); 
    } 
    catch (Exception e) {
      try {
        logger.logMessage("ERRORE", e.getMessage(), null);
      } 
      catch (IOException e1) { e1.printStackTrace(); }
    }
  }
  private void comunica() throws Exception {
    logger.logMessage("INFO", "Comunicazione iniziata con --> " + getName(), null); 
    // Il ciclo va fino a quando il client non vuole uscire
    boolean restare = true;
    while (restare) {
      // Se il client esce senza inviare risposta
      final String riga = this.clientInput.readLine();
      if (riga == null) {
        logger.logMessage("INFO", "Connessione chiusa dal client", null);
        break;
      }
      // Prendo e controllo la risposta con i dati del client
      JSONObject rispostaJson;
      try { rispostaJson = new JSONObject(riga); } 
      catch (Exception parseEx) {
        logger.logMessage("ERRORE", "JSON non valido", this.clientOutput);
        continue;
      }
      // Controllo il tipo della risposta e rispondo in base al valore
      switch (rispostaJson.getString("tipo")) {
        case "contatto" -> {
          boolean contatto = Boolean.parseBoolean(rispostaJson.getString("valore").trim());
          if (contatto) {
            logger.logMessage(
              "AVVISO", 
              "Rilevato contatto in zona --> " + rispostaJson.getString("zona"), 
              this.clientOutput
            );
          }
          else
            logger.logMessage("INFO", "Nessun contatto rilevato", this.clientOutput);
        }
        case "movimento"-> {
          boolean movimento = Boolean.parseBoolean(rispostaJson.getString("valore").trim());
          if (movimento) {
            logger.logMessage(
              "AVVISO", 
              "Rilevato movimento in zona --> " + rispostaJson.getString("zona") +
              " all'ora --> " + rispostaJson.getString("ora"), 
              this.clientOutput
            );
          }
          else
            logger.logMessage("INFO", "Nessun movimento rilevato",this.clientOutput);
        }
        case "temperatura" -> {
          // Prendo e controllo la temperatura convertendola 
          try {
            double temp = Double.parseDouble(rispostaJson.getString("valore").trim());
            if (temp > this.MAX_TEMPERATURA) {
              logger.logMessage(
                "ALLARME", 
                "Temperatura sopra la soglia massima! (" + this.MAX_TEMPERATURA + " Gradi)", 
                this.clientOutput
              );
            }
            else
              logger.logMessage("INFO", "Temperatura ok", this.clientOutput);
          } 
          catch (NumberFormatException ex) {
            logger.logMessage("ERRORE", "Valore non valido", this.clientOutput);
          }
        }
        case "exit" -> {  
          restare = false;
          logger.logMessage("INFO", "Chiusura comunicazione richiesta", this.clientOutput);
        }
        default -> {
          logger.logMessage("ERRORE", "Tipo di richiesta inesistente", this.clientOutput);
        }
      }
    }
    logger.logMessage(
      "INFO", 
      "Comunicazione terminata con --> " + getName(), 
      null
    ); 
  }
}
