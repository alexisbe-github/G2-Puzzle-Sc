package main.java.model.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TestClient {

	public static void main(String[] args) throws IOException {
		String ip = getIP();
		int port = 8080;
		Client c = new Client();
		c.seConnecter(ip, port);
		String message;
		Scanner sc = new Scanner(System.in);
		while (true) {
			message = sc.nextLine();
			c.lancerRequete(message);
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
