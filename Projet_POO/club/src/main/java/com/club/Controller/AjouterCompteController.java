package com.club.Controller;

import com.club.Model.Gestion;
import com.club.Model.Orthophoniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class AjouterCompteController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToCompte(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Compte.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCompte1(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Compte.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAgenda(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Agenda.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPatients(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CompleterProfilePatient.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToFicheSuivi(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FicheSuivi.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAcceuil(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Acceuil.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToConnexion(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("connexion-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDM(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DossierMedical.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField adresse;

    @FXML
    private TextField telephone;

    @FXML
    private TextField email;

    @FXML
    private TextField motPasse;

    private Gestion gestion;

    public AjouterCompteController() {
        gestion = new Gestion();
    }

    @FXML
    void ajouterOrthophoniste(ActionEvent event) {
        String nomText = nom.getText();
        String prenomText = prenom.getText();
        String adresseText = adresse.getText();
        String telephoneText = telephone.getText();
        String emailText = email.getText();
        String motDePasseText = motPasse.getText();

        // Créer un nouvel orthophoniste avec les informations saisies
        Orthophoniste nouvelOrthophoniste = new Orthophoniste(nomText, prenomText, adresseText, telephoneText, emailText, motDePasseText);

        // Ajouter l'orthophoniste à la liste des orthophonistes existante
        gestion.ajouterOrthophoniste(nouvelOrthophoniste);

        // Afficher un message de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout d'orthophoniste");
        alert.setHeaderText(null);
        alert.setContentText("L'orthophoniste a été ajouté avec succès !");

        // Ajouter une classe CSS personnalisée à la boîte de dialogue d'erreur
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("my-alert");

        alert.showAndWait();

        // Effacer les champs de texte après l'ajout
        nom.clear();
        prenom.clear();
        adresse.clear();
        telephone.clear();
        email.clear();
        motPasse.clear();
    }


}
