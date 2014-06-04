package wargame;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

public class TerminalInputHandler extends MyActionListener {
	protected JTextField field;

	public TerminalInputHandler(final JTextField field) {
		this.field = field;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		game.inputRead(field.getText());
	}

}
