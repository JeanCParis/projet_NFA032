package wargame;

import interfaceGraphique.IGPA;

import java.util.List;

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
	
	public static final int AIRCRAFT_LANDING_DISTANCE = 1;
	public static final int AIRCRAFT_TAKE_OFF_DISTANCE = 1;
	public static final int AIRCRAFT_MAX_MOVING_DISTANCE = 3;
	public static final int CARRIER_MAX_MOVING_DISTANCE = 1;

	protected final IGPA igpa;
	protected GameState currentGameState = GameState.INITIALIZATION;

	protected final WorldMap map;
	protected Army army;
	protected Vehicle selectedVehicle;
	protected ActionType selectedAction;

	public Game() {

		MyActionListener.setGame(this);

		igpa = new IGPA(X_MAP_SIZE, Y_MAP_SIZE);
		igpa.creerFenetre();

		igpa.declarerImage(SEA_KEY, "eau.png");
		igpa.declarerImage(LAND_KEY, "sable.png");
		igpa.declarerImage(AIRCRAFT_LAND_KEY, "avion-terre.png");
		igpa.declarerImage(AIRCRAFTS_LAND_KEY, "avions-terre.png");
		igpa.declarerImage(AIRCRAFT_WATER_KEY, "avion-eau.png");
		igpa.declarerImage(AIRCRAFTS_WATER_KEY, "avions-eau.png");
		igpa.declarerImage(CARRIER_KEY, "porte-avion-eau.png");

		final int[][] keysTable = {
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY },
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY },
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY },
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY },
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, LAND_KEY, LAND_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY },
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, LAND_KEY, LAND_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY },
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY },
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY },
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY },
				{ SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY,
						SEA_KEY, SEA_KEY, SEA_KEY, SEA_KEY } };

		map = new WorldMap(X_MAP_SIZE, Y_MAP_SIZE);
		map.initialisation(keysTable);

		igpa.definirTerrain(map, keysTable);
	}

	public void start() {
		igpa.writeOnTerminalln("Welcome to Wargame !");
		igpa.writeOnTerminal("General, please enter your army's name : ");
		igpa.enableInputFromTerminal();
	}

	public void squareClicked(final Square square) {
		int xPosition = square.getXPositon();
		int yPosition = square.getYPosition();
		
		switch (currentGameState) {

		case START:
			try {
				final AircraftCarrier carrier = new AircraftCarrier();
				for (int i = 0; i < MAX_AIRCRAFTS_CARRIER; ++i) {
					carrier.addAircraft(new Aircraft(carrier));
				}
				carrier.moveToPosition(xPosition, yPosition);
				army.addVehicle(carrier);
				map.addVehicleToGroundlevel(carrier, xPosition, yPosition);
				igpa.modifierCase(xPosition, yPosition, CARRIER_KEY);
				igpa.reafficher();
				igpa.writeOnTerminalln("General, you have placed your carrier on position ("
						+ xPosition + "," + yPosition + ").");
				igpa.writeOnTerminalln("General, please select the square from which you wish to select a vehicule.");
				currentGameState = GameState.INGAME_SELECTING_VEHICLE;
			} catch (final IncompatibleVehiculeException e) {
				igpa.writeOnTerminalln("General, the vehicle type " + e.getVehicle().toString() + " is incompatible with the selected square.");
				igpa.writeOnTerminalln("General, please select another one.");
			} catch (final FullException e) {
				igpa.writeOnTerminalln("General, the selected square is already occupied, please select another one.");
			}
			break;

		case INGAME_SELECTING_VEHICLE:
			final List<Vehicle> groundVehicles = square.getGroundlevelVehicles();
			final List<Vehicle> skyVehicles = square.getSkylevelVehicles();
			
			if (groundVehicles.isEmpty() && skyVehicles.isEmpty()) {
				igpa.writeOnTerminalln("General, the selected square is not occupied by any vehicle.");
			} else {
				currentGameState = GameState.INGAME_SELECTING_VEHICLE_SELECTED;
				igpa.writeOnTerminalln("General, please select the vehicule you wish to move.");
				switch (square.getType()) {
				case LAND:
					for (final Vehicle vehicle : skyVehicles) {
						switch (vehicle.getType()) {
						case AIRCRAFT:
							igpa.addRightVehicleChoice(vehicle,
									AIRCRAFT_LAND_KEY);
							break;
						}
					}
					for (final Vehicle vehicle : groundVehicles) {
						switch (vehicle.getType()) {
						case AIRCRAFT:
							igpa.addLeftVehicleChoice(vehicle,
									AIRCRAFT_LAND_KEY);
							break;
						}
					}
					break;
				case SEA:
					for (final Vehicle vehicle : groundVehicles) {
						switch (vehicle.getType()) {
						case AIRCRAFT_CARRIER:
							igpa.addLeftVehicleChoice(vehicle, CARRIER_KEY);
							final AircraftCarrier carrier = (AircraftCarrier) vehicle;
							for (final Vehicle subvehicle : carrier.getAircrafts()) {
								igpa.addLeftVehicleChoice(subvehicle, AIRCRAFT_WATER_KEY);
							}
							break;
						}
					}
					for (final Vehicle vehicle : skyVehicles) {
						switch (vehicle.getType()) {
						case AIRCRAFT:
							igpa.addRightVehicleChoice(vehicle, AIRCRAFT_WATER_KEY);
							break;
						}
					}
					break;
				}
			}
			break;

		case INGAME_SELECTING_SQUARE:
			final int previousXPosition = selectedVehicle.getXPosition();
			final int previousYPosition = selectedVehicle.getYPosition();
			
			switch (selectedVehicle.getType()) {		
			case AIRCRAFT:
				switch(selectedAction) {			
				case TAKE_OFF:
					if(calculateDistance(xPosition, yPosition, previousXPosition, previousYPosition)==AIRCRAFT_TAKE_OFF_DISTANCE){
						try {
							square.addVehicleToSkylevel(selectedVehicle);
							final Aircraft aircraft = (Aircraft) selectedVehicle;
							aircraft.getCarrier().removeAircraft(aircraft);
							aircraft.setCarrier(null);
							
							igpa.modifierCase(previousXPosition,previousYPosition,determineSquareKey(previousXPosition,previousYPosition));
							igpa.modifierCase(xPosition, yPosition,determineSquareKey(xPosition, yPosition));
							selectedVehicle.moveToPosition(xPosition, yPosition);

							selectedVehicle = null;
							selectedAction = null;
							currentGameState = GameState.INGAME_SELECTING_VEHICLE;
							
							igpa.clearVehicleChoices();
							igpa.clearActionChoices();
							igpa.reafficher();

						} catch (final FullException e) {
							igpa.writeOnTerminalln("General, the selected square is already full.");
						} catch (final IncompatibleVehiculeException e) {
							igpa.writeOnTerminalln("General, the vehicule canot move to this type of square.");
						}
					}
					else {
						igpa.writeOnTerminalln("General, you must select a square that is located next to the aircraft carrier.");
					}
					break;
					
				case LAND:
					if(calculateDistance(xPosition, yPosition, previousXPosition, previousYPosition)==AIRCRAFT_LANDING_DISTANCE){
						List<Vehicle> vehicles = map.getGroundlevelVehicles(xPosition, yPosition);			
						if(vehicles.size() != 0 && vehicles.get(0).getType()==VehicleType.AIRCRAFT_CARRIER) {
							try {
								final Aircraft aircraft = (Aircraft)selectedVehicle;
								final AircraftCarrier carrier = (AircraftCarrier)vehicles.get(0);
								
								carrier.addAircraft(aircraft);
								aircraft.setCarrier(carrier);
								aircraft.moveToPosition(xPosition, yPosition);
								map.removeVehicleFromSkylevel(aircraft, previousXPosition, previousYPosition);
								
								igpa.modifierCase(previousXPosition,previousYPosition,determineSquareKey(previousXPosition,previousYPosition));
								igpa.modifierCase(xPosition, yPosition,determineSquareKey(xPosition, yPosition));
	
								selectedVehicle = null;
								selectedAction = null;
								currentGameState = GameState.INGAME_SELECTING_VEHICLE;
								
								igpa.clearVehicleChoices();
								igpa.clearActionChoices();
								igpa.reafficher();
							} catch (FullException e) {
								igpa.writeOnTerminalln("General, the aircraft carrier is already full.");
							}
						}
						else {
							igpa.writeOnTerminalln("General, you must select a square on which an aircraft carrier is located.");
						}
					}
					else {
						igpa.writeOnTerminalln("General, you must select a carrier that is located next to aircraft.");
					}
					break;
					
				case MOVE:
					if(calculateDistance(xPosition, yPosition, previousXPosition, previousYPosition)<=AIRCRAFT_MAX_MOVING_DISTANCE){
						try {
							square.addVehicleToSkylevel(selectedVehicle);
							selectedVehicle.moveToPosition(xPosition, yPosition);
							map.removeVehicleFromSkylevel(selectedVehicle, previousXPosition, previousYPosition);

							selectedVehicle = null;
							selectedAction = null;
							currentGameState = GameState.INGAME_SELECTING_VEHICLE;
							
							igpa.modifierCase(xPosition, yPosition,determineSquareKey(xPosition, yPosition));
							igpa.modifierCase(previousXPosition,previousYPosition,determineSquareKey(previousXPosition,previousYPosition));
							igpa.clearVehicleChoices();
							igpa.clearActionChoices();
							igpa.reafficher();
						} catch (final FullException e) {
							igpa.writeOnTerminalln("General, the selected square is already full.");
						} catch (final IncompatibleVehiculeException e) {
							igpa.writeOnTerminalln("General, the vehicule canot move to this type of square.");
						}
						break;
					}
					else {
						igpa.writeOnTerminalln("General, an aircraft canot be moved more than " + AIRCRAFT_MAX_MOVING_DISTANCE + " squares a time." );
					}
					break;
					
				case ATTACK:
					igpa.writeOnTerminalln("General, this action is not yet implemented, please select another action.");
					selectedAction = null;
					currentGameState = GameState.INGAME_SELECTING_ACTION;
					break; 
				}
				break;
				
			case AIRCRAFT_CARRIER:
				switch(selectedAction) {
				case MOVE:
					if(calculateDistance(xPosition, yPosition, previousXPosition, previousYPosition)<=CARRIER_MAX_MOVING_DISTANCE){
						try {
							square.addVehicleToGroundlevel(selectedVehicle);
							selectedVehicle.moveToPosition(xPosition, yPosition);
							map.removeVehicleFromGroundlevel(selectedVehicle, previousXPosition, previousYPosition);
							
							selectedVehicle = null;
							selectedAction = null;
							currentGameState = GameState.INGAME_SELECTING_VEHICLE;
							
							igpa.modifierCase(xPosition, yPosition,determineSquareKey(xPosition, yPosition));
							igpa.modifierCase(previousXPosition,previousYPosition,determineSquareKey(previousXPosition,previousYPosition));
							igpa.clearVehicleChoices();
							igpa.clearActionChoices();
							igpa.reafficher();
						} catch (final FullException e) {
							igpa.writeOnTerminalln("General, the selected square is already full.");
						} catch (final IncompatibleVehiculeException e) {
							igpa.writeOnTerminalln("General, the vehicule canot move to this type of square.");
						}
						break;
					}
					else {
						igpa.writeOnTerminalln("General, a carrier canot be moved more than " + CARRIER_MAX_MOVING_DISTANCE + " squares a time." );
					}
					break;
					
				case ATTACK:
					igpa.writeOnTerminalln("General, this action is not yet implemented, please select another action.");
					selectedAction = null;
					currentGameState = GameState.INGAME_SELECTING_ACTION;
					break; 
				}
				break;
			}
		}
	}

	private int determineSquareKey(final int xPosition, final int yPosition) {
		final List<Vehicle> groundVehicles = map.getGroundlevelVehicles(xPosition, yPosition);
		final List<Vehicle> skyVehicles = map.getSkylevelVehicles(xPosition, yPosition);
		switch (map.getType(xPosition, yPosition)) {
		case SEA:
			if (groundVehicles.isEmpty()) {
				if (skyVehicles.isEmpty()) {
					return SEA_KEY;
				} else {
					if (skyVehicles.size() > 1) {
						return AIRCRAFTS_WATER_KEY;
					} else {
						return AIRCRAFT_WATER_KEY;
					}
				}
			} else {
				return CARRIER_KEY;
			}
		case LAND:
			if (groundVehicles.isEmpty()) {
				if (skyVehicles.isEmpty()) {
					return LAND_KEY;
				} else {
					if (skyVehicles.size() > 1) {
						return AIRCRAFTS_LAND_KEY;
					} else {
						return AIRCRAFT_LAND_KEY;
					}
				}
			} else {
				return CARRIER_KEY;
			}
		default:
			return 0;
		}
	}
	
	private int calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1-x2)+Math.abs(y1-y2);
	}

	public void vehicleClicked(final Vehicle vehicle) {
		selectedVehicle = vehicle;
		currentGameState = GameState.INGAME_SELECTING_ACTION;
		igpa.clearActionChoices();
		
		switch(selectedVehicle.getType()) {
		
		case AIRCRAFT:
			Aircraft aircraft = (Aircraft)selectedVehicle;
			if(aircraft.getCarrier()!=null) {
				igpa.addActionChoice(ActionType.TAKE_OFF);
			}
			else {
				igpa.addActionChoice(ActionType.MOVE);
				igpa.addActionChoice(ActionType.ATTACK);
				igpa.addActionChoice(ActionType.LAND);
			}
			break;
		case AIRCRAFT_CARRIER:
			igpa.addActionChoice(ActionType.MOVE);
			igpa.addActionChoice(ActionType.ATTACK);
			break;
		}
		
		igpa.reafficher();
		igpa.writeOnTerminalln("General, you've selected " + vehicle + " .");
		igpa.writeOnTerminalln("General, please select an action to perform.");
	}
	
	public void actionClicked(final ActionType action) {
		selectedAction = action;
		currentGameState = GameState.INGAME_SELECTING_SQUARE;
		
		igpa.writeOnTerminalln("General, please select the square on which you wish to perform the action.");
	}

	public void inputRead(final String string) {
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
