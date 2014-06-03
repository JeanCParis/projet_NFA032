package wargame;

public abstract class Vehicle {
	protected VehicleType type;
	protected int xPosition, yPosition;
	protected LevelType level;

	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public void moveToPosition(final int xPosition, final int yPosition,
			final LevelType level) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.level = level;
	}

	public VehicleType getType() {
		return type;
	}

	public LevelType getLevel() {
		return level;
	}

	public void setType(final VehicleType type) {
		this.type = type;
	}

	@Override
	public abstract String toString();
}