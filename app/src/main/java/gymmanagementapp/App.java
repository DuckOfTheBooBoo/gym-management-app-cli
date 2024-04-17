package gymmanagementapp;

import javax.swing.*;

public class App {
    private static String fullName = null;
    private static String email = null;

    public static void main(String[] args) {
        while (true) {
            fullName = JOptionPane.showInputDialog(null, "WELCOME TO GYM EXPERT\nEnter your name:", "GYM EXPERT", JOptionPane.PLAIN_MESSAGE);

            if (fullName == null) {
                System.exit(0);
            }

            email = JOptionPane.showInputDialog(null, "Enter your e-mail:", "GYM EXPERT", JOptionPane.PLAIN_MESSAGE);

            if (email == null) {
                System.exit(0);
            }

            if (fullName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your name and e-mail.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }

        Object choiceInput = JOptionPane.showInputDialog(null, "Select options below", "Select option", JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Trial", "Personal Trainer", "Membership", "Suplement", "Bundle"}, "Trial");

        String choice = choiceInput != null ? choiceInput.toString() : "";

        
        switch (choice) {        
            case "Trial":
                TrialHelper.trialPass(fullName,email);
                break;

            case "Personal Trainer":
                PersonalTrainerHelper.ptProcedure(fullName, email);
                break;
            
            case "Membership":
                MembershipHelper.selectMembership(fullName, email);
                break;
            
            case "Suplement":
                SuplementHelper.spProcedure(fullName, email);
                break;
            
            case "Bundle":
                BundleHelper.selectBundle(fullName, email);
                break;
            
            // Jika user tidak memilih apapun, maka akan keluar dari program
            default:
                JOptionPane.showMessageDialog(null, "You did not select any option. Exiting...");
                break;
        }
    }
}
