package com.club.Controller;

import com.club.Model.Gestion;
import com.club.Model.Orthophoniste;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherOrthophonistesController implements Initializable {

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

    public void switchToCompte1(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Compte.fxml"));
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
    private GridPane grid;

    private List<Orthophoniste> cartes;
    private List<OrthophonisteCarte> cardControllers = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cartes=recupererOrthophonistes();


        int columns = 1;
        int row = 1;

        try {

            for (int i = 0; i < cartes.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/club/Controller/OrthophonisteCarte.fxml")); // Assurez-vous que le chemin est correct
                AnchorPane box = fxmlLoader.load();
                OrthophonisteCarte orthophonisteCarte = fxmlLoader.getController();
                orthophonisteCarte.setData(cartes.get(i));
                cardControllers.add(orthophonisteCarte);

                if (columns == 3) {
                    columns = 1;
                    ++row;
                }

                grid.add(box, columns++, row);
                GridPane.setMargin(box, new Insets(7));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static final String FICHIER_ORTHOPHONISTES = "orthophonistes.ser";

    // Méthode pour récupérer les orthophonistes à partir du fichier sérialisé
    public List<Orthophoniste> recupererOrthophonistes() {
        List<Orthophoniste> orthophonistes = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHIER_ORTHOPHONISTES))) {
            // Lecture de la liste des orthophonistes depuis le fichier sérialisé
            List<Orthophoniste> loadedOrthophonistes = (List<Orthophoniste>) ois.readObject();

            // Itérer sur les orthophonistes chargés pour extraire les informations
            for (Orthophoniste orthophoniste : loadedOrthophonistes) {
                Orthophoniste carte = new Orthophoniste();
                carte.setNom(orthophoniste.getNom());
                carte.setPrenom(orthophoniste.getPrenom());
                carte.setAdresse(orthophoniste.getAdresse());
                carte.setTelephone(orthophoniste.getTelephone());
                carte.setEmail(orthophoniste.getEmail());
                carte.setMotDePasse(orthophoniste.getMotDePasse());
                orthophonistes.add(carte);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return orthophonistes;
    }

}
