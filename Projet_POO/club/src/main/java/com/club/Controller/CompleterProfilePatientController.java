/*package com.club.Controller;

import com.club.Model.Orthophoniste;
import com.club.Model.Patient;
import com.club.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.util.Date;
import java.util.*;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class CompleterProfilePatientController {

    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private VBox patientDetailsBox;
    @FXML
    private Button completerButton;
    @FXML
    private TextField diplomeField;
    @FXML
    private TextField professionField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField classeEtudesField;
    @FXML
    private TextField parentTelephone1Field;
    @FXML
    private TextField parentTelephone2Field;
    @FXML
    private DatePicker dateNaissancePicker; // This variable should match the fx:id in your FXML file

    @FXML
    private TextField lieuNaissanceField;

    @FXML
    private TextField adresseField;

    /*@FXML
    private void afficherDetailsPatients() {
        Patient patient = patientComboBox.getSelectionModel().getSelectedItem();
        if (patient != null) {
            // Display common details (e.g., nom, prénom, numéro de dossier)
            System.out.println("Nom: " + patient.getNom());
            System.out.println("Prénom: " + patient.getPrenom());
            System.out.println("Date de naissance: " + patient.getDateNaissance());
            System.out.println("Lieu de naissance: " + patient.getLieuNaissance());
            System.out.println("Adresse: " + patient.getAdresse());

            if ( patient.getAge() >=18 ) {
                // Display adult-specific details
                Adulte adulte = (Adulte) patient;
                System.out.println("Profession: " + adulte.getProfession());
                System.out.println("Diplôme: " + adulte.getDiplome());
                System.out.println("Numéro de téléphone: " + adulte.getNumeroTelephone());
            } else {
                // Display child-specific details
                Enfant enfant = (Enfant) patient;
                System.out.println("Classe d'études: " + enfant.getClasseEtudes());
                System.out.println("Téléphone parent 1: " + enfant.getNumerosTelephoneParents()[0]);
                System.out.println("Téléphone parent 2: " + enfant.getNumerosTelephoneParents()[1]);
            }
        }
    }

*/
/******************

    @FXML
    private void afficherDetailsPatients() {
        Patient patient = patientComboBox.getSelectionModel().getSelectedItem();
        if (patient != null) {
            try {
                // Determine the type of patient and load the appropriate FXML
                FXMLLoader loader;
                if (patient.getAge() >= 18) {
                    loader = new FXMLLoader(getClass().getResource("PatientAdulteCarte.fxml"));
                } else {
                    loader = new FXMLLoader(getClass().getResource("PatientEnfantCarte.fxml"));
                }

                // Load the FXML and get the root node
                AnchorPane patientDetailPane = loader.load();

                // Get the controller associated with the FXML and set the patient data
                if (patient.getAge() >= 18) {
                    PatientAdultCarteController controller = loader.getController();
                    controller.setPatient((Adulte) patient);
                } else {
                    PatientEnfantCarteController controller = loader.getController();
                    controller.setPatient((Enfant) patient);
                }

                // Display the loaded pane in your UI
                patientDetailsBox.getChildren().clear();
                patientDetailsBox.getChildren().add(patientDetailPane);
                patientDetailsBox.setVisible(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Orthophoniste orthophoniste;

    private List<Patient> patients;

    @FXML
    public void initialize() {
        // Initialize the orthophoniste
        completerButton.setOnAction(event -> {
            System.out.println("Complete button clicked"); // Add this line for debugging
            completerPatient(); // Call the completerPatient() method
        });
        orthophoniste = new Orthophoniste("Nom", "Prenom", "Adresse", "Téléphone", "Email", "MotDePasse");

        // Load patients
        patients = PatientLoader.loadPatients("patients.ser");
        patientComboBox.getItems().addAll(patients);

        // Set button action
        completerButton.setOnAction(event -> completerPatient());

        // Set ComboBox action
        patientComboBox.setOnAction(event -> afficherDetailsPatient());
    }

    private void afficherDetailsPatient() {
        Patient patient = patientComboBox.getSelectionModel().getSelectedItem();
        if (patient != null) {
            patientDetailsBox.getChildren().clear();

            boolean isComplete = patient.isComplete();
            boolean isAdult = patient.isAdult();

            if (isComplete) {
                try {
                    FXMLLoader loader;
                    if (isAdult) {
                        loader = new FXMLLoader(getClass().getResource("PatientAdulteCarte.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("PatientEnfantCarte.fxml"));
                    }
                    AnchorPane patientDetailPane = loader.load();

                    if (patient.getAge() >= 18) {
                        PatientAdultCarteController controller = loader.getController();
                        controller.setPatient(patient);
                    } else {
                        PatientEnfantCarteController controller = loader.getController();
                        controller.setPatient(patient);
                    }

                    patientDetailsBox.getChildren().add(patientDetailPane);
                    patientDetailsBox.setVisible(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                dateNaissancePicker.setVisible(true);
                lieuNaissanceField.setVisible(true);
                adresseField.setVisible(true);
                completerButton.setVisible(true);

                diplomeField.setVisible(isAdult);
                professionField.setVisible(isAdult);
                telephoneField.setVisible(isAdult);

                classeEtudesField.setVisible(!isAdult);
                parentTelephone1Field.setVisible(!isAdult);
                parentTelephone2Field.setVisible(!isAdult);

                patientDetailsBox.getChildren().addAll(
                        dateNaissancePicker, lieuNaissanceField, adresseField,
                        diplomeField, professionField, telephoneField,
                        classeEtudesField, parentTelephone1Field, parentTelephone2Field,
                        completerButton
                );
                patientDetailsBox.setVisible(true);
            }
        }
    }

    private void completerPatient() {
        Patient patient = patientComboBox.getSelectionModel().getSelectedItem();
        if (patient != null) {
            LocalDate localDate = dateNaissancePicker.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            patient.setDateNaissance(date);
            patient.setLieuNaissance(lieuNaissanceField.getText());
            patient.setAdresse(adresseField.getText());

            try {
                if (patient.getAge() >= 18) {
                    Adulte adultePatient = new Adulte(patient.getNom(), patient.getPrenom(), patient.getDateNaissance(), patient.getAge(), patient.getLieuNaissance(), patient.getAdresse(), patient.getDossierMedical(), "", "", "");
                    patients.set(patients.indexOf(patient), adultePatient);

                    adultePatient.setDiplome(diplomeField.getText());
                    adultePatient.setProfession(professionField.getText());
                    adultePatient.setNumeroTelephone(telephoneField.getText());
                } else {
                    Enfant enfantPatient = new Enfant(patient.getNom(), patient.getPrenom(), patient.getDateNaissance(), patient.getAge(), patient.getLieuNaissance(), patient.getAdresse(), patient.getDossierMedical(), "", new String[]{"", ""});
                    patients.set(patients.indexOf(patient), enfantPatient);

                    enfantPatient.setClasseEtudes(classeEtudesField.getText());
                    enfantPatient.setNumerosTelephoneParents(new String[]{parentTelephone1Field.getText(), parentTelephone2Field.getText()});
                }

                // Log before saving patients
                System.out.println("Saving patients...");
                PatientLoader.savePatients(patients, "patients.ser");
                System.out.println("Patients saved successfully");

                // Refresh the patient details
                afficherDetailsPatient();

                // Show success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Les informations du patient ont été complétées et sauvegardées avec succès.");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();

                // Show error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la sauvegarde des informations du patient.");
                alert.showAndWait();
            }
        }
    }


}*/
package com.club.Controller;

import com.club.Model.Orthophoniste;
import com.club.Model.Patient;
import com.club.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.util.Date;
import java.util.*;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class CompleterProfilePatientController {


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
    private ComboBox<Patient> patientComboBox;
    @FXML
    private VBox patientDetailsBox;
    @FXML
    private Button completerButton;
    @FXML
    private TextField diplomeField;
    @FXML
    private TextField professionField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField classeEtudesField;
    @FXML
    private TextField parentTelephone1Field;
    @FXML
    private TextField parentTelephone2Field;
    @FXML
    private DatePicker dateNaissancePicker;
    @FXML
    private TextField lieuNaissanceField;
    @FXML
    private TextField adresseField;



    private Orthophoniste orthophoniste;
    private List<Patient> patients;

    @FXML
    public void initialize() {
        orthophoniste = new Orthophoniste("Nom", "Prenom", "Adresse", "Téléphone", "Email", "MotDePasse");
        patients = PatientLoader.loadPatients("patients.ser");
        patientComboBox.getItems().addAll(patients);
        completerButton.setOnAction(event -> completerPatient());
        patientComboBox.setOnAction(event -> afficherDetailsPatient());
    }
    @FXML
    private void afficherDetailsPatient() {
        Patient patient = patientComboBox.getSelectionModel().getSelectedItem();
        if (patient != null) {
            patientDetailsBox.getChildren().clear();

            boolean isComplete = patient.isComplete();
            boolean isAdult = patient.isAdult();

            if (isComplete) {
                try {
                    FXMLLoader loader;
                    if (isAdult) {
                        loader = new FXMLLoader(getClass().getResource("PatientAdulteCarte.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("PatientEnfantCarte.fxml"));
                    }
                    AnchorPane patientDetailPane = loader.load();

                    if (patient.getAge() >= 18) {
                        PatientAdultCarteController controller = loader.getController();
                        controller.setPatient(patient);
                    } else {
                        PatientEnfantCarteController controller = loader.getController();
                        controller.setPatient(patient);
                    }

                    patientDetailsBox.getChildren().add(patientDetailPane);
                    patientDetailsBox.setVisible(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                dateNaissancePicker.setVisible(true);
                lieuNaissanceField.setVisible(true);
                adresseField.setVisible(true);
                completerButton.setVisible(true);

                diplomeField.setVisible(isAdult);
                professionField.setVisible(isAdult);
                telephoneField.setVisible(isAdult);

                classeEtudesField.setVisible(!isAdult);
                parentTelephone1Field.setVisible(!isAdult);
                parentTelephone2Field.setVisible(!isAdult);

                patientDetailsBox.getChildren().addAll(
                        dateNaissancePicker, lieuNaissanceField, adresseField,
                        diplomeField, professionField, telephoneField,
                        classeEtudesField, parentTelephone1Field, parentTelephone2Field,
                        completerButton
                );
                patientDetailsBox.setVisible(true);
            }
        }
    }

    private void completerPatient() {
        Patient patient = patientComboBox.getSelectionModel().getSelectedItem();
        if (patient != null) {
            LocalDate localDate = dateNaissancePicker.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            patient.setDateNaissance(date);
            patient.setLieuNaissance(lieuNaissanceField.getText());
            patient.setAdresse(adresseField.getText());

            try {
                if (patient.getAge() >= 18) {
                    Adulte adultePatient = new Adulte(patient.getNom(), patient.getPrenom(), patient.getDateNaissance(), patient.getAge(), patient.getLieuNaissance(), patient.getAdresse(), patient.getDossierMedical(), "", "", "");
                    adultePatient.setDiplome(diplomeField.getText());
                    adultePatient.setProfession(professionField.getText());
                    adultePatient.setNumeroTelephone(telephoneField.getText());
                    patients.set(patients.indexOf(patient), adultePatient);
                } else {
                    Enfant enfantPatient = new Enfant(patient.getNom(), patient.getPrenom(), patient.getDateNaissance(), patient.getAge(), patient.getLieuNaissance(), patient.getAdresse(), patient.getDossierMedical(), "", new String[]{"", ""});
                    enfantPatient.setClasseEtudes(classeEtudesField.getText());
                    enfantPatient.setNumerosTelephoneParents(new String[]{parentTelephone1Field.getText(), parentTelephone2Field.getText()});
                    patients.set(patients.indexOf(patient), enfantPatient);
                }

                // Save the updated list of patients
                PatientLoader.savePatients(patients, "patients.ser");

                // Reload the list of patients from the saved file
                patients = PatientLoader.loadPatients("patients.ser");

                // Update the ComboBox with the updated list of patients
                patientComboBox.getItems().setAll(patients);

                // Select the previously selected patient to trigger the display of details
                patientComboBox.getSelectionModel().select(patient);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Les informations du patient ont été complétées et sauvegardées avec succès.");
                // Ajouter une classe CSS personnalisée à la boîte de dialogue d'erreur
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                dialogPane.getStyleClass().add("my-alert");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la sauvegarde des informations du patient.");
                // Ajouter une classe CSS personnalisée à la boîte de dialogue d'erreur
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                dialogPane.getStyleClass().add("my-alert");
                alert.showAndWait();
            }
        }
    }
}