package pbotubes.Service; // ← service lowercase!

import pbotubes.Model.*; // ← model lowercase, import spesifik
import pbotubes.Config.*; // ← config lowercase!
import pbotubes.Presentation.*; // ← presentation lowercase!

import java.sql.*;
import java.util.ArrayList;
public class DatabaseService {

    // ========== KENDARAAN ==========
    // EPIC 2 (Intan) : Load seluruh data kendaraan dari DB ke ArrayList saat aplikasi start.

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


    // EPIC 2 - Task 1 (Intan) : Dipanggil dari admin.menuTambahKendaraan() untuk menyimpan kendaraan baru,
    // dan dari staff (proses sewa/pengembalian) untuk update status kendaraan.
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

    // EPIC 2 - Task 3 (Intan) : Dipanggil dari admin.menuHapusKendaraan().

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
    // ==EPIC 3 (Zaidan) : Load seluruh data pelanggan dari DB ke ArrayList saat aplikasi start.
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

    // EPIC 3 - Task 1 (Zaidan) : Dipanggil dari staff.menuDaftarPelanggan() untuk menyimpan pelanggan baru.
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
    // EPIC 4 (Alwi) : Load seluruh data transaksi dari DB ke ArrayList saat aplikasi start.

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
                        rs.getInt("durasi_sewa"),
                        rs.getBoolean("menggunakan_asuransi"));
                t.setHariTerlambat(rs.getInt("hari_terlambat"));
                t.setDenda(rs.getDouble("denda"));
                t.setTanggalPinjam(rs.getTimestamp("tanggal_pinjam"));
                t.setTanggalKembali(rs.getTimestamp("tanggal_kembali")); // ← TAMBAH
                list.add(t);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal load transaksi: " + e.getMessage());
        }
        return list;
    }

     // EPIC 4 - Task 2 & 3 (Alwi) : Dipanggil saat proses sewa (status BERJALAN) dan saat pengembalian.
    public static void saveTransaksi(ArrayList<Transaksi> daftarTransaksi) {
        String sql = "INSERT INTO transaksi (id_transaksi, nama_pelanggan, plat_nomor, status, total_tagihan, durasi_sewa, hari_terlambat, denda, menggunakan_asuransi, tanggal_pinjam, tanggal_kembali) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE status=?, total_tagihan=?, hari_terlambat=?, denda=?, tanggal_kembali=?";

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
                ps.setBoolean(9, t.isMenggunakanAsuransi());

                // Tanggal pinjam: pakai yang sudah ada atau NOW() kalau baru
                if (t.getTanggalPinjam() != null) {
                    ps.setTimestamp(10, t.getTanggalPinjam());
                } else {
                    ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                }

                // Tanggal kembali: null jika masih BERJALAN, NOW() jika SELESAI
                if (t.getTanggalKembali() != null) {
                    ps.setTimestamp(11, t.getTanggalKembali());
                } else {
                    ps.setNull(11, Types.TIMESTAMP);
                }

                // Update fields
                ps.setString(12, t.getStatus());
                ps.setDouble(13, t.getTotalTagihan());
                ps.setInt(14, t.getHariTerlambat());
                ps.setDouble(15, t.getDenda());

                if (t.getTanggalKembali() != null) {
                    ps.setTimestamp(16, t.getTanggalKembali());
                } else {
                    ps.setNull(16, Types.TIMESTAMP);
                }

                ps.executeUpdate();
            }
            System.out.println("[DEBUG] Transaksi tersimpan ke DB!");
        } catch (SQLException e) {
            System.err.println("[ERROR] Gagal save transaksi: " + e.getMessage());
        }
    }
    // ========== LOGIN ==========
    // EPIC 1 - Task 1 (Silvi) : Login berbasis DB (dicoba dulu sebelum fallback ke akun hardcoded di
    // LoginRentalKendaraan.main()). 
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