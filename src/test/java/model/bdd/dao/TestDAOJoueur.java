package test.java.model.bdd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.BeforeAll;

import main.java.model.bdd.Connexion;
import main.java.model.bdd.dao.DAOJoueur;

public class TestDAOJoueur {
	
	private DAOJoueur dao;
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	@BeforeAll
	public void setUp() {
		con = Connexion.getInstance().getConnexion();
	}
	
}