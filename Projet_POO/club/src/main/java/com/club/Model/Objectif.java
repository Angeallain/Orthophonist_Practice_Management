package com.club.Model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Objectif implements Serializable{
    private String nom;
    private TypeObjectif type;
    private int note;



    public Objectif(String nom, TypeObjectif type) {
        this.nom = nom;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public TypeObjectif getType() {
        return type;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int nouvelleNote) {
        // Assurez-vous que la nouvelle note est dans la plage autorisée
        if (nouvelleNote >= 1 && nouvelleNote <= 5) {
            this.note = nouvelleNote;
        } else {
            // Affichez un message d'erreur ou lancez une exception selon vos besoins
            throw new IllegalArgumentException("La note doit être comprise entre 1 et 5.");
        }
    }


    @Override
    public String toString() {
        return nom + " - " + type;
    }



}

