package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import Exception.CodePostaleException;
import objet.Paquet;
/**
 * <b>Classe RequeteClient (Runnable) du client.</b>
 * <p>
 * Demarre quand le client s'est connecté au serveur permet et s'est authenfier
 * de l'utilisateur (On cherche a savoir qui veut se connecté)
 * </p>
 * 
 * @see RequeteClient
 * @see Connection
 * 
 * @author Guillaume
 * @author Maxime
 * @version 3.0
 */
public class RequeteClient implements Runnable {
	private Socket socket; // Objet permettant de gÃ©rer la connexion avec le serveur
	private OutputStream out; // Permet de gÃ©rer le flux sortant
	private InputStream in; // Permet de gÃ©rer le flux entrant
	private BufferedReader input;
	private BufferedWriter output;
	private boolean	test;
	private int connexion;
	/* Constructeur: met en place le dialogue (envoie/rÃ©ception) avec le serveur */
	public RequeteClient(Socket socket, int a) throws Exception {
		this.socket = socket; // demande une connexion Ã  localhost sur le port 4568
		this.out = socket.getOutputStream(); // rÃ©cupÃšre flux sortant de la connexion
		this.in = socket.getInputStream(); // rÃ©cupÃšre flux entrant de la connexion
		this.input = new BufferedReader(new InputStreamReader(in));
		this.output = new BufferedWriter(new OutputStreamWriter(out));
		this.test = true;
		this.connexion = a;
		/* Lance son propre thread */
		Thread thread = new Thread(this);
		thread.start();
		Requete();
	}
	/**
	 * <b> Methode de la classe Requete </b>
	 * <p>
	 * Gere les requetes du cote client en fonction l'utilisateur connecté propose des choix de requetes.
	 * </p>
	 * @throws IOException
	 * 
	 * @throws CodePostaleException
	 * 			Exception sur le code postale.
	 */
	public void Requete() throws IOException, CodePostaleException {

		/* Boucle infinie qui envoie au serveur les messages Ã©crits dans le terminal */
		while (test) {
			Scanner sc = new Scanner(System.in);
			//cas Visiteur
			if (connexion == 3){
				System.out.println("Voulez vous voir la liste de nos bien ? "
									+ "Quitter ?");
				String laString = (sc.nextLine());
				if(laString.contains("voir") || laString.contains("oui")){
					demandeRequete();
				}
				if(laString.contains("Quitter") || laString.contains("quitter")){
					System.out.println("A la prochaine  !");
					socket.close();
				}
			}
			
			//cas admin
			if (connexion == 2){
				System.out.println("Que voulez vous faire admin ? :"
									+ " Supprimer ? "
									+ " Quitter ?");
				String laString = (sc.nextLine());
				if(laString.contains("Supprimer") || laString.contains("supprimer") || laString.contains("suppr")){
					SuppressionAdmin();
				}
				if(laString.contains("Quitter") || laString.contains("quitter")){
					System.out.println("A la prochaine  !");
					socket.close();
				}
			}
			//cas Loueur
			if (connexion == 1 ){
				System.out.println("Voulez vous voir  : \n "
									+ "la liste de nos bien ?\n"
									+" la liste de vos bien ?\n"
									+ " ou creer une demande ? ");
				String laString = (sc.nextLine());
				if(laString.contains("voir") || laString.contains("liste")){
					//cas de demande de voir mes appartements
					if(laString.contains("mes")){
						demandeRequeteMoi();
					}
					else{
						demandeRequete();
					}
				}
				else if (laString.contains("creer") || laString.contains("Creer")){
					creerRequete(0);
				}
			}
			//cas Locataire
			if (connexion == 0){
				System.out.println("Voulez vous :"
									+ "voir la liste de nos bien"
									+ "louer ? "
									+ "voir votre appartement");
				String laString = (sc.nextLine());
				if(laString.contains("voir")){
					demandeRequete();
				}
				else{
					creerRequete(1);
				}
			}
			//Fin de la demande Client
			System.out.println("Avez vous une autre requete ?");
			String testString = (sc.nextLine());
			if (!(testString.contains("oui"))){
				test = false;
				System.out.println("A la prochaine  !");
				/*
				 * Pensez a fermer la connexion ici !
				 */
				socket.close();
			}
		}
		
	}	
	/**
	 * <b> Methode de la classe creerRequete </b>
	 * <p>
	 * creer un message de requete a envoyer au serveur en fonction des données saisi par l'utilisateur
	 * </p>
	 * @throws IOException
	 * 
	 * @throws CodePostaleException
	 * 			Exception sur le code postale.
	 */
	public void creerRequete(int demandeDeLoc) throws IOException, CodePostaleException{
		Paquet paquet  = new Paquet();
		//Loueur
		if (connexion == 1){
			if(demandeDeLoc == 0){
				ArrayList<String> str = new ArrayList<String> ();
				str.add("Creer");
				Scanner sc = new Scanner(System.in);
				System.out.println("Veuillez rentrer les informations de votre nouvel appart :");
				System.out.println("Type : ( Chambre, Duplex, Loft ou Autre)");
				str.add(sc.nextLine());
				System.out.println("Veuillez saisir un nombre de pieces :");
				str.add(sc.nextLine());
				System.out.println("Veuillez saisir un prix :");
				str.add(sc.nextLine());
				System.out.println("Veuillez saisir une adresse :");
				String tempoadresse = sc.nextLine();
				tempoadresse = tempoadresse.replaceAll("\\s",",");
				str.add(tempoadresse);
				System.out.println("Veuillez saisir un codePostal :");
				String cp  = sc.nextLine();
				while(String.valueOf(cp).length() != 5){
					System.out.println("Veuillez saisir un codePostal VALIDE ( 5 chiffres) :");
					cp  = sc.nextLine();
				}
				str.add(cp);
				String info = paquet.encodeCreerToString(str);
				output.write(info);
				output.newLine();
				output.flush();
			}
			//alors c'est un Loueur qui veut Louer un appart !! 
			if (demandeDeLoc == 1 ){
				int id = 0;
				//Peut etre voir au niveau de Serveur esclave si l'appart est déjà loué  et joué avec la variable in.readLine() qui permet de get la valeur print par ServeurEsclave
				output.write("ListeLocation");
				output.newLine();
				output.flush();
				Scanner sc = new Scanner(System.in);
				System.out.println("Quel Appartement voulez vous louer ? ( Rentrez y donc son numéro )");
				String test2 = sc.nextLine();
				try{
					 id = Integer.parseInt(test2);
				}catch(NumberFormatException e){ System.out.println("Ce n'est pas un ID ");}
				if ( id != 0){
					System.out.println(test2);
					test2 = "LouerLoueur " + id;
					
					output.write(test2);
					output.newLine();
					output.flush();
				}
			}
		}
		//Locataire
		if (connexion == 0){
			int id = 0;
			//Peut etre voir au niveau de Serveur esclave si l'appart est déjà loué  et joué avec la variable in.readLine() qui permet de get la valeur print par ServeurEsclave
			output.write("ListeLocation");
			output.newLine();
			output.flush();
			Scanner sc = new Scanner(System.in);
			System.out.println("Quel Appartement voulez vous louer ? ( Rentrez y donc son numéro )");
			String test = sc.nextLine();
			try{
				 id = Integer.parseInt(test);
			}catch(NumberFormatException e){ System.out.println("Ce n'est pas un ID ");}
			if ( id != 0){
				System.out.println(test);
				test = "Louer " + id;
				
				output.write(test);
				output.newLine();
				output.flush();
			}
			
		}
		
	}
	/**
	 * <b> Methode de la classe creerRequete </b>
	 * <p>
	 * creer un message de requete Admin a envoyer au serveur en fonction des données saisi.
	 * </p>
	 * @throws IOException
	 * 
	 */
	public void SuppressionAdmin() throws IOException{
		int id = 0;
		//rentrer info
		Scanner sc = new Scanner(System.in);
		System.out.println("Voulez vous : \n"
							+ "Supprimer un appartement?\n"
							+ "Supprimer une location ?");
		String choix = sc.nextLine();
		if(choix.contains("appartement") || choix.contains("Appartement") || choix.contains("appart") || choix.contains("Appart")){
			//demande de vision de tout les apparts
			choix = "Voir ToutAdmin";
			output.write(choix);
			output.newLine();
			output.flush();
			System.out.println("Quel Appartement voulez vous supprimer ? ( Rentrez y donc son numéro )");
			String test = sc.nextLine();
			try{
				 id = Integer.parseInt(test);
			}catch(NumberFormatException e){ System.out.println("Ce n'est pas un ID ");}
			if ( id != 0){
				//System.out.println(test);
				test = "SupprimerAdmin " + id;
				
				output.write(test);
				output.newLine();
				output.flush();
			}
		}	
		if(choix.contains("location") || choix.contains("Location") || choix.contains("Loc") || choix.contains("loc")){
			//demande de vision de tout les apparts
			choix = "Voir ToutLocationAdmin";
			output.write(choix);
			output.newLine();
			output.flush();
			System.out.println("Quel Location voulez vous supprimer ? ( Rentrez y donc son numéro )");
			String test = sc.nextLine();
			try{
				 id = Integer.parseInt(test);
			}catch(NumberFormatException e){ System.out.println("Ce n'est pas un ID ");}
			if ( id != 0){
				//System.out.println(test);
				test = "SupprimerAdminLoc " + id;
				
				output.write(test);
				output.newLine();
				output.flush();
			}
		}
		
	}
	public void demandeRequeteMoi() throws IOException{
		int id = 0;
		//rentrer info
		Scanner sc = new Scanner(System.in);
		System.out.println("Voulez vous voir: \n"
							+ "Votre appartement ?"
							+ "la liste de vos Locataires ?\n"
							+ "la liste de vos Locations ?\n"
							+ "supprimer l'une de vos Locations ?");
		String choix = sc.nextLine();
		if(choix.contains("mon appartement") || choix.contains("Mon appartement") || choix.equals("Mon Appartement")){
			choix = "Voir Moi";
			output.write(choix);
			output.newLine();
			output.flush();
		}
		if(choix.contains("Locataires") || choix.contains("Locataire") || choix.contains("locataire") || choix.contains("Locataire")){
			choix = "Voir MaListeLocataires";
			output.write(choix);
			output.newLine();
			output.flush();
		}
		if(choix.contains("supprimer") || choix.contains("Supprimer")){
			choix = "Voir MaListeLocations";
			output.write(choix);
			output.newLine();
			output.flush();
			System.out.println("Quel Appartement voulez vous supprimer ? ( Rentrez y donc son numéro )");
			String test = sc.nextLine();
			try{
				 id = Integer.parseInt(test);
			}catch(NumberFormatException e){ System.out.println("Ce n'est pas un ID ");}
			if ( id != 0){
			//	System.out.println(test);
				test = "Supprimer " + id;
				
				output.write(test);
				output.newLine();
				output.flush();
			}
		}
		if(choix.contains("Locations") || choix.contains("Location") || choix.contains("locations") || choix.contains("location")){
			choix = "Voir MaListeLocations";
			output.write(choix);
			output.newLine();
			output.flush();
		}

	}
	public void demandeRequete() throws IOException{
			//on doit faire tout les test si les type a l'interieur des tapes sont faux
			//String message = cmd.nextLine();
			ArrayList<String> str = new ArrayList<String>();
			Paquet paquet = new Paquet();
			//rentrer info
			Scanner sc = new Scanner(System.in);
			System.out.println("Voulez vous voir "
					+ "toute la liste ou une recherche avancée ?"
					+ "votre appartement ?");
			String choix = sc.nextLine();
			if(choix.contains("toute")){
				choix = "Voir Tout";
				output.write(choix);
				output.newLine();
				output.flush();
			}
			else if(choix.contains("mon appartement") || choix.contains("Mon appartement") || choix.equals("Mon Appartement") || choix.equals("appartement") || choix.equals("Appartement")){
				choix = "Voir Moi";
				output.write(choix);
				output.newLine();
				output.flush();
			}
			else{
				str.add("Voir");
				System.out.println("Veuillez saisir un appart :");
				str.add(sc.nextLine());
			//	System.out.println("Vous avez saisi : " + str);
				System.out.println("Veuillez saisir un nombre de pieces :");
				str.add(sc.nextLine());
				System.out.println("Veuillez saisir un prix :");
				str.add(sc.nextLine());
				//Message test = new Message("Loft", 4, 1000);
				System.out.println(str);
				
				//permet d'encoder notre entrée 
				paquet.encode(str);
				//on la décode pour la passer dans le write();
				String information = paquet.toString();
				System.out.println(information);
				output.write(information);
				output.newLine();
				output.flush();
			}
	   
	}
	/* Thread: boucle infinie qui rÃ©cupÃšre les messages du serveur */
	public void run() {
		String message = null;
		boolean receive = false;
		try {
			/* A chaque message reÃ§u par le buffer 'input',on affiche le message dans la console */
			while ((message = input.readLine()) != null) {
				System.out.println(message);
				receive = true;
			}
			/*
			 * Permet de lancer une n-ieme requete
			 */
			if(receive == true){
				Requete();
			}
		} catch (IOException e) {} catch (CodePostaleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}