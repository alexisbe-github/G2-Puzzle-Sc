package main.java.controleur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.vue.VueGenerale;

public class RecherchePartieControleur implements Initializable{

	Stage owner;
	JoueurSQL joueurChoisi;
	
	@FXML
	TextField saisieIP;
	
	@FXML
	TextField saisiePort;
	
	@FXML
	MenuButton menuProfils;
	
	public RecherchePartieControleur(Stage stage) {
		this.owner = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.updateListeProfils();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateListeProfils() throws IOException {
		List<JoueurSQL> joueurs = new ArrayList<JoueurSQL>();
		DAOJoueur j = new DAOJoueur();
		joueurs = j.trouverTout();
		menuProfils.getItems().clear();
		if(!joueurs.isEmpty()) {
			for(JoueurSQL jactuel : joueurs) {
				MenuItem item = new MenuItem(jactuel.getPseudo());
				item.setOnAction( value -> {
					joueurChoisi = jactuel;
					this.menuProfils.setText(jactuel.getPseudo());
				});
				menuProfils.getItems().add(item);
			}
		}
	}
	
	@FXML
	private void connexion(ActionEvent event) throws IOException, NumberFormatException, ClassNotFoundException{
		if(joueurChoisi!=null) {
			Client c = new Client(new Joueur(joueurChoisi.getPseudo(), new File("src/main/resources/images/defaulticon.png").toURI().toURL().toString()));
			c.seConnecter(saisieIP.getText(),Integer.parseInt(saisiePort.getText()));
			//System.out.println("Connexion ip:"+saisieIP.getText()+" Port:"+saisiePort.getText());
			((VueGenerale) this.owner).changerVue("Lobby Multijoueur", "src/main/resources/ui/fxml/Lobby.fxml", new LobbyControleur(this.owner, c.getPartie(), false));
		}
	}
	
}
