package main.java.controleur;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.model.Puzzle;
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;

public class JeuMultiCoopControleur extends JeuMultiControleur implements Initializable {

	private int numJoueurCourant;

	boolean flagThreadEnd = false;
	private int numJoueur;

	/**
	 * 
	 * @param stage  : fenêtre dans laquelle la scene est affichée
	 * @param partie : partie jouée
	 * @throws IOException : Exception lors d'un problème de lecture de l'image
	 */
	public JeuMultiCoopControleur(Stage stage, int numJoueur, Joueur joueur, List<Joueur> joueurs, int numJoueurCourant,
			Puzzle puzzle, Client client) throws IOException {
		this.owner = stage;
		this.joueur = joueur;
		this.numJoueur = numJoueur;
		this.client = client;

		this.numJoueurCourant = numJoueurCourant;
		this.puzzle = puzzle;
		this.joueurs = joueurs;

	}

	@Override
	protected void readStream() throws IOException, InterruptedException {

		System.out.println("super");

		this.client.lancerRequete("p");

		while (!this.puzzle.verifierGrille()) {
			Platform.runLater(() -> {

				try {
					ObjectInputStream ois;
					ois = new ObjectInputStream(client.getSocket().getInputStream());
					Object oisObj = ois.readObject();

					if (oisObj instanceof List) {
						List<Object> tab = (List<Object>) oisObj;
						if (tab.get(0).equals("p")) {
							this.puzzle = (Puzzle) tab.get(2);
							this.numJoueurCourant = (int) tab.get(1);
							updateAll();
						}
						client.lancerRequete("p");
					}

				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			});
			Thread.sleep(500);
		}

	}

	@Override
	protected  void lancerThread() {
		new Thread(() -> {
			try {
				readStream();
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}



}
