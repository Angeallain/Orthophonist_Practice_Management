package com.club.Controller;

import com.club.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FicheSuiviController {

    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private ComboBox<RendezVous> rendezVousComboBox;
    @FXML
    private TableView<Objectif> ObjectifsRVTable;
    @FXML
    private TableColumn<Objectif, String> NomObjRVColumn;
    @FXML
    private TableColumn<Objectif, String> TypeObjRVColumn;
    @FXML
    private TableColumn<Objectif, Integer> noteObjRVColumn;
    @FXML
    private TextField noteTextField;
    private List<RendezVous> rendezVousList = new ArrayList<>();
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private Text nomPatient;
    @FXML
    private AnchorPane lineChartAnchor;


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
    public void initialize() {

        lineChartAnchor.setVisible(false);

        // Charger la liste des patients au démarrage de l'application
        List<Patient> patients = DataLoader.chargerPatientsDepuisFichier("patients.ser");
        patientComboBox.getItems().addAll(patients);

        // Charger la liste des rendez-vous au démarrage de l'application
        List<RendezVous> tousLesRendezVous = DataLoader.chargerRendezVousDepuisFichier("rendezVous.ser");
        rendezVousList = chargerRendezVousDepuisFichier("rendezVous.ser");

        // Lorsqu'un patient est sélectionné dans le ComboBox, charger ses rendez-vous
        patientComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String nomPatientSelectionne = newSelection.getNom();
                String prenomPatientSelectionne = newSelection.getPrenom();

                // Concaténer le nom et le prénom
                String nomComplet = nomPatientSelectionne + " " + prenomPatientSelectionne;
                // Mettre à jour le texte avec le nom et prénom du patient sélectionné
                nomPatient.setText(nomComplet);
                // Obtenir les rendez-vous du patient sélectionné
                List<RendezVous> rendezVousList = RendezVousFilter.obtenirRendezVousPatient(tousLesRendezVous, newSelection.getNom(), newSelection.getPrenom());
                rendezVousComboBox.getItems().clear();
                rendezVousComboBox.getItems().addAll(rendezVousList);


            }
        });


        // Lorsqu'un patient est sélectionné dans le ComboBox
        patientComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Obtenir les rendez-vous du patient sélectionné
                List<RendezVous> rendezVousList = RendezVousFilter.obtenirRendezVousPatient(tousLesRendezVous, newSelection.getNom(), newSelection.getPrenom());
                // Agréger les objectifs avec le même nom
                Map<String, List<Objectif>> objectifsParNom = new HashMap<>();
                for (RendezVous rendezVous : rendezVousList) {
                    if (rendezVous instanceof Suivi) {
                        List<Objectif> objectifs = ((Suivi) rendezVous).getObjectifs();
                        for (Objectif objectif : objectifs) {
                            objectifsParNom.computeIfAbsent(objectif.getNom(), k -> new ArrayList<>()).add(objectif);
                        }
                    }
                }
                // Peupler le LineChart avec les données d'évolution des objectifs
                lineChart.getData().clear();
                for (Map.Entry<String, List<Objectif>> entry : objectifsParNom.entrySet()) {
                    XYChart.Series<Number, Number> series = new XYChart.Series<>();
                    series.setName(entry.getKey());
                    int rvOrder = 1; // Initialiser le compteur d'ordre des rendez-vous

                    for (Objectif objectif : entry.getValue()) {
                        // Rechercher le rendez-vous contenant l'objectif
                        for (RendezVous rendezVous : rendezVousList) {
                            if (rendezVous instanceof Suivi && ((Suivi) rendezVous).getObjectifs().contains(objectif)) {
                                // Nous avons trouvé le rendez-vous contenant cet objectif
                                // Ajouter les données de l'ordre du rendez-vous et de la note au LineChart
                                series.getData().add(new XYChart.Data<>(rvOrder, objectif.getNote()));
                                break; // Sortir de la boucle une fois que nous avons trouvé le rendez-vous
                            }
                        }
                        rvOrder++; // Incrémenter le compteur d'ordre des rendez-vous
                    }

                    lineChart.getData().add(series);
                }
            }
        });

        // Configurer l'affichage des rendez-vous dans le ComboBox
        rendezVousComboBox.setCellFactory(param -> new ListCell<RendezVous>() {
            @Override
            protected void updateItem(RendezVous item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Rendez-vous " + (getIndex() + 1));
                }
            }
        });

        rendezVousComboBox.setConverter(new StringConverter<RendezVous>() {
            @Override
            public String toString(RendezVous object) {
                return object == null ? "" : "Rendez-vous " + (rendezVousComboBox.getItems().indexOf(object) + 1);
            }

            @Override
            public RendezVous fromString(String string) {
                return null; // Non nécessaire pour ce cas
            }
        });

        // Lorsqu'un rendez-vous est sélectionné dans le ComboBox, charger ses objectifs
        rendezVousComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Charger les objectifs du rendez-vous sélectionné
                if (newSelection instanceof Suivi) {
                    List<Objectif> objectifs = ((Suivi) newSelection).getObjectifs();
                    // Ajouter une déclaration de débogage pour vérifier les objectifs chargés
                    System.out.println("Objectifs chargés: " + objectifs);
                    ObservableList<Objectif> objectifsObservableList = FXCollections.observableArrayList(objectifs);
                    ObjectifsRVTable.setItems(objectifsObservableList);
                } else {
                    ObjectifsRVTable.getItems().clear(); // Effacer les objectifs si le rendez-vous n'est pas un Suivi
                }
            }
        });


        // Configurer les colonnes de la TableView
        NomObjRVColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TypeObjRVColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        noteObjRVColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
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


    // Méthode pour modifier la note de l'objectif sélectionné
    public void modifierNote() {
        Objectif selectedObjectif = ObjectifsRVTable.getSelectionModel().getSelectedItem();
        if (selectedObjectif != null) {
            try {
                int newNote = Integer.parseInt(noteTextField.getText());
                if (newNote >= 1 && newNote <= 5) {
                    selectedObjectif.setNote(newNote);
                    sauvegarderObjectifsDansFichier();
                    ObjectifsRVTable.refresh(); // Refresh the TableView to show the updated note
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
        // Ajouter une classe CSS personnalisée à la boîte de dialogue d'erreur
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        dialogPane.getStyleClass().add("my-alert");
        alert.showAndWait();
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



    @FXML
    private void masquerStat(ActionEvent event) {
        lineChartAnchor.setVisible(false);
    }

    @FXML
    private void demasquerStat(ActionEvent event) {
        lineChartAnchor.setVisible(true);
    }



}
