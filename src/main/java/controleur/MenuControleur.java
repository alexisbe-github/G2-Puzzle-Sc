package main.java.controleur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;
import main.java.model.partie.ContextePartie;
import main.java.model.partie.PartieMultijoueur;
import main.java.model.partie.PartieMultijoueurCompetitive;
import main.java.model.partie.PartieMultijoueurCooperative;
import main.java.model.partie.PartieSolo;
import main.java.model.serialisation.Serialisation;
import main.java.model.serveur.Serveur;
import main.java.utils.InvalidPortException;
import main.java.utils.NetworkUtils;
import main.java.vue.VueGenerale;
import main.java.vue.VueJeuSolo;

public class MenuControleur implements Initializable {

	private Stage owner;

	@FXML
	private RadioButton coopRadio;

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
		((VueGenerale) this.owner).changerVue("Nouvelle Partie", "src/main/resources/ui/fxml/NouvellePartie.fxml",
				new NouvellePartieControleur(this.owner));
	}

	@FXML
	private void continuerPartie(ActionEvent event) throws IOException {
		File folder = new File("src/main/java/model/serialisation/objets");
		File[] filesList = folder.listFiles();
		File choisi = null;
		long last = Long.MIN_VALUE;
		for(File f : filesList) {
			if(f.lastModified() > last)
				choisi = f;
		}
		PartieSolo p = (PartieSolo) Serialisation.deserialiserObjet(PartieSolo.class, choisi.getPath());
		new VueJeuSolo(p);
	}

	@FXML
	private void recherchePartie(ActionEvent event) throws IOException {
		VueGenerale vm = new VueGenerale(this.owner);
		vm.changerVue("Connexion", "src/main/resources/ui/fxml/Connexion.fxml", new RecherchePartieControleur(vm));
	}

	@FXML
	private void statistiques(ActionEvent event) throws IOException {
		VueGenerale vm = new VueGenerale(this.owner);
		vm.changerVue("Statistiques", "src/main/resources/ui/fxml/Statistiques.fxml", new StatistiquesControleur(vm));

	}

	@FXML
	private void theme(ActionEvent event) {
		System.out.println("Theme");
	}

	@FXML
	private void debug(ActionEvent event) throws IOException, InvalidPortException, ClassNotFoundException {
		boolean estCoop = coopRadio.isSelected();
		VueGenerale vg = new VueGenerale(this.owner);
		byte[] img = Files.readAllBytes(Paths.get("src/main/resources/images/defaulticon.png"));
		Joueur j = new Joueur("j1", img);
		ContextePartie cp = new ContextePartie(j);
		PartieMultijoueur p;
		if (estCoop)
			p = new PartieMultijoueurCooperative();
		else
			p = new PartieMultijoueurCompetitive();
		cp.setStrategy(p);

		Serveur s = new Serveur();
		s.lancerServeur(p, 8080);
		Client c = new Client(j);
		c.seConnecter(NetworkUtils.getServeurIPV4(true), 8080);

		LobbyControleur lc = new LobbyControleur(vg, p, j, estCoop, img, 3, c);
		vg.changerVue("Lobby", "src/main/resources/ui/fxml/Lobby.fxml", lc);
		
		
		
		Joueur j2 = new Joueur("j2", img);
		Client c2 = new Client(j2);
		c2.seConnecter("localhost", 8080);
		//System.out.println("Connexion ip:"+saisieIP.getText()+" Port:"+saisiePort.getText());
		((VueGenerale) this.owner).changerVue("Lobby Multijoueur", "src/main/resources/ui/fxml/Lobby.fxml", new LobbyControleur(this.owner, j2, c2));
	}
}
