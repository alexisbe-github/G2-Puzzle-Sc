package test.java.model.bdd.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.DAOPartie;
import main.java.model.bdd.dao.DAOPartieCooperative;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.bdd.dao.beans.PartieCooperativeSQL;
import main.java.model.bdd.dao.beans.PartieSQL;

public class TestDAOPartieCooperative implements TestCRUD {

	private static DAOPartieCooperative dao;
	private static List<Long> listePartiesCooperatives = new ArrayList<>();
	private static List<Long> listeJoueurs = new ArrayList<>();
	private static List<Long> listeParties = new ArrayList<>();
	private static DAOJoueur daoJoueur;
	private static DAOPartie daoPartie;
	private static long idJoueur1, idJoueur2, idJoueur3, idJoueur4, idPartie1, idPartie2, idPartie3;

	@BeforeClass
	public static void setUp() {
		dao = new DAOPartieCooperative();
		daoJoueur = new DAOJoueur();
		daoPartie = new DAOPartie();

		JoueurSQL j1 = new JoueurSQL();
		daoJoueur.creer(j1);
		idJoueur1 = j1.getId();

		JoueurSQL j2 = new JoueurSQL();
		daoJoueur.creer(j2);
		idJoueur2 = j2.getId();

		JoueurSQL j3 = new JoueurSQL();
		daoJoueur.creer(j3);
		idJoueur3 = j3.getId();

		JoueurSQL j4 = new JoueurSQL();
		daoJoueur.creer(j4);
		idJoueur4 = j4.getId();

		PartieSQL p1 = new PartieSQL();
		daoPartie.creer(p1);
		idPartie1 = p1.getId();

		PartieSQL p2 = new PartieSQL();
		daoPartie.creer(p2);
		idPartie2 = p2.getId();

		PartieSQL p3 = new PartieSQL();
		daoPartie.creer(p3);
		idPartie3 = p3.getId();
	}

	@Test
	@Override
	public void testCreate() {
		PartieCooperativeSQL p = new PartieCooperativeSQL();
		int val = 999;
		p.setIdPartie(idPartie1);
		p.setIdJoueur(idJoueur1);
		p.setNbCoups(val);
		dao.creer(p);
		listePartiesCooperatives.add(p.getIdPartie());
		listeJoueurs.add(p.getIdJoueur());
		listeParties.add(p.getIdPartie());

		Assertions.assertEquals(dao.trouver(p.getIdPartie()).getNbCoups(), val);
	}

	@Test
	@Override
	public void testRead() {
		PartieCooperativeSQL p = new PartieCooperativeSQL();
		int val = 999;
		p.setIdPartie(idPartie1);
		p.setIdJoueur(idJoueur2);
		p.setNbCoups(val);
		dao.creer(p);
		listePartiesCooperatives.add(p.getIdPartie());
		listeJoueurs.add(p.getIdJoueur());
		listeParties.add(p.getIdPartie());

		Assertions.assertNotNull(dao.trouver(p.getIdPartie()));
	}

	@Test
	@Override
	public void testUpdate() {
		PartieCooperativeSQL p = new PartieCooperativeSQL();
		int val1 = 999, val2 = 9999;
		p.setIdPartie(idPartie2);
		p.setIdJoueur(idJoueur3);
		p.setNbCoups(val1);
		dao.creer(p);
		p.setNbCoups(val2);
		dao.maj(p);
		listePartiesCooperatives.add(p.getIdPartie());
		listeJoueurs.add(p.getIdJoueur());
		listeParties.add(p.getIdPartie());

		Assertions.assertEquals(dao.trouver(p.getIdPartie()).getNbCoups(), val2);
	}

	@Test
	@Override
	public void testDelete() {
		PartieCooperativeSQL p = new PartieCooperativeSQL();
		int val = 999;
		p.setIdPartie(idPartie3);
		p.setIdJoueur(idJoueur4);
		p.setNbCoups(val);
		dao.creer(p);
		dao.supprimer(dao.trouver(p.getIdPartie()));

		Assertions.assertEquals(dao.trouver(p.getIdPartie()).getNbCoups(), 0);
	}

	@AfterClass
	public static void cleanUp() {

		for (long id : listePartiesCooperatives) {
			dao.supprimer(dao.trouver(id));
		}

		daoPartie.supprimer(daoPartie.trouver(idPartie1));
		daoPartie.supprimer(daoPartie.trouver(idPartie2));
		daoPartie.supprimer(daoPartie.trouver(idPartie3));

		daoJoueur.supprimer(daoJoueur.trouver(idJoueur1));
		daoJoueur.supprimer(daoJoueur.trouver(idJoueur2));
		daoJoueur.supprimer(daoJoueur.trouver(idJoueur3));
		daoJoueur.supprimer(daoJoueur.trouver(idJoueur4));
	}

}