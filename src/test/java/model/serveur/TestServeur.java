package test.java.model.serveur;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieMultijoueur;
import main.java.model.partie.PartieMultijoueurCooperative;
import main.java.model.serveur.Serveur;
import main.java.utils.InvalidPortException;
import main.java.utils.UtilsNetwork;

public class TestServeur {

	private static Client client;
	private static Joueur joueur;
	private static PartieMultijoueur partieMultiCoop;
	private static Serveur serveur;
	private final int PORT_VALIDE = InvalidPortException.PORT_MAX;
	private final int PORT_INVALIDE = InvalidPortException.PORT_MAX + 1;

	@BeforeAll
	public static void setUp() {
		joueur = new Joueur("Joueur hôte", null);
		client = new Client(joueur);
		partieMultiCoop = new PartieMultijoueurCooperative();
		serveur = new Serveur();
	}

	@Test
	@Order(1)
	public void testLancerServeurPortInvalide() {
		Assertions.assertThrows(InvalidPortException.class, () -> {
			serveur.lancerServeur(partieMultiCoop, PORT_INVALIDE);
		});
	}

	@Test
	@Order(2)
	public void testLancerServeur() {
		Assertions.assertDoesNotThrow(() -> {
			serveur.lancerServeur(partieMultiCoop, PORT_VALIDE);
		});
		
		int nombreDeConnexions = serveur.getNoConnexion();
		int nombreDeConnexionsAttendus = 0; //il doit y avoir 0 connexion au serveur
		Assertions.assertEquals(nombreDeConnexions, nombreDeConnexionsAttendus);
		
		nombreDeConnexionsAttendus = 1; //il doit y avoir 1 connexion au serveur
		client.seConnecter(UtilsNetwork.getServeurIPV4(true), PORT_VALIDE);
		nombreDeConnexions = serveur.getNoConnexion();
		Assertions.assertEquals(nombreDeConnexions, nombreDeConnexionsAttendus);
	}


}
