package Serveur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocketFactory;

import objet.BDD;

public class ServeurAdminSSL implements Runnable {
	
	private int PORT_SERVEUR_SSL = 1999;
	private final ExecutorService pool; // Objet permettant de gérer les threads qui sont lancés
	private ServerSocket serveurSocketSSL;
	private BDD bd;

	/**
	 * <b> Constructeur de la classe ServeurAdminSSL</b>
	 * 
	 * @param bd
	 * 		
	 * */
	public ServeurAdminSSL(BDD bd) throws Exception{
		this.bd = bd;
		pool = Executors.newFixedThreadPool(2); // Fixe le nombre de threads en cours (les autres resterons en attente de place)
		System.setProperty("javax.net.ssl.keyStore", "Fichier_Certif");
		System.setProperty("javax.net.ssl.keyStorePassword", "chatchat");
		serveurSocketSSL = ((SSLServerSocketFactory)SSLServerSocketFactory.getDefault()).createServerSocket(PORT_SERVEUR_SSL);
	}
	@Override
	public void run() {
		while(true){
			try{
				// On peut écrire plus simplement: pool.execute(new ServeurEsclave(serverSocket.accept()));
				//Version SSL
				Socket socketSSL = serveurSocketSSL.accept();
				//Version SSL
				Authentification authen2 = new Authentification(socketSSL, bd);
				pool.execute(authen2); // et execute le thread de l'instance (la méthode run() ).
							
			}
			catch (IOException e){

			      System.err.println( "Erreur : " + e );

			 }
		}

	}
}