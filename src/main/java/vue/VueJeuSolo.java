package main.java.vue;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.controlleur.JeuSoloControleur;
import main.java.controlleur.NouvellePartieControleur;
import main.java.model.partie.PartieSolo;

public class VueJeuSolo extends Stage{

	public VueJeuSolo(PartieSolo partie) throws IOException {
		this.initModality(Modality.NONE);
		FXMLLoader loader = new FXMLLoader(Paths.get("src/main/resources/ui/fxml/JeuSolo.fxml").toUri().toURL());
		
		this.setWidth(Screen.getPrimary().getBounds().getWidth()/2);
        this.setHeight(Screen.getPrimary().getBounds().getHeight()/2);
        this.setResizable(false);
        JeuSoloControleur controller = new JeuSoloControleur(this, partie);
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);        
        
        //scene.getStylesheets().add("src/main/resources/ui/styles/style.css");
        this.setScene(scene);
        controller.setKeyController();
        this.show();
	}
	
	public void changerVue(String fxmlPath) throws IOException {
		NouvellePartieControleur controller = new NouvellePartieControleur(this);
		FXMLLoader loader = new FXMLLoader(Paths.get(fxmlPath).toUri().toURL());
        loader.setController(controller);
        Parent root = loader.load();
		
        Scene scene = new Scene(root);
        this.setScene(scene);
	}
	
}
