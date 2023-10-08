package main.java.controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.java.vue.VueGenerale;

public class MenuControleur implements Initializable{
	
	Stage owner;

	public MenuControleur(Stage stage) {
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
		((VueGenerale) this.owner).changerVue("src/main/resources/ui/fxml/NouvellePartie.fxml", new NouvellePartieControleur(this.owner));
	}
	
	@FXML
	private void continuerPartie(ActionEvent event) {
		System.out.println("Continuer Partie");
	}
	
	@FXML
	private void recherchePartie(ActionEvent event) throws IOException {
		VueGenerale vm = new VueGenerale(this.owner);
		vm.changerVue("src/main/resources/ui/fxml/Connexion.fxml", new RecherchePartieControleur(this.owner));
	}
	
	@FXML
	private void statistiques(ActionEvent event) throws IOException {
		VueGenerale vm = new VueGenerale(this.owner);
		vm.changerVue("src/main/resources/ui/fxml/Statistiques.fxml", new StatistiquesControleur(this.owner));

	}
	
	@FXML
	private void theme(ActionEvent event) {
		System.out.println("Theme");
	}
	
}
