package main.java.controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;
import main.java.model.Case;
import main.java.model.EDeplacement;
import main.java.model.ia.expertsystem.SystemeExpert;
import main.java.model.partie.PartieSolo;
import main.java.model.serialisation.Serialisation;

public class JeuSoloControleur extends JeuControleur implements Initializable, PropertyChangeListener {

	private PartieSolo partie;
	private boolean estEnPause = false;
	ExecutorService executor = Executors.newCachedThreadPool();

	private List<EDeplacement> solution;
	private Thread threadIA;
	private int i; // index solution IA
	private boolean iaLance = false;
	private List<Label> cases = new ArrayList<>();

	@FXML
	private Button boutonIA;
	@FXML
	private Button boutonUndo;
	@FXML
	private Button boutonQuitter;
	@FXML
	private Button boutonPause;

	public JeuSoloControleur(Stage stage, PartieSolo partie) {
		this.owner = stage;
		this.partie = partie;
		this.owner.requestFocus();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boutonPause.setFocusTraversable(false);
		boutonIA.setFocusTraversable(false);
		boutonUndo.setFocusTraversable(false);
		boutonQuitter.setFocusTraversable(false);

		grille.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> this.handlePressAction(event));
		grille.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> this.handleReleaseAction(event));
		owner.setOnCloseRequest(event -> this.handleExit(event));

		this.initJoueur();
		this.updateAll();
		
		this.partie.addPropertyChangeListener(this);
		this.partie.getTimer().addPropertyChangeListener("property", this);
		this.partie.getTimer().lancerChrono();
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
				cases.add(l);
			}
		}
	}

	@Override
	protected void updateJeu() {
		this.updateImages(); // BUG ANIM VIENT D'ICI (si tu mets le updateImages dans le "Initialize" et que
								// commentes celui la ça """marche""")
		if (this.partie.getPuzzle().verifierGrille())
			this.updateVictoire();
	}

	@Override
	protected void updateVictoire() {
		victoireLabel.setVisible(true);
		this.estEnPause = true;
		this.partie.getTimer().stopChrono();
	}

	@Override
	protected void initJoueur() {
		Image image = new Image(new ByteArrayInputStream(this.partie.getJoueur().getImage()));
		this.logoJoueur.setImage(image);
		this.pseudoJoueur.setText(this.partie.getJoueur().getNom());
	}

	@Override
	protected void updateInfos() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				nbCoups.setText("Nombre de coups : " + partie.getPuzzle().getNbCoups());
				chrono.setText(partie.getTimer().getMS());
			}
		});

	}

	public void deplacerCase(EDeplacement dir) {
		if (!partie.getPuzzle().undoActive()) {
			boutonUndo.setDisable(true);
		}
		partie.deplacerCase(dir);
		this.updateJeu();
	}

	@Override
	public void setKeyController() {

		this.owner.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!estEnPause) {
					switch (event.getCode()) {
					case UP:
						animCase(EDeplacement.HAUT);
						break;
					case DOWN:
						animCase(EDeplacement.BAS);
						break;
					case LEFT:
						animCase(EDeplacement.GAUCHE);
						break;
					case RIGHT:
						animCase(EDeplacement.DROITE);
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
									animCase(solution.get(i));
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
		this.updateJeu();
	}

	@FXML
	private void pauseButton(ActionEvent event) {
		if (!this.partie.getPuzzle().verifierGrille()) {
			this.estEnPause = !estEnPause;
			if (estEnPause)
				this.partie.getTimer().stopChrono();
			else
				this.partie.getTimer().lancerChrono();
		}
	}

	@FXML
	private void quitButton(ActionEvent event) {
		handleExit(event);
		owner.close();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.updateInfos();
	}

	/**
	 * Enregistre la position du clic
	 * 
	 * @param event
	 */
	private void handlePressAction(MouseEvent event) {
		xClick = event.getX();// translation en abscisse
		yClick = event.getY();// translation en ordonnée
	}

	/**
	 * Compare les clics initiaux a la position de fin des clics et bouge les cases
	 * en conséquence.
	 * 
	 * @param event
	 */
	private void handleReleaseAction(MouseEvent event) {
		double translateX = event.getX() - xClick;
		double translateY = event.getY() - yClick;
		if (!estEnPause && (Math.abs(translateY) > 10 || Math.abs(translateX) > 10)) {
			if (Math.abs(translateX) > Math.abs(translateY)) {
				if (translateX > 0)
					this.animCase(EDeplacement.DROITE);
				else
					this.animCase(EDeplacement.GAUCHE);
			} else {
				if (translateY > 0)
					this.animCase(EDeplacement.BAS);
				else
					this.animCase(EDeplacement.HAUT);
			}
		}
	}

	/**
	 * 
	 * Renvoi le label correspondant à une case à l'aide de l'index de la case.
	 * 
	 * @param index : index de la case
	 * @return l : label correspondant à la case.
	 */
	private Label getLabelParIndex(int index) {
		for (Label l : cases) {
			if (l.getId().equals("case" + index))
				return l;
		}
		return null;
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
		if (!this.partie.getPuzzle().verifierGrille()) {
			this.partie.getTimer().stopChrono();
			String dossier = "src/main/java/model/serialisation/objets/";
			String nom = String.format("partie_solo-%d.ser", System.currentTimeMillis());
			String chemin = dossier + nom;
			Serialisation.serialiserObjet(this.partie, chemin);
		}
	}

	private void animCase(EDeplacement dir) {

		int xMultiplier = 0, yMultiplier = 0;

		switch (dir) {
		case HAUT:
			yMultiplier = -1;
			break;
		case BAS:
			yMultiplier = 1;
			break;
		case DROITE:
			xMultiplier = 1;
			break;
		case GAUCHE:
			xMultiplier = -1;
			break;
		}

		System.out.println("avantif");
		int nCoordX = partie.getPuzzle().getXCaseVide() - xMultiplier;
		int nCoordY = partie.getPuzzle().getYCaseVide() - yMultiplier;

		if (nCoordX < partie.getPuzzle().getTaille() && nCoordX >= 0 && nCoordY < partie.getPuzzle().getTaille()
				&& nCoordY >= 0) {

			System.out.println("coups " + partie.getPuzzle().getNbCoups());
			Case c = partie.getPuzzle().getCase(nCoordX, nCoordY);
			Label p = getLabelParIndex(c.getIndex());
			int largeurCase = (int) p.getWidth();

			TranslateTransition anim = new TranslateTransition(Duration.millis(200), p);

			anim.setByX(xMultiplier * largeurCase);
			anim.setByY(yMultiplier * largeurCase);
			System.out.println("dpx " + xMultiplier * largeurCase);
			System.out.println("dpy " + yMultiplier * largeurCase);
			System.out.println("node: " + p.getId());

			anim.play();

			anim.setOnFinished((event) -> {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						deplacerCase(dir);
					}

				});

			});

		}

	}

}
