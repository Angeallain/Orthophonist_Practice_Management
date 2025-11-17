package com.club.Model;

import java.util.Date;

public class Enfant extends Patient {
    private String classeEtudes;
    private String[] numerosTelephoneParents = new String[2];

    // Constructeur avec super() pour appeler le constructeur de la classe parent Patient
    public Enfant(String nom, String prenom, Date dateNaissance, int age, String lieuNaissance,
                  String adresse, DossierMedical dossierMedical, String classeEtudes, String[] numerosTelephoneParents) {
        super(nom, prenom, dateNaissance, age, lieuNaissance, adresse, dossierMedical);
        this.classeEtudes = classeEtudes;
        this.numerosTelephoneParents = numerosTelephoneParents;
    }


    public String getClasseEtudes() {
        return classeEtudes;
    }

    public void setClasseEtudes(String classeEtudes) {
        this.classeEtudes = classeEtudes;
    }

    public String[] getNumerosTelephoneParents() {
        return numerosTelephoneParents;
    }

    public void setNumerosTelephoneParents(String[] numerosTelephoneParents) {
        this.numerosTelephoneParents = numerosTelephoneParents;
    }

    public boolean isComplete() {
        return super.isComplete() && classeEtudes != null && numerosTelephoneParents[0] != null && numerosTelephoneParents[1] != null;
    }

}
