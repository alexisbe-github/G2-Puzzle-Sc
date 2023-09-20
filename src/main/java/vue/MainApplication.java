package main.java.vue;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application{
    
	public static void open() {
		launch();
	}
	
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Taquin");
        
        try {
        	VueMenu frame = new VueMenu(primaryStage);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        
    }

}
