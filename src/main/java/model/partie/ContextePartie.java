package main.java.model.partie;

import java.awt.image.BufferedImage;

import main.java.model.joueur.Joueur;

public class ContextePartie {

	private StrategyPartie strategyPartie;

	/**
	 * Création du contexte du design pattern strategy de la partie
	 * 
	 * @param joueurCreateurPartie, créateur/hôte de la partie Par défaut la partie
	 *                              instanciée est une
	 *                              {@link main.java.model.partie.PartieSolo}
	 */
	public ContextePartie(Joueur joueurCreateurPartie) {
		this.strategyPartie = new PartieSolo(joueurCreateurPartie);
	}

	/**
	 * Méthode de lancement de strategy
	 * 
	 * @param image        BufferedImage
	 * @param taillePuzzle int
	 */
	public void lancerPartie(BufferedImage image, int taillePuzzle) {
		strategyPartie.lancerPartie(image, taillePuzzle);
	}

	public void setStrategy(StrategyPartie sp) {
		this.strategyPartie = sp;
	}
}
