package Serveur;

import java.net.Socket;
import java.util.ArrayList;
import objet.BDD;
import objet.Locataire;
import objet.Loueur;
import objet.Type;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Classe cotÃ© serveur permettant de gÃ©rer le dialogue avec un client
 */
public class ServeurEsclave implements Runnable {

	@SuppressWarnings("unused")
	private Socket socket; 
	private OutputStream out; 
	private InputStream in; 
	private BufferedReader input;
	private BufferedWriter output;
	private BDD laBase;
	private Loueur leLoueur = null;
	private Locataire leLocataire = null;
	/**
	 * <b> Constructeur de la classe ServeurEsclave</b>
	 * 
	 * @param bd
	 * 		
	 * */
	public ServeurEsclave(Socket socket, BDD bd,Loueur leLoueur, Locataire leLocataire) throws Exception {
		this.socket = socket;
		this.out = socket.getOutputStream();
		this.in = socket.getInputStream();
		this.input = new BufferedReader(new InputStreamReader(in));
		this.output = new BufferedWriter(new OutputStreamWriter(out));
		this.laBase = bd;
		this.leLocataire = leLocataire;
		this.leLoueur = leLoueur;
		envoyerMessage("Vous etes connecté");
	}

	/**
	 * <b> Envoie une  message au client</b>
	 * 
	 * @param  message
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
	/**
	 * <b> Permet de traiter la requete du client par l'utilisation de TraitementRequete.java</b>
	 * 
	 * @param
	 * 		
	 * */
	public void run() {
		try {
			String message = null;
			System.out.println("Thread lancé");
			while ((message = input.readLine()) != null) {

				String[] lesSplits = new String[4];
				lesSplits = message.split(" ");
				if (lesSplits[0].equals("Creer") && leLoueur != null){
					creerNewAppart(lesSplits);
				}

				if (lesSplits[0].equals("Creer") && leLoueur == null){
					envoyerMessage("Vous n'etes pas Loueur pour faire cette action");
				}
				if (lesSplits[0].equals("Louer") && leLocataire != null){
					louerNewAppart(lesSplits[1]);
				}
				if(lesSplits[0].equals("LouerLoueur") && leLoueur !=null){
					louerNewAppartLoueur(lesSplits[1]);
				}
				if (lesSplits[0].equals("ListeLocation") && leLocataire !=null){
					envoyerMessage("Voici la liste des appartements libre : \n");
					envoyerListeMessage(new TraitementRequete(laBase).ListeAppartLibre());
				}
				if(lesSplits[0].equals("Supprimer") && leLoueur !=null){
					envoyerMessage(new TraitementRequete(laBase).supprimerAppartementLoueur(leLoueur,lesSplits[1]));
				}
				if(lesSplits[0].equals("SupprimerAdmin")){
					envoyerMessage(new TraitementRequete(laBase).supprimerAppartementAdmin(lesSplits[1]));
				}
				if(lesSplits[0].equals("SupprimerAdminLoc")){
					envoyerMessage(new TraitementRequete(laBase).supprimerLocationByAdmin(lesSplits[1]));
				}
				else if (lesSplits[0].equals("Voir")){
					
					if(!lesSplits[1].isEmpty()){
						Type t;
						if(lesSplits[1].equals("MaListeLocataires") && leLoueur != null){
							envoyerListeMessage(new TraitementRequete(laBase).ListeMesLocataire(leLoueur));		
						}
						else if(lesSplits[1].equals("MaListeLocations") && leLoueur != null){
							envoyerListeMessage(new TraitementRequete(laBase).ListeMesLocations(leLoueur));		
						}
						else if(lesSplits[1].equals("Tout")){
							envoyerListeMessage(new TraitementRequete(laBase).ListeAppartLibre());
						}
						else if(lesSplits[1].equals("ToutAdmin")){
							envoyerListeMessage(new TraitementRequete(laBase).ListeAppart());
						}
						else if(lesSplits[1].equals("ToutLocationAdmin")){
							envoyerListeMessage(new TraitementRequete(laBase).ListeLocation());
						}
						else if(lesSplits[1].equals("Moi")){
							if(leLoueur != null && leLocataire == null){
								envoyerMessage(leLoueur.getAppart().toString());
							}
							if(leLoueur == null && leLocataire != null){
								envoyerMessage(leLocataire.getLieu().toString());
							}
							
						}
						else if(Type.TypeExist(lesSplits[1])){ 
							t=Type.StringtoType(lesSplits[1]);
							if(!lesSplits[2].isEmpty()){
								if(!lesSplits[3].isEmpty()){
									try {
										envoyerMessage("Les "+t.toString()+"s au prix de "+lesSplits[3]+" avec "+lesSplits[2]+" piece(s) disponnibles sont : ");
										int p = Integer.parseInt(lesSplits[2]);
										int l = Integer.parseInt(lesSplits[3]);
										envoyerListeMessage(new TraitementRequete(laBase).ListeAppartLibreType(t,p,l));
									} catch (Exception e) {
										e.printStackTrace();
									}
								}else{
									try {
										envoyerMessage("Les "+t.toString()+"s possedant "+lesSplits[2]+" piece(s) disponnibles sont : ");
										int p = Integer.parseInt(lesSplits[2]);
										envoyerListeMessage(new TraitementRequete(laBase).ListeAppartLibreType(t,p));
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}else{
								try {
									envoyerMessage("Les "+t.toString()+"s disponnibles sont : ");
									envoyerListeMessage(new TraitementRequete(laBase).ListeAppartLibreType(t));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}else{
							envoyerMessage("Le type renseigné n'existe pas...");	
						}
					}else{
						envoyerMessage("Aucun type renseigné");
					}
				}
				}	
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * <b> Lance une requete pour la location d'un appartement de la part d'un Loueur</b>
	 * 
	 * @param id
	 * 		
	 * */
	private void louerNewAppartLoueur(String id) throws Exception {
		TraitementRequete tR = new TraitementRequete(laBase);
		int test = tR.creerLocation(id, leLoueur);
		if(test == 0){
			envoyerMessage("L'appartement demandé est malheuresement déjà loué");
		}else if(test ==1){
			envoyerMessage("Félicitation, vous avez un nouvel appartement !");
		}else if (test == 2){
			envoyerMessage("Cette appartement n'existe pas dans notre base !");
		}else if (test == 3){
			envoyerMessage("Vous ne pouvez pas louer votre propre appartement !");
		}
	
	}
	/**
	 * <b> Lance une requete pour la location d'un appartement de la part d'un Locataire</b>
	 * 
	 * @param id
	 * 		
	 * */
	private void louerNewAppart(String id) throws Exception {
		TraitementRequete tR = new TraitementRequete(laBase);
		int test = tR.creerLocation(id, leLocataire);
		if(test == 0){
			envoyerMessage("L'appartement demandé est malheuresement déjà loué");
		}else if(test ==1){
			envoyerMessage("Félicitation, vous avez un nouvel appartement !");
		}else if (test == 2){
			envoyerMessage("Cette appartement n'existe pas dans notre base !");
		}
	
	}
	/**
	 * <b> Creer un nouvel appartement pour un Loueur ( à l'aide de TraitementRequete)</b>
	 * 
	 * @param id
	 * 		
	 * */
	private void creerNewAppart(String[] lesSplits) throws Exception {
		TraitementRequete tR = new TraitementRequete(laBase);
		ArrayList<String> str = new ArrayList<String> ();
		for(int i=0; i<lesSplits.length; i++){
			str.add(lesSplits[i]);
		}
		laBase.incrementHighestIDAppart();
		tR.creerAppartement(str, leLoueur, laBase.getHighestIDAppart());
		envoyerMessage("Voila la liste de vos apparts mis a jour");
		envoyerListeMessage(tR.AppartLoueur(leLoueur));
		
	}
}