package objet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Personne {
	protected Appartement lieu;
	protected String nom;
	protected String prenom;
	protected String email;
	protected int id;
	public Personne(String nom, String prenom,String email, int id){
		if (isValidEmail(email)==true){
			this.email = email;
			this.nom = nom;
			this.prenom = prenom;
			this.id = id;
			this.lieu =null;
		}
	}
	public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
        );
  
     public static boolean isValidEmail(String email)
     {
             return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
     }
	public int getId(){
		return id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdentite(){
		return nom + " " + prenom;
	}
	public String toString() {
		return "Son nom : " + nom + "Son prenom :" + prenom + "Son id  : " + id;
	}
	public Appartement getLieu() {
		return lieu;
	}
	public void setLieu(Appartement lieu) {
		this.lieu = lieu;
	}
}