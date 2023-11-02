package main.java.controleur;

import java.awt.print.Printable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
	private boolean flagThread = false;
	private Stage owner;
	private Image img;
	private int taille;
	private int numJoueur = 1; // DEBUG
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
		VueJeuMultiCoop vj = new VueJeuMultiCoop(taille, newImg, numJoueur, joueur, partie.getJoueurs(),
				((PartieMultijoueurCooperative) partie).getIndexJoueurCourant(),
				((PartieMultijoueurCooperative) partie).getPuzzleCommun(), this.client);
	}

	private void readStream() throws IOException, ClassNotFoundException, InterruptedException {
		if (!flagThread) {
			flagThread = true;
			while (true) {
				Platform.runLater(() -> {
					System.out.println("avant lecture : " + joueurs);
					List<Joueur> j;
					ObjectInputStream ois;
					try {
						client.lancerRequete("l");
						ois = new ObjectInputStream(client.getSocket().getInputStream());
						j = (List<Joueur>) ois.readObject();
						flagThread = false;
						joueurs = new ArrayList<>(j);
						System.out.println("apres lecture : " + joueurs);
						updateJoueurs();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				Thread.sleep(5000);
			}
		}
	}

	private void readInitStream() {

	}

	private void lancerThread() {
		System.out.println("le thread est censé se lancer");
		new Thread(() -> {
			try {
				readStream();

			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}

}
