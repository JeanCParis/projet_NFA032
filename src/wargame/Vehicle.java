package wargame;

public abstract class Vehicle {
	protected static VehicleType type;
	protected int xPosition, yPosition;
	
	public int getXPosition() {
		return xPosition;
	}
	
	public int getYPosition() {
		return yPosition;
	}
	
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	public static VehicleType getType() {
		return type;
	}

	public static void setType(VehicleType type) {
		Vehicle.type = type;
	}

	public abstract String toString();
}