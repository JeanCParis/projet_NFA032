package wargame;

import java.util.ArrayList;

public class WorldMap {
	protected int xSize, ySize;
	protected Square[][] squares;

	public WorldMap(final int xSize, final int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		squares = new Square[xSize][ySize];
	}

	public void initialisation(final int[][] keysTable) {
		if (keysTable.length != xSize || keysTable[0].length != ySize) {
			throw new RuntimeException();
		} else {
			for (int i = 0; i < xSize; ++i) {
				for (int j = 0; j < ySize; ++j) {
					switch (keysTable[i][j]) {
					case Game.SEA_KEY:
						squares[i][j] = new SeaSquare(i, j);
						break;
					case Game.LAND_KEY:
						squares[i][j] = new LandSquare(i, j);
						break;
					default:
						throw new RuntimeException();
					}
				}
			}
		}
	}

	public Square getSquare(final int xPosition, final int yPosition) {
		return squares[xPosition][yPosition];
	}

	public SquareType getType(final int xPosition, final int yPosition) {
		return squares[xPosition][yPosition].getType();
	}

	public void createSeaSquare(final int xPosition, final int yPosition) {
		squares[xPosition][yPosition] = new SeaSquare(xPosition, yPosition);
	}

	public void createLandSquare(final int xPosition, final int yPosition) {
		squares[xPosition][yPosition] = new LandSquare(xPosition, yPosition);
	}

	public void addVehicleToGroundlevel(final Vehicle Vehicle,
			final int xPosition, final int yPosition)
			throws IncompatibleVehiculeException, FullException {
		squares[xPosition][yPosition].addVehicleToGroundlevel(Vehicle);
	}

	public void addVehicleToSkylevel(final Vehicle Vehicle,
			final int xPosition, final int yPosition)
			throws IncompatibleVehiculeException, FullException {
		squares[xPosition][yPosition].addVehicleToSkylevel(Vehicle);
	}

	public void removeVehicleFromGroundlevel(final Vehicle Vehicle,
			final int xPosition, final int yPosition) {
		squares[xPosition][yPosition].removeVehicleFromGroundlevel(Vehicle);
	}

	public void removeVehicleFromSkylevel(final Vehicle Vehicle,
			final int xPosition, final int yPosition) {
		squares[xPosition][yPosition].removeVehicleFromSkylevel(Vehicle);
	}

	public ArrayList<Vehicle> getGroundlevelVehicules(final int xPosition,
			final int yPosition) {
		return squares[xPosition][yPosition].getGroundlevelVehicles();
	}

	public ArrayList<Vehicle> getSkylevelVehicules(final int xPosition,
			final int yPosition) {
		return squares[xPosition][yPosition].getSkylevelVehicles();
	}
}
