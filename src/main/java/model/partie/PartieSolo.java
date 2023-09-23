package main.java.model.partie;

import java.awt.image.BufferedImage;

import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.model.joueur.Joueur;

public class PartieSolo implements StrategyPartie {

	private Joueur joueur;
	private Puzzle puzzle;

	public PartieSolo(Joueur joueur) {
		this.joueur = joueur;
	}

	@Override
	public void lancerPartie(BufferedImage image, int taillePuzzle) {
		this.puzzle = new Puzzle(taillePuzzle, image);
	}
	
	public void deplacerCase(EDeplacement dp) {
		puzzle.deplacerCase(dp);
	}

}
