package main.java.model.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.util.StringUtils;

import main.java.model.bdd.Connexion;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;

/**
 * <i>Servlet</i> faisant le lien entre le serveur et les objets correspondant
 * aux joueurs
 */
public class ServletClassement extends HttpServlet {

	private static final long serialVersionUID = 5719132411446135771L;
	private static DAOJoueur dao = new DAOJoueur();

	/**
	 * Performe une requête <b>GET</b> permettant de récupérer le classement des
	 * joueurs
	 *
	 * <br>
	 * <br>
	 * 
	 * Liste des paramètres acceptés dans l'URL de la requête : <br>
	 * <ul>
	 * <li><b>min</b> : La borne minimale du classement (obligatoirement supérieure
	 * ou égale à <b>1</b>)</li>
	 * <li><b>max</b> : La borne maximale du classement (obligatoirement supérieure
	 * ou égale à <b><i>min</i></b>)</li>
	 * <li><b>tri</b> : L'ordre de tri (valeurs possibles : <b><i>ASC</i></b> ou
	 * <b><i>DESC</i></b>)</li>
	 * <li><b>filtre</b> : La valeur par rapport à laquelle filtrer les résultats
	 * (valeurs possibles : <b><i>nb_victoires</i></b>,
	 * <b><i>ratio_victoires</i></b>, <b><i>temps_partie</i></b>,
	 * <b><i>nb_coups</i></b>, <b><i>taille_grille</i></b>)</li>
	 * <li><b><i>[<u>OPTIONNEL</u>]</i> taille_grille</b> : La taille de la grille
	 * pour laquelle récupérer les informations (obligatoirement supérieure à
	 * 3)</li>
	 * </ul>
	 * 
	 * <br>
	 * <br>
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) {
		String min = requete.getParameter("min");
		String max = requete.getParameter("max");
		String tri = requete.getParameter("tri");
		String filtre = requete.getParameter("filtre");
		String taille_grille = requete.getParameter("taille_grille");

		String sql;
		List<List<Object>> res = new ArrayList<>();

		switch (filtre.toLowerCase(Locale.FRANCE)) {
		case "nb_victoires":
			sql = "SELECT id_joueur, COUNT(*) AS nb_victoires FROM partie_competitive WHERE id_vainqueur = id_joueur GROUP BY id_joueur ORDER BY nombre_de_victoires ? LIMIT ? OFFSET ?;";
			Connection connexion = Connexion.getInstance().getConnection();
			try (PreparedStatement pstmt = connexion.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY)) {
				verifierTri(tri);
				verifierMin(min);
				verifierMax(min, max);
				pstmt.setString(1, tri);
				pstmt.setInt(2, Integer.parseInt(max) - Integer.parseInt(min) + 1);
				pstmt.setInt(3, Integer.parseInt(min) - 1);
				pstmt.execute();
				try (ResultSet rs = pstmt.getResultSet()) {
					while (rs.next()) {
						List<Object> liste = new ArrayList<>();
						long id = rs.getLong("id_joueur");
						int nb_victoires = rs.getInt("nb_victoires");
						liste.add(dao.trouver(id));
						liste.add(nb_victoires);
						res.add(liste);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			System.err.println("Le filtre est incorrect !");
			return;
		}
	}

	/**
	 * Performe une requête <b>POST</b> sur les données des joueurs
	 * 
	 * <br>
	 * <br>
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void doPost(HttpServletRequest requete, HttpServletResponse reponse) {

	}

	/**
	 * @param min Le paramètre <code>tri</code> de la requête
	 */
	private static void verifierTri(String tri) throws Exception {
		if (!(tri.toLowerCase(Locale.FRANCE).equals("asc") || tri.toLowerCase(Locale.FRANCE).equals("desc"))) {
			throw new Exception("Le paramètre doit être égal à « ASC » ou « DESC » !");
		}
	}

	/**
	 * @param min Le paramètre <code>min</code> de la requête
	 */
	private static void verifierMin(String min) throws Exception {
		if (!StringUtils.isStrictlyNumeric(min)) {
			throw new Exception("Le paramètre doit être un nombre !");
		}
		int res = Integer.parseInt(min);
		if (res <= 0) {
			throw new Exception("Le paramètre doit être supérieur ou égal à 1 !");
		}
	}

	/**
	 * @param max Le paramètre <code>max</code> de la requête
	 */
	private static void verifierMax(String min, String max) throws Exception {
		verifierMin(min);
		if (!StringUtils.isStrictlyNumeric(max)) {
			throw new Exception("Le paramètre doit être un nombre !");
		}
		int res = Integer.parseInt(max);
		if (res <= Integer.parseInt(min)) {
			throw new Exception("Le paramètre doit être supérieur ou égal au mimimum !");
		}
	}
}
