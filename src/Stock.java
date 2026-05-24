public class Stock {

    private String symbol;
    private String companyName;
    private double price;

    // Constructor
    public Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(double price) {
        this.price = price;
    }

    // Display stock details
    public void displayStock() {
        System.out.println("Symbol      : " + symbol);
        System.out.println("Company     : " + companyName);
        System.out.println("Price       : ₹" + price);
        System.out.println("--------------------------");
    }
}