package pbotubes;

public class Transaksi {
    String idTransaksi;
    String namaPelanggan;
    String platNomor;
    String status;
    double totalTagihan;
    int durasiSewa;
    int hariTerlambat;
    double denda;
    boolean menggunakanAsuransi; // ← TAMBAHAN MODUL 4

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
    }

    // Getter
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
    } // ← TAMBAHAN

    // Setter
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
}