package Serveur;

import objet.BDD;

public class ServeurMaitre {
	@SuppressWarnings("unused")
	private BDD bd;
	private Thread t1;
	private Thread t2;
	private Thread t3;
	private Thread t4;
	/**
	 * <b> Constructeur de la classe ServeurMaitre</b>
	 * 
	 * @param bd
	 * 		
	 * */
	public ServeurMaitre(BDD bd){
		this.bd = bd;
		try {
			ServeurMaitreTest nonSSL = new ServeurMaitreTest(bd);
			ServeurMaitreSSL sSL= new ServeurMaitreSSL(bd);
			ServeurAdminSSL Admin = new ServeurAdminSSL(bd);
			CheckingLocation checking= new CheckingLocation(bd);
			t1 = new Thread(sSL);
			t2 = new Thread(nonSSL);
			t3 = new Thread(Admin);
			t4 = new Thread(checking);
			System.out.println("Lancement des serveurs");
			t1.start();
			t2.start();
			t3.start();
			System.out.println("Lancement du check sur les Locations");
			t4.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}