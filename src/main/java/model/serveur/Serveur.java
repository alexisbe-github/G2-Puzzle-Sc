package main.java.model.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import main.java.model.partie.PartieMultijoueur;
import main.java.utils.InvalidPortException;
import main.java.utils.UtilsNetwork;

public class Serveur {
	
	private boolean serverOn;
	private int noConnexion;
	
	public Serveur() {
		serverOn = false;
		noConnexion = 0;
	}

	public synchronized void lancerServeur(PartieMultijoueur partie, int port) throws InvalidPortException {
		UtilsNetwork.checkPort(port);
		serverOn = true;
		new Thread(() -> {
			System.out.println("Lancement du serveur...");
			try {
				ThreadGroup groupe = new ThreadGroup("socketsClients"); // on fait un groupe de thread pour gérer les
																		// multiples connexion au serveur
				ServerSocket serverSocket = new ServerSocket(port); // on fait un serveur socket sur le port pour les
																	// connexions qu'on transforme en socket
				noConnexion = 0;

				// on accepte les connexion sur la server socket et on incrémente le nombre de
				// connexions, à chaque connexion on démarre un serveurthread à l'aide du thread
				// group
				while (serverOn) {
					Socket clientSocket = serverSocket.accept();
					System.out.println(noConnexion);
					noConnexion++;
					ServeurThread st = new ServeurThread(clientSocket, groupe, noConnexion, partie);
					st.start();
				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public void stopServeur() {
		this.serverOn = false;
	}
	
	public int getNoConnexion() {
		return this.noConnexion;
	}

}
