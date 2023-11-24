package main.java.controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.model.joueur.Joueur;

public class StatistiquesControleur implements Initializable {

	private Stage owner;
	
	@FXML
	private TableView<Joueur> tableau;
	@FXML
	private TableColumn<Joueur, Image> colonnePhoto;
	@FXML
	private TableColumn<Joueur, String> colonnePseudo;
	@FXML
	private TableColumn<Joueur, Integer> colonneVictoires;

	public StatistiquesControleur(Stage stage) {
		this.owner = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
