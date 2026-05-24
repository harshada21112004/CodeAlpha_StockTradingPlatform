import java.time.LocalDateTime;

public class Transaction {

    private String stockSymbol;
    private int quantity;
    private double price;
    private String type; // BUY or SELL
    private LocalDateTime dateTime;

    // Constructor
    public Transaction(String stockSymbol, int quantity,
                       double price, String type) {

        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.dateTime = LocalDateTime.now();
    }

    // Getters
    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // Total Amount
    public double getTotalAmount() {
        return quantity * price;
    }

    // Display Transaction
    public void displayTransaction() {

        System.out.println("--------------------------------");
        System.out.println("Type      : " + type);
        System.out.println("Stock     : " + stockSymbol);
        System.out.println("Quantity  : " + quantity);
        System.out.println("Price     : ₹" + price);
        System.out.println("Amount    : ₹" + getTotalAmount());
        System.out.println("Date-Time : " + dateTime);
        System.out.println("--------------------------------");
    }

    @Override
    public String toString() {
        return type + " | " +
                stockSymbol + " | " +
                quantity + " Shares | ₹" +
                getTotalAmount() + " | " +
                dateTime;
    }
}