package wargame;

import interfaceGraphique.Terminal;

public class LandSquare extends Square {

	public LandSquare(final int xPosition, final int yPosition, final SquareType type) {
		super(xPosition, yPosition, type);
	}
	
	@Override
	public void addVehicleToGroundlevel(Vehicle vehicle) throws IncompatibleVehiculeException, FullException{
		throw new IncompatibleVehiculeException(vehicle);
	}
}
