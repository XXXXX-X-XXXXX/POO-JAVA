package SAVANE;

public class Oiseaux implements Mouvement {

    private String name;
    private int age;

    public Oiseaux(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void afficherMoyen() {
        IO.println("l'oiseau vole");
    }
}
