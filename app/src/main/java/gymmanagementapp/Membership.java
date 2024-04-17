package gymmanagementapp;

// Class untuk merepresentasikan objek Membership
public class Membership {
    public String name = ""; // nama memberhsip
    public int price = 0; // harga membership
    public int durationMonth = 0; // Durasi lama membership dalam bulan

    public Membership(String name, int price, int durationMonth) {
        this.name = name;
        this.price = price;
        this.durationMonth = durationMonth;
    }

    // Mengembalikan detail membership
    public String getDetails() {
        return String.format("%s (Rp. %d)", this.name, this.price);
    }
}

// Class pembantu untuk membuat objek Membership
class MembershipHelper {
    private static Membership[] memberships = {
        new Membership("One Month", 250000, 1),
        new Membership("3 Months", 550000, 3),
        new Membership("6 Months", 1250000, 6),
        new Membership("12 Months (1 year)", 2500000, 12)
    };

    // Method untuk mengembalikan daftar objek Membership
    public static Membership[] getMemberships() {
        return memberships;
    }
}