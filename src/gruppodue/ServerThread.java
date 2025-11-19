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
    logger.logMessage(
      "INFO", 
      "Comunicazione iniziata con --> " + getName(), 
      null
    ); 
    boolean uscita = false;
    while (!uscita) {
      logger.logMessage("RICHIESTA", "Ora puoi inserire i dati", this.clientOutput);
      final JSONObject rispostaJson = new JSONObject(this.clientInput.readLine());
      switch (rispostaJson.getString("tipo")) {
        case "contatto" -> {
          if (Boolean.parseBoolean(rispostaJson.getString("valore"))) {
            logger.logMessage(
              "AVVISO", 
              "Rilevato contatto in zona --> " + rispostaJson.getString("zona"), 
              this.clientOutput
            );
          }
        }
        case "movimento"-> {
          if (Boolean.parseBoolean(rispostaJson.getString("valore"))) {
            logger.logMessage(
              "AVVISO", 
              "Rilevato movimento in zona --> " + rispostaJson.getString("zona") +
              "All'ora --> " + rispostaJson.getString("ora"), 
              this.clientOutput
            );
          }
        }
        case "temperatura" -> {
          if (Double.parseDouble(rispostaJson.getString("valore")) > this.MAX_TEMPERATURA) {
            logger.logMessage(
              "ALLARME", 
              "Temperatura sopra la soglia massima! (" + this.MAX_TEMPERATURA + " Gradi)", 
              this.clientOutput
            );
          }
        }
        case "exit" -> {  
          uscita = true;
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
