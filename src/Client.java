// Représente un client du bar (le joueur)
public class Client {

    // Nom du client
    private String name;

    // Âge du client
    private int age;

    // Moyen de paiement du client
    private PaymentOptions paymentOptions;

    // Constructeur principal : le client a automatiquement 50€ sur sa carte
    public Client(String name, int age) {
        this.name = name;
        this.age = age;
        this.paymentOptions = new CreditCardPayment(50.0); // carte avec 50€
    }

    // Nom du client
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Âge du client
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PaymentOptions getPaymentOptions() {
        return paymentOptions;
    }

    // Optionnel : changer de moyen de paiement plus tard
    public void setPaymentOptions(PaymentOptions paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    // Vérifie si le client est majeur (18+)
    public boolean isAdult() {
        return age >= 18;
    }

    // Vérifie s'il peut commander de l'alcool
    public boolean canOrderAlcohol() {
        return isAdult();
    }

    // Tente de payer un montant donné
    public boolean pay(double amount) {
        if (paymentOptions == null) {
            System.out.println(name + " has no payment method.");
            return false;
        }

        if (!paymentOptions.canPay(amount)) {
            System.out.println(name + " cannot pay " + amount + ".");
            return false;
        }

        paymentOptions.pay(amount);
        return true;
    }

    @Override
    public String toString() {
        return "Client{name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
