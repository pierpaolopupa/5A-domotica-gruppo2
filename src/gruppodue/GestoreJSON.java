package gruppodue;

import java.util.ArrayList;
import org.json.JSONObject;

// TODO: Risolvere errore ID che non incrementa in prendiUltimoID() e refactor codice generale
public class GestoreJSON {
  private ArrayList<JSONObject> cronologiaJson;
  public GestoreJSON() {
    this.cronologiaJson = new ArrayList<JSONObject>();
  }
  private String prendiUltimoID() {
    if (this.cronologiaJson.isEmpty())
      return "0";
    return Integer.toString(
      Integer.parseInt(
        this.cronologiaJson.get(this.cronologiaJson.size() - 1).getString("ID")
      ) + 1
    );
  }
  public String generaDati(final String tipo, final double valoreDouble, 
    final boolean valoreBool, final String zona, final String ora
  ) {
    JSONObject json = new JSONObject();
    json.put("ID", this.prendiUltimoID());
    switch (tipo.toLowerCase()) {
      case "contatto":
        json.put("tipo", "contatto");
        json.put("valore", Boolean.toString(valoreBool));
        json.put("zona", zona);
        break;
      case "movimento":
        json.put("tipo", "movimento");
        json.put("valore", Boolean.toString(valoreBool));
        json.put("zona", zona);
        json.put("ora", ora);
        break;
      case "temperatura":
        json.put("tipo", "temperatura");
        json.put("valore", Double.toString(valoreDouble));
        break;
      default:
        return "";
    }
    this.cronologiaJson.add(json);
    return json.toString();
  }
}
