package main.java.model.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.resources.utils.EnvironmentVariablesUtils;

/**
 * Connexion à une base de données
 *
 */

public class Connexion {

	private static Connexion instance = null;
	private Connection con;

	/**
	 * Se connecte à la base de données spécifiée dans le fichier
	 * <code>ENVIRONMENT.properties</code>.
	 *
	 * @see main.resources.utils.EnvironmentVariablesUtils
	 */
	private Connexion() {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			this.con = DriverManager.getConnection(EnvironmentVariablesUtils.getBDDURL(),
					EnvironmentVariablesUtils.getBDDUSER(), EnvironmentVariablesUtils.getBDDMDP());

		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Erreur lors de la connexion : " + e.getMessage());
		}

	}

	/**
	 * Singleton renvoyant la connexion.
	 *
	 * @return La connexion
	 */
	public static Connexion getInstance() {
		if (instance == null) {
			instance = new Connexion();
		}
		return instance;
	}

	/**
	 * Ferme la connexion.
	 * 
	 * @return <code>true</code> en cas de succès, <code>false</code> en cas d'échec 
	 */
	public boolean fermerConnexion() {
		try {
			this.con.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 *
	 * @return La connexion sous forme d'objet sur lequel on peut exécuter des
	 *         requêtes
	 */
	public Connection getConnexion() {
		return this.con;
	}

}
