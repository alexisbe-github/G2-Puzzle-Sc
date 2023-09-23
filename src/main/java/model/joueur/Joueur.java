package main.java.model.joueur;

import java.awt.image.BufferedImage;

public class Joueur {

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

}
