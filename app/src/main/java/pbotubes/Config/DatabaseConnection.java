package pbotubes.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/DB_TUBES_PBO";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[INFO] Koneksi ke database Laragon berhasil!");
            } catch (ClassNotFoundException e) {
                System.err.println("[ERROR] Driver MySQL tidak ditemukan: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("[ERROR] Koneksi database gagal: " + e.getMessage());
            }
        }
        return connection;
    }
}