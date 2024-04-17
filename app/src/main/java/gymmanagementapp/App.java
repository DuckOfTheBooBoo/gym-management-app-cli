package gymmanagementapp;

import javax.swing.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class App {
    private static String fullName = null;
    private static String email = null;
    
    /*dateInput method
     * method ini menerima 2 parameter
        * - dialogMsg: teks yang akan ditampilkan di dialog swing JOptionPane
        * - title: judul dialog
     * method ini mengembalikan objek Date yang digunakan jika kita ingin menginputkan tanggal
     */
    public static Date dateInput(String dialogMsg, String title) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Menetapkan format tanggal dalam bentuk teks
        
        while (true) {
            // Disini kita minta user untuk memasukkan string berupa tanggal
            String dateInput = JOptionPane.showInputDialog(null, dialogMsg, title, JOptionPane.PLAIN_MESSAGE);
            
            /*
             * Disini kita menggunakan try catch statement karena proses dateFormat.parse berpotensi
             * menimbulkan error yaitu ParseException (lompat ke baris 45 untuk melihat error handling)
             */
            try { 
                // Konversi string ke objek Date
                date = dateFormat.parse(dateInput);

                // Cek apakah tanggalnya valid; yaitu jika tanggalnya lebih kecil dari tanggal sekarang (sudah lewat)
                if (date.getTime() < new Date().getTime()) {

                    // Jika benar, maka tampilkan dialog error
                    JOptionPane.showMessageDialog(null, "Date cannot be in the past.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    // Mulai ulang loop (ke baris 24)
                } else {
                    // Jika tidak, keluar dari while loop (di baris ke 23) dan lompat ke baris 50
                    break;
                }

            } catch (ParseException e) { // Disini kita handle error ParseException
                // Tampilkan dialog error
                JOptionPane.showMessageDialog(null, String.format("Invalid date format -> %s.", dateInput), "Invalid input", JOptionPane.ERROR_MESSAGE);
                
                // Mulai ulang loop (ke baris 24)
            }
        }

        return date; // Kembalikan variable date yang memiliki tipe data Date
    }

    // Kesini gengs
    /*
     * trialPass(), prosedur khusus untuk melakukan proses pemilihan trial pass GYM EXPERT
     */
    public static void trialPass() {
        Date date = null; // placeholder untuk tanggal trial
        int price = 32000; // harga trial (Rp.)
        
        // Ini teks yang akan ditampilkan di dialog swing JOptionPane
        String trialDialogMsg = "Trial Price: Rp. 32.000\nEnter date (dd/MM/yyyy):";

        // Meminta user memasukkan tanggal trial dengan memanggil method dateInput
        /* Dengan memberikan argumen:
         *   dialogMsg (parameter) = trialDialogMsg (argument)
         *   title (parameter) = "Select Trial Date" (argument)
         */
        date = dateInput(trialDialogMsg, "Select Trial Date");

        // Membuat objek Receipt melalui TrialReceipt (subclass dari Receipt)
        Receipt receipt = new TrialReceipt(fullName, email, date, price);

        // Tampilkan receipt (struk) dengan JOptionPane
        receipt.showReceipt();
    }

    /* method selectPersonalTrainer(), method ini berguna untuk memilih personal trainer
     * dan mengembalikan objek PersonalTrainer ke dalam prosedur
     */
    public static PersonalTrainer selectPersonalTrainer() {
        // Mengambil daftar personal trainer dari PersonalTrainerHelper
        PersonalTrainer[] trainers = PersonalTrainerHelper.getPersonalTrainers();
        // Inisialisasi variabel trainer yang terpilih
        PersonalTrainer selectedTrainer = null;

        // Membuat mapping index trainer dalam variable trainers
        /*
         * Contoh isi data
         * Tecchi Agustina -> 4
         * Karena instance personalTrainer yang memiliki nama Tecchi Agustina berada di index (posisi ke) 4 di array traines (baris ke 87). FYI posisi array dimulai dari 0, jadi item pertama di array berada di index 0.
         */
        Map<String, Integer> trainerMap = new HashMap<String, Integer>();


        // Membangun teks dialog yang berisikan PersonalTrainer dan atributnya dengan loop
        String pTDialogMsg = "";
        for (int i = 0; i < trainers.length; i++) {
            trainerMap.put(trainers[i].name, i + 1);
            pTDialogMsg += String.format("%d. %s", i + 1, trainers[i].getDetails());
        }

        // Memasukkan mapping yang sudah dijelaskan di baris ke 91
        for (int i = 0; i < trainers.length; i++) {
            trainerMap.put(trainers[i].name, i);
        }

        // Meminta user untuk memilih personal trainer
        /*
         * trainerMap.keySet().toArray(): Mengubah keys yang berada di trainerMap menjadi array
         * Jadi, array yang berisi keys dari trainerMap
         */
        Object ptChoiceInput = JOptionPane.showInputDialog(null, pTDialogMsg, "Select Personal Trainer", JOptionPane.PLAIN_MESSAGE, null, trainerMap.keySet().toArray(), 1);

        // Cek apakah user tidak memilih personal trainer
        if (ptChoiceInput == null) {
            // Tampilkan dialog error
            JOptionPane.showMessageDialog(null, "You did not select any personal trainers. Exiting...");

            // Matikkan program jika user tidak memilih PersonalTrainer
            System.exit(0);
        }

        // Proses mendapatkan index PersonalTrainer relatif ke array trainer dari trainerMap
        int trainerIndex = trainerMap.get(ptChoiceInput);

        // Memilih PersonalTrainer yang berada di dalam array trainer dengan index (trainerIndex)
        selectedTrainer = trainers[trainerIndex];

        // Meminta user untuk memasukkan tanggal untuk menyewa PersonalTrainer
        Date date = dateInput("Input personal trainer date (dd/MM/yyyy):", "Personal Trainer Date");

        // Menyimpan tanggal ke dalam trainer yang dipilih
        selectedTrainer.date = date;

        return selectedTrainer;
    }

    public static void personalTrainer() {
        // Memilih PersonalTrainer dengan memanggil prosedur selectPersonalTrainer (ke baris 85)
        PersonalTrainer selectedTrainer = selectPersonalTrainer();

        // Membuat objek Receipt dengan menggunakan class PersonalTrainerReceipt
        Receipt receipt = new PersonalTrainerReceipt(fullName, email, selectedTrainer);

        // Menampilkan receipt (struk)
        receipt.showReceipt();
    }    

    /*Prosedur membership()
     * Berguna untuk memilih membership
     */
    public static void membership() {
        // Mengambil daftar membership dari MembershipHelper (cek Membership.java baris ke 30)
        Membership[] memberships = MembershipHelper.getMemberships();

        // Inisialisasi membership yang akan dipilih
        Membership selectedMembership = null;

        // ArrayList membershipOptions untuk menyimpan daftar nilai yang akan ditampilkan
        // di dropdown JOptionPane
        ArrayList<String> membershipOptions = new ArrayList<String>();

        // HashMap membershipMap untuk menyimpan index dari membershipOptions
        Map<String, Integer> membershipMap = new HashMap<>();

        // Membangun teks dialog yang berisikan membership dan atributnya (harga dan durasi membership)
        for (int i = 0; i < memberships.length; i++) {
            membershipOptions.add(memberships[i].getDetails());
            membershipMap.put(memberships[i].getDetails(), i);
        }


        // Meminta user untuk memilih membership
        // membershipOptions.toArray(): merubah ArrayList ke Array, karena JOptionPane hanya bisa menerima Array
        Object choiceInput = JOptionPane.showInputDialog(null, "Select Membership", "Select Membership", JOptionPane.PLAIN_MESSAGE, null, membershipOptions.toArray(), null);

        if (choiceInput == null) {
            // Matikan program jika user tidak memilih membership apapun
            JOptionPane.showMessageDialog(null, "You did not select any membership. Exiting...");
            return;
        }

        // Proses mendapatkan index membership relatif ke array membership dari membershipMap
        int membershipIndex = membershipMap.get(choiceInput);
        selectedMembership = memberships[membershipIndex];

        if (selectedMembership == null) {
            // Matikan program jika user tidak memilih membership apapun
            return;
        }

        // Menyimpan membership yang dipilih ke dalam objek Receipt
        Receipt receipt = new MembershipReceipt(fullName, email, selectedMembership);

        // Menampilkan receipt (struk)
        receipt.showReceipt();
    }

    /*method selectSuplement()
     * Berguna untuk memilih suplement
     */
    public static Suplement selectSuplement() {
        // Mengambil daftar suplement dari SuplementHelper (cek Suplement.java baris ke 45)
        Suplement[] suplements = SuplementHelper.getSuplements();

        // ArrayList suplementOptions untuk menyimpan daftar suplement yang akan ditampilkan
        ArrayList<String> suplementOptions = new ArrayList<String>();

        // HashMap suplementMap untuk menyimpan index dari suplementOptions relatif ke suplements array
        Map<String, Integer> suplementMap = new HashMap<>();

        // Inisialisasi suplement yang akan dipilih
        Suplement selectedSuplement = null;

        // Membangun teks dialog yang berisikan suplement dan (nama, deskripsi, stok, dan harga)
        String dialogMsg = "";
        for(int i = 0; i < suplements.length; i++) {
            dialogMsg += String.format("""
            %d. %s
            """, i + 1, suplements[i].getDetails());
            suplementOptions.add(suplements[i].name);
            suplementMap.put(suplements[i].name, i);
        }

        // Meminta user untuk memilih suplement
        Object choiceInput = JOptionPane.showInputDialog(null, dialogMsg, "Select Suplement", JOptionPane.PLAIN_MESSAGE, null, suplementOptions.toArray(), null);

        if (choiceInput == null) {
            // return null (akan di handle di prosedur suplement()) jika user tidak memilih suplement
            JOptionPane.showMessageDialog(null, "You did not select any suplement. Exiting...");
            return null;
        }

        // Mengambil index suplement
        int suplementIndex = suplementMap.get(choiceInput);
        // Memilih suplement dari array suplements berdasarkan index
        selectedSuplement = suplements[suplementIndex];

        // ArrayList untuk menyimpan pilihan varian setiap suplement
        ArrayList<String> variantOptions = new ArrayList<String>();

        // Mendambahkan setiap varian yang berada di suplement yang terpilih (selectedSuplement)
        // ke variantOptions
        for (int i = 0; i < selectedSuplement.variants.length; i++) {
            variantOptions.add(selectedSuplement.variants[i]);
        }

        // Meminta user untuk memilih varian suplement
        Object variantInput = JOptionPane.showInputDialog(null, "Select variant", "Select Suplement Variant", JOptionPane.PLAIN_MESSAGE, null, variantOptions.toArray(), null);


        if (variantInput == null) {
            // return null (akan di handle di prosedur suplement()) jika user tidak memilih varian suplement
            JOptionPane.showMessageDialog(null, "You did not select any variant. Exiting...");
            return null;
        }

        // Menetapkan varian yang dipilih user sebagai selectedVariant (atribut Suplement)
        selectedSuplement.selectedVariant = variantInput.toString();

        // Mengembalikan nilai selectedSuplement (suplement yang dipilih user)
        return selectedSuplement;
    }

    /*Prosedur suplement()
     * Berfungsi untuk memilih suplement dengan sistem cart (keranjang)
     * yang berguna agar user dapat memilih banyak suplement sekaligus
     */
    public static void suplement() {
        // Membuat cart dengan tipe data ArrayList yang akan menampung Suplement
        ArrayList<Suplement> cart = new ArrayList<Suplement>();

        // Indikasi jika user ingin memilih banyak suplement
        boolean multipleSuplements = false;
        
        // do while loop
        do {
            // Memilih suplement
            Suplement selectedSuplement = selectSuplement();

            if (selectedSuplement == null) {
                // Matikan program jika user tidak memilih suplement apapun
                System.exit(0);
            }
            
            // Meminta user untuk memilih kuantitas (jumlah suplement) yang akan dibeli
            while (true) {
                // Meminta user untuk memasukkan kuantitas
                String quantity = JOptionPane.showInputDialog(null, "Input quantity:", "Select Quantity", JOptionPane.PLAIN_MESSAGE);

                // Disini kita menggunakan try catch karena Integer.parseInt berpotensi
                // Menyebabkan Error (Exception). Ini terjadi jika user tidak memasukkan angka, namun huruf
                try { 
                    // mengubah quantity ke Integer
                    int quantityInt = Integer.parseInt(quantity);

                    // Cek jika kuantitas yang dimasukkan lebih dari 0 DAN kurang atau sama dengan dari stok
                    if (quantityInt > 0 && quantityInt <= selectedSuplement.stock) {
                        // Memasukkan kuantitas ke atribut selectedQuantity
                        selectedSuplement.selectedQuantity = quantityInt;
                        break;  // Keluar dari loop (baris ke 292)
                    } else {
                        // Menampilkan dialog error jika kondisi di atas tidak benar
                        JOptionPane.showMessageDialog(null, "Invalid quantity. Please try again.");
                    }
                } catch (Exception e) {
                    // Handle error (jika error terjadi di baris 300)
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                }
            }

            // Menanyakan user apakah mereka ingin membeli suplement lain
            int buyMore = JOptionPane.showConfirmDialog(null, "Do you want to buy other suplements?", "Choice", JOptionPane.YES_NO_OPTION);

            // Jika iya, membuat multipleSuplements bernilai true
            if (buyMore == JOptionPane.YES_OPTION) {
                multipleSuplements = true;
            // Jika tidak, membuat multipleSuplements bernilai false
            } else {
                multipleSuplements = false;
            }

            // Menambahkan suplement yang dipilih ke dalam cart
            cart.add(selectedSuplement);
            
            // Update stok suplement yang dipilih
            // (jumlah stok) - (kuantitas yang dipilih user)
            // selectedSuplement.stock = selectedSuplement.stock - selectedSuplement.selectedQuantity;
            selectedSuplement.stock -= selectedSuplement.selectedQuantity;

        } while(multipleSuplements); // loop akan berhenti jika multipleSuplements bernilai false

        // Membuat objek Receipt dengan menggunakan class CartReceipt
        Receipt receipt = new CartReceipt(fullName, email, cart);

        // Menampilkan receipt (struk)
        receipt.showReceipt();
    }

    // Prosedur bundle()
    // Berguna untuk memilih bundle, membership, dan suplement
    public static void bundle() {
        // Mengambil daftar budnle dari BundleHelper (cek Bundle.java baris ke 38)
        ArrayList<Bundle> bundles = BundleHelper.getBundles();

        // Initialisasi variabel bundle yang akan dipilih
        Bundle selectedBundle = null;

        // Membuat ArrayList untuk daftar pilihan bundle
        ArrayList<String> bundleOptions = new ArrayList<String>();

        // Membuat HashMap untuk mapping pilihan bundle dengan index yang berada di array bundles
        Map<String, Integer> bundleMap = new HashMap<>();
        
        // Membuat dialog untuk memilih bundle dengan setiap atribut bundle
        String dialogMsg = "";
        for(int i = 0; i < bundles.size(); i++) {
            dialogMsg += String.format("%d. %s", i + 1, bundles.get(i).getDetails());    
            bundleOptions.add(bundles.get(i).name);
            bundleMap.put(bundles.get(i).name, i);
        }

        // Meminta user untuk memilih bundle
        Object choiceInput = JOptionPane.showInputDialog(null, dialogMsg, "Select Bundle", JOptionPane.PLAIN_MESSAGE, null, bundleOptions.toArray(), null);

        if (choiceInput == null) {
            // Matikan program jika user tidak memilih bundle
            JOptionPane.showMessageDialog(null, "You did not select any bundle. Exiting...");
            System.exit(0);
        }

        int bundleIndex = bundleMap.get(choiceInput);
        selectedBundle = bundles.get(bundleIndex);

        // Memilih suplement untuk bundle
        Suplement selectedSuplement = selectSuplement();

        if (selectedSuplement == null) {
            // Matikan program jika user tidak memilih suplement
            System.exit(0);
        }

        selectedBundle.suplement = selectedSuplement;

        // Memilih PersonalTrainer
        PersonalTrainer selectedPersonalTrainer = selectPersonalTrainer();

        if (selectedPersonalTrainer == null) {
            // Matikan program jika user tidak memilih PersonalTrainer
            System.exit(0);
        }

        // Menetapkan personal trainer yang dipilih sebagai personal trainer dalam bundle
        selectedBundle.personalTrainer = selectedPersonalTrainer;

        // Membuat objek Receipt dengan menggunakan class BundleReceipt
        Receipt receipt = new BundleReceipt(fullName, email, selectedBundle);

        // Tampilkan receipt (struk)
        receipt.showReceipt();
    }

    // Main method, berguna untuk menjadi "pintu utama" program.
    public static void main(String[] args) {
        // Instances initialization
        // Ask for name and email

        // Disini kita menggunakan while loop untuk mengulang program hingga user menekan tombol cancel.
        while (true) {
            // Disini kita minta user memasukkan name.
            fullName = JOptionPane.showInputDialog(null, "WELCOME TO GYM EXPERT\nEnter your name:", "GYM EXPERT", JOptionPane.PLAIN_MESSAGE);

            // Cek jika nilai fullName adalah null, ini terjadi jika user menclick tombol close (x)
            if (fullName == null) {
                // Matikkan program
                System.exit(0);
            }

            // Disini kita meminta user memasukan email.
            email = JOptionPane.showInputDialog(null, "Enter your e-mail:", "GYM EXPERT", JOptionPane.PLAIN_MESSAGE);

            // Cek jika nilai email adalah null, ini terjadi jika user menclick tombol close (x)
            if (email == null) {
                // Matikkan program
                System.exit(0);
            }

            // Kita menggunakan IF statement untuk mengecek apakah user memasukkan name dan email.
            /*
             * Disini kita mencoba untuk memeriksa apakah fullName atau email empty
             * fullName atau email bisa empty ("") jika user menclick tombol OK 
             * walaupun user tidak memasukkan apapun ke input dialog JOptionPane
             */
            // Jika user tidak memasukkan name dan email,
            if (fullName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your name and e-mail.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            } else {
                // Jika kedua variable (fullName dan email) memiliki nilai (tidak kosong/empty)
                // Maka kita akan keluar dari loop untuk lanjut ke proses selanjutnya.
                break; // keluar loop (di baris 414) dan akan lanjut ke baris 449
            }
            // kita minta user memasukkan kembali name dan email. 
        }

        // KESINI GENGS
        // Disini kita meminta user untuk memilih pilihan
        // Pilihan tersebut diantaranya adalah
        /*
            Trial
            Personal Trainer
            Membership
            Suplement
            Bundle
         */
        /*
         * null: ParentComponent (abaikan)
         * "Select options below": Teks (string) untuk ditampilkan dalam dialog JOptionPane
         * JOptionPane.PLAIN_MESSAGE: Membuat dialog JOptionPane tidak menggunakan icon
         * null: Icon (abaikan)
         * new Object[] { "Trial", "Personal Trainer", "Membership", "Suplement", "Bundle" }
            * Pilihan (array Object) yang ditampilkan di tombol dropdown JOptionPane
         */
        Object choiceInput = JOptionPane.showInputDialog(null, "Select options below", "Select option", JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Trial", "Personal Trainer", "Membership", "Suplement", "Bundle"}, "Trial");
        
        // Jika user tidak memilih apapun, maka choiceInput bernilai null        
        // Kode di bawah ini sama seperti
        /*
         * if (choiceInput == null) {
             choice = "";
           } else {
             choice = choiceInput.toString();
           }
         */
        String choice = choiceInput != null ? choiceInput.toString() : "";

        // Disini kita menggunakan switch case untuk melakukan percabangan logika
        switch (choice) {
            // Jika user memilih Trial, maka kita akan memanggil prosedur trialPass()
            case "Trial":
                trialPass();
                break;
            
            // Jika user memilih Personal Trainer, maka kita akan memanggil prosedur 
            // personalTrainer()
            case "Personal Trainer":
                personalTrainer();
                break;
            
            // Jika user memilih Membership, maka kita akan memanggil prosedur
            // membership()
            case "Membership":
                membership();
                break;
            
            // Jika user memilih Suplement, maka kita akan memanggil prosedur
            // suplement()
            case "Suplement":
                suplement();
                break;
            
            // Jika user memilih Bundle, maka kita akan memanggil prosedur
            // bundle()
            case "Bundle":
                bundle();
                break;
            
            // Jika user tidak memilih apapun, maka akan keluar dari program
            default:
                JOptionPane.showMessageDialog(null, "You did not select any option. Exiting...");
                break;
        }
    }
}
