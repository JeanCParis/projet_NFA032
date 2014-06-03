package wargame;

import java.util.ArrayList;

public abstract class Square {
	protected final SquareType type;
	protected final int xPositon, yPosition;

	protected ArrayList<Vehicle> groundlevelVehicles = new ArrayList<Vehicle>();
	protected ArrayList<Vehicle> skylevelVehicles = new ArrayList<Vehicle>();

	protected Square(final int xPosition, final int yPosition, final SquareType type) {
		this.xPositon = xPosition;
		this.yPosition = yPosition;
		this.type = type;
	}

	public int getXPositon() {
		return xPositon;
	}

	public int getYPosition() {
		return yPosition;
	}

	public SquareType getType() {
		return type;
	}
	
	public abstract void addVehicleToGroundlevel(Vehicle vehicle)
			throws IncompatibleVehiculeException, FullException;

	public void addVehicleToSkylevel(final Vehicle vehicle)
			throws IncompatibleVehiculeException, FullException {
		if (vehicle.getType() == VehicleType.AIRCRAFT) {
			if (skylevelVehicles.size() < Game.MAX_SKYLEVEL_AIRCRAFTS) {
				skylevelVehicles.add(vehicle);
			} else {
				throw new FullException();
			}
		} else {
			throw new IncompatibleVehiculeException(vehicle);
		}
	}

	public void removeVehicleFromGroundlevel(final Vehicle vehicle) {
		groundlevelVehicles.remove(vehicle);
	}

	public void removeVehicleFromSkylevel(final Vehicle vehicle) {
		skylevelVehicles.remove(vehicle);
	}

	public ArrayList<Vehicle> getGroundlevelVehicles() {
		return groundlevelVehicles;
	}

	public ArrayList<Vehicle> getSkylevelVehicles() {
		return skylevelVehicles;
	}
}