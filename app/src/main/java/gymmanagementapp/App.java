package gymmanagementapp;

import javax.swing.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
    private static String fullName = null;
    private static String email = null;
    
    public static Date dateInput(String dialogMsg, String title) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        while (true) {
            String dateInput = JOptionPane.showInputDialog(null, dialogMsg, title, JOptionPane.PLAIN_MESSAGE);

            try {
                date = dateFormat.parse(dateInput);

                // Check if date has already passed.
                if (date.getTime() < new Date().getTime()) {
                    JOptionPane.showMessageDialog(null, "Date cannot be in the past.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                } else {
                    break;
                }

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, String.format("Invalid date format -> %s.", dateInput), "Invalid input", JOptionPane.ERROR_MESSAGE);
            }
        }

        return date;
    }

    public static void trialPass() {
        Date date = null;
        int price = 32000;
        String trialDialogMsg = "Trial Price: Rp. 32.000\nEnter date (dd/MM/yyyy):";

        date = dateInput(trialDialogMsg, "Select Trial Date");

        Receipt receipt = Receipt.fromTrial(fullName, email, date, price);
        receipt.showReceipt();
    }

    public static void personalTrainer() {
        PersonalTrainer[] trainers = PersonalTrainer.getPersonalTrainers();
        PersonalTrainer selectedTrainer = null;

        // Construct menu string
        String pTDialogMsg = "";
        for (int i = 0; i < trainers.length; i++) {
            String msg = String.format("""
            %d. %s
                Fee: Rp. %d/session
                Rating: %d
            """, i + 1, trainers[i].name, trainers[i].fee, trainers[i].rating);
            pTDialogMsg += msg;
        }

        Object ptChoiceInput = JOptionPane.showInputDialog(null, pTDialogMsg, "Select Personal Trainer", JOptionPane.PLAIN_MESSAGE, null, new Object[]{1, 2, 3, 4, 5}, 1);

        if (ptChoiceInput == null) {
            JOptionPane.showMessageDialog(null, "You did not select any personal trainers. Exiting...");
            return;
        }

        int ptChoice = Integer.parseInt(ptChoiceInput.toString());
        selectedTrainer = trainers[ptChoice - 1];

        Date date = dateInput("Input personal trainer date (dd/MM/yyyy):", "Personal Trainer Date");
        Receipt receipt = Receipt.fromPersonalTrainer(fullName, email, selectedTrainer, date);
        receipt.showReceipt();
    }

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
                trialPass();
                break;
            
            case "2":
                personalTrainer();
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
