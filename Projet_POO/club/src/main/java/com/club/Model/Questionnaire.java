package com.club.Model;

import java.util.ArrayList;
import java.util.List;

public class Questionnaire {
    private String nom;
    private List<Question> questions;

    public Questionnaire(String nom) {
        this.nom = nom;
        this.questions = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void ajouterQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}