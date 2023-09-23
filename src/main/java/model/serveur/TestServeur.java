package main.java.model.serveur;

import main.java.model.joueur.Joueur;
import main.java.model.partie.ContextePartie;
import main.java.model.partie.PartieMultijoueurCooperative;

public class TestServeur {

	public static void main(String[] args) {
		Joueur joueurHote = new Joueur("Pop simoké",null);
		ContextePartie cp = new ContextePartie(joueurHote);
		PartieMultijoueurCooperative pmCoop = new PartieMultijoueurCooperative(joueurHote);
		cp.setStrategy(pmCoop);
		cp.lancerPartie(null, 3);
		Serveur.lancerServeur(pmCoop);
	}
}
