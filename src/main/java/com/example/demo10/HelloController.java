package com.example.demo10;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label newpartie;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void newpartie() {
        newpartie.setText("newpartie");
    }
    @FXML
    void handleButtonClick(ActionEvent event) {
        // Your code to handle the button click event goes here
        System.out.println("Button clicked!");
    }
}