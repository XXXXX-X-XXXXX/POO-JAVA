package SAVANE;

public class Serpent implements Mouvement {

    private String name;
    private int age;

    public Serpent(String name,int age){
        this.name = name;
        this.age = age;
    }


    @Override
    public void afficherMoyen() {
        IO.println("le Serpet rampe");
    }
}
