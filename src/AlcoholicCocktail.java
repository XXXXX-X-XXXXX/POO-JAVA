public class AlcoholicCocktail extends Cocktail {
    private double alcoholContent;
    private boolean organic; // true = bio, false = non bio
    private int stock;       // nombre de cocktails disponibles
    private static final int MINIMUM_AGE = 18;

    public AlcoholicCocktail(String name,
                             double price,
                             int preparationTime,
                             double alcoholContent,
                             boolean organic,
                             int stock) {
        super(name, price, preparationTime);
        this.alcoholContent = alcoholContent;
        this.organic = organic;
        this.stock = stock;
    }

    @Override
    public String getDescription() {
        String organicTag = organic ? " [ORGANIC]" : " [NON-ORGANIC]";
        return name + organicTag
                + " (Alcoholic " + alcoholContent + "°)"
                + " - " + price + "€"
                + " - Prep: " + preparationTime + "s"
                + " - Stock: " + stock;
    }

    public boolean canBeServedTo(int clientAge) {
        return clientAge >= MINIMUM_AGE;
    }

    public double getAlcoholContent() {
        return alcoholContent;
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
