package gruppodue;

import java.io.DataOutputStream;
import java.io.IOException;

public class Log {
  /**
   * @param type Il tipo di log (INFO, AVVISO, ALLARME).
   * @param message Il messaggio da loggare.
   * @param stream La fonte a cui inviare il log.
   * @throws IOException
   */
  public void logMessage(String type, String message, DataOutputStream stream) throws IOException {
    final String log = "(" + type + "): " + message + ".\n";
    if (stream != null)
      stream.writeBytes(log);
    else
      System.out.println(log);
  }
}
