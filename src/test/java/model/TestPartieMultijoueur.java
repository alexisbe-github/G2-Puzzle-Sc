package test.java.model;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieMultijoueurCompetitive;
import main.java.model.serveur.Serveur;
import main.java.utils.InvalidPortException;
import main.java.utils.NetworkUtils;
import test.java.aserveur.TestServeur;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPartieMultijoueur {

	private static Client client1, client2;
	private static Joueur joueur1, joueur2;
	private static PartieMultijoueurCompetitive partieMultiCompetitive;
	private static Serveur serveur;
	private final String ip = NetworkUtils.getServeurIPV4(true);
	private final static int PORT_VALIDE = 8090;

	@BeforeAll
	public static void setUp() throws InvalidPortException {
		joueur1 = new Joueur("Joueur hôte", null);
		client1 = new Client(joueur1);
		joueur2 = new Joueur("Joueur 2", null);
		client2 = new Client(joueur2);
		partieMultiCompetitive = new PartieMultijoueurCompetitive();
		if(TestServeur.serveur == null) {
			TestServeur.serveur = new Serveur();
		}
		serveur = TestServeur.serveur;
		serveur.lancerServeur(partieMultiCompetitive, PORT_VALIDE);
		serveur.setPartie(partieMultiCompetitive);
		//serveur = new Serveur();
		//serveur.lancerServeur(partieMultiCompetitive, PORT_VALIDE);
	}

	@Test
	@Order(1)
	public void testAjouterJoueur() throws IOException, InterruptedException {
		client1.seConnecter(ip, PORT_VALIDE);
		client2.seConnecter(ip, PORT_VALIDE);
		TimeUnit.SECONDS.sleep(1); // attente de la connexion des joueurs

		int nbConnexionsAttendues = 2;
		int nbConnexions = partieMultiCompetitive.getJoueurs().size();

		Assertions.assertEquals(nbConnexionsAttendues, nbConnexions);
	}

	@Test
	@Order(2)
	public void testDeconnecterJoueur() throws IOException, InterruptedException {
		// on le fait pour 1 joueur
		try {
			client1.getSocket().close();
		} catch (NullPointerException npe) {
			int nbConnexionsAttendues = 1;
			int nbConnexions = partieMultiCompetitive.getJoueurs().size();

			Assertions.assertEquals(nbConnexionsAttendues, nbConnexions);
		}

		// puis le deuxieme
		try {
			client2.getSocket().close();
		} catch (NullPointerException npe) {
			int nbConnexionsAttendues = 0;
			int nbConnexions = partieMultiCompetitive.getJoueurs().size();

			Assertions.assertEquals(nbConnexionsAttendues, nbConnexions);
		}
	}

}
