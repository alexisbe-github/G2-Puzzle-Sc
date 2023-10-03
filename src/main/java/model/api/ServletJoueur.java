package main.java.model.api;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <i>Servlet</i> faisant le lien entre le serveur et les objets correspondant
 * aux joueurs
 */
public class ServletJoueur extends HttpServlet {

	private static final long serialVersionUID = 5719132411446135771L;

	/**
	 * Performe une requête <b>GET</b> permettant de récupérer des données sur les
	 * joueurs
	 *
	 * <br>
	 * <br>
	 * 
	 * Liste des paramètres acceptés dans l'URL de la requête :
	 * <br>
	 * <ul>
	 * <li><b>id</b> : L'identifiant du joueur à récupérer</li>
	 * <li><b>pseudo</b> : Le pseudonyme du joueur <i>(non recommandé)</i></li>
	 * </ul>
	 * 
	 * <br>
	 * <br>
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) {
		String id = requete.getParameter("id");
		String pseudo = requete.getParameter("pseudo");
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

}
