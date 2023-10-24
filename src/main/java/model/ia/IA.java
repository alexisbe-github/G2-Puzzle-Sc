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

		Queue<Noeud> fermes = new LinkedList<>();
		boolean succes = false;
		while (!ouverts.isEmpty() && !succes) {
			Noeud n = getMinimum(ouverts);
			System.out.println(n.getPuzzle());
			if (puzzle.verifierGrille())
				succes = true;
			else {
				ouverts.remove(n);
				fermes.add(n);
				for (Noeud s : n.successeurs()) {
					if (!ouverts.contains(s) && !fermes.contains(s)) {
						ouverts.add(s);
						s.setPere(n);
						s.setG(n.getG() + 1);
					} else {
						if (s.getG() > n.getG() + 1) {
							s.setPere(n);
							if (fermes.contains(s)) {
								fermes.remove(s);
								ouverts.add(s);
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

	private static Noeud getMinimum(Queue<Noeud> ouverts) {
		Noeud minimumNode = null;
		int min = 9999;

		for (Noeud n : ouverts) {
			int cost = n.getG() + n.calculerH();
			if (cost < min) {
				min = cost;
				minimumNode = n;
			}
		}
		return minimumNode;
	}

}
