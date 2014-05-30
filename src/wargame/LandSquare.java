package wargame;

import interfaceGraphique.Terminal;

public class LandSquare extends Square {

	public LandSquare(int xPosition, int yPosition) {
		super(xPosition, yPosition);
	}
	
	@Override
	public void addVehicleToGroundlevel(Vehicle vehicle) throws IncompatibleVehiculeException, FullException{
		throw new IncompatibleVehiculeException(vehicle);
	}
}
