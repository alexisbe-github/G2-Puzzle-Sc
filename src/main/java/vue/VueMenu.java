package main.java.vue;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VueMenu extends Stage{

	public VueMenu(Stage primary) throws IOException {
		this.initModality(Modality.NONE);
		//this.initStyle(StageStyle.DECORATED);
        
		Parent root = FXMLLoader.load(getClass().getResource("VueMenuFXML.fxml"));
		
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        
        this.setScene(scene);
        this.show();
	}
}
