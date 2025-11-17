package com.club.Model;

import java.util.Date;

public class Adulte extends Patient {
    private String diplome;
    private String profession;
    private String numeroTelephone;

    // Constructeur avec super() pour appeler le constructeur de la classe parent Patient
    public Adulte(String nom, String prenom, Date dateNaissance, int age, String lieuNaissance,
                  String adresse, DossierMedical dossierMedical, String diplome, String profession, String numeroTelephone) {
        super(nom, prenom, dateNaissance, age, lieuNaissance, adresse, dossierMedical);
        this.diplome = diplome;
        this.profession = profession;
        this.numeroTelephone = numeroTelephone;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }


    public boolean isComplete() {
        return super.isComplete() && diplome != null && profession != null && numeroTelephone != null;
    }

}