package main.java.controleur;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import main.java.model.Puzzle;
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieMultijoueur;
import main.java.model.partie.PartieMultijoueurCooperative;
import main.java.utils.Utils;
import main.java.vue.VueJeuMultiCoop;

public class LobbyControleur implements Initializable{

	private Joueur joueur;
	private boolean estHote;
	private boolean estCoop;
	private Stage owner;
	private Image img;
	private int taille;
	private int numJoueur = 1; //DEBUG
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
	
	public LobbyControleur(Stage stage, Joueur j, boolean estHote) throws IOException {
		this.owner = stage;
		this.estHote = estHote;
		byte[] imgjoueur = Files.readAllBytes(Paths.get("src/main/resources/images/defaulticon.png"));
		this.joueur = j;
	}
	
	public LobbyControleur(Stage stage, PartieMultijoueur partie, Joueur j, boolean estHote, boolean estCoop, Image img, int taille, Client client) throws IOException {
		this.owner = stage;
		this.estHote = estHote;
		this.estCoop = estCoop;
		this.img = img;
		this.taille = taille;
		this.client=client;
		this.partie=partie;
		byte[] imgjoueur = Files.readAllBytes(Paths.get("src/main/resources/images/defaulticon.png"));
		this.joueur = j;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lancerPartie.setManaged(estHote);
		this.updateJoueurs();
		this.updateInfos();
	}
	
	private void updateJoueurs() {
		//TODO
		for(Joueur j : joueurs) {
			this.boxJoueurs.getChildren().clear();
			VBox v = new VBox(); //Box dans laquelle on affichera les infos des joueurs
			v.setAlignment(Pos.CENTER);
			v.setPrefHeight(200);
			v.setPrefWidth(100);
			v.setSpacing(5);
			ImageView i = new ImageView(); //Logo du joueur
			i.setFitHeight(60);
			i.setFitWidth(60);
			Image image = new Image(new ByteArrayInputStream(this.joueur.getImage()));
			i.setImage(image);
			Label l = new Label(j.getNom()); //Pseudo du joueur
			v.setId("box"+j.getNom());
			v.getChildren().add(i);
			v.getChildren().add(l);
			boxJoueurs.getChildren().add(v); //Ajout a la box principal
		}
	}
	
	private void updateInfos() {
		//TODO
		this.imagePuzzle.setImage(img);
		this.labelTaille.setText("Taille : "+this.taille);
		this.labelType.setText("Partie "+(estCoop ? "coopérative" : "compétitive"));
	}
	
	@FXML
	private void lancerPartieMulti() throws IOException {
		byte[] newImg = Utils.imageToByteArray(img,null);
		partie.lancerPartie(newImg, taille);
		VueJeuMultiCoop vj = new VueJeuMultiCoop(taille, newImg, numJoueur, joueur, partie.getJoueurs(), 
				((PartieMultijoueurCooperative) partie).getIndexJoueurCourant(), ((PartieMultijoueurCooperative) partie).getPuzzleCommun(),
				this.client);
	}
	
	private void readStream() throws IOException, ClassNotFoundException {
		BufferedReader fluxEntrant = new BufferedReader(
		new InputStreamReader(client.getSocket().getInputStream()));
		String s = fluxEntrant.readLine();
		ObjectInputStream ois = new ObjectInputStream(client.getSocket().getInputStream());
		Joueur j = (Joueur) ois.readObject();
		if(s.equals("c")) {
			joueurs.add(j);
		}else if(s.equals("d")) {
			joueurs.remove(j);
		}
		this.updateJoueurs();
	}
	
}
