import java.util.HashMap;

public class User {

    private String username;
    private double balance;

    // Portfolio: Stock Symbol -> Quantity
    private HashMap<String, Integer> portfolio;

    // Constructor
    public User(String username, double balance) {
        this.username = username;
        this.balance = balance;
        portfolio = new HashMap<>();
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    // Add money
    public void deposit(double amount) {
        balance += amount;
    }

    // Deduct money
    public void withdraw(double amount) {
        balance -= amount;
    }

    // Buy Stock
    public void buyStock(String symbol, int quantity, double stockPrice) {

        double totalCost = quantity * stockPrice;

        if (balance >= totalCost) {

            balance -= totalCost;

            portfolio.put(
                    symbol,
                    portfolio.getOrDefault(symbol, 0) + quantity
            );

            System.out.println("Successfully bought "
                    + quantity + " shares of " + symbol);

        } else {
            System.out.println("Insufficient Balance!");
        }
    }

    // Sell Stock
    public void sellStock(String symbol, int quantity, double stockPrice) {

        int ownedShares = portfolio.getOrDefault(symbol, 0);

        if (ownedShares >= quantity) {

            portfolio.put(symbol, ownedShares - quantity);

            balance += quantity * stockPrice;

            System.out.println("Successfully sold "
                    + quantity + " shares of " + symbol);

        } else {
            System.out.println("Not enough shares to sell!");
        }
    }

    // Display Portfolio
    public void displayPortfolio() {

        System.out.println("\n----- Portfolio -----");

        if (portfolio.isEmpty()) {
            System.out.println("No stocks owned.");
        } else {
            for (String symbol : portfolio.keySet()) {
                System.out.println(
                        symbol + " : "
                                + portfolio.get(symbol)
                                + " shares");
            }
        }

        System.out.println("Balance : ₹" + balance);
    }
}