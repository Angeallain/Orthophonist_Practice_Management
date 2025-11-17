package com.club.Model;

import java.util.ArrayList;
import java.util.List;

public class FicheSuivi {
    private List<Objectif> objectifs;

    public FicheSuivi() {
        this.objectifs = new ArrayList<>();
    }

    public void ajouterObjectif(Objectif objectif) {
        objectifs.add(objectif);
    }

    public List<Objectif> getObjectifs() {
        return objectifs;
    }
}

