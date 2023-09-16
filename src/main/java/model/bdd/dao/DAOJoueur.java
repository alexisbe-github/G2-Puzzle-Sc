package main.java.model.bdd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.model.bdd.dao.beans.JoueurSQL;

/**
 * La couche <code>DAO</code> secondaire qui fait le lien entre la base de
 * données et le <code>JavaBean</code>
 * {@link main.java.model.bdd.dao.beans.JoueurSQL JoueurSQL}, spécifiquement
 * pour la table <code><i>joueur</i></code> de la base de données, qui
 * enregistre les informations et les statistiques relatives aux joueurs.
 * 
 * 
 * @see JoueurSQL <code>JoueurSQL</code><a role="link" aria-disabled="true"> -
 *      Le <code>JavaBean</code> géré par la classe</a>
 * @see DAO <code>DAO</code><a role="link" aria-disabled="true"> - La couche
 *      abstraite principale dont hérite cette classe</a>
 *
 */
public class DAOJoueur extends DAO<JoueurSQL> {

	/**
	 * Table <code><i>joueur</i></code>, contenant différentes informations et
	 * statistiques sur les joueurs.
	 */
	private final String JOUEUR = "joueur";
	/**
	 * Colonne <code><i>id</i></code>, correspondant à l'identifiant des joueurs.
	 */
	private final String ID = "id";
	/**
	 * Colonne <code><i>nom</i></code>, correspondant au pseudonyme des joueurs.
	 */
	private final String PSEUDO = "pseudo";

	/**
	 * {@inheritDoc}
	 * 
	 * @param id L'identifiant du joueur
	 * @return Le joueur correspondant à l'identifiant passé en paramètre
	 * 
	 */
	@Override
	public JoueurSQL trouver(long id) {
		JoueurSQL joueur = new JoueurSQL();
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion.prepareStatement("SELECT * FROM " + JOUEUR + " WHERE " + ID + " = ?;",
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setLong(1, id);
			pstmt.execute();
			try (ResultSet rs = pstmt.getResultSet()) {
				if (rs.first()) {
					joueur.setId(id);
					joueur.setPseudo(rs.getString(PSEUDO));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return joueur;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param joueur Le joueur à créer dans la base de données
	 * @return Le joueur
	 */
	@Override
	public JoueurSQL creer(JoueurSQL joueur) {
		Connection connexion = this.connexion.getConnexion();
		try {
			try (PreparedStatement pstmt1 = connexion
					.prepareStatement("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES " + "WHERE table_name = '"
							+ JOUEUR + "'")) {
				pstmt1.execute();

				try (ResultSet rsid = pstmt1.getResultSet()) {
					if (rsid.next()) {
						long id = rsid.getLong(1);
						joueur.setId(id);
					}
				}
			}

			try (PreparedStatement pstmt2 = connexion.prepareStatement("INSERT INTO " + JOUEUR + " VALUES (?, ?);")) {
				pstmt2.setLong(1, joueur.getId());
				pstmt2.setString(2, joueur.getPseudo());
				pstmt2.execute();
			}
		} catch (SQLException e) {

		}
		return joueur;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param joueur Le joueur à mettre à jour dans la base de données
	 * @return Le joueur
	 */
	@Override
	public JoueurSQL maj(JoueurSQL joueur) {
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion
				.prepareStatement("UPDATE " + JOUEUR + " SET " + PSEUDO + " = ? WHERE " + ID + " = ?")) {
			pstmt.setString(1, joueur.getPseudo());
			pstmt.setLong(2, joueur.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return joueur;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param joueur Le joueur à supprimer de la base de données
	 */
	@Override
	public void supprimer(JoueurSQL joueur) {
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion.prepareStatement("DELETE FROM " + JOUEUR + " WHERE " + ID + " = ?;")) {
			pstmt.setLong(1, joueur.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}