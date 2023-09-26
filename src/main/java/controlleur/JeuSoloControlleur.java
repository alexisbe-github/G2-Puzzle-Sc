package main.java.controlleur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.model.partie.PartieSolo;

public class JeuSoloControlleur implements Initializable{

	private Stage owner;
	private PartieSolo partie;
	private Scene mainScene;
	
	@FXML
	Label chrono;
	
	@FXML
	ImageView logoJoueur;
	
	@FXML
	Label pseudoJoueur;
	
	@FXML
	Button boutonUndo;
	
	@FXML
	Label nbCoups;
	
	@FXML
	GridPane grille; 
	
	@FXML
	Button boutonPause;
	
	public JeuSoloControlleur(Stage stage, PartieSolo partie) throws IOException {
		this.owner = stage;
		this.partie = partie;
		partie.lancerPartie(ImageIO.read(new File("src/test/resources/testimg.jpg")), 4);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boutonPause.setFocusTraversable(false);
		boutonUndo.setFocusTraversable(false);
		
		this.initGridPane();
		this.initJoueur();
		
	}
	
	
	
	/**
	 * Initialise la grille et les images à l'intérieur. 
	 */
	private void initGridPane() {
		//Définition de la taille d'une case
		double largeurCase = owner.getWidth()/this.partie.getPuzzle().getTaille()*0.5;
		//Initialisation des contraintes des cases (tailles colomnes et lignes)
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPrefWidth(largeurCase);
		cc.setMinWidth(largeurCase);
		cc.setMaxWidth(largeurCase);
		cc.setHalignment(HPos.CENTER);
		RowConstraints rc = new RowConstraints();
		rc.setPrefHeight(largeurCase);
		rc.setMinHeight(largeurCase);
		rc.setMaxHeight(largeurCase);
		//Appliquation des contraintes
		for(int i = 0;i<partie.getPuzzle().getTaille();i++) {
			this.grille.getColumnConstraints().add(cc);
			this.grille.getRowConstraints().add(rc);
		}
		this.updateImages();
	}
	
	private void updateImages() {
		//Définition de la taille d'une case
		double largeurCase = owner.getWidth()/this.partie.getPuzzle().getTaille()*0.5;
		Image image;
		//Ajout de l'image correspondant à chaque case de la grille.
		for(int i = 0;i<partie.getPuzzle().getTaille();i++) {
			for(int j = 0;j<partie.getPuzzle().getTaille();j++) {
				image = SwingFXUtils.toFXImage(this.partie.getPuzzle().getCase(j, i).getImage(), null);
				ImageView iv = new ImageView(image);
				iv.setFitWidth(largeurCase);
				iv.setFitHeight(largeurCase);
				iv.setId("case"+this.partie.getPuzzle().getCase(j, i).getIndex());
				this.grille.add(iv, j, i);
				if(this.partie.getPuzzle().getCase(j,i).getIndex()!=-1) {
					Label li = new Label(""+this.partie.getPuzzle().getCase(j, i).getIndex());
					li.setFont(new Font(18));
					li.setTextFill(Color.YELLOW);
					li.setAlignment(Pos.CENTER);
					this.grille.add(li, j, i);
				}
			}
		}
	}
	
	
	
	/*
	
	//Bug : probablement du à la méthode GetNodeByCoordinate en dessous : comment calculer la width ?
	private void resizeImages() {
		for(int i=0;i<partie.getPuzzle().getTaille();i++) {
			for(int j=0;j<partie.getPuzzle().getTaille();j++) {
				ImageView iv = (ImageView) this.getNodeByCoordinate(j, i);
				if(iv!=null) {
					int width = 100;
					iv.setFitHeight(width);//TODO Trouver un moyen d'avoir une taille cohérente
					iv.setFitWidth(width);//(si possible taille affichée de la case)
				}
			}
		}
	}
	
	//Bug : marche que sur la premiere ligne (ou pas ? peut etre resizeImages)
	private Node getNodeByCoordinate(Integer row, Integer column) {
	    for (Node node : grille.getChildren()) {
	    	if(node instanceof ImageView) {
		        if(GridPane.getColumnIndex(node) == row && GridPane.getColumnIndex(node) == column){
		            return node;
		        }
	    	}
	    }
	    return null;
	}
	*/
	
	private void initJoueur() {
		Image image = SwingFXUtils.toFXImage(this.partie.getJoueur().getImage(), null);
		this.logoJoueur.setImage(image);
		this.updateInfos();
	}
	
	private void updateInfos() {
		this.nbCoups.setText("nbCoups : "+this.partie.getPuzzle().getNbCoups());
	}
	
	public void setKeyController() {
		this.owner.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
	                case UP:
	                	System.out.println("move up");
	                	break;
	                case DOWN:
	                	System.out.println("move down");
	                	break;
	                case LEFT:
	                	System.out.println("move left");
	                	break;
	                case RIGHT:
	                	System.out.println("move right");
	                	break;
					default:
						break;
				}
			}
		});
	}
	
	
	@FXML
	private void undoButton(ActionEvent event) {
		 System.out.println("undo");//TODO not implemented
	}
	
	@FXML
	private void pauseButton(ActionEvent event) {
		System.out.println("pause");//TODO not implemented
	}
	
}
