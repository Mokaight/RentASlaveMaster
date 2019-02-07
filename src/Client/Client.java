package Client;


import java.net.Socket;
import java.util.Scanner;

import javax.net.ssl.SSLSocketFactory;

/**
 * <b>Classe principale du coté Client.</b>
 * <p>
 * Gere une connexion au serveur Maitre qui lui attribura un esclave.
 * C'est l'un des 2 classes a lancer.
 * </p>
 * 
 * @see Client
 * 
 * @author Guillaume
 * @author Maxime
 * @version 3.0
 */
public class Client {

	private Socket socket; // Objet permettant de gÃ©rer la connexion avec le serveur

	/* Main */
	public static void main(String[] args) throws Exception {
		new Client(); //création d'un client
	}
	/**
	 * <p> Constructeur de la classe Client
	 * 
	 * @throws Exception
	 */
	public Client() throws Exception {
		
		System.out.println("Voulez vous être sécurisé ou non ? ");
		Scanner sc = new Scanner(System.in);
		String choix = (sc.nextLine());
		if (choix.contains("oui") || choix.contains("Oui")){
			System.setProperty("javax.net.ssl.trustStore", "Fichier_Certif");
			this.socket = ((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket("localhost", 5000);
		}
		if (choix.contains("Admin") || choix.contains("admin")){
			System.setProperty("javax.net.ssl.trustStore", "Fichier_Certif");
			this.socket = ((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket("localhost", 1999);
		
		}
		else{
			this.socket = new Socket("localhost", 6969); // demande une connexion Ã  localhost sur le port 4568
		}
		
		Thread thread = new Thread(new Connexion(socket));
		thread.start();
		
	}
}