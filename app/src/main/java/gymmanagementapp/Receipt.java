package gymmanagementapp;

import javax.swing.*;

import java.text.SimpleDateFormat;
import java.util.*;

abstract public class Receipt {
    protected String content = "";
    protected String fullName;
    protected String email;
    protected int total;

    public Receipt(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public void showReceipt() {
        JOptionPane.showMessageDialog(null, this.content, "Receipt", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void constructHeader() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String header = String.format("""
                ========GYM EXPERT========
                Jakarta, M.H. Tamrin City
                Date: %s
                Full name: %s
                E-mail: %s
                ==========================
                """, df.format(new Date()), fullName, email);
        this.content += header;
    }

    abstract protected void constructBody();

    protected void constructFooter() {
        this.content += String.format("""
            
                ==========================
                TOTAL: Rp. %d
                """,this.total);
    }
}

class TrialReceipt extends Receipt {
    private Date date;
    private int price;

    public TrialReceipt(String fullName, String email, Date date, int price) {
        super(fullName, email);
        this.date = date;
        this.price = price;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    protected void constructBody() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");    
        String dateString = df.format(this.date);
        this.content += String.format("""
            Free Trial date: %s
            Price: Rp. %d""", dateString, this.price);
        this.total = this.price;
    }
}

class MembershipReceipt extends Receipt {
    private Membership membership;

    public MembershipReceipt(String fullName, String email, Membership membership) {
        super(fullName, email);
        this.membership = membership;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    protected void constructBody() {
        this.content += String.format("""
                Membership: %s
                Price: Rp. %d
                """, membership.name, membership.price);
        this.total = this.membership.price;
    }
}

class CartReceipt extends Receipt {
    private ArrayList<Suplement> cart;

    public CartReceipt(String fullName, String email, ArrayList<Suplement> cart) {
        super(fullName, email);
        this.cart = cart;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    protected void constructBody() {
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
        this.content += items + totalStr;
        this.total = total;
    }
}

class PersonalTrainerReceipt extends Receipt {
    private PersonalTrainer trainer;

    public PersonalTrainerReceipt(String fullName, String email, PersonalTrainer trainer) {
        super(fullName, email);
        this.trainer = trainer;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    protected void constructBody() {
        this.content += String.format("""
            Personal Trainer: %s
            Date: %s
            Fee: Rp. %d""", trainer.name, trainer.getDateString(), trainer.fee);       
        this.total = this.trainer.fee;
    }
}

class BundleReceipt extends Receipt {
    private Bundle bundle;

    public BundleReceipt(String fullName, String email, Bundle bundle) {
        super(fullName, email);
        this.bundle = bundle;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    protected void constructBody() {
        this.content += String.format("""
                %s
                  - %s Membership
                  - Personal Trainer %s at %s
                  - %s (%d scoop/week)
                Rp. %d
                """, bundle.name, bundle.membership.name, bundle.personalTrainer.name, bundle.personalTrainer.getDateString(), bundle.suplement.name, bundle.scoopQuota, bundle.price);
        this.total = this.bundle.price;
    }
}