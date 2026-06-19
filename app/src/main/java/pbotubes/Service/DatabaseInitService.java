package pbotubes.Service;
import pbotubes.Config.DatabaseConnection;
import java.sql.*;

public class DatabaseInitService {

    public void initialize() {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.err.println("[FATAL] Tidak dapat terhubung ke database. Aplikasi akan berhenti.");
            System.exit(1);
        }

        try (Statement stmt = conn.createStatement()) {
            createTables(stmt);
            seedDefaultUsers(stmt);
            System.out.println("[INFO] Inisialisasi database berhasil.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal inisialisasi database: " + e.getMessage());
        }
    }

    private void createTables(Statement stmt) throws SQLException {
        stmt.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(50) NOT NULL,
                        role ENUM('ADMIN', 'STAFF', 'OWNER') NOT NULL
                    )
                """);

        stmt.execute("""
                    CREATE TABLE IF NOT EXISTS kendaraan (
                        plat_nomor VARCHAR(20) PRIMARY KEY,
                        jenis ENUM('Mobil', 'Motor') NOT NULL,
                        harga_sewa DOUBLE NOT NULL,
                        merk VARCHAR(50) NOT NULL,
                        info_tambahan VARCHAR(100),
                        status ENUM('TERSEDIA', 'SEDANG DISEWA') DEFAULT 'TERSEDIA'
                    )
                """);

        stmt.execute("""
                    CREATE TABLE IF NOT EXISTS pelanggan (
                        ktp VARCHAR(20) PRIMARY KEY,
                        nama VARCHAR(100) NOT NULL,
                        no_telepon VARCHAR(20) NOT NULL
                    )
                """);

        // ← MODUL 4: Tambah kolom menggunakan_asuransi
        stmt.execute("""
                    CREATE TABLE IF NOT EXISTS transaksi (
                        id_transaksi VARCHAR(20) PRIMARY KEY,
                        nama_pelanggan VARCHAR(100) NOT NULL,
                        plat_nomor VARCHAR(20) NOT NULL,
                        status ENUM('BERJALAN', 'SELESAI') NOT NULL,
                        total_tagihan DOUBLE NOT NULL,
                        durasi_sewa INT DEFAULT 0,
                        hari_terlambat INT DEFAULT 0,
                        denda DOUBLE DEFAULT 0,
                        menggunakan_asuransi BOOLEAN DEFAULT FALSE,
                        tanggal_pinjam TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        tanggal_kembali TIMESTAMP NULL,
                        FOREIGN KEY (plat_nomor) REFERENCES kendaraan(plat_nomor)
                    )
                """);
    }

    private void seedDefaultUsers(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.execute("""
                        INSERT INTO users (username, password, role) VALUES
                        ('admin123', 'admin123', 'ADMIN'),
                        ('staff123', 'staff123', 'STAFF'),
                        ('owner123', 'owner123', 'OWNER')
                    """);
            System.out.println("[INFO] Data user default berhasil dibuat.");
        }
    }
}