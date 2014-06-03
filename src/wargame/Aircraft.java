package wargame;

public class Aircraft extends Vehicle {
	protected AircraftCarrier carrier;
	
	public Aircraft(AircraftCarrier carrier) {
		this.carrier = carrier;
	}
	
	public void leaveCarrier() {
		carrier.removeAircraft(this);
		carrier = null;
	}
	
	public void landOnCarrier(AircraftCarrier carrier) {
		this.carrier = carrier;
	}
	
	@Override
	public String toString() {
		return "Aircraft";
	}
}