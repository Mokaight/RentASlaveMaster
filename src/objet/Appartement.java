package objet;

public class Appartement {
	Adresse adr;
	int nbPiece;
	int loyer;
	Loueur leLoueur;
	Personne leLocataire;
	Type type;
	int louerOuNon; // 0 pour libre / 1 pour louer
	int id;
	/**
	 * <b> Constructeur de la classe Appartement</b>
	 * 
	 * @param adresse, nbpiece,loyer,loueur,type,id
	 * 		
	 * */
	public Appartement(Adresse adresse, int nbpiece, int loyer, Loueur loueur , Type type, int id) throws Exception{
		if ((nbpiece <= 0) || (loyer <= 0)){
			throw new Exception("Le loyer ou le nombre de pièce n'est pas valide");
		}
		else{
			this.adr = adresse;
			this.nbPiece =nbpiece;
			this.loyer = loyer;
			this.leLoueur = loueur;
			this.type = type;
			this.louerOuNon = 0;
			this.id = id;
			this.leLocataire = null;
		}
	}

	public int getId(){
		return id;
	}
	public String toString() {
		
		 return "Le " + id + " contient " + nbPiece + "pièces  et coute " + loyer + "euros par mois";
			
		
		}
	public Adresse getAdr() {
		return adr;
	}

	public void setAdr(Adresse adr) {
		this.adr = adr;
	}

	public int getNbPiece() {
		return nbPiece;
	}

	public void setNbPiece(int nbPiece) {
		this.nbPiece = nbPiece;
	}

	public int getLoyer() {
		return loyer;
	}

	public void setLoyer(int loyer) {
		this.loyer = loyer;
	}

	public Loueur getLeLoueur() {
		return leLoueur;
	}

	public void setLeLoueur(Loueur leLoueur) {
		this.leLoueur = leLoueur;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getLouerOuNon() {
		return louerOuNon;
	}

	public void setLouerOuNon(int louerOuNon) {
		this.louerOuNon = louerOuNon;
	}

	public Personne getLeLocataire() {
		return leLocataire;
	}

	public void setLeLocataire(Personne leLocataire) {
		this.leLocataire = leLocataire;
	}
	
	
}