package main.java.model.partie;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javafx.scene.image.Image;
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
	public void lancerPartie(Image image, int taillePuzzle) {
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
	 * Permet d'ajouter un PCL, et d'observer la classe.
	 * @param PropertyChangeListener à appliquer
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
	        pcs.addPropertyChangeListener(pcl);
	}
	
	/**
	 * Permet de retirer un PCL.
	 * @param PropertyChangeListener à appliquer
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
	        pcs.removePropertyChangeListener(pcl);
	}

}
