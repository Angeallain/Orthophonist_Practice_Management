package com.club.Model;

public class Exercice {
    private String consigne;
    private String materielRequis;

    public Exercice(String consigne, String materielRequis) {
        this.consigne = consigne;
        this.materielRequis = materielRequis;
    }

    public String getConsigne() {
        return consigne;
    }

    public String getMaterielRequis() {
        return materielRequis;
    }
}
