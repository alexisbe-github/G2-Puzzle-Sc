package main.java.model.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.resources.utils.EnvironmentVariablesUtils;

public enum Connexion
{
    INSTANCE;

    // instance vars, constructor
    private Connection connection = null;

    Connexion()
    {
    	try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			this.connection = DriverManager.getConnection(EnvironmentVariablesUtils.getBDDURL(),
					EnvironmentVariablesUtils.getBDDUSER(), EnvironmentVariablesUtils.getBDDMDP());

		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Erreur lors de la connexion : " + e.getMessage());
		}
    }

    // Static getter
    public static Connexion getInstance()
    {
        return INSTANCE;
    }

    public Connection getConnection()
    {
        return connection;
    }
}
