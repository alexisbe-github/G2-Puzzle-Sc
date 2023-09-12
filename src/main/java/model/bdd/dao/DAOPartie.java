package main.java.model.bdd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import main.java.model.bdd.dao.beans.PartieSQL;

/**
 * La couche <code>DAO</code> secondaire qui fait le lien entre la base de
 * données et le <code>JavaBean</code>
 * {@link main.java.model.bdd.dao.beans.PartieSQL PartieSQL}, spécifiquement
 * pour la table <code><i>partie</i></code> de la base de données, qui
 * enregistre les informations relatives aux parties.
 * 
 * 
 * @see PartieSQL <code>PartieSQL</code><a role="link" aria-disabled="true"> -
 *      Le <code>JavaBean</code> géré par la classe</a>
 * @see DAO <code>DAO</code><a role="link" aria-disabled="true"> - La couche
 *      abstraite principale dont hérite cette classe</a>
 *
 */
public class DAOPartie extends DAO<PartieSQL> {

	/**
	 * Table <code><i>partie</i></code>, contenant différentes informations et
	 * statistiques sur les parties.
	 */
	private final String PARTIE = "partie";
	/**
	 * Colonne <code><i>id</i></code>, correspondant à l'identifiant de la partie.
	 */
	private final String ID = "id";
	/**
	 * Colonne <code><i>duree_secondes</i></code>, correspondant à la durée de la
	 * partie en secondes.
	 */
	private final String DUREE_SECONDES = "duree_secondes";
	/**
	 * Colonne <code><i>taille_grille</i></code>, correspondant à la taille de la
	 * grille en cases.
	 */
	private final String TAILLE_GRILLE = "taille_grille";
	/**
	 * Colonne <code><i>id_vainqueur</i></code>, correspondant à la date de création
	 * de la partie.
	 */
	private final String TIMESTAMP = "id_vainqueur";

	/**
	 * {@inheritDoc}
	 * 
	 * @param id L'identifiant de la partie
	 * @return La partie correspondant à l'identifiant passé en paramètre
	 * 
	 */
	@Override
	public PartieSQL trouver(long id) {
		PartieSQL partie = new PartieSQL();
		Connection connexion = this.connexion.getConnexion();
		try {
			try (PreparedStatement pstmt = connexion.prepareStatement(
					"SELECT * FROM " + PARTIE + " WHERE " + ID + " = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY)) {
				pstmt.setLong(1, id);
				pstmt.execute();
				try (ResultSet rs = pstmt.getResultSet()) {
					if (rs.first()) {
						partie.setId(id);
						partie.setDureeSecondes(rs.getInt(DUREE_SECONDES));
						partie.setTailleGrille(rs.getInt(TAILLE_GRILLE));
						partie.setTimestamp(rs.getTimestamp(TIMESTAMP));
					}
				}
			}
		} catch (SQLException e) {

		}
		return partie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param partie La partie à créer dans la base de données
	 * @return La partie
	 */
	@Override
	public PartieSQL creer(PartieSQL partie) {
		Connection connexion = this.connexion.getConnexion();
		try {
			try (PreparedStatement pstmt1 = connexion
					.prepareStatement("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES " + "WHERE table_name = '"
							+ PARTIE + "'")) {
				pstmt1.execute();

				try (ResultSet rsid = pstmt1.getResultSet()) {
					if (rsid.next()) {
						long id = rsid.getLong(1);
						partie.setId(id);
					}
				}
				pstmt1.close();

			}

			try (PreparedStatement pstmt2 = connexion
					.prepareStatement("INSERT INTO " + PARTIE + " VALUES (?, ?, ?, ?);")) {
				pstmt2.setLong(1, partie.getId());
				pstmt2.setInt(2, partie.getDureeSecondes());
				pstmt2.setInt(3, partie.getTailleGrille());
				pstmt2.setTimestamp(4, new Timestamp(System.currentTimeMillis()), Calendar.getInstance(Locale.FRANCE));
				pstmt2.execute();
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return partie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param partie La partie à mettre à jour dans la base de données
	 * @return La partie
	 */
	@Override
	public PartieSQL maj(PartieSQL partie) {
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion.prepareStatement("UPDATE " + PARTIE + " SET " + ID + " = ?, "
				+ DUREE_SECONDES + " = ?, " + TAILLE_GRILLE + " = ? WHERE " + ID + " = ?")) {
			pstmt.setLong(1, partie.getId());
			pstmt.setInt(2, partie.getDureeSecondes());
			pstmt.setInt(3, partie.getTailleGrille());
			pstmt.setLong(4, partie.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param partie La partie à supprimer de la base de données
	 */
	@Override
	public void supprimer(PartieSQL partie) {
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion.prepareStatement("DELETE FROM " + PARTIE + " WHERE " + ID + " = ?;")) {
			pstmt.setLong(1, partie.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
