package com.club.Model;

import java.io.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Atelier extends RendezVous implements Serializable {
    private String thematique;
    private List<Patient> participants;

    public Atelier(Date date, Time heure, int duree, String thematique, List<Patient> participants) {
        super(date, heure, duree);
        this.thematique = thematique;
        this.participants = participants;
    }

    public String getThematique() {
        return thematique;
    }

    public List<Patient> getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "Atelier{" +
                "thematique='" + thematique + '\'' +
                ", participants=" + participants +
                '}';
    }

    // Méthode pour générer le rapport d'observation
    @Override
    String genererRapportObservation() {
        return "Rapport d'observation pour l'atelier sur " + thematique;
    }



}
