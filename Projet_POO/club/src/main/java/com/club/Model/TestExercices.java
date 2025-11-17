package com.club.Model;

import java.util.ArrayList;
import java.util.List;

public class TestExercices {
    private String nom;
    private List<Exercice> exercices;

    public TestExercices(String nom) {
        this.nom = nom;
        this.exercices = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void ajouterExercice(Exercice exercice) {
        exercices.add(exercice);
    }

    public List<Exercice> getExercices() {
        return exercices;
    }
}