import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Bienvenue au Bar Java ===");

        // Saisie du nom
        System.out.print("Entrez votre nom : ");
        String name = scanner.nextLine();

        // Saisie de l'âge
        System.out.print("Entrez votre âge : ");
        int age = scanner.nextInt();

        // Création du client
        Client client = new Client(name, age);
        System.out.println("\nBonjour " + client.getName() + ", âge " + client.getAge() + ".");

        // Création du bar (menu + staff)
        Bar bar = new Bar();

        System.out.println("\n--- Menu du bar ---");
        bar.printMenu();

        System.out.println("\n--- Employés du bar ---");
        bar.printStaff();

        // Exemple : test de paiement
        System.out.println("\nTest de paiement : vous tentez de payer 10€.");
        boolean paid = client.pay(10.0);
        System.out.println("Paiement réussi ? " + paid);

        // Tu peux enlever scanner.close() si tu prévois d'autres lectures plus tard dans le programme
        scanner.close();
    }
}
