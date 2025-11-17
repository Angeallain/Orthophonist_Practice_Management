package com.club.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class OurApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        //CompleterProfilePatient
        Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());

        // Charger l'icône à partir des ressources
        Image icon = new Image(getClass().getResourceAsStream("pics/icon.png"));

        // Définir l'icône de l'application
        stage.getIcons().add(icon);

        stage.setTitle("Gestion d'un cabinet d'orthophonie");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}