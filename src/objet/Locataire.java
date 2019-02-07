package objet;

public class Locataire extends Personne{

	
	int prixDuLoyer;
	private int type = 1;
	public Locataire(String nom, String prenom,String email, Appartement lieu, int id){
		super(nom, prenom,email, id);
		
		this.prixDuLoyer = lieu.getLoyer();
	}
	public Locataire (String nom, String prenom,String mail, int id){
		super(nom,prenom,mail,id);
		this.lieu = null;
		this.prixDuLoyer = 0;
	}
	public int getType() {
		return type;
	}
	public int getPrixDuLoyer() {
		return prixDuLoyer;
	}

}
