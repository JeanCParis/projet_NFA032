package wargame;

public class IncompatibleVehiculeException extends Exception{
	Vehicle vehicle;
	
	public IncompatibleVehiculeException(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}
}
