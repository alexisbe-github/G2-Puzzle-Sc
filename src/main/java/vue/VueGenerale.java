package main.java.vue;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controleur.MenuControleur;
import main.java.controleur.NouvellePartieControleur;

public class VueGenerale extends Stage{

	public VueGenerale(Stage primary) throws IOException {
		this.initModality(Modality.NONE);
	}
	
	public void changerVue(String titre, String fxmlPath, Initializable controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(Paths.get(fxmlPath).toUri().toURL());
        loader.setController(controller);
        Parent root = loader.load();
		
        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles.css");
        this.setScene(scene);
        this.setTitle("Taquin - "+titre);
        this.show();
	}
	
}
