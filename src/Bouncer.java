// Représente un videur du bar
public class Bouncer extends Employee {

    // Constructeur du videur
    public Bouncer(String name, int speed, int quality) {
        super(name, speed, quality);
    }

    // Tâches principales du videur
    @Override
    public void doTasks() {
        System.out.println(getName() + " is guarding the entrance of the bar.");
    }

    // Vérifie l'identité d'un client (ici, on se base sur l'âge)
    // Méthode spécifique à Bouncer (pas d'@Override)
    public void checkIdentity(Client client) {
        if (client.isAdult()) {
            System.out.println(getName() + " checked " + client.getName() + "'s ID: access granted.");
        } else {
            System.out.println(getName() + " checked " + client.getName() + "'s ID: access denied (underage).");
        }
    }

    // Méthode spécifique à Bouncer (pas d'@Override)
    public void denyEntry(Client client) {
        System.out.println(getName() + " is denying entry to " + client.getName() + ".");
    }
}