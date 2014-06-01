package wargame;

import java.util.ArrayList;

public class WorldMap {
	protected int xSize, ySize;
	protected Square[][] squares;
	
	public WorldMap(int xSize, int ySize){
		this.xSize = xSize;
		this.ySize = ySize;
		squares = new Square[xSize][ySize];
	}
	
	public void initialisation(int[][] keysTable) {
		if(keysTable.length != xSize || keysTable[0].length != ySize) {
			throw new RuntimeException();
		}
		else {
			for (int i=0 ; i<xSize ; ++i) {
				for (int j=0 ; j<ySize ; ++j) {
					switch(keysTable[i][j]) {
						case Game.SEA_KEY :
							squares[i][j] = new SeaSquare(i,j);
							break;
						case Game.LAND_KEY :
							squares[i][j] = new LandSquare(i,j);
							break;
						default :
							throw new RuntimeException();
					}
				}
			}
		}
	}
	
	public SquareType getType(int xPosition, int yPosition) {
		return squares[xPosition][yPosition].getType();
	}
	
	public void createSeaSquare(int xPosition, int yPosition) {
		squares[xPosition][yPosition] = new SeaSquare(xPosition, yPosition);
	}
	
	public void createLandSquare(int xPosition, int yPosition) {
		squares[xPosition][yPosition] = new LandSquare(xPosition, yPosition);
	}
	
	public void addVehicleToGroundlevel(Vehicle Vehicle, int xPosition, int yPosition) throws IncompatibleVehiculeException, FullException {
		squares[xPosition][yPosition].addVehicleToGroundlevel(Vehicle);
	}
	
	public void addVehicleToSkylevel(Vehicle Vehicle, int xPosition, int yPosition) throws IncompatibleVehiculeException, FullException {
		squares[xPosition][yPosition].addVehicleToSkylevel(Vehicle);
	}
	
	public void removeVehicleFromGroundlevel(Vehicle Vehicle, int xPosition, int yPosition) {
		squares[xPosition][yPosition].removeVehicleFromGroundlevel(Vehicle);
	}
	
	public void removeVehicleFromSkylevel(Vehicle Vehicle, int xPosition, int yPosition) {
		squares[xPosition][yPosition].removeVehicleFromSkylevel(Vehicle);
	}
	
	public ArrayList<Vehicle> getGroundlevelVehicules(int xPosition, int yPosition) {
		return squares[xPosition][yPosition].getGroundlevelVehicles();
	}
	
	public ArrayList<Vehicle> getSkylevelVehicules(int xPosition, int yPosition) {
		return squares[xPosition][yPosition].getSkylevelVehicles();
	}
}
