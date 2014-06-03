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

	protected IGPA igpa;
	protected GameState currentGameState = GameState.INITIALIZATION;

	protected WorldMap map;
	protected Army army;
	protected Vehicle selectedVehicle;

	public Game() {

		SquareClickHandler.setGame(this);
		TerminalInputHandler.setGame(this);
		VehicleClickHandler.setGame(this);

		LandSquare.setType(SquareType.LAND);
		SeaSquare.setType(SquareType.SEA);

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

	public void squareClicked(final int xPosition, final int yPosition) {
		switch (currentGameState) {

		case START:
			try {
				final AircraftCarrier carrier = new AircraftCarrier();
				for (int i = 0; i < MAX_AIRCRAFTS_CARRIER; ++i) {
					carrier.addAircraft(new Aircraft(carrier));
				}
				carrier.moveToPosition(xPosition, yPosition,
						LevelType.GROUND_LEVEL);
				army.addVehicle(carrier);
				map.addVehicleToGroundlevel(carrier, xPosition, yPosition);
				igpa.modifierCase(xPosition, yPosition, CARRIER_KEY);
				igpa.reafficher();
				igpa.writeOnTerminalln("General, you have placed your carrier on position ("
						+ xPosition + "," + yPosition + ").");
				igpa.writeOnTerminalln("General, you can now select a vehicle to move on the worldmap.");
				currentGameState = GameState.INGAME_SELECTING_VEHICLE;
			} catch (final IncompatibleVehiculeException e) {
				igpa.writeOnTerminalln("General, the vehicle type "
						+ e.getVehicle().toString()
						+ " is incompatible with the selected square.");
				igpa.writeOnTerminalln("General, please select another one.");
			} catch (final FullException e) {
				igpa.writeOnTerminalln("General, the selected square is already occupied, please select another one.");
			}
			break;

		case INGAME_SELECTING_VEHICLE:
			final List<Vehicle> groundVehicles = map.getGroundlevelVehicules(
					xPosition, yPosition);
			final List<Vehicle> skyVehicles = map.getSkylevelVehicules(
					xPosition, yPosition);
			if (groundVehicles.isEmpty() && skyVehicles.isEmpty()) {
				igpa.writeOnTerminalln("General, the selected square is not occupied by any vehicle.");
			} else {
				currentGameState = GameState.INGAME_SELECTING_VEHICLE_SELECTED;
				switch (map.getType(xPosition, yPosition)) {
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
							for (final Vehicle subvehicle : carrier
									.getAircrafts()) {
								igpa.addLeftVehicleChoice(subvehicle,
										AIRCRAFT_WATER_KEY);
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

		case INGAME_SELECTING_VEHICLE_CONFIRMED:
			final int previousXPosition = selectedVehicle.getXPosition();
			final int previousYPosition = selectedVehicle.getYPosition();

			if (!(xPosition == previousXPosition && yPosition == previousYPosition)) {
				switch (selectedVehicle.getLevel()) {

				case CARRIER_LEVEL:
					try {
						map.addVehicleToSkylevel(selectedVehicle, xPosition,
								yPosition);
						final Aircraft aircraft = (Aircraft) selectedVehicle;
						aircraft.getCarrier().removeAircraft(aircraft);
						aircraft.setCarrier(null);
						igpa.clearVehicleChoices();
						igpa.modifierCase(
								previousXPosition,
								previousYPosition,
								determineSquareKey(previousXPosition,
										previousYPosition));
						igpa.modifierCase(xPosition, yPosition,
								determineSquareKey(xPosition, yPosition));
						selectedVehicle.moveToPosition(xPosition, yPosition,
								LevelType.SKY_LEVEL);

						selectedVehicle = null;
						currentGameState = GameState.INGAME_SELECTING_VEHICLE;
						igpa.reafficher();

					} catch (final FullException e) {
						igpa.writeOnTerminalln("General, the selected square is already full on the sky level.");
					} catch (final IncompatibleVehiculeException e) {
						igpa.writeOnTerminalln("General, the vehicule canot move to this type of square.");
					}
					break;

				case SKY_LEVEL:
					try {
						map.addVehicleToSkylevel(selectedVehicle, xPosition,
								yPosition);
						map.removeVehicleFromSkylevel(selectedVehicle,
								previousXPosition, previousYPosition);
						igpa.clearVehicleChoices();
						igpa.modifierCase(
								previousXPosition,
								previousYPosition,
								determineSquareKey(previousXPosition,
										previousYPosition));
						selectedVehicle.moveToPosition(xPosition, yPosition,
								selectedVehicle.getLevel());

						switch (selectedVehicle.getType()) {
						case AIRCRAFT:
							igpa.modifierCase(xPosition, yPosition,
									AIRCRAFT_WATER_KEY);
							break;
						case AIRCRAFT_CARRIER:
							igpa.modifierCase(xPosition, yPosition, CARRIER_KEY);
							break;
						}

						selectedVehicle = null;
						currentGameState = GameState.INGAME_SELECTING_VEHICLE;
						igpa.reafficher();

					} catch (final FullException e) {
						igpa.writeOnTerminalln("General, the selected square is already full on the sky level.");
					} catch (final IncompatibleVehiculeException e) {
						igpa.writeOnTerminalln("General, the vehicule canot move to this type of square.");
					}
					break;

				case GROUND_LEVEL:
					try {
						map.addVehicleToGroundlevel(selectedVehicle, xPosition,
								yPosition);
						map.removeVehicleFromGroundlevel(selectedVehicle,
								previousXPosition, previousYPosition);
						igpa.clearVehicleChoices();
						igpa.modifierCase(
								previousXPosition,
								previousYPosition,
								determineSquareKey(previousXPosition,
										previousYPosition));
						selectedVehicle.moveToPosition(xPosition, yPosition,
								selectedVehicle.getLevel());

						switch (selectedVehicle.getType()) {
						case AIRCRAFT:
							igpa.modifierCase(xPosition, yPosition,
									AIRCRAFT_LAND_KEY);
							break;
						case AIRCRAFT_CARRIER:
							igpa.modifierCase(xPosition, yPosition, CARRIER_KEY);
							break;
						}

						selectedVehicle = null;
						currentGameState = GameState.INGAME_SELECTING_VEHICLE;
						igpa.reafficher();

					} catch (final FullException e) {
						igpa.writeOnTerminalln("General, the selected square is already full on the sky level.");
					} catch (final IncompatibleVehiculeException e) {
						igpa.writeOnTerminalln("General, the vehicule canot move to this type of square.");
					}
					break;
				}
			} else {
				igpa.writeOnTerminalln("General, moving a vehicle to it's current location doesn't make sense.");
			}
		}
	}

	private int determineSquareKey(final int xPosition, final int yPosition) {
		final List<Vehicle> groundVehicles = map.getGroundlevelVehicules(
				xPosition, yPosition);
		final List<Vehicle> skyVehicles = map.getSkylevelVehicules(xPosition,
				yPosition);
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
						return AIRCRAFTS_WATER_KEY;
					} else {
						return AIRCRAFT_WATER_KEY;
					}
				}
			} else {
				return CARRIER_KEY;
			}
		default:
			return 0;
		}
	}

	public void vehicleClicked(final Vehicle vehicle) {
		selectedVehicle = vehicle;
		igpa.writeOnTerminalln("General, you've selected " + vehicle + " .");
		igpa.writeOnTerminalln("General, please select a square on which to move the vehicle.");
		currentGameState = GameState.INGAME_SELECTING_VEHICLE_CONFIRMED;
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
