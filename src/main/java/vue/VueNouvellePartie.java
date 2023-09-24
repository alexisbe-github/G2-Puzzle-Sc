package main.java.vue;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controlleur.NouvellePartieControlleur;

public class VueNouvellePartie extends Stage{

	public VueNouvellePartie() throws IOException {
		this.initModality(Modality.NONE);
		//this.initStyle(StageStyle.DECORATED);
        
		NouvellePartieControlleur controller = new NouvellePartieControlleur(this);
		FXMLLoader loader = new FXMLLoader(Paths.get("src/main/resources/ui/fxml/NouvellePartie.fxml").toUri().toURL());
        loader.setController(controller);
        Parent root = loader.load();
		
        Scene scene = new Scene(root);
        
        //scene.getStylesheets().add("src/main/resources/ui/styles/style.css");
        
        this.setScene(scene);
        this.show();
	}
}
