package main.java.model.partie;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.model.joueur.Joueur;

public class PartieMultijoueurCompetitive extends PartieMultijoueur {

	public PartieMultijoueurCompetitive() {
		joueurs = new ArrayList<>();
		tablePuzzleDesJoueurs = new HashMap<>();
		tableSocketDesJoueurs = new HashMap<>();
	}

	@Override
	public void lancerPartie(BufferedImage image, int taillePuzzle) {
		for (Joueur j : joueurs) {
			Puzzle puzzleDuJoueurJ = new Puzzle(taillePuzzle, image);
			tablePuzzleDesJoueurs.put(j, puzzleDuJoueurJ);
		}
	}

	@Override
	public void deplacerCase(EDeplacement dp, Joueur joueur, int numJoueur) throws IOException {
		Puzzle puzzleDuJoueur = tablePuzzleDesJoueurs.get(joueur);
		puzzleDuJoueur.deplacerCase(dp);
	}

	/**
	 * Vérifie si un joueur de la partie a gagné
	 * @return boolean si un joueur a gagné
	 */
	public boolean unJoueurAGagne() {
		//parcours de tous les joueurs et si une grille est vérifiée alors la partie est finie et on return true
		for (Map.Entry<Joueur, Puzzle> mapEntry : tablePuzzleDesJoueurs.entrySet()) {
			Joueur j = mapEntry.getKey();
			Puzzle p = mapEntry.getValue();
			if (p.verifierGrille()) {
				return true;
			}
		}
		return false;
	}

}
