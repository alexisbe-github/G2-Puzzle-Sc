package main.java.model.partie;

import java.awt.image.BufferedImage;

import main.java.model.Puzzle;
import main.java.model.joueur.Joueur;

public class PartieMultijoueurCooperative extends PartieMultijoueur{
	
	private Puzzle puzzleCommun;
	private int indexJoueurCourant; //index qui indique quel joueur de la List<Joueur> joueurs doit jouer son tour
	private int nbCoups;
	
	/**
	 * Construit une partie multijoueur cooperative se jouant tour par tour à partir d'un joueur hôte
	 * @param joueurHote
	 */
	public PartieMultijoueurCooperative(Joueur joueurHote) {
		indexJoueurCourant = 0;
		joueurs.add(joueurHote);
		nbCoups = 0;
	}

	@Override
	public void lancerPartie(BufferedImage image, int taillePuzzle) {
		puzzleCommun = new Puzzle(taillePuzzle,image);
	}

}
