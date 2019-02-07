package objet;

import java.util.Calendar;
import java.util.Date;

public class Location {

	String leLoueur;
	String leLocataire;
	int IDappart;
	long dateDebut;
	long dateFin;
	public Location(String emailLoueur, String emailLocataire,int IDappart, long deb, long fin ) {
		this.leLoueur = emailLoueur;
		this.leLocataire = emailLocataire;
		this.IDappart = IDappart;
		this.dateDebut = deb;
		this.dateFin = fin;
		// TODO Auto-generated constructor stub
	}

	public String getLeLoueur() {
		return leLoueur;
	}

	public void setLeLoueur(String leLoueur) {
		this.leLoueur = leLoueur;
	}

	public String getLeLocataire() {
		return leLocataire;
	}

	public void setLeLocataire(String leLocataire) {
		this.leLocataire = leLocataire;
	}

	public int getIDappart() {
		return IDappart;
	}

	public void setIDappart(int iDappart) {
		IDappart = iDappart;
	}


    public long getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(long dateDebut) {
		this.dateDebut = dateDebut;
	}

	public long getDateFin() {
		return dateFin;
	}

	public void setDateFin(long dateFin) {
		this.dateFin = dateFin;
	}

	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

}
