package wargame;

import javax.swing.JButton;

public class SquareButton extends JButton {
	int xPosition, yPosition;
	
	public SquareButton(int xPosition, int yPosition)  {
		super();
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
