package wargame;

import interfaceGraphique.Terminal;

public class SeaSquare extends Square {

	public SeaSquare(int xPosition, int yPosition) {
		super(xPosition, yPosition);
	}
	
	@Override
	public void addVehicleToGroundlevel(Vehicle vehicle) throws IncompatibleVehiculeException, FullException {
		if(vehicle instanceof AircraftCarrier) {
			if(groundlevelVehicles.isEmpty()) {
				groundlevelVehicles.add(vehicle);
			}
			else {
				throw new FullException();
			}
		}
		else {
			throw new IncompatibleVehiculeException(vehicle);
		}
	}
}
