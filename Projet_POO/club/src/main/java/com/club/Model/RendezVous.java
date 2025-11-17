package com.club.Model;

import java.io.*;
import java.sql.Time;
import java.util.Date;

public abstract class RendezVous implements Serializable {
    private Patient patient;
    private Date date;
    private Time heure;
    private int duree;
    private String observation;
    private static final long serialVersionUID = 1L;

    public RendezVous(Date date, Time heure, int duree) {
        this.date = date;
        this.duree = duree;
        this.heure = heure;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Méthode pour vérifier si un rendez-vous existe déjà à la même date, heure et durée
    public boolean rendezVousExiste(Date autreDate, Time autreHeure, int autreDuree) {
        return date.equals(autreDate) && heure.equals(autreHeure) && duree == autreDuree;
    }

    abstract String genererRapportObservation();

    // Méthode pour sauvegarder les rendez-vous dans un fichier
    public void sauvegarderRendezVous(String cheminFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            oos.writeObject(this);
            System.out.println("Rendez-vous sauvegardé dans le fichier " + cheminFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger les rendez-vous depuis un fichier
    public static RendezVous chargerRendezVous(String cheminFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            return (RendezVous) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Ajout d'une observation
    public void ajouterObservation(String observation) {
        if (observation != null && !observation.trim().isEmpty()) {
            this.observation = observation;
        } else {
            throw new IllegalArgumentException("L'observation ne peut pas être vide.");
        }
    }

    // Modification d'une observation
    public void modifierObservation(String observation) {
        ajouterObservation(observation);
    }

    public boolean hasObservation() {
        return observation != null && !observation.isEmpty();
    }

}
