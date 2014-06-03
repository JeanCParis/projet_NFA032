package wargame;

import java.util.ArrayList;

public class AircraftCarrier extends Vehicle {
	ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();

	public AircraftCarrier() {
		type = VehicleType.AIRCRAFT_CARRIER;
	}

	public ArrayList<Aircraft> getAircrafts() {
		return aircrafts;
	}

	public void addAircraft(final Aircraft aircraft) {
		if (aircrafts.size() < Game.MAX_AIRCRAFTS_CARRIER) {
			aircrafts.add(aircraft);
		}
	}

	public void removeAircraft(final Aircraft aircraft) {
		aircrafts.remove(aircraft);
	}

	@Override
	public void moveToPosition(final int xPosition, final int yPosition,
			final LevelType level) {
		super.moveToPosition(xPosition, yPosition, level);
		for (final Aircraft aircraft : aircrafts) {
			aircraft.moveToPosition(xPosition, yPosition,
					LevelType.CARRIER_LEVEL);
		}
	}

	@Override
	public String toString() {
		return "Aircraft Carrier";
	}
}