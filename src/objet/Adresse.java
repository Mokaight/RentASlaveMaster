package objet;

import java.util.ArrayList;
import Exception.CodePostaleException;

public class Adresse {
	String rue;
	int codePostale;
	/**
	 * <b> Constructeur de la classe Adresse</b>
	 * 
	 * @param ArrayList r, int cp
	 * 		
	 * */
	public Adresse(ArrayList<String> r, int cp) throws CodePostaleException{
		if(String.valueOf(cp).length() != 5){
			throw new CodePostaleException();
		}else{
			this.rue=String.join(" ", r);
			this.codePostale=cp;
		}
	}
	/**
	 * <b> Constructeur de la classe Adresse</b>
	 * 
	 * @param String a, int cp
	 * 		
	 * */
	public Adresse(String a ,int cp) throws CodePostaleException{
		if(String.valueOf(cp).length() != 5){
			throw new CodePostaleException();
		}else{
			this.rue = a;
			this.codePostale=cp;
		}
	}
	public String toString(){
		return rue  + "Codepostale : "+codePostale;
	}
	public String getRue() {
		return rue;
	}
	public int getCodePostale() {
		return codePostale;
	}
}