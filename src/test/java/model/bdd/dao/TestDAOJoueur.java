package test.java.model.bdd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import main.java.model.bdd.Connexion;
import main.java.model.bdd.dao.DAOJoueur;

public class TestDAOJoueur implements TestCRUD{
	
	private DAOJoueur dao;
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	@BeforeAll
	public void setUp() {
		con = Connexion.getInstance().getConnexion();
	}

	@Test
	@Override
	public void testCreate() {
		
	}

	@Test
	@Override
	public void testRead() {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testDelete() {
		// TODO Auto-generated method stub
		
	}
	
}