package wargame;

import interfaceGraphique.*;

public class Wargame {
	public static final int MAX_SKYLEVEL_AIRCRAFTS = 5;
	public static final int MAX_AIRCRAFTS_CARRIER = 5;
	public static final int X_SIZE = 10;
	public static final int Y_SIZE = 10;
	public static final int SEA_KEY = 0;
	public static final int LAND_KEY = 1;
	public static final int AIRCRAFT_KEY = 10;
	public static final int CARRIER_KEY = 11;
	
	private static WorldMap map;
	private static Army army;
	private static boolean continueWargame = true;
	private static IGPA igpa;
	
	
	public static void main(String[] a){
		gameInitialisation();
		igpa.creerFenetre();
		
		playerInitialisation();
		
		while(continueWargame) {
			continueWargame = false;
		}
		
		igpa.fermer();
	}
	
	private static void gameInitialisation() {
		igpa = new IGPA(X_SIZE, Y_SIZE);
		map = new WorldMap(X_SIZE, Y_SIZE);
		
		igpa.declarerImage(SEA_KEY,"eau.png");
		igpa.declarerImage(LAND_KEY,"sable.png");
		
		int[][] keysTable = {{SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY},
							 {SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY},
							 {SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY},
							 {SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY},
							 {SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, LAND_KEY, LAND_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY},
							 {SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, LAND_KEY, LAND_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY},
							 {SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY},
							 {SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY},
							 {SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY},
							 {SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY}};
		
		igpa.definirTerrain(keysTable);
		map.initialisation(keysTable);
	}
	
	private static void playerInitialisation() {
		Terminal.ecrireStringln("Welcome to Wargame !");
		Terminal.sautDeLigne();
		
		Terminal.ecrireString("Please enter your army's name : ");
		String name = Terminal.lireString();
		army = new Army(name);
	}
}
