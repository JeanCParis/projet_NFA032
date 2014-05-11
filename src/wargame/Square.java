package wargame;

import interfaceGraphique.Terminal;

import java.util.ArrayList;

public abstract class Square { 
	
	protected ArrayList<Vehicle> groundlevelVehicles = new ArrayList<Vehicle>();
	protected ArrayList<Vehicle> skylevelVehicles = new ArrayList<Vehicle>();
	
	public abstract void addVehicleToGroundlevel(Vehicle Vehicle);
	
	public void addVehicleToSkylevel(Vehicle Vehicle) {
		if(Vehicle instanceof Aircraft) {
			if(skylevelVehicles.size() < Wargame.MAX_SKYLEVEL_AIRCRAFTS) {
				skylevelVehicles.add(Vehicle);
			}
			else {
				Terminal.ecrireException(new Exception());
			}
		}
	}
	
	public void removeVehicleFromGroundlevel(Vehicle Vehicle) {
		try {
			groundlevelVehicles.remove(Vehicle);
		} catch(Exception e){
			Terminal.ecrireException(new Exception());
		}
	}
	
	public void removeVehicleFromSkylevel(Vehicle Vehicle) {
		try {
			skylevelVehicles.remove(Vehicle);
		} catch(Exception e){
			Terminal.ecrireException(new Exception());
		}
	}
	
	public ArrayList<Vehicle> getGroundlevelVehicles() {
		return groundlevelVehicles;
	}
	
	public ArrayList<Vehicle> getSkylevelVehicles() {
		return skylevelVehicles;
	}
}