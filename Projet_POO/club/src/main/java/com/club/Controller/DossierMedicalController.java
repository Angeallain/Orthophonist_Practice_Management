package com.club.Controller;

import com.club.Model.Patient;
import com.club.Model.RendezVous;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DossierMedicalController {


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
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        List<Patient> patients = DataLoader.chargerPatientsDepuisFichier("patients.ser");
        List<RendezVous> tousLesRendezVous = DataLoader.chargerRendezVousDepuisFichier("rendezVous.ser");

        VBox vBox = new VBox();
        for (Patient patient : patients) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DMcarte.fxml"));
                AnchorPane card = loader.load();

                // Remplir les informations du patient
                Text nomText = (Text) card.lookup("#nomText");
                Text prenomText = (Text) card.lookup("#prenomText");
                Text ageText = (Text) card.lookup("#ageText");
                ListView<String> rendezVousListView = (ListView<String>) card.lookup("#rendezVousListView");

                nomText.setText(patient.getNom());
                prenomText.setText(patient.getPrenom());
                ageText.setText(String.valueOf(patient.getAge()));

                // Ajouter les rendez-vous à la ListView
                List<RendezVous> rendezVousList = FicheSuiviController.RendezVousFilter.obtenirRendezVousPatient(tousLesRendezVous, patient.getNom(), patient.getPrenom());
                for (RendezVous rendezVous : rendezVousList) {
                    String rvInfo = String.format("%s - %s ", rendezVous.getDate(), rendezVous.getHeure());
                    rendezVousListView.getItems().add(rvInfo);
                }

                vBox.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        scrollPane.setContent(vBox);
    }


    // classe interne
    static class DataLoader {
        public static List<RendezVous> chargerRendezVousDepuisFichier(String filename) {
            List<RendezVous> rendezVousList = new ArrayList<>();
            try (FileInputStream fis = new FileInputStream(filename);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                rendezVousList = (List<RendezVous>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return rendezVousList;
        }

        public static List<Patient> chargerPatientsDepuisFichier(String filename) {
            List<Patient> patientsList = new ArrayList<>();
            try (FileInputStream fis = new FileInputStream(filename);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                patientsList = (List<Patient>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return patientsList;
        }
    }

    //classe interne
    static class RendezVousFilter {
        public static List<RendezVous> obtenirRendezVousPatient(List<RendezVous> rendezVousList, String nom, String prenom) {
            return rendezVousList.stream()
                    .filter(rdv -> rdv.getPatient().getNom().equals(nom) && rdv.getPatient().getPrenom().equals(prenom))
                    .collect(Collectors.toList());
        }
    }




}
