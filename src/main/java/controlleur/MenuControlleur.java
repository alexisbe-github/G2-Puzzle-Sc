package main.java.controlleur;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MenuControlleur implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void quit(ActionEvent event) {
		 System.exit(0); // Exit with status code 0 (normal exit)
	}
	
}
