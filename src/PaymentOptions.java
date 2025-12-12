// Interface représentant un moyen de paiement
public interface PaymentOptions {

    // Vérifier si le paiement peut être effectué pour un certain montant
    boolean canPay(double amount);

    // Effectuer le paiement pour un certain montant
    void pay(double amount);
}