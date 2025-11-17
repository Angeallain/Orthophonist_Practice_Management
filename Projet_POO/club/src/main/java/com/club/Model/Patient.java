package com.club.Model;

import java.io.*;
import java.util.Date;
import java.util.List;

public class Patient implements Serializable {
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String lieuNaissance;
    private String adresse;

    private int age;
    private DossierMedical dossierMedical;

    private static final long serialVersionUID = 1L;

    public Patient(String nom, String prenom, Date dateNaissance, int age, String lieuNaissance, String adresse, DossierMedical dossierMedical) {

        this.nom=nom;
        this.prenom=prenom;
        this.adresse=adresse;
        this.lieuNaissance=lieuNaissance;
        this.dateNaissance=dateNaissance;
        this.dossierMedical=dossierMedical;
        this.age=age;

    }

    public Patient(String nom, String prenom,int age){

        this.nom=nom;
        this.prenom=prenom;
        this.age=age;
    }

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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public DossierMedical getDossierMedical() {
        return dossierMedical;
    }

    public void setDossierMedical(DossierMedical dossierMedical) {
        this.dossierMedical = dossierMedical;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    // Méthode pour sauvegarder les patients dans un fichier
    public void sauvegarderPatient(String cheminFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            oos.writeObject(this);
            System.out.println("Patient sauvegardé dans le fichier " + cheminFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Méthode pour charger les patients depuis un fichier
    public static Patient chargerPatient(String cheminFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            return (Patient) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return nom + " " + prenom + " (N° Dossier: " + dossierMedical.getNumeroDossier() + ")";
    }


    public boolean isComplete() {
        return adresse != null && lieuNaissance != null && dateNaissance != null && dossierMedical != null;
    }

    public boolean isAdult() {
        return age >= 18;
    }



}
