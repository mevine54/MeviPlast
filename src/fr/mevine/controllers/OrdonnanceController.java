package fr.mevine.controllers;

import fr.mevine.models.Client;
import fr.mevine.models.Medecin;
import fr.mevine.models.Ordonnance;

import java.util.ArrayList;
import java.util.List;

public class OrdonnanceController {
    private List<Ordonnance> ordonnances;

    // Constructeur du contrôleur d'ordonnance
    public OrdonnanceController() {
        this.ordonnances = new ArrayList<>();
    }

    // Ajouter une ordonnance
    public void ajouterOrdonnance(Ordonnance ordonnance) {
        ordonnances.add(ordonnance);
    }

    // Afficher toutes les ordonnances prescrites par un médecin
    public List<Ordonnance> afficherOrdonnancesParMedecin(Medecin medecin) {
        List<Ordonnance> result = new ArrayList<>();
        for (Ordonnance o : ordonnances) {
            if (o.getMedecin().equals(medecin)) {
                result.add(o);
            }
        }
        return result;
    }

    // Afficher toutes les ordonnances d'un patient
    public List<Ordonnance> afficherOrdonnancesParPatient(Client patient) {
        List<Ordonnance> result = new ArrayList<>();
        for (Ordonnance o : ordonnances) {
            if (o.getPatient().equals(patient)) {
                result.add(o);
            }
        }
        return result;
    }

    // Afficher toutes les ordonnances du jour
    public List<Ordonnance> afficherOrdonnancesDuJour(String date) {
        List<Ordonnance> result = new ArrayList<>();
        for (Ordonnance o : ordonnances) {
            if (o.getDate().equals(date)) {
                result.add(o);
            }
        }
        return result;
    }
}
