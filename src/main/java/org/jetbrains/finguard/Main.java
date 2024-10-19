package org.jetbrains.finguard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML file for Add Account page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jetbrains/finguard/add-account.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 800, 600); // Set scene size to match your design

            // Optionally add BootstrapFX stylesheet (ensure it's properly added to your project dependencies)
            // scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

            primaryStage.setTitle("FinGuard - Personal Budgeting");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
