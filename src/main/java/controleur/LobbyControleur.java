package main.java.controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
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
	
	public LobbyControleur(Stage stage, PartieMultijoueur partie, boolean estHote) throws IOException {
		this.owner = stage;
		this.estHote = estHote;
		this.partie = partie;
		Joueur j = new Joueur("pedro", new File("src/main/resources/images/defaulticon.png").toURI().toURL().toString());
	}
	
	public LobbyControleur(Stage stage, PartieMultijoueur partie, boolean estHote, boolean estCoop, Image img, int taille) throws IOException {
		this.owner = stage;
		this.estHote = estHote;
		this.partie = partie;
		this.estCoop = estCoop;
		this.img = img;
		this.taille = taille;
		joueur = new Joueur("pedro", new File("src/main/resources/images/defaulticon.png").toURI().toURL().toString());
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
			i.setImage(new Image(j.getImageUrl()));
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
		VueJeuMultiCoop vj = new VueJeuMultiCoop((PartieMultijoueurCooperative) partie, taille, newImg, 0, joueur);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.updateJoueurs();
		
	}
	
}
