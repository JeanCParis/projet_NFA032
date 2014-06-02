package wargame;

import java.util.ArrayList;

public abstract class Square {
	protected static SquareType type;
	protected final int xPositon, yPosition;

	protected ArrayList<Vehicle> groundlevelVehicles = new ArrayList<Vehicle>();
	protected ArrayList<Vehicle> skylevelVehicles = new ArrayList<Vehicle>();

	protected Square(final int xPosition, final int yPosition) {
		this.xPositon = xPosition;
		this.yPosition = yPosition;
	}

	public int getXPositon() {
		return xPositon;
	}

	public int getYPosition() {
		return yPosition;
	}

	public static SquareType getType() {
		return type;
	}

	public static void setType(final SquareType type) {
		Square.type = type;
	}

	public abstract void addVehicleToGroundlevel(Vehicle vehicle)
			throws IncompatibleVehiculeException, FullException;

	public void addVehicleToSkylevel(final Vehicle vehicle)
			throws IncompatibleVehiculeException, FullException {
		if (vehicle instanceof Aircraft) {
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