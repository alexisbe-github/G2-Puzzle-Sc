package main.java.model.serveur;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import main.java.model.Puzzle;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieMultijoueur;

public class Serveur {

	private static PartieMultijoueur partie;

	public static void lancerServeur(PartieMultijoueur partieM) {
		partie = partieM;
		new Thread(() -> {
			System.out.println("Lancement du serveur...");
			String ip = getIP();
			try {
				ThreadGroup groupe = new ThreadGroup("socketsClients");
				ServerSocket serverSocket = new ServerSocket(8080);
				System.out.println("\n\n\nServeur lancé:\nIP: " + ip + "\nPort: " + serverSocket.getLocalPort());
				int noConnexion = 0;
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

	/**
	 * @return the IPv4 of the machine (String)
	 */
	private static String getIP() {
		String ip = "";
		try (final DatagramSocket socket = new DatagramSocket()) {
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}
}
