package wargame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class TerminalInputHandler implements ActionListener {
	protected static Game game;
	
	public static void setGame(Game thisGame) {
		game = thisGame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField field = (JTextField)e.getSource();
		game.inputRead(field.getText());
	}
	
}
