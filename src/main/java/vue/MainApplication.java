package main.java.vue;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.controleur.MenuControleur;

public class MainApplication extends Application{
    
	public static void open() {
		launch();
	}
	
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Taquin");
        
        try {
        	VueGenerale frame = new VueGenerale(primaryStage);
        	frame.changerVue("src/main/resources/ui/fxml/MenuPrincipal.fxml", new MenuControleur(frame));
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        
    }

}
