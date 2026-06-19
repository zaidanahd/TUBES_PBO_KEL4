package pbotubes.Model;

import java.sql.Timestamp;

public class Transaksi {
    String idTransaksi;
    String namaPelanggan;
    String platNomor;
    String status;
    double totalTagihan;
    int durasiSewa;
    int hariTerlambat;
    double denda;
    boolean menggunakanAsuransi;
    Timestamp tanggalPinjam; // ← TAMBAH
    Timestamp tanggalKembali; // ← TAMBAH

    // Constructor tanpa asuransi (backward compatible)
    public Transaksi(String idTransaksi, String namaPelanggan, String platNomor,
            String status, double totalTagihan, int durasiSewa) {
        this(idTransaksi, namaPelanggan, platNomor, status, totalTagihan, durasiSewa, false);
    }

    // Constructor dengan asuransi
    public Transaksi(String idTransaksi, String namaPelanggan, String platNomor,
            String status, double totalTagihan, int durasiSewa, boolean menggunakanAsuransi) {
        this.idTransaksi = idTransaksi;
        this.namaPelanggan = namaPelanggan;
        this.platNomor = platNomor;
        this.status = status;
        this.totalTagihan = totalTagihan;
        this.durasiSewa = durasiSewa;
        this.hariTerlambat = 0;
        this.denda = 0;
        this.menggunakanAsuransi = menggunakanAsuransi;
        this.tanggalPinjam = new Timestamp(System.currentTimeMillis()); // ← Set saat pembuatan
        this.tanggalKembali = null; // ← Default null
    }

    // Getter yang sudah ada...
    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalTagihan() {
        return totalTagihan;
    }

    public int getDurasiSewa() {
        return durasiSewa;
    }

    public int getHariTerlambat() {
        return hariTerlambat;
    }

    public double getDenda() {
        return denda;
    }

    public boolean isMenggunakanAsuransi() {
        return menggunakanAsuransi;
    }

    // ← TAMBAH getter tanggal
    public Timestamp getTanggalPinjam() {
        return tanggalPinjam;
    }

    public Timestamp getTanggalKembali() {
        return tanggalKembali;
    }

    // Setter yang sudah ada...
    public void setStatus(String status) {
        this.status = status;
    }

    public void setHariTerlambat(int hariTerlambat) {
        this.hariTerlambat = hariTerlambat;
    }

    public void setDenda(double denda) {
        this.denda = denda;
    }

    public void setTotalTagihan(double totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

    // ← TAMBAH setter tanggal
    public void setTanggalPinjam(Timestamp tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public void setTanggalKembali(Timestamp tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }
}