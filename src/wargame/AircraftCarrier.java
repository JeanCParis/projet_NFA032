package wargame;

import interfaceGraphique.Terminal;

import java.util.ArrayList;

public class AircraftCarrier extends Vehicle {
	ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();

	public ArrayList<Aircraft> getAircrafts() {
		return aircrafts;
	}

	public void addAircraft(final Aircraft aircraft) {
		if (aircrafts.size() < Game.MAX_AIRCRAFTS_CARRIER) {
			aircrafts.add(aircraft);
		} else {
			throw new RuntimeException();
		}
	}

	public void removeAircraft(final Aircraft aircraft) {
		try {
			aircrafts.remove(aircraft);
		} catch (final Exception e) {
			Terminal.ecrireException(new Exception());
		}
	}

	@Override
	public void moveToPosition(final int xPosition, final int yPosition, final LevelType level) {
		super.moveToPosition(xPosition, yPosition, level);
		for (final Aircraft aircraft : aircrafts) {
			aircraft.moveToPosition(xPosition, yPosition, LevelType.CARRIER_LEVEL);
		}
	}

	@Override
	public String toString() {
		return "Aircraft Carrier";
	}
}