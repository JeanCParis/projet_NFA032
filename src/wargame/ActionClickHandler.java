package wargame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionClickHandler extends MyActionListener {
	protected ActionType action;
	
	public ActionClickHandler(ActionType action) {
		this.action = action;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		game.actionClicked(action);
	} 
}
