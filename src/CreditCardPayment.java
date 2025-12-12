// Exemple de moyen de paiement par carte
public class CreditCardPayment implements PaymentOptions {

    // Solde disponible sur la carte (exemple simplifié)
    private double balance;

    public CreditCardPayment(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean canPay(double amount) {
        return balance >= amount;
    }

    @Override
    public void pay(double amount) {
        if (canPay(amount)) {
            balance -= amount;
            System.out.println("Payment of " + amount + " completed by card. Remaining balance: " + balance);
        } else {
            System.out.println("Payment failed: not enough balance on the card.");
        }
    }

    public double getBalance() {
        return balance;
    }
}