package fr.mevine.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DashboardView extends JFrame {

    private JPanel panelCentral;
    private DefaultTableModel tableModel; // Modèle de table pour l'historique
    private ArrayList<Object[]> achatsHistorique = new ArrayList<>(); // Liste pour stocker les achats

    public DashboardView() {
        // Paramètres de base de la fenêtre
        setTitle("Pharmacie Sparadrap - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Disposition principale
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Panneau central
        panelCentral = new JPanel(new CardLayout());
        add(panelCentral, BorderLayout.CENTER);

        // Page d'accueil au démarrage
        revenirAccueil();

        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color vertClair = new Color(85, 170, 85);
                Color vertFonce = new Color(34, 139, 34);
                GradientPaint gradient = new GradientPaint(0, 0, vertFonce, 0, getHeight(), vertClair);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(true);

        JButton btnAchat = createVisibleGradientButton("Effectuer un achat");
        JButton btnHistorique = createVisibleGradientButton("Consulter l'historique des achats");
        JButton btnMedecin = createVisibleGradientButton("Consulter un médecin / spécialiste");
        JButton btnClient = createVisibleGradientButton("Consulter un client");
        JButton btnQuitter = createVisibleGradientButton("Quitter l'application");

        sidebar.add(btnAchat);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnHistorique);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnMedecin);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnClient);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnQuitter);

        btnAchat.addActionListener(e -> afficherAchatPanel());
        btnHistorique.addActionListener(e -> afficherHistoriquePanel());
        btnMedecin.addActionListener(e -> afficherMedecinPanel());
        btnClient.addActionListener(e -> afficherClientPanel());
        btnQuitter.addActionListener(e -> System.exit(0));

        return sidebar;
    }

    private JButton createVisibleGradientButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color vertClair = new Color(144, 238, 144);
                Color vertFonce = new Color(34, 139, 34);
                GradientPaint gradient = new GradientPaint(0, 0, vertClair, getWidth(), getHeight(), vertFonce);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(250, 50));
        button.setMaximumSize(new Dimension(250, 50));
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        return button;
    }

    // Page d'achat restaurée avec tout le contenu
    private void afficherAchatPanel() {
        panelCentral.removeAll();

        // Panneau pour l'achat
        JPanel panelAchat = new JPanel();
        panelAchat.setLayout(new BoxLayout(panelAchat, BoxLayout.Y_AXIS));
        panelAchat.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelAchat.setBackground(new Color(230, 255, 230)); // Couleur vert très clair

        // Label dynamique pour indiquer le type d'achat avec une plus grande police
        JLabel labelAchatType = new JLabel("Achat direct sélectionné");
        labelAchatType.setFont(new Font("Arial", Font.BOLD, 18));  // Augmenter la taille de la police

        // Label pour le type d'achat
        JLabel labelTypeAchat = new JLabel("Sélectionner le type d'achat:");
        String[] typesAchat = {"Achat direct", "Achat via ordonnance"};
        JComboBox<String> typeAchatCombo = new JComboBox<>(typesAchat);

        // Sélection des médicaments
        JLabel labelMedicament = new JLabel("Sélectionner un médicament:");
        String[] medicaments = {"Paracétamol", "Ibuprofène", "Aspirine"};
        JComboBox<String> medicamentCombo = new JComboBox<>(medicaments);

        // Prix unitaire et quantité
        JLabel labelPrixUnitaire = new JLabel("Prix unitaire:");
        JTextField prixUnitaireField = new JTextField("5.00", 10);
        prixUnitaireField.setEditable(false);

        JLabel labelQuantite = new JLabel("Quantité:");
        JTextField quantiteField = new JTextField("1", 10);

        // Prix total
        JLabel labelPrixTotal = new JLabel("Prix total:");
        JTextField prixTotalField = new JTextField("5.00", 10);
        prixTotalField.setEditable(false);

        // Ajouter un écouteur pour mettre à jour le prix total en fonction de la quantité
        quantiteField.addActionListener(e -> {
            double prixUnitaire = Double.parseDouble(prixUnitaireField.getText());
            int quantite = Integer.parseInt(quantiteField.getText());
            double prixTotal = prixUnitaire * quantite;
            prixTotalField.setText(String.format("%.2f", prixTotal));
        });

        // Liste des médicaments ajoutés
        JLabel labelMedicamentsAjoutes = new JLabel("Médicaments ajoutés:");
        JTextArea medicamentsAjoutesArea = new JTextArea(5, 20);
        medicamentsAjoutesArea.setEditable(false);

        // Bouton pour ajouter le médicament
        JButton btnAjouterMedicament = new JButton("Ajouter médicament");
        ArrayList<String> listeMedicamentsAjoutes = new ArrayList<>();
        ArrayList<Object[]> achatsValides = new ArrayList<>();

        btnAjouterMedicament.addActionListener(e -> {
            String selectedMedicament = (String) medicamentCombo.getSelectedItem();
            String quantite = quantiteField.getText();
            double prixUnitaire = Double.parseDouble(prixUnitaireField.getText());
            double prixTotal = prixUnitaire * Integer.parseInt(quantite);
            String medicamentDetail = selectedMedicament + " (x" + quantite + ") - Prix: " + prixTotal + " €";
            listeMedicamentsAjoutes.add(medicamentDetail);

            // Mise à jour de la textArea
            medicamentsAjoutesArea.setText(String.join("\n", listeMedicamentsAjoutes));

            // Stocker les achats validés
            Object[] achat = {selectedMedicament, quantite, prixTotal};
            achatsValides.add(achat);

            // Remettre le champ quantité à 1 et mettre à jour le prix total
            quantiteField.setText("1");
            prixTotalField.setText("5.00");
        });

        // Ajout dynamique pour le choix du médecin et du client si "Achat via ordonnance"
        JLabel labelMedecin = new JLabel("Sélectionner un médecin traitant:");
        JComboBox<String> medecinCombo = new JComboBox<>(getListeMedecins());

        JLabel labelClient = new JLabel("Sélectionner un client:");
        JComboBox<String> clientCombo = new JComboBox<>(getListeClients());

        // Par défaut, on cache les champs médecin et client
        labelMedecin.setVisible(false);
        medecinCombo.setVisible(false);
        labelClient.setVisible(false);
        clientCombo.setVisible(false);

        // Gérer le changement de type d'achat
        typeAchatCombo.addActionListener(e -> {
            String typeSelectionne = (String) typeAchatCombo.getSelectedItem();
            if ("Achat via ordonnance".equals(typeSelectionne)) {
                labelAchatType.setText("Achat via ordonnance sélectionné");
                labelMedecin.setVisible(true);
                medecinCombo.setVisible(true);
                labelClient.setVisible(true);
                clientCombo.setVisible(true);
            } else {
                labelAchatType.setText("Achat direct sélectionné");
                labelMedecin.setVisible(false);
                medecinCombo.setVisible(false);
                labelClient.setVisible(false);  // Cacher le champ client pour achat direct
            }
        });

        // Bouton retour et validation
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> revenirAccueil());

        JButton btnValiderAchat = new JButton("Valider l'achat");
        btnValiderAchat.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Votre achat a bien été pris en compte !");

            // Ajouter l'achat à l'historique
            for (Object[] achat : achatsValides) {
                Object[] achatHistorique = {
                        "13/10/2024",
                        clientCombo.getSelectedItem(), // Nom du client
                        achat[0], // Médicament
                        achat[1], // Quantité
                        String.format("%.2f", achat[2]), // Prix total
                        medecinCombo.getSelectedItem()  // Nom du médecin (si achat via ordonnance)
                };
                achatsHistorique.add(achatHistorique); // Ajouter à la liste d'historique
            }

            revenirAccueil();  // Retourner à l'accueil après validation
        });

        // Ajout des composants au panel d'achat
        panelAchat.add(labelAchatType);  // Libellé indicatif pour le type d'achat
        panelAchat.add(labelTypeAchat);
        panelAchat.add(typeAchatCombo);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelMedicament);
        panelAchat.add(medicamentCombo);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelPrixUnitaire);
        panelAchat.add(prixUnitaireField);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelQuantite);
        panelAchat.add(quantiteField);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelPrixTotal);
        panelAchat.add(prixTotalField);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(btnAjouterMedicament);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelMedicamentsAjoutes);
        panelAchat.add(new JScrollPane(medicamentsAjoutesArea));
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelMedecin);  // Médecin pour achat avec ordonnance
        panelAchat.add(medecinCombo);
        panelAchat.add(labelClient);   // Client pour achat avec ordonnance
        panelAchat.add(clientCombo);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(btnValiderAchat);
        panelAchat.add(btnRetour);

        // Remplacer le contenu du panneau central
        panelCentral.add(panelAchat);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    // Page d'historique des achats avec possibilité de modifier
    private void afficherHistoriquePanel() {
        panelCentral.removeAll();

        // Panneau pour l'historique des achats
        JPanel panelHistorique = new JPanel();
        panelHistorique.setLayout(new BoxLayout(panelHistorique, BoxLayout.Y_AXIS));
        panelHistorique.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelHistorique.setBackground(new Color(230, 255, 230)); // Appliquer la même couleur que la page achat

        // Label pour le titre
        JLabel labelHistorique = new JLabel("Historique des achats");
        labelHistorique.setFont(new Font("Arial", Font.BOLD, 18));

        // Tableau pour afficher les achats avec les colonnes supplémentaires
        String[] colonnes = {"Date", "Client", "Médicament", "Quantité", "Prix total", "Médecin"};
        Object[][] achats = achatsHistorique.toArray(new Object[0][]);

        tableModel = new DefaultTableModel(achats, colonnes);
        JTable tableAchats = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableAchats);

        // Bouton pour modifier l'achat sélectionné
        JButton btnModifierAchat = new JButton("Modifier l'achat sélectionné");
        btnModifierAchat.addActionListener(e -> {
            int selectedRow = tableAchats.getSelectedRow();
            if (selectedRow != -1) {
                // Ouvre une boîte de dialogue pour modifier le médicament et la quantité
                String medicament = (String) tableAchats.getValueAt(selectedRow, 2);
                String quantite = (String) tableAchats.getValueAt(selectedRow, 3);
                double prixUnitaire = 5.00; // Prix unitaire par défaut (tu peux l'adapter si nécessaire)

                JPanel modificationPanel = new JPanel(new GridLayout(2, 2));
                modificationPanel.add(new JLabel("Médicament:"));
                JTextField medicamentField = new JTextField(medicament);
                modificationPanel.add(medicamentField);
                modificationPanel.add(new JLabel("Quantité:"));
                JTextField quantiteField = new JTextField(quantite);
                modificationPanel.add(quantiteField);

                int result = JOptionPane.showConfirmDialog(this, modificationPanel, "Modifier l'achat",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // Mettre à jour la table avec les nouvelles valeurs
                    tableAchats.setValueAt(medicamentField.getText(), selectedRow, 2);  // Mettre à jour le médicament
                    tableAchats.setValueAt(quantiteField.getText(), selectedRow, 3);  // Mettre à jour la quantité

                    // Recalculer le prix total basé sur la nouvelle quantité
                    int nouvelleQuantite = Integer.parseInt(quantiteField.getText());
                    double nouveauPrixTotal = prixUnitaire * nouvelleQuantite;
                    tableAchats.setValueAt(String.format("%.2f", nouveauPrixTotal), selectedRow, 4);  // Mettre à jour le prix total

                    JOptionPane.showMessageDialog(this, "L'achat a été modifié !");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un achat à modifier.");
            }
        });

        // Bouton retour et quitter
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> revenirAccueil());

        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(e -> System.exit(0));

        // Ajout des composants au panneau d'historique
        panelHistorique.add(labelHistorique);
        panelHistorique.add(Box.createRigidArea(new Dimension(0, 10)));
        panelHistorique.add(scrollPane);
        panelHistorique.add(Box.createRigidArea(new Dimension(0, 10)));
        panelHistorique.add(btnModifierAchat);
        panelHistorique.add(Box.createRigidArea(new Dimension(0, 10)));
        panelHistorique.add(btnRetour);
        panelHistorique.add(btnQuitter);

        // Remplacer le contenu du panneau central
        panelCentral.add(panelHistorique);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    // Méthode pour revenir à la page d'accueil
    private void revenirAccueil() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Bienvenue dans la Pharmacie Sparadrap");
        panel.add(label);

        // Remplacer le contenu du panneau central
        panelCentral.removeAll();
        panelCentral.add(panel);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    // Page pour afficher les détails d'un médecin / spécialiste
    private void afficherMedecinPanel() {
        panelCentral.removeAll();

        // Contenu temporaire, à ajuster selon les besoins
        JPanel panelMedecin = new JPanel();
        JLabel label = new JLabel("Détails des médecins / spécialistes");
        panelMedecin.add(label);

        // Bouton retour pour revenir à la page d'accueil
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> revenirAccueil());
        panelMedecin.add(btnRetour);

        // Remplacer le contenu du panneau central
        panelCentral.add(panelMedecin);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    // Page pour afficher les détails d'un client
    private void afficherClientPanel() {
        panelCentral.removeAll();

        // Contenu temporaire, à ajuster selon les besoins
        JPanel panelClient = new JPanel();
        JLabel label = new JLabel("Détails des clients");
        panelClient.add(label);

        // Bouton retour pour revenir à la page d'accueil
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> revenirAccueil());
        panelClient.add(btnRetour);

        // Remplacer le contenu du panneau central
        panelCentral.add(panelClient);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    // Liste simulée des médecins
    private String[] getListeMedecins() {
        return new String[] {
                "Dr Jean Dupont (Généraliste)",
                "Dr Claire Martin (Cardiologue)",
                "Dr Pierre Bernard (Dermatologue)"
        };
    }

    // Liste simulée des clients
    private String[] getListeClients() {
        return new String[] {
                "Pierre Martin",
                "Marie Dupuis",
                "Jean Leclerc"
        };
    }

    // Méthode principale
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardView::new);
    }
}
