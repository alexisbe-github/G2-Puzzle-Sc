package main.java.model.ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.utils.Utils;

public class IA {

	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle(3);
		System.out.println(puzzle);
		List<EDeplacement> solveur = solveTaquin(puzzle);
		for (EDeplacement dp : solveur) {
			puzzle.deplacerCase(dp);
			System.out.println(dp);
			//System.out.println(puzzle);
		}
		System.out.println(solveur.size());

	}
	
	private static List<EDeplacement> solveTaquin4(Puzzle puzzle) {
		List<EDeplacement> solution = new ArrayList<>();
		Deque<Noeud> ouverts = new LinkedList<>();
		ouverts.add(new Noeud(puzzle));
		
		Noeud noeudTmp = chercherNoeudResolu(ouverts);
		while (noeudTmp.getPere() != null) {
			solution.add(noeudTmp.getdeplacement());
			noeudTmp = noeudTmp.getPere();
		}
		Collections.reverse(solution);
		return solution;
	}

	private static List<EDeplacement> solveTaquin3(Puzzle puzzle) {
		List<EDeplacement> solution = new ArrayList<>();
		Deque<Noeud> ouverts = new LinkedList<>();
		ouverts.add(new Noeud(puzzle));

		Deque<Noeud> fermes = new LinkedList<>();
		boolean succes = false;
		while (!ouverts.isEmpty() && !succes) {
			Noeud n = ouverts.element();
			if (n.getPuzzle().verifierGrille())
				succes = true;
			else {
				ouverts.remove(n);
				fermes.add(n);
				for (Noeud s : n.successeurs()) {
					if (!ouverts.contains(s) && !fermes.contains(s)) {
						ouverts.add(s);
						s.setPere(n);
					} 
				}
			}
		}
		Noeud noeudTmp = chercherNoeudResolu(ouverts);
		while (noeudTmp.getPere() != null) {
			solution.add(noeudTmp.getdeplacement());
			noeudTmp = noeudTmp.getPere();
		}
		Collections.reverse(solution);
		return solution;
	}

	public static List<EDeplacement> solveTaquin(Puzzle puzzle) {
		List<EDeplacement> solution = new ArrayList<>();
		Deque<Noeud> ouverts = new LinkedList<>();
		ouverts.add(new Noeud(puzzle));

		Deque<Noeud> fermes = new LinkedList<>();
		boolean succes = false;
		while (!ouverts.isEmpty() && !succes) {
			Noeud n = getMinimum(ouverts);
			if (n.getPuzzle().verifierGrille())
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
						if (s.getG() > n.getG() + Math.abs(s.getG() - n.getG())) {
							s.setPere(n);
							s.setG(n.getG() + Math.abs(s.getG() - n.getG()));
							if (fermes.contains(s)) {
								fermes.remove(s);
								ouverts.add(s);
							}
						}
					}
				}
			}
		}
		Noeud noeudTmp = chercherNoeudResolu(ouverts);
		while (noeudTmp.getPere() != null) {
			solution.add(noeudTmp.getdeplacement());
			noeudTmp = noeudTmp.getPere();
		}
		Collections.reverse(solution);
		return solution;
	}

	/**
	 * Solveur en déplacements aléatoires (taille < 4)
	 * 
	 * @param puzzle
	 * @return
	 */
	private static List<EDeplacement> solveTaquin2(Puzzle puzzle) {
		List<EDeplacement> solution = new ArrayList<>();

		boolean succes = false;
		Noeud noeudCourant = new Noeud(puzzle);
		while (!succes) {
			if (noeudCourant.getPuzzle().verifierGrille()) {
				succes = true;
			}
			List<Noeud> successeurs = noeudCourant.successeurs();
			Noeud successeurChoisi;
			do {
				int random = Utils.getRandomNumberInRange(0, successeurs.size() - 1);
				successeurChoisi = successeurs.get(random);
			} while (successeurChoisi.deplacementNulPere());
			successeurChoisi.setPere(noeudCourant);
			noeudCourant = successeurChoisi;
		}

		while (noeudCourant.getPere() != null) {
			solution.add(noeudCourant.getdeplacement());
			noeudCourant = noeudCourant.getPere();
		}
		Collections.reverse(solution);
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

	private static Noeud chercherNoeudResolu(Queue<Noeud> ouverts) {
		Noeud noeudResolu = null;
		for (Noeud noeud : ouverts) {
			if (noeud.getPuzzle().verifierGrille())
				noeudResolu = noeud;
		}
		return noeudResolu;
	}

}
