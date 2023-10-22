package main.java.model.joueur;

import java.io.Serializable;
import java.util.Objects;

public class Joueur implements Serializable{

	private String nom;
	private String imageUrl;

	/**
	 * 
	 * @param nom   String du nom du joueur
	 * @param URL de l'Image correspondant à l'avatar du joueur
	 */
	public Joueur(String nom, String imageUrl) {
		this.nom = nom;
		this.imageUrl = imageUrl;
	}

	public String getNom() {
		return nom;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	
	@Override
	public String toString() {
		return this.nom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joueur other = (Joueur) obj;
		return Objects.equals(nom, other.nom);
	}

	
}
