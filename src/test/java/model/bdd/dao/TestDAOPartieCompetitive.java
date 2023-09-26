package test.java.model.bdd.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.DAOPartie;
import main.java.model.bdd.dao.DAOPartieCompetitive;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.bdd.dao.beans.PartieCompetitiveSQL;
import main.java.model.bdd.dao.beans.PartieSQL;

public class TestDAOPartieCompetitive implements TestCRUD {

	private static DAOPartieCompetitive dao;
	private static List<Long> listePartiesCompetitives = new ArrayList<>();
	private static List<Long> listeJoueurs = new ArrayList<>();
	private static List<Long> listeParties = new ArrayList<>();
	private static DAOJoueur daoJoueur;
	private static DAOPartie daoPartie;
	private static long idJoueur1, idJoueur2, idJoueur3, idJoueur4, idJoueur5, idPartie1, idPartie2, idPartie3;

	@BeforeAll
	public static void setUp() {
		dao = new DAOPartieCompetitive();
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

		JoueurSQL j5 = new JoueurSQL();
		daoJoueur.creer(j5);
		idJoueur5 = j5.getId();

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
		PartieCompetitiveSQL p = new PartieCompetitiveSQL();
		p.setIdPartie(idPartie1);
		p.setIdJoueur(idJoueur1);
		p.setIdVainqueur(idJoueur1);
		dao.creer(p);
		listePartiesCompetitives.add(p.getIdPartie());
		listeJoueurs.add(p.getIdJoueur());
		listeParties.add(p.getIdPartie());

		Assertions.assertEquals(dao.trouver(p.getIdPartie()).getIdVainqueur(), idJoueur1);
	}

	@Test
	@Override
	public void testRead() {
		PartieCompetitiveSQL p = new PartieCompetitiveSQL();
		p.setIdPartie(idPartie1);
		p.setIdJoueur(idJoueur2);
		p.setIdVainqueur(idJoueur2);
		dao.creer(p);
		listePartiesCompetitives.add(p.getIdPartie());
		listeJoueurs.add(p.getIdJoueur());
		listeParties.add(p.getIdPartie());

		Assertions.assertNotNull(dao.trouver(p.getIdPartie()));
	}

	@Test
	@Override
	public void testUpdate() {
		PartieCompetitiveSQL p = new PartieCompetitiveSQL();
		p.setIdPartie(idPartie2);
		p.setIdJoueur(idJoueur3);
		p.setIdVainqueur(idJoueur3);
		dao.creer(p);
		p.setIdVainqueur(idJoueur4);
		dao.maj(p);
		listePartiesCompetitives.add(p.getIdPartie());
		listeJoueurs.add(p.getIdJoueur());
		listeParties.add(p.getIdPartie());

		Assertions.assertEquals(dao.trouver(p.getIdPartie()).getIdVainqueur(), idJoueur4);
	}

	@Test
	@Override
	public void testDelete() {
		PartieCompetitiveSQL p = new PartieCompetitiveSQL();
		p.setIdPartie(idPartie3);
		p.setIdJoueur(idJoueur5);
		p.setIdVainqueur(idJoueur5);
		dao.creer(p);
		dao.supprimer(dao.trouver(p.getIdPartie()));

		Assertions.assertEquals(dao.trouver(p.getIdPartie()).getIdVainqueur(), 0);
	}
	
	@Test
	@Override
	public void testFindAll() {
		PartieCompetitiveSQL p1 = new PartieCompetitiveSQL();
		PartieCompetitiveSQL p2 = new PartieCompetitiveSQL();
		PartieCompetitiveSQL p3 = new PartieCompetitiveSQL();
		dao.creer(p1);
		dao.creer(p2);
		dao.creer(p3);
		List<PartieCompetitiveSQL> parties = dao.trouverTout();
		
		Assertions.assertTrue(parties.stream().anyMatch(item -> item.equals(parties.get(0))));
		Assertions.assertTrue(parties.stream().anyMatch(item -> item.equals(parties.get(1))));
		Assertions.assertTrue(parties.stream().anyMatch(item -> item.equals(parties.get(2))));
	}

	@AfterAll
	public static void cleanUp() {

		for (long id : listePartiesCompetitives) {
			dao.supprimer(dao.trouver(id));
		}

		daoPartie.supprimer(daoPartie.trouver(idPartie1));
		daoPartie.supprimer(daoPartie.trouver(idPartie2));
		daoPartie.supprimer(daoPartie.trouver(idPartie3));

		daoJoueur.supprimer(daoJoueur.trouver(idJoueur1));
		daoJoueur.supprimer(daoJoueur.trouver(idJoueur2));
		daoJoueur.supprimer(daoJoueur.trouver(idJoueur3));
		daoJoueur.supprimer(daoJoueur.trouver(idJoueur4));
		daoJoueur.supprimer(daoJoueur.trouver(idJoueur5));
	}

}