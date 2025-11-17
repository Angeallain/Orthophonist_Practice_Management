package com.club.Controller;

import com.club.Model.Patient;
import com.club.Model.RendezVous;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AcceuilController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label totalRendezVousLabel;

    @FXML
    private Label monthlyRendezVousLabel;

    @FXML
    private Label mostFrequentRdvTypeLabel;

    private List<RendezVous> rendezVousList;
    @FXML
    private PieChart patientPieChart;


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
        chargerRendezVousDepuisFichier();
        afficherStatistiques();
        chargerRendezVousDepuisFichier();
        afficherStatistiques();
        afficherStatistiquesPatients();
    }

    private void chargerRendezVousDepuisFichier() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("rendezVous.ser"))) {
            rendezVousList = (List<RendezVous>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void afficherStatistiques() {
        // Total des rendez-vous
        int totalRendezVous = rendezVousList.size();
        totalRendezVousLabel.setText("Total des rendez-vous : " + totalRendezVous);
        totalRendezVousLabel.setStyle("-fx-text-fill: white;");

        // Rendez-vous ce mois
        YearMonth currentMonth = YearMonth.now();
        long monthlyRendezVous = rendezVousList.stream()
                .filter(rdv -> {
                    java.util.Date utilDate = new java.util.Date(rdv.getDate().getTime());
                    return YearMonth.from(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).equals(currentMonth);
                })
                .count();
        monthlyRendezVousLabel.setText("Rendez-vous ce mois : " + monthlyRendezVous);
        monthlyRendezVousLabel.setStyle("-fx-text-fill: white;");

        // Types de rendez-vous les plus fréquents
        Map<String, Long> rdvTypeCount = rendezVousList.stream()
                .collect(Collectors.groupingBy(rdv -> rdv.getClass().getSimpleName(), Collectors.counting()));
        String mostFrequentRdvType = rdvTypeCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Aucun type");
        mostFrequentRdvTypeLabel.setText("Types de rendez-vous les plus fréquents : " + mostFrequentRdvType);
        mostFrequentRdvTypeLabel.setStyle("-fx-text-fill: white;");
    }


    private void afficherStatistiquesPatients() {
        List<Patient> patientsList;

        // Lire les données des patients à partir de patients.ser
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("patients.ser"))) {
            patientsList = (List<Patient>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Collecter les patients uniques
        Set<Patient> uniquePatients = patientsList.stream().collect(Collectors.toSet());

        // Compter les enfants et les adultes parmi les patients uniques
        long enfants = uniquePatients.stream()
                .filter(patient -> patient.getAge() < 18)
                .count();

        long adultes = uniquePatients.stream()
                .filter(patient -> patient.getAge() >= 18)
                .count();

        // Créer des segments de données pour le PieChart
        PieChart.Data enfantsData = new PieChart.Data("Enfants (" + enfants + ")", enfants);
        PieChart.Data adultesData = new PieChart.Data("Adultes (" + adultes + ")", adultes);

        // Ajouter les données au PieChart
        patientPieChart.setData(FXCollections.observableArrayList(enfantsData, adultesData));
        // Désactiver la légende pour une vue plus claire
        patientPieChart.setLegendVisible(false);

        // Définir les couleurs des segments pour une visualisation verdoyante et intrigante
        String enfantsColor = "#ff9999";  // Couleur pour les enfants
        String adultesColor = "#66b3ff";  // Couleur pour les adultes

        // Appliquer les styles aux labels du PieChart
        patientPieChart.lookupAll(".chart-pie-label").forEach(label -> {
            if (label instanceof Text text) {
                if (text.getText().contains("Enfants")) {
                    text.setStyle("-fx-fill: " + enfantsColor + ";");
                } else if (text.getText().contains("Adultes")) {
                    text.setStyle("-fx-fill: " + adultesColor + ";");
                }
            }
        });

        // Appliquer les styles aux segments du PieChart
        patientPieChart.getData().forEach(data -> {
            if (data.getName().contains("Enfants")) {
                data.getNode().setStyle("-fx-pie-color: " + enfantsColor + ";");
            } else if (data.getName().contains("Adultes")) {
                data.getNode().setStyle("-fx-pie-color: " + adultesColor + ";");
            }
        });
    }


}
