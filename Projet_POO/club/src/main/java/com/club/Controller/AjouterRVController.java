package com.club.Controller;

import com.club.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.time.LocalDate;

import java.io.*;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.MultipleSelectionModel;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;



public class AjouterRVController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchToCompte(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Compte.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField heureTextField;
    @FXML
    private TextField dureeTextField;
    @FXML
    private Text ajouterPtext;
    @FXML
    private Text infoRVtext;
    @FXML
    private Button ajouterButton;

    @FXML
    private ComboBox<Patient> pComb; // ComboBox pour les patients
    @FXML
    private ComboBox<ModeRV> tComb; // ComboBox pour le type de suivi
    @FXML
    private DatePicker datePicker1;
    @FXML
    private TextField heureTextField1;
    @FXML
    private TextField dureeTextField1;
    @FXML
    private Button ajouterButton1;
    @FXML
    private Text Ptext;
    @FXML
    private Text typetext;
    @FXML
    private Text infoRVtext1;
    @FXML
    private TextField nomObjectifTextField;
    @FXML
    private ComboBox<String> typeObjectifComboBox;
    @FXML
    private ListView<Objectif> objectifsListView;
    private ObservableList<Objectif> objectifs = FXCollections.observableArrayList();
    @FXML
    private VBox ajouterObjVbox;





    @FXML
    private ListView<Patient> Plist;
    @FXML
    private TextField thematique;
    @FXML
    private DatePicker datePicker2;
    @FXML
    private TextField heureTextField2;
    @FXML
    private TextField dureeTextField2;
    @FXML
    private Button ajouterButton2;
    @FXML
    private Text infoRVtext2;


    private List<RendezVous> rendezVousList = new ArrayList<>();
    private List<Patient> patientList = new ArrayList<>();
    private List<DossierMedical> dossierMedicalList = new ArrayList<>();

    @FXML
    private MultipleSelectionModel<Patient> multipleSelection;



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
    private void initialize() {
        // Charger les patients depuis le fichier
        patientList = chargerPatientsDepuisFichier("patients.ser");

        // Remplir le ComboBox des patients
        ObservableList<Patient> observablePatientList = FXCollections.observableArrayList(patientList);
        pComb.setItems(observablePatientList);

        // Remplir la ListView des patients
        Plist.setItems(observablePatientList);
        if (Plist.getSelectionModel() != null) {
            Plist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Permettre la sélection multiple
        }




        // Masquer les champs au démarrage
        setFieldsVisibility(false);
        setFieldsVisibility1(false);
        setFieldsVisibility2(false);

        // Ajouter un listener pour le changement de sélection du ComboBox
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Consultation".equals(newValue)) {
                setFieldsVisibility(true);
                setFieldsVisibility1(false);
                setFieldsVisibility2(false);
            } else if ("Suivi".equals(newValue)){
                setFieldsVisibility1(true);
                setFieldsVisibility(false);
                setFieldsVisibility2(false);
            } else if ("Atelier".equals(newValue)){
                setFieldsVisibility2(true);
                setFieldsVisibility(false);
                setFieldsVisibility1(false);
            } else {
                setFieldsVisibility(false);
                setFieldsVisibility1(false);
                setFieldsVisibility2(false);
            }
        });

        typeComboBox.setItems(FXCollections.observableArrayList("Consultation", "Suivi", "Atelier"));
        tComb.setItems(FXCollections.observableArrayList(ModeRV.values())); // Remplir le ComboBox avec les valeurs de l'enum ModeRV

        typeObjectifComboBox.setItems(FXCollections.observableArrayList("COURT_TERME", "MOYEN_TERME", "LONG_TERME"));
        objectifsListView.setItems(objectifs);


    }





    @FXML
    private void ajouterRendezVous() {
        try {
            String type = typeComboBox.getValue();
            System.out.println("Type de rendez-vous sélectionné : " + type);

            // Charger les rendez-vous existants depuis le fichier
            rendezVousList = chargerRendezVousDepuisFichier("rendezVous.ser");

            if ("Consultation".equals(type)) {
                String nom = nomTextField.getText();
                String prenom = prenomTextField.getText();
                int age = Integer.parseInt(ageTextField.getText());
                LocalDate localDate = datePicker.getValue();
                LocalTime localTime = LocalTime.parse(heureTextField.getText());
                int duree = Integer.parseInt(dureeTextField.getText());

                if (isConflict(localDate, localTime, duree)) {
                    showAlert("Conflit", "Un rendez-vous existe déjà à ce créneau horaire.", Alert.AlertType.ERROR);
                    return;
                }

                Date date = java.sql.Date.valueOf(localDate);
                Time heure = java.sql.Time.valueOf(localTime);

                if (duree <= 0) {
                    throw new IllegalArgumentException("La durée doit être supérieure à zéro.");
                }

                Patient patient = new Patient(nom, prenom, age);
                DossierMedical dossierMedical = new DossierMedical(generateNumeroDossier());
                patient.setDossierMedical(dossierMedical);

                Consultation consultation = new Consultation(date, heure, duree, patient);
                dossierMedical.ajouterRendezVous(consultation);

                rendezVousList.add(consultation);
                patientList.add(patient);
                dossierMedicalList.add(dossierMedical);

                sauvegarderRendezVousDansFichier(rendezVousList, "rendezVous.ser");
                sauvegarderPatientsDansFichier(patientList, "patients.ser");
                sauvegarderDossiersMedicauxDansFichier(dossierMedicalList, "dossiersMedicaux.ser");

                showAlert("Succès", "La consultation a été ajoutée avec succès.", Alert.AlertType.INFORMATION);
            } else if ("Suivi".equals(type)) {
                LocalDate localDate = datePicker1.getValue();
                LocalTime localTime = LocalTime.parse(heureTextField1.getText());
                int duree = Integer.parseInt(dureeTextField1.getText());

                if (isConflict(localDate, localTime, duree)) {
                    showAlert("Conflit", "Un rendez-vous existe déjà à ce créneau horaire.", Alert.AlertType.ERROR);
                    return;
                }

                Date date = java.sql.Date.valueOf(localDate);
                Time heure = java.sql.Time.valueOf(localTime);

                if (duree <= 0) {
                    throw new IllegalArgumentException("La durée doit être supérieure à zéro.");
                }

                Patient selectedPatient = pComb.getValue();
                ModeRV modeRdv = tComb.getValue();

                if (selectedPatient == null) {
                    throw new IllegalArgumentException("Veuillez sélectionner un patient.");
                }

                if (modeRdv == null) {
                    throw new IllegalArgumentException("Veuillez sélectionner un mode de rendez-vous.");
                }

                // Récupérer tous les objectifs de la ListView
                List<Objectif> allObjectifs = new ArrayList<>(objectifsListView.getItems());

                // Ajouter une sortie console pour afficher tous les objectifs de la ListView
                System.out.println("Tous les objectifs : " + allObjectifs);

                Suivi suivi = new Suivi(date, heure, duree, selectedPatient, generateNumeroDossier(), modeRdv, allObjectifs);

               // Ajouter une sortie console pour afficher les objectifs du suivi
                System.out.println("Objectifs du suivi : " + allObjectifs);

                selectedPatient.getDossierMedical().ajouterRendezVous(suivi);
                rendezVousList.add(suivi);

                sauvegarderRendezVousDansFichier(rendezVousList, "rendezVous.ser");

                showAlert("Succès", "Le suivi a été ajouté avec succès.", Alert.AlertType.INFORMATION);
            } else if ("Atelier".equals(type)) {
                String thematiqueText = thematique.getText();
                LocalDate localDate = datePicker2.getValue();
                LocalTime localTime = LocalTime.parse(heureTextField2.getText());
                int duree = Integer.parseInt(dureeTextField2.getText());

                if (isConflict(localDate, localTime, duree)) {
                    showAlert("Conflit", "Un rendez-vous existe déjà à ce créneau horaire.", Alert.AlertType.ERROR);
                    return;
                }

                Date date = java.sql.Date.valueOf(localDate);
                Time heure = java.sql.Time.valueOf(localTime);

                if (duree <= 0) {
                    throw new IllegalArgumentException("La durée doit être supérieure à zéro.");
                }

                ObservableList<Patient> selectedPatients = Plist.getSelectionModel().getSelectedItems();
                List<Patient> participants = new ArrayList<>(selectedPatients);

                Atelier atelier = new Atelier(date, heure, duree, thematiqueText, participants);

                for (Patient patient : participants) {
                    patient.getDossierMedical().ajouterRendezVous(atelier);
                }

                rendezVousList.add(atelier);

                sauvegarderRendezVousDansFichier(rendezVousList, "rendezVous.ser");

                showAlert("Succès", "L'atelier a été ajouté avec succès.", Alert.AlertType.INFORMATION);
            }

            resetFields();
        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Veuillez entrer une durée valide.", Alert.AlertType.ERROR);
        } catch (DateTimeParseException e) {
            showAlert("Erreur de format", "Veuillez entrer une heure valide au format HH:mm.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Erreur de validation", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout du rendez-vous.", Alert.AlertType.ERROR);
        }
    }

    private boolean isConflict(LocalDate date, LocalTime time, int duration) {
        for (RendezVous rv : rendezVousList) {
            LocalDate rvDate = new java.sql.Date(rv.getDate().getTime()).toLocalDate();
            LocalTime rvTime = new java.sql.Time(rv.getHeure().getTime()).toLocalTime();
            int rvDuree = rv.getDuree();

            if (rvDate.equals(date)) {

                LocalTime rvEndTime = rvTime.plusMinutes(rvDuree);
                LocalTime newEndTime = time.plusMinutes(duration);


                if ((time.isBefore(rvEndTime) && newEndTime.isAfter(rvTime)) ||
                        (time.equals(rvTime) && newEndTime.equals(rvEndTime))) {
                    return true;
                }
            }
        }
        return false;
    }



    private void resetFields() {
        typeComboBox.setValue(null);
        nomTextField.clear();
        prenomTextField.clear();
        ageTextField.clear();
        datePicker.setValue(null);
        heureTextField.clear();
        dureeTextField.clear();
        pComb.setValue(null);
        tComb.setValue(null);
        datePicker1.setValue(null);
        heureTextField1.clear();
        dureeTextField1.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Ajouter une classe CSS personnalisée à la boîte de dialogue d'erreur
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("my-alert");

        alert.showAndWait();
    }

    private void sauvegarderRendezVousDansFichier(List<RendezVous> rendezVousList, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(rendezVousList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sauvegarderPatientsDansFichier(List<Patient> patientList, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(patientList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sauvegarderDossiersMedicauxDansFichier(List<DossierMedical> dossierMedicalList, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(dossierMedicalList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int generateNumeroDossier() {
        return (int) (Math.random() * 10000);
    }

    private void setFieldsVisibility(boolean isVisible) {
        ajouterPtext.setVisible(isVisible);
        infoRVtext.setVisible(isVisible);
        ajouterButton.setVisible(isVisible);
        nomTextField.setVisible(isVisible);
        prenomTextField.setVisible(isVisible);
        ageTextField.setVisible(isVisible);
        datePicker.setVisible(isVisible);
        heureTextField.setVisible(isVisible);
        dureeTextField.setVisible(isVisible);
    }

    private void setFieldsVisibility1(boolean isVisible) {
        Ptext.setVisible(isVisible);
        infoRVtext1.setVisible(isVisible);
        ajouterButton1.setVisible(isVisible);
        typetext.setVisible(isVisible);
        tComb.setVisible(isVisible);
        pComb.setVisible(isVisible);
        datePicker1.setVisible(isVisible);
        heureTextField1.setVisible(isVisible);
        dureeTextField1.setVisible(isVisible);
        ajouterObjVbox.setVisible(isVisible);
    }

    private void setFieldsVisibility2(boolean isVisible) {
        thematique.setVisible(isVisible);
        infoRVtext2.setVisible(isVisible);
        datePicker2.setVisible(isVisible);
        heureTextField2.setVisible(isVisible);
        dureeTextField2.setVisible(isVisible);
        ajouterButton2.setVisible(isVisible);
        Plist.setVisible(isVisible);
    }

    // Méthode pour charger les patients depuis un fichier
    private List<Patient> chargerPatientsDepuisFichier(String nomFichier) {
        List<Patient> patients = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(nomFichier);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            patients = (List<Patient>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patients;
    }

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

    @FXML
    private void ajouterObjectif(ActionEvent event) {
        String nom = nomObjectifTextField.getText();
        TypeObjectif type = TypeObjectif.valueOf(typeObjectifComboBox.getValue());

        Objectif objectif = new Objectif(nom, type);

        // Ajouter l'objectif à la ListView uniquement
        objectifsListView.getItems().add(objectif);

        nomObjectifTextField.clear();
        typeObjectifComboBox.setValue(null);
    }





}