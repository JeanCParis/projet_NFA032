package interfaceGraphique;

import java.awt.*;
import javax.swing.*;
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
					images.get(jeu[i][j]).paintIcon(this,g,i*48+24,j*48+24);
			    }
		}
    }
}

public class IGPA extends JFrame {
    private int[][] jeu;
    private HashMap<Integer,ImageIcon>  images;
    private SpecialPanel jpane;
    
    public IGPA(int x, int y) {
		jeu = new int[x][y];
		images = new  HashMap<Integer,ImageIcon>();
    }
    
    public void creerFenetre(){
		if (! this.isVisible()){
		    this.setTitle("Terrain");
		    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    jpane = new SpecialPanel(jeu, images);
		    this.setContentPane(jpane);
		    jpane.setPreferredSize(new Dimension(jeu.length*48+48,
							 jeu[0].length*48+48));
		    jpane.setBackground(Color.black);
		    this.pack();
		    this.setVisible(true);
		}
    }
    
    public void definirTerrain(int[][]  je){
		if (jeu.length != je.length && jeu[0].length != je[0].length){
		    throw new TailleErreur();
		}
		for (int i = 0; i<je.length; i++){
		    for (int j=0; j<je[0].length; j++){
		    	jeu[i][j]=je[i][j];
		    }
		}
    }
    
    public void declarerImage(int c, String s){
    	images.put(c,new ImageIcon("images/" + s));
    }
    
    public void modifierCase(int x, int y, int val){
    	jeu[x][y]=val;
    }
    
    public void reafficher(){
    	jpane.repaint();
    }
    
    public void fermer(){
    	this.dispose();
    }
}

class TailleErreur extends RuntimeException{}

