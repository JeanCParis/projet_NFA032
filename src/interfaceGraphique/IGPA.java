package interfaceGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import wargame.Game;
import wargame.SquareButton;
import wargame.SquareClickHandler;
import wargame.TerminalInputHandler;
import wargame.Vehicle;
import wargame.VehicleClickHandler;
import wargame.WorldMap;

class SpecialPanel extends JPanel {
	int[][] jeu;
	Color blanc = Color.white;
	Color noir = new Color(150, 120, 120);
	private final HashMap<Integer, ImageIcon> images;

	SpecialPanel(final int[][] je, final HashMap<Integer, ImageIcon> im) {
		jeu = je;
		images = im;
		this.setForeground(Color.YELLOW);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < jeu.length; i++) {
			g.drawString("" + i, i * 48 + 44, 15);
			g.drawString("" + i, i * 48 + 48, jeu[0].length * 48 + 40);
			for (int j = 0; j < jeu[0].length; j++) {
				g.drawString("" + j, 7, j * 48 + 48);
				g.drawString("" + j, jeu.length * 48 + 30, j * 48 + 48);
			}
		}
	}
}

public class IGPA extends JFrame {
	private final int[][] jeu;
	private final JButton[][] squares;
	private ArrayList<JButton> leftVehicleChoices;
	private ArrayList<JButton> rightVehicleChoices;
	private final JScrollPane outputTerminalScrollPane;
	private final JTextArea outputTerminal;
	private final JTextField inputTerminal;
	private final HashMap<Integer, ImageIcon> images;
	private SpecialPanel jpane;

	public IGPA(final int x, final int y) {
		jeu = new int[x][y];
		squares = new JButton[x][y];
		leftVehicleChoices = new ArrayList<JButton>();
		rightVehicleChoices = new ArrayList<JButton>();
		outputTerminal = new JTextArea();
		outputTerminalScrollPane = new JScrollPane(outputTerminal);
		inputTerminal = new JTextField();
		images = new HashMap<Integer, ImageIcon>();
	}

	public void creerFenetre() {
		if (!this.isVisible()) {
			this.setTitle("Wargame");
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jpane = new SpecialPanel(jeu, images);
			this.setContentPane(jpane);
			jpane.setPreferredSize(new Dimension(jeu.length * Game.SQUARE_SIZE
					+ 48 + 120, jeu[0].length * Game.SQUARE_SIZE + 48 + 150));
			jpane.setBackground(Color.black);
			jpane.setLayout(null);
			this.pack();
			this.setVisible(true);

			// outputTerminal
			outputTerminal.setBounds(32, jeu[0].length * Game.SQUARE_SIZE + 48
					+ 10, jeu.length * Game.SQUARE_SIZE - 20, 150 - 50);
			outputTerminal.setBackground(new Color(0, 100, 0));
			outputTerminal.setEditable(false);
			final DefaultCaret caret = (DefaultCaret) outputTerminal.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);// astuce pour que
																// le dernier
																// string ajout�
																// soit visible
			// outputTerminalScrollPane
			outputTerminalScrollPane.setBounds(32, jeu[0].length
					* Game.SQUARE_SIZE + 48 + 10, jeu.length * Game.SQUARE_SIZE
					- 20, 150 - 50);
			jpane.add(outputTerminalScrollPane);
			// inputTerminal
			inputTerminal.setBounds(outputTerminal.getX(),
					outputTerminal.getY() + outputTerminal.getHeight(),
					outputTerminal.getWidth(), 20);
			inputTerminal.setBackground(new Color(0, 120, 0));
			inputTerminal.addActionListener(new TerminalInputHandler(
					inputTerminal));
			jpane.add(inputTerminal);
		}
	}

	public void definirTerrain(final WorldMap map, final int[][] je) {
		if (jeu.length != je.length && jeu[0].length != je[0].length) {
			throw new TailleErreur();
		}
		for (int i = 0; i < je.length; i++) {
			for (int j = 0; j < je[0].length; j++) {
				jeu[i][j] = je[i][j];
				final JButton button = new SquareButton(i, j);
				button.addActionListener(new SquareClickHandler(map.getSquare(
						i, j)));
				button.setBounds(Game.BORDER + i * Game.SQUARE_SIZE,
						Game.BORDER + j * Game.SQUARE_SIZE, Game.SQUARE_SIZE,
						Game.SQUARE_SIZE);
				button.setIcon(images.get(jeu[i][j]));
				squares[i][j] = button;
				jpane.add(button);
			}
		}
		jpane.repaint();
	}

	public void declarerImage(final int c, final String s) {
		images.put(c, new ImageIcon("images/" + s));
	}

	public void modifierCase(final int x, final int y, final int val) {
		jeu[x][y] = val;
		squares[x][y].setIcon(images.get(val));
	}

	public void reafficher() {
		jpane.repaint();
	}

	public void fermer() {
		this.dispose();
	}

	public void writeOnTerminal(final String string) {
		outputTerminal.append(string);
	}

	public void writeOnTerminalln(final String string) {
		outputTerminal.append(string + "\n");
	}

	public void enableInputFromTerminal() {
		inputTerminal.setEditable(true);
	}

	public void disableInputFromTerminal() {
		inputTerminal.setText("");
		inputTerminal.setEditable(false);
	}

	public void clearVehicleChoices() {
		for (final JButton button : leftVehicleChoices) {
			jpane.remove(button);
		}
		leftVehicleChoices = new ArrayList<JButton>();

		for (final JButton button : rightVehicleChoices) {
			jpane.remove(button);
		}
		rightVehicleChoices = new ArrayList<JButton>();
	}

	public void addLeftVehicleChoice(final Vehicle vehicle, final int val) {
		final JButton button = new JButton(images.get(val));
		button.setBounds(squares[9][0].getX() + Game.SQUARE_SIZE + 30,
				squares[9][0].getY() + leftVehicleChoices.size()
						* Game.SQUARE_SIZE, Game.SQUARE_SIZE, Game.SQUARE_SIZE);
		button.addActionListener(new VehicleClickHandler(vehicle));
		jpane.add(button);
		jpane.repaint();
		leftVehicleChoices.add(button);
	}

	public void addRightVehicleChoice(final Vehicle vehicle, final int val) {
		final JButton button = new JButton(images.get(val));
		button.setBounds(squares[9][0].getX() + 2 * Game.SQUARE_SIZE + 40,
				squares[9][0].getY() + rightVehicleChoices.size()
						* Game.SQUARE_SIZE, Game.SQUARE_SIZE, Game.SQUARE_SIZE);
		button.addActionListener(new VehicleClickHandler(vehicle));
		jpane.add(button);
		jpane.repaint();
		rightVehicleChoices.add(button);
	}
}

class TailleErreur extends RuntimeException {
}
