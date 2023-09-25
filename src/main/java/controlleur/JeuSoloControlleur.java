package main.java.controlleur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import main.java.model.partie.PartieSolo;

public class JeuSoloControlleur implements Initializable{

	Stage owner;
	PartieSolo partie;
	
	@FXML
	Label chrono;
	
	@FXML
	ImageView logoJoueur;
	
	@FXML
	Label pseudoJoueur;
	
	@FXML
	Button boutonUndo;
	
	@FXML
	Label nBCoups;
	
	@FXML
	GridPane grille; 
	
	public JeuSoloControlleur(Stage stage, PartieSolo partie) throws IOException {
		this.owner = stage;
		this.partie = partie;
		partie.lancerPartie(ImageIO.read(new File("src/test/resources/testimg.jpg")), 4);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initGridPane();
		this.initJoueur();
	}
	
	
	
	
	private void initGridPane() {
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(100/partie.getPuzzle().getTaille());
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(100/partie.getPuzzle().getTaille());
		
		for(int i = 0;i<partie.getPuzzle().getTaille();i++) {
			this.grille.getColumnConstraints().add(cc);
			this.grille.getRowConstraints().add(rc);
		}
		Image image;
		for(int i = 0;i<partie.getPuzzle().getTaille();i++) {
			for(int j = 0;j<partie.getPuzzle().getTaille();j++) {
				image = SwingFXUtils.toFXImage(this.partie.getPuzzle().getCase(j, i).getImage(), null);
				ImageView iv = new ImageView(image);
				iv.setFitWidth(50);
				iv.setFitHeight(50);
				iv.setId("case"+this.partie.getPuzzle().getCase(j, i).getIndex());
				this.grille.add(iv, j, i);
				this.grille.add(new Label(""+this.partie.getPuzzle().getCase(j, i).getIndex()), j, i);
			}
		}
		this.resizeImages();
		this.nBCoups.setText("case:");
	}
	
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
	
	private void initJoueur() {
		Image image = SwingFXUtils.toFXImage(this.partie.getJoueur().getImage(), null);
		this.logoJoueur.setImage(image);
	}
	
	
	
	@FXML
	private void lancerPartie(ActionEvent event) {
		 System.exit(0); // Exit with status code 0 (normal exit)
	}
	
}
