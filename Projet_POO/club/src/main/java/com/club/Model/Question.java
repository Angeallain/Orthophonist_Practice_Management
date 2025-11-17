package com.club.Model;

public class Question {
    private String enonce;
    private TypeQuestion type;

    public Question(String enonce, TypeQuestion type) {
        this.enonce = enonce;
        this.type = type;
    }

    public String getEnonce() {
        return enonce;
    }

    public TypeQuestion getType() {
        return type;
    }
}
