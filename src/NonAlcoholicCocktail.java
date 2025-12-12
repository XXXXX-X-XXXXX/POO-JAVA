public class NonAlcoholicCocktail extends Cocktail {
    private boolean organic;
    private String nutritionalBenefit;
    private int stock; // nombre de cocktails en stock

    public NonAlcoholicCocktail(String name,
                                double price,
                                int preparationTime,
                                boolean organic,
                                int stock) {
        super(name, price, preparationTime);
        this.organic = organic;
        this.nutritionalBenefit = "Rich in vitamins";
        this.stock = stock;
    }

    @Override
    public String getDescription() {
        String organicTag = organic ? " [ORGANIC]" : " [NON-ORGANIC]";
        return name + organicTag
                + " (Non-alcoholic) - " + price + "€"
                + " - Prep: " + preparationTime + "s"
                + " - Stock: " + stock;
    }

    public boolean isOrganic() {
        return organic;
    }

    public String getNutritionalBenefit() {
        return nutritionalBenefit;
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
