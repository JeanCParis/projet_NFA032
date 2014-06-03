package wargame;

public class SeaSquare extends Square {

	public SeaSquare(final int xPosition, final int yPosition, final SquareType type) {
		super(xPosition, yPosition, type);
	}

	@Override
	public void addVehicleToGroundlevel(final Vehicle vehicle)
			throws IncompatibleVehiculeException, FullException {
		if (vehicle.getType() == VehicleType.AIRCRAFT_CARRIER) {
			if (groundlevelVehicles.isEmpty()) {
				groundlevelVehicles.add(vehicle);
			} else {
				throw new FullException();
			}
		} else {
			throw new IncompatibleVehiculeException(vehicle);
		}
	}
}
