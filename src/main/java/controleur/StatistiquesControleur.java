package main.java.controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.model.joueur.Joueur;

public class StatistiquesControleur implements Initializable {

	private Stage owner;
	
	@FXML
	private TableView<Joueur> tableau;
	@FXML
	private TableColumn<Joueur, Image> colonnePhoto;
	@FXML
	private TableColumn<Joueur, String> colonnePseudo;
	@FXML
	private TableColumn<Joueur, Integer> colonneVictoires;

	public StatistiquesControleur(Stage stage) {
		this.owner = stage;
	}

	@Override
	@FXML
	public void initialize(URL location, ResourceBundle resources) {
        colonnePhoto.setCellValueFactory(cellData -> {
			try {
				return null; // new SimpleObjectProperty<>(SwingFXUtils.toFXImage((Utils.byteArrayToBufferedImage(cellData.getValue().getImage())), null));
			} catch (/*IO*/Exception e) {
				return new SimpleObjectProperty<>();
			}
		});
        colonnePseudo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNom()));
        colonneVictoires.setCellValueFactory(cellData -> new SimpleObjectProperty<>(0));

        try {
//			tableau.getItems().add(new Joueur("Jean-Pierre", Utils.imageToByteArray(new Image("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png"), "png")));
//			tableau.getItems().add(new Joueur("Jean-Paul", Utils.imageToByteArray(new Image("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png"), "png")));
//	        tableau.getItems().add(new Joueur("Jean-Patrick", Utils.imageToByteArray(new Image("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png"), "png")));
//	        tableau.getItems().add(new Joueur("Jean-Peuplu", Utils.imageToByteArray(new Image("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png"), "png")));
		} catch (/*IO*/Exception e) {
			e.printStackTrace();
		}
	}

}
