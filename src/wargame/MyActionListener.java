package wargame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener implements ActionListener {
	protected static Game game;
	
	public static void setGame(final Game thisGame) {
		game = thisGame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	} 
}

