package main.java.model.partie;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import main.java.model.EDeplacement;
import main.java.model.Puzzle;
import main.java.model.joueur.Joueur;

public abstract class PartieMultijoueur implements StrategyPartie {

	protected List<Joueur> joueurs;
	protected Map<Joueur, Socket> tableSocketDesJoueurs;
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public abstract void deplacerCase(EDeplacement dp, Joueur joueur, int numJoueur) throws IOException;

	public abstract void deconnecterJoueur(Joueur j);

	public void ajouterJoueur(Joueur j, Socket s) {
		joueurs.add(j);
		tableSocketDesJoueurs.put(j, s);
		System.out.println(joueurs);
		pcs.firePropertyChange("property", 1, 0);
	}

	public List<Joueur> getJoueurs() {
		return this.joueurs;
	}

	public Map<Joueur, Socket> getTableSocketDesJoueurs() {
		return tableSocketDesJoueurs;
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
