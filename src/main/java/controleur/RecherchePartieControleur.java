package main.java.controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecherchePartieControleur implements Initializable{

	Stage owner;
	
	@FXML
	TextField saisieIP;
	
	@FXML
	TextField saisiePort;
	
	public RecherchePartieControleur(Stage stage) {
		this.owner = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void connexion(ActionEvent event){
		 System.out.println("Connexion ip:"+saisieIP.getText()+" Port:"+saisiePort.getText());
	}
	
}
