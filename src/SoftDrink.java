public class SoftDrink extends Drink {
    private boolean organic; // true = bio, false = non bio

    public SoftDrink(String name, double price, boolean organic, int stock) {
        super(name, price, stock); // stock = stock initial
        this.organic = organic;
    }

    @Override
    public boolean isAlcoholic() { return false; }

    @Override
    public boolean isOrganic() { return organic; }

    @Override
    public String getDescription() {
        String organicTag = organic ? " [ORGANIC]" : " [NON-ORGANIC]";
        return name + organicTag + " - " + price + "€ - Stock: " + getStock();
    }
}
