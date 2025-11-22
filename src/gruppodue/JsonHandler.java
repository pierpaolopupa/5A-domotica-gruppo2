package gruppodue;

import java.util.ArrayList;
import org.json.JSONObject;

public class JsonHandler {
  /**
   * @param tipo Chi ha inviato i dati.
   * @param valore Che valore hanno i dati (decimale se temperatura).
   * @param zona In che zona sono stati rilevati (nessuna se temperatura).
   * @param ora A che ora sono stati rilevati (nessuna se temperatura).
   * @return JSON con i dati inviati.
   */
  public String creaDatiJson(final String valore, final String zona, final String ora) {
    // Invio un JSON con struttura uguale per tutti per render il codice piu' leggibile.
    JSONObject jo = new JSONObject();
    jo.put("valore", valore);
    jo.put("zona", zona);
    jo.put("ora", ora);
    return jo.toString();
  }
}
