package gymmanagementapp;

import javax.swing.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class App {
    private static String fullName = null;
    private static String email = null;
    
    // Function ini berguna untuk menerima input tanggal
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

    // Kesini gengs
    public static void trialPass() {
        Date date = null;
        int price = 32000;
        
        // Ini teks yang akan ditampilkan di dialog swing JOptionPane
        String trialDialogMsg = "Trial Price: Rp. 32.000\nEnter date (dd/MM/yyyy):";

        date = dateInput(trialDialogMsg, "Select Trial Date");

        Receipt receipt = new TrialReceipt(fullName, email, date, price);
        receipt.showReceipt();
    }

    public static PersonalTrainer selectPersonalTrainer() {
        PersonalTrainer[] trainers = PersonalTrainerHelper.getPersonalTrainers();
        PersonalTrainer selectedTrainer = null;
        Map<String, Integer> trainerMap = new HashMap<String, Integer>();


        // Construct menu string
        String pTDialogMsg = "";
        for (int i = 0; i < trainers.length; i++) {
            trainerMap.put(trainers[i].name, i + 1);
            pTDialogMsg += String.format("%d. %s", i + 1, trainers[i].getDetails());
        }

        for (int i = 0; i < trainers.length; i++) {
            trainerMap.put(trainers[i].name, i);
        }

        Object ptChoiceInput = JOptionPane.showInputDialog(null, pTDialogMsg, "Select Personal Trainer", JOptionPane.PLAIN_MESSAGE, null, trainerMap.keySet().toArray(), 1);

        if (ptChoiceInput == null) {
            JOptionPane.showMessageDialog(null, "You did not select any personal trainers. Exiting...");
            return null;
        }

        int trainerIndex = trainerMap.get(ptChoiceInput);
        selectedTrainer = trainers[trainerIndex];
        Date date = dateInput("Input personal trainer date (dd/MM/yyyy):", "Personal Trainer Date");
        selectedTrainer.setDate(date);

        return selectedTrainer;
    }

    public static void personalTrainer() {
        PersonalTrainer selectedTrainer = selectPersonalTrainer();

        Receipt receipt = new PersonalTrainerReceipt(fullName, email, selectedTrainer);
        receipt.showReceipt();
    }    

    public static void membership() {
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

    public static Suplement selectSuplement() {
        Suplement[] suplements = SuplementHelper.getSuplements();
        ArrayList<String> suplementOptions = new ArrayList<String>();
        Map<String, Integer> suplementMap = new HashMap<>();
        Suplement selectedSuplement = null;
        String dialogMsg = "";

        // Construct dialog message
        for(int i = 0; i < suplements.length; i++) {
            dialogMsg += String.format("""
            %d. %s
            """, i + 1, suplements[i].getDetails());
            suplementOptions.add(suplements[i].name);
            suplementMap.put(suplements[i].name, i);
        }

        Object choiceInput = JOptionPane.showInputDialog(null, dialogMsg, "Select Suplement", JOptionPane.PLAIN_MESSAGE, null, suplementOptions.toArray(), null);

        if (choiceInput == null) {
            JOptionPane.showMessageDialog(null, "You did not select any suplement. Exiting...");
            return null;
        }

        int suplementIndex = suplementMap.get(choiceInput);
        selectedSuplement = suplements[suplementIndex];

        // Select variants
        // String variantDialogMsg = "";
        ArrayList<String> variantOptions = new ArrayList<String>();

        // Construct variant msg
        for (int i = 0; i < selectedSuplement.variants.length; i++) {
            variantOptions.add(selectedSuplement.variants[i]);
        }

        Object variantInput = JOptionPane.showInputDialog(null, "Select variant", "Select Suplement Variant", JOptionPane.PLAIN_MESSAGE, null, variantOptions.toArray(), null);

        if (variantInput == null) {
            JOptionPane.showMessageDialog(null, "You did not select any variant. Exiting...");
            return null;
        }

        selectedSuplement.selectedVariant = variantInput.toString();

        return selectedSuplement;
    }

    public static void suplement() {
        ArrayList<Suplement> cart = new ArrayList<Suplement>();
        boolean multipleSuplements = false;
        
        
        do {
            Suplement selectedSuplement = selectSuplement();

            if (selectedSuplement == null) {
                return;
            }
            
            // Select quantity
            while (true) {
                String quantity = JOptionPane.showInputDialog(null, "Input quantity:", "Select Quantity", JOptionPane.PLAIN_MESSAGE);

                try {
                    int quantityInt = Integer.parseInt(quantity);
                    if (quantityInt > 0 && quantityInt <= selectedSuplement.stock) {
                        selectedSuplement.selectedQuantity = quantityInt;
                        break;  
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid quantity. Please try again.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                }
            }

            int buyMore = JOptionPane.showConfirmDialog(null, "Do you want to buy other suplements?", "Choice", JOptionPane.YES_NO_OPTION);

            if (buyMore == JOptionPane.YES_OPTION) {
                multipleSuplements = true;
            } else {
                multipleSuplements = false;
            }

            cart.add(selectedSuplement);
            
            // Update Suplements stock array
            selectedSuplement.stock -= selectedSuplement.selectedQuantity;

        } while(multipleSuplements);

        Receipt receipt = new CartReceipt(fullName, email, cart);
        receipt.showReceipt();
    }

    public static void bundle() {
        ArrayList<Bundle> bundles = BundleHelper.getBundles();
        Bundle selectedBundle = null;
        ArrayList<String> bundleOptions = new ArrayList<String>();
        Map<String, Integer> bundleMap = new HashMap<>();
        
        String dialogMsg = "";

        for(int i = 0; i < bundles.size(); i++) {
            dialogMsg += String.format("%d. %s", i + 1, bundles.get(i).getDetails());    
            bundleOptions.add(bundles.get(i).name);
            bundleMap.put(bundles.get(i).name, i);
        }

        Object choiceInput = JOptionPane.showInputDialog(null, dialogMsg, "Select Bundle", JOptionPane.PLAIN_MESSAGE, null, bundleOptions.toArray(), null);

        if (choiceInput == null) {
            JOptionPane.showMessageDialog(null, "You did not select any bundle. Exiting...");
            return;
        }

        int bundleIndex = bundleMap.get(choiceInput);
        selectedBundle = bundles.get(bundleIndex);

        // Choose suplement
        Suplement selectedSuplement = selectSuplement();

        if (selectedSuplement == null) {
            return;
        }

        selectedBundle.suplement = selectedSuplement;

        // Choose Personal trainer
        PersonalTrainer selectedPersonalTrainer = selectPersonalTrainer();

        if (selectedPersonalTrainer == null) {
            return;
        }

        selectedBundle.personalTrainer = selectedPersonalTrainer;
        Receipt receipt = new BundleReceipt(fullName, email, selectedBundle);
        receipt.showReceipt();
    }

    public static void main(String[] args) {
        // Instances initialization
        // Ask for name and email

        // Disini kita menggunakan while loop untuk mengulang program hingga user menekan tombol cancel.
        while (true) {
            // Disini kita minta user memasukkan name.
            fullName = JOptionPane.showInputDialog(null, "WELCOME TO GYM EXPERT\nEnter your name:", "GYM EXPERT", JOptionPane.PLAIN_MESSAGE);

            if (fullName == null) {
                System.exit(0);
            }

            // Disini kita meminta user memasukan email.
            email = JOptionPane.showInputDialog(null, "Enter your e-mail:", "GYM EXPERT", JOptionPane.PLAIN_MESSAGE);

            if (email == null) {
                System.exit(0);
            }

            // Kita menggunakan IF statement untuk mengecek apakah user memasukkan name dan email.
            // Disini kita mengecek jika fullName adalah null atau email adalah null
            // fullName dan email bisa memiliki nilai null jika pada dialog, kita click cancel
            // atau tidak memasukkan input apapun.
            // if ((fullName == null || fullName.isEmpty()) || (email == null || email.isEmpty())) {
            if (fullName.isEmpty() || email.isEmpty()) {
                // Jika user tidak memasukkan name dan email,
                // kita minta user memasukkan kembali name dan email.
                JOptionPane.showMessageDialog(null, "Please enter your name and e-mail.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            } else {
                // Jika kedua variable (fullName dan email) memiliki nilai bukan null
                // Maka kita akan keluar dari loop untuk lanjut ke proses selanjutnya.
                break;
            }
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
        Object choiceInput = JOptionPane.showInputDialog(null, "Select options below", "Select option", JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Trial", "Personal Trainer", "Membership", "Suplement", "Bundle"}, "Trial");

        // Jika user tidak memilih apapun, maka choiceInput bernilai null        
        // Kode ini sama seperti
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
