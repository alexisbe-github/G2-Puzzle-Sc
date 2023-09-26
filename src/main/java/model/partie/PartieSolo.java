package main.java.model.partie;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.model.joueur.Joueur;

public class PartieSolo implements StrategyPartie {

	private Joueur joueur;
	private Puzzle puzzle;
	private PropertyChangeSupport pcs;

	public PartieSolo(Joueur joueur) {
		this.joueur = joueur;
		this.pcs = new PropertyChangeSupport(this);
	}

	@Override
	public void lancerPartie(BufferedImage image, int taillePuzzle) {
		this.puzzle = new Puzzle(taillePuzzle, image);
	}
	
	public void deplacerCase(EDeplacement dp) {
		puzzle.deplacerCase(dp);
		pcs.firePropertyChange("property", 1, 0);
	}

	public Puzzle getPuzzle() {
		return this.puzzle;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	/**
	 * TODO javadoc
	 * @param pcl
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
	        pcs.addPropertyChangeListener(pcl);
	}
	
	/**
	 * TODO javadoc
	 * @param pcl
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
	        pcs.removePropertyChangeListener(pcl);
	}
}
