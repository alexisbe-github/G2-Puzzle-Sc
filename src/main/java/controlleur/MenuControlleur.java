package main.java.controlleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.java.vue.VueMenu;

public class MenuControlleur implements Initializable{
	
	Stage owner;

	public MenuControlleur(Stage stage) {
		this.owner = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void quit(ActionEvent event) {
		 System.exit(0); // Exit with status code 0 (normal exit)
	}
	
	@FXML
	private void nouvellePartie(ActionEvent event) throws IOException {
		((VueMenu) this.owner).changerVue("src/main/resources/ui/fxml/NouvellePartie.fxml");
	}
	
	@FXML
	private void continuerPartie(ActionEvent event) {
		System.out.println("Continuer Partie");
	}
	
	@FXML
	private void recherchePartie(ActionEvent event) {
		System.out.println("Recherche Partie");
	}
	
	@FXML
	private void statistiques(ActionEvent event) {
		System.out.println("Statistiques");
	}
	
	@FXML
	private void theme(ActionEvent event) {
		System.out.println("Theme");
	}
	
}
