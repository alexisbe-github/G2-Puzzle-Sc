package main.java.model.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import main.java.model.partie.PartieMultijoueur;
import main.java.utils.InvalidPortException;
import main.java.utils.NetworkUtils;

public class Serveur {

	private boolean serverOn;
	private int noConnexion;
	private ServerSocket serverSocket;

	public Serveur() {
		serverOn = false;
		noConnexion = 0;
		try {
			final ServerSocket serverSocket = new ServerSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // on fait un serveur socket sur le port pour les
		// connexions qu'on transforme en socket

	}

	public void lancerServeur(PartieMultijoueur partie, int port) throws InvalidPortException {
		NetworkUtils.checkPort(port);
		try {
			serverSocket.setReuseAddress(true);
			try {
				serverSocket.bind(new java.net.InetSocketAddress(port));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		serverOn = true;
		new Thread(() -> {
			System.out.println("Lancement du serveur...");
			try {
				ThreadGroup groupe = new ThreadGroup("socketsClients"); // on fait un groupe de thread pour gérer les
																		// multiples connexion au serveur

				noConnexion = 0;

				// on accepte les connexion sur la server socket et on incrémente le nombre de
				// connexions, à chaque connexion on démarre un serveurthread à l'aide du thread
				// group
				while (serverOn) {
					try {
						Socket clientSocket = serverSocket.accept();
						noConnexion++;
						ServeurThread st = new ServeurThread(clientSocket, groupe, noConnexion, partie);
						st.start();
					} catch (SocketException se) {
						this.serverOn = false;
					}

				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public int getNoConnexion() {
		return this.noConnexion;
	}

}
