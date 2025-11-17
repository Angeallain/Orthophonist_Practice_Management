package com.club.Model;

import java.util.Date;
import java.sql.Time;

public class Main {
    public static void main(String[] args) {
        // Création d'un orthophoniste
        Orthophoniste orthophoniste = new Orthophoniste("Lazib", "Malak", "58 Heuraoua Alger", "0000000000", "mm_lazib@esi.dz", "motDePasse");

        // Programmation d'un rendez-vous
        Date date = new Date(); // Date actuelle
        Time heure = new Time(System.currentTimeMillis()); // Heure actuelle
        int duree = 60; // Durée du rendez-vous en minutes
        String type = "consultation"; // Type de rendez-vous
        String nomPatient = "Mokrane";
        String prenomPatient = "Sirine";
        int agePatient = 19;

        orthophoniste.programmerRendezVous(date, heure, duree, type, nomPatient, prenomPatient, agePatient);

        // Affichage des rendez-vous programmés par l'orthophoniste
        for (RendezVous rendezVous : orthophoniste.getRendezVousList()) {
            if (rendezVous instanceof Consultation) {
                Consultation consultation = (Consultation) rendezVous;
                Patient patient = consultation.getPatient();
                System.out.println("Consultation programmée pour : " + patient.getNom() + " " + patient.getPrenom() + " (Age: " + patient.getAge() + ")");
            }
        }
    }
}
