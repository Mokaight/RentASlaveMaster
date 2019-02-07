package Serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLServerSocketFactory;

import objet.BDD;

public class ServeurMaitreSSL implements Runnable {
	
	private int PORT_SERVEUR_SSL = 5000;
	private final ExecutorService pool; 
	private ServerSocket serveurSocketSSL;
	private BDD bd;
	/**
	 * <b> Constructeur de la classe ServeurMaitreSSL</b>
	 * 
	 * @param bd
	 * 		
	 * */
	public ServeurMaitreSSL(BDD bd) throws Exception{
		this.bd = bd;
		pool = Executors.newFixedThreadPool(10); 
		System.setProperty("javax.net.ssl.keyStore", "Fichier_Certif");
		System.setProperty("javax.net.ssl.keyStorePassword", "chatchat");
		serveurSocketSSL = ((SSLServerSocketFactory)SSLServerSocketFactory.getDefault()).createServerSocket(PORT_SERVEUR_SSL);
	}
	@Override
	/**
	 * <b> Lance l'authentification par un appel au Thread</b>
	 * 
	 * @param 
	 * 		
	 * */
	public void run() {
		while(true){
			try{

				Socket socketSSL = serveurSocketSSL.accept();
				Authentification authen2 = new Authentification(socketSSL, bd);
				pool.execute(authen2); 
							
			}
			catch (IOException e){

			      System.err.println( "Erreur : " + e );

			 }
		}

	}
}