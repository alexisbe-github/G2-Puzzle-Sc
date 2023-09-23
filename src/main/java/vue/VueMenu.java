package main.java.vue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VueMenu extends Stage{

	public VueMenu(Stage primary) throws IOException {
		this.initModality(Modality.NONE);
		//this.initStyle(StageStyle.DECORATED);
        
		Parent root = FXMLLoader.load(Paths.get("src/main/resources/ui/fxml/MenuPrincipal.fxml").toUri().toURL());
		
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        
        this.setScene(scene);
        this.show();
	}
}
