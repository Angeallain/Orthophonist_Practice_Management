package com.club.Model;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;


public class DossierMedical implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numeroDossier;
    private List<RendezVous> rendezVousList;
    private List<String> bilansObservation;
    private List<FicheSuivi> ficheSuivis;

    public DossierMedical(int numeroDossier) {
        this.numeroDossier = numeroDossier;
        this.rendezVousList = new ArrayList<>();
        this.bilansObservation = new ArrayList<>();
    }



    public int getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(int numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public List<RendezVous> getRendezVousList() {
        return rendezVousList;
    }

    public void ajouterRendezVous(RendezVous rendezVous) {
        this.rendezVousList.add(rendezVous);
    }

    public List<String> getBilansObservation() {
        return bilansObservation;
    }

    public void ajouterBilanObservation(String bilan) {
        this.bilansObservation.add(bilan);
    }
}
