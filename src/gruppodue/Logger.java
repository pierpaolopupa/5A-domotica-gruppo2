package gruppodue;

import java.io.DataOutputStream;
import java.io.IOException;

enum LivelloLog {
  INFO,
  AVVISO,
  RICHIESTA,
  ALLARME
}

public class Logger {
  /**
   * @param livello Il tipo di log.
   * @return
   */
  private String convertiLivelloLog(final LivelloLog livello) {
    switch (livello) {
      case INFO -> { return "Info"; }
      case AVVISO -> { return "Avviso"; }
      case RICHIESTA -> { return "Richiesta"; }
      case ALLARME -> { return "Allarme"; }
      default -> { return "Indefinito"; }
    }
  }
  /**
   * @param livello Il tipo di log.
   * @param msg Il contenuto del log.
   * @param stream La stream a cui inviare i dati (se null vengono mostrati nel terminale in cui viene eseguito).
   */
  public void log(final LivelloLog livello, final String msg, final DataOutputStream stream) {
    final String log = 
      "(" + this.convertiLivelloLog(livello) + "): " + msg + "\n";
    if (stream == null)
      System.out.print(log);
    else {
      try { 
        stream.writeBytes(log);
        stream.flush(); 
      }
      catch (IOException ex) {
        System.err.println("Errore durante l'invio del log. (Logger.java): " + ex.getMessage());
      }
    }
  }
}
