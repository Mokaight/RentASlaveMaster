package Exception;


/**
 * <b> Classe CodePostalException : </b>
 * <p>
 * Permet de gerer les exception lié a un code postal incorrecte
 * </p>
 * 
 * @author Guillaume
 * @author Maxime
 * @version 3.0
 */
public class CodePostaleException extends Exception{ 
	
	private static final long serialVersionUID = 1L;

		public CodePostaleException(){

		    System.out.println("Vous essayez d'instancier une classe Adresse avec un code Postale au mauvais format !");

		  }  
}