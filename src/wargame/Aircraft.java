package wargame;

public class Aircraft extends Vehicle {
	public Aircraft(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	@Override
	public String toString() {
		return "Aircraft";
	}
}