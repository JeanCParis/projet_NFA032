package wargame;

import javax.swing.JButton;

public class SquareButton extends JButton {
	protected final int xPosition, yPosition;
	
	public SquareButton(final int xPosition, final int yPosition)  {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}
}
