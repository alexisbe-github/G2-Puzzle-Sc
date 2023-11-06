package main.java.controleur;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.model.EDeplacement;
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieMultijoueur;
import main.java.model.partie.PartieMultijoueurCooperative;
import main.java.utils.Utils;
import main.java.vue.VueJeuMultiCoop;

public class LobbyControleur implements Initializable {

	private Joueur joueur;
	private boolean estHote;
	private boolean estCoop;
	private Stage owner;
	private Image img;
	private int taille = 0;
	private int numJoueur = 1; // TODO
	private Client client;
	private PartieMultijoueur partie;

	private List<Joueur> joueurs = new ArrayList<>();

	@FXML
	private Button lancerPartie;

	@FXML
	private ImageView imagePuzzle;

	@FXML
	private HBox boxJoueurs;

	@FXML
	private Label labelTaille;

	@FXML
	private Label labelType;

	public LobbyControleur(Stage stage, Joueur j, Client client, boolean estHote) throws IOException {
		this.owner = stage;
		this.estHote = estHote;
		this.client = client;
		byte[] imgjoueur = Files.readAllBytes(Paths.get("src/main/resources/images/defaulticon.png"));
		this.joueur = j;
	}

	public LobbyControleur(Stage stage, PartieMultijoueur partie, Joueur j, boolean estHote, boolean estCoop, Image img,
			int taille, Client client) throws IOException {
		this.owner = stage;
		this.estHote = estHote;
		this.estCoop = estCoop;
		this.img = img;
		this.taille = taille;
		this.client = client;
		this.partie = partie;
		byte[] imgjoueur = Files.readAllBytes(Paths.get("src/main/resources/images/defaulticon.png"));
		this.joueur = j;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lancerPartie.setManaged(estHote);
		this.updateInfos();
		this.lancerThread();
	}

	private void updateJoueurs() {
		System.out.println("c'est censé update les joueurs : joueurs -> " + joueurs);
		this.boxJoueurs.getChildren().clear();
		for (Joueur j : joueurs) {
			System.out.println("infos joueur update : " + j.getNom() + " " + j.getImage().length);
			VBox v = new VBox(); // Box dans laquelle on affichera les infos des joueurs
			v.setAlignment(Pos.CENTER);
			v.setPrefHeight(200);
			v.setPrefWidth(100);
			v.setSpacing(5);
			ImageView i = new ImageView(); // Logo du joueur
			i.setFitHeight(60);
			i.setFitWidth(60);
			Image image = new Image(new ByteArrayInputStream(j.getImage()));
			i.setImage(image);
			Label l = new Label(j.getNom()); // Pseudo du joueur
			v.setId("box" + j.getNom());
			v.getChildren().add(i);
			v.getChildren().add(l);
			boxJoueurs.getChildren().add(v); // Ajout a la box principal
		}
	}

	private void updateInfos() {
		// TODO
		this.imagePuzzle.setImage(img);
		this.labelTaille.setText("Taille : " + this.taille);
		this.labelType.setText("Partie " + (estCoop ? "coopérative" : "compétitive"));
	}

	@FXML
	private void lancerPartieMulti() throws IOException {
		byte[] newImg = Utils.imageToByteArray(img, null);
		partie.lancerPartie(newImg, taille);
		client.lancerRequete("s");
	}

	private void readStream() throws IOException, ClassNotFoundException, InterruptedException {
		while (true) {
			Platform.runLater(() -> {
				System.out.println("avant lecture : " + joueurs);
				List<Joueur> j;
				ObjectInputStream ois;
				try {
					client.lancerRequete("l");
					ois = new ObjectInputStream(client.getSocket().getInputStream());
					j = (List<Joueur>) ois.readObject();
					joueurs = new ArrayList<>(j);
					System.out.println("apres lecture : " + joueurs);
					updateJoueurs();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			Thread.sleep(3000);
		}
	}

//	BufferedReader fluxEntrant = new BufferedReader(
//	new InputStreamReader(this.client.getSocket().getInputStream()));
//ligne = fluxEntrant.readLine();
//if (ligne != null) {
//String reponse = ligne; // calcul de la reponse
//char c = reponse.charAt(0);
//switch (c) {
//case 's':
//	VueJeuMultiCoop vj = new VueJeuMultiCoop(numJoueur, joueur, partie.getJoueurs(),
//			((PartieMultijoueurCooperative) partie).getIndexJoueurCourant(),
//			((PartieMultijoueurCooperative) partie).getPuzzleCommun(), this.client);
//	break;
//case 'i':
//	if (this.estHote) {
//		// Envoi des infos au serveur
//		ObjectOutputStream oop = new ObjectOutputStream(client.getSocket().getOutputStream());
//		PrintStream fluxSortant = new PrintStream(client.getSocket().getOutputStream());
//		oop.writeObject(this.img);
//		// fluxSortant.println(this.estCoop ? "coop" : "compet");
//		// fluxSortant.println(this.taille);
//	}
//	break;
//}
//}

	private void readInitStream() throws IOException, ClassNotFoundException {
		if (!estHote) {
			ObjectInputStream ois = new ObjectInputStream(client.getSocket().getInputStream());
			// BufferedReader fluxEntrant = new BufferedReader(
			// new InputStreamReader(this.client.getSocket().getInputStream()));

			client.lancerRequete("i");
			Image newImg = (Image) ois.readObject();
			// String newTaille = fluxEntrant.readLine();
			// String newType = fluxEntrant.readLine();

			// this.taille = Integer.parseInt(newTaille);
			// this.estCoop = newType == "cooperative";

			this.updateInfos();
		}
	}

	private void lancerThread() {
		System.out.println("le thread est censé se lancer");
		new Thread(() -> {
			try {
				readStream();
			} catch (ClassNotFoundException | IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}

}
