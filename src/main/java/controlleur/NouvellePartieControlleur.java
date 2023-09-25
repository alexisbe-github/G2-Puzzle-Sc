package main.java.controlleur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieSolo;
import main.java.utils.Utils;
import main.java.vue.VueJeuSolo;

public class NouvellePartieControlleur implements Initializable{

	Stage owner;

	public NouvellePartieControlleur(Stage stage) {
		this.owner = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void lancerPartie(ActionEvent event) throws IOException {
		 new VueJeuSolo(new PartieSolo(
				 new Joueur("popsimouk", 
						 ImageIO.read(new File("src/main/resources/images/defaulticon.png"))
						 )
				 ));
	}
	
}
