package main.java.model.bdd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public enum Connexion {
	INSTANCE;

	private Connection connection = null;

	Connexion() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource dataSource = (DataSource) envContext.lookup("jdbc/taquin");
			this.connection = dataSource.getConnection();
		} catch (NamingException | SQLException e) {
			System.err.println("Erreur lors de la connexion : " + e.getMessage());
		}
	}

	public static Connexion getInstance() {
		return INSTANCE;
	}

	public Connection getConnection() {
		return connection;
	}

	/**
	 * Ferme la connexion
	 */
	public void fermerConnexion() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {

			}
			connection = null;
		}
	}
}
