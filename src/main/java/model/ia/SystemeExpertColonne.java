package main.java.model.ia;

import java.awt.Point;

import main.java.model.Case;
import main.java.model.EDeplacement;
import main.java.model.Puzzle;

public class SystemeExpertColonne {

	public void solveColonne(Puzzle puzzle) {
		System.out.println("=======================ON START COLONNE==============");
		for (int index = 1; index < puzzle.getTaille() - 1; index++) {
			while (manhattanDistance(index * puzzle.getTaille(), puzzle) != 0) {
				deplacer(puzzle, index * puzzle.getTaille());
				System.out.println(index * puzzle.getTaille());
			}
		}
	}

	private static void deplacer(Puzzle puzzle, int index) {
		boolean res;

		do {
			Point coordonneesIndex = chercherCoordonneesIndex(index, puzzle);
			deplacerEnXColonne(puzzle, index, coordonneesIndex);
			deplacerEnYColonne(puzzle, index, coordonneesIndex);
			res = true;
			if (getDistanceY(index, puzzle) > 0) {
				res = (puzzle.getXCaseVide() == coordonneesIndex.x && puzzle.getYCaseVide() == coordonneesIndex.y - 1)
						|| (puzzle.getXCaseVide() - coordonneesIndex.x == -1
								&& puzzle.getYCaseVide() - coordonneesIndex.y == 0);
			} else {
				if (getDistanceY(index, puzzle) < 0) {
					res = (puzzle.getXCaseVide() - coordonneesIndex.x == 0
							&& puzzle.getYCaseVide() - coordonneesIndex.y == 1)
							|| (puzzle.getXCaseVide() - coordonneesIndex.x == -1
									&& puzzle.getYCaseVide() - coordonneesIndex.y == 0);
				} else {
					res = (puzzle.getXCaseVide() - coordonneesIndex.x == -1
							&& puzzle.getYCaseVide() - coordonneesIndex.y == 0);
				}
			}

		} while (!res);
		if (deplacerIndexVersCaseVide(puzzle, index, chercherCoordonneesIndex(index, puzzle)) != null) {
			puzzle.deplacerCase(deplacerIndexVersCaseVide(puzzle, index, chercherCoordonneesIndex(index, puzzle)));
			System.out.println(puzzle);
		}
	}

	private static EDeplacement deplacerIndexVersCaseVide(Puzzle puzzle, int index, Point coordonneesIndex) {
		if (puzzle.getXCaseVide() - coordonneesIndex.x == 0 && puzzle.getYCaseVide() - coordonneesIndex.y == 1
				&& puzzle.getYCaseVide() != 0) {
			return EDeplacement.BAS;
		}
		if (puzzle.getXCaseVide() - coordonneesIndex.x < 0 && puzzle.getYCaseVide() - coordonneesIndex.y == 0) {
			return EDeplacement.GAUCHE;
		}
		if (puzzle.getXCaseVide() - coordonneesIndex.x == 0 && puzzle.getYCaseVide() - coordonneesIndex.y == -1) {
			return EDeplacement.HAUT;
		}

		return null;
	}

	private static void deplacerEnXColonne(Puzzle puzzle, int index, Point coordonneesIndex) {

		//cas où la case est sur la même ligne qu'elle doit être
		if(getDistanceY(index, puzzle)==0) {
			
			//cas où la case vide est à droite 2eme ligne de la case à déplacer
			if(puzzle.getXCaseVide()>coordonneesIndex.x && puzzle.getYCaseVide() == 2) {
				puzzle.deplacerCase(EDeplacement.HAUT);
				System.out.println(puzzle);
				puzzle.deplacerCase(EDeplacement.DROITE);
				System.out.println(puzzle);
				puzzle.deplacerCase(EDeplacement.DROITE);
				System.out.println(puzzle);
				puzzle.deplacerCase(EDeplacement.BAS);
			}
			
			//cas où la case est en dessous
			if(puzzle.getYCaseVide() > coordonneesIndex.y ) {
				
				//cas où la case vide est en dessous à gauche
				if(puzzle.getXCaseVide()< coordonneesIndex.x) {
					puzzle.deplacerCase(EDeplacement.DROITE);
					System.out.println(puzzle);
				}else {
					//cas où la case vide est en dessous à droite
					if(puzzle.getXCaseVide()> coordonneesIndex.x) {
						puzzle.deplacerCase(EDeplacement.GAUCHE);
						System.out.println(puzzle);
					}else {
						//cas où elle est juste en dessous même colonne
						if(puzzle.getXCaseVide() == coordonneesIndex.x) {
							puzzle.deplacerCase(EDeplacement.DROITE);
							System.out.println(puzzle);
							puzzle.deplacerCase(EDeplacement.BAS);
							System.out.println(puzzle);
						}
						
					}
				}
			}
		}else {
			
			//cas où la case n'est pas sur la même ligne qu'elle doit être
			
			//cas où la case est à gauche
			if(puzzle.getXCaseVide()<coordonneesIndex.x) {
				puzzle.deplacerCase(EDeplacement.GAUCHE);
				System.out.println(puzzle);
			}else {
				
				//cas où la case est à droite de la case vide
				if(puzzle.getXCaseVide()>coordonneesIndex.x-1) {
					puzzle.deplacerCase(EDeplacement.DROITE);
				}
			}
		}
	}

	private static void deplacerEnYColonne(Puzzle puzzle, int index, Point coordonneesIndex) {

		//cas où la case est plus haut que ce qu'elle devrait être
		if(getDistanceY(index, puzzle)>0) {
			
			//cas où la case vide est au dessus
			//if(puzzle.getYCaseVide() == 0)
			
		}else {
			
			//cas où la case est plus basse que ce qu'elle devrait être
		}
	}

	private static int manhattanDistance(int index, Puzzle puzzle) {
		int distance = getDistanceXAbs(index, puzzle) + getDistanceYAbs(index, puzzle);
		return distance;
	}

	private static int getDistanceXAbs(int index, Puzzle puzzle) {
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

		Point point = chercherCoordonneesIndex(index, puzzle);
		distance = Math.abs(point.x - xBut);
		return distance;
	}

	private static int getDistanceYAbs(int index, Puzzle puzzle) {
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

		Point point = chercherCoordonneesIndex(index, puzzle);
		distance = Math.abs(point.y - yBut);
		return distance;
	}

	private static int getDistanceX(int index, Puzzle puzzle) {
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

		Point point = chercherCoordonneesIndex(index, puzzle);
		distance = point.x - xBut;
		return distance;
	}

	private static int getDistanceY(int index, Puzzle puzzle) {
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

		Point point = chercherCoordonneesIndex(index, puzzle);
		distance = point.y - yBut;
		return distance;
	}

	private static Point chercherCoordonneesIndex(int index, Puzzle puzzle) {
		Case[][] grille = puzzle.getGrille();
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille.length; j++) {
				if (grille[j][i].getIndex() == index)
					return new Point(j, i);
			}
		}
		return null;
	}
}
