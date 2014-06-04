package wargame;

public abstract class Vehicle {
	protected final VehicleType type;
	protected int xPosition, yPosition;

	protected Vehicle(final VehicleType type) {
		this.type = type;
	}
	
	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public void moveToPosition(final int xPosition, final int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public VehicleType getType() {
		return type;
	}

	@Override
	public abstract String toString();
}