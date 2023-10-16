package main.java.model.ia;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Case;
import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.utils.Utils;

public class IA {

	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle(4);

		solveTaquin(puzzle);
		System.out.println(puzzle);
		System.out.println(calculerH(puzzle));

	}

	private static List<EDeplacement> solveTaquin(Puzzle puzzle) {
		List<EDeplacement> solution = new ArrayList<>();

		return solution;
	}

	private static int calculerH(Puzzle p) {
		int h = 0;
		Case[][] grille = p.getGrille();
		int compteur = 0;
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille.length; j++) {
				if (j == grille.length - 1 && i == grille.length - 1) {
					if (grille[j][i].getIndex() != Case.INDEX_CASE_VIDE)
						h += manhattanDistance(p, grille[j][i].getIndex());
				} else {
					if (compteur != grille[j][i].getIndex())
						h += manhattanDistance(p, grille[j][i].getIndex());
				}
				compteur++;
			}
		}
		return h;
	}

	private static int manhattanDistance(Puzzle p, int index) {
		Case[][] grille = p.getGrille();
		int compteur = 0;
		int distance = 0;
		int xBut, yBut;
		xBut = 0;
		yBut = 0;
		for (int i = 0; i < p.getTaille(); i++) {
			for (int j = 0; j < p.getTaille(); j++) {
				if (compteur == index) {
					xBut = j;
					yBut = i;
				}
				compteur++;
			}
		}

		Point point = chercherCoordonneesIndex(p, index);
		System.out.println("x:" + point.x + "   y:" + point.y + "   xbut:" + xBut + "     ybut:" + yBut);
		distance = Math.abs(point.x - xBut) + Math.abs(point.y - yBut);
		System.out.println(index + ": " + distance);
		return distance;
	}

	private static Point chercherCoordonneesIndex(Puzzle p, int index) {
		Case[][] grille = p.getGrille();
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille.length; j++) {
				if (grille[j][i].getIndex() == index)
					return new Point(j, i);
			}
		}
		return null;
	}
}
