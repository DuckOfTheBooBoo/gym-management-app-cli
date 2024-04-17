package gymmanagementapp;

import javax.swing.*;

import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Kelas abstrak Receipt
 * Kelas ini berguna untuk membuat sebuah "template" untuk subclass nanti yang akan dibuat
 */
abstract public class Receipt {
    protected String content = ""; // Berisi konten struk
    protected String fullName; // Berisi full name user
    protected String email; // Berisi email user
    protected int total; // Berisi total transaksi

    public Receipt(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    // method untuk menampilkan struk dengan JOptionPane
    public void showReceipt() {
        JOptionPane.showMessageDialog(null, this.content, "Receipt", JOptionPane.INFORMATION_MESSAGE);
    }

    // method untuk membuat bagian atas dari struk
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
        this.content += header; // Menambahkan bagian header ke dalam content
    }

    // method abstrak untuk membuat bagian tengah struk.
    // Karena konten struk berbeda-beda tergantung dengan produk apa yang dipilih
    // Maka kita biarkan setiap subclass untuk mengimplementasikannya secara mandiri (logic yang berbeda beda)
    abstract protected void constructBody();

    // method untuk membuat bagian bawah struk
    protected void constructFooter() {
        // Menambahkan bagian footer ke dalam content
        this.content += String.format("""

                ==========================
                TOTAL: Rp. %d
                """,this.total);
    }
}

// Class TrialReceipt yang mewarisi Receipt
// Berguna untuk membuat Receipt (struk) dengan content Trial
class TrialReceipt extends Receipt {
    private Date date; // Berisi tanggal trial
    private int price; // Berisi harga trial

    public TrialReceipt(String fullName, String email, Date date, int price) {
        super(fullName, email);
        this.date = date;
        this.price = price;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    // Membangun content struk sesuai dengan data yang trial miliki
    protected void constructBody() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); // Menetapkan standar format tanggal  
        String dateString = df.format(this.date); // Mengubah date menjadi string
        this.content += String.format("""
            Free Trial date: %s
            Price: Rp. %d""", dateString, this.price);
        this.total = this.price; // Menetapkan harga trial sebagai total
    }
}

// Class MembershipReceipt yang mewarisi Receipt
// Berguna untuk membuat Receipt (struk) dengan content Membership
class MembershipReceipt extends Receipt {
    private Membership membership; // berisi objek membership yang dipilih user

    public MembershipReceipt(String fullName, String email, Membership membership) {
        super(fullName, email);
        this.membership = membership;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    // Membangun content struk sesuai dengan data yang membership miliki
    protected void constructBody() {
        this.content += String.format("""
                Membership: %s
                Price: Rp. %d
                """, membership.name, membership.price);
        this.total = this.membership.price;
    }
}

// Class CartReceipt yang mewarisi Receipt
// Berguna untuk membuat Receipt (struk) dengan content yang berada di dalam keranjang (cart)
class CartReceipt extends Receipt {
    private ArrayList<Suplement> cart; // berisi keranjang saat memilih suplement

    public CartReceipt(String fullName, String email, ArrayList<Suplement> cart) {
        super(fullName, email);
        this.cart = cart;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    // Membangun content struk sesuai dengan data (setiap suplement) yang cart (keranjang) miliki
    protected void constructBody() {
        // Membangun content suplemen yang berada di dalam cart satu persatu
        String items = "";
        for (Suplement suplement : cart) {
            items += String.format("""
                    %s \tRp. %d
                    (%s)
                    \tQty: %d
                    """, suplement.name, suplement.price, suplement.selectedVariant, suplement.selectedQuantity);
            this.total += suplement.price * suplement.selectedQuantity; // menambahkan total
        }
    }
}

// Class PersonalTrainerReceipt yang mewarisi Receipt
// Berguna untuk membuat Receipt (struk) dengan content yang PersonalTrainer miliki
class PersonalTrainerReceipt extends Receipt {
    private PersonalTrainer trainer; // berisi PersonalTrainer yang dipilih user

    public PersonalTrainerReceipt(String fullName, String email, PersonalTrainer trainer) {
        super(fullName, email);
        this.trainer = trainer;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    // Membangun content struk sesuai dengan data yang PersonalTrainer miliki
    protected void constructBody() {
        this.content += String.format("""
            Personal Trainer: %s
            Date: %s
            Fee: Rp. %d""", trainer.name, trainer.getDateString(), trainer.fee);       
        this.total = this.trainer.fee;
    }
}

// Class BundleReceipt yang mewarisi Receipt
// Berguna untuk membuat Receipt (struk) dengan content yang Bundle miliki
class BundleReceipt extends Receipt {
    private Bundle bundle; // Bundle yang dipilih user

    public BundleReceipt(String fullName, String email, Bundle bundle) {
        super(fullName, email);
        this.bundle = bundle;
        this.constructHeader();
        this.constructBody();
        this.constructFooter();
    }

    // Membangun content struk sesuai dengan data yang Bundle miliki
    protected void constructBody() {
        this.content += String.format("""
                %s
                %s
                %s
                %s (%d scoop/week)
                Rp. %d
                """, bundle.name, bundle.membership.name, bundle.personalTrainer.name, bundle.suplement.name, bundle.scoopQuota, bundle.price);
        this.total = this.bundle.price; // Menetapkan harga total sesuai dengan harga bundle
    }
}