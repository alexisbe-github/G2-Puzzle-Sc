package main.java.controleur;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.model.bdd.SQLUtils;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;

public class StatistiquesControleur implements Initializable {

	private Stage owner;

	@FXML
	private TableView<JoueurSQL> tableau;
	@FXML
	private TableColumn<JoueurSQL, String> colonnePhoto;
	@FXML
	private TableColumn<JoueurSQL, String> colonnePseudo;
	@FXML
	private TableColumn<JoueurSQL, Long> colonneVictoires;

	public StatistiquesControleur(Stage stage) {
		this.owner = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		DAOJoueur daoj = new DAOJoueur();
		List<JoueurSQL> jsql = daoj.trouverTout();
		ObservableList<JoueurSQL> joueursData = FXCollections.observableArrayList(jsql);

		colonnePseudo.setCellValueFactory(new PropertyValueFactory<JoueurSQL, String>("pseudo"));
		colonnePhoto.setCellValueFactory(new PropertyValueFactory<JoueurSQL, String>("urlpp"));
		colonneVictoires.setCellValueFactory(new PropertyValueFactory<JoueurSQL, Long>("id"));

		colonnePseudo.setCellFactory(param -> new TableCell<JoueurSQL, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
					setGraphic(null);
				} else {
					this.setAlignment(Pos.CENTER);
					setGraphic(new Label(item));
				}
				this.setItem(item);
			}
		});
		
		colonnePhoto.setCellFactory(param -> new TableCell<JoueurSQL, String>() {
			private ImageView imageView = new ImageView();
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
					setGraphic(null);
				} else {
					imageView.setImage(new Image(item));
					imageView.setFitWidth(50);
					imageView.setFitHeight(50);
					this.setAlignment(Pos.CENTER);
					setGraphic(imageView);
				}
				this.setItem(item);
			}
		});
		
		colonneVictoires.setCellFactory(param -> new TableCell<JoueurSQL, Long>() {
			@Override
			protected void updateItem(Long item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
					setGraphic(null);
				} else {
					this.setAlignment(Pos.CENTER);
					int nbVictoires = 0;
					DAOJoueur daoj = new DAOJoueur();
					JoueurSQL j = daoj.trouver(item);
					nbVictoires = SQLUtils.getNbVictoires(j,0);
					setGraphic(new Label(""+nbVictoires));
				}
				this.setItem(item);
			}
		});

		this.tableau.setItems(joueursData);

	}

}
