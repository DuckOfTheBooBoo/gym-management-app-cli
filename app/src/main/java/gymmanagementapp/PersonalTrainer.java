package gymmanagementapp;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

public class PersonalTrainer {
    public String name;
    public int fee;
    public int rating;
    public Date date;

    public PersonalTrainer(String name, int fee, int rating) {
        this.name = name;
        this.fee = fee;
        this.rating = rating;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(this.date);
    }

    public String getDetails() {
        return String.format("""
                %s
                    Rating: %d
                    Fee: Rp. %d/session
                """, this.name, this.rating, this.fee);
    }
}

class PersonalTrainerHelper {
    private static PersonalTrainer[] personalTrainers = {
        new PersonalTrainer("Bambang Supraptno", 56000, 4),
        new PersonalTrainer("Agus Agus", 78000, 5),
        new PersonalTrainer("Latifah Diyahh", 69900, 10),
        new PersonalTrainer("Tecchi Agustina", 80000, 10),
        new PersonalTrainer("David Ferguson", 50000, 4)
    };

    public static PersonalTrainer[] getPersonalTrainers() {
        return personalTrainers;
    }

    public static PersonalTrainer selectPersonalTrainer() {
        PersonalTrainer[] trainers = PersonalTrainerHelper.getPersonalTrainers();
        PersonalTrainer selectedTrainer = null;
        Map<String, Integer> trainerMap = new HashMap<String, Integer>();


        // Construct menu string
        String pTDialogMsg = "";
        for (int i = 0; i < trainers.length; i++) {
            trainerMap.put(trainers[i].name, i + 1);
            pTDialogMsg += String.format("%d. %s", i + 1, trainers[i].getDetails());
        }

        for (int i = 0; i < trainers.length; i++) {
            trainerMap.put(trainers[i].name, i);
        }

        Object ptChoiceInput = JOptionPane.showInputDialog(null, pTDialogMsg, "Select Personal Trainer", JOptionPane.PLAIN_MESSAGE, null, trainerMap.keySet().toArray(), 1);

        if (ptChoiceInput == null) {
            JOptionPane.showMessageDialog(null, "You did not select any personal trainers. Exiting...");
            return null;
        }

        int trainerIndex = trainerMap.get(ptChoiceInput);
        selectedTrainer = trainers[trainerIndex];
        Date date = Util.dateInput("Input personal trainer date (dd/MM/yyyy):", "Personal Trainer Date");
        selectedTrainer.setDate(date);

        return selectedTrainer;
    }

    public static void ptProcedure(String fullName, String email) {
        PersonalTrainer selectedTrainer = selectPersonalTrainer();

        Receipt receipt = new PersonalTrainerReceipt(fullName, email, selectedTrainer);
        receipt.showReceipt();
    }
}