package wargame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VehicleClickHandler implements ActionListener {
	protected static Game game;
	protected Vehicle vehicle;

	public VehicleClickHandler(final Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public static void setGame(final Game thisGame) {
		game = thisGame;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		game.vehicleClicked(vehicle);
	}

}
