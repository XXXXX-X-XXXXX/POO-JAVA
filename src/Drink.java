public abstract class Drink {
    protected String name;
    protected double price;

    public Drink(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public abstract String getDescription();

    public String getName() { return name; }
    public double getPrice() { return price; }
}
