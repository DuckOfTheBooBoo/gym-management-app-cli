package gymmanagementapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

public class Util {
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
}
