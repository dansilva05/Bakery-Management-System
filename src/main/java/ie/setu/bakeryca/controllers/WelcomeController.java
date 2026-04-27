package ie.setu.bakeryca.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ie.setu.bakeryca.services.AppData;
import ie.setu.bakeryca.services.StoreFileManager;

import java.io.IOException;

public class WelcomeController {
    @FXML
    private void enterStore(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ie/setu/bakeryca/bakery-system.fxml")
            );
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(
                    getClass().getResource("/ie/setu/bakeryca/stylesheet.css").toExternalForm()
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // save the store before the window closes
            // I asked GPT what to use in order to get this to run before closing
            stage.setOnCloseRequest(e -> {
                try {
                    StoreFileManager.saveStore(AppData.getStore(), AppData.getSaveFile());
                } catch (Exception ex) {
                    // window closes, nothing I can do
                }
            });

            stage.setTitle("Bakery Management System");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
