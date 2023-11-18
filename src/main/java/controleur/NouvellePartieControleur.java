package main.java.controleur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

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
import javafx.scene.image.Image;
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
import main.java.utils.Utils;
import main.java.vue.VueGenerale;
import main.java.vue.VueJeuSolo;

public class NouvellePartieControleur implements Initializable {

	private Stage owner;
	private JoueurSQL joueurChoisi;
	private Image imageChoisie;
	private ToggleGroup radioGroupe;

	@FXML
	private RadioButton soloRadio;
	@FXML
	private RadioButton multiCoopRadio;
	@FXML
	private RadioButton multiCompetRadio;
	@FXML
	private RadioButton IARadio;

	@FXML
	private MenuButton menuProfils;

	@FXML
	private ImageView imageJoueur;
	@FXML
	private Label pseudoJoueur;

	@FXML
	private TextField saisieTaille;

	@FXML
	private ImageView imagePerso;

	public NouvellePartieControleur(Stage stage) throws IOException {
		this.owner = stage;
		this.imageChoisie = new Image(new File("src/test/resources/testimg.jpg").toURI().toURL().toString());
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
		if (this.joueurChoisi != null) {
			// imageJoueur.setImage(SwingFXUtils.toFXImage(this.joueurChoisi.getImage(),
			// null));
			imageJoueur.setImage(
					SwingFXUtils.toFXImage(ImageIO.read(new File("src/main/resources/images/defaulticon.png")), null));
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
		if (!joueurs.isEmpty()) {
			for (JoueurSQL jactuel : joueurs) {
				MenuItem item = new MenuItem(jactuel.getPseudo());
				item.setOnAction(value -> {
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
		imagePerso.setImage(this.imageChoisie);
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
		if (file != null) {
			imageChoisie = new Image(file.toURI().toString(), 500, 500, false, false);
			this.updateImagePartie();
		}
	}

	@FXML
	private void creerProfilBouton(ActionEvent event) throws IOException {
		VueGenerale vm = new VueGenerale(this.owner);
		vm.changerVue("Creation Profil", "src/main/resources/ui/fxml/CreationProfil.fxml",
				new CreerProfilControleur(vm));
	}

	private void handleMenuClick(MouseEvent event) {
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
		if (joueurChoisi != null) {
			try {
				byte[] img = Files.readAllBytes(Paths.get("src/main/resources/images/defaulticon.png"));
				Joueur j = new Joueur(joueurChoisi.getPseudo(), img);
				int taille = Integer.parseInt(this.saisieTaille.getText());
				if (soloRadio.isSelected()) {
					// new VueJeuSolo(new PartieSolo(new Joueur(joueurChoisi.getPseudo(),
					// joueurChoisi.getImage()), taille, imageChoisie);
					new VueJeuSolo(new PartieSolo(j), taille, Utils.imageToByteArray(imageChoisie, null));
				} else if (multiCoopRadio.isSelected()) {
					try {
						VueGenerale vg = new VueGenerale(this.owner);

						ContextePartie cp = new ContextePartie(j);
						PartieMultijoueurCooperative pm = new PartieMultijoueurCooperative();
						cp.setStrategy(pm);
						
						Serveur s = new Serveur();
						s.lancerServeur(pm, 8080);
						
						Client c = new Client(j);
						c.seConnecter(NetworkUtils.getServeurIPV4(true), 8080);

						LobbyControleur lc = new LobbyControleur(vg, pm, j, true,
								Utils.imageToByteArray(imageChoisie, null), taille, c);
						vg.changerVue("Lobby", "src/main/resources/ui/fxml/Lobby.fxml", lc);
					} catch (InvalidPortException e) {
						// TODO
						e.printStackTrace();
					}
				} else if (multiCompetRadio.isSelected()) {
					// TODO
				} else if (IARadio.isSelected()) {
					// TODO
				}
			} catch (NumberFormatException e) {
				this.erreurLancement();
			}
		}
	}

}
