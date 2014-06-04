package wargame;

public class Aircraft extends Vehicle {
	protected AircraftCarrier carrier;

	public Aircraft()
	{
		super(VehicleType.AIRCRAFT);
	}
	
	public Aircraft(final AircraftCarrier carrier) {
		this();
		this.carrier = carrier;
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