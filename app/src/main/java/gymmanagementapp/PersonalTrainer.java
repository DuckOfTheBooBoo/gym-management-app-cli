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