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
  private final Logger logger = new Logger();
  /**
   * @param _client Il client accettato dal server (Guarda Server.java)
   */
  public ServerThread(final Socket _client) { 
    this.client = _client;
    try {
      this.clientInput = new BufferedReader(new InputStreamReader(this.client.getInputStream())); 
      this.clientOutput = new DataOutputStream(this.client.getOutputStream());
    }
    catch (IOException ex) {
      System.err.println("Errore durante il costruttore del thread (ServerThread.java): " + ex.getMessage());
    }
  }
  public void comunica() throws Exception {
    logger.log(LivelloLog.INFO, "Comunicazione iniziata con: " + this.client, null);
    boolean condizione = true;
    while (condizione) {
      String rispostaClient = this.clientInput.readLine(); // Il client legge il menu e risponde.
      switch (rispostaClient.trim()) {
        case "0" -> { condizione = false; }
        case "1" -> { 
          final Double temp = Double.parseDouble(new JSONObject(this.clientInput.readLine()).getString("valore"));
          if (temp < this.MAX_TEMPERATURA)
            logger.log(LivelloLog.INFO, "Temperatura ok!", this.clientOutput);
          else
            logger.log(LivelloLog.ALLARME, "Temperatura sopra la soglia massima!", this.clientOutput);
        }
        case "2" -> {
          final JSONObject jo = new JSONObject(this.clientInput.readLine());
          if (!Boolean.parseBoolean(jo.getString("valore")))
            logger.log(LivelloLog.INFO, "Nessun movimento rilevato", this.clientOutput);
          else {
            logger.log(
              LivelloLog.AVVISO, 
              "Movimento rilevato in zona: " + jo.getString("zona") + ", all'ora: " + jo.getString("ora"), 
              this.clientOutput
            );
          }
        }
        case "3" -> {
          final JSONObject jo = new JSONObject(this.clientInput.readLine());
          if (!Boolean.parseBoolean(jo.getString("valore")))
            logger.log(LivelloLog.INFO, "Nessun contatto rilevato", this.clientOutput);
          else {
            logger.log(
              LivelloLog.AVVISO, 
              "Contatto rilevato in zona: " + jo.getString("zona"),
              this.clientOutput
            );
          }
        }
      }
    }
    this.clientOutput.flush();
    this.client.close();
  }
  @Override
  public void run() {
    try { 
      this.comunica();
      this.client.close();
    }
    catch (Exception ex) {
      System.err.println("Errore durante l'esecuzione di run (ServerThread.java): " + ex.getMessage());
    }
  }
}
