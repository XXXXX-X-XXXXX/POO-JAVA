// Représente un serveur dans le bar
public class Waiter extends Employee {




    // Constructeur du serveur
    public Waiter(String name, int speed, int quality) {
        super(name, speed, quality);
    }

    // Décrit les tâches principales du serveur
    @Override
    public void doTasks() {
        System.out.println(getName() + " is serving customers at the tables.");
    }
}