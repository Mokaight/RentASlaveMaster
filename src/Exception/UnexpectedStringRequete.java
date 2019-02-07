package Exception;
/**
 * <b> Classe UnexpectedStringRequete : </b>
 * <p>
 * Permet de gerer les exception liee a une saisi non reconnu de l'utilisateur
 * </p>
 * 
 * @author Guillaume
 * @author Maxime
 * @version 3.0
 */
public class UnexpectedStringRequete extends Exception{ 
		
		private static final long serialVersionUID = 1L;

			public UnexpectedStringRequete(String s){
			    System.out.println("Exception UnexpectedStringRequete : "+s+" is incorrect value.\n");
			  }  
}

