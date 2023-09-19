package test.java.model.bdd.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import main.java.model.bdd.Connexion;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;

public class TestDAOJoueur implements TestCRUD {

	private static DAOJoueur dao;
	private static List<Long> listeJoueurs = new ArrayList<>();

	@BeforeClass
	public static void setUp() {
		dao = new DAOJoueur();
	}

	@Test
	@Override
	public void testCreate() {
		JoueurSQL j = new JoueurSQL();
		String pseudo = "Test Créer";
		j.setPseudo(pseudo);
		dao.creer(j);
		listeJoueurs.add(j.getId());

		Assertions.assertEquals(dao.trouver(j.getId()).getPseudo(), pseudo);
	}

	@Test
	@Override
	public void testRead() {
		JoueurSQL j = new JoueurSQL();
		dao.creer(j);
		listeJoueurs.add(j.getId());

		Assertions.assertNotNull(dao.trouver(j.getId()));
	}

	@Test
	@Override
	public void testUpdate() {
		JoueurSQL j = new JoueurSQL();
		String pseudo = "Test Màj";
		String pseudo2 = "Retest Màj";
		j.setPseudo(pseudo);
		dao.creer(j);
		j.setPseudo(pseudo2);
		dao.maj(j);
		listeJoueurs.add(j.getId());

		Assertions.assertEquals(dao.trouver(j.getId()).getPseudo(), pseudo2);
	}

	@Test
	@Override
	public void testDelete() {
		JoueurSQL j = new JoueurSQL();
		String pseudo = "Test Supprimer";
		j.setPseudo(pseudo);
		dao.creer(j);
		dao.supprimer(dao.trouver(j.getId()));

		Assertions.assertNull(dao.trouver(j.getId()).getPseudo());
	}

	@AfterClass
	public static void cleanUp() {
		for (long id : listeJoueurs) {
			dao.supprimer(dao.trouver(id));
		}
	}

}