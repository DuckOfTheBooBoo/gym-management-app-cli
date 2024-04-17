package gymmanagementapp;

import java.util.*;

public class Trial {
    public Date trialDate;
    public int price;

    public Trial(Date trialDate, int price) {
        this.trialDate = trialDate;
        this.price = price;
    }
}

class TrialHelper {
    public Trial trial = new Trial(null, 32000);

    public static void trialPass(String fullName, String email) {
        Date date = null;
        int price = 32000;
        
        // Ini teks yang akan ditampilkan di dialog swing JOptionPane
        String trialDialogMsg = "Trial Price: Rp. 32.000\nEnter date (dd/MM/yyyy):";

        date = Util.dateInput(trialDialogMsg, "Select Trial Date");

        Receipt receipt = new TrialReceipt(fullName, email, date, price);
        receipt.showReceipt();
    }
}