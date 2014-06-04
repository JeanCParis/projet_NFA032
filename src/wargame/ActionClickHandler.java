package wargame;

import java.awt.event.ActionEvent;

public class ActionClickHandler extends MyActionListener {
	protected final ActionType action;
	
	public ActionClickHandler(final ActionType action) {
		this.action = action;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		game.actionClicked(action);
	} 
}
