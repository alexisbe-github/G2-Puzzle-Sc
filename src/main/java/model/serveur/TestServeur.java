package main.java.model.serveur;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import main.java.model.client.TestClient;
import main.java.model.joueur.Joueur;
import main.java.model.partie.ContextePartie;
import main.java.model.partie.PartieMultijoueurCooperative;

public class TestServeur {

	public static void main(String[] args) throws IOException, InterruptedException {
		Joueur joueurHote = new Joueur("Pop simoké", null);
		ContextePartie cp = new ContextePartie(joueurHote);
		PartieMultijoueurCooperative pmCoop = new PartieMultijoueurCooperative(joueurHote);
		cp.setStrategy(pmCoop);
		Serveur.lancerServeur(pmCoop);
		TimeUnit.SECONDS.sleep(15); //on attend 15s avant de lancer la partie
		System.out.println("Partie coop lancée!");
		cp.lancerPartie(null, 3);
		TestClient.main(args);
	}
}
