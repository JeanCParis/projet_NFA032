package wargame;

import interfaceGraphique.*;

public class DemoIGPA{
    public static void main(String[] a){
	IGPA igpa = new IGPA(4,2); // cree un objet IGPA avec 4 colonnes et 2 lignes
	// declaration des images utilises
	igpa.declarerImage(5,"eau.png");
	igpa.declarerImage(10,"porte-avion-eau.png");
	// declaration de la carte initiale
	int[][] init={{5,5},{5,10},{5,5},{5,5}};
	igpa.definirTerrain(init);
	igpa.creerFenetre();
	Terminal.ecrireStringln("Tapez enter pour continuer");
	Terminal.lireString();
	// deplacement du porte-avion: 2 changements de case + reaffichage
	igpa.modifierCase(1,1,5);
	igpa.modifierCase(2,0,10);
	igpa.reafficher();
	Terminal.ecrireStringln("Tapez enter pour continuer");
	Terminal.lireString();
	// il pleut des porte-avions
	igpa.modifierCase(0,0,10);
	igpa.modifierCase(1,1,10);
	igpa.modifierCase(3,1,10);
	// mais on n'a pas encore reaffiche
	Terminal.ecrireStringln("Tapez enter pour continuer");
	Terminal.lireString();
	// et maintenant, on affiche.
	igpa.reafficher();
	Terminal.ecrireStringln("Tapez enter pour continuer");
	Terminal.lireString();
	// on ferme la fenêtre
	igpa.fermer();
    }
}
