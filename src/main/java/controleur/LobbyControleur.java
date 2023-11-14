package main.java.controleur;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
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
import main.java.vue.VueJeuMultiCoop;

public class LobbyControleur implements Initializable {

	private Joueur joueur;
	private boolean estHote;
	private boolean estCoop;
	private Stage owner;
	// private Image img;
	private byte[] img;
	private int taille;
	private Client client;
	private PartieMultijoueur partie;
	private boolean flagLancement = false;
	private boolean flagThreadEnd = false;

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

	public LobbyControleur(Stage stage, Joueur j, Client client) throws IOException {
		this.owner = stage;
		this.estHote = false;
		this.client = client;
		this.joueur = j;
	}

	public LobbyControleur(Stage stage, PartieMultijoueur partie, Joueur j, boolean estCoop, byte[] img, int taille,
			Client client) throws IOException {
		this.owner = stage;
		this.estHote = true;
		this.estCoop = estCoop;
		this.img = img;
		this.taille = taille;
		this.client = client;
		this.partie = partie;
		this.joueur = j;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lancerPartie.setManaged(estHote);
		this.updateInfos();
		
		this.lancerThread();
		try {
			if (estHote) {
				List<Object> output = new ArrayList<Object>();
				output.add("i");
				output.add(this.img);
				// output.add(this.estCoop);
				output.add(this.taille);
				client.lancerRequete(output);
			} else {
				client.lancerRequete("i");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateJoueurs() {
		this.boxJoueurs.getChildren().clear();
		for (Joueur j : joueurs) {
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
		if (img != null && taille > 2) {
			this.imagePuzzle.setImage(new Image(new ByteArrayInputStream(this.img)));
			this.labelTaille.setText("Taille : " + this.taille);
			this.labelType.setText("Partie " + (estCoop ? "coopérative" : "compétitive"));
		}
	}

	@FXML
	private void lancerPartieMulti() throws IOException {
		partie.lancerPartie(this.img, taille);
		this.flagLancement = true;
	}

	private void readStream() throws IOException, ClassNotFoundException, InterruptedException {

		if (!this.estHote)
			this.client.lancerRequete("i");

		while (!flagThreadEnd) {
			Platform.runLater(() -> {
				List<Joueur> j;

				try {
					ObjectInputStream ois;
					ois = new ObjectInputStream(client.getSocket().getInputStream());

					Object oisObj = ois.readObject();

					if (oisObj instanceof List) {

						List<Object> tab = (List<Object>) oisObj;

						System.out.println("SIUUUU " + tab.get(0));

						if (tab.get(0).equals("i") && !estHote) {

							// 0: param, 1: joueurs, 2: image, 3: taille, 4: estCoop
							System.out.println("BIEN RECU : " + tab.get(0));
							this.taille = (int) tab.get(3);
							this.img = (byte[]) tab.get(2);
							this.estCoop = (boolean) tab.get(4);
							this.updateInfos();
							client.lancerRequete("l");

						} else if (flagLancement)
							client.lancerRequete("s");

						else
							client.lancerRequete("l");

						if (tab.get(0) instanceof String) {

							if (tab.get(0).equals("s")) {
								VueJeuMultiCoop vj = new VueJeuMultiCoop(client.getNoClient(), joueur, partie.getJoueurs(),
										((PartieMultijoueurCooperative) partie).getIndexJoueurCourant(),
										((PartieMultijoueurCooperative) partie).getPuzzleCommun(), this.client);
								flagThreadEnd = true;
//							} else if (tab.get(0).equals("i")) {
//								this.img = (byte[]) tab.get(2);
//								this.estCoop = (boolean) tab.get(3);
//								this.taille = (int) tab.get(4);
//								this.updateInfos();
							}

						}
						if (tab.get(1) instanceof List) {

							j = (List<Joueur>) tab.get(1);
							this.joueurs = new ArrayList<>(j);
							this.updateJoueurs();

						}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			});

			Thread.sleep(500);
		}

	}

//	private void sendInitStream() throws IOException {
//		//TODO
//		client.lancerRequete("i");
//		ObjectOutputStream oos = new ObjectOutputStream(client.getSocket().getOutputStream());
//		List<Object> output = new ArrayList<Object>();
//		output.add(true);
//		output.add(this.img);
//		output.add(this.taille);
//		output.add(this.estCoop);
//		oos.writeObject(output);
//	}

	private void readInitStream() {
		// TODO

	}

	private void lancerThread() {
		new Thread(() -> {
			try {
				readStream();
			} catch (InterruptedException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}

}
