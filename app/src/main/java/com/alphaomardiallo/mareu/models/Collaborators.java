package com.alphaomardiallo.mareu.models;

public enum Collaborators {
    JOHNDOE("John Doe", "john.doe@lamzone.com"),
    JANEDOE("Jane Doe", "jane.doe@lamzone.com"),
    ALPHADIALLO("Alpha Diallo", "alpha.diallo@lamzone.com");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String name;
    private String email;

    Collaborators(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
