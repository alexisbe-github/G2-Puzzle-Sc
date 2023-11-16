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
	protected byte[] image;
	protected int taille;
	protected boolean partieLancee = false;

	public abstract void deplacerCase(EDeplacement dp, Joueur joueur, int numJoueur) throws IOException;

	public abstract void deconnecterJoueur(Joueur j);

	public void ajouterJoueur(Joueur j, Socket s) throws IOException {
		joueurs.add(j);
		tableSocketDesJoueurs.put(j, s);
	}

//	public void envoyerJoueurs(boolean lancement) throws IOException {
//		for (Map.Entry<Joueur, Socket> mapEntry : tableSocketDesJoueurs.entrySet()) {
//			Joueur jcourant = mapEntry.getKey();
//			Socket scourant = mapEntry.getValue();
//
//			List<Object> output = new ArrayList<Object>();
//			
//			if (lancement)
//				output.add("s");
//			else
//				output.add("c");
//			
//			output.add(joueurs);
//
//			ObjectOutputStream oop = new ObjectOutputStream(scourant.getOutputStream());
//			oop.writeObject(output);
//		}
//	}

	public void setInfos(byte[] img, int t) {
		this.image = img;
		this.taille = t;
	}
	
	public void envoyerJoueurs(String param, Socket s) throws IOException {
		List<Object> output = new ArrayList<Object>();
		ObjectOutputStream oop = new ObjectOutputStream(s.getOutputStream());

		output.add(param);
		output.add(joueurs);

		if (param.equals("i")) {
				output.add(this.image);
				output.add(this.taille);
				output.add(this instanceof PartieMultijoueurCooperative);
		}else if(param.equals("s")) {
			this.partieLancee=true;
		}
		
		if(partieLancee) {
			output.set(0, "s");
			if(this instanceof PartieMultijoueurCooperative) {
				output.add(((PartieMultijoueurCooperative) this).getIndexJoueurCourant());
				output.add(((PartieMultijoueurCooperative) this).getPuzzleCommun());
			}else if(this instanceof PartieMultijoueurCompetitive) {
				
			}
		}
		
		if(partieLancee) System.out.println("SPSPSPSPSP");
		oop.writeObject(output);
	}

	public List<Joueur> getJoueurs() {
		return this.joueurs;
	}

	public Map<Joueur, Socket> getTableSocketDesJoueurs() {
		return tableSocketDesJoueurs;
	}

}
