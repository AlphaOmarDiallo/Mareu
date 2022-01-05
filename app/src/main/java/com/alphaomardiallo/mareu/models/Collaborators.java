package com.alphaomardiallo.mareu.models;

public enum Collaborators {
    JOHNDOE("John Doe", "john.doe@lamzone.com"),
    JANEDOE("Jane Doe", "jane.doe@lamzone.com"),
    ALPHADIALLO("Alpha Diallo", "alpha.diallo@lamzone.com"),
    THIAGOSILVA("Thiago Silva", "thiago.silva@lamzone.com"),
    MARCOVERRATTI("Marco Verratti", "marco.verratti@lamzone.com"),
    KYLLIANMBAPPE("KYLLIANMBAPPE", "kyllian.mbappe@lamzone.com"),
    COLINDAGBA("Colin Dagba", "colin.dagba@lamzone.com"),
    SAKINAKARCHAOUI("Sakina Karchaoui", "sakina.karchaoui@lamzone.com"),
    GRACEGEYORO("Grace Geyoro", "grace.geyoro@lamzone.com"),
    AMINATADIALLO("Aminata Diallo", "aminata.diallo@lamzone.com"),
    MARIEANTOINETTEKATOTO("Marie-Antoinette Katoto", "marie-antoinette.katoto@lamzone.com"),
    JORDYNHUITEMA("Jordyn Huitema", "jordyn.huitema@lamzone.com");

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

    public static int collaboratorsLength() {
        return Collaborators.values().length;
    }

    public static int collaboratorsNumber(String[] args) {
        return Collaborators.values().length;
    }

}
