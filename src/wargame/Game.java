package wargame;

import java.util.List;

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
		TerminalInputHandler.setGame(this);
		VehicleClickHandler.setGame(this);
		
		Aircraft.setType(VehicleType.AIRCRAFT);
		AircraftCarrier.setType(VehicleType.AIRCRAFT_CARRIER);
		LandSquare.setType(SquareType.LAND);
		SeaSquare.setType(SquareType.SEA);
		
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
		igpa.writeOnTerminalln("Welcome to Wargame !");
		igpa.writeOnTerminal("General, please enter your army's name : ");
		igpa.enableInputFromTerminal();
	}
	
	public void squareClicked(int xPosition, int yPosition) {
		switch (currentGameState) {
		case START:
			try {
				AircraftCarrier carrier = new AircraftCarrier(xPosition, yPosition);
				for (int i=0 ; i<MAX_AIRCRAFTS_CARRIER ; ++i) {
					carrier.addAircraft(new Aircraft(xPosition, yPosition));
				}
				army.addVehicle(carrier);
				map.addVehicleToGroundlevel(carrier, xPosition, yPosition);
				igpa.modifierCase(xPosition, yPosition, CARRIER_KEY);
				igpa.reafficher();
				igpa.writeOnTerminalln("General, you have placed your vehicle on position (" + xPosition + "," + yPosition + ").");
				igpa.writeOnTerminalln("General, you can now select a vehicle to move on the worldmap.");
				currentGameState = GameState.INGAME_SELECTING_VEHICLE;
			} catch(IncompatibleVehiculeException e) {
				igpa.writeOnTerminalln("General, the vehicle type " + e.getVehicle().toString() + " is incompatible with the selected square.");
				igpa.writeOnTerminalln("General, please select another one.");
			} catch(FullException e) {
				igpa.writeOnTerminalln("General, the selected square is already occupied, please select another one.");
			}
			break;
		case INGAME_SELECTING_VEHICLE:
				List<Vehicle> groundVehicles, skyVehicles;
				groundVehicles = map.getGroundlevelVehicules(xPosition, yPosition);
				skyVehicles = map.getSkylevelVehicules(xPosition, yPosition);
				if (groundVehicles.isEmpty() && skyVehicles.isEmpty())
				{
					igpa.writeOnTerminalln("General, the selected square is not occupied by any vehicle.");
				}
				else {
					currentGameState = GameState.INGAME_SELECTING_VEHICLE_SELECTED;
					switch(map.getType(xPosition, yPosition)) {
					case LAND :
						for(Vehicle vehicle : groundVehicles) {
							switch(vehicle.getType()) {
							case AIRCRAFT :
								igpa.addRightVehicleChoice(AIRCRAFT_LAND_KEY);
								break;
							}
						}
						break;
					case SEA :
						for(Vehicle vehicle : groundVehicles) {
							switch(vehicle.getType()) {
							case AIRCRAFT :
								igpa.addRightVehicleChoice(AIRCRAFT_WATER_KEY);
								break;
							case AIRCRAFT_CARRIER :
								igpa.addLeftVehicleChoice(CARRIER_KEY);
								AircraftCarrier carrier = (AircraftCarrier)vehicle;
								for(Vehicle subvehicle : carrier.getAircrafts()) {
									igpa.addLeftVehicleChoice(AIRCRAFT_WATER_KEY);
								}
								break;
							}
						}
						break;
					}
				}
		}				
	}
	
	public void vehicleClicked() {
		//todo
	}
	
	public void inputRead(String string) {
		switch (currentGameState) {
			case INITIALIZATION:
				igpa.disableInputFromTerminal();
				igpa.writeOnTerminalln(" " + string);
				army = new Army(string);
				
				igpa.writeOnTerminalln("General, please select the square on which you wish to put your aircraft carrier.");
				currentGameState = GameState.START;
				break;
		}
	}
	
	public void gameTermination() {
		igpa.fermer();
	}
}
