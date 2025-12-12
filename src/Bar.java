import java.util.ArrayList;
import java.util.List;

public class Bar {

    private List<Drink> menu;

    public Bar() {
        menu = new ArrayList<>();
        createDrinks();
    }

    private void createDrinks() {
        // Soft drinks
        menu.add(new SoftDrink("Coca-Cola", 3.0, false, 20));   // 20 en stock
        menu.add(new SoftDrink("Orange Juice", 3.5, true, 15)); // 15 en stock

        // Alcoholic cocktails
        menu.add(new AlcoholicCocktail("Mojito", 8.0, 120, 14.0, true, 10));    // 10 en stock
        menu.add(new AlcoholicCocktail("Margarita", 9.0, 150, 18.0, false, 8)); // 8 en stock

        // Non-alcoholic cocktails
        menu.add(new NonAlcoholicCocktail("Virgin Mojito", 6.0, 90, true, 12)); // 12 en stock
        menu.add(new NonAlcoholicCocktail("Fruit Punch", 5.5, 60, false, 9));   // 9 en stock
    }

    public void printMenu() {
        for (Drink drink : menu) {
            System.out.println(drink.getDescription());
        }
    }

    public static void main(String[] args) {
        Bar bar = new Bar();
        bar.printMenu();
    }
}
