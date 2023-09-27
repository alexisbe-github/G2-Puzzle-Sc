package main.java.model.serveur;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import main.java.model.client.TestClient;
import main.java.model.joueur.Joueur;
import main.java.model.partie.ContextePartie;
import main.java.model.partie.PartieMultijoueurCooperative;
import main.java.utils.InvalidPortException;

public class TestServeur {

	public static void main(String[] args) throws IOException, InterruptedException {
		Joueur joueurHote = new Joueur("Pop simoké", null);
		ContextePartie cp = new ContextePartie(joueurHote);
		PartieMultijoueurCooperative pmCoop = new PartieMultijoueurCooperative();
		cp.setStrategy(pmCoop);
		try {
			Serveur.lancerServeur(pmCoop, 8080);
		} catch (InvalidPortException e1) {

		}
		new Thread(() -> {
			try {
				TestClient.lancerClient(joueurHote);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		TimeUnit.SECONDS.sleep(8); // on attend 8s avant de lancer la partie
		System.out.println("Partie coop lancée!");
		cp.lancerPartie(null, 3);
	}
}
