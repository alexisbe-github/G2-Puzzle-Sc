package main.java.model.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import main.java.model.joueur.Joueur;

public class Client {

	private boolean estConnecte;
	private Socket socket;
	private Joueur joueur;

	public Client(Joueur joueur) {
		this.joueur = joueur;
		this.connecte = false;
	}

	private void setConnection() {
		connecte = true;
	}

	public boolean getConnecte() {
		return connecte;
	}

	public void seConnecter(String ip, int port) throws IOException {
		socket = new Socket(ip, port);
		setConnection();
		ajouterJoueur();
	}

	public Socket getSocket() {
		return socket;
	}

	public void lancerRequete(String content) throws IOException {
		BufferedReader fluxEntrant = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream fluxSortant = new PrintStream(socket.getOutputStream());
		String requete = content;
		fluxSortant.println(requete); // envoi de la requete au serveur
	}

	private void ajouterJoueur() throws IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		outputStream.writeObject(joueur);
	}

}
