package gruppodue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
  final String host = "localhost";
  final int porta = 1234;
  @SuppressWarnings({ "resource", "unused" })
  public void comunica() {
    try {
      Socket socket = new Socket(host, porta);
      DataOutputStream serverOutput = new DataOutputStream(socket.getOutputStream());
      BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      GestoreJSON gestoreJSON = new GestoreJSON();
      String serverRequest = serverInput.readLine();
      // TOGLI IL COMMENTO DA QUELLO CHE VUOI TESTARE

      // serverOutput.writeBytes(
      //   gestoreJSON.generaDati(
      //     "temperatura", 
      //     36.0, 
      //     false, 
      //     "", 
      //     ""
      //   ) + "\n"
      // );

      serverOutput.writeBytes(
        gestoreJSON.generaDati(
          "contatto", 
          0, 
          true, 
          "Cucina", 
          ""
        ) + "\n"
      );

      // serverOutput.writeBytes(
      //   gestoreJSON.generaDati(
      //     "movimento", 
      //     0, 
      //     true, 
      //     "Giardino", 
      //     "11:05"
      //   ) + "\n"
      // );
      serverOutput.flush();
      System.out.println(serverInput.readLine());
    }
    catch (Exception e) {
      System.err.println("errore dal client --> " + e.getMessage());
    }
  }
  public static void main(String[] args) {
    Client client = new Client(); 
    client.comunica();
  }
}
