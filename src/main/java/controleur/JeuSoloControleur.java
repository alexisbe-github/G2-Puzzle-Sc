package main.java.controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.model.EDeplacement;
import main.java.model.ia.expertsystem.SystemeExpert;
import main.java.model.partie.PartieSolo;
import main.java.model.serialisation.Serialisation;

public class JeuSoloControleur extends JeuControleur implements Initializable, PropertyChangeListener {

	private PartieSolo partie;
	private boolean estEnPause = false;
	
	private List<EDeplacement> solution;
	private Thread threadIA;
	private int i; // index solution IA
	private boolean iaLance = false;

	@FXML
	private Button boutonIA;
	@FXML
	private Button boutonUndo;
	@FXML
	private Button boutonQuitter;
	@FXML
	private Button boutonPause;

	/**
	 * 
	 * @param stage  : fenêtre dans laquelle la scene est affichée
	 * @param partie : partie jouée
	 * @throws IOException : Exception lors d'un problème de lecture de l'image
	 */
	public JeuSoloControleur(Stage stage, PartieSolo partie, int taille, byte[] img) throws IOException {
		this.owner = stage;
		this.partie = partie;
		
		partie.lancerPartie(img, taille);
		owner.setOnCloseRequest(event -> this.handleExit(event));
		this.owner.requestFocus();
	}

	public JeuSoloControleur(Stage stage, PartieSolo partie) {
		this.owner = stage;
		this.partie = partie;
		owner.setOnCloseRequest(event -> this.handleExit(event));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boutonPause.setFocusTraversable(false);
		boutonIA.setFocusTraversable(false);
		boutonUndo.setFocusTraversable(false);
		boutonQuitter.setFocusTraversable(false);

		this.updateImages();
		this.initJoueur();

		this.partie.addPropertyChangeListener(this);

		grille.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> this.handlePressAction(event));
		grille.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> this.handleReleaseAction(event));
	}

	/**
	 * mise a jour des images affichées en fonction de la position des cases dans la
	 * grille
	 */
	@Override
	protected void updateImages() {
		// Définition de la taille d'une case
		double largeurCase = owner.getWidth() / this.partie.getPuzzle().getTaille() * 0.5;
		Image image;
		grille.getChildren().clear();

		for (int i = 0; i < partie.getPuzzle().getTaille(); i++) {
			for (int j = 0; j < partie.getPuzzle().getTaille(); j++) {
				Label l = new Label();
				if (partie.getPuzzle().getCase(j, i).getIndex() != -1)
					l.setText("" + ((int) partie.getPuzzle().getCase(j, i).getIndex() + 1));
				l.setFont(new Font(18));
				l.setTextFill(Color.YELLOW);
				l.setPrefWidth(largeurCase);
				l.setPrefHeight(largeurCase);
				l.setAlignment(Pos.CENTER);
				l.setLayoutX(j * largeurCase);
				l.setLayoutY(i * largeurCase);

				l.setId("case" + partie.getPuzzle().getCase(j, i).getIndex());

				image = new Image(new ByteArrayInputStream(partie.getPuzzle().getCase(j, i).getImage()));

				Background bgi = new Background(
						new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
								BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true, true, true, false)));
				if (partie.getPuzzle().getCase(j, i).getIndex() != -1)
					l.setBackground(bgi);
				grille.getChildren().add(l);
			}
		}
	}

	@Override
	protected void updateJeu() {
		this.updateImages();
		if (this.partie.getPuzzle().verifierGrille())
			this.updateVictoire();
	}

	@Override
	protected void updateVictoire() {
		victoireLabel.setVisible(true);
		this.estEnPause = true;
	}

	@Override
	protected void initJoueur() {
		Image image = new Image(new ByteArrayInputStream(this.partie.getJoueur().getImage()));
		this.logoJoueur.setImage(image);
		this.pseudoJoueur.setText(this.partie.getJoueur().getNom());
		this.updateInfos();
	}

	@Override
	protected void updateInfos() {
		this.nbCoups.setText("Nombre de coups : " + this.partie.getPuzzle().getNbCoups());
	}

	@Override
	public void setKeyController() {

		this.owner.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!estEnPause) {
					// dpAnim(event.getCode());
					switch (event.getCode()) {
					case UP:
						if (!partie.getPuzzle().undoActive()) {
							boutonUndo.setDisable(true);
						}
						if (!iaLance) {
							partie.deplacerCase(EDeplacement.HAUT);
						}
						break;
					case DOWN:
						if (!partie.getPuzzle().undoActive()) {
							boutonUndo.setDisable(true);
						}
						if (!iaLance) {
							partie.deplacerCase(EDeplacement.BAS);
						}
						break;
					case LEFT:
						if (!partie.getPuzzle().undoActive()) {
							boutonUndo.setDisable(true);
						}
						if (!iaLance) {
							partie.deplacerCase(EDeplacement.GAUCHE);
						}
						break;
					case RIGHT:
						if (!partie.getPuzzle().undoActive()) {
							boutonUndo.setDisable(true);
						}
						if (!iaLance) {
							partie.deplacerCase(EDeplacement.DROITE);
						}
						break;
					default:
						break;
					}
				}
			}
		});
	}

	@FXML
	private void startIAButton(ActionEvent event) {

		if (boutonIA.getText().equals("Lancer l'IA")) {
			i = 0;
			solution = SystemeExpert.solveTaquin(partie.getPuzzle());
			Task<Void> task = new Task<Void>() { // tâche parallèle mise à jour vue
				public Void call() throws Exception {

					while (!partie.getPuzzle().verifierGrille()) {
						Platform.runLater(new Runnable() { // classe anonyme

							public void run() {
								if (i < solution.size()) {
									partie.deplacerCase(solution.get(i));
									i++;
								}
							}
						});
						Thread.sleep(300);
					}
					return null;
				}
			};
			boutonIA.setText("Arrêter l'IA");
			threadIA = new Thread(task);
			threadIA.setDaemon(true);
			threadIA.start();
			iaLance = true;
			boutonUndo.setDisable(true);
		} else {
			boutonIA.setText("Lancer l'IA");
			threadIA.interrupt();
			if (partie.getPuzzle().undoActive()) {
				boutonUndo.setDisable(false);
			}
			iaLance = false;
		}

	}

	@FXML
	private void undoButton(ActionEvent event) {
		this.partie.undo();
		if (!this.partie.getPuzzle().undoActive()) {
			boutonUndo.setDisable(true);
		}
	}

	@FXML
	private void pauseButton(ActionEvent event) {
		if (!this.partie.getPuzzle().verifierGrille()) {
			this.estEnPause = !estEnPause;
		}
	}

	@FXML
	private void quitButton(ActionEvent event) {
		handleExit(event);
		owner.close();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.updateAll();
	}

	private void handlePressAction(MouseEvent event) {
		xClick = event.getX();// translation en abscisse
		yClick = event.getY();// translation en ordonnée
	}

	private void handleReleaseAction(MouseEvent event) {
		double translateX = event.getX() - xClick;
		double translateY = event.getY() - yClick;
		if (!estEnPause && (Math.abs(translateY) > 10 || Math.abs(translateX) > 10)) {
			if (Math.abs(translateX) > Math.abs(translateY)) {
				if (translateX > 0)
					partie.deplacerCase(EDeplacement.DROITE);
				else
					partie.deplacerCase(EDeplacement.GAUCHE);
			} else {
				if (translateY > 0)
					partie.deplacerCase(EDeplacement.BAS);
				else
					partie.deplacerCase(EDeplacement.HAUT);
			}
		}
	}

	/**
	 * Gère la situation dans laquelle l'utilisateur quitte la partie en cours
	 * 
	 * @param e L'événement
	 */
	private void handleExit(Event e) {
//		if (!(e instanceof WindowEvent || e instanceof ActionEvent)) {
//			return;
//		}
		// TODO Ne pas sérialiser si la partie est terminée
		String dossier = "src/main/java/model/serialisation/objets/";
		String nom = String.format("partie_solo-%d.ser", System.currentTimeMillis());
		String chemin = dossier + nom;
		Serialisation.serialiserObjet(this.partie, chemin);
	}



}
