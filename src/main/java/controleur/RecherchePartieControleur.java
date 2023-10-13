package main.java.controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.vue.VueGenerale;

public class RecherchePartieControleur implements Initializable{

	Stage owner;
	Joueur joueur;
	
	@FXML
	TextField saisieIP;
	
	@FXML
	TextField saisiePort;
	
	public RecherchePartieControleur(Stage stage) {
		this.owner = stage;
		this.joueur = new Joueur("pedro", null);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void connexion(ActionEvent event) throws IOException, NumberFormatException, ClassNotFoundException{
		Client c = new Client(joueur);
		c.seConnecter(saisieIP.getText(),Integer.parseInt(saisiePort.getText()));
		System.out.println("Connexion ip:"+saisieIP.getText()+" Port:"+saisiePort.getText());
		((VueGenerale) this.owner).changerVue("Lobby Multijoueur", "src/main/resources/ui/fxml/Lobby.fxml", new LobbyControleur(this.owner, c.getPartie(), false)); 
	}
	
}
