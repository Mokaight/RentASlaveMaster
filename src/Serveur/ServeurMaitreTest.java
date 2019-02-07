package Serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import objet.BDD;

/**
 * Classe principale coté serveur
 * On ne doit pas trop y toucher je pense 
 */
public class ServeurMaitreTest implements Runnable{
	
	
	private int PORT_SERVEUR = 6969;
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private BDD bd;
	/**
	 * <b> Constructeur de la classe ServeurMaitreTest</b>
	 * 
	 * @param bd
	 * 		
	 * */
	public ServeurMaitreTest(BDD bd) throws Exception{
		this.bd = bd;
		pool = Executors.newFixedThreadPool(10);
	}
	@Override
	/**
	 * <b> Lance l'authentification par un appel au Thread</b>
	 * 
	 * @param 
	 * 		
	 * */
	public void run() {
		try {
			serverSocket = new ServerSocket(PORT_SERVEUR);
		//	pool = Executors.newFixedThreadPool(10); //TODO: Pas sur que ca soit utile *2

		while(true){
			Socket socket = serverSocket.accept(); 

			Authentification authen = new Authentification(socket, bd);
			pool.execute(authen);

		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
   }
}