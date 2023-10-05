package test.java.model;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieMultijoueurCompetitive;
import main.java.model.serveur.Serveur;
import main.java.utils.InvalidPortException;
import main.java.utils.NetworkUtils;
import test.java.aserveur.TestServeur;

public class TestPartieMultijoueur {

	private static Client client1, client2;
	private static Joueur joueur1, joueur2;
	private static PartieMultijoueurCompetitive partieMultiCompetitive;
	private static Serveur serveur;
	private final String ip = NetworkUtils.getServeurIPV4(true);
	private final static int PORT_VALIDE = 8090;
	private final static int TAILLE = 3;
	
	@BeforeAll
	public static void setUp() throws InvalidPortException, IOException {
		joueur1 = new Joueur("Joueur hôte", null);
		client1 = new Client(joueur1);
		joueur2 = new Joueur("Joueur 2", null);
		client2 = new Client(joueur2);
		partieMultiCompetitive = new PartieMultijoueurCompetitive();
		if(TestServeur.serveur == null) {
			TestServeur.serveur = new Serveur();
			TestServeur.serveur.lancerServeur(partieMultiCompetitive, PORT_VALIDE);
		}
		serveur = TestServeur.serveur;
		serveur.setPartie(partieMultiCompetitive);
		//serveur = new Serveur();
		//serveur.lancerServeur(partieMultiCompetitive, PORT_VALIDE);
		partieMultiCompetitive.lancerPartie(ImageIO.read(new File("src/test/resources/testimg.jpg")), TAILLE);
	}

	@Test
	public void testAjouterJoueur() throws IOException, InterruptedException {
		client1.seConnecter(ip, PORT_VALIDE);
		client2.seConnecter(ip, PORT_VALIDE);
		TimeUnit.SECONDS.sleep(1); // attente de la connexion des joueurs

		int nbConnexionsAttendues = 2;
		int nbConnexions = partieMultiCompetitive.getJoueurs().size();

		Assertions.assertEquals(nbConnexionsAttendues, nbConnexions);
	}

}
