package main.java.model.partie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
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
		this.envoyerJoueurs();
		System.out.println(joueurs);
	}
	
	public void envoyerJoueurs() throws IOException {
		for (Map.Entry<Joueur, Socket> mapEntry : tableSocketDesJoueurs.entrySet()) {
			Joueur jcourant = mapEntry.getKey();
			Socket scourant = mapEntry.getValue();
			ObjectOutputStream oop = new ObjectOutputStream(scourant.getOutputStream());
			oop.writeObject(joueurs);
		}
	}
	
	public void envoyerLancement() throws IOException {
		for (Map.Entry<Joueur, Socket> mapEntry : tableSocketDesJoueurs.entrySet()) {
			Joueur jcourant = mapEntry.getKey();
			Socket scourant = mapEntry.getValue();
			PrintStream fluxSortant = new PrintStream(scourant.getOutputStream());
			fluxSortant.println("s");
		}
	}
	
//	public void demanderInfos() throws IOException, ClassNotFoundException {
//		for (Map.Entry<Joueur, Socket> mapEntry : tableSocketDesJoueurs.entrySet()) {
//			Joueur jcourant = mapEntry.getKey();
//			Socket scourant = mapEntry.getValue();
//			
//			PrintStream fluxSortant = new PrintStream(scourant.getOutputStream());
//			fluxSortant.println("i");
//			
//			ObjectInputStream ois = new ObjectInputStream(scourant.getInputStream());
//			BufferedReader fluxEntrant = new BufferedReader(new InputStreamReader(scourant.getInputStream()));
//			
//			Image img = (Image) ois.readObject();
//			
//			this.envoyerInfos(img);
//		}
//	}
//	
//	private void envoyerInfos(Image img) throws IOException {
//		for (Map.Entry<Joueur, Socket> mapEntry : tableSocketDesJoueurs.entrySet()) {
//			Joueur jcourant = mapEntry.getKey();
//			Socket scourant = mapEntry.getValue();
//			
//			ObjectOutputStream oop = new ObjectOutputStream(scourant.getOutputStream());
//			oop.writeObject(img);
//			
//		}
//	}

	public List<Joueur> getJoueurs() {
		return this.joueurs;
	}

	public Map<Joueur, Socket> getTableSocketDesJoueurs() {
		return tableSocketDesJoueurs;
	}
	
}
