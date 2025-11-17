package com.club.Model;

public class Session {
    private static Orthophoniste orthophonisteConnecte;

    public static Orthophoniste getOrthophonisteConnecte() {
        return orthophonisteConnecte;
    }

    public static void setOrthophonisteConnecte(Orthophoniste orthophoniste) {
        orthophonisteConnecte = orthophoniste;
    }
}
