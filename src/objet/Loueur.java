package objet;

import java.util.ArrayList;

public class Loueur extends Personne{

	private ArrayList<Appartement> Appart;
	private int Type = 0;
	public Loueur(String nom, String prenom,String email, int id){
		super(nom, prenom,email, id);
		Appart = new ArrayList<Appartement>();
	}
	public int getType(){
		return this.Type;
	}
	public synchronized ArrayList<Appartement> getAppart() {
		return Appart;
	}

	public void setAppart(ArrayList<Appartement> appart) {
		Appart = appart;
	}

	public void addAppart(Appartement appart){
		Appart.add(appart);
	}
	public void deleteAppart(Appartement appart){
		Appart.remove(appart);
	}

}
