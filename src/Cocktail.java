import java.util.HashMap;
import java.util.Map;

public abstract class Cocktail extends Drink {
    protected Map<SoftDrink, Integer> ingredients; // nécessaire pour le stock ingrédients
    protected int preparationTime;

    // AJOUT: initialStock (combien de cocktails dispo)
    public Cocktail(String name, double price, int preparationTime, int initialStock) {
        super(name, price, initialStock);
        this.preparationTime = preparationTime;
        this.ingredients = new HashMap<>();
    }

    public void addIngredient(SoftDrink drink, int quantityCl) {
        ingredients.put(drink, quantityCl);
    }

    public Map<SoftDrink, Integer> getIngredients() {
        return ingredients;
    }

    public int getPreparationTime() {
        return preparationTime;
    }
}
