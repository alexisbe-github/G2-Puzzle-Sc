package main.java.model.ia;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import main.java.model.Case;
import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.model.ia.memento.Caretaker;

public class Noeud {
	private Puzzle puzzle;
	private Noeud pere;
	private Caretaker caretaker;
	private EDeplacement dpMinimal;
	private int g;

	public Noeud(Puzzle puzzle) {
		this.puzzle = puzzle;
		caretaker = new Caretaker();
		caretaker.addMemento(puzzle.saveToMemento());
		try {
			puzzle = (Puzzle) puzzle.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		this.g = 0;
		this.dpMinimal = EDeplacement.BAS;
	}

	public List<Noeud> successeurs() {
		List<Noeud> successeurs = new ArrayList<>();
		for (EDeplacement dp : puzzle.listeDeplacementsPossibles()) {
			puzzle.deplacerCase(dp);
			Noeud successeur = new Noeud(puzzle);
			successeur.setG(this.getG() + 1);
			successeur.setPere(this);
			successeurs.add(successeur);
			this.restoreToThatNode();
		}
		return successeurs;
	}

	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void restoreToThatNode() {
		puzzle.restoreFromMemento(this.caretaker.getMemento(0));
		try {
			puzzle = (Puzzle) puzzle.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public Noeud getNoeudMinimal() {
		int min = 999999;
		int indexMinimal = 0;
		List<Noeud> successeurs = this.successeurs();
		for (int i = 0; i < puzzle.listeDeplacementsPossibles().size(); i++) {
			EDeplacement dp = puzzle.listeDeplacementsPossibles().get(i);
			Noeud noeudCourant = successeurs.get(i);
			int minCourant = this.getG() + 1 + noeudCourant.calculerH();
			if (minCourant < min) {
				indexMinimal = i;
				min = minCourant;
			}
		}
		return successeurs.get(indexMinimal);
	}

	public int calculerH() {
		this.restoreToThatNode();
		int h = 0;
		Case[][] grille = puzzle.getGrille();
		int compteur = 0;
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille.length; j++) {
				if (j == grille.length - 1 && i == grille.length - 1) {
					if (grille[j][i].getIndex() != Case.INDEX_CASE_VIDE)
						h += manhattanDistance(grille[j][i].getIndex());
				} else {
					if (compteur != grille[j][i].getIndex())
						h += manhattanDistance(grille[j][i].getIndex());
				}
				compteur++;
			}
		}
		return h;
	}

	private int manhattanDistance(int index) {
		Case[][] grille = puzzle.getGrille();
		int compteur = 0;
		int distance = 0;
		int xBut, yBut;
		xBut = 0;
		yBut = 0;
		for (int i = 0; i < puzzle.getTaille(); i++) {
			for (int j = 0; j < puzzle.getTaille(); j++) {
				if (compteur == index) {
					xBut = j;
					yBut = i;
				}
				compteur++;
			}
		}

		Point point = chercherCoordonneesIndex(index);
		distance = Math.abs(point.x - xBut) + Math.abs(point.y - yBut);
		return distance;
	}

	private Point chercherCoordonneesIndex(int index) {
		Case[][] grille = puzzle.getGrille();
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille.length; j++) {
				if (grille[j][i].getIndex() == index)
					return new Point(j, i);
			}
		}
		return null;
	}

	public void setPere(Noeud n) {
		this.pere = n;
	}

	public Noeud getPere() {
		return pere;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public EDeplacement getDeplacementMinimal() {
		return dpMinimal;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Noeud other = (Noeud) obj;
		return Objects.equals(puzzle, other.puzzle);
	}

}
