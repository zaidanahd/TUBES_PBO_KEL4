package pbotubes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//Task 4.3 - class service untuk proses peminjaman dan pengembalian
public class PeminjamanService {
    private BiayaPengembalian kalkulasiBiaya;
    private static final double BIAYA_ASURANSI = 100000;

    public PeminjamanService() {
        
        this.kalkulasiBiaya = new DefaultBiayaPengembalian();
    }

    // Setter untuk inject implementasi kustom (DIP)
    public void setKalkulasiBiaya(BiayaPengembalian kalkulasiBiaya) {
        this.kalkulasiBiaya = kalkulasiBiaya;
    }

    // Task 4.3: Logika peminjaman lengkap
    public Transaksi prosesPeminjaman(String ktp, String platNomor, int lamaSewa, boolean asuransi) {
        // 1. Validasi KTP terdaftar
        Pelanggan pelanggan = cariPelanggan(ktp);
        if (pelanggan == null) {
            throw new IllegalArgumentException("[ERROR] Pelanggan dengan KTP " + ktp + " tidak terdaftar!");
        }

        // 2. Cek kendaraan tersedia
        Kendaraan kendaraan = cariKendaraan(platNomor);
        if (kendaraan == null) {
            throw new IllegalArgumentException("[ERROR] Kendaraan dengan plat " + platNomor + " tidak ditemukan!");
        }

        if (!kendaraan.status.equalsIgnoreCase("TERSEDIA")) {
            throw new IllegalStateException("[ERROR] Kendaraan " + platNomor + " sedang tidak tersedia!");
        }

        // 3. Hitung estimasi biaya awal
        double estimasiBiaya = kendaraan.hargaSewa * lamaSewa;

        if(asuransi){
        estimasiBiaya += BIAYA_ASURANSI;
        }

        // 4. Ubah status kendaraan
        kendaraan.status = "SEDANG DISEWA";

        // 5. Generate ID Transaksi (Task 4.2)
        String idTransaksi = IdGenerator.generateTransaksiId();

        // 6. Buat transaksi baru
        Transaksi transaksi = new Transaksi(
                idTransaksi,
                pelanggan.nama,
                platNomor,
                "BERJALAN",
                estimasiBiaya,
                asuransi);

        App.daftarTransaksi.add(transaksi);

        return transaksi;
    }

    private Pelanggan cariPelanggan(String ktp) {
        for (Pelanggan p : App.daftarPelanggan) {
            if (p.ktp.equals(ktp)) {
                return p;
            }
        }
        return null;
    }

    private Kendaraan cariKendaraan(String platNomor) {
        for (Kendaraan k : App.daftarKendaraan) {
            if (k.platNomor.equalsIgnoreCase(platNomor)) {
                return k;
            }
        }
        return null;
    }

    // Delegate ke interface untuk kalkulasi biaya pengembalian
    public double hitungBiayaPengembalian(Transaksi transaksi, int hariTerlambat, double dendaPerHari) {
        return kalkulasiBiaya.hitungBiayaPengembalian(transaksi, hariTerlambat, dendaPerHari);
    }

    public double prosesPengembalian(Transaksi transaksi, TingkatKerusakan kerusakan) {

    DamageService damageService = new DamageService();

    double dendaKerusakan = damageService.hitungDendaKerusakan(kerusakan, transaksi.menggunakanAsuransi);

    transaksi.status = "SELESAI";

    transaksi.totalTagihan += dendaKerusakan;

    return transaksi.totalTagihan;
    }
}