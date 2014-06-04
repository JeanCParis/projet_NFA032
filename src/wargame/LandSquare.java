package wargame;

public class LandSquare extends Square {

	public LandSquare(final int xPosition, final int yPosition, final SquareType type) {
		super(xPosition, yPosition, type);
	}
	
	@Override
	public void addVehicleToGroundlevel(final Vehicle vehicle) throws IncompatibleVehiculeException, FullException{
		throw new IncompatibleVehiculeException();
	}
}
