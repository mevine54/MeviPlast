package fr.mevine.models;

import fr.mevine.enums.Specialite;

public class Specialiste extends Medecin {
    private Specialite specialite;

    // Constructeur
    public Specialiste(String nom, String prenom, Adresse adresse, String telephone, String email,
                       String numeroAgrement, Specialite specialite) {
        super(nom, prenom, adresse, telephone, email, numeroAgrement);
        this.specialite = specialite;
    }

    // Getter pour la spécialité
    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return super.toString() + ", Spécialité: " + specialite;
    }
}
