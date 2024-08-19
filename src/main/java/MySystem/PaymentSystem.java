package MySystem;

import java.util.HashMap;
import java.util.Map;

public class PaymentSystem {
    private Map<String, Double> userBalances;

    public PaymentSystem() {
        this.userBalances = new HashMap<>();
    }

    public void addFunds(String username, double amount) {
        userBalances.put(username, userBalances.getOrDefault(username, 0.0) + amount);
    }

    public boolean processPayment(String buyerUsername, String sellerUsername, double amount) {
        if (!userBalances.containsKey(buyerUsername) || userBalances.get(buyerUsername) < amount) {
            return false;
        }

        userBalances.put(buyerUsername, userBalances.get(buyerUsername) - amount);
        userBalances.put(sellerUsername, userBalances.getOrDefault(sellerUsername, 0.0) + amount);
        return true;
    }

    public double getBalance(String username) {
        return userBalances.getOrDefault(username, 0.0);
    }
}