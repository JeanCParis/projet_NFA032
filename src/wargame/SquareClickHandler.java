package wargame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SquareClickHandler implements ActionListener {
	protected static Game game;
	
	public static void setGame(Game thisGame) {
		game = thisGame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		game.squareClicked();
	}
}
