package wargame;

import interfaceGraphique.*;

public class Game {
	public static final int SEA_KEY = 0;
	public static final int LAND_KEY = 1;
	public static final int AIRCRAFT_LAND_KEY = 10;
	public static final int AIRCRAFT_WATER_KEY = 11;
	public static final int AIRCRAFTS_LAND_KEY = 100;
	public static final int AIRCRAFTS_WATER_KEY = 101;
	public static final int CARRIER_KEY = 20;
	
	
	public static final int X_MAP_SIZE = 10;
	public static final int Y_MAP_SIZE = 10;
	public static final int SQUARE_SIZE = 48;
	public static final int BORDER = 24;
	
	public static final int MAX_SKYLEVEL_AIRCRAFTS = 5;
	public static final int MAX_AIRCRAFTS_CARRIER = 5;
	
	protected WorldMap map;
	protected Army army;
	protected IGPA igpa;
	
	protected GameState currentGameState = GameState.INITIALIZATION;
	
	public void gameInitialisation() {
		
		SquareClickHandler.setGame(this);
		
		igpa = new IGPA(X_MAP_SIZE, Y_MAP_SIZE);
		igpa.creerFenetre();
		
		igpa.declarerImage(SEA_KEY,"eau.png");
		igpa.declarerImage(LAND_KEY,"sable.png");
		igpa.declarerImage(AIRCRAFT_LAND_KEY,"avion-terre.png");
		igpa.declarerImage(AIRCRAFTS_LAND_KEY,"avions-terre.png");
		igpa.declarerImage(AIRCRAFT_WATER_KEY,"avion-eau.png");
		igpa.declarerImage(AIRCRAFTS_WATER_KEY,"avions-eau.png");
		igpa.declarerImage(CARRIER_KEY,"porte-avion-eau.png");
		
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
		
		map = new WorldMap(X_MAP_SIZE, Y_MAP_SIZE);		
		map.initialisation(keysTable);
	}
	
	public void playerInitialisation() {
		Terminal.ecrireStringln("Welcome to Wargame !");
		Terminal.sautDeLigne();
		
		Terminal.ecrireString("Please enter your army's name : ");
		String name = Terminal.lireString();
		army = new Army(name);
	}
	
	public void squareClicked() {
		switch (currentGameState) {
		case INITIALIZATION:
			igpa.modifierCase(9, 9, AIRCRAFT_LAND_KEY);
			igpa.reafficher();
			break;
		case INGAME:
			//
			break;
		}
	}
	
	public void gameTermination() {
		igpa.fermer();
	}
}
