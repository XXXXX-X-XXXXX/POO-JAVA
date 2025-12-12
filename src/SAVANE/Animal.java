package SAVANE;

public abstract class Animal  {
    private String name;
    private int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void dormir() {
        IO.println("ZZZZzzzzZZZZZZ!!!!!!");
    }

    public  void  ToString() {
        IO.println("Nom  : " + this.name);
        IO.println("Âge  : " + this.age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
