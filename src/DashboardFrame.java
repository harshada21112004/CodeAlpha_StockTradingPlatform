import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DashboardFrame extends JFrame {

    private int userId;
    private String username;
    private double balance;

    private JLabel welcomeLabel;
    private JLabel balanceLabel;

    private JTable stockTable;
    private JTable portfolioTable;

    private DefaultTableModel stockModel;
    private DefaultTableModel portfolioModel;

    private JTextField symbolField;
    private JTextField quantityField;

    private JTextArea historyArea;

    private JButton buyButton;
    private JButton sellButton;
    private JButton refreshButton;
    private JButton logoutButton;

    public DashboardFrame(int userId, String username, double balance) {

        this.userId = userId;
        this.username = username;
        this.balance = balance;

        setTitle("Stock Trading Platform");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        initializeComponents();

        loadStocks();
        loadPortfolio();
        loadTransactions();

        setVisible(true);
    }

    private void initializeComponents() {

        welcomeLabel = new JLabel("Welcome : " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(20, 10, 300, 30);
        add(welcomeLabel);

        balanceLabel = new JLabel("Balance : ₹" + balance);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setBounds(700, 10, 250, 30);
        add(balanceLabel);

        stockModel = new DefaultTableModel();
        stockModel.addColumn("Symbol");
        stockModel.addColumn("Company");
        stockModel.addColumn("Price");

        stockTable = new JTable(stockModel);

        JScrollPane stockScroll = new JScrollPane(stockTable);
        stockScroll.setBorder(
                BorderFactory.createTitledBorder("Market Stocks"));
        stockScroll.setBounds(20, 60, 450, 250);
        add(stockScroll);

        portfolioModel = new DefaultTableModel();
        portfolioModel.addColumn("Symbol");
        portfolioModel.addColumn("Quantity");

        portfolioTable = new JTable(portfolioModel);

        JScrollPane portfolioScroll =
                new JScrollPane(portfolioTable);

        portfolioScroll.setBorder(
                BorderFactory.createTitledBorder("My Portfolio"));

        portfolioScroll.setBounds(500, 60, 450, 250);
        add(portfolioScroll);

        JLabel symbolLabel = new JLabel("Stock Symbol:");
        symbolLabel.setBounds(20, 340, 100, 25);
        add(symbolLabel);

        symbolField = new JTextField();
        symbolField.setBounds(130, 340, 120, 25);
        add(symbolField);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setBounds(280, 340, 80, 25);
        add(qtyLabel);

        quantityField = new JTextField();
        quantityField.setBounds(360, 340, 100, 25);
        add(quantityField);

        buyButton = new JButton("Buy");
        buyButton.setBounds(500, 340, 100, 30);
        add(buyButton);

        sellButton = new JButton("Sell");
        sellButton.setBounds(620, 340, 100, 30);
        add(sellButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(740, 340, 100, 30);
        add(refreshButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(850, 340, 100, 30);
        add(logoutButton);

        historyArea = new JTextArea();
        historyArea.setEditable(false);

        JScrollPane historyScroll =
                new JScrollPane(historyArea);

        historyScroll.setBorder(
                BorderFactory.createTitledBorder("Transaction History"));

        historyScroll.setBounds(20, 400, 930, 180);
        add(historyScroll);

        buyButton.addActionListener(e -> buyStock());
        sellButton.addActionListener(e -> sellStock());

        refreshButton.addActionListener(e -> {
            loadStocks();
            loadPortfolio();
            loadTransactions();
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
    }

    private void loadStocks() {

        stockModel.setRowCount(0);

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs =
                    st.executeQuery("SELECT * FROM stocks");

            while (rs.next()) {

                stockModel.addRow(new Object[]{
                        rs.getString("symbol"),
                        rs.getString("company_name"),
                        rs.getDouble("price")
                });
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPortfolio() {

        portfolioModel.setRowCount(0);

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM portfolio WHERE user_id=?");

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                portfolioModel.addRow(new Object[]{
                        rs.getString("stock_symbol"),
                        rs.getInt("quantity")
                });
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadTransactions() {

        historyArea.setText("");

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM transactions WHERE user_id=? ORDER BY transaction_id");

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                historyArea.append(
                        rs.getString("transaction_type")
                                + " | "
                                + rs.getString("stock_symbol")
                                + " | Qty:"
                                + rs.getInt("quantity")
                                + " | Price: ₹"
                                + rs.getDouble("price")
                                + " | "
                                + rs.getTimestamp("transaction_time")
                                + "\n");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buyStock() {

        try {

            String symbol =
                    symbolField.getText().trim().toUpperCase();

            int quantity =
                    Integer.parseInt(quantityField.getText().trim());

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT price FROM stocks WHERE symbol=?");

            ps.setString(1, symbol);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Stock Not Found");

                return;
            }

            double price = rs.getDouble("price");
            double total = price * quantity;

            if (balance < total) {

                JOptionPane.showMessageDialog(
                        this,
                        "Insufficient Balance");

                return;
            }

            balance -= total;

            PreparedStatement updateUser =
                    con.prepareStatement(
                            "UPDATE users SET balance=? WHERE user_id=?");

            updateUser.setDouble(1, balance);
            updateUser.setInt(2, userId);

            updateUser.executeUpdate();

            PreparedStatement check =
                    con.prepareStatement(
                            "SELECT * FROM portfolio WHERE user_id=? AND stock_symbol=?");

            check.setInt(1, userId);
            check.setString(2, symbol);

            ResultSet rs2 = check.executeQuery();

            if (rs2.next()) {

                int oldQty = rs2.getInt("quantity");

                PreparedStatement update =
                        con.prepareStatement(
                                "UPDATE portfolio SET quantity=? WHERE user_id=? AND stock_symbol=?");

                update.setInt(1, oldQty + quantity);
                update.setInt(2, userId);
                update.setString(3, symbol);

                update.executeUpdate();

            } else {

                PreparedStatement insert =
                        con.prepareStatement(
                                "INSERT INTO portfolio(user_id,stock_symbol,quantity) VALUES(?,?,?)");

                insert.setInt(1, userId);
                insert.setString(2, symbol);
                insert.setInt(3, quantity);

                insert.executeUpdate();
            }

            PreparedStatement trans =
                    con.prepareStatement(
                            "INSERT INTO transactions(user_id,stock_symbol,quantity,price,transaction_type) VALUES(?,?,?,?,?)");

            trans.setInt(1, userId);
            trans.setString(2, symbol);
            trans.setInt(3, quantity);
            trans.setDouble(4, price);
            trans.setString(5, "BUY");

            trans.executeUpdate();

            balanceLabel.setText("Balance : ₹" + balance);

            historyArea.append(
                    "BUY " + symbol +
                            " Qty:" + quantity +
                            " Amount:₹" + total + "\n");

            loadPortfolio();
            loadTransactions();

            JOptionPane.showMessageDialog(
                    this,
                    "Stock Purchased Successfully");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Input");
        }
    }

    private void sellStock() {

        try {

            String symbol =
                    symbolField.getText().trim().toUpperCase();

            int quantity =
                    Integer.parseInt(quantityField.getText().trim());

            Connection con = DBConnection.getConnection();

            // Check owned stock
            PreparedStatement check =
                    con.prepareStatement(
                            "SELECT quantity FROM portfolio WHERE user_id=? AND stock_symbol=?");

            check.setInt(1, userId);
            check.setString(2, symbol);

            ResultSet rs = check.executeQuery();

            if (!rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "You do not own this stock!");

                return;
            }

            int ownedQty = rs.getInt("quantity");

            if (ownedQty < quantity) {

                JOptionPane.showMessageDialog(
                        this,
                        "Not enough shares!");

                return;
            }

            // Get current stock price
            PreparedStatement pricePs =
                    con.prepareStatement(
                            "SELECT price FROM stocks WHERE symbol=?");

            pricePs.setString(1, symbol);

            ResultSet priceRs = pricePs.executeQuery();

            if (!priceRs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Stock not found!");

                return;
            }

            double price = priceRs.getDouble("price");
            double total = price * quantity;

            // Update portfolio
            int remainingQty = ownedQty - quantity;

            if (remainingQty == 0) {

                PreparedStatement delete =
                        con.prepareStatement(
                                "DELETE FROM portfolio WHERE user_id=? AND stock_symbol=?");

                delete.setInt(1, userId);
                delete.setString(2, symbol);

                delete.executeUpdate();

            } else {

                PreparedStatement update =
                        con.prepareStatement(
                                "UPDATE portfolio SET quantity=? WHERE user_id=? AND stock_symbol=?");

                update.setInt(1, remainingQty);
                update.setInt(2, userId);
                update.setString(3, symbol);

                update.executeUpdate();
            }

            // Update balance
            balance += total;

            PreparedStatement updateUser =
                    con.prepareStatement(
                            "UPDATE users SET balance=? WHERE user_id=?");

            updateUser.setDouble(1, balance);
            updateUser.setInt(2, userId);

            updateUser.executeUpdate();

            // Insert transaction
            PreparedStatement trans =
                    con.prepareStatement(
                            "INSERT INTO transactions(user_id,stock_symbol,quantity,price,transaction_type) VALUES(?,?,?,?,?)");

            trans.setInt(1, userId);
            trans.setString(2, symbol);
            trans.setInt(3, quantity);
            trans.setDouble(4, price);
            trans.setString(5, "SELL");

            trans.executeUpdate();

            balanceLabel.setText("Balance : ₹" + balance);

            historyArea.append(
                    "SELL " + symbol +
                            " Qty:" + quantity +
                            " Amount:₹" + total + "\n");

            loadPortfolio();
            loadTransactions();

            JOptionPane.showMessageDialog(
                    this,
                    "Stock Sold Successfully!");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error while selling stock!");
        }
    }
}