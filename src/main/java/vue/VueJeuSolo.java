package main.java.vue;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.controleur.JeuSoloControleur;
import main.java.model.partie.PartieSolo;

public class VueJeuSolo extends Stage{

	public VueJeuSolo(PartieSolo partie, int taille, byte[] img) throws IOException {
		this.initModality(Modality.NONE);
		FXMLLoader loader = new FXMLLoader(Paths.get("src/main/resources/ui/fxml/JeuSolo.fxml").toUri().toURL());
		this.setWidth(Screen.getPrimary().getBounds().getWidth()/2);
        this.setHeight(Screen.getPrimary().getBounds().getHeight()/2);
        this.setResizable(false);
        JeuSoloControleur controller = new JeuSoloControleur(this, partie, taille, img);
        loader.setController(controller);
        
        this.setTitle("Taquin - Jeu Solo");
        Parent root = loader.load();
        Scene scene = new Scene(root);        
        
        //scene.getStylesheets().add("src/main/resources/ui/styles/style.css");
        this.setScene(scene);
        controller.setKeyController();
        //TODO Ameliorable : loader.getController.setKeyController() -> Permettrait de gérer cette fenetre comme les autres
        this.show();
	}
	
}
