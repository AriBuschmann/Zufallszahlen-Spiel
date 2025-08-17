import java.awt.*;
import java.awt.event.*;
import java.lang.StringBuilder;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

/**
 * Zufallszahlen-Spiel:
 * - Benutzer gibt eine Zahl zwischen 1 und 10 ein.
 * - Programm generiert eine Zufallszahl zwischen 1 und 10.
 * - Anzeige, ob die Eingabe korrekt war; Ausgabe der generierten Zahl.
 */
public class ZufallszahlenSpiel {

    // GUI-Komponenten
    private JFrame frame;
    private JTextField inputField;
    private JButton checkButton;
    private JTextArea resultArea;
    private JLabel infoLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // GUI erstellen und sichtbar machen
            ZufallszahlenSpiel app = new ZufallszahlenSpiel();
            app.createAndShowGUI();
        });
    }

    // Erstellen und Anzeigen der GUI
    public void createAndShowGUI() {
        // JFrame erstellen
        frame = new JFrame("Zufallszahlen-Spiel 1–10");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Größe des Fensters
        frame.setSize(400, 500);

        // Hauptpanel mit vertikaler Anordnung
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Beschriftung für die Eingabe
        JLabel promptLabel = new JLabel("Gib eine Zahl zwischen 1 und 10 ein:");
        promptLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(promptLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        // Eingabefeld erstellen; Spaltenanzahl legt Breite in Zeichen fest
        inputField = new JTextField(10);
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(inputField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Button zum Prüfen
        checkButton = new JButton("Prüfen");
        checkButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(checkButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Textbereich zur Ergebnisanzeige (nicht editierbar)
        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Informationslabel für Hinweise / Fehlermeldungen
        infoLabel = new JLabel(" ");
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(infoLabel);

        // Button-Klick und Enter im Textfeld -> gleiche Logik
        ActionListener action = e -> checkGuessAndGenerate();
        checkButton.addActionListener(action);
        inputField.addActionListener(action);

        // Komponenten dem Frame hinzufügen
        frame.getContentPane().add(mainPanel, BorderLayout.NORTH);

        // Fenster zentrieren und sichtbar machen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Liest die Eingabe, validiert sie, generiert eine Zufallszahl
    // und aktualisiert die GUI
    private void checkGuessAndGenerate() {
        // Eingabetext aus dem Eingabefeld holen und Leerzeichen entfernen
        String text = inputField.getText().trim();

        // Versuch, die Eingabe als ganze Zahl zu parsen
        int guess;
        try {
            guess = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            // Wenn keine ganze Zahl eingegeben wurde, passende Meldung anzeigen
            resultArea.setText("Ungültige Eingabe!");
            infoLabel.setText("Bitte eine Ganzzahl zwischen 1 und 10 eingeben.");
            // Eingabefeld für schnelle Wiederholung markieren
            inputField.requestFocusInWindow();
            inputField.selectAll();
            return;
        }

        // Bereichsprüfung: Zahl muss zwischen 1 und 10 liegen
        if (guess < 1 || guess > 10) {
            resultArea.setText("Zahl außerhalb des erlaubten Bereichs.");
            infoLabel.setText("Erlaubte Werte: ganze Zahlen von 1 bis 10.");
            inputField.requestFocusInWindow();
            inputField.selectAll();
            return;
        }

        // Zufallszahl generieren (inklusive 1 und 10)
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 11);

        // Ergebnis vergleichen ausgeben
        StringBuilder sb = new StringBuilder();
        sb.append("Ergebnis:\n\n");
        sb.append("Deine Zahl: ").append(guess).append("\n");
        sb.append("Generierte Zahl: ").append(randomNumber).append("\n\n");
        if (guess == randomNumber) {
            sb.append("Glückwunsch! Du hast die Zahl richtig geraten.");
            infoLabel.setText("Treffer!");
        } else {
            sb.append("Leider falsch! Versuche es noch einmal.");
            infoLabel.setText("Nicht getroffen.");
        }

        // Ergebnis in das Textfeld schreiben und Eingabe zur Wiederholung markieren
        resultArea.setText(sb.toString());
        inputField.requestFocusInWindow();
        inputField.selectAll();
    }
}