package Serveur;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import Exception.CodePostaleException;
import objet.Adresse;
import objet.Appartement;
import objet.BDD;
import objet.Locataire;
import objet.Location;
import objet.Loueur;
import objet.Type;

public class TraitementRequete {
	private BDD laBase;
	/**
	 * <b> Constructeur de la classe TraitementRequete</b>
	 * 
	 * @param bd
	 * 		
	 * */
	public TraitementRequete(BDD bd) {
		// TODO Auto-generated constructor stub
		this.laBase = bd;
	}
	/**
	 * <b> Affiche la liste des Locations du serveur</b>
	 * 
	 * @param 
	 * 		
	 * */
	public ArrayList<String>ListeLocation(){
		ArrayList<String> Result = new ArrayList<String>();
		Loueur leLoueur = null;
		Locataire leLocataire = null;
		Appartement leAppart = null;
		
		for (int i =0 ; i <laBase.getLesLocations().size(); i++){
			for(int j=0;j<laBase.getLesLoueurs().size();j++){
				if(laBase.getLesLoueurs().get(j).getEmail() == laBase.getLesLocations().get(i).getLeLoueur()){
					leLoueur = laBase.getLesLoueurs().get(i);
				}
			}
			for(int k=0;k<laBase.getLesLocataires().size();k++){
				if(laBase.getLesLocataires().get(k).getEmail() == laBase.getLesLocations().get(i).getLeLocataire()){
					leLocataire = laBase.getLesLocataires().get(i);
				}
			}
			for(int l=0;l<laBase.getLesAppartements().size();l++){
				if(laBase.getLesAppartements().get(l).getId() == laBase.getLesLocations().get(i).getIDappart()){
					leAppart = laBase.getLesAppartements().get(l);
				}
			}
			Result.add(i+") "+leLocataire.getIdentite() +" loue l'appartement :" + leAppart.toString() + "au loueur :" + leLoueur.getIdentite());
		}
		
		return Result; 
	}
	/**
	 * <b> Affiche la liste des appartements Libre (sans locataire ) du serveur</b>
	 * 
	 * @param 
	 * 		
	 * */
	public ArrayList<String> ListeAppartLibre(){
		ArrayList<String> Result = new ArrayList<String>();
		for (int i =0 ; i <laBase.getLesLoueurs().size(); i++){
			for(int j=0; j< laBase.getLesLoueurs().get(i).getAppart().size(); j++){
					if(laBase.getLesLoueurs().get(i).getAppart().get(j).getLouerOuNon() == 0){
						Result.add(laBase.getLesLoueurs().get(i).getAppart().get(j).toString());
					}
				
				
			}
		}
		if (Result.isEmpty()){
			Result.add("Aucun Appartement Disponible");
		}
		return Result;
	}
	/**
	 * <b> Affiche la liste des appartements  du serveur</b>
	 * 
	 * @param 
	 * 		
	 * */
	public ArrayList<String> ListeAppart(){
		ArrayList<String> Result = new ArrayList<String>();
		for (int i =0 ; i <laBase.getLesLoueurs().size(); i++){
			for(int j=0; j< laBase.getLesLoueurs().get(i).getAppart().size(); j++){
					Result.add(laBase.getLesLoueurs().get(i).getAppart().get(j).toString());
				
			}
		}
		if (Result.isEmpty()){
			Result.add("Aucun Appartement Disponible");
		}
		return Result;
	}
	/**
	 * <b> Affiche la liste des appartements par Type et  sans locataire  du serveur</b>
	 * 
	 * @param type
	 * 		
	 * */
	public ArrayList<String> ListeAppartLibreType(Type type){
		ArrayList<String> Result = new ArrayList<String>();
		for (int i =0 ; i <laBase.getLesLoueurs().size(); i++){
			
			for(int j=0; j< laBase.getLesLoueurs().get(i).getAppart().size(); j++){
		
				if (laBase.getLesLoueurs().get(i).getAppart().get(j).getType().equals(type)){
					
					Result.add(laBase.getLesLoueurs().get(i).getAppart().get(j).toString());
				}
			}
		}
		if (Result.isEmpty()){
			Result.add("Aucun Appartement Disponible");
		}
		return Result;
	}
	/**
	 * <b> Affiche la liste des appartements Libre avec un type et un nombre de pièce du serveur</b>
	 * 
	 * @param  type, nbpiece
	 * 		
	 * */
	public ArrayList<String> ListeAppartLibreType(Type type,int p){
		ArrayList<String> Result = new ArrayList<String>();
		for (int i =0 ; i <laBase.getLesLoueurs().size(); i++){
			System.out.println("Boucle1");
			for(int j=0; j< laBase.getLesLoueurs().get(i).getAppart().size(); j++){
				System.out.println("Boucle2");
				if (laBase.getLesLoueurs().get(i).getAppart().get(j).getType().equals(type)){
					System.out.println("dans le if");
					if(laBase.getLesLoueurs().get(i).getAppart().get(j).getNbPiece()==p){
						System.out.println("dans le if2");
							Result.add(laBase.getLesLoueurs().get(i).getAppart().get(j).toString());
					}
				}
			}
		}
		if (Result.isEmpty()){
			Result.add("Aucun Appartement Disponible");
		}
		return Result;
	}
	/**
	 * <b> Affiche la liste des appartements Libre avec un type , un prix  et un loyer du serveur</b>
	 * 
	 * @param type, nbpiece, loyer
	 * 		
	 * */
	public ArrayList<String> ListeAppartLibreType(Type type,int p,int l){
		ArrayList<String> Result = new ArrayList<String>();
		for (int i =0 ; i <laBase.getLesLoueurs().size(); i++){
			System.out.println("Boucle1");
			for(int j=0; j< laBase.getLesLoueurs().get(i).getAppart().size(); j++){
				System.out.println("Boucle2");
				if (laBase.getLesLoueurs().get(i).getAppart().get(j).getType().equals(type)){
					System.out.println("dans le if");
					if(laBase.getLesLoueurs().get(i).getAppart().get(j).getNbPiece()==p){
						System.out.println("dans le if2");
						if(laBase.getLesLoueurs().get(i).getAppart().get(j).getLoyer()<=l){
							System.out.println("dans le if3");
								Result.add(laBase.getLesLoueurs().get(i).getAppart().get(j).toString());
						}
					}
				}
			}
		}
		if (Result.isEmpty()){
			Result.add("Aucun Appartement Disponible");
		}
		return Result;
	}
	/**
	 * <b> Affiche la liste des Loueurs du serveur</b>
	 * 
	 * @param 
	 * 		
	 * */
	public ArrayList<String> ListeLoueur(){
		ArrayList<String> Result = new ArrayList<String>();
		for (int i =0 ; i <laBase.getLesLoueurs().size(); i++){
		//	System.out.println("Boucle1");
			Result.add(laBase.getLesLoueurs().get(i).getIdentite());
				
		}
		return Result;
	}
	/**
	 * <b> Affiche la liste des Locataires du serveur</b>
	 * 
	 * @param 
	 * 		
	 * */
	public ArrayList<String> ListeLocataire(){
		ArrayList<String> Result = new ArrayList<String>();
		for (int i =0 ; i <laBase.getLesLocataires().size(); i++){

			Result.add(laBase.getLesLocataires().get(i).getIdentite());
				
		}
		return Result;
	}
	/**
	 * <b> Affiche l'appartement d'un Loueur </b>
	 * 
	 * @param l
	 * 		
	 * */
	public ArrayList<String> AppartLoueur(Loueur l){
		ArrayList<String> Result = new ArrayList<String>();
		for (int i =0 ; i <l.getAppart().size(); i++){
		//	System.out.println("Boucle1");
			Result.add(l.getAppart().get(i).toString());
				
		}
		return Result;
	}
	/**
	 * <b> Crée un apprtement dans le serveur et l'ajoute aussi a la base de donnée physique</b>
	 * 
	 * @param str, Loueur, id
	 * @throws NumberFormatException, CodePostaleException
	 * 		
	 * */
	public synchronized void creerAppartement(ArrayList<String> str, Loueur leLoueur, int id) throws NumberFormatException, CodePostaleException, Exception {
		
		String tempoadresse = str.get(4).replaceAll(","," ");
	//	System.out.println(tempoadresse + "NUMERO" +  str.get(5));
		Appartement a=new Appartement(new Adresse(tempoadresse, Integer.parseInt(str.get(5))), Integer.parseInt(str.get(2)), Integer.parseInt(str.get(3)),leLoueur , Type.valueOf(str.get(1)),id);
		leLoueur.addAppart(a);
		laBase.addAppartementFichier(a);
		// TODO : ajout a la base ecrit dans le fichier
	}
	/**
	 * <b> Crée une location dans le serveur et l'ajoute aussi a la base de donnée physique</b>
	 * 
	 * @param id, Locataire
	 * 
	 * 		
	 * */
	public synchronized int creerLocation(String id, Locataire leLocataire){
		int idAppart = Integer.parseInt(id);
		for (int i = 0 ; i <laBase.getlesAppartements().size();i++){
			if(laBase.getlesAppartements().get(i).getId() == idAppart){
				if (laBase.getlesAppartements().get(i).getLouerOuNon() == 0){
					//alors on loue l'appart pour le locataire 
					//System.out.println("J'ai trouver l'appart " );
					laBase.getlesAppartements().get(i).setLouerOuNon(1);
					laBase.getlesAppartements().get(i).setLeLocataire(leLocataire);
					envoyerEmailFromLoueurToLocataire(laBase.getlesAppartements().get(i).getLeLoueur(),leLocataire,laBase.getlesAppartements().get(i));
					//ICI ON DOIT AJOUTER UNE LOCATION ( QUAND LE TEMPS SERA VENUE DE S OCCUPER DES LOCATIONS)
					Date date = new Date();
					laBase.addLocationFichier(new Location(laBase.getlesAppartements().get(i).getLeLoueur().getEmail(), leLocataire.getEmail(),laBase.getlesAppartements().get(i).getId(),date.getTime(),Location.addDays(date, 30).getTime()));
					return 1;
				}	
				return 0;
			}
		}
		return 2;
	}
	/**
	 * <b> Crée une location dans le serveur et l'ajoute aussi a la base de donnée physique</b>
	 * 
	 * @param id, Loueur
	 * 
	 * 		
	 * */
	public synchronized int creerLocation(String id, Loueur leLoueur){
		int idAppart = Integer.parseInt(id);
		for (int i = 0 ; i <laBase.getlesAppartements().size();i++){
			if(laBase.getlesAppartements().get(i).getId() == idAppart){
				if (laBase.getlesAppartements().get(i).getLouerOuNon() == 0){
				if (laBase.getlesAppartements().get(i).getLeLoueur() == leLoueur){
					return 3;
				}
				else{
					laBase.getlesAppartements().get(i).setLouerOuNon(1);
					laBase.getlesAppartements().get(i).setLeLocataire(leLoueur);
					envoyerEmailFromLoueurToLoueur(laBase.getlesAppartements().get(i).getLeLoueur(),leLoueur,laBase.getlesAppartements().get(i));
					Date date = new Date();
					laBase.addLocationFichier(new Location(laBase.getlesAppartements().get(i).getLeLoueur().getEmail(), leLoueur.getEmail(),laBase.getlesAppartements().get(i).getId(),date.getTime(),Location.addDays(date, 30).getTime()));
					
					return 1;
				}
				}	
				return 0;
			}
		}
		return 2;
	}
	/**
	 * <b> Envoie un email du Loueur à un Locataire</b>
	 * 
	 * @param Loueur, locataire, appartement
	 * 
	 * 		
	 * */
	public void envoyerEmailFromLoueurToLocataire(Loueur leLoueur, Locataire leLocataire, Appartement Appart){
		SendMail mail = new SendMail();
			try {
				mail.sendMail("administration@Projet.com", "Service Administration", leLoueur.getEmail(), "Location de votre Appartement : " + Appart.toString(),
						"Bonjour, nous vous informons que votre Appartement" 
								+ Appart.toString() + "a été loué par : " 
								+ leLocataire.getIdentite() 
								+ "voici son mail : " 
								+ leLocataire.getEmail()
								+ " \n Cordialement !");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	/**
	 * <b> Envoie un email du Loueur à un autre Loueur considéré ici comme Locataire</b>
	 * 
	 * @param Loueur, Loueur,  appartement
	 * 
	 * 		
	 * */
	public void envoyerEmailFromLoueurToLoueur (Loueur leLoueur1, Loueur leLoueur2, Appartement Appart){
		SendMail mail = new SendMail();
		try {
			mail.sendMail("administration@Projet.com", "Service Administration", leLoueur1.getEmail(), "Location de votre Appartement : " + Appart.toString(),
					"Bonjour, nous vous informons que votre Appartement" 
							+ Appart.toString() + "a été loué par : " 
							+ leLoueur2.getIdentite() 
							+ "voici son mail : " 
							+ leLoueur2.getEmail()
							+ " \n Cordialement !");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <b> Affiche la liste des Locataires d'un Loueur</b>
	 * 
	 * @param Loueur
	 * 
	 * 		
	 * */
	public ArrayList<String> ListeMesLocataire(Loueur leLoueur) {
		ArrayList<String> str = new ArrayList<String> ();
		for (int i = 0 ; i<laBase.getlesAppartements().size(); i++){
			if(laBase.getlesAppartements().get(i).getLeLoueur() == leLoueur && laBase.getlesAppartements().get(i).getLeLocataire() !=null){
				str.add(laBase.getlesAppartements().get(i).getLeLocataire().getIdentite());
			}
		}
		if (str.isEmpty()){
			str.add("Vous n'avez aucune Locataire");
		}
		return str;
	}
	/**
	 * <b> Affiche la liste des Locations d'un Loueur</b>
	 * 
	 * @param Loueur
	 * 
	 * 		
	 * */
	public ArrayList<String> ListeMesLocations(Loueur leLoueur) {
		ArrayList<String> str = new ArrayList<String> ();
		
		for (int i = 0 ; i<laBase.getlesAppartements().size(); i++){
			if(laBase.getlesAppartements().get(i).getLeLoueur() == leLoueur){
				str.add(laBase.getlesAppartements().get(i).toString());
			}
		}
		if (str.isEmpty()){
			str.add("Vous n'avez aucune Location");
		}
		return str;
	}
	/**
	 * <b> Supprime un appartement  d'un Loueur</b>
	 * 
	 * @param Loueur, string
	 * 
	 * 		
	 * */
	public synchronized String supprimerAppartementLoueur(Loueur leLoueur,String string) {
		String Result = null;
		int id = Integer.parseInt(string);
		for (int i = 0 ; i<laBase.getlesAppartements().size(); i++){
			if(laBase.getlesAppartements().get(i).getId() == id){
				if (laBase.getlesAppartements().get(i).getLeLoueur() == leLoueur){
					Result = supprimerAppartement(leLoueur,laBase.getlesAppartements().get(i));
					
				}
			}
		}
		return Result;
	}
	/**
	 * <b> Supprime un appartement  d'un Loueur</b>
	 * 
	 * @param Loueur, appartement
	 * 
	 * 		
	 * */
	public  synchronized String supprimerAppartement(Loueur leLoueur,Appartement Appart){
		//envoyer un mail dans cette fonction ? pour prévenir de la suppression  ? ??
		Loueur leLoueurTemp = null;
		Locataire leLocataireTemp = null;
		//on test aussi dans la liste des Loueurs car un Loueur peut Louer l'appart
		for (int i= 0 ; i<laBase.getLesLoueurs().size() ; i++){
			if(laBase.getLesLoueurs().get(i).getLieu() == Appart){
				laBase.getLesLoueurs().get(i).setLieu(null);
				leLoueurTemp = laBase.getLesLoueurs().get(i);
			}
		}
		//On ne test pas soit l'un soit l'autre car impossible que l'appart soit dans les DEUX 
		//on test dans la liste des Locataires
		for (int i= 0 ; i<laBase.getLesLocataires().size() ; i++){
			if(laBase.getLesLocataires().get(i).getLieu() == Appart){
				laBase.getLesLocataires().get(i).setLieu(null);
				leLocataireTemp = laBase.getLesLocataires().get(i);
			}
		}
		//Envoie de l'email
		if (leLoueur != null){
			SendMail mail = new SendMail();
			if (leLoueurTemp != null){
				try {
					mail.sendMail("administration@Projet.com", "Service Administration", leLoueurTemp.getEmail(), "Suppression de votre appartement ",
							"Bonjour, nous vous informons que votre Appartement" + Appart.toString() + "a été supprimé , vous êtes donc à la rue");
					mail.sendMail("administration@Projet.com", "Service Administration", leLoueur.getEmail(), "Suppression de votre appartement ",
							"Bonjour, nous vous informons que votre Appartement" + Appart.toString() + "a bien été supprimé de la Base de Donnée");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if( leLocataireTemp != null){
				try {
					mail.sendMail("administration@Projet.com", "Service Administration", leLocataireTemp.getEmail(),"Suppression de votre appartement ",
							"Bonjour, nous vous informons que votre Appartement" + Appart.toString() + "a été supprimé , vous êtes donc à la rue");
					mail.sendMail("administration@Projet.com", "Service Administration", leLoueur.getEmail(), "Suppression de votre appartement ",
							"Bonjour, nous vous informons que votre Appartement" + Appart.toString() + "a bien été supprimé de la Base de Donnée");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
		laBase.supprimerAppartement(Appart);
		return " L'appartement " + Appart.toString() + " a bien été supprimer ";
	}
	/**
	 * <b> Supprime un appartement par un admin </b>
	 * 
	 * @param string
	 * 
	 * 		
	 * */
	public String supprimerAppartementAdmin(String string) {
		String Result = null;
		Loueur Loueur = null;
		int id = Integer.parseInt(string);
		for (int i = 0 ; i<laBase.getlesAppartements().size(); i++){
			if(laBase.getlesAppartements().get(i).getId() == id){
					for(int j = 0; j< laBase.getLesLoueurs().size(); j++){
						if (laBase.getLesLoueurs().get(j) == laBase.getlesAppartements().get(i).getLeLoueur()){
							//Envoyer un mail au loueur disant que son appart a été supprimer par un admin ?
							laBase.getLesLoueurs().get(j).deleteAppart(laBase.getlesAppartements().get(i));
							Loueur = laBase.getLesLoueurs().get(j);
						}
					}
					laBase.getlesAppartements().get(i).setLeLoueur(null);
					Result  = supprimerAppartement(Loueur,laBase.getlesAppartements().get(i));
				}
		}
		
		return Result;
	}
	/**
	 * <b> Supprime une location par un Admin</b>
	 * 
	 * @param string
	 * 
	 * 		
	 * */
	public String supprimerLocationByAdmin (String a){
		SendMail mail = new SendMail();
		Locataire leLocataire = null;
		Loueur leLoueur = null;
		Appartement leAppart = null;
		String Return = "Erreur";
		int i = Integer.parseInt(a);
				for(int j=0;j<laBase.getLesLoueurs().size();j++){
					if(laBase.getLesLoueurs().get(j).getEmail() == laBase.getLesLocations().get(i).getLeLoueur()){
						leLoueur = laBase.getLesLoueurs().get(i);
					}
				}
				for(int k=0;k<laBase.getLesLocataires().size();k++){
					if(laBase.getLesLocataires().get(k).getEmail() == laBase.getLesLocations().get(i).getLeLocataire()){
						leLocataire = laBase.getLesLocataires().get(i);
					}
				}
				for(int l=0;l<laBase.getLesAppartements().size();l++){
					if(laBase.getLesAppartements().get(l).getId() == laBase.getLesLocations().get(i).getIDappart()){
						leAppart = laBase.getLesAppartements().get(l);
					}
				}
				if (leLoueur != null && leLocataire != null && leAppart != null){
					leAppart.setLouerOuNon(0);
					leLocataire.setLieu(null);
					Return = "La Location a bien été Supprimer";
					return Return;
				}
				try {
					mail.sendMail("administration@Projet.com", "Service Administration", leLocataire.getEmail(),"Suppression de votre appartement ",
							"Bonjour, nous vous informons que votre bail avec l'Appartement :" + leAppart.toString() + "a été supprimé par l'Admin, vous êtes donc à la rue");
					mail.sendMail("administration@Projet.com", "Service Administration", leLoueur.getEmail(), "Mise sur le marché ",
							"Bonjour, nous vous informons que votre Appartement : " + leAppart.toString() + "va bientot être remis sur le marché après suppression par l'admin !");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Return;
	}
			
		
	
}