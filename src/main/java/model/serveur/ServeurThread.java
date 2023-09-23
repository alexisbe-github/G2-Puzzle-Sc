package main.java.model.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class ServeurThread extends Thread {

	private Socket socket;
	private int noConnexion; // numero du client distant
	private BufferedReader fluxEntrant;
	private PrintStream fluxSortant;

	/**
	 * Suppose socket deje connecte vers le client n noConnexion
	 * 
	 * @param noConnexion : n du client
	 */
	public ServeurThread(Socket socket, ThreadGroup groupe, int noConnexion) throws IOException {
		super(groupe, "ReceveurEnvoyeur");
		this.socket = socket;
		this.noConnexion = noConnexion;
		fluxEntrant = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		/* e present fluxEntrant est pret pour lire du texte provenant du client */
		fluxSortant = new PrintStream(this.socket.getOutputStream());
		/*
		 * e present fluxSortant est pret pour renvoyer des reponses textuelles au
		 * client
		 */
	}

	public void run() {
		String ligne;
		String reponse;
		try {
			while (!isInterrupted()) {
				ligne = fluxEntrant.readLine(); // saisit le texte du client
				System.out.println(" le client num " + this.noConnexion + " a envoye : ");
				System.out.println(ligne); // echo de la question sur la console

				reponse = ligne; // calcul de la reponse
				

				fluxSortant.println(reponse); // envoi de la reponse au client
				sleep(5);
			} // while
		} catch (InterruptedException erreur) {
			/* le thread s'arrete */} catch (IOException erreur) {
			System.err.println(" on ne peut pas lire sur le socket provenant du client");
		}
	}// run

}
