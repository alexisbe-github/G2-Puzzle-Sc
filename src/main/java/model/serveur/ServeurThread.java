package main.java.model.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

import main.java.model.EDeplacement;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieMultijoueur;

public class ServeurThread extends Thread {

	private Socket socket;
	private int noConnexion; // numero du client distant
	private BufferedReader fluxEntrant;
	private PrintStream fluxSortant;
	private PartieMultijoueur partie;
	private boolean flagJoueurAjoute = false;
	private Joueur joueur;

	/**
	 * Suppose socket dejà connectée vers le client num noConnexion
	 * 
	 * @param noConnexion : num du client
	 */
	public ServeurThread(Socket socket, ThreadGroup groupe, int noConnexion, PartieMultijoueur partie)
			throws IOException {
		super(groupe, "ReceveurEnvoyeur");
		this.partie = partie;
		this.socket = socket;
		this.noConnexion = noConnexion;
		fluxEntrant = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		/* à present fluxEntrant est pret pour lire du texte provenant du client */
		fluxSortant = new PrintStream(this.socket.getOutputStream());
		/*
		 * à present le fluxSortant est pret pour renvoyer des reponses textuelles au
		 * client
		 */
	}

	public void run() {
		String ligne;
		String reponse;
		try {
			while (!isInterrupted()) {

				if (!flagJoueurAjoute) {
					ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
					this.joueur = (Joueur) inputStream.readObject();
					partie.ajouterJoueur(joueur, socket);
					flagJoueurAjoute = true;
				} else {
					ligne = fluxEntrant.readLine(); // saisit le texte du client
					System.out.println("Le client numéro " + this.noConnexion + " a envoye : ");
					System.out.println(ligne); // echo de la question sur la console

					reponse = ligne; // calcul de la reponse
					char c = reponse.charAt(0);
					switch (c) {
					case 'h':
						partie.deplacerCase(EDeplacement.HAUT, joueur, this.noConnexion);
						break;
					case 'b':
						partie.deplacerCase(EDeplacement.BAS, joueur, this.noConnexion);
						break;
					case 'g':
						partie.deplacerCase(EDeplacement.GAUCHE, joueur, this.noConnexion);
						break;
					case 'd':
						partie.deplacerCase(EDeplacement.DROITE, joueur, this.noConnexion);
						break;
					}
					// fluxSortant.println(partie); // envoi de la reponse au client
				}

				sleep(5);
			} // while
		} catch (InterruptedException erreur) {
			/* le thread s'arrete */} catch (IOException erreur) {
			// System.err.println("On ne peut pas lire sur le socket provenant du client");
			System.out.println("Deconnexion du client numéro " + this.noConnexion);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// run

}
