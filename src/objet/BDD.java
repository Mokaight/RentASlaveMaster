package objet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * C'est dans cette class ou on chargera les Loueurs et les locataires
 */
public class BDD {

	ArrayList<Loueur> lesLoueurs;
	ArrayList<Locataire> lesLocataires;
	ArrayList<Appartement> lesAppartements;
	ArrayList<Location> lesLocations;
	String chemin;
	private int HighestIDAppart;
	//private int HighestIDlesLocataires;
	//private int HighestIDlesLoueurs;
	public BDD(String c) {
		lesLoueurs = new ArrayList<Loueur>();
		lesLocataires = new ArrayList<Locataire>();
		lesAppartements = new ArrayList<Appartement> ();
		lesLocations = new ArrayList<Location> ();
		this.chemin =c;
		this.HighestIDAppart = -1;
	}

	public int getHighestIDAppart(ArrayList<Appartement> lesA){
		int a=-1;
		for(int i = 0 ; i < lesA.size();i++){
			if(a <lesA.get(i).getId()){
				a = lesA.get(i).getId();
			}
		}
		return a;
	}
	public String getLeChemin (){
		return chemin;
	}
	public ArrayList<Appartement> getLesAppartements() {
		return lesAppartements;
	}
	public ArrayList<Location> getLesLocations() {
		return lesLocations;
	}
	public ArrayList<Appartement> getlesAppartements(){
		return this.lesAppartements;
	}
	public void incrementHighestIDAppart(){
		this.HighestIDAppart = this.HighestIDAppart+1;
	}
	public int getHighestIDAppart(){
		return HighestIDAppart;
	}
	public synchronized void supprimerAppartement (Appartement ap){
		this.lesAppartements.remove(ap);
	}
	public synchronized void addLoueurs(Loueur l){
		lesLoueurs.add(l);
	}
	public synchronized void addLocataires(Locataire l){
		lesLocataires.add(l);
	}
	public synchronized ArrayList<Loueur> getLesLoueurs() {
		return lesLoueurs;
	}
	public void setLesLoueurs(ArrayList<Loueur> lesLoueurs) {
		this.lesLoueurs = lesLoueurs;
	}
	public ArrayList<Locataire> getLesLocataires() {
		return lesLocataires;
	}
	public void setLesLocataires(ArrayList<Locataire> lesLocataires) {
		this.lesLocataires = lesLocataires;
	}	
	public String getChemin() {
		return chemin;
	}
	
	public void addAppartementFichier(Appartement a){
		try{
			final FileWriter writer = new FileWriter(chemin + "Appartement.txt",true);
				try{
					System.out.println("Rentrer dans e write \n");
					System.out.println(a.getAdr().getRue()+"/"+a.getAdr().getCodePostale()+" "+a.getNbPiece()+" "+a.getLoyer()+" "+a.getLeLoueur().getId()+" "+a.getType().toString()+" "+a.getId());
					writer.write(a.getAdr().getRue()+"/"+a.getAdr().getCodePostale()+" "+a.getNbPiece()+" "+a.getLoyer()+" "+a.getLeLoueur().getId()+" "+a.getType().toString()+" "+a.getId());
				}finally {
					writer.close();
				}
		}catch(IOException e){
			System.out.print("Erreur dans l'ecriture du fichier : \n"+chemin+"\n Exception : "+e);
		}
	}
	

	public void addLoueurFichier(Loueur l){
		try{
			final FileWriter writer = new FileWriter(chemin + "Loueur.txt",true);
			try{
			    writer.write(l.getNom()+" "+l.getPrenom()+" "+l.getEmail()+" "+l.getId());

			}finally {
			    writer.close();
	        }
		}catch(IOException e){
			System.out.print("Erreur dans l'ecriture du fichier : \n"+chemin+"\n Exception : "+e);
		}
	}
	
	public void addLocataireFichier(Locataire l){
		try{
			final FileWriter writer = new FileWriter(chemin + "Locataire.txt",true);
			try{
		    writer.write(l.getNom()+" "+l.getPrenom()+" "+l.getEmail()+" "+l.getId());
			}finally {
			    writer.close();
	        }
		}catch(IOException e){
			System.out.print("Erreur dans l'ecriture du fichier : \n"+chemin+"\n Exception : "+e);
		}
	}
	/*
	 * Le string est le chemin absolue du fichier contenant les loueurs
	 */
	public void chargementDesLoueurs () throws IOException{
		String line;
		String[] lesSplits = new String[4];
		Loueur ll;
		Scanner scanner = new Scanner(new File(chemin + "Loueur.txt"));
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			lesSplits = line.split(" ");
			ll = new Loueur(lesSplits[0],lesSplits[1],lesSplits[2], Integer.parseInt(lesSplits[3]));
			lesLoueurs.add(ll);
		}
		scanner.close();
	}
	
	/* 
	 * Le string est le chemin absolue du fichier contenant les Locataire
	 * 
	 */
	public void chargementDesLocataires () throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(chemin + "Locataire.txt"));
		String line;
		String[] lesSplits = new String[4] ;
		while ((line = br.readLine()) != null) {
			lesSplits = line.split(" ");
			Locataire lo = new Locataire(lesSplits[0],lesSplits[1],lesSplits[2],Integer.parseInt(lesSplits[3]));
			lesLocataires.add(lo);
		}
		br.close();
	}
	public void chargementDesLocations () throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(chemin + "Location.txt"));
		String line;
		String[] lesSplits = new String[4] ;
		while ((line = br.readLine()) != null) {
			lesSplits = line.split(" ");
			Location lo = new Location(lesSplits[0],lesSplits[1],Integer.parseInt(lesSplits[2]),Long.parseLong(lesSplits[3]),Long.parseLong(lesSplits[4]));
			lesLocations.add(lo);
			/////////
			//lesSplit[0] = loueur
			//lesSplits 1 = locataire
			//les Split 2 = ID de l'appart
			//les split 3 = date début
			//les Split 4 = date de fin
			Locataire leLocataire = null;
			Appartement leAppart = null;
			for(int j=0; j<lesLocataires.size(); j++){
				if (lesLocataires.get(j).getEmail() == lesSplits[1]){
					leLocataire = lesLocataires.get(j);
				}
			}
			for(int k = 0; k<lesAppartements.size();k++){
				if (lesAppartements.get(k).getId() == Integer.parseInt(lesSplits[2])){
					leAppart = lesAppartements.get(k);
				}
			}
			if(leLocataire != null && leAppart != null){
				//On Setup l'appart
				leAppart.setLouerOuNon(1);
				leAppart.setLeLocataire(leLocataire);
				//On Setup le Locataire
				leLocataire.setLieu(leAppart);
			}
		}
		br.close();
	}
	/*
	 * La syntaxe des adresses est 2 allée des serres\ 
	 */
	public void chargementDesAppartements (ArrayList<Loueur> Lou, ArrayList<Locataire> Loc) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(chemin + "Appartement.txt"));
		String line;
		String[] lesSplits = new String[4] ;
		String[] Split1 = new String[2];
		Loueur ll = null;
		ArrayList<String> tempo = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			ll = null;
			Split1 = line.split("/");
			String[] adress = Split1[0].split(" ");
			lesSplits = Split1[1].split(" ");
			for (int i = 0 ; i < adress.length ; i ++){
				tempo.add(adress[i]);
			}
			Adresse aDDress  = new Adresse(tempo, Integer.parseInt(lesSplits[0]));
			for (int j = 0 ; j < lesLoueurs.size() ; j ++){
				if(lesLoueurs.get(j).getId() == Integer.parseInt(lesSplits[3])){
					 ll = lesLoueurs.get(j); 
				}
			}
			Appartement AP = new Appartement(aDDress,Integer.parseInt(lesSplits[1]),Integer.parseInt(lesSplits[2]),ll,Type.valueOf(lesSplits[4]), Integer.parseInt(lesSplits[5]));
			lesAppartements.add(AP);
			ll.addAppart(AP);
			
		}
		br.close();
		this.HighestIDAppart = getHighestIDAppart(lesAppartements);
	}

	public void addLocationFichier(Location location) {
		try{
			final FileWriter writer = new FileWriter(chemin + "Location.txt",true);
			try{
		    writer.write(location.getLeLoueur()+" "+location.getLeLocataire()+" "+location.getIDappart()+" "+location.getDateDebut()+" "+location.getDateFin());
			}finally {
			    writer.close();
	        }
		}catch(IOException e){
			System.out.print("Erreur dans l'ecriture du fichier : \n"+chemin+"\n Exception : "+e);
		}
		
	}

}