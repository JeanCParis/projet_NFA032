package wargame;

import interfaceGraphique.Terminal;

public class SeaSquare extends Square {

	public SeaSquare(int xPosition, int yPosition) {
		super(xPosition, yPosition);
	}
	
	@Override
	public void addVehicleToGroundlevel(Vehicle Vehicle) {
		if(Vehicle instanceof AircraftCarrier) {
			if(groundlevelVehicles.isEmpty()) {
				groundlevelVehicles.add(Vehicle);
			}
			else {
				Terminal.ecrireException(new Exception());
			}
		}
		else {
			Terminal.ecrireException(new Exception());
		}
	}
}
