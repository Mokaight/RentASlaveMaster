package objet;

import java.util.ArrayList;
import java.util.Arrays;

public class Paquet {

	private String[] partiesMessage;
	//Plus complexe avec plus de parametre
	
	
	
	public Paquet() {
		this.partiesMessage  = new String[4];
	}

	public void encode (ArrayList<String> str){
		//Plus complexe si plus de parametre
		for (int i=0 ; i< str.size() ; i ++)
		{
			partiesMessage[i] = str.get(i);
		}
		
	}
	public String encodeCreerToString( ArrayList<String> str){
		return str.get(0) +" " +str.get(1) +" " + str.get(2) +" " + str.get(3)+ " " + str.get(4) + " " + str.get(5);
	}
	public void afficherEncode (Paquet test){
		System.out.println(partiesMessage[0] +partiesMessage[1]+partiesMessage[2] );
	}
	@Override
	public String toString() {
		
	 return partiesMessage[0] + " " + partiesMessage[1] + " " + partiesMessage[2]+" "+partiesMessage[3];
		
	
	}
	public String toStringCreer(){
		return partiesMessage[0] + " " + partiesMessage[1] + " " + partiesMessage[2]+" "+partiesMessage[3];
	}
	
}
