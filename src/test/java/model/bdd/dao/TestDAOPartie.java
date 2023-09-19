package test.java.model.bdd.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import main.java.model.bdd.dao.DAOPartie;
import main.java.model.bdd.dao.beans.PartieSQL;

public class TestDAOPartie implements TestCRUD {

	private static DAOPartie dao;
	private static List<Long> listeParties = new ArrayList<>();

	@BeforeClass
	public static void setUp() {
		dao = new DAOPartie();
	}

	@Test
	@Override
	public void testCreate() {
		PartieSQL p = new PartieSQL();
		int val = 999;
		p.setDureeSecondes(val);
		p.setTailleGrille(val);
		dao.creer(p);
		listeParties.add(p.getId());

		Assertions.assertEquals(dao.trouver(p.getId()).getDureeSecondes(), val);
		Assertions.assertEquals(dao.trouver(p.getId()).getTailleGrille(), val);
	}

	@Test
	@Override
	public void testRead() {
		PartieSQL p = new PartieSQL();
		dao.creer(p);
		listeParties.add(p.getId());

		Assertions.assertNotNull(dao.trouver(p.getId()));
	}

	@Test
	@Override
	public void testUpdate() {
		PartieSQL p = new PartieSQL();
		int val = 999;
		int val2 = 9999;
		p.setDureeSecondes(val);
		p.setTailleGrille(val);
		dao.creer(p);
		p.setDureeSecondes(val2);
		p.setTailleGrille(val2);
		dao.maj(p);
		listeParties.add(p.getId());

		Assertions.assertEquals(dao.trouver(p.getId()).getDureeSecondes(), val2);
		Assertions.assertEquals(dao.trouver(p.getId()).getTailleGrille(), val2);
	}

	@Test
	@Override
	public void testDelete() {
		PartieSQL p = new PartieSQL();
		int val = 999;
		p.setDureeSecondes(val);
		p.setTailleGrille(val);
		dao.creer(p);
		dao.supprimer(dao.trouver(p.getId()));

		Assertions.assertEquals(dao.trouver(p.getId()).getDureeSecondes(), 0);
		Assertions.assertEquals(dao.trouver(p.getId()).getTailleGrille(), 0);
	}

	@AfterClass
	public static void cleanUp() {
		for (long id : listeParties) {
			dao.supprimer(dao.trouver(id));
		}
	}

}