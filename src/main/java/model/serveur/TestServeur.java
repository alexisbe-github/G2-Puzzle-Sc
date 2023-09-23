package main.java.model.serveur;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import main.java.model.client.Client;
import main.java.model.client.TestClient;
import main.java.model.joueur.Joueur;
import main.java.model.partie.ContextePartie;
import main.java.model.partie.PartieMultijoueurCooperative;

public class TestServeur {

	public static void main(String[] args) throws IOException, InterruptedException {
		Joueur joueurHote = new Joueur("Pop simoké", null);
		ContextePartie cp = new ContextePartie(joueurHote);
		PartieMultijoueurCooperative pmCoop = new PartieMultijoueurCooperative();
		cp.setStrategy(pmCoop);
		Serveur.lancerServeur(pmCoop);
		String ip = TestClient.getIP();
		int port = 8080;
		Client c = new Client(joueurHote);
		c.seConnecter(ip, port);
		TimeUnit.SECONDS.sleep(8); //on attend 15s avant de lancer la partie
		System.out.println("Partie coop lancée!");
		cp.lancerPartie(null, 3);
		Scanner sc = new Scanner(System.in);
		String message;
		while (true) {
			System.out.println("HAUT:h BAS:b GAUCHE:g DROITE:d");
			message = sc.nextLine();
			c.lancerRequete(message);
		}
		
		
	}
}
