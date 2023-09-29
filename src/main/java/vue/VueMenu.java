package main.java.vue;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.controlleur.MenuControleur;
import main.java.controlleur.NouvellePartieControleur;

public class VueMenu extends Stage{

	public VueMenu(Stage primary) throws IOException {
		this.initModality(Modality.NONE);
		//this.initStyle(StageStyle.DECORATED);
        
		MenuControleur controller = new MenuControleur(this);
		FXMLLoader loader = new FXMLLoader(Paths.get("src/main/resources/ui/fxml/MenuPrincipal.fxml").toUri().toURL());
        loader.setController(controller);
        Parent root = loader.load();
		
        Scene scene = new Scene(root);
        
        //scene.getStylesheets().add("src/main/resources/ui/styles/style.css");
        
        this.setScene(scene);
        this.show();
	}
	
	public void changerVue(String fxmlPath, Initializable controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(Paths.get(fxmlPath).toUri().toURL());
        loader.setController(controller);
        Parent root = loader.load();
		
        Scene scene = new Scene(root);
        this.setScene(scene);
	}
	
}
