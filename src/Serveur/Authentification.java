package Serveur;

import java.net.*;
import java.util.Scanner;

import objet.BDD;
import objet.Locataire;
import objet.Loueur;

import java.io.*;

public class Authentification implements Runnable {

	private Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String login = "zero", pass =  null;
	public boolean authentifier = false;
	public Thread t2;
	private BDD bd;
	private static String chemin;
	public String id = null;
	private static boolean Admin = false;
	//permet de choisir le fichier de la bdd , si c'est false alors locataire, sinon Loueur
	private boolean idDansLaClass = false;
	//Permet de créer l'instance du gugus qui est connecter
	private Loueur leLoueur= null;
	private Locataire leLocataire =null;
	/**
	 * <b> Constructeur de la classe Authentification</b>
	 * 
	 * @param s, bd
	 * 		
	 * */
	public Authentification(Socket s, BDD bd){
		 socket = s;
		 this.bd = bd;
		 Authentification.chemin = bd.getChemin();
		}
	public void run() {
	
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			if (socket.getLocalPort() == 1999){
				while(AdminAuthentifier(in)==false){
				
				}
			}else{
				out.println("Voulez vous vous connecter en tant que Loueur, Locataire ou simplement faire une recherche ?");
				out.flush();
				id = in.readLine();
				if (id.equals("Locataire") || id.equals("locataire")){
					while(isAuthentifier(in,idDansLaClass)==false){
						
					}
				}
				if (id.equals("Loueur") || id.equals("loueur")){
					idDansLaClass = true;
					while(isAuthentifier(in,idDansLaClass)==false){
						
					}
				}
				else {
					//dans ce cas la il n'est pas connecter et ne fait que visiter le site
					out.println("connecte 3");
					out.flush();
				}
			}
			try {
				if(Admin == false){
					//peut être passer un flag a ServeurEsclave pour qu'il sache si Loueur, Locataire ou simplement recherche ? 
					ServeurEsclave esclave = new ServeurEsclave(socket, bd,leLoueur,leLocataire);
					t2 = new Thread(esclave);
					t2.start();
				}
				if(Admin == true){
					ServeurAdminSlave esclave = new ServeurAdminSlave(socket, bd);
					t2 = new Thread(esclave);
					t2.start();	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}catch (IOException e) {
			System.err.println(login+" ne répond pas !");
		}
	}
	/**
	 * <b> Permet l'authentification de l'administrateur</b>
	 * 
	 * @param in
	 * 		
	 * */
	private boolean AdminAuthentifier(BufferedReader in) throws IOException {
		out.println("Bienvenue Admin, veuillez confirmer votre connexion ? ");
		out.flush();
		if (in.readLine().contains("oui") || in.readLine().contains("Oui")){
			while(!authentifier){
				out.println("Entrez votre login (adresse email) :");
				out.flush();
				login = in.readLine();
				out.println("Entrez votre mot de passe :");
				out.flush();
				pass = in.readLine();
				if(isAdmin(login, pass)){
						out.println("connecte 2");
						out.flush();
						authentifier = true;
				}
				else {out.println("erreur"); out.flush();}
			 }
		}
		return authentifier;
	}
	/**
	 * <b> Permet l'authentification des clients</b>
	 * 
	 * @param in,idDansLaClass
	 * 		
	 * */
	private boolean isAuthentifier(BufferedReader in, boolean idDansLaClass) throws IOException{
		while(!authentifier){
			out.println("Entrez votre login (adresse email) :");
			out.flush();
			login = in.readLine();
			out.println("Entrez votre mot de passe :");
			out.flush();
			pass = in.readLine();

			if(isValid(login, pass, idDansLaClass)){
				if (id.equals("Locataire") || id.equals("locataire")){
					leLocataire = rechercherLocataire(login,bd);
					out.println("connecte 0");
					out.flush();
					authentifier = true;
				}
				if (id.equals("Loueur") || id.equals("loueur")){
					out.println("connecte 1");
					leLoueur = rechercherLoueur(login,bd);
					out.flush();
					authentifier = true;
				}	
			}
			else {out.println("erreur"); out.flush();}
		 }
		return authentifier;
	}
	/**
	 * <b> Permet le test des logins et mot de passe dans un fichier pour l'admin</b>
	 * 
	 * @param login, pass
	 * 		
	 * */
	private static boolean isAdmin(String login, String pass) {
		boolean connexion = false;
		System.out.println("login" +login +  " pass" + pass);
		try {
			Scanner sc = new Scanner(new File(chemin +"idAdmin.txt"));	
			while(sc.hasNext()){
				if(sc.nextLine().equals(login+" "+pass)){
              	  connexion=true;
              	  Admin = true;
				  break;
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {	
			System.err.println("Le fichier n'existe pas !");
		}
		return connexion;
		
	}	
	/**
	 * <b> Permet le test des logins et mot de passe dans un fichier pour les clients</b>
	 * 
	 * @param login, pass ,id
	 * 		
	 * */
	private static boolean isValid(String login, String pass, boolean id) {
		boolean connexion = false;
		try {
			if (id == true){
				Scanner sc = new Scanner(new File(chemin + "idLoueur.txt"));	
				while(sc.hasNext()){
					if(sc.nextLine().equals(login+" "+pass)){
	              	  connexion=true;
					  break;
					}
	             }
				sc.close();
			}
			if (id == false){
				Scanner sc = new Scanner(new File(chemin + "idLocataire.txt"));	
				while(sc.hasNext()){
					if(sc.nextLine().equals(login+" "+pass)){
	              	  connexion=true;
					  break;
					}
	             }
				sc.close();
			}
		} catch (FileNotFoundException e) {	
			System.err.println("Le fichier n'existe pas !");
		}
	return connexion;
		
	}
	/**
	 * <b> Permet d'identifier le locataire dans la base de donner</b>
	 * 
	 * @param login,bd
	 * 		
	 * */
	private Locataire rechercherLocataire(String login, BDD bd){
		for (int i=0; i<bd.getLesLocataires().size();i++){
			if (bd.getLesLocataires().get(i).getEmail().equals(login)){
				return bd.getLesLocataires().get(i);
			}
		}
		return null;
	}
	
	/**
	 * <b> Permet d'identifier le loueur dans la base de donnée</b>
	 * 
	 * @param login,bd
	 * 		
	 * */
	private Loueur rechercherLoueur(String login, BDD bd){
		for (int i=0; i<bd.getLesLoueurs().size();i++){
			if (bd.getLesLoueurs().get(i).getEmail().equals(login)){
				return bd.getLesLoueurs().get(i);
			}
		}
		return null;
	}
}