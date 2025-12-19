public class AlcoholicCocktail extends Cocktail {
    private double alcoholContent;
    private boolean organic; // true = bio, false = non bio
    private static final int MINIMUM_AGE = 18;

    public AlcoholicCocktail(String name,
                             double price,
                             int preparationTime,
                             double alcoholContent,
                             boolean organic,
                             int stock) {
        super(name, price, preparationTime, stock); // stock = stock initial
        this.alcoholContent = alcoholContent;
        this.organic = organic;
    }

    @Override
    public boolean isAlcoholic() {
        return true;
    }

    @Override
    public boolean isOrganic() {
        return organic;
    }

    @Override
    public String getDescription() {
        String organicTag = organic ? " [ORGANIC]" : " [NON-ORGANIC]";
        return name + organicTag
                + " (Alcoholic " + alcoholContent + "°)"
                + " - " + price + "€"
                + " - Prep: " + preparationTime + "s"
                + " - Stock: " + getStock();
    }

    public boolean canBeServedTo(int clientAge) {
        return clientAge >= MINIMUM_AGE;
    }

    public double getAlcoholContent() {
        return alcoholContent;
    }
}
