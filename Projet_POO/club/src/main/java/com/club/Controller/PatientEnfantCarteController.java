package com.club.Controller;
import com.club.Model.Enfant;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import com.club.Model.Patient;


public class PatientEnfantCarteController {

    @FXML
    private Text nomText;
    @FXML
    private Text prenomText;
    @FXML
    private Text dateNaissanceText;
    @FXML
    private Text lieuNaissanceText;
    @FXML
    private Text adresseText;
    @FXML
    private Text classeEtudesText;
    @FXML
    private Text telephoneParent1Text;
    @FXML
    private Text telephoneParent2Text;

    public void setPatient(Patient patient) {
        if (patient instanceof Enfant) {
            Enfant enfantPatient = (Enfant) patient;
            nomText.setText(enfantPatient.getNom());
            prenomText.setText(enfantPatient.getPrenom());
            dateNaissanceText.setText(enfantPatient.getDateNaissance().toString());
            lieuNaissanceText.setText(enfantPatient.getLieuNaissance());
            adresseText.setText(enfantPatient.getAdresse());
            classeEtudesText.setText(enfantPatient.getClasseEtudes());
            telephoneParent1Text.setText(enfantPatient.getNumerosTelephoneParents()[0]);
            telephoneParent2Text.setText(enfantPatient.getNumerosTelephoneParents()[1]);
        }
    }
}
