package gymmanagementapp;

public class Membership {
    public String name = "";
    public int price = 0;
    public int durationMonth = 0;

    public Membership(String name, int price, int durationMonth) {
        this.name = name;
        this.price = price;
        this.durationMonth = durationMonth;
    }

    public static Membership[] getMemberships() {
        return new Membership[] {
            new Membership("One Month", 250000, 1),
            new Membership("3 Months", 550000, 3),
            new Membership("6 Months", 1250000, 6),
            new Membership("12 Months (1 year)", 2500000, 12)
        };
    }
}
