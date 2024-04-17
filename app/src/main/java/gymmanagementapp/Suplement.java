package gymmanagementapp;

import java.util.*;
import javax.swing.JOptionPane;

public class Suplement {
    public String name = "";
    public String description = "";
    public String[] variants;
    public String selectedVariant = null;
    public int selectedQuantity = 0;
    public int price = 0;
    public int stock = 0;

    public Suplement(String name, String description, String[] variants, int price, int stock) {
        this.name = name;
        this.description = description;
        this.variants = variants;
        this.price = price;
        this.stock = stock;
    }

    public String getDetails() {
        return String.format("""
                %s
                  %s
                  Variants: %s
                  Rp. %d
                  Stock: %d
                """, this.name, this.description, Arrays.toString(this.variants), this.price, this.stock);
    }
}

class SuplementHelper {
    private static Suplement[] suplements = {
        new Suplement("Pro Garnier 900gr", "Pro Garnier adalah suplemen mass garnier untuk meningkatkan berat badan\n    yang sehat dan berkontribusi pada pertumbuhan massa otot.", new String[] {"Glame Chocolate", "Caramel Fusion", "Honeydew Melon", "Choco Mint", "Vanilla Popcorn"}, 245000, 12),
        new Suplement("Pro Whey100 900gr", "Pro Whey100 adalah whey protein powder yang mengandung 24g protein, 140 kalori, 1g lemak,\n    dan menggunakan pemanis alami daun stevia.", new String[] {"Coklat", "Vanilla Kurma Madu", "Stroberi"}, 294000, 10),
        new Suplement("Pro Isolate 900gr", "Pro Isolate adalah Whey proetin murni dengan daya serap 90% yang mampu dengan efektif\n    menambah asupan protein harian dengan 27g Protein, 140 Kalori, 1g Gula, & 0 fat/sajian.", new String[] {"Chocolate Soul", "Taro Velvet", "Banana Coffe", "Strawberry Parfait", "Honey Banana"}, 427000, 10),
        new Suplement("Pro Creatine 420gr", "Pro Creatine merupakan suplemen creatine yang diformulasikan khusu untuk membantu\n    meningkatkan kekuatan & daya tahan otot serta meningkatkan Volume otot secara maksimal.", new String[] {"Bubblegum", "Mixberry"}, 259000, 12),
        new Suplement("Pro Isolate 900gr + Pro Garnier 900gr (Combo Deals)", "Varian Combo Deals ini sangat cocok bagi yang ingin Bulking, karena menyerap daya 90% yang\n    mampu menambah asupan protein harian membuat pertumbuhan otot semakin sempurna.", new String[] {"Caramel Fusion", "Glame Chocolate", "Honeydew Melon", "Vanilla Popcorn", "Choco Mint"}, 672000, 7),
    };
    
    public static Suplement[] getSuplements() {
        return suplements;
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

    public static void spProcedure(String fullName, String email) {
        ArrayList<Suplement> cart = new ArrayList<Suplement>();
        boolean multipleSuplements = false;
        
        
        do {
            Suplement selectedSuplement = selectSuplement();

            if (selectedSuplement == null) {
                return;
            }
            
            // Select quantity
            while (true) {
                String quantity = JOptionPane.showInputDialog(null, "Input quantity: (available stock: " + selectedSuplement.stock + ")", "Select Quantity", JOptionPane.PLAIN_MESSAGE);

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
}