package com.club.Controller;

import com.club.Model.Orthophoniste;
import com.club.Model.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompteController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;



    public void switchToAjouterCompte(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AjouterCompte.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAfficherOrthophonistes(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AfficherOrthophonistes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToCompte(ActionEvent event) throws IOException {
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
    private Text nom;
    @FXML
    private Text prenom;
    @FXML
    private Text adresse;
    @FXML
    private Text telephone;
    @FXML
    private Text email;
    @FXML
    private Text motPasse;

    // Attribut pour stocker les détails de l'orthophoniste connecté
    private Orthophoniste orthophonisteConnecte;

    // Méthode pour initialiser le contrôleur
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Récupérer les détails de l'orthophoniste connecté depuis la session
        orthophonisteConnecte = Session.getOrthophonisteConnecte();

        // Mettre à jour les champs de texte avec les informations de l'orthophoniste connecté
        nom.setText(orthophonisteConnecte.getNom());
        prenom.setText(orthophonisteConnecte.getPrenom());
        adresse.setText(orthophonisteConnecte.getAdresse());
        telephone.setText(orthophonisteConnecte.getTelephone());
        email.setText(orthophonisteConnecte.getEmail());
        motPasse.setText(orthophonisteConnecte.getMotDePasse());
    }



}
