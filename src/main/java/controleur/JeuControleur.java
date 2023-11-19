package main.java.controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class JeuControleur implements Initializable {

	protected Stage owner;

	protected double xClick;
	protected double yClick;

	@FXML
	protected Label chrono;
	@FXML
	protected Label victoireLabel;
	@FXML
	protected Label nbCoups;

	@FXML
	protected ImageView logoJoueur;
	@FXML
	protected Label pseudoJoueur;

	@FXML
	protected AnchorPane grille;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	protected abstract void initJoueur();

	protected abstract void updateImages();

	protected abstract void updateJeu();
	
	protected abstract void updateInfos();
	
	protected abstract void updateVictoire();
	
	protected void updateAll() {
		this.updateJeu();
		this.updateInfos();
	}

	protected abstract void setKeyController();
	
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
