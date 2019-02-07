package Client;

import java.net.*;
import java.util.Scanner;
import java.io.*;
/**
 * <b>Classe Connexion/Authentification (Runnable) du client.</b>
 * <p>
 * Demarre quand le client s'est connecté au serveur permet l'authenfication 
 * de l'utilisateur (On cherche a savoir qui veut se connecté)
 * </p>
 * 
 * @see run
 * @see Connection
 * 
 * @author Guillaume
 * @author Maxime
 * @version 3.0
 */
public class Connexion implements Runnable {

    private Socket socket = null;
    public static Thread t2;
    public static String login = null, pass = null, connexion = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Scanner sc = null;
    private boolean connect = false;
    private String[] lesSplits;
    
	/**
	 * <b> Constructeur de la classe Connexion</b>
	 * 
	 * @param s
	 * 		La socket de la connexion 
	 */
    public Connexion(Socket s){
        socket = s;
    }
    
    public void run() {
        try {
	        out = new PrintWriter(socket.getOutputStream());
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));    
	        sc = new Scanner(System.in);
	        System.out.println(in.readLine());
	        connexion = sc.nextLine();
	        out.println(connexion);
	        out.flush();
	        if (connexion.equals("Autre") || connexion.equals("autre")){
	        	String temperary = in.readLine();
	        	if(temperary.contains("connecte")){
		        	lesSplits = temperary.split(" ");
			        connect = true;
			    }
	        }
	        else{
		        while(!connect ){
			        System.out.println(in.readLine());
			        login = sc.nextLine();
			        out.println(login);
			        out.flush();
			        System.out.println(in.readLine());
			        pass = sc.nextLine();
			        out.println(pass);
			        out.flush();
			        String temperary = in.readLine();
			        if(temperary.contains("connecte")){
			        	lesSplits = temperary.split(" ");
				        connect = true;
				    }
			        else {
			            System.err.println("Vos informations sont incorrectes "); 
			          }
			    }
	        }
	        try {
				t2 = new Thread(new RequeteClient(socket, Integer.parseInt(lesSplits[1])));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        t2.start();
        } catch (IOException e) {
            System.err.println("Le serveur ne repond plus...");

        }

    }


}