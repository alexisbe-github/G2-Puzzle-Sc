package main.java.model.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieMultijoueur;

public class Client {

	private boolean estConnecte;
	private Socket socket;
	private Joueur joueur;

	public Client(Joueur joueur) {
		this.joueur = joueur;
		this.estConnecte = false;
	}

	private void setConnection() {
		estConnecte = true;
	}

	public boolean getEstConnecte() {
		return estConnecte;
	}

	public void seConnecter(String ip, int port) throws IOException, ClassNotFoundException {
		socket = new Socket(ip, port);
		setConnection();
		ObjectInputStream fluxEntrant = new ObjectInputStream(socket.getInputStream());
		PartieMultijoueur p = (PartieMultijoueur) fluxEntrant.readObject();
		lancerRequete("c");
		this.ajouterJoueur();
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
