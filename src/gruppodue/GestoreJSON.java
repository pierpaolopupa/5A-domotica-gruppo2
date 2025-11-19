package gruppodue;

import java.util.ArrayList;

import org.json.JSONObject;

public class GestoreJSON {
  private ArrayList<JSONObject> richiesteJson = new ArrayList<JSONObject>();
  private int creaNuovoId() {
    if (this.richiesteJson.isEmpty())
      return 0;
    return Integer.parseInt(
      this.richiesteJson.get(
        this.richiesteJson.size() - 1).getString("ID")
    ) + 1; 
  }
  /**
   * @param tipo Chi sta inviando? (contatto, movimento, temperatura).
   * @param valoreDouble Il valore per la temperatura.
   * @param valoreBool Il valore per contatto e movimento.
   * @param zona La zona in cui e' stato rilevato contatto o movimento.
   * @param ora L'ora in cui e' stato rilevato contatto o movimento.
   * @return Un json stringa dei parametri. 
   */
  public String generaDati(String tipo, double valoreDouble, boolean valoreBool, String zona, String ora) {
    JSONObject json = new JSONObject();
    json.put("ID", this.creaNuovoId());
    json.put("tipo", tipo.toLowerCase());
    switch (tipo.toLowerCase()) {
      case "exit" -> {}
      case "contatto" -> {
        json.put("valore", valoreBool);
        json.put("zona", zona);
      }
      case "movimento" -> {
        json.put("valore", valoreBool);
        json.put("zona", zona);
        json.put("ora", ora);
      }
      case "temperatura" -> json.put("valore", valoreDouble);
      default -> {
        return "";
      }
    }
    this.richiesteJson.add(json);
    return json.toString();
  }
}
