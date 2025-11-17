package com.club.Model;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Orthophoniste implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private String motDePasse;

    public Orthophoniste(String nom, String prenom, String adresse, String telephone, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Orthophoniste() {
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }


    // Liste des rendez-vous programmés par l'orthophoniste
    private List<RendezVous> rendezVousList = new ArrayList<>();

    public List<RendezVous> getRendezVousList() {
        return rendezVousList;
    }
    private List<Consultation> consultationsList = new ArrayList<>();

    // Méthode pour programmer un rendez-vous
    public void programmerRendezVous(Date date, Time heure, int duree, String type, String nomPatient, String prenomPatient, int agePatient) {
        // Vérifier si un rendez-vous existe déjà à la même heure
        for (RendezVous rendezVous : rendezVousList) {
            if (rendezVous.rendezVousExiste(date, heure, duree)) {
                System.out.println("Un rendez-vous existe déjà à cette heure !");
                return;
            }
        }

        // Créer le rendez-vous en fonction du type
        RendezVous nouveauRendezVous = null;
        if (type.equalsIgnoreCase("consultation")) {
            Patient patient = new Patient(nomPatient, prenomPatient, agePatient);
            nouveauRendezVous = new Consultation(date, heure, duree, patient);
            consultationsList.add((Consultation) nouveauRendezVous); // Ajouter la consultation à la liste des consultations
            System.out.println("Consultation programmée !");
        } else if (type.equalsIgnoreCase("suivi")) {

        } else if (type.equalsIgnoreCase("atelier")) {

        } else {
            System.out.println("Type de rendez-vous invalide !");
            return;
        }

        // Ajouter le rendez-vous à la liste des rendez-vous programmés
        rendezVousList.add(nouveauRendezVous);
    }



    public void ajouterPatient(Patient patient) {
        patientsList.add(patient);
    }

    // Méthodes pour charger et sauvegarder la liste des patients
    public void sauvegarderPatients(String cheminFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            oos.writeObject(patientsList);
            System.out.println("Liste de patients sauvegardée dans le fichier " + cheminFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargerPatients(String cheminFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            patientsList = (List<Patient>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour compléter les informations du patient
    public void completerInformationsPatient(Patient patient, String diplome, String profession, String telephone, String classeEtudes, String[] parentTelephones) {
        if (patient instanceof Enfant) {
            Enfant enfant = (Enfant) patient;
            enfant.setClasseEtudes(classeEtudes);
            enfant.setNumerosTelephoneParents(parentTelephones);
        } else if (patient instanceof Adulte) {
            Adulte adulte = (Adulte) patient;
            adulte.setDiplome(diplome);
            adulte.setProfession(profession);
            adulte.setNumeroTelephone(telephone);
        }
    }


    private List<Patient> patientsList = new ArrayList<>();
    public List<Patient> getPatientsList() {
        return patientsList;
    }




}

