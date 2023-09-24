package main.java.controlleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class NouvellePartieControlleur implements Initializable{

	Stage owner;

	public NouvellePartieControlleur(Stage stage) {
		this.owner = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void lancerPartie(ActionEvent event) {
		 System.exit(0); // Exit with status code 0 (normal exit)
	}
	
}
