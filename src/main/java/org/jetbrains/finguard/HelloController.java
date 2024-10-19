package org.jetbrains.finguard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField inputField;

    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {

        // This method will be called automatically after the FXML components are loaded
        submitButton.setOnAction(event -> {

            String inputText = inputField.getText();
            welcomeLabel.setText("Welcome to FinGuard, " + inputText + "!");
        });
    }
}
