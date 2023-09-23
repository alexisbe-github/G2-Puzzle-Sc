package main.java.model.partie;

import java.awt.image.BufferedImage;

import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.model.joueur.Joueur;

public class PartieMultijoueurCooperative extends PartieMultijoueur {

	private Puzzle puzzleCommun;
	private int indexJoueurCourant; // index qui indique quel joueur de la List<Joueur> joueurs doit jouer son tour

	/**
	 * Construit une partie multijoueur cooperative se jouant tour par tour à partir
	 * d'un joueur hôte
	 * 
	 * @param joueurHote
	 */
	public PartieMultijoueurCooperative(Joueur joueurHote) {
		indexJoueurCourant = 0;
		joueurs.add(joueurHote);
	}

	@Override
	public void lancerPartie(BufferedImage image, int taillePuzzle) {
		puzzleCommun = new Puzzle(taillePuzzle, image);
	}

	/**
	 * Incrémente l'index du joueur qui joue son tour dans la liste de joueurs
	 */
	private void passerAuJoueurSuivant() {
		this.indexJoueurCourant++;
		this.indexJoueurCourant %= joueurs.size();
	}

	/**
	 * 
	 * @param dp        EDeplacement de la case
	 * @param numJoueur numero du joueur dans la liste
	 */
	public void deplacerCase(EDeplacement dp, int numJoueur) {
		if (numJoueur == this.indexJoueurCourant - 1) {
			puzzleCommun.deplacerCase(dp);
			passerAuJoueurSuivant();
			System.out.println(puzzleCommun);
		}
	}

}
