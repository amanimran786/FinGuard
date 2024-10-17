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
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jetbrains/finguard/hello-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 400, 300);

            // Optionally add BootstrapFX stylesheet
            scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

            primaryStage.setTitle("FinGuard - Personal Budgeting");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Initialize SQLite database connection
            DatabaseHandler dbHandler = new DatabaseHandler();
            dbHandler.createNewTable(); // Example usage: create a new table when the app starts

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
