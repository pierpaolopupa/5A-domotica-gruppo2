package gruppodue;

import java.io.BufferedReader;
import java.util.Scanner;
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
      Scanner in = new Scanner(System.in);
      int scelta;
      int tipo_contatto, tipo_movimento;
      String zona_contatto, zona_movimento;
      boolean valore_contatto, valore_movimento;
      String ora_movimento;
      // serverOutput.writeBytes(
      //   gestoreJSON.generaDati(
      //     "temperatura", 
      //     36.0, 
      //     false, 
      //     "", 
      //     ""
      //   ) + "\n"
      // );
      
      while(true) {
    	  System.out.println("______0=fine"+"\t"+"1=temperatura"+"\t"+"2=contatto"+"\t"+"3=movimento______");
    	  System.out.println();
    	  do {
    		  System.out.println("Cosa vuoi rilevare?: ");
    		  scelta = in.nextInt();
    	  } while(scelta < 0 || scelta > 3);
    	  switch(scelta) {
    	  //case 1 da fare
    	  case 2:
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
    		    break;
    	  case 3:
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
    		break;
    		//case 0 da fare
    	  }
    	  serverOutput.flush();
          System.out.println(serverInput.readLine());
      }
    }

      
      /*serverOutput.writeBytes(
        gestoreJSON.generaDati(
          "contatto", 
          0, 
          true, 
          "Cucina", 
          ""
        ) + "\n"
      );*/

      // serverOutput.writeBytes(
      //   gestoreJSON.generaDati(
      //     "movimento", 
      //     0, 
      //     true, 
      //     "Giardino", 
      //     "11:05"
      //   ) + "\n"
      // );
    catch (Exception e) {
      System.err.println("errore dal client --> " + e.getMessage());
    }
  }
  public static void main(String[] args) {
    Client client = new Client(); 
    client.comunica();
  }
}
