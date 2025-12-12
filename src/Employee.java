// Classe abstraite représentant un employé générique du bar
public abstract class Employee implements EmployeeRole {

    // Nom complet de l'employé
    private String name;

    // Vitesse de travail (par exemple de 1 à 10)
    private int speed;

    // Qualité de travail (par exemple de 1 à 10)
    private int quality;

    // Constructeur principal
    public Employee(String name, int speed, int quality) {
        this.name = name;
        this.speed = speed;
        this.quality = quality;
    }

    // Implémentation de getName() de l'interface
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    // doTasks() reste abstraite, chaque type d'employé la définit
    @Override
    public abstract void doTasks();

    @Override
    public String toString() {
        return "Employee{name='" + name + '\'' +
                ", speed=" + speed +
                ", quality=" + quality +
                '}';
    }
}
