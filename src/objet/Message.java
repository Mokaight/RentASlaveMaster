package objet;
import java.util.ArrayList;
import java.util.Scanner;
public class Message {
	private static ArrayList<String> str;
	private String Message;
	private int nombre;
	private int prix;
	public Message(String a, int b, int c) {
		this.Message = a;
		this.nombre = b;
		this.prix = c;
	}
	
	@Override
	public String toString() {
		//séparer par des espaces pour la fonction split
		return Message +" "+nombre + " " + prix;
	}

	public static void main (String[] argc){
		str = new ArrayList<String>();
		Paquet paquet = new Paquet();
		//rentrer info
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir un appart :");
		str.add(sc.nextLine());
		System.out.println("Vous avez saisi : " + str);
		System.out.println("Veuillez saisir un nombre de pieces :");
		str.add(sc.nextLine());
		System.out.println("Veuillez saisir un prix :");
		str.add(sc.nextLine());
		//Message test = new Message("Loft", 4, 1000);
		System.out.println(str);
		paquet.encode(str);
		//test d'affichage du paquet
		System.out.println("Affichage du paquet \n");
		paquet.afficherEncode(paquet);
		
		//décodage du paquet
		System.out.println("Affichage du decodage du paquet \n");
		String info = paquet.toString();
		System.out.println(info);
		sc.close();
	}
}