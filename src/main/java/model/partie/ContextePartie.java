package main.java.model.partie;

import java.awt.image.BufferedImage;

import main.java.model.joueur.Joueur;

public class ContextePartie {

	private StrategyPartie strategyPartie;

	public ContextePartie(Joueur joueurCreateurPartie) {
		this.strategyPartie = new PartieSolo(joueurCreateurPartie);
	}

	public void lancerPartie(BufferedImage image, int taillePuzzle) {
		strategyPartie.lancerPartie(image, taillePuzzle);
	}

	public void setStrategy(StrategyPartie sp) {
		this.strategyPartie = sp;
	}
}
