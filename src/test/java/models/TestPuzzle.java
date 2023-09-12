package test.java.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.model.Case;
import main.java.model.EDeplacement;
import main.java.model.Puzzle;

public class TestPuzzle {

	private static Puzzle puzzle;
	private final static int TAILLE = 3;

	@BeforeAll
	public static void setUp() {
		puzzle = new Puzzle(TAILLE);
	}

	@Test
	public void testConstructor() {
		int tailleMauvaise = 2;
		Puzzle p = new Puzzle(tailleMauvaise);
		Assertions.assertTrue(p.getGrille().length > 2,
				"La taille du puzzle devrait être supérieure à 2, actuellement:" + tailleMauvaise);

		int tailleMauvaise2 = -1;
		Puzzle p2 = new Puzzle(tailleMauvaise2);
		Assertions.assertTrue(p2.getGrille().length > 2,
				"La taille du puzzle devrait être supérieure à 2, actuellement:" + tailleMauvaise2);

	}

	@Test
	public void testMelangerGrille() {
		Case[][] grille1 = puzzle.getGrille().clone();
		puzzle.melanger();
		Case[][] grille2 = puzzle.getGrille();

		Assertions.assertNotEquals(grille1, grille2, "Les 2 grilles sont identiques, elles n'ont pas été mélangées.");
	}

	@Test
	public void verifierGrille() {
		Case[][] grilleCorrecte = new Case[TAILLE][TAILLE];
		Case[][] grilleIncorrecte = new Case[TAILLE][TAILLE];

		int compteur = 0;
		for (int i = 0; i < TAILLE; i++) {
			for (int j = 0; j < TAILLE; j++) {
				if (i != 0 && j != 0) {
					grilleCorrecte[i][j] = new Case(compteur);
					grilleIncorrecte[i][j] = new Case(compteur);
				}
				compteur++;
			}
		}

		grilleCorrecte[0][0] = new Case(-1);
		int numChangement = grilleIncorrecte[TAILLE - 1][TAILLE - 2].getIndex();
		grilleIncorrecte[TAILLE - 1][TAILLE - 2] = new Case(-1);
		grilleIncorrecte[0][0] = new Case(numChangement);

		puzzle.setGrille(grilleCorrecte);
		Assertions.assertTrue(puzzle.verifierGrille(), "La grille devrait être correcte");

		puzzle.setGrille(grilleIncorrecte);
		Assertions.assertFalse(puzzle.verifierGrille(), "La grille devrait être incorrecte");
	}

	@Test
	public void testDeplacerCaseHaut() {
		int oldX, oldY;
		oldX = puzzle.getXCaseVide();
		oldY = puzzle.getYCaseVide();

		puzzle.deplacerCase(EDeplacement.HAUT);

		int newX, newY;
		newX = puzzle.getXCaseVide();
		newY = puzzle.getYCaseVide();

		if (oldY == puzzle.getTaille()) {
			Assertions.assertEquals(oldY, newY,
					"Le déplacement vers le haut n'est pas possible lorsque la case vide se trouve tout en bas (" + oldY
							+ ") or elle se trouve maintenant en " + newY);
		} else {
			Assertions.assertEquals(oldY, newY + 1,
					"La case vide se trouve en " + newY + ", elle devait se trouver en " + (newY + 1));
		}
	}

	@Test
	public void testDeplacerCaseGauche() {
		int oldX, oldY;
		oldX = puzzle.getXCaseVide();
		oldY = puzzle.getYCaseVide();

		puzzle.deplacerCase(EDeplacement.GAUCHE);

		int newX, newY;
		newX = puzzle.getXCaseVide();
		newY = puzzle.getYCaseVide();

		if (oldX == puzzle.getTaille()) {
			Assertions.assertEquals(oldX, newX,
					"Le déplacement vers la gauche n'est pas possible lorsque la case vide se trouve tout à droite ("
							+ oldX + ") or elle se trouve maintenant en " + newX);
		} else {
			Assertions.assertEquals(oldX, newX + 1,
					"La case vide se trouve en " + newX + ", elle devait se trouver en " + (newX + 1));
		}
	}

	@Test
	public void testDeplacerCaseDroit() {
		int oldX, oldY;
		oldX = puzzle.getXCaseVide();
		oldY = puzzle.getYCaseVide();

		puzzle.deplacerCase(EDeplacement.DROITE);

		int newX, newY;
		newX = puzzle.getXCaseVide();
		newY = puzzle.getYCaseVide();

		if (oldX == 0) {
			Assertions.assertEquals(oldX, newX,
					"Le déplacement vers la droite n'est pas possible lorsque la case vide se trouve tout à gauche ("
							+ oldX + ") or elle se trouve maintenant en " + newX);
		} else {
			Assertions.assertEquals(oldX, newX - 1,
					"La case vide se trouve en " + newX + ", elle devait se trouver en " + (newX - 1));
		}
	}

	@Test
	public void testDeplacerCaseBas() {
		int oldX, oldY;
		oldX = puzzle.getXCaseVide();
		oldY = puzzle.getYCaseVide();

		puzzle.deplacerCase(EDeplacement.BAS);

		int newX, newY;
		newX = puzzle.getXCaseVide();
		newY = puzzle.getYCaseVide();

		if (oldY == 0) {
			Assertions.assertEquals(oldY, newY,
					"Le déplacement vers le bas n'est pas possible lorsque la case vide se trouve tout en haut (" + oldY
							+ ") or elle se trouve maintenant en " + newY);
		} else {
			Assertions.assertEquals(oldY, newY - 1,
					"La case vide se trouve en " + newY + ", elle devait se trouver en " + (newY + 1));
		}
	}
}
