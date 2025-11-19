package gruppodue;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
  final String host = "localhost";
  final int porta = 1234;
  final Log logger = new Log();
  public void comunica() {
    try {
      Socket socket = new Socket(host, porta);
      DataOutputStream serverOutput = new DataOutputStream(socket.getOutputStream());
      BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      GestoreJSON gestoreJSON = new GestoreJSON();
      System.out.println(serverInput.readLine());
      Scanner in = new Scanner(System.in);
      int scelta;
      int tipo_contatto, tipo_movimento;
      String zona_contatto, zona_movimento;
      boolean valore_contatto, valore_movimento;
      String ora_movimento;
      boolean uscita = false;
      while(!uscita) {
        System.out.print(
          """
          0 = fine
          1 = temperatura
          2 = contatto
          3 = movimento
          """ + "Cosa vuoi rilevare?: "
        );
        scelta = in.nextInt();
    	  switch(scelta) {
          case 0 -> {
            uscita = true;
            serverOutput.writeBytes(
              gestoreJSON.generaDati("exit", 0, false, "", "") + "\n"
            );
          }
          case 1 -> {
            //  Fare caso 1 (non mettere break alla fine perche' lo fa da solo)
          }
          // TODO: Richiedere bene e inviare in modo corretto i dati
          case 2 -> {
            System.out.println("***CONTATTO***");
            System.out.println("Tipo di contatto: ");
            tipo_contatto = in.nextInt();
            System.out.println("Assicurati che sia effettivamente avvenuto un contatto: ");
            valore_contatto = in.nextBoolean();
            System.out.println("Zona in cui è avvenuto il contatto: ");
            zona_contatto = in.next();
            serverOutput.writeBytes(gestoreJSON.generaDati(
                  "contatto",
                  tipo_contatto,
                  valore_contatto,
                  zona_contatto,
                  ""
                ) + "\n"
            );
          }
          // TODO: Richiedere bene e inviare in modo corretto i dati
          case 3 -> {
            System.out.println("***MOVIMENTO***");
            System.out.println("Tipo di movimento: ");
            tipo_movimento = in.nextInt();
            System.out.println("Assicurati che ci sia stato davvero un movimento: ");
            valore_movimento = in.nextBoolean();
            System.out.println("Zona in cui c'è stato il movimento: ");
            zona_movimento = in.next();
            System.out.println("Ora in cui c'è stasto il movimento: ");
            ora_movimento = in.next();
            serverOutput.writeBytes(gestoreJSON.generaDati(
              "movimento",
                tipo_movimento,
                valore_movimento,
                zona_movimento,
                ora_movimento
              ) + "\n"
            );
          }
        } 
        System.out.println(serverInput.readLine());
      }
      in.close();
      socket.close();
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
