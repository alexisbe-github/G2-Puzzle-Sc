package main.java.model.joueur;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

public class Joueur implements Serializable{

	private String nom;
	private BufferedImage image;

	/**
	 * 
	 * @param nom   String du nom du joueur
	 * @param image BufferedImage correspondant à l'avatar du joueur
	 */
	public Joueur(String nom, BufferedImage image) {
		this.nom = nom;
		this.image = image;
	}

	public String getNom() {
		return nom;
	}

	public BufferedImage getImage() {
		return image;
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
