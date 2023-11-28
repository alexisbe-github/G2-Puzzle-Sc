package main.java.controleur;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.utils.HostedImage;

public class CreerProfilControleur implements Initializable {

	private Stage owner;
	private BufferedImage image;
	private String extension;

	@FXML
	private ImageView imageJoueur;

	@FXML
	private TextField saisiePseudo;

	public CreerProfilControleur(Stage stage) throws IOException {
		this.owner = stage;
		this.image = ImageIO.read(new File("src/main/resources/images/defaulticon.png"));
	}

	private void updateImage() {
		image = Scalr.resize(image, Scalr.Mode.FIT_EXACT, 250, 250);
		this.imageJoueur.setImage(SwingFXUtils.toFXImage(this.image, null));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.updateImage();
		this.addImageAction();
	}

	private void addImageAction() {
		this.imageJoueur.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters()
						.add(new ExtensionFilter("Images (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png"));
				fileChooser.setTitle("Sélectionner une image");
				File file = fileChooser.showOpenDialog(owner);
				if (file != null) {
					try {
						image = Scalr.resize(ImageIO.read(file), Scalr.Mode.FIT_EXACT, 1000, 1000);
						extension = file.getName().substring(file.getName().lastIndexOf(".") + 1,
								file.getName().length());
						if (extension.equals("jpeg"))
							extension = "jpg";
					} catch (Exception e) {
						e.printStackTrace();
					}
					updateImage();
				}
			}
		});
	}

	@FXML
	public void creerProfilBouton() {
		if (this.image != null && this.saisiePseudo.getText() != null) {
			DAOJoueur dao = new DAOJoueur();
			JoueurSQL joueur = new JoueurSQL();
			joueur.setPseudo(this.saisiePseudo.getText());
			joueur.setUrlpp(HostedImage.uploadImage(image, extension));
			dao.creer(joueur);
			this.owner.close();
		}
	}

}
