package pbotubes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // URL ke database MySQL Laragon (sesuaikan nama databasenya nanti)
    private static final String URL = "jdbc:mysql://localhost:3306/db_tubes_pbo";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Bawaan Laragon kosong

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Membuka koneksi menggunakan driver yang sudah di-load Gradle
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[INFO] Koneksi ke database Laragon berhasil!");
            } catch (SQLException e) {
                System.err.println("[ERROR] Koneksi database gagal: " + e.getMessage());
            }
        }
        return connection;
    }
}