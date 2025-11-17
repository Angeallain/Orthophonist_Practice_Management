package com.club.Model;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Suivi extends RendezVous implements Serializable {
    private Patient patient;
    private int numeroDossier;
    private ModeRV modeRdv;
    private List<Objectif> objectifs;

    public Suivi(Date date, Time heure, int duree, Patient patient, int numeroDossier, ModeRV modeRdv, List<Objectif> objectifs) {
        super(date, heure, duree);
        this.patient = patient;
        this.numeroDossier = numeroDossier;
        this.modeRdv = modeRdv;
        this.objectifs = new ArrayList<>(objectifs);
    }


    // Méthode pour générer le rapport d'observation
    @Override
    String genererRapportObservation() {
        return "Rapport d'observation pour le suivi de " + patient.getNom() + " " + patient.getPrenom();
    }

    // Getters et setters
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(int numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public ModeRV getModeRdv() {
        return modeRdv;
    }

    public void setModeRdv(ModeRV modeRdv) {
        this.modeRdv = modeRdv;
    }


    public List<Objectif> getObjectifs() {
        return objectifs;
    }

    public void addObjectif(Objectif objectif) {
        objectifs.add(objectif);
    }

    // Méthode pour sauvegarder les séances de suivi dans un fichier
    public void sauvegarderSuivi(String cheminFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            oos.writeObject(this);
            System.out.println("Suivi sauvegardé dans le fichier " + cheminFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger les séances de suivi depuis un fichier
    public static Suivi chargerSuivi(String cheminFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            return (Suivi) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
