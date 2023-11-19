package main.java.controleur;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.model.Puzzle;
import main.java.model.client.Client;
import main.java.model.joueur.Joueur;

public class JeuMultiCoopControleur extends JeuMultiControleur implements Initializable {

	private int numJoueurCourant;

	boolean flagThreadEnd = false;
	private int numJoueur;

	/**
	 * 
	 * @param stage  : fenêtre dans laquelle la scene est affichée
	 * @param partie : partie jouée
	 * @throws IOException : Exception lors d'un problème de lecture de l'image
	 */
	public JeuMultiCoopControleur(Stage stage, int numJoueur, Joueur joueur, List<Joueur> joueurs, int numJoueurCourant,
			Puzzle puzzle, Client client) throws IOException {
		this.owner = stage;
		this.joueur = joueur;
		this.numJoueur = numJoueur;
		this.client = client;

		this.numJoueurCourant = numJoueurCourant;
		this.puzzle = puzzle;
		this.joueurs = joueurs;

	}

	@Override
	protected void readStream() throws IOException, InterruptedException {

		System.out.println("super");

		this.client.lancerRequete("p");

		while (!this.puzzle.verifierGrille()) {
			Platform.runLater(() -> {

				try {
					ObjectInputStream ois;
					ois = new ObjectInputStream(client.getSocket().getInputStream());
					Object oisObj = ois.readObject();

					if (oisObj instanceof List) {
						List<Object> tab = (List<Object>) oisObj;
						if (tab.get(0).equals("p")) {
							this.puzzle = (Puzzle) tab.get(2);
							this.numJoueurCourant = (int) tab.get(1);
							updateAll();
						}
						client.lancerRequete("p");
					}

				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			});
			Thread.sleep(500);
		}

	}

	@Override
	protected  void lancerThread() {
		new Thread(() -> {
			try {
				readStream();
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}

	/*
	 * 
	 * private void handlePressAction(MouseEvent event) { xClick =
	 * event.getX();//translation en abscisse yClick = event.getY();//translation en
	 * ordonnée }
	 * 
	 * private void handleReleaseAction(MouseEvent event) { double translateX =
	 * event.getX()-xClick; double translateY = event.getY()-yClick; try {
	 * if(!estEnPause && (Math.abs(translateY) > 10 || Math.abs(translateX) > 10)) {
	 * if(Math.abs(translateX)>Math.abs(translateY)) { if(translateX>0)
	 * partie.deplacerCase(EDeplacement.DROITE, joueur, numJoueur); else
	 * partie.deplacerCase(EDeplacement.GAUCHE, joueur, numJoueur); }else {
	 * if(translateY>0) partie.deplacerCase(EDeplacement.BAS, joueur, numJoueur);
	 * else partie.deplacerCase(EDeplacement.HAUT, joueur, numJoueur); } }
	 * }catch(IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	/*
	 * DEPLACEMENT, ANIMATION (Code un peu déstructuré, c'est normal) private void
	 * dpAnim(KeyCode key) { double largeurCase =
	 * owner.getWidth()/this.partie.getPuzzle().getTaille()*0.5; int xMultiplier =
	 * 0,yMultiplier = 0; if(key==KeyCode.UP) { yMultiplier=-1; }else
	 * if(key==KeyCode.DOWN) { yMultiplier=1; }else if(key==KeyCode.RIGHT) {
	 * xMultiplier=1; }else if(key==KeyCode.LEFT) { xMultiplier=-1; }
	 * 
	 * 
	 * Node casedp = null;
	 * 
	 * if(partie.getPuzzle().getXCaseVide()+xMultiplier <
	 * partie.getPuzzle().getTaille() &&
	 * partie.getPuzzle().getXCaseVide()+xMultiplier>=0 &&
	 * partie.getPuzzle().getYCaseVide()+yMultiplier <
	 * partie.getPuzzle().getTaille() &&
	 * partie.getPuzzle().getYCaseVide()+yMultiplier>=0 || true) {
	 * 
	 * int idCase = partie.getPuzzle().getCase(
	 * partie.getPuzzle().getXCaseVide()+xMultiplier,
	 * partie.getPuzzle().getYCaseVide()+yMultiplier).getIndex();
	 * 
	 * for(int i=0;i<grille.getChildren().size();i++) { String id1 = "case"+idCase;
	 * String id2 = grille.getChildren().get(i).getId();
	 * if(grille.getChildren().get(i).getId().equals("case"+idCase)) {
	 * casedp=grille.getChildren().get(i); } }
	 * 
	 * System.out.println(partie.getPuzzle());
	 * 
	 * TranslateTransition translateTransition = new TranslateTransition();
	 * translateTransition.setNode(casedp);
	 * translateTransition.setDuration(Duration.millis(50));
	 * translateTransition.setToX(largeurCase*xMultiplier);
	 * translateTransition.setToY(largeurCase*yMultiplier);
	 * translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) { switch (key) { case UP:
	 * partie.deplacerCase(EDeplacement.HAUT); break; case DOWN:
	 * partie.deplacerCase(EDeplacement.BAS); break; case LEFT:
	 * partie.deplacerCase(EDeplacement.GAUCHE); break; case RIGHT:
	 * partie.deplacerCase(EDeplacement.DROITE); break; default: break; } } });
	 * translateTransition.play(); }
	 * 
	 * }
	 */

}
