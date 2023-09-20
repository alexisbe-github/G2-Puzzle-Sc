package main.java.vue.test;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class acceuil extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./resources/acceuil.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("FXML Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lance l'application JavaFX
        launch(args);
    }
}
