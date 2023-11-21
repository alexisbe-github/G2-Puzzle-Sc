package main.java.controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
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
import main.java.model.Case;
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
		owner.setOnCloseRequest(event -> this.handleExit(event));
		this.owner.requestFocus();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boutonPause.setFocusTraversable(false);
		boutonIA.setFocusTraversable(false);
		boutonUndo.setFocusTraversable(false);
		boutonQuitter.setFocusTraversable(false);

		this.partie.addPropertyChangeListener(this);

		grille.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> this.handlePressAction(event));
		grille.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> this.handleReleaseAction(event));

		this.updateAll();
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

	public void deplacerCase(EDeplacement dir) {
		if (!partie.getPuzzle().undoActive()) {
			boutonUndo.setDisable(true);
		}
		if (!iaLance) {
			partie.deplacerCase(dir);
		}
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
		String dossier = "src/main/java/model/serialisation/objets/";
		String nom = String.format("partie_solo-%d.ser", System.currentTimeMillis());
		String chemin = dossier + nom;
		Serialisation.serialiserObjet(this.partie, chemin);
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

			System.out.println("CASE " + c.getIndex() + " DEPLACEE VERS " + dir);
			int objectifX = (int) (p.getLayoutX()+xMultiplier*largeurCase);
			int objectifY = (int) (p.getLayoutX()+xMultiplier*largeurCase);
			
			this.animate(xMultiplier * largeurCase, yMultiplier * largeurCase, p, dir);
			

//			TranslateTransition anim = new TranslateTransition(Duration.millis(200), p);
//
//			anim.setByX(xMultiplier * largeurCase);
//			anim.setByY(yMultiplier * largeurCase);
//			System.out.println("dpx " + xMultiplier * largeurCase);
//			System.out.println("dpy " + yMultiplier * largeurCase);
//			System.out.println("node: " + p.getId());
//
//			anim.play();
//
//			anim.setOnFinished((event) -> {
//				deplacerCase(dir);
//
//			});
			//Y'a aucune raison apparente que ça ne marche pas mais ça marche pas

		}

	}

	/**
	 * 
	 * ça marche pas non plus
	 * 
	 * @param x
	 * @param y
	 * @param l
	 * @param dir
	 */
	private void animate(int x, int y, Label l, EDeplacement dir) {
		
		Task<Void> t = new Task<>(){
			@Override
			public Void call() throws Exception{
				int dpx = 0;
				int dpy = 0;
				int ogx = (int) l.getLayoutX();
				int ogy = (int) l.getLayoutY();
				while (dpx != x || dpy != y) {
					if (x < 0)
						dpx--;
					else if(x>0)
						dpx++;
					if (y < 0)
						dpy--;
					else if(y > 0)
						dpy++;
					
					final int innerDpx = dpx;
					final int innerDpy = dpy;
					System.out.println("innerDpx"+innerDpx+" innerdpy"+innerDpy);
					System.out.println("x "+x+"  y "+y);
					Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            l.relocate(ogx+innerDpx, ogy+innerDpy);
//                            l.setTranslateX(x);
//                            l.setTranslateY(y);
                            l.setVisible(true);    
                        }
                    }
                    );
					Thread.sleep(1);
					
				}
				deplacerCase(dir);
				return null;
			}
		};
		
		Thread th = new Thread(t);
		th.setDaemon(false);
		th.run();
		
	}

}
