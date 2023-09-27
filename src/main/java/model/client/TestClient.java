package main.java.model.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import main.java.model.joueur.Joueur;
import main.java.utils.Utils;
import main.java.utils.InvalidPortException;
import main.java.utils.NetworkUtils;

public class TestClient {

	public static void main(String[] args) throws IOException {
		Joueur j = new Joueur("Client", null);
		lancerClient(j);
	}

	public static void lancerClient(Joueur j) throws IOException {
		String ip = NetworkUtils.getServeurIPV4(true);
		int port = 8080;
		Client c = new Client(j);
		c.seConnecter(ip, port);
		System.out.println("connecté");
		String message;
		Scanner sc = new Scanner(System.in);
		new Thread(() -> {
			try {
				read(c.getSocket());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		while (true) {
			message = sc.nextLine();
			c.lancerRequete(message);
		}
	}

	public static void read(Socket s) throws IOException {
		BufferedReader fluxEntrant = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String ligne;
		while ((ligne = fluxEntrant.readLine()) != null) {
			System.out.println(ligne); // Affichez chaque ligne reçue
		}
	}

}
