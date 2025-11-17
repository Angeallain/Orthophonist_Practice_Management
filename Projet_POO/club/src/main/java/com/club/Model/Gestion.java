package com.club.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Gestion {

    private List<Orthophoniste> orthophonistes;

    public Gestion() {
        orthophonistes = chargerOrthophonistesDepuisFichier("orthophonistes.ser");
        if (orthophonistes == null) {
            orthophonistes = new ArrayList<>();
            initialiserOrthophonistes();
        }
    }

    // Méthode pour initialiser les orthophonistes
    private void initialiserOrthophonistes() {
        orthophonistes.add(new Orthophoniste("Lazib", "Malak", "adresseMalak", "0000000000", "mm_lazib@esi.dz", "Malak18"));
        sauvegarderOrthophonistesDansFichier("orthophonistes.ser");
    }

    // Méthode pour ajouter un orthophoniste à la liste
    public void ajouterOrthophoniste(Orthophoniste orthophoniste) {
        orthophonistes.add(orthophoniste);
        sauvegarderOrthophonistesDansFichier("orthophonistes.ser");
    }

    // Méthode pour sauvegarder la liste des orthophonistes dans un fichier
    public void sauvegarderOrthophonistesDansFichier(String cheminFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            oos.writeObject(orthophonistes);
            System.out.println("Liste des orthophonistes sauvegardée dans le fichier " + cheminFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger la liste des orthophonistes depuis un fichier
    public List<Orthophoniste> chargerOrthophonistesDepuisFichier(String cheminFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            return (List<Orthophoniste>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour se connecter
    public boolean seConnecter(String nomUtilisateur, String motDePasse) {
        for (Orthophoniste orthophoniste : orthophonistes) {
            if (orthophoniste.getNom().equals(nomUtilisateur) && orthophoniste.getMotDePasse().equals(motDePasse)) {
                Session.setOrthophonisteConnecte(orthophoniste);
                System.out.println("Connexion réussie pour l'orthophoniste " + orthophoniste.getNom() + orthophoniste.getPrenom());
                return true;
            }
        }
        return false;
    }
}
