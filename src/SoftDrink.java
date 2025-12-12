public class SoftDrink extends Drink {
    private boolean organic; // true = bio, false = non bio
    private int stock;       // nombre de boissons en stock

    public SoftDrink(String name, double price, boolean organic, int stock) {
        super(name, price);
        this.organic = organic;
        this.stock = stock;
    }

    @Override
    public String getDescription() {
        String organicTag = organic ? " [ORGANIC]" : " [NON-ORGANIC]";
        return name + organicTag + " - " + price + "€ - Stock: " + stock;
    }

    public boolean isOrganic() {
        return organic;
    }

    public int getStock() {
        return stock;
    }

    public void decreaseStock(int quantity) {
        stock -= quantity;
        if (stock < 0) {
            stock = 0;
        }
    }
}
