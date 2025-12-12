import java.util.ArrayList;
import java.util.List;

public class Bar {

    private List<Drink> menu;
    private List<Employee> staff;

    public Bar() {
        menu = new ArrayList<>();
        staff = new ArrayList<>();
        createDrinks();
        createStaff();
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

    private void createStaff() {
        staff.add(new Barman("John", 8, 9));
        staff.add(new Waiter("Emma", 7, 8));
        staff.add(new Cleaner("Paul", 6, 9));
        // Tu peux ajouter ton Bouncer plus tard si tu veux :
        // staff.add(new Bouncer("Mike", 7, 7));
    }

    public void printMenu() {
        System.out.println("=== MENU ===");
        for (Drink drink : menu) {
            System.out.println(drink.getDescription());
        }
    }

    public void printStaff() {
        System.out.println("\n=== STAFF ===");
        for (Employee e : staff) {
            System.out.println(e);           // utilise Employee.toString()
            e.doTasks();               // montre le travail de chacun
        }
    }

    public static void main(String[] args) {
        Bar bar = new Bar();
        bar.printMenu();
        bar.printStaff();
    }
}
