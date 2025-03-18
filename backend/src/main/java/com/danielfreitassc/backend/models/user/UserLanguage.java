package com.danielfreitassc.backend.models.user;

public enum UserLanguage {
    PORTUGUESE("Português"),
    ENGLISH("Ingles");
    private String language;

    UserLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
