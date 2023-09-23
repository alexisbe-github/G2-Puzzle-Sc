package main.java.model.serveur;

import main.java.model.joueur.Joueur;
import main.java.model.partie.ContextePartie;
import main.java.model.partie.PartieMultijoueurCooperative;

public class TestServeur {

	public static void main(String[] args) {
		Serveur s = new Serveur();
		Joueur joueurHote = new Joueur("Pop simoké",null);
		ContextePartie cp = new ContextePartie(joueurHote);
		//PartieMultijoueurCooperative pmCoop = new PartieMultijoueurCooperative();
		//cp.setStrategy(pmCoop);
		//cp.lancerPartie(null, 3);
		//cp.setStrategy();
		//s.lancerServeur();
	}
}
