public abstract class Drink {
    protected String name;
    protected double price;

    // STOCK
    protected int stock;
    protected final int initialStock;

    public Drink(String name, double price, int initialStock) {
        this.name = name;
        this.price = price;
        this.initialStock = initialStock;
        this.stock = initialStock;
    }

    public abstract String getDescription();
    public abstract boolean isAlcoholic();
    public abstract boolean isOrganic();

    public String getName() { return name; }
    public double getPrice() { return price; }

    // getters stock
    public int getStock() { return stock; }
    public int getInitialStock() { return initialStock; }

    // actions stock
    public void decrementStock() {
        if (stock > 0) stock--;
    }

    public void restock() {
        stock = initialStock;
    }
}
