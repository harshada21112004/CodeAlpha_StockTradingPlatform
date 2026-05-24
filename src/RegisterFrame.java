import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterFrame extends JFrame implements ActionListener {

    JLabel titleLabel, userLabel, balanceLabel;
    JTextField userField, balanceField;
    JButton registerButton, clearButton;

    public RegisterFrame() {

        setTitle("User Registration");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        titleLabel = new JLabel("Register New User");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(120, 20, 250, 30);

        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 90, 100, 25);

        userField = new JTextField();
        userField.setBounds(170, 90, 180, 25);

        balanceLabel = new JLabel("Initial Balance:");
        balanceLabel.setBounds(50, 140, 100, 25);

        balanceField = new JTextField();
        balanceField.setBounds(170, 140, 180, 25);

        registerButton = new JButton("Register");
        registerButton.setBounds(80, 210, 120, 35);

        clearButton = new JButton("Clear");
        clearButton.setBounds(240, 210, 120, 35);

        registerButton.addActionListener(this);
        clearButton.addActionListener(this);

        add(titleLabel);
        add(userLabel);
        add(userField);
        add(balanceLabel);
        add(balanceField);
        add(registerButton);
        add(clearButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == registerButton) {

            String username = userField.getText().trim();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter Username!"
                );
                return;
            }

            double balance;

            try {
                balance = Double.parseDouble(
                        balanceField.getText()
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter Valid Balance!"
                );
                return;
            }

            try {

                Connection con =
                        DBConnection.getConnection();

                String checkQuery =
                        "SELECT * FROM users WHERE username=?";

                PreparedStatement checkPs =
                        con.prepareStatement(checkQuery);

                checkPs.setString(1, username);

                ResultSet rs =
                        checkPs.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Username Already Exists!"
                    );

                    con.close();
                    return;
                }

                String insertQuery =
                        "INSERT INTO users(username,balance) VALUES(?,?)";

                PreparedStatement ps =
                        con.prepareStatement(insertQuery);

                ps.setString(1, username);
                ps.setDouble(2, balance);

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Registration Successful!"
                    );

                    userField.setText("");
                    balanceField.setText("");
                }

                con.close();

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error!"
                );
            }
        }

        if (e.getSource() == clearButton) {

            userField.setText("");
            balanceField.setText("");
        }
    }
}