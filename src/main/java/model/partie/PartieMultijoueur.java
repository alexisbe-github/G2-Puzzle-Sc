package main.java.model.partie;

import java.util.List;

import main.java.model.joueur.Joueur;

public abstract class PartieMultijoueur implements StrategyPartie{

	protected List<Joueur> joueurs;
	
	public List<Joueur> getJoueurs(){
		return this.joueurs;
	}
	
	public void ajouterJoueur(Joueur j) {
		joueurs.add(j);
	}
	
}
