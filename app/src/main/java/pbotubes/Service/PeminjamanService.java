package pbotubes.Service; 

import pbotubes.Model.Kendaraan; 
import pbotubes.Model.Pelanggan;
import pbotubes.Model.Transaksi;
import pbotubes.Presentation.LoginRentalKendaraan; 

// EPIC 4 - Task 4.3 (Alwi) - class service untuk proses peminjaman dan pengembalian
public class PeminjamanService {

    // Overload: tanpa asuransi (backward compatible)
    public Transaksi prosesPeminjaman(String ktp, String platNomor, int lamaSewa) {
        return prosesPeminjaman(ktp, platNomor, lamaSewa, false);
    }

    // Method utama dengan asuransi (MODUL 4)
    public Transaksi prosesPeminjaman(String ktp, String platNomor, int lamaSewa, boolean menggunakanAsuransi) {
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

        // ← PAKAI GETTER, bukan field langsung
        if (!kendaraan.getStatus().equalsIgnoreCase("TERSEDIA")) {
            throw new IllegalStateException("[ERROR] Kendaraan " + platNomor + " sedang tidak tersedia!");
        }

        // 3. Hitung estimasi biaya awal
        // ← PAKAI GETTER
        double estimasiBiaya = kendaraan.getHargaSewa() * lamaSewa;

        // 4. Ubah status kendaraan
        // ← PAKAI SETTER
        kendaraan.setStatus("SEDANG DISEWA");

        // 5. Generate ID Transaksi (Task 4.2)
        String idTransaksi = IdGenerator.generateTransaksiId();

        // 6. Buat transaksi baru (dengan asuransi)
        Transaksi transaksi = new Transaksi(
                idTransaksi,
                pelanggan.getNama(), // ← PAKAI GETTER
                platNomor,
                "BERJALAN",
                estimasiBiaya,
                lamaSewa,
                menggunakanAsuransi);

        LoginRentalKendaraan.daftarTransaksi.add(transaksi);

        return transaksi;
    }

    private Pelanggan cariPelanggan(String ktp) {
        for (Pelanggan p : LoginRentalKendaraan.daftarPelanggan) {
            if (p.getKtp().equals(ktp)) { // ← PAKAI GETTER
                return p;
            }
        }
        return null;
    }

    private Kendaraan cariKendaraan(String platNomor) {
        for (Kendaraan k : LoginRentalKendaraan.daftarKendaraan) {
            if (k.getPlatNomor().equalsIgnoreCase(platNomor)) { // ← PAKAI GETTER
                return k;
            }
        }
        return null;
    }
}