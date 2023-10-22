package test.java.model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.scene.image.Image;
import main.java.model.Case;
import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieSolo;

public class TestPartieSolo {

	private static PartieSolo partie;
	private static Joueur joueur;
	private static Case[][] grille;
	private final static int TAILLE = 3;

	@BeforeAll
	public static void setUp() {
		joueur = new Joueur("Joueur", null);
		partie = new PartieSolo(joueur);
		grille = new Case[][] { { new Case(1), new Case(2), new Case(3) }, { new Case(4), new Case(-1), new Case(5) },
				{ new Case(6), new Case(7), new Case(8) } };
	}

	@Test
	public void testLancerPartie() throws IOException {
		partie.lancerPartie(new Image(new File("src/test/resources/testimg.jpg").toURI().toURL().toString()), TAILLE);
		Puzzle puzzle = partie.getPuzzle();
		Assertions.assertEquals(puzzle.getTaille(), TAILLE);
		Assertions.assertEquals(puzzle.getNbCoups(), 0);
	}

	@Test
	public void testDeplacerCase() throws IOException {
		partie.lancerPartie(new Image(new File("src/test/resources/testimg.jpg").toURI().toURL().toString()), TAILLE);
		Puzzle puzzle = partie.getPuzzle();
		puzzle.setGrille(grille);

		int oldX, oldY, newX, newY;
		oldX = puzzle.getXCaseVide();
		oldY = puzzle.getYCaseVide();

		partie.deplacerCase(EDeplacement.HAUT);
		newX = puzzle.getXCaseVide();
		newY = puzzle.getYCaseVide();

		Assertions.assertEquals(oldX, newX);
		Assertions.assertEquals(oldY + 1, newY);
	}
}
