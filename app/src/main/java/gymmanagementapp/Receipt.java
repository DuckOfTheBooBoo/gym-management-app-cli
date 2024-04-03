package gymmanagementapp;

import javax.swing.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Receipt {
    private String content = "";

    public Receipt(String content) {
        this.content = content;
    }

    public void showReceipt() {
        JOptionPane.showMessageDialog(null, content, "Receipt", JOptionPane.INFORMATION_MESSAGE);
    }

    public static Receipt fromTrial(String fullName, String email, Date date, int price) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");    
        String dateString = df.format(date);
        String content = String.format("Full name: %s\nE-mail: %s\nFree Trial date: %s\nPrice: Rp. %d", fullName, email, dateString, price);
        return new Receipt(content);
    }

    public static Receipt fromPersonalTrainer(String fullName, String email, PersonalTrainer trainer, Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");    
        String dateString = df.format(date);
        String content = String.format("Full name: %s\nE-mail: %s\nPersonal Trainer: %s\nDate: %s\nFee: Rp. %d", fullName, email, trainer.name, dateString, trainer.fee);
        return new Receipt(content);
    }

    public static Receipt fromMembership(String fullName, String email, Membership membership) {
        String content = String.format("""
                Full name: %s
                E-mail: %s
                Membership: %s
                Price: Rp. %d
                """, fullName, email, membership.name, membership.price);
        return new Receipt(content);
    }

    public static Receipt fromCart(String fullName, String email, ArrayList<Suplement> cart) {
        String header = String.format("""
                Full name: %s
                E-mail: %s
                =======ITEMS=======
                """, fullName, email);

        int total = 0;
        // Construct items list
        String items = "";
        for (Suplement suplement : cart) {
            items += String.format("""
                    %s \tRp. %d
                    (%s)
                    \tQty: %d
                    """, suplement.name, suplement.price, suplement.selectedVariant, suplement.selectedQuantity);
            total += suplement.price * suplement.selectedQuantity;
        }

        String totalStr = String.format("\nTotal:\t Rp. %d", total);
        String content = header + items + totalStr;
        return new Receipt(content);
    }   
}
