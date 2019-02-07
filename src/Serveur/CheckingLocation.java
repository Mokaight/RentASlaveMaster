package Serveur;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import objet.Appartement;
import objet.BDD;
import objet.Locataire;
import objet.Loueur;

public class CheckingLocation implements Runnable {
		private BDD bd;
		private Loueur leLoueur = null;
		private Locataire leLocataire = null;
		private Appartement leAppart = null;
		private SendMail mail;
		private Date date;

		public CheckingLocation(BDD bd){
			this.bd = bd;
			Thread thread = new Thread(this);
			thread.start();
		}
		@Override
		public void run() {
			while(true){
				date =new Date();
				mail = new SendMail();
				for (int i=0 ; i< bd.getLesLocations().size();i++){
					if(bd.getLesLocations().get(i).getDateFin() <= date.getTime()){
						/*
						 * l'appart du locataire est a nouveau dispo
						 * l'appart n'est plus loué
						 * le locataire n'a plus d'appartement
						 * un mail est envoyé au Locataire
						 * un mail est envoyé au Loueur
						 */
						for(int j=0;j<bd.getLesLoueurs().size();j++){
							if(bd.getLesLoueurs().get(j).getEmail() == bd.getLesLocations().get(i).getLeLoueur()){
								leLoueur = bd.getLesLoueurs().get(i);
							}
						}
						for(int k=0;k<bd.getLesLocataires().size();k++){
							if(bd.getLesLocataires().get(k).getEmail() == bd.getLesLocations().get(i).getLeLocataire()){
								leLocataire = bd.getLesLocataires().get(i);
							}
						}
						for(int l=0;l<bd.getLesAppartements().size();l++){
							if(bd.getLesAppartements().get(l).getId() == bd.getLesLocations().get(i).getIDappart()){
								leAppart = bd.getLesAppartements().get(l);
							}
						}
						if (leLoueur != null && leLocataire != null && leAppart != null){
							leAppart.setLouerOuNon(0);
							leLocataire.setLieu(null);
							
						}
						try {
							mail.sendMail("administration@Projet.com", "Service Administration", leLocataire.getEmail(),"Suppression de votre appartement ",
									"Bonjour, nous vous informons que votre bail avec l'Appartement :" + leAppart.toString() + "arrive a expiration, vous êtes donc à la rue");
							mail.sendMail("administration@Projet.com", "Service Administration", leLoueur.getEmail(), "Mise sur le marché ",
									"Bonjour, nous vous informons que votre Appartement : " + leAppart.toString() + "va bientot être remis sur le marché !");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					
				}
			}

		}
}
