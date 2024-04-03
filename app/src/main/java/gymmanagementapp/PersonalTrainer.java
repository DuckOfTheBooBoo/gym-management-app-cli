package gymmanagementapp;

public class PersonalTrainer {
    public String name;
    public int fee;
    public int rating;

    public PersonalTrainer(String name, int fee, int rating) {
        this.name = name;
        this.fee = fee;
        this.rating = rating;
    }

    public static PersonalTrainer[] getPersonalTrainers() {
        return new PersonalTrainer[] {
            new PersonalTrainer("Bambang Supraptno", 56000, 4),
            new PersonalTrainer("Agus Agus", 78000, 5),
            new PersonalTrainer("Latifah Diyahh", 69900, 10),
            new PersonalTrainer("Tecchi Agustina", 80000, 10),
            new PersonalTrainer("David Ferguson", 50000, 4)
        };
    }
}