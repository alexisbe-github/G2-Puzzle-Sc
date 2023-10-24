package main.java.controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieMultijoueur;
import main.java.model.partie.PartieMultijoueurCooperative;
import main.java.utils.Utils;
import main.java.vue.VueJeuMultiCoop;

public class LobbyControleur implements Initializable, PropertyChangeListener{

	private PartieMultijoueur partie;
	private Joueur joueur;
	private boolean estHote;
	private boolean estCoop;
	private Stage owner;
	private Image img;
	private int taille;
	private int numJoueur = 1; //DEBUG
	private Client client;
	
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
		this.partie = partie;
		this.estCoop = estCoop;
		this.img = img;
		this.taille = taille;
		this.client=client;
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
		for(Joueur j : partie.getJoueurs()) {
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.updateJoueurs();
		
	}
	
}
