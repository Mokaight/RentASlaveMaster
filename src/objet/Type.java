package objet;

public enum Type {
	Duplex ("Duplex"),
	Chambre ("Chambre"),
	Loft ("Loft"),
	Autre ("Autre");
	
	private String name = "";
	
	Type(String t){
		this.name = t;
	}
	public String toString(){
	    return name;
	}
	public static boolean TypeExist(String s){
    	for(Type t: Type.values()){
	        if(t.toString().equalsIgnoreCase(s))
	          return true;
	      }
    	return false;
	}
	public static Type StringtoType(String s){
    	for(Type t: Type.values()){
	        if(t.toString().equalsIgnoreCase(s))
	          return t;
	      }
    	return null;
	}
}