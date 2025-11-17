package com.club.Controller;

import com.club.Model.Gestion;
import com.club.Model.Orthophoniste;
import com.club.Model.Session;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

public class ConnexionController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Gestion gestion;

    public ConnexionController() {
        gestion = new Gestion();
    }

    //go to connexion page
    public void switchToScene1(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("connexion-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField nomUtilisateurField;

    @FXML
    private PasswordField motDePasseField;

    // Méthode pour gérer le clic sur le bouton de connexion
    @FXML
    private void handleConnexion(ActionEvent event) throws IOException {
        String nomUtilisateur = nomUtilisateurField.getText();
        String motDePasse = motDePasseField.getText();

        // Appel de la méthode seConnecter
        if (gestion.seConnecter(nomUtilisateur, motDePasse)) {
            root = FXMLLoader.load(getClass().getResource("Acceuil.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 700);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } else {
            // Si aucun orthophoniste correspondant n'est trouvé, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect !");

            // Ajouter une classe CSS personnalisée à la boîte de dialogue d'erreur
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            dialogPane.getStyleClass().add("my-alert");

            alert.showAndWait();
        }
    }
}
