package gruppodue;

import org.json.JSONObject;

public class JsonHandler {
  /**
   * @param valore Che valore hanno i dati.
   * @param zona In che zona sono stati rilevati.
   * @param ora A che ora sono stati rilevati.
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
