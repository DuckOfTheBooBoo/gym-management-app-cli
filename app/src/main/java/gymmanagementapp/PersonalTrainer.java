package gymmanagementapp;

import java.util.Date;
import java.text.SimpleDateFormat;

public class PersonalTrainer {
    public String name;
    public int fee;
    public int rating;
    public Date date;

    public PersonalTrainer(String name, int fee, int rating) {
        this.name = name;
        this.fee = fee;
        this.rating = rating;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Ini mengubah tipe data Date menjadi string dd/MM/yyyy (hari/bulan/tahun)
    // Sejatinya, tipe data Date bukanlah objek yang bisa direpresentasikan secara string
    // Date bukanlah String dan sebaliknya. Date merupakan tipe data yang terpisah; berbeda dengan String. Namun sebuah tanggal (Date) dapat direpresentasikan ke dalam String dengan bentuk (hari/bulan/tahun)
    // Jadi, cara kita menggunakan sebuah tanggal di dalam Java adalah dengan menggunakan tipe data Date. Namun, dalam Java, tipe data Date bukanlah String; sebuah objek teks.
    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(this.date);
    }

    public String getDetails() {
        return String.format("""
                %s
                    Rating: %d
                    Fee: Rp. %d/session
                """, this.name, this.rating, this.fee);
    }
}

class PersonalTrainerHelper {
    private static PersonalTrainer[] personalTrainers = {
        new PersonalTrainer("Bambang Supraptno", 56000, 4),
        new PersonalTrainer("Agus Agus", 78000, 5),
        new PersonalTrainer("Latifah Diyahh", 69900, 10),
        new PersonalTrainer("Tecchi Agustina", 80000, 10),
        new PersonalTrainer("David Ferguson", 50000, 4)
    };

    public static PersonalTrainer[] getPersonalTrainers() {
        return personalTrainers;
    }
}