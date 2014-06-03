package wargame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VehicleClickHandler extends MyActionListener {
	protected Vehicle vehicle;

	public VehicleClickHandler(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		game.vehicleClicked(vehicle);
	}

}
