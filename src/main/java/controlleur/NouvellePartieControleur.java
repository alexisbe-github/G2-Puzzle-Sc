package main.java.controlleur;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import main.java.model.joueur.Joueur;
import main.java.model.partie.PartieSolo;
import main.java.vue.VueJeuSolo;
import main.java.vue.VueMenu;

public class NouvellePartieControleur implements Initializable{

	Stage owner;
	Joueur joueurChoisi;
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
		this.joueurChoisi = new Joueur("popsimouk", ImageIO.read(new File("src/main/resources/images/defaulticon.png")));
		this.imageChoisie = ImageIO.read(new File("src/test/resources/testimg.jpg"));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateInfosJoueur();
		
		List<Joueur> jtest = new LinkedList<Joueur>();
		try {
			jtest.add(new Joueur("babarstat", ImageIO.read(new File("src/main/resources/images/defaulticon.png"))));
			jtest.add(new Joueur("emile2006", ImageIO.read(new File("src/main/resources/images/defaulticon.png"))));
			jtest.add(new Joueur("alexsiuuu", ImageIO.read(new File("src/main/resources/images/defaulticon.png"))));
			jtest.add(new Joueur("gustavo", ImageIO.read(new File("src/main/resources/images/defaulticon.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		updateListeProfils(jtest);
		updateImagePartie();
		
		this.radioGroupe = new ToggleGroup();
		
		this.IARadio.setToggleGroup(radioGroupe);
		this.multiCompetRadio.setToggleGroup(radioGroupe);
		this.multiCoopRadio.setToggleGroup(radioGroupe);
		this.soloRadio.setToggleGroup(radioGroupe);
		
	}
	
	
	private void updateInfosJoueur() {
		imageJoueur.setImage(SwingFXUtils.toFXImage(this.joueurChoisi.getImage(), null));
		pseudoJoueur.setText(this.joueurChoisi.getNom());
	}
	
	
	private void updateListeProfils(List<Joueur> joueurs) {
		menuProfils.getItems().clear();
		for(Joueur j : joueurs) {
			MenuItem item = new MenuItem(j.getNom());
			item.setOnAction( value -> {
				this.setJoueurChoisi(j);
			});
			menuProfils.getItems().add(item);
		}
	}
	
	private void updateImagePartie() {
		imagePerso.setImage(SwingFXUtils.toFXImage(this.imageChoisie, null));
	}
	
	private void setJoueurChoisi(Joueur j) {
		this.joueurChoisi = j;
		this.updateInfosJoueur();
	}
	
	@FXML
	private void lancerPartie(ActionEvent event) throws IOException {
		try {
			if(soloRadio.isSelected()) {
				int taille = Integer.parseInt(this.saisieTaille.getText());
				new VueJeuSolo(new PartieSolo(joueurChoisi), taille, imageChoisie);
			}else if(multiCoopRadio.isSelected()){
				//TODO
			}else if(multiCompetRadio.isSelected()) {
				//TODO
			}else if(IARadio.isSelected()) {
				//TODO
			}
		}catch(NumberFormatException e) {
			
		}
	}
	
	@FXML
	private void changerImageBouton(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("JPG Images (*.jpg, *.jpeg)", "*.jpg", "*.jpeg"));
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(this.owner);
		if(file!=null) {
			this.imageChoisie = ImageIO.read(file);
			imageChoisie = Scalr.resize(imageChoisie, Scalr.Mode.FIT_EXACT, 1000, 1000);
			this.updateImagePartie();
		}
	}
	
	@FXML
	private void creerProfilBouton(ActionEvent event) throws IOException {
		((VueMenu) this.owner).changerVue("src/main/resources/ui/fxml/CreationProfil.fxml", new CreerProfilControleur(this.owner));
	}
	
}
