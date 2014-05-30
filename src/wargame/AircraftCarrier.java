package wargame;

import interfaceGraphique.Terminal;

import java.util.ArrayList;

public class AircraftCarrier extends Vehicle {
	ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
	
	public AircraftCarrier(int xPosition, int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	public ArrayList<Aircraft> getAircrafts() {
		return aircrafts;
	}
	
	public void addAircraft(Aircraft aircraft) {
		if(aircrafts.size() < Game.MAX_AIRCRAFTS_CARRIER) {
			aircrafts.add(aircraft);
		}
		else {
			throw new RuntimeException();
		}
	}
	
	public void removeAircraft(Aircraft aircraft) {
		try {
			aircrafts.remove(aircraft);
		} catch(Exception e){
			Terminal.ecrireException(new Exception());
		}
	}

	@Override
	public String toString() {
		return "Aircraft Carrier";
	}
}