package main.java.model.ia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.java.model.EDeplacement;
import main.java.model.Puzzle;

public class IA {

	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle(3);
		solveTaquin(puzzle);
		// solveTaquin(puzzle);
//		System.out.println(puzzle);
//		// System.out.println(calculerH(puzzle));
//		System.out.println(puzzle.listeDeplacementsPossibles());

	}

	private static List<EDeplacement> solveTaquin(Puzzle puzzle) {
		System.out.println(puzzle);
		List<EDeplacement> solution = new ArrayList<>();
		Queue<Noeud> ouverts = new LinkedList<>();
		ouverts.add(new Noeud(puzzle));

		List<Noeud> fermes = new ArrayList<>();
		boolean succes = false;
		while (!ouverts.isEmpty() && !succes) {
			Noeud n = ouverts.element().getNoeudMinimal();
			System.out.println(n.getPuzzle());
			if (puzzle.verifierGrille())
				succes = true;
			else {
				System.out.println(ouverts.remove(n));
				fermes.add(n);
				for (Noeud successeur : n.successeurs()) {
					if (!ouverts.contains(successeur) && !fermes.contains(successeur)) {
						ouverts.add(successeur);
					} else {
						if (successeur.getG() > n.getG() + successeur.calculerH()) {
							successeur.setPere(n);
							if (fermes.contains(successeur)) {
								fermes.remove(successeur);
								ouverts.add(successeur);
							}
						}
					}
				}
			}
		}
		for (Noeud noeud : ouverts) {
			System.out.println(noeud.getDeplacementMinimal());
		}
		return solution;
	}

}
