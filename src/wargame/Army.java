package wargame;

import java.util.ArrayList;

public class Army {
	protected final String name;
	protected final ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	public Army(final String name) {
		this.name = name;
	}
	
	public void addVehicle(final Vehicle Vehicle) {
		vehicles.add(Vehicle);
	}
	
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
}