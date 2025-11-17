package com.club.Model;

import java.util.ArrayList;
import java.util.List;

public class EpreuveClinique {
    private String nom;
    private List<ObservationClinique> observations;
    private List<Questionnaire> questionnaires;
    private List<TestExercices> testsExercices;

    public EpreuveClinique(String nom) {
        this.nom = nom;
        this.observations = new ArrayList<>();
        this.questionnaires = new ArrayList<>();
        this.testsExercices = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void ajouterObservation(ObservationClinique observation) {
        observations.add(observation);
    }

    public void ajouterQuestionnaire(Questionnaire questionnaire) {
        questionnaires.add(questionnaire);
    }

    public void ajouterTestExercices(TestExercices testExercices) {
        testsExercices.add(testExercices);
    }

    public List<ObservationClinique> getObservations() {
        return observations;
    }

    public List<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    public List<TestExercices> getTestsExercices() {
        return testsExercices;
    }
}

