package wargame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SquareClickHandler implements ActionListener {
	protected static Game game;
	protected Square square;

	public SquareClickHandler(final Square square) {
		this.square = square;
	}

	public static void setGame(final Game thisGame) {
		game = thisGame;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		game.squareClicked(square.getXPositon(), square.getYPosition());
	}
}
