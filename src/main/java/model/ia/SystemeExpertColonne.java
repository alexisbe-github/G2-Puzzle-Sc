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
				res = (puzzle.getXCaseVide() - coordonneesIndex.x == 0
						&& puzzle.getYCaseVide() - coordonneesIndex.y == -1) || (puzzle.getXCaseVide() - coordonneesIndex.x == -1
						&& puzzle.getYCaseVide() - coordonneesIndex.y == 0) ;
			}
		} while (!res);
		if(deplacerIndexVersCaseVide(puzzle, index, chercherCoordonneesIndex(index, puzzle)) != null) {
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

		// cas où la case vide se trouve en dessous
//		if (puzzle.getYCaseVide() == coordonneesIndex.y + 1 && puzzle.getXCaseVide() == coordonneesIndex.x) {
//			puzzle.deplacerCase(EDeplacement.DROITE);
//			System.out.println(puzzle);
//			puzzle.deplacerCase(EDeplacement.BAS);
//			System.out.println(puzzle);
//		}
		//cas où la case x=0 même colonne juste en dessous
		if (puzzle.getXCaseVide() == 0 && coordonneesIndex.x == 0 && coordonneesIndex.y - puzzle.getYCaseVide() == -1) {
			puzzle.deplacerCase(EDeplacement.GAUCHE);
			System.out.println(puzzle);
			puzzle.deplacerCase(EDeplacement.BAS);
			System.out.println(puzzle);
			puzzle.deplacerCase(EDeplacement.BAS);
			System.out.println(puzzle);
			puzzle.deplacerCase(EDeplacement.DROITE);
			System.out.println(puzzle);
		}
		
		// cas où la case est en bas à gauche
		if (puzzle.getXCaseVide() == 0 && puzzle.getYCaseVide() == puzzle.getTaille() - 1) {
			puzzle.deplacerCase(EDeplacement.GAUCHE);
			System.out.println(puzzle);
			puzzle.deplacerCase(EDeplacement.BAS);
			System.out.println(puzzle);
		}

		// cas où la case à déplacer doit se retrouver vers la gauche
		if (getDistanceX(index, puzzle) > 0) {
			// si la case vide ne se trouve pas à gauche de la case à déplacer
			if (!(puzzle.getXCaseVide() == coordonneesIndex.x - 1 && puzzle.getYCaseVide() == coordonneesIndex.y)) {

				// on déplace la case vide à gauche de la case à déplacer

				if (puzzle.getXCaseVide() == 1 && puzzle.getYCaseVide() != coordonneesIndex.y) {
					puzzle.deplacerCase(EDeplacement.GAUCHE);
					System.out.println(puzzle);
					puzzle.deplacerCase(EDeplacement.HAUT);
					System.out.println(puzzle);
					puzzle.deplacerCase(EDeplacement.HAUT);
					System.out.println(puzzle);
					puzzle.deplacerCase(EDeplacement.DROITE);
					System.out.println(puzzle);
				} else {
					// si la case vide est en dessous même colonne
					if (puzzle.getXCaseVide() == coordonneesIndex.x && puzzle.getYCaseVide() > coordonneesIndex.y) {
						puzzle.deplacerCase(EDeplacement.DROITE);
						System.out.println(puzzle);
						puzzle.deplacerCase(EDeplacement.BAS);
						System.out.println(puzzle);
					} else {

						// si la case vide est à droite et pas sur la même ligne
						if (puzzle.getYCaseVide() != coordonneesIndex.y
								&& puzzle.getXCaseVide() >= coordonneesIndex.x) {
							puzzle.deplacerCase(EDeplacement.DROITE);
							System.out.println(puzzle);
						} else {
							// si la case vide est est à gauche et pas sur la même ligne
							if (puzzle.getYCaseVide() != coordonneesIndex.y
									&& puzzle.getXCaseVide() < coordonneesIndex.x - 1) {
								puzzle.deplacerCase(EDeplacement.GAUCHE);
								System.out.println(puzzle);
							} else {
								// si la case vide est sur la même ligne et à gauche
								if (puzzle.getYCaseVide() == coordonneesIndex.y
										&& puzzle.getXCaseVide() < coordonneesIndex.x - 1) {
									puzzle.deplacerCase(EDeplacement.GAUCHE);
									System.out.println(puzzle);
								} else {
									// si la case vide est sur la même ligne et à droite
									if (puzzle.getYCaseVide() == coordonneesIndex.y
											&& puzzle.getXCaseVide() > coordonneesIndex.x + 1) {
										puzzle.deplacerCase(EDeplacement.GAUCHE);
										System.out.println(puzzle);
									}
								}
							}
						}
					}
				}
			}
		} else {
			// cas où la case à déplacer doit se retrouver vers la droite
			if (getDistanceX(index, puzzle) < 0) {

				if (!(puzzle.getXCaseVide() == coordonneesIndex.x + 1 && puzzle.getYCaseVide() == coordonneesIndex.y)) {

					// on déplace la case vide à gauche de la case à déplacer

					// si la case vide est sur la même colonne et au dessus de la case vide
					if (puzzle.getXCaseVide() == coordonneesIndex.x && puzzle.getYCaseVide() < coordonneesIndex.y - 1) {
						puzzle.deplacerCase(EDeplacement.GAUCHE);
						System.out.println(puzzle);
						puzzle.deplacerCase(EDeplacement.HAUT);
						System.out.println(puzzle);
						puzzle.deplacerCase(EDeplacement.HAUT);
						System.out.println(puzzle);
						puzzle.deplacerCase(EDeplacement.DROITE);
						System.out.println(puzzle);
					} else {
						// si la case vide est à droite et pas sur la même ligne
						if (puzzle.getYCaseVide() != coordonneesIndex.y
								&& puzzle.getXCaseVide() > coordonneesIndex.x + 1
								&& !(puzzle.getYCaseVide() < (index / puzzle.getTaille())
										&& puzzle.getXCaseVide() == 1)) {
							puzzle.deplacerCase(EDeplacement.DROITE);
							System.out.println(puzzle);
						} else {
							// si la case vide est à gauche et pas sur la même ligne
							if (puzzle.getYCaseVide() != coordonneesIndex.y
									&& puzzle.getXCaseVide() <= coordonneesIndex.x) {
								puzzle.deplacerCase(EDeplacement.GAUCHE);
								System.out.println(puzzle);
							} else {
								// si la case vide est sur la même ligne et à gauche
								if (puzzle.getYCaseVide() == coordonneesIndex.y
										&& puzzle.getXCaseVide() < coordonneesIndex.x - 1) {
									puzzle.deplacerCase(EDeplacement.GAUCHE);
									System.out.println(puzzle);
								} else {
									// si la case vide est sur la même ligne et à droite
									if (puzzle.getYCaseVide() == coordonneesIndex.y
											&& puzzle.getXCaseVide() > coordonneesIndex.x + 1
											&& !(puzzle.getYCaseVide() < (index / puzzle.getTaille())
													&& puzzle.getXCaseVide() == 1)) {
										puzzle.deplacerCase(EDeplacement.DROITE);
										System.out.println(puzzle);
									}
								}
							}
						}
					}
				}
			} else {
				if (!(puzzle.getXCaseVide() == coordonneesIndex.x + 1 && puzzle.getYCaseVide() == coordonneesIndex.y)) {

					// on déplace la case vide à la même colonne que de la case à déplacer

					// si la case vide est à droite et pas sur la même ligne
					if (puzzle.getYCaseVide() != coordonneesIndex.y && puzzle.getXCaseVide() >= coordonneesIndex.x
							&& !(puzzle.getYCaseVide() < (index / puzzle.getTaille()) && puzzle.getXCaseVide() == 1)) {
						puzzle.deplacerCase(EDeplacement.DROITE);
						System.out.println(puzzle);
					} else {
						// si la case vide est est à gauche et pas sur la même ligne
						if (puzzle.getYCaseVide() != coordonneesIndex.y && puzzle.getXCaseVide() < coordonneesIndex.x) {
							puzzle.deplacerCase(EDeplacement.GAUCHE);
							System.out.println(puzzle);
						} else {
							// si la case vide est sur la même ligne et à gauche
							if (puzzle.getYCaseVide() == coordonneesIndex.y
									&& puzzle.getXCaseVide() < coordonneesIndex.x - 1) {
								puzzle.deplacerCase(EDeplacement.GAUCHE);
								System.out.println(puzzle);
							} else {
								// si la case vide est sur la même ligne et à droite
								if (puzzle.getYCaseVide() == coordonneesIndex.y
										&& puzzle.getXCaseVide() > coordonneesIndex.x + 1) {
									puzzle.deplacerCase(EDeplacement.DROITE);
									System.out.println(puzzle);
								}
							}
						}
					}

				}
			}
		}
	}

	private static void deplacerEnYColonne(Puzzle puzzle, int index, Point coordonneesIndex) {

		// si la case à déplacer n'est pas sur la même ligne que là où elle doit être
		if (getDistanceY(index, puzzle) != 0) {

			// on déplace la case vide à gauche ou à droite de la case à déplacer

			// si la case vide est au dessus et pas sur la même colonne
			if (puzzle.getXCaseVide() != coordonneesIndex.x && puzzle.getYCaseVide() < coordonneesIndex.y) {
				puzzle.deplacerCase(EDeplacement.HAUT);
				System.out.println(puzzle);
			} else {
				// si la case vide est est en dessous et pas sur la même colonne
				if (puzzle.getXCaseVide() != coordonneesIndex.x && puzzle.getYCaseVide() > coordonneesIndex.y
						&& !(puzzle.getYCaseVide() == (index / puzzle.getTaille()) && puzzle.getXCaseVide() == 0)) {
					puzzle.deplacerCase(EDeplacement.BAS);
					System.out.println(puzzle);
				} else {
					// si la case vide est sur la même ligne et à droite de la case à déplacer
					if (puzzle.getYCaseVide() == coordonneesIndex.y && puzzle.getXCaseVide() > coordonneesIndex.x
							&& !(puzzle.getYCaseVide() == (index / puzzle.getTaille()) && puzzle.getXCaseVide() == 0)) {
						if(puzzle.getYCaseVide() == puzzle.getTaille()-1) {
							puzzle.deplacerCase(EDeplacement.BAS);
							System.out.println(puzzle);
							puzzle.deplacerCase(EDeplacement.DROITE);
							System.out.println(puzzle);
						}else {
						puzzle.deplacerCase(EDeplacement.HAUT);
						System.out.println(puzzle);
						}
					} else {
						// si la case vide est sur la même ligne et à gauche de la case à déplacer
						if (puzzle.getYCaseVide() == coordonneesIndex.y && puzzle.getXCaseVide() < coordonneesIndex.x) {
							if (puzzle.getYCaseVide() == puzzle.getTaille() - 1) {
								puzzle.deplacerCase(EDeplacement.BAS);
								System.out.println(puzzle);
								puzzle.deplacerCase(EDeplacement.GAUCHE);
							} else {
								if (puzzle.getYCaseVide() == 1) {
									puzzle.deplacerCase(EDeplacement.HAUT);
									System.out.println(puzzle);
									puzzle.deplacerCase(EDeplacement.GAUCHE);
								} else {
									puzzle.deplacerCase(EDeplacement.HAUT);
								}
							}
							System.out.println(puzzle);

						} else {
							// si la case vide est sur la même colonne et au dessus de la case vide
							if (puzzle.getXCaseVide() == coordonneesIndex.x
									&& puzzle.getYCaseVide() < coordonneesIndex.y - 1) {
								puzzle.deplacerCase(EDeplacement.HAUT);
								System.out.println(puzzle);
							} else {
								// si la case vide est sur la même colonne et en dessous de la case vide
								if (puzzle.getXCaseVide() == coordonneesIndex.x
										&& puzzle.getYCaseVide() > coordonneesIndex.y + 1
										&& !(puzzle.getYCaseVide() == (index / puzzle.getTaille())
												&& puzzle.getXCaseVide() == 0)) {
									puzzle.deplacerCase(EDeplacement.DROITE);
									System.out.println(puzzle);
									puzzle.deplacerCase(EDeplacement.BAS);
									System.out.println(puzzle);
								}
							}
						}
					}
				}
			}
		} else {
			// si la case à déplacer est sur la même ligne que là où elle doit être

			// si la case vide est au dessus et pas sur la même colonne
			if (puzzle.getXCaseVide() != coordonneesIndex.x && puzzle.getYCaseVide() < coordonneesIndex.y) {
				puzzle.deplacerCase(EDeplacement.HAUT);
				System.out.println(puzzle);
			} else {
				// si la case vide est en dessous et pas sur la même colonne
				if (puzzle.getXCaseVide() != coordonneesIndex.x && puzzle.getYCaseVide() > coordonneesIndex.y
						&& !(puzzle.getYCaseVide() == (index / puzzle.getTaille()) && puzzle.getXCaseVide() == 0)) {
					puzzle.deplacerCase(EDeplacement.BAS);
					System.out.println(puzzle);
				} else {
					// si la case vide est sur la même ligne et à droite de la case à déplacer
					if (puzzle.getYCaseVide() == coordonneesIndex.y && puzzle.getXCaseVide() > coordonneesIndex.x) {
						if (puzzle.getYCaseVide() != puzzle.getTaille() - 1) {
							puzzle.deplacerCase(EDeplacement.HAUT);
							System.out.println(puzzle);
							puzzle.deplacerCase(EDeplacement.DROITE);
							System.out.println(puzzle);
							puzzle.deplacerCase(EDeplacement.DROITE);
							System.out.println(puzzle);
							puzzle.deplacerCase(EDeplacement.BAS);
						} else {
							puzzle.deplacerCase(EDeplacement.BAS);
						}
						System.out.println(puzzle);
					} else {
						// si la case vide est sur la même ligne et à gauche de la case à déplacer
						if (puzzle.getYCaseVide() == coordonneesIndex.y && puzzle.getXCaseVide() < coordonneesIndex.x) {
							puzzle.deplacerCase(EDeplacement.HAUT);
							System.out.println(puzzle);
						} else {
							// si la case vide est sur la même colonne et au dessus
							if (puzzle.getYCaseVide() < coordonneesIndex.y - 1
									&& puzzle.getXCaseVide() == coordonneesIndex.x) {
								puzzle.deplacerCase(EDeplacement.HAUT);
								System.out.println(puzzle);
							} else {
								// si la case vide est sur la même colonne et en dessous

								if (puzzle.getYCaseVide() > coordonneesIndex.y + 1
										&& puzzle.getXCaseVide() == coordonneesIndex.x
										&& !(puzzle.getYCaseVide() == (index / puzzle.getTaille())
												&& puzzle.getXCaseVide() == 0)) {
									puzzle.deplacerCase(EDeplacement.BAS);
									System.out.println(puzzle);
								}
							}
						}
					}
				}
			}
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
