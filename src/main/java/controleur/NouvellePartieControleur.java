package main.java.controleur;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.model.partie.ContextePartie;
import main.java.model.partie.PartieMultijoueurCooperative;
import main.java.model.partie.PartieSolo;
import main.java.model.serveur.Serveur;
import main.java.utils.InvalidPortException;
import main.java.utils.NetworkUtils;
import main.java.vue.VueGenerale;
import main.java.vue.VueJeuSolo;

public class NouvellePartieControleur implements Initializable{

	Stage owner;
	JoueurSQL joueurChoisi;
	BufferedImage imageChoisie;
	ToggleGroup radioGroupe;
	
	@FXML
	RadioButton soloRadio;
	@FXML
	RadioButton multiCoopRadio;
	@FXML
	RadioButton multiCompetRadio;
	@FXML
	RadioButton IARadio;
	
	@FXML
	MenuButton menuProfils;
	
	@FXML
	ImageView imageJoueur;
	@FXML
	Label pseudoJoueur;
	
	@FXML
	TextField saisieTaille;
	
	@FXML
	ImageView imagePerso;

	public NouvellePartieControleur(Stage stage) throws IOException {
		this.owner = stage;
		this.imageChoisie = ImageIO.read(new File("src/test/resources/testimg.jpg"));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		this.menuProfils.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> this.handleMenuClick(event));

		this.radioGroupe = new ToggleGroup();
		
		this.IARadio.setToggleGroup(radioGroupe);
		this.multiCompetRadio.setToggleGroup(radioGroupe);
		this.multiCoopRadio.setToggleGroup(radioGroupe);
		this.soloRadio.setToggleGroup(radioGroupe);
		
		try {
			this.updateAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void updateInfosJoueur() throws IOException {
		if(this.joueurChoisi != null) {
			//imageJoueur.setImage(SwingFXUtils.toFXImage(this.joueurChoisi.getImage(), null));
			imageJoueur.setImage(SwingFXUtils.toFXImage(ImageIO.read(new File("src/main/resources/images/defaulticon.png")),null));
			pseudoJoueur.setText(this.joueurChoisi.getPseudo());
		}
	}
	
	private void updateAll() throws IOException {
		this.updateImagePartie();
		this.updateInfosJoueur();
		this.updateListeProfils();
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
					try {
						this.setJoueurChoisi(jactuel);
						this.menuProfils.setText(jactuel.getPseudo());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				menuProfils.getItems().add(item);
			}
		}
	}
	
	private void updateImagePartie() {
		imagePerso.setImage(SwingFXUtils.toFXImage(this.imageChoisie, null));
	}
	
	private void setJoueurChoisi(JoueurSQL j) throws IOException {
		this.joueurChoisi = j;
		this.updateInfosJoueur();
	}
	
	
	@FXML
	private void changerImageBouton(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("JPG Images (*.jpg, *.jpeg)", "*.jpg", "*.jpeg"));
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(this.owner);
		if(file!=null) {
			imageChoisie = Scalr.resize(ImageIO.read(file), Scalr.Mode.FIT_EXACT, 1000, 1000);
			this.updateImagePartie();
		}
	}
	
	@FXML
	private void creerProfilBouton(ActionEvent event) throws IOException {
		VueGenerale vm = new VueGenerale(this.owner);
		vm.changerVue("Creation Profil", "src/main/resources/ui/fxml/CreationProfil.fxml", new CreerProfilControleur(vm));
	}
	
	private void handleMenuClick(MouseEvent event){
		try {
			this.updateListeProfils();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void erreurLancement() {
		System.out.println("erreurlancement");
	}
	
	
	@FXML
	private void lancerPartie(ActionEvent event) throws IOException, ClassNotFoundException {
		if(joueurChoisi != null) {
			try {
				Joueur j = new Joueur(joueurChoisi.getPseudo(), ImageIO.read(new File("src/main/resources/images/defaulticon.png")));
				int taille = Integer.parseInt(this.saisieTaille.getText());
				if(soloRadio.isSelected()) {
					//new VueJeuSolo(new PartieSolo(new Joueur(joueurChoisi.getPseudo(), joueurChoisi.getImage()), taille, imageChoisie);
					new VueJeuSolo(new PartieSolo(j), taille, imageChoisie);
				}else if(multiCoopRadio.isSelected()){
					VueGenerale vg = new VueGenerale(this.owner);
					
					ContextePartie cp = new ContextePartie(j);
					PartieMultijoueurCooperative pm = new PartieMultijoueurCooperative();
					cp.setStrategy(pm);
					try {
						Serveur s = new Serveur();
						s.lancerServeur(pm, 8080);
					}catch(InvalidPortException e) {
						//TODO
					}
					Client c = new Client(j);
					c.seConnecter(NetworkUtils.getServeurIPV4(true), 8080); //ICI BUG
					
					LobbyControleur lc = new LobbyControleur(vg, pm, true, true, imageChoisie, taille);
					vg.changerVue("Lobby", "src/main/resources/ui/fxml/Lobby.fxml", lc);
				}else if(multiCompetRadio.isSelected()) {
					//TODO
				}else if(IARadio.isSelected()) {
					//TODO
				}
			}catch(NumberFormatException e) {
				this.erreurLancement();
			}
		}
	}
	
}
