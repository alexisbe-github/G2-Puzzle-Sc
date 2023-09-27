package main.java.model.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import main.java.model.partie.PartieMultijoueur;
import main.java.utils.InvalidPortException;
import main.java.utils.UtilsNetwork;

public class Serveur {

	public static void lancerServeur(PartieMultijoueur partie, int port) throws InvalidPortException {
		UtilsNetwork.checkPort(port);
			
		new Thread(() -> {
			System.out.println("Lancement du serveur...");
			try {
				ThreadGroup groupe = new ThreadGroup("socketsClients"); // on fait un groupe de thread pour gérer les
																		// multiples connexion au serveur
				ServerSocket serverSocket = new ServerSocket(port); // on fait un serveur socket sur le port pour les
																	// connexions
				int noConnexion = 0;

				// on accepte les connexion sur la server socket et on incrémente le nombre de
				// connexions, à chaque connexion on démarre un serveurthread à l'aide du thread
				// group
				while (true) {
					Socket clientSocket = serverSocket.accept();
					noConnexion++;
					ServeurThread st = new ServeurThread(clientSocket, groupe, noConnexion, partie);
					st.start();
				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}).start();

	}

}
