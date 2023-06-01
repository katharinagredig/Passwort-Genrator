import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.Random;

// Die Hauptklasse des Passwort-Generators
public class PasswortGenerator {
    // Definieren der Zeichensätze, die für die Erzeugung der Passwörter verwendet werden können.
    private static final String CHARACTERSUPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CHARACTERSLOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHARACTERSSONDER = "!@#$%^&*()";
    private static final String CHARACTERNUMBERS = "0123456789";

    // Komponenten / Benutzeroberflächenelemente
    private JTextField passwordLengthField;
    private JTextField generatedPasswordField;
    private JCheckBox grossbuchstaben;
    private JCheckBox kleinbuchstaben;
    private JCheckBox zahlen;
    private JCheckBox sonderzeichen;

    // Hauptmethode, die das Programm startet
    public static void main(String[] args) {
        // Erzeugung und Anzeige der GUI in einem separaten Thread
        SwingUtilities.invokeLater(PasswortGenerator::createAndShowGUI);
    }

    // Methode zum Erzeugen und Anzeigen der GUI
    private static void createAndShowGUI() {
        // Erzeugung des Hauptfensters
        JFrame frame = new JFrame("Passwort Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Programmfenster über Schaltfläche 'X' schliessen
        frame.setSize(700, 600);

        // Erzeugung des Hauptfensters
        PasswortGenerator passwortGenerator = new PasswortGenerator();
        frame.add(passwortGenerator.createContentPane());

        // Anzeige des Fensters
        frame.setVisible(true);
    }

    // Methode zum Erzeugen des Hauptfensters und Hinzufügen aller Benutzeroberflächenelemente
    public JPanel createContentPane() {
        // Erzeugung des Panels
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Hinzufügen aller Eingabezeilen
        addInputRow(panel, "Passwortlänge:", passwordLengthField = new JTextField());
        addInputRow(panel, "Großbuchstaben:", grossbuchstaben = new JCheckBox());
        addInputRow(panel, "Kleinbuchstaben:", kleinbuchstaben = new JCheckBox());
        addInputRow(panel, "Zahlen:", zahlen = new JCheckBox());
        addInputRow(panel, "Sonderzeichen:", sonderzeichen = new JCheckBox());

        // Hinzufügen des Felds für das generierte Passwort
        addInputRow(panel, "Generiertes Passwort:", generatedPasswordField = new JTextField());
        generatedPasswordField.setEditable(false);

        // Hinzufügen der Schaltflächen zum Kopieren und Generieren des Passworts
        JButton copyButton = new JButton("Kopiere Passwort");
        copyButton.addActionListener(this::copyPasswordAction);
        panel.add(copyButton);

        JButton generateButton = new JButton("Passwort generieren");
        generateButton.addActionListener(this::generatePasswordAction);
        panel.add(generateButton);

        // Rückgabewert Hauptfenster
        return panel;
    }

    // Methode zum Hinzufügen einer Eingabezeile zum Panel
    private void addInputRow(JPanel panel, String labelText, JComponent inputComponent) {
        JPanel row = new JPanel(new BorderLayout());
        row.add(new JLabel(labelText), BorderLayout.WEST);
        row.add(inputComponent, BorderLayout.CENTER);
        panel.add(row);
    }

    // Methode, die ausgeführt wird, wenn der "Kopiere Passwort"-Knopf geklickt wird
    private void copyPasswordAction(ActionEvent e) {
        // Kopiert den Text des generierten Passworts in die Zwischenablage
        StringSelection stringSelection = new StringSelection(generatedPasswordField.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    // Methode, die ausgeführt wird, wenn der "Passwort generieren"-Knopf gedrückt wird
    private void generatePasswordAction(ActionEvent e) {
        // Überprüfung, welche Checkboxen ausgewählt wurden
        int selectedCheckboxes = 0;
        String characterSet = "";
        if(grossbuchstaben.isSelected()) {
            characterSet += CHARACTERSUPPERCASE;
            selectedCheckboxes++;
        }
        if(kleinbuchstaben.isSelected()) {
            characterSet += CHARACTERSLOWERCASE;
            selectedCheckboxes++;
        }
        if(zahlen.isSelected()) {
            characterSet += CHARACTERNUMBERS;
            selectedCheckboxes++;
        }
        if(sonderzeichen.isSelected()) {
            characterSet += CHARACTERSSONDER;
            selectedCheckboxes++;
        }

        // Mindestens zwei Optionen müssen ausgewählt sein
        if(selectedCheckboxes < 2) {
            JOptionPane.showMessageDialog(null,
                    "Bitte wählen Sie mindestens zwei Optionen aus.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Überprüfung der Länge des Passworts
        try {
            int passwordLength = Integer.parseInt(passwordLengthField.getText());
            if (passwordLength < 8) {
                JOptionPane.showMessageDialog(null,
                        "Ein sicheres Passwort sollte mindestens 8 Zeichen lang sein.",
                        "Warnung",
                        JOptionPane.WARNING_MESSAGE);
            }

            // Generierung des Passworts und Anzeige im Feld
            String newGeneratedPassword = generatePassword(passwordLength, characterSet);
            generatedPasswordField.setText(newGeneratedPassword);
        } catch (NumberFormatException nfe) {
            // Falls die Eingabe der Passwortlänge ungültig ist, wird eine Fehlermeldung angezeigt
            JOptionPane.showMessageDialog(null,
                    "Bitte geben Sie eine gültige Zahl für die Passwortlänge ein.",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Methode zum Generieren des Passworts
    private static String generatePassword(int passwordLength, String characterSet) {
        // Erzeugung eines zufälligen Passworts mit der angegebenen Länge und aus dem angegebenen Zeichensatz
        Random random = new Random();
        StringBuilder password = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(characterSet.length());
            char randomChar = characterSet.charAt(index);
            password.append(randomChar);
        }

        // Rückgabe des generierten Passworts
        return password.toString();
    }
}


