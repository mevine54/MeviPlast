package fr.mevine.models;

public class Medecin extends Utilisateur {
    private String numeroAgrement;

    // Constructeur
    public Medecin(String nom, String prenom, Adresse adresse, String telephone, String email, String numeroAgrement) {
        super(nom, prenom, adresse, telephone, email);
        this.numeroAgrement = numeroAgrement;
    }

    // Getters et setters
    public String getNumeroAgrement() {
        return numeroAgrement;
    }

    public void setNumeroAgrement(String numeroAgrement) {
        this.numeroAgrement = numeroAgrement;
    }

    @Override
    public String toString() {
        return super.toString() + ", Numéro d'agrément: " + numeroAgrement;
    }
}
