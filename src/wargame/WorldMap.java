package wargame;

import java.util.ArrayList;

public class WorldMap {
	protected final int xSize, ySize;
	protected final Square[][] squares;

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
						squares[i][j] = new SeaSquare(i, j, SquareType.SEA);
						break;
					case Game.LAND_KEY:
						squares[i][j] = new LandSquare(i, j, SquareType.LAND);
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

	public ArrayList<Vehicle> getGroundlevelVehicles(final int xPosition,
			final int yPosition) {
		return squares[xPosition][yPosition].getGroundlevelVehicles();
	}

	public ArrayList<Vehicle> getSkylevelVehicles(final int xPosition,
			final int yPosition) {
		return squares[xPosition][yPosition].getSkylevelVehicles();
	}
}
