package main.java.controleur;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.vue.VueGenerale;

public class RecherchePartieControleur implements Initializable{

	private Stage owner;
	private JoueurSQL joueurChoisi;
	
	@FXML
	private TextField saisieIP;
	
	@FXML
	private TextField saisiePort;
	
	@FXML
	private MenuButton menuProfils;
	
	public RecherchePartieControleur(Stage stage) {
		this.owner = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.updateListeProfils();
		} catch (IOException e) {
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
			byte[] img = Files.readAllBytes(Paths.get("src/main/resources/images/defaulticon.png"));
			Joueur j = new Joueur(joueurChoisi.getPseudo(), img);
			Client c = new Client(j);
			c.seConnecter(saisieIP.getText(),Integer.parseInt(saisiePort.getText()));
			//System.out.println("Connexion ip:"+saisieIP.getText()+" Port:"+saisiePort.getText());
			((VueGenerale) this.owner).changerVue("Lobby Multijoueur", "src/main/resources/ui/fxml/Lobby.fxml", new LobbyControleur(this.owner, j, c));
		
		}
	}
	
}
