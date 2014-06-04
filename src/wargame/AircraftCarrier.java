package wargame;

import java.util.ArrayList;

public class AircraftCarrier extends Vehicle {
	final ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();

	public AircraftCarrier() {
		super(VehicleType.AIRCRAFT_CARRIER);
	}

	public ArrayList<Aircraft> getAircrafts() {
		return aircrafts;
	}

	public void addAircraft(final Aircraft aircraft) throws FullException {
		if (aircrafts.size() < Game.MAX_AIRCRAFTS_CARRIER) {
			aircrafts.add(aircraft);
		}
		else {
			throw new FullException();
		}
	}

	public void removeAircraft(final Aircraft aircraft) {
		aircrafts.remove(aircraft);
	}

	@Override
	public void moveToPosition(final int xPosition, final int yPosition) {
		super.moveToPosition(xPosition, yPosition);
		for (final Aircraft aircraft : aircrafts) {
			aircraft.moveToPosition(xPosition, yPosition);
		}
	}

	@Override
	public String toString() {
		return "Aircraft Carrier";
	}
}