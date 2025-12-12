package CHATEAU;

public abstract class Personnage {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Personnage(String name) {
        this.name = name;
    }
}
