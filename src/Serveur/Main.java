package Serveur;


import objet.BDD;


public class Main {

    public static void main(String[] args) {
	    try {
		//	System.out.println("Veuillez rentrez le chemin absolue du dossier contenant tout les fichiers de la BDD : ");
		//	Scanner sc = new Scanner(System.in);
		//	String choix = (sc.nextLine());
			BDD bd = new BDD("/Users/Mokeight/Desktop/JavaDocuments/");
			
			//Chargement de la base de donnée
			bd.chargementDesLocataires();
			bd.chargementDesLoueurs();
			bd.chargementDesAppartements(bd.getLesLoueurs(), bd.getLesLocataires());
			bd.chargementDesLocations();
			//Warning car not use
			ServeurMaitre SM = new ServeurMaitre(bd);
	    	
	    } catch (Exception e) {
			e.printStackTrace();
		}
    
    }
}