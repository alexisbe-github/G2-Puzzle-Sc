package main.java.vue;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controlleur.JeuSoloControlleur;
import main.java.controlleur.NouvellePartieControlleur;
import main.java.model.partie.PartieSolo;

public class VueJeuSolo extends Stage{

	public VueJeuSolo(PartieSolo partie) throws IOException {
		this.initModality(Modality.NONE);
		//this.initStyle(StageStyle.DECORATED);
        
		JeuSoloControlleur controller = new JeuSoloControlleur(this, partie);
		FXMLLoader loader = new FXMLLoader(Paths.get("src/main/resources/ui/fxml/JeuSolo.fxml").toUri().toURL());
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("src/main/resources/ui/styles/style.css");
        
        this.setScene(scene);
        this.show();
	}
	
	public void changerVue(String fxmlPath) throws IOException {
		NouvellePartieControlleur controller = new NouvellePartieControlleur(this);
		FXMLLoader loader = new FXMLLoader(Paths.get(fxmlPath).toUri().toURL());
        loader.setController(controller);
        Parent root = loader.load();
		
        Scene scene = new Scene(root);
        this.setScene(scene);
	}
	
}
