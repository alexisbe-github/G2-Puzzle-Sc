package main.java.model.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

import main.java.model.EDeplacement;
import main.java.model.joueur.Joueur;

public class ServeurThread extends Thread {

	private Socket socket;
	private int noConnexion; // numero du client distant
	private BufferedReader fluxEntrant;
	private PrintStream fluxSortant;
	private boolean flagJoueurAjoute = false;
	private Joueur joueur;
	private Serveur serveur;

	/**
	 * Suppose socket dejà connectée vers le client num noConnexion
	 * 
	 * @param noConnexion : num du client
	 */
	public ServeurThread(Socket socket, ThreadGroup groupe, int noConnexion, Serveur serveur)
			throws IOException {
		super(groupe, "ReceveurEnvoyeur");
		this.socket = socket;
		this.noConnexion = noConnexion;
		this.serveur = serveur;
		fluxEntrant = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		fluxSortant = new PrintStream(this.socket.getOutputStream());
	}

	@Override
	public void run() {
		String ligne;
		String reponse;
		try {
			while (!isInterrupted()) {
				if (!flagJoueurAjoute) {
					ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
					this.joueur = (Joueur) inputStream.readObject();
					serveur.getPartie().ajouterJoueur(joueur, socket);
					flagJoueurAjoute = true;
				} else {
					ligne = fluxEntrant.readLine(); // saisit le texte du client
					if (ligne != null) {
						System.out.println("Le client numéro " + this.noConnexion + " a envoye : ");
						System.out.println(ligne); // echo de la question sur la console

						reponse = ligne; // calcul de la reponse
						char c = reponse.charAt(0);
						switch (c) {
						case 'h':
							serveur.getPartie().deplacerCase(EDeplacement.HAUT, joueur, this.noConnexion);
							break;
						case 'b':
							serveur.getPartie().deplacerCase(EDeplacement.BAS, joueur, this.noConnexion);
							break;
						case 'g':
							serveur.getPartie().deplacerCase(EDeplacement.GAUCHE, joueur, this.noConnexion);
							break;
						case 'd':
							serveur.getPartie().deplacerCase(EDeplacement.DROITE, joueur, this.noConnexion);
							break;
						case 'l':
							serveur.getPartie().envoyerJoueurs();
							break;
						case 's':
							serveur.getPartie().envoyerLancement();
							break;
						}
					}
				}

				sleep(5);
			} // while
		} catch (InterruptedException erreur) {
			/* le thread s'arrete */} catch (IOException erreur) {
			erreur.printStackTrace();
			System.out.println("Déconnexion du client numéro " + this.noConnexion);
			serveur.getPartie().deconnecterJoueur(joueur); // cas où le cient n'est plus connecté au serveur
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// run

}
