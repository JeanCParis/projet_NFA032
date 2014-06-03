package wargame;

import interfaceGraphique.*;

public class Wargame {
	
	public static Game game;
	
	public static void main(String[] args) {
		game = new Game();
		game.start();
	}
}
