package main.java.controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class JeuControleur implements Initializable {

	protected Stage owner;

	protected double xClick;
	protected double yClick;

	@FXML
	protected Label chrono;
	@FXML
	protected Label victoireLabel;
	@FXML
	protected Label nbCoups;

	@FXML
	protected ImageView logoJoueur;
	@FXML
	protected Label pseudoJoueur;

	@FXML
	protected AnchorPane grille;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	protected abstract void initJoueur();

	protected abstract void updateImages();

	protected abstract void updateJeu();

	protected abstract void updateInfos();

	protected abstract void updateVictoire();

	protected void updateAll() {
		updateJeu();
		updateInfos();
	}

	protected abstract void setKeyController();

}
