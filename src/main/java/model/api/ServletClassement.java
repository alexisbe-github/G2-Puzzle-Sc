package main.java.model.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mysql.cj.util.StringUtils;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.model.bdd.Connexion;
import main.java.model.bdd.dao.DAOJoueur;

/**
 * <i>Servlet</i> faisant le lien entre le serveur et les objets correspondant
 * aux joueurs
 */
@WebServlet(name = "Classement", urlPatterns = { "/classement" })
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
	 * <b><i>nb_coups</i></b>)</li>
	 * <li><b><i>[<u>OPTIONNEL</u>]</i> taille_grille</b> : La taille de la grille
	 * pour laquelle récupérer les informations (obligatoirement supérieure à 3) ;
	 * si aucune valeur n'est spécifiée, la valeur par défaut est 3</li>
	 * </ul>
	 * 
	 * <br>
	 * <br>
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) {
		reponse.setCharacterEncoding("UTF-8");
		reponse.setContentType("application/json");

		String min = requete.getParameter("min");
		String max = requete.getParameter("max");
		String tri = requete.getParameter("tri");
		String filtre = requete.getParameter("filtre");
		String taille_grille = requete.getParameter("taille_grille");

		StringBuilder json = new StringBuilder("{\r\n");
		String sql;
		List<List<Object>> res = new ArrayList<>();

		switch (filtre.toLowerCase(Locale.FRANCE)) {
		case "nb_victoires":
			if (taille_grille == null) {
				sql = "SELECT id_joueur, COUNT(*) AS nb_victoires FROM partie_competitive AS pc INNER JOIN partie AS p ON pc.id_partie = p.id WHERE pc.id_vainqueur = id_joueur GROUP BY id_joueur ORDER BY nb_victoires %s LIMIT ? OFFSET ?";
			} else {
				sql = "SELECT id_joueur, COUNT(*) AS nb_victoires FROM partie_competitive AS pc INNER JOIN partie AS p ON pc.id_partie = p.id WHERE pc.id_vainqueur = id_joueur AND p.taille_grille = ? ORDER BY nb_victoires %s LIMIT ? OFFSET ?";
			}
			Connection connexion = Connexion.getInstance().getConnection();
			try {
				verifierTailleGrille(taille_grille);
				verifierTri(tri);
				verifierMin(min);
				verifierMax(min, max);
				sql = String.format(sql, tri);
				try (PreparedStatement pstmt = connexion.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
					if (taille_grille != null) {
						pstmt.setInt(1, Integer.parseInt(taille_grille));
						pstmt.setInt(2, Integer.parseInt(max) - Integer.parseInt(min) + 1);
						pstmt.setInt(3, Integer.parseInt(min) - 1);
					} else {
						int limite = Integer.parseInt(max) - Integer.parseInt(min) + 1;
						pstmt.setInt(1, limite);
						int offset = Integer.parseInt(min) - 1;
						pstmt.setInt(2, offset);
					}
					pstmt.execute();
					try (ResultSet rs = pstmt.getResultSet()) {
						while (rs.next()) {
							List<Object> liste = new ArrayList<>();
							long id = rs.getLong("id_joueur");
							int nb_victoires = rs.getInt("nb_victoires");
							json.append("[pseudo: ");
							json.append(dao.trouver(id).getPseudo());
							json.append(", nb_victoires: ");
							json.append(nb_victoires);
							json.append("],\r\n");
							res.add(liste);
							System.out.println(liste);
						}
					}
				}
			} catch (SQLException e) {
				json.append("erreur_sql: '");
				json.append(e.getMessage());
				json.append("',\r\n");
				e.printStackTrace();
			} catch (Exception e) {
				json.append("erreur: '");
				json.append(e.getMessage());
				json.append(" - ");
				json.append(e.getStackTrace());
				json.append(" - ");
				json.append(e.getClass());
				json.append("',\r\n");
				e.printStackTrace();
			}
			break;
		default:
			System.err.println("Le filtre est incorrect !");
			return;
		}
		try {
			json.append("}");
			ServletOutputStream out = reponse.getOutputStream();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	 * @param taille_grille Le paramètre <code>taille_grille</code> de la requête
	 */
	private static void verifierTailleGrille(String taille_grille) throws Exception {
		if (taille_grille == null) {
			return;
		}
		if (!StringUtils.isStrictlyNumeric(taille_grille)) {
			throw new Exception("Le paramètre doit être un nombre !");
		}
		int res = Integer.parseInt(taille_grille);
		if (res < 3) {
			throw new Exception("Le paramètre doit être supérieur ou égal à 3 !");
		}
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
		if (res < Integer.parseInt(min)) {
			throw new Exception("Le paramètre doit être supérieur ou égal au mimimum !");
		}
	}

//	/**
//	 * @return La connexion à la base de données
//	 */
//	private static Connection getConnexion() {
//		Connection connexion;
//		try {
//			Context initContext = new InitialContext();
//			Context envContext = (Context) initContext.lookup("java:comp/env");
//			DataSource dataSource = (DataSource) envContext.lookup("jdbc/taquin");
//			connexion = dataSource.getConnection();
//			return connexion;
//		} catch (NamingException | SQLException e) {
//			return null; // TODO return message
//		}
//	}
}
