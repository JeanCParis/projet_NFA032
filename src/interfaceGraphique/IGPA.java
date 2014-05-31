package interfaceGraphique;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import wargame.Game;
import wargame.SquareClickHandler;
import wargame.SquareButton;
import wargame.TerminalInputHandler;

import java.util.*;

class SpecialPanel extends JPanel {
    int[][] jeu;
    Color blanc = Color.white;
    Color noir = new Color(150,120,120);
    private HashMap<Integer,ImageIcon>  images;
    
    SpecialPanel(int[][]  je, HashMap<Integer,ImageIcon> im){
		jeu = je;
		images = im;
		this.setForeground(Color.YELLOW);
    }
    
    public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		for (int i=0; i<jeu.length; i++){
			    g.drawString(""+i,i*48+44,15);
			    g.drawString(""+i,i*48+48,jeu[0].length*48+40);			    
			    for (int j=0; j<jeu[0].length; j++){
					g.drawString(""+j,7,j*48+48);
					g.drawString(""+j,jeu.length*48+30,j*48+48);
			    }
		}
    }
}

public class IGPA extends JFrame {
    private int[][] jeu;
    private JButton[][] squares;
    private JButton[] groundVehicleChoices;
    private JButton[] skyVehicleChoices;
    private JScrollPane outputTerminalScrollPane; 
    private JTextArea outputTerminal;
    private JTextField inputTerminal;
    private HashMap<Integer,ImageIcon>  images;
    private SpecialPanel jpane;
    
    public IGPA(int x, int y) {
		jeu = new int[x][y];
		squares = new JButton[x][y];
		groundVehicleChoices = new JButton[4];//temp
		skyVehicleChoices = new JButton[4];//temp
		outputTerminal = new JTextArea();
		outputTerminalScrollPane = new JScrollPane(outputTerminal);
		inputTerminal = new JTextField();
		images = new  HashMap<Integer,ImageIcon>();
    }
    
    public void creerFenetre(){
		if (! this.isVisible()){
		    this.setTitle("Wargame");
		    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    jpane = new SpecialPanel(jeu, images);
		    this.setContentPane(jpane);
		    jpane.setPreferredSize(new Dimension(jeu.length*Game.SQUARE_SIZE+48+120,
							 jeu[0].length*Game.SQUARE_SIZE+48+150));
		    jpane.setBackground(Color.black);
		    jpane.setLayout(null);
		    this.pack();
		    this.setVisible(true);
		    
		    //outputTerminal
		    outputTerminal.setBounds(32, jeu[0].length*Game.SQUARE_SIZE+48+10, jeu.length*Game.SQUARE_SIZE - 20, 150 - 50);
		    outputTerminal.setBackground(new Color(0,100,0));
		    outputTerminal.setEditable(false);
		    DefaultCaret caret = (DefaultCaret)outputTerminal.getCaret();
		    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);//astuce pour que le dernier string ajouté soit visible
			//outputTerminalScrollPane
		    outputTerminalScrollPane.setBounds(32, jeu[0].length*Game.SQUARE_SIZE+48+10, jeu.length*Game.SQUARE_SIZE - 20, 150 - 50);
		    jpane.add(outputTerminalScrollPane);
			//inputTerminal
			inputTerminal.setBounds(outputTerminal.getX(), outputTerminal.getY() + outputTerminal.getHeight(), outputTerminal.getWidth(), 20);
			inputTerminal.setBackground(new Color(0,120,0));
			inputTerminal.addActionListener(new TerminalInputHandler());
			jpane.add(inputTerminal);
		}
    }
    
    public void definirTerrain(int[][]  je){
		if (jeu.length != je.length && jeu[0].length != je[0].length){
		    throw new TailleErreur();
		}
		for (int i = 0; i<je.length; i++){
		    for (int j=0; j<je[0].length; j++){
		    	jeu[i][j]=je[i][j];
		    	JButton button = new SquareButton(i, j);
		    	button.addActionListener(new SquareClickHandler());
		    	button.setBounds(Game.BORDER + i*Game.SQUARE_SIZE, Game.BORDER + j*Game.SQUARE_SIZE, Game.SQUARE_SIZE, Game.SQUARE_SIZE);
		    	button.setIcon(images.get(jeu[i][j]));
		    	button.setVisible(true);
		    	squares[i][j]=button;
		    	jpane.add(button);
		    }
		}
		reafficher();
    }
    
    public void declarerImage(int c, String s){
    	images.put(c,new ImageIcon("images/" + s));
    }
    
    public void modifierCase(int x, int y, int val){
    	jeu[x][y]=val;
    	squares[x][y].setIcon(images.get(val));
    }
    
    public void writeOnTerminal(String string) {
    	outputTerminal.append(string);
    }
    
    public void writeOnTerminalln(String string) {
    	outputTerminal.append(string + "\n");
    }
    
    public void enableInputFromTerminal() {
    	inputTerminal.setEditable(true);
    }
    
    public void disableInputFromTerminal() {
    	inputTerminal.setText("");
    	inputTerminal.setEditable(false);
    }
   
    public void reafficher(){
    	jpane.repaint();
    }
    
    public void fermer(){
    	this.dispose();
    }
}

class TailleErreur extends RuntimeException{}

