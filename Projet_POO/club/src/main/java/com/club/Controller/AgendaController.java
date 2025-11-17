package com.club.Controller;

import com.club.Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgendaController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<RendezVous> rendezVousTable;
    @FXML
    private TableColumn<RendezVous, Date> dateColumn;
    @FXML
    private TableColumn<RendezVous, Time> heureColumn;
    @FXML
    private TableColumn<RendezVous, String> typeColumn;
    @FXML
    private TableColumn<RendezVous, Integer> dureeColumn;
    @FXML
    private TableColumn<RendezVous, String> ObservationColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField observationTextField;

    private List<RendezVous> rendezVousList = new ArrayList<>();

    // UI elements pour Consultation
    @FXML
    private Text nomPrenomPatientConsultation;
    @FXML
    private Text age;
    @FXML
    private Text PText;
    @FXML
    private Text agePconsultationText;

    // UI elements pour Suivi
    @FXML
    private Text nomPrenomPatientSuivi;
    @FXML
    private Text modeSuivi;
    @FXML
    private Text modeRVsuiviText;
    // Déclaration des champs pour TableView et TableColumn des objectifs
    @FXML
    private TableView<Objectif> ObjectifsTable;
    @FXML
    private TableColumn<Objectif, String> NomObjColumn;
    @FXML
    private TableColumn<Objectif, String> TypeObjColumn;
    @FXML
    private TableColumn<Objectif, Integer> noteObjColumn;
    @FXML
    private TextField noteObjTextField;
    @FXML
    private Button buttonNote;

    private Objectif objectifSelectionne;


    // UI elements pour Atelier
    @FXML
    private ListView<String> listePatientsAtelier;
    @FXML
    private Text themeAtelier;
    @FXML
    private Text thematiqueText;
    @FXML
    private Text patientAteliertext;


    @FXML
    public void initialize() {

        setFieldsVisibility(false);

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        dureeColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));
        typeColumn.setCellValueFactory(cellData -> {
            RendezVous rv = cellData.getValue();
            return new SimpleStringProperty(rv.getClass().getSimpleName());
        });

        ObservationColumn.setCellValueFactory(cellData -> {
            RendezVous rv = cellData.getValue();
            String observationStatus = rv.hasObservation() ? "faite" : "non faite";
            return new SimpleStringProperty(observationStatus);
        });

        // Charger les rendez-vous depuis un fichier
        rendezVousList = chargerRendezVousDepuisFichier("rendezVous.ser");
        rendezVousTable.getItems().addAll(rendezVousList);

        NomObjColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TypeObjColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        noteObjColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        // Rendre la colonne de note éditable avec des champs de texte
        noteObjColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        noteObjColumn.setOnEditCommit(event -> {
            Objectif objectif = event.getRowValue();
            objectif.setNote(event.getNewValue());
        });

        // Lorsqu'une ligne est sélectionnée dans la TableView, afficher la note dans le champ de texte
        ObjectifsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                objectifSelectionne = newSelection;
                noteObjTextField.setText(String.valueOf(newSelection.getNote()));
            }
        });

        // Action lorsque le bouton "Modifier note" est cliqué
        buttonNote.setOnAction(event -> modifierNote());

    }

    @FXML
    private void detailsRendezVous(ActionEvent event) {
        RendezVous selectedRendezVous = rendezVousTable.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            if (selectedRendezVous instanceof Consultation) {
                setFieldsVisibilityConsultation(true);
                setFieldsVisibilitySuivi(false);
                setFieldsVisibilityAtelier(false);
                setFieldsVisibility1(true);
                Consultation consultation = (Consultation) selectedRendezVous;
                nomPrenomPatientConsultation.setText(consultation.getPatient().getNom() + " " + consultation.getPatient().getPrenom());
                age.setText(String.valueOf(consultation.getPatient().getAge()));
                clearSuiviFields();
                clearAtelierFields();
            } else if (selectedRendezVous instanceof Suivi) {
                setFieldsVisibilitySuivi(true);
                setFieldsVisibilityConsultation(false);
                setFieldsVisibilityAtelier(false);
                setFieldsVisibility1(true);
                Suivi suivi = (Suivi) selectedRendezVous;
                nomPrenomPatientSuivi.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                modeSuivi.setText(suivi.getModeRdv().toString());

                // Récupérer les objectifs associés au Suivi sélectionné
                List<Objectif> objectifs = suivi.getObjectifs();

                // Vérifier si le Suivi a des objectifs
                if (!objectifs.isEmpty()) {
                    // Afficher les objectifs dans la TableView
                    ObservableList<Objectif> objectifsObservableList = FXCollections.observableArrayList(objectifs);
                    ObjectifsTable.setItems(objectifsObservableList);
                } else {
                    // Si le Suivi n'a pas d'objectifs, vous pouvez effacer la TableView ou afficher un message
                    ObjectifsTable.getItems().clear(); // Efface les anciennes données de la TableView
                    // Ou affichez un message dans la TableView
                    ObjectifsTable.setPlaceholder(new Label("Aucun objectif pour ce rendez-vous"));
                }
                clearConsultationFields();
                clearAtelierFields();
            } else if (selectedRendezVous instanceof Atelier) {
                setFieldsVisibilityAtelier(true);
                setFieldsVisibilityConsultation(false);
                setFieldsVisibilitySuivi(false);
                setFieldsVisibility1(false);
                Atelier atelier = (Atelier) selectedRendezVous;
                listePatientsAtelier.getItems().clear();
                for (Patient patient : atelier.getParticipants()) {
                    listePatientsAtelier.getItems().add(patient.getNom() + " " + patient.getPrenom());
                }
                themeAtelier.setText(atelier.getThematique());
                clearConsultationFields();
                clearSuiviFields();
            }
        }
    }

    private void clearConsultationFields() {
        nomPrenomPatientConsultation.setText("");
        age.setText("");
    }

    private void clearSuiviFields() {
        nomPrenomPatientSuivi.setText("");
        modeSuivi.setText("");
    }

    private void clearAtelierFields() {
        listePatientsAtelier.getItems().clear();
        themeAtelier.setText("");
    }



    @FXML
    private void supprimerRendezVous(ActionEvent event) {
        RendezVous selectedRendezVous = rendezVousTable.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            rendezVousList.remove(selectedRendezVous);
            rendezVousTable.getItems().remove(selectedRendezVous);
            sauvegarderRendezVousDansFichier(rendezVousList, "rendezVous.ser");
        }
    }



    @FXML
    private void afficherRendezVous(ActionEvent event) {
        Date selectedDate = java.sql.Date.valueOf(datePicker.getValue());
        rendezVousTable.getItems().clear();
        for (RendezVous rv : rendezVousList) {
            if (rv.getDate().equals(selectedDate)) {
                rendezVousTable.getItems().add(rv);
            }
        }
    }

    @FXML
    private void ajouterObservation() {
        RendezVous selectedRendezVous = rendezVousTable.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            try {
                selectedRendezVous.ajouterObservation(observationTextField.getText());
                sauvegarderRendezVousDansFichier(rendezVousList, "rendezVous.ser");
                showAlert("Succès", "Observation ajoutée avec succès.", Alert.AlertType.INFORMATION);
            } catch (IllegalArgumentException e) {
                showAlert("Erreur", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un rendez-vous.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void modifierObservation() {
        RendezVous selectedRendezVous = rendezVousTable.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            try {
                selectedRendezVous.modifierObservation(observationTextField.getText());
                sauvegarderRendezVousDansFichier(rendezVousList, "rendezVous.ser");
                showAlert("Succès", "Observation modifiée avec succès.", Alert.AlertType.INFORMATION);
            } catch (IllegalArgumentException e) {
                showAlert("Erreur", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un rendez-vous.", Alert.AlertType.ERROR);
        }
    }

    // Méthode pour sauvegarder les rendez-vous dans un fichier
    private void sauvegarderRendezVousDansFichier(List<RendezVous> rendezVousList, String cheminFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            oos.writeObject(rendezVousList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger les rendez-vous depuis un fichier
    private List<RendezVous> chargerRendezVousDepuisFichier(String filename) {
        List<RendezVous> rendezVousList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            rendezVousList = (List<RendezVous>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Si le fichier n'existe pas, retourner une liste vide
            System.out.println("Fichier non trouvé, création d'un nouveau fichier.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rendezVousList;
    }

    public void switchToAjouterRV(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AjouterRV.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCompte(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Compte.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);

        // Ajouter une classe CSS personnalisée à la boîte de dialogue d'erreur
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("my-alert");

        alert.showAndWait();
    }


    private void setFieldsVisibility(boolean isVisible) {
        nomPrenomPatientConsultation.setVisible(isVisible);
        age.setVisible(isVisible);
        PText.setVisible(isVisible);
        agePconsultationText.setVisible(isVisible);
        nomPrenomPatientSuivi.setVisible(isVisible);
        modeSuivi.setVisible(isVisible);
        modeRVsuiviText.setVisible(isVisible);
        listePatientsAtelier.setVisible(isVisible);
        themeAtelier.setVisible(isVisible);
        thematiqueText.setVisible(isVisible);
        patientAteliertext.setVisible(isVisible);
        noteObjTextField.setVisible(isVisible);
        buttonNote.setVisible(isVisible);
        ObjectifsTable.setVisible(isVisible);
    }

    private void setFieldsVisibilityConsultation(boolean isVisible) {
        nomPrenomPatientConsultation.setVisible(isVisible);
        age.setVisible(isVisible);
        agePconsultationText.setVisible(isVisible);
    }


    private void setFieldsVisibilitySuivi(boolean isVisible) {
        nomPrenomPatientSuivi.setVisible(isVisible);
        modeSuivi.setVisible(isVisible);
        modeRVsuiviText.setVisible(isVisible);
        noteObjTextField.setVisible(isVisible);
        buttonNote.setVisible(isVisible);
        ObjectifsTable.setVisible(isVisible);

    }

    private void setFieldsVisibilityAtelier(boolean isVisible) {
        listePatientsAtelier.setVisible(isVisible);
        themeAtelier.setVisible(isVisible);
        thematiqueText.setVisible(isVisible);
        patientAteliertext.setVisible(isVisible);
    }

    private void setFieldsVisibility1(boolean isVisible) {
        PText.setVisible(isVisible);
    }

    // Méthode pour ajouter un nouvel objectif à la liste
    public void ajouterObjectif(Objectif objectif) {
        ObservableList<Objectif> objectifsObservableList = ObjectifsTable.getItems();
        objectifsObservableList.add(objectif);
    }

    // Méthode pour modifier la note de l'objectif sélectionné
    public void modifierNote() {
        Objectif selectedObjectif = ObjectifsTable.getSelectionModel().getSelectedItem();
        if (selectedObjectif != null) {
            try {
                int newNote = Integer.parseInt(noteObjTextField.getText());
                if (newNote >= 1 && newNote <= 5) {
                    selectedObjectif.setNote(newNote);
                    sauvegarderObjectifsDansFichier();
                    ObjectifsTable.refresh(); // Refresh the TableView to show the updated note
                } else {
                    showAlert("Invalid Note", "La note doit être entre 1 et 5.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Veuillez entrer un nombre valide pour la note.");
            }
        } else {
            showAlert("No Selection", "Aucun objectif sélectionné pour modification.");
        }
    }



    private void sauvegarderObjectifsDansFichier() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rendezVous.ser"))) {
            oos.writeObject(rendezVousList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
