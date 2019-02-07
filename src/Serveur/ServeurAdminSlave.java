package Serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import objet.BDD;

public class ServeurAdminSlave implements Runnable {
	private Socket socket; 
	private OutputStream out; 
	private InputStream in; 
	private BufferedReader input;
	private BufferedWriter output;
	private BDD laBase;
	
	/**
	 * <b> Constructeur de la class ServeurAdminSlave</b>
	 * 
	 * @param socket,bd
	 * 		
	 * */
	public ServeurAdminSlave(Socket socket, BDD bd) throws Exception {
		this.setSocket(socket);
		this.out = socket.getOutputStream();
		this.in = socket.getInputStream();
		this.input = new BufferedReader(new InputStreamReader(in));
		this.output = new BufferedWriter(new OutputStreamWriter(out));
		this.laBase = bd;
		envoyerMessage("Vous êtes connecté Admin");
	}
	/**
	 * <b> Envoie un message au client</b>
	 * 
	 * @param message
	 * 		
	 * */
	public void envoyerMessage(String message) throws Exception {
		output.write(message);
		output.newLine();
		output.flush();
	}

	/**
	 * <b> Envoie une liste de message au client</b>
	 * 
	 * @param ArrayList message
	 * 		
	 * */
	public void envoyerListeMessage (ArrayList<String> message) throws Exception {
		for(int i = 0 ; i < message.size() ; i ++){
			output.write(message.get(i));
			output.newLine();
			output.flush();
		}
	}

	public void run() {
		String message = null;
		System.out.println("Thread admin lancé");
		try {
			while ((message = input.readLine()) != null) {
				String[] lesSplits = new String[4];
				lesSplits = message.split(" ");
				if(lesSplits[0].equals("SupprimerAdmin")){
					envoyerMessage(new TraitementRequete(laBase).supprimerAppartementAdmin(lesSplits[1]));
				}
				if(lesSplits[0].equals("SupprimerAdminLoc")){
					envoyerMessage(new TraitementRequete(laBase).supprimerLocationByAdmin(lesSplits[1]));
				}
				else if (lesSplits[0].equals("Voir")){
					if(!lesSplits[1].isEmpty()){
						if(lesSplits[1].equals("ToutAdmin")){
							envoyerListeMessage(new TraitementRequete(laBase).ListeAppart());
						}
					}
					else if(lesSplits[1].equals("ToutLocationAdmin")){
						envoyerListeMessage(new TraitementRequete(laBase).ListeLocation());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}

	private void setSocket(Socket socket) {
		this.socket = socket;
	}
}