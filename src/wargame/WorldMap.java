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
	
	public void createSeaSquare(int xPos, int yPos) {
		squares[xPos][yPos] = new SeaSquare(xPos, yPos);
	}
	
	public void createLandSquare(int xPos, int yPos) {
		squares[xPos][yPos] = new LandSquare(xPos, yPos);
	}
	
	public void addVehicleToGroundlevel(Vehicle Vehicle, int xPos, int yPos) {
		squares[xPos][yPos].addVehicleToGroundlevel(Vehicle);
	}
	
	public void addVehicleToSkylevel(Vehicle Vehicle, int xPos, int yPos) {
		squares[xPos][yPos].addVehicleToSkylevel(Vehicle);
	}
	
	public void removeVehicleFromGroundlevel(Vehicle Vehicle, int xPos, int yPos) {
		squares[xPos][yPos].removeVehicleFromGroundlevel(Vehicle);
	}
	
	public void removeVehicleFromSkylevel(Vehicle Vehicle, int xPos, int yPos) {
		squares[xPos][yPos].removeVehicleFromSkylevel(Vehicle);
	}
	
	public ArrayList<Vehicle> getGroundlevelVehicules(int xPos, int yPos) {
		return squares[xPos][yPos].getGroundlevelVehicles();
	}
	
	public ArrayList<Vehicle> getSkylevelVehicules(int xPos, int yPos) {
		return squares[xPos][yPos].getSkylevelVehicles();
	}
}
