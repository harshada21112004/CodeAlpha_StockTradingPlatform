import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame implements ActionListener {

    JLabel titleLabel, userLabel;
    JTextField userField;
    JButton loginButton, exitButton;

    public LoginFrame() {

        setTitle("Stock Trading Platform - Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        titleLabel = new JLabel("Stock Trading Platform");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(70, 20, 250, 30);

        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 80, 100, 25);

        userField = new JTextField();
        userField.setBounds(150, 80, 180, 25);

        loginButton = new JButton("Login");
        loginButton.setBounds(80, 140, 100, 30);

        exitButton = new JButton("Exit");
        exitButton.setBounds(210, 140, 100, 30);

        loginButton.addActionListener(this);
        exitButton.addActionListener(this);

        add(titleLabel);
        add(userLabel);
        add(userField);
        add(loginButton);
        add(exitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {

            String username = userField.getText().trim();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter Username!"
                );
                return;
            }

            try {

                Connection con =
                        DBConnection.getConnection();

                String sql =
                        "SELECT * FROM users WHERE username=?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setString(1, username);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Login Successful"
                    );

                    dispose();

                    new DashboardFrame(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getDouble("balance")
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "User Not Found!"
                    );
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

        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}