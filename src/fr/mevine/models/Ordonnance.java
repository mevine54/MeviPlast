package fr.mevine.models;

import java.util.List;

public class Ordonnance {
    private String date;
    private Medecin medecin;
    private Client patient;
    private List<Medicament> medicaments;
    private Specialiste specialiste;

    // Constructeur
    public Ordonnance(String date, Medecin medecin, Client patient, List<Medicament> medicaments, Specialiste specialiste) {
        this.date = date;
        this.medecin = medecin;
        this.patient = patient;
        this.medicaments = medicaments;
        this.specialiste = specialiste;
    }

    // Getters et setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Client getPatient() {
        return patient;
    }

    public void setPatient(Client patient) {
        this.patient = patient;
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(List<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    public Specialiste getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(Specialiste specialiste) {
        this.specialiste = specialiste;
    }

    @Override
    public String toString() {
        return "Ordonnance du " + date + " pour le patient: " + patient.getNom() + " par le Dr " + medecin.getNom();
    }
}
