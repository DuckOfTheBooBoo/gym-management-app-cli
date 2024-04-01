package gymmanagementapp;

import javax.swing.*;

public class App {
    private static String fullName = null;
    private static String email = null;
    
    public static void main(String[] args) {
        // Instances initialization
        // Ask for name and email
        while (true) {
            fullName = JOptionPane.showInputDialog(null, "WELCOME TO GYM EXPERT\nEnter your name:", "GYM EXPERT", JOptionPane.PLAIN_MESSAGE);
            email = JOptionPane.showInputDialog(null, "Enter your e-mail:", "GYM EXPERT", JOptionPane.PLAIN_MESSAGE);

            if ((fullName == null || fullName.isEmpty()) || (email == null || email.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Please enter your name and e-mail.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }

        // Menu choices
        String menuChoices = """
            1. Trial (One-day pass)
            2. Personal Trainer
            3. Membership
            4. Suplement
            5. Bundle
            """;
        
        Object choiceInput = JOptionPane.showInputDialog(null, menuChoices, "Select option", JOptionPane.PLAIN_MESSAGE, null, new Object[]{"1", "2", "3", "4", "5"}, "1");

        String choice = choiceInput != null ? choiceInput.toString() : "";

        switch (choice) {
            case "1":
                JOptionPane.showMessageDialog(null, choice);
                break;
            
            case "2":
                JOptionPane.showMessageDialog(null, choice);
                break;
            
            case "3":
                JOptionPane.showMessageDialog(null, choice);
                break;
            
            case "4":
                JOptionPane.showMessageDialog(null, choice);
                break;
            
            case "5":
                JOptionPane.showMessageDialog(null, choice);
                break;
            
            default:
                JOptionPane.showMessageDialog(null, "You did not select any option. Exiting...");
                break;
        }
    }
}
