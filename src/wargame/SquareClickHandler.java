package wargame;

import java.awt.event.ActionEvent;

public class SquareClickHandler extends MyActionListener {
	protected final Square square;

	public SquareClickHandler(final Square square) {
		this.square = square;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		game.squareClicked(square);
	}
}
