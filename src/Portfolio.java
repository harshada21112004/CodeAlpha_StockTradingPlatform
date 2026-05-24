import java.util.HashMap;
import java.util.Map;

public class Portfolio {

    // Stock Symbol -> Quantity
    private HashMap<String, Integer> holdings;

    public Portfolio() {
        holdings = new HashMap<>();
    }

    // Add shares
    public void addStock(String symbol, int quantity) {

        holdings.put(
                symbol,
                holdings.getOrDefault(symbol, 0) + quantity
        );
    }

    // Remove shares
    public boolean removeStock(String symbol, int quantity) {

        if (!holdings.containsKey(symbol)
                || holdings.get(symbol) < quantity) {

            return false;
        }

        int remaining = holdings.get(symbol) - quantity;

        if (remaining == 0) {
            holdings.remove(symbol);
        } else {
            holdings.put(symbol, remaining);
        }

        return true;
    }

    // Get quantity of a stock
    public int getQuantity(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }

    // Display portfolio
    public void displayPortfolio() {

        System.out.println("\n===== PORTFOLIO =====");

        if (holdings.isEmpty()) {
            System.out.println("No stocks owned.");
            return;
        }

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {

            System.out.println(
                    "Stock: " + entry.getKey()
                            + " | Shares: " + entry.getValue()
            );
        }
    }

    // Calculate total portfolio value
    public double calculateValue(HashMap<String, Stock> marketStocks) {

        double totalValue = 0;

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {

            String symbol = entry.getKey();
            int quantity = entry.getValue();

            if (marketStocks.containsKey(symbol)) {

                totalValue += quantity *
                        marketStocks.get(symbol).getPrice();
            }
        }

        return totalValue;
    }
}