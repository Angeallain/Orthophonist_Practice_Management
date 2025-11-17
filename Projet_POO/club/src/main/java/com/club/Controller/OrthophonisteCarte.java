package com.club.Controller;

import com.club.Model.Orthophoniste;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.InputStream;

public class OrthophonisteCarte {

    @FXML
    private Text adresse;

    @FXML
    private Text email;

    @FXML
    private Text motPasse;

    @FXML
    private Text nom;

    @FXML
    private Text prenom;

    @FXML
    private Text tel;

    private Orthophoniste carte;
    private AnchorPane cardPane;

    // Ajout de setData pour initialiser cardPane
    public void setData(Orthophoniste carte) {

        this.carte=carte;

        // Initialisation de cardPane
        cardPane = new AnchorPane();

        try {


            nom.setText(carte.getNom());
            prenom.setText(carte.getPrenom());
            adresse.setText(carte.getAdresse());
            tel.setText(carte.getTelephone());
            email.setText(carte.getEmail());
            motPasse.setText(carte.getMotDePasse());
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }




}
