package main.java.model.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import main.java.model.joueur.Joueur;

public class Client {
	
	private boolean connecte = false;
	private String ip = "";
	private int port = -1;
	private Socket socket;

	private void setConnection() {
		connecte = true;
	}

	public boolean getConnecte() {
		return connecte;
	}

	public void seConnecter(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			this.ip = ip;
			this.port = port;
			setConnection();
		} catch (IOException e) {
			connecte = false;
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void lancerRequete(String content) throws IOException {
		BufferedReader fluxEntrant = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream fluxSortant = new PrintStream(socket.getOutputStream());
		String requete = content;
		fluxSortant.println(requete); // envoi de la requete au serveur
		String reponse = fluxEntrant.readLine(); // reception de la reponse
		System.out.println(reponse);
	}

}
