package main.java.model.partie;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.model.EDeplacement;
import main.java.model.joueur.Joueur;

public abstract class PartieMultijoueur implements StrategyPartie, Serializable {

	protected List<Joueur> joueurs;
	protected Map<Joueur, Socket> tableSocketDesJoueurs;

	public abstract void deplacerCase(EDeplacement dp, Joueur joueur, int numJoueur) throws IOException;

	public abstract void deconnecterJoueur(Joueur j);

	public void ajouterJoueur(Joueur j, Socket s) throws IOException {
		joueurs.add(j);
		tableSocketDesJoueurs.put(j, s);
		this.envoyerJoueurs(false);
		System.out.println(joueurs);
	}

	public void envoyerJoueurs(boolean lancement) throws IOException {
		for (Map.Entry<Joueur, Socket> mapEntry : tableSocketDesJoueurs.entrySet()) {
			Joueur jcourant = mapEntry.getKey();
			Socket scourant = mapEntry.getValue();

			List<Object> output = new ArrayList<Object>();
			if (lancement)
				output.add("s");
			else
				output.add("c");
			output.add(joueurs);

			ObjectOutputStream oop = new ObjectOutputStream(scourant.getOutputStream());
			oop.writeObject(output);
		}
	}

	public void envoyerLancement() throws IOException {
		for (Map.Entry<Joueur, Socket> mapEntry : tableSocketDesJoueurs.entrySet()) {
			Joueur jcourant = mapEntry.getKey();
			Socket scourant = mapEntry.getValue();

			ObjectOutputStream oop = new ObjectOutputStream(scourant.getOutputStream());
			oop.writeObject("s");
		}
	}

	public List<Joueur> getJoueurs() {
		return this.joueurs;
	}

	public Map<Joueur, Socket> getTableSocketDesJoueurs() {
		return tableSocketDesJoueurs;
	}

}
