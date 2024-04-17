package gymmanagementapp;

import java.util.ArrayList;

public class Bundle {
    public String name = ""; // Nama bundle
    public int price = 0; // Harga bundle
    public int scoopQuota = 0; // kuota suplemen per bulan
    public String description = ""; // deskripsi bundle
    public Suplement suplement; // suplement yang akan dipilih
    public Membership membership; // membership yang akan dipilih
    public PersonalTrainer personalTrainer; // personal trainer yang akan dipilih

    public Bundle(String name, int price, int scoopQuota, String description, Suplement suplement, Membership membership, PersonalTrainer personalTrainer) {
        this.name = name;
        this.price = price;
        this.scoopQuota = scoopQuota;
        this.description = description;
        this.suplement = suplement;
        this.membership = membership;
        this.personalTrainer = personalTrainer;
    }

    // Mengembalikan detail bundle
    public String getDetails() {
        return String.format("""
            %s
              %s
              Rp. %d
            """, this.name, this.description, this.price);
    }
}

// Class pembantu untuk Bundle
class BundleHelper {
    private static ArrayList<Bundle> bundles = new ArrayList<Bundle>();

    // method untuk memberikan daftar objek Bundle
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