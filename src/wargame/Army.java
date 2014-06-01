package wargame;

import java.util.ArrayList;

public class Army {
	protected String name;
	protected ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	public Army(String name) {
		this.name = name;
	}
	
	public void addVehicle(Vehicle Vehicle) {
		vehicles.add(Vehicle);
	}
	
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
}