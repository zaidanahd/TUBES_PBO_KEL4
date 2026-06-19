package pbotubes;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseService {

    // ========== KENDARAAN ==========

    public static ArrayList<Kendaraan> loadKendaraan() {
        ArrayList<Kendaraan> list = new ArrayList<>();
        String sql = "SELECT * FROM kendaraan";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return list;

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Kendaraan k = new Kendaraan(
                        rs.getString("plat_nomor"),
                        rs.getString("jenis"),
                        rs.getDouble("harga_sewa"),
                        rs.getString("merk"),
                        rs.getString("info_tambahan"));
                k.setStatus(rs.getString("status"));
                list.add(k);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal load kendaraan: " + e.getMessage());
        }
        return list;
    }

    public static void saveKendaraan(Kendaraan k) {
        String sql = "INSERT INTO kendaraan (plat_nomor, jenis, harga_sewa, merk, info_tambahan, status) VALUES (?, ?, ?, ?, ?, ?) "
                +
                "ON DUPLICATE KEY UPDATE harga_sewa=?, merk=?, info_tambahan=?, status=?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getPlatNomor());
            ps.setString(2, k.getJenis());
            ps.setDouble(3, k.getHargaSewa());
            ps.setString(4, k.getMerk());
            ps.setString(5, k.getInfoTambahan());
            ps.setString(6, k.getStatus());
            ps.setDouble(7, k.getHargaSewa());
            ps.setString(8, k.getMerk());
            ps.setString(9, k.getInfoTambahan());
            ps.setString(10, k.getStatus());
            ps.executeUpdate();
            System.out.println("[DEBUG] Kendaraan " + k.getPlatNomor() + " tersimpan ke DB!");
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal save kendaraan: " + e.getMessage());
        }
    }

    public static void deleteKendaraan(String platNomor) {
        String sql = "DELETE FROM kendaraan WHERE plat_nomor=?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, platNomor);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal delete kendaraan: " + e.getMessage());
        }
    }

    // ========== PELANGGAN ==========

    public static ArrayList<Pelanggan> loadPelanggan() {
        ArrayList<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return list;

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Pelanggan(
                        rs.getString("ktp"),
                        rs.getString("nama"),
                        rs.getString("no_telepon")));
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal load pelanggan: " + e.getMessage());
        }
        return list;
    }

    public static void savePelanggan(Pelanggan p) {
        String sql = "INSERT INTO pelanggan (ktp, nama, no_telepon) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE nama=?, no_telepon=?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getKtp());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getNoTelepon());
            ps.setString(4, p.getNama());
            ps.setString(5, p.getNoTelepon());
            ps.executeUpdate();
            System.out.println("[DEBUG] Pelanggan " + p.getNama() + " tersimpan ke DB!");
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal save pelanggan: " + e.getMessage());
        }
    }

    // ========== TRANSAKSI ==========

    public static ArrayList<Transaksi> loadTransaksi() {
        ArrayList<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return list;

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaksi t = new Transaksi(
                        rs.getString("id_transaksi"),
                        rs.getString("nama_pelanggan"),
                        rs.getString("plat_nomor"),
                        rs.getString("status"),
                        rs.getDouble("total_tagihan"),
                        rs.getInt("durasi_sewa"));
                t.setHariTerlambat(rs.getInt("hari_terlambat"));
                t.setDenda(rs.getDouble("denda"));
                list.add(t);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal load transaksi: " + e.getMessage());
        }
        return list;
    }

    public static void saveTransaksi(ArrayList<Transaksi> daftarTransaksi) {
        String sql = "INSERT INTO transaksi (id_transaksi, nama_pelanggan, plat_nomor, status, total_tagihan, durasi_sewa, hari_terlambat, denda) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE status=?, total_tagihan=?, hari_terlambat=?, denda=?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Transaksi t : daftarTransaksi) {
                ps.setString(1, t.getIdTransaksi());
                ps.setString(2, t.getNamaPelanggan());
                ps.setString(3, t.getPlatNomor());
                ps.setString(4, t.getStatus());
                ps.setDouble(5, t.getTotalTagihan());
                ps.setInt(6, t.getDurasiSewa());
                ps.setInt(7, t.getHariTerlambat());
                ps.setDouble(8, t.getDenda());
                ps.setString(9, t.getStatus());
                ps.setDouble(10, t.getTotalTagihan());
                ps.setInt(11, t.getHariTerlambat());
                ps.setDouble(12, t.getDenda());
                ps.executeUpdate();
            }
            System.out.println("[DEBUG] Transaksi tersimpan ke DB!");
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal save transaksi: " + e.getMessage());
        }
    }

    // ========== LOGIN ==========

    public static User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
            return null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                String uname = rs.getString("username");
                return switch (role) {
                    case "ADMIN" -> new admin(uname);
                    case "STAFF" -> new staff(uname);
                    case "OWNER" -> new owner(uname);
                    default -> null;
                };
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal login dari DB: " + e.getMessage());
        }
        return null;
    }
}