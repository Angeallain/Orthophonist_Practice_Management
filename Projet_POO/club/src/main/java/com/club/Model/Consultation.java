package com.club.Model;

import java.io.*;
import java.sql.Time;
import java.util.Date;

public class Consultation extends RendezVous implements Serializable {
    private Patient patient;
    private static final long serialVersionUID = 1L;

    // Constructeur
    public Consultation(Date date, Time heure, int duree, Patient patient) {
        super(date, heure, duree);
        this.patient = patient;
    }

    // Méthode pour générer le rapport d'observation
    @Override
    String genererRapportObservation() {
        return "Rapport d'observation pour la consultation de " + patient.getNom() + " " + patient.getPrenom();
    }

    // Getters et setters
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    // Méthode pour sauvegarder les séances de consultation dans un fichier
    public void sauvegarderConsultation(String cheminFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            oos.writeObject(this);
            System.out.println("Consultation sauvegardée dans le fichier " + cheminFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger les séances de consultation depuis un fichier
    public static Consultation chargerConsultation(String cheminFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            return (Consultation) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }



}

