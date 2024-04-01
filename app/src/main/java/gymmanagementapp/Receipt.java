package gymmanagementapp;

import javax.swing.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
