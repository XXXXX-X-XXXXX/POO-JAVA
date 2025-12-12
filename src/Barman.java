// Représente un barman dans le bar
public class Barman extends Employee {

    // Constructeur du barman
    public Barman(String name, int speed, int quality) {
        super(name, speed, quality);
    }

    // Décrit les tâches principales du barman
    @Override
    public void doTasks() {
        System.out.println(getName() + " is preparing cocktails at the bar.");
    }
}