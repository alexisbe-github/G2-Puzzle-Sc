package main.java.model.partie;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.model.joueur.Joueur;

public abstract class PartieMultijoueur implements StrategyPartie {

	protected List<Joueur> joueurs;
	protected Map<Joueur, Puzzle> tablePuzzleDesJoueurs;
	protected Map<Joueur, Socket> tableSocketDesJoueurs;

	public List<Joueur> getJoueurs() {
		return this.joueurs;
	}

	public abstract void deplacerCase(EDeplacement dp, Joueur joueur, int numJoueur) throws IOException;

	public Puzzle getPuzzleDuJoueur(Joueur j) {
		return tablePuzzleDesJoueurs.get(j);
	}

	public void ajouterJoueur(Joueur j, Socket s) {
		joueurs.add(j);
		tableSocketDesJoueurs.put(j, s);
		System.out.println(joueurs);
	}

	public void deconnecterJoueur(Joueur j) {
		joueurs.remove(j);
		tablePuzzleDesJoueurs.remove(j);
		tableSocketDesJoueurs.remove(j);
	}

}
