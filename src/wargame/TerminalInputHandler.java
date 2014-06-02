package wargame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class TerminalInputHandler implements ActionListener {
	protected static Game game;
	protected JTextField field;

	public TerminalInputHandler(final JTextField field) {
		this.field = field;
	}

	public static void setGame(final Game thisGame) {
		game = thisGame;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		game.inputRead(field.getText());
	}

}
