package gruppodue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.json.JSONObject;

public class ServerThread extends Thread {
  Socket client;
  DataOutputStream clientOutput;
  final int MAX_TEMPERATURA = 35;
  public ServerThread(Socket _client) {
    this.client = _client;
    try { this.clientOutput = new DataOutputStream(this.client.getOutputStream()); }
    catch (IOException e) { e.printStackTrace(); }
  }
  /*
   * Mostra un messaggio al client in base a vari parametri.
   */
  public void logMessage(String type, String ID, String message) {
    try { 
      this.clientOutput.writeBytes(
        "[ID Richiesta]: " + ID +
        "\n[" + type + "]: " + message + "\n"
      ); 
    }
    catch (IOException e) { e.printStackTrace(); }
  }
  /**
   * Metodo dei Thread...
   */
  @Override
  public void run() {
    try { 
      this.comunica();
      this.client.close();
      this.clientOutput.close(); 
    }
    catch (Exception e) { e.printStackTrace(); }
  }
  /**
   * Cominca con il client una volta stabilita la connessione.
   */
  public void comunica() throws Exception {
    BufferedReader clientInput = new BufferedReader(
      new InputStreamReader(this.client.getInputStream())
    );
    boolean canContinue = true;
    while (canContinue) {
      // Il server quì accetta i dati dall'utente
      this.logMessage("INFO", "-", "Inserisci i dati JSON: ");
      String answer = clientInput.readLine();
      if (answer == null) // In caso di errore nella risposta non succede niente
        continue;
      JSONObject json = new JSONObject(answer);
      final String jsonId = json.getString("ID");
      switch (json.getString("tipo")) {
        case "contatto":
          if (!Boolean.parseBoolean(json.getString("valore"))) // Se non c'e' contatto esci.
            break;
          this.logMessage(
            "AVVISO", 
            jsonId, 
            "Rilevato contatto in zona: '" + json.getString("zona") + "'"
          );
          break;
        case "movimento":
          if (!Boolean.parseBoolean(json.getString("valore"))) // Se non c'e' movimento esci.
            break;
          this.logMessage(
            "AVVISO", 
            jsonId,
            "Rilevato movimento in zona: '" + json.getString("zona") +
            "' all'ora: " + json.getString("ora")
          );
          break;
        case "temperatura":
          // Controllo della temperatura
          if (Double.parseDouble(json.getString("valore")) <= MAX_TEMPERATURA)
            break;  
          this.logMessage(
            "ALLARME", 
            jsonId, 
            "Temperatura superiore a: " + json.getString("valore")
          );
          break;
        case "exit": // Il client verrrà chiuso automaticamente dopo.
          canContinue = false;
          break;
        default:
          this.logMessage("ERRORE", jsonId, "il tipo specificato non esiste.");
          break;
      }
    }
  }
}
