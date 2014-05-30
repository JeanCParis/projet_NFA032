package wargame;

import interfaceGraphique.Terminal;

import java.util.ArrayList;

public abstract class Square { 
	
	protected int xPositon, yPosition;
	
	protected ArrayList<Vehicle> groundlevelVehicles = new ArrayList<Vehicle>();
	protected ArrayList<Vehicle> skylevelVehicles = new ArrayList<Vehicle>();
	
	protected Square(int xPosition, int yPosition) {
		this.xPositon = xPosition;
		this.yPosition = yPosition;
	}
	
	public abstract void addVehicleToGroundlevel(Vehicle vehicle) throws IncompatibleVehiculeException, FullException;
	
	public void addVehicleToSkylevel(Vehicle vehicle) throws IncompatibleVehiculeException, FullException {
		if(vehicle instanceof Aircraft) {
			if(skylevelVehicles.size() < Game.MAX_SKYLEVEL_AIRCRAFTS) {
				skylevelVehicles.add(vehicle);
			}
			else {
				throw new FullException();
			}
		}
		else {
			throw new IncompatibleVehiculeException(vehicle);
		}
	}
	
	public void removeVehicleFromGroundlevel(Vehicle vehicle) {
		groundlevelVehicles.remove(vehicle);
	}
	
	public void removeVehicleFromSkylevel(Vehicle vehicle) {
		skylevelVehicles.remove(vehicle);
	}
	
	public ArrayList<Vehicle> getGroundlevelVehicles() {
		return groundlevelVehicles;
	}
	
	public ArrayList<Vehicle> getSkylevelVehicles() {
		return skylevelVehicles;
	}
}