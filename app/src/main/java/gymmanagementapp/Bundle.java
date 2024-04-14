package gymmanagementapp;

import java.util.ArrayList;

public class Bundle {
    public String name = "";
    public int price = 0;
    public int scoopQuota = 0; // per week
    public String description = "";
    public Suplement suplement;
    public Membership membership;
    public PersonalTrainer personalTrainer;

    public Bundle(String name, int price, int scoopQuota, String description, Suplement suplement, Membership membership, PersonalTrainer personalTrainer) {
        this.name = name;
        this.price = price;
        this.scoopQuota = scoopQuota;
        this.description = description;
        this.suplement = suplement;
        this.membership = membership;
        this.personalTrainer = personalTrainer;
    }

    public String getDetails() {
        return String.format("""
            %s
              %s
              Rp. %d
            """, this.name, this.description, this.price);
    }
}

class BundleHelper {
    private static ArrayList<Bundle> bundles = new ArrayList<Bundle>();

    public static ArrayList<Bundle> getBundles() {
        if (bundles.size() == 0) {
            Membership[] memberships = MembershipHelper.getMemberships();            
            bundles.add(new Bundle("Bundle ADDICT", 1000000, 1, "3 Months Membership\nFree Suplement 1 scoop/week", null, memberships[0], null));
            bundles.add(new Bundle("Bundle EXPERT", 2100000, 3, "6 Months Membership\nFree 1 Personal Trainer Session\nFree Suplement 3 scoop/week", null, memberships[1], null));
            bundles.add(new Bundle("Bundle MAJAPAHIT", 3400000, 1, "12 Months Membership\nFree 2 Personal Trainer Session\nFree Suplement 4 scoop/week", null, memberships[2], null));
        }
        return bundles;
    }
}