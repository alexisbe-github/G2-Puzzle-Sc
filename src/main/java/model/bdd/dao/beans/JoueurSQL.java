package main.java.model.bdd.dao.beans;

import java.io.Serializable;

/**
 * <code>{@link <a href=
 * "https://fr.wikipedia.org/wiki/JavaBeans">Java Bean</a>}</code> correspondant
 * à un joueur, permettant de faire le lien à travers la couche <code>DAO</code>
 * entre la base de données et le modèle.
 */
public class JoueurSQL implements Serializable {

	private static final long serialVersionUID = 6019572450306649467L;

	private long id;
	private String pseudo;

	/**
	 * @return L'identifiant
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id L'identifiant à mettre à jour
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Le pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * @param pseudo Le pseudo à mettre à jour
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

}