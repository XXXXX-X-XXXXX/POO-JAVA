public class NonAlcoholicCocktail extends Cocktail {
    private boolean organic;
    private String nutritionalBenefit;

    public NonAlcoholicCocktail(String name,
                                double price,
                                int preparationTime,
                                boolean organic,
                                int stock) {
        super(name, price, preparationTime, stock); // stock = stock initial
        this.organic = organic;
        this.nutritionalBenefit = "Rich in vitamins";
    }

    @Override
    public boolean isAlcoholic() {
        return false;
    }

    @Override
    public boolean isOrganic() {
        return organic;
    }

    @Override
    public String getDescription() {
        String organicTag = organic ? " [ORGANIC]" : " [NON-ORGANIC]";
        return name + organicTag
                + " (Non-alcoholic) - " + price + "€"
                + " - Prep: " + preparationTime + "s"
                + " - Stock: " + getStock();
    }

    public String getNutritionalBenefit() {
        return nutritionalBenefit;
    }
}
