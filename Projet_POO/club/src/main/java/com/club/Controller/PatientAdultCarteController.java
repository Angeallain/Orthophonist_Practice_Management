package com.club.Controller;
import com.club.Model.Adulte;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import com.club.Model.Patient;

public class PatientAdultCarteController {
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
    private Text diplomeText;
    @FXML
    private Text professionText;
    @FXML
    private Text telephoneText;

    public void setPatient(Patient patient) {
        if (patient instanceof Adulte) {
            Adulte adultePatient = (Adulte) patient;
            nomText.setText(adultePatient.getNom());
            prenomText.setText(adultePatient.getPrenom());
            dateNaissanceText.setText(adultePatient.getDateNaissance().toString());
            lieuNaissanceText.setText(adultePatient.getLieuNaissance());
            adresseText.setText(adultePatient.getAdresse());
            diplomeText.setText(adultePatient.getDiplome());
            professionText.setText(adultePatient.getProfession());
            telephoneText.setText(adultePatient.getNumeroTelephone());
        }
    }

}
