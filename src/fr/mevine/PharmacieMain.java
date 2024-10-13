package fr.mevine;

import fr.mevine.enums.CategorieMedicament;
import fr.mevine.enums.Specialite;
import fr.mevine.models.*;
import fr.mevine.views.DashboardView;

import javax.swing.*;
import java.util.Arrays;

public class PharmacieMain {

    public static void main(String[] args) {
        // Création d'objets pour tester
        Adresse adresseClient = new Adresse("12 Rue de la Santé", "75000", "Paris");
        Adresse adresseMedecin = new Adresse("34 Rue du Médecin", "75000", "Paris");
        Adresse adresseMutuelle = new Adresse("56 Rue des Assurances", "75000", "Paris");

        Mutuelle mutuelle = new Mutuelle("Mutuelle A", adresseMutuelle, "0123456789", "mutuelleA@gmail.com", "75", 80.0);
        Medecin medecin = new Medecin("Dupont", "Jean", adresseMedecin, "0123456789", "jean.dupont@medecin.com", "AG123456");
        Client client = new Client("Martin", "Pierre", adresseClient, "0678901234", "pierre.martin@gmail.com", "123456789012345", "01/01/1980", mutuelle, medecin);

        Medicament medicament1 = new Medicament("Paracétamol", CategorieMedicament.DOULEUR, 5.0, "01/01/2021", 2);
        Medicament medicament2 = new Medicament("Ibuprofène", CategorieMedicament.ANTIINFLAMMATOIRE, 7.0, "01/01/2021", 1);

        Specialiste specialiste = new Specialiste("Martin", "Claire", adresseMedecin, "0123456790", "claire.martin@medecin.com", "SP987654", Specialite.CARDIOLOGUE);

        // Créer une ordonnance
        Ordonnance ordonnance = new Ordonnance("13/10/2024", medecin, client, Arrays.asList(medicament1, medicament2), specialiste);

        // Afficher les détails
        System.out.println(client);
        System.out.println(medecin);
        System.out.println(specialiste);
        System.out.println(ordonnance);

        // *** Affichage de l'interface graphique (Swing) ***
        SwingUtilities.invokeLater(() -> {
            new DashboardView(); // Affiche l'interface graphique Swing
        });
    }
}