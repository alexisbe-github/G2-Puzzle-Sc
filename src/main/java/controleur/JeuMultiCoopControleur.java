package main.java.controleur;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

public class JeuMultiCoopControleur implements Initializable{

	private Stage owner;
	
	private Puzzle puzzle;
	private int numJoueurCourant;
	private List<Joueur> joueurs;
	
	private int numJoueur;
	private Joueur joueur;
	private boolean estEnPause = false;
	private double xClick;
	private double yClick;
	private Client client;
	
	@FXML
	VBox boxJoueurs;
	
	@FXML
	Label chrono;
	
	@FXML
	Label victoireLabel;
	
	@FXML
	ImageView logoJoueur;
	
	@FXML
	Label pseudoJoueur;
	
	@FXML
	Button boutonUndo;
	
	@FXML
	Label nbCoups;
	
	@FXML
	AnchorPane grille; 
	
	@FXML
	Button boutonPause;
	
	/**
	 * 
	 * @param stage : fenêtre dans laquelle la scene est affichée
	 * @param partie : partie jouée
	 * @throws IOException : Exception lors d'un problème de lecture de l'image
	 */
	public JeuMultiCoopControleur
	(Stage stage, int numJoueur, Joueur joueur,
			List<Joueur> joueurs, int numJoueurCourant, Puzzle puzzle, Client client) 
					throws IOException {
		this.owner = stage;
		this.joueur = joueur;
		this.numJoueur = numJoueur;
		this.client=client;
		this.numJoueurCourant=numJoueurCourant;
		this.puzzle=puzzle;
		this.joueurs = joueurs;
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boutonPause.setFocusTraversable(false);
		boutonUndo.setFocusTraversable(false);
		
		this.updateImages();
		this.initJoueur();
		
		//grille.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> this.handlePressAction(event));
		//grille.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> this.handleReleaseAction(event));
		
		this.updateJoueurs();
		
	}
	

	/**
	 * mise a jour des images affichées en fonction de la position des cases dans la grille
	 */
	private void updateImages() {
		//Définition de la taille d'une case
		double largeurCase = owner.getWidth()/this.puzzle.getTaille()*0.5;
		Image image;
		grille.getChildren().clear();
		
		for(int i=0;i<puzzle.getTaille();i++) {
			for(int j=0;j<puzzle.getTaille();j++) {
				Label l = new Label();
				if(puzzle.getCase(j, i).getIndex()!=-1)
					l.setText(""+((int)puzzle.getCase(j,i).getIndex()+1));
				l.setFont(new Font(18));
				l.setTextFill(Color.YELLOW);
				l.setPrefWidth(largeurCase);
				l.setPrefHeight(largeurCase);
				l.setAlignment(Pos.CENTER);
				l.setLayoutX(j*largeurCase);
				l.setLayoutY(i*largeurCase);
				
				l.setId("case"+puzzle.getCase(j, i).getIndex());
				
				image = new Image(new ByteArrayInputStream(puzzle.getCase(j, i).getImage()));
				
				Background bgi = new Background(new BackgroundImage(image,
				        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				          new BackgroundSize(100, 100, true, true , true, false) ));
				if(puzzle.getCase(j, i).getIndex()!=-1) l.setBackground( bgi );
				grille.getChildren().add(l);
			}
		}
	}
	
	private void updateJoueurs() {
		this.boxJoueurs.getChildren().clear();
		for(Joueur j : joueurs) {
			VBox v = new VBox(); //Box dans laquelle on affichera les infos des joueurs
			v.setAlignment(Pos.CENTER);
			v.setPrefHeight(200);
			v.setPrefWidth(100);
			ImageView i = new ImageView(); //Logo du joueur
			i.setFitHeight(60);
			i.setFitWidth(60);
			Image image = new Image(new ByteArrayInputStream(this.joueur.getImage()));
			i.setImage(image);
			Label l = new Label(j.getNom()); //Pseudo du joueur
			v.setId("box"+j.getNom());
			v.getChildren().add(i);
			v.getChildren().add(l);
			boxJoueurs.getChildren().add(v); //Ajout a la box principal
		}
	}
	
	private void updateJeu() {
		this.updateImages();
		if(this.puzzle.verifierGrille()) this.updateVictoire();
	}
	
	private void updateVictoire(){
		victoireLabel.setVisible(true);
		this.estEnPause = true;
	}
	
	private void initJoueur() {
		Image image = new Image(new ByteArrayInputStream(this.joueur.getImage()));
		this.logoJoueur.setImage(image);
		this.pseudoJoueur.setText(joueur.getNom());
		this.updateInfos();
	}
	
	private void updateInfos() {
		this.nbCoups.setText("nbCoups : "+this.puzzle.getNbCoups());
	}
	
	public void setKeyController() {
		this.owner.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(!estEnPause) {
					//dpAnim(event.getCode());
					try {
						switch (event.getCode()) {
		                case UP:
							client.lancerRequete("h");
							readStream();
		                	//partie.deplacerCase(EDeplacement.HAUT, joueur, numJoueur);
		                	break;
		                case DOWN:
		                	client.lancerRequete("b");
		                	readStream();
		                	//partie.deplacerCase(EDeplacement.BAS, joueur, numJoueur);
		                	break;
		                case LEFT:
		                	client.lancerRequete("g");
		                	readStream();
		                	//partie.deplacerCase(EDeplacement.GAUCHE, joueur, numJoueur);
		                	break;
		                case RIGHT:
		                	client.lancerRequete("d");
		                	readStream();
		                	//partie.deplacerCase(EDeplacement.DROITE, joueur, numJoueur);
		                	break;
						default:
							break;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	
	@FXML
	private void undoButton(ActionEvent event) {
		 System.out.println("undo");
		 //TODO not implemented
	}
	
	@FXML
	private void pauseButton(ActionEvent event) {
		if(!this.puzzle.verifierGrille()) {
			this.estEnPause = !estEnPause;
		}
	}

	
	public void updateAll() {
		this.updateJeu();
		this.updateInfos();
	}
	
	
	private void readStream() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(client.getSocket().getInputStream());
		puzzle = (Puzzle) ois.readObject();
		BufferedReader fluxEntrant = new BufferedReader(
		new InputStreamReader(client.getSocket().getInputStream()));
		System.out.println(fluxEntrant.readLine());
		updateAll();
	}
	
	
	
	/*

    private void handlePressAction(MouseEvent event) {
        xClick = event.getX();//translation en abscisse
        yClick = event.getY();//translation en ordonnée
    }
    
    private void handleReleaseAction(MouseEvent event) {
    	double translateX = event.getX()-xClick;
    	double translateY = event.getY()-yClick;
    	try {
    		if(!estEnPause && (Math.abs(translateY) > 10 || Math.abs(translateX) > 10)) {
        		if(Math.abs(translateX)>Math.abs(translateY)) {
            		if(translateX>0) partie.deplacerCase(EDeplacement.DROITE, joueur, numJoueur);
            			else partie.deplacerCase(EDeplacement.GAUCHE, joueur, numJoueur);
            	}else {
            		if(translateY>0) partie.deplacerCase(EDeplacement.BAS, joueur, numJoueur);
        			else partie.deplacerCase(EDeplacement.HAUT, joueur, numJoueur);
            	}
        	}
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    }
    */
	
	
	
	
    
    /* DEPLACEMENT, ANIMATION (Code un peu déstructuré, c'est normal)
    private void dpAnim(KeyCode key) {
    	double largeurCase = owner.getWidth()/this.partie.getPuzzle().getTaille()*0.5;
    	int xMultiplier = 0,yMultiplier = 0;
    	if(key==KeyCode.UP) {
    		yMultiplier=-1;
    	}else if(key==KeyCode.DOWN) {
    		yMultiplier=1;
    	}else if(key==KeyCode.RIGHT) {
    		xMultiplier=1;
    	}else if(key==KeyCode.LEFT) {
    		xMultiplier=-1;
    	}
    
    	
    	Node casedp = null;
    	
    	if(partie.getPuzzle().getXCaseVide()+xMultiplier < partie.getPuzzle().getTaille() &&
    			partie.getPuzzle().getXCaseVide()+xMultiplier>=0 &&
    			partie.getPuzzle().getYCaseVide()+yMultiplier < partie.getPuzzle().getTaille() &&
    			partie.getPuzzle().getYCaseVide()+yMultiplier>=0 || true) {
    		
    		int idCase = partie.getPuzzle().getCase(
    				partie.getPuzzle().getXCaseVide()+xMultiplier,
    				partie.getPuzzle().getYCaseVide()+yMultiplier).getIndex();
    		
    		for(int i=0;i<grille.getChildren().size();i++) {
    			String id1 = "case"+idCase;
    			String id2 = grille.getChildren().get(i).getId();
        		if(grille.getChildren().get(i).getId().equals("case"+idCase)) {
        			casedp=grille.getChildren().get(i);
        		}
        	}
    		
    		System.out.println(partie.getPuzzle());
        	
        	TranslateTransition translateTransition = new TranslateTransition();
        	translateTransition.setNode(casedp);
            translateTransition.setDuration(Duration.millis(50));
            translateTransition.setToX(largeurCase*xMultiplier);
            translateTransition.setToY(largeurCase*yMultiplier);
            translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
            	@Override
				public void handle(ActionEvent event) {
            		switch (key) {
	                case UP:
	                	partie.deplacerCase(EDeplacement.HAUT);
	                	break;
	                case DOWN:
	                	partie.deplacerCase(EDeplacement.BAS);
	                	break;
	                case LEFT:
	                	partie.deplacerCase(EDeplacement.GAUCHE);
	                	break;
	                case RIGHT:
	                	partie.deplacerCase(EDeplacement.DROITE);
	                	break;
					default:
						break;
            		}
				}
            });
            translateTransition.play();
        }
    		
    }
    */
    
}
