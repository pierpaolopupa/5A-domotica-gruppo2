package gruppodue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.json.JSONObject;

// TODO: Refactor generale
public class ServerThread extends Thread {
  Socket client;
  final int MAX_TEMPERATURA = 35;
  public ServerThread(Socket _client) {
    this.client = _client;
  }
  public void logMessage(final String type, final String ID, final String message, final DataOutputStream output) {
    try { output.writeBytes("[" + type + " (ID: " + ID  + ")]: " + message + "\n"); }
    catch (IOException e) { e.printStackTrace(); }
  }
  @Override
  public void run() {
    try { this.comunica(); }
    catch (Exception e) { e.printStackTrace(); }
  }
  public void comunica() throws Exception {
    BufferedReader clientInput = new BufferedReader(
      new InputStreamReader(this.client.getInputStream())
    );
    DataOutputStream clientOutput = new DataOutputStream(this.client.getOutputStream());
    boolean canContinue = true;
    while (canContinue) {
      this.logMessage("INFO", "-", "Inserisci i dati JSON: ", clientOutput);
      String answer = clientInput.readLine();
      if (answer == null)
        break;
      JSONObject json = new JSONObject(answer);
      final String JSON_ID = json.getString("ID");
      switch (json.getString("tipo")) {
        case "contatto":
          if (!Boolean.parseBoolean(json.getString("valore")))
            break;
          this.logMessage("AVVISO", JSON_ID, "Rilevato contatto in zona: '" + json.getString("zona") + "'", clientOutput);
          break;
        case "movimento":
          if (!Boolean.parseBoolean(json.getString("valore")))
            break;
          this.logMessage(
            "AVVISO", 
            JSON_ID,
            "Rilevato movimento in zona: '" + json.getString("zona") +
            "' all'ora: " + json.getString("ora"),
            clientOutput
          );
          break;
        case "temperatura":
          if (Double.parseDouble(json.getString("valore")) > MAX_TEMPERATURA)
            this.logMessage("ALLARME", JSON_ID, "Temperatura superiore a: " + json.getString("valore"), clientOutput);
          break;
        case "exit":
          canContinue = false;
          this.client.close();
          break;
        default:
          this.logMessage("ERRORE", JSON_ID, "il tipo specificato non esiste.", clientOutput);
          break;
      }
    }
  }
}
