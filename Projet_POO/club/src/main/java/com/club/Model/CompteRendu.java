package com.club.Model;

import java.util.ArrayList;
import java.util.List;

// Classe représentant un compte rendu pour une épreuve clinique
public class CompteRendu {
    private EpreuveClinique epreuve;
    private List<Integer> scores;
    private String conclusion;

    public CompteRendu(EpreuveClinique epreuve) {
        this.epreuve = epreuve;
        this.scores = new ArrayList<>();
    }

    public void ajouterScore(int score) {
        scores.add(score);
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public EpreuveClinique getEpreuve() {
        return epreuve;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public String getConclusion() {
        return conclusion;
    }
}

