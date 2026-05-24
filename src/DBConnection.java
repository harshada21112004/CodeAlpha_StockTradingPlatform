import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/stockdb";

    private static final String USER = "root";

    private static final String PASSWORD = "sql123";

    public static Connection getConnection() {

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {

            System.out.println("Connection Failed!");
            e.printStackTrace();
        }

        return con;
    }
}