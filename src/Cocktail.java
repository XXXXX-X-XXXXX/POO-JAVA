import java.util.HashMap;
import java.util.Map;

public abstract class Cocktail extends Drink {
    protected Map<SoftDrink, Integer> ingredients; // ← NÉCESSAIRE pour le stock
    protected int preparationTime;

    public Cocktail(String name, double price, int preparationTime) {
        super(name, price);
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
