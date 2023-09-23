package main.java.model.serveur;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Serveur {

	public static void lancerServeur() {
		System.out.println("Lancement du serveur...");
		String ip = getIP();
		try {
			ThreadGroup groupe = new ThreadGroup("socketsClients");
			ServerSocket serverSocket = new ServerSocket(8080);
			System.out.println("\n\n\nServeur lancé:\nIP: " + ip + "\nPort: " + serverSocket.getLocalPort());
			int noConnexion = 0;
			while(true) {
				Socket clientSocket = serverSocket.accept();
				noConnexion++;
				ServeurThread st = new ServeurThread(clientSocket, groupe, noConnexion);
				st.start();
			}
		
			
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
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
