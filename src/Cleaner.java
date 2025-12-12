// Représente le personnel de ménage du bar
public class Cleaner extends Employee {

    // Constructeur du personnel de ménage
    public Cleaner(String name, int speed, int quality)
    {
        super(name, speed, quality);
    }

    // Décrit les tâches principales du personnel de ménage
    @Override
    public void doTasks() {
        System.out.println(getName() + " is cleaning the bar area.");
    }
}