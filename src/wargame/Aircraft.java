package wargame;

public class Aircraft extends Vehicle {
	protected AircraftCarrier carrier;

	public Aircraft(final AircraftCarrier carrier) {
		this.carrier = carrier;
		type = VehicleType.AIRCRAFT;
	}

	public AircraftCarrier getCarrier() {
		return carrier;
	}

	public void setCarrier(final AircraftCarrier carrier) {
		this.carrier = carrier;
	}

	@Override
	public String toString() {
		return "Aircraft";
	}
}