package gymmanagementapp;

import javax.swing.*;
import java.util.*;

public class Membership {
    public String name = "";
    public int price = 0;
    public int durationMonth = 0;

    public Membership(String name, int price, int durationMonth) {
        this.name = name;
        this.price = price;
        this.durationMonth = durationMonth;
    }

    public String getDetails() {
        return String.format("%s (Rp. %d)", this.name, this.price);
    }
}

class MembershipHelper {
    private static Membership[] memberships = {
        new Membership("One Month", 250000, 1),
        new Membership("3 Months", 550000, 3),
        new Membership("6 Months", 1250000, 6),
        new Membership("12 Months (1 year)", 2500000, 12)
    };

    public static Membership[] getMemberships() {
        return memberships;
    }

    public static void selectMembership(String fullName, String email) {
        Membership[] memberships = MembershipHelper.getMemberships();
        Membership selectedMembership = null;
        ArrayList<String> membershipOptions = new ArrayList<String>();
        Map<String, Integer> membershipMap = new HashMap<>();

        // Construct dialog message
        for (int i = 0; i < memberships.length; i++) {
            membershipOptions.add(memberships[i].getDetails());
            membershipMap.put(memberships[i].getDetails(), i);
        }

        Object choiceInput = JOptionPane.showInputDialog(null, "Select Membership", "Select Membership", JOptionPane.PLAIN_MESSAGE, null, membershipOptions.toArray(), null);

        if (choiceInput == null) {
            JOptionPane.showMessageDialog(null, "You did not select any membership. Exiting...");
            return;
        }

        int membershipIndex = membershipMap.get(choiceInput);
        selectedMembership = memberships[membershipIndex];

        if (selectedMembership == null) {
            return;
        }

        Receipt receipt = new MembershipReceipt(fullName, email, selectedMembership);
        receipt.showReceipt();
    }
}