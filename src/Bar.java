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
        menu.add(new SoftDrink("Eau", 1.00, false, 10));
        menu.add(new SoftDrink("Coca-Cola Cherry", 3.0, false, 1));//  stock 10


        // Alcoholic cocktails
        menu.add(new AlcoholicCocktail("Mojito", 8.0, 120, 14.0, true, 10));    // 10 en stock
        menu.add(new AlcoholicCocktail("Margarita", 9.0, 150, 18.0, false, 8)); // 8 en stock

        // Non-alcoholic cocktails
        menu.add(new NonAlcoholicCocktail("Virgin Mojito", 6.0, 90, true, 12)); // 12 en stock
        menu.add(new NonAlcoholicCocktail("Fruit Punch", 5.5, 60, false, 9));   // 9 en stock
        menu.add(new SoftDrink("CasiTail", 3.0, false, 1));
    }

    private void createStaff() {
        staff.add(new Barman("Lucas", 8, 9));
        staff.add(new Waiter("Jade", 7, 8));
        staff.add(new Waiter("Mathilde", 9, 6));
        staff.add(new Waiter("Stagiaire", 5, 1));
        staff.add(new Bouncer("Mamad", 7, 9));
        staff.add(new Bouncer("Abdel", 7, 2));

        staff.add(new Cleaner("Clement", 6, 9));
        // Tu peux ajouter ton Bouncer plus tard si tu veux :

    }

    // Méthode pour récupérer le menu complet
    public List<Drink> getMenu() {
        return menu;
    }

    // Méthode pour récupérer un serveur (Waiter)
    public Employee getWaiter() {
        // Retourne le premier Waiter trouvé dans le staff
        for (Employee e : staff) {
            if (e instanceof Waiter) {
                return e;
            }
        }
        return null;
    }

    // Méthode pour récupérer la liste du staff
    public List<Employee> getStaff() {
        return staff;
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
