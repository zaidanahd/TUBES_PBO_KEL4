package pbotubes.Presentation;

import pbotubes.Model.*;
import pbotubes.Service.*;

import java.util.Scanner;


//Dashboard & menu untuk role STAFF.
//Gabungan dua Epic:
//EPIC 3 (Zaidan) : Manajemen Pelanggan (Daftar & Cari Pelanggan)
//EPIC 4 (Alwi)   : Transaksi Peminjaman & Pengembalian (+ Lihat Kendaraan Tersedia)
 
public class staff extends User {

    private PeminjamanService peminjamanService;

    public staff(String username) {
        super(username);
        this.peminjamanService = new PeminjamanService();
    }

    //EPIC 1 - Task 2 (Silvi) :  Override menu() agar Staff hanya melihat menu Manajemen Pelanggan & Transaksi.
    // (gabungan Epic 3 & Epic 4), sesuai acceptance criteria pembatasan akses per role.

    @Override
    public void menu() {
        Scanner input = new Scanner(System.in);
        int pilihan = -1;

        do {
            System.out.println("\n===== DASHBOARD STAFF =====");
            System.out.println("Selamat datang, " + username);
            System.out.println("1. Data Pelanggan (Daftar Pelanggan Baru)");
            System.out.println("2. Cari Data Pelanggan");
            System.out.println("3. Proses Sewa");
            System.out.println("4. Pengembalian");
            System.out.println("5. Lihat Kendaraan Tersedia");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");

            try {
                pilihan = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Masukkan angka!");
                continue;
            }

            switch (pilihan) {
                case 1:
                    menuDaftarPelanggan(input);
                    break;
                case 2:
                    menuCariPelanggan(input);
                    break;
                case 3:
                    menuProsesSewa(input);
                    break;
                case 4:
                    menuPengembalian(input);
                    break;
                case 5:
                    menuLihatKendaraanTersedia(input);
                    break;
                case 0:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    //EPIC 3 - Task 1 (Zaidan) : User Story 1, Staff mendaftarkan pelanggan baru (Nomor KTP & Nama).
    private void menuDaftarPelanggan(Scanner input) {
        System.out.println("========================================");
        System.out.println("       MENU PENDAFTARAN PELANGGAN       ");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        System.out.print("Masukkan Nomor KTP : ");
        String ktp = input.nextLine().trim();

        if (ktp.equals("0"))
            return;

        if (ktp.isEmpty() || !ktp.matches("[0-9]+")) {
            System.out.println("[ERROR] Nomor KTP tidak boleh kosong dan hanya boleh berisi angka!");
            return;
        }

        for (Pelanggan p : LoginRentalKendaraan.daftarPelanggan) {
            if (p.getKtp().equals(ktp)) {
                System.out.println("Pelanggan dengan KTP tersebut sudah terdaftar.");
                return;
            }
        }

        System.out.print("Masukkan Nama Lengkap: ");
        String nama = input.nextLine();

        System.out.print("Masukkan No Telepon  : ");
        String noTelp = input.nextLine();

        Pelanggan pelangganBaru = new Pelanggan(ktp, nama, noTelp);
        LoginRentalKendaraan.daftarPelanggan.add(pelangganBaru);

        DatabaseService.savePelanggan(pelangganBaru);

        System.out.println("\n[SUKSES] Pelanggan " + nama + " (KTP: " + ktp + ") berhasil didaftarkan.");
    }

    //EPIC 3 - Task 2 (Zaidan) :User Story 2, Staff mencari data pelanggan berdasarkan Nomor KTP.
    private void menuCariPelanggan(Scanner input) {
        System.out.println("========================================");
        System.out.println("         MENU PENCARIAN PELANGGAN       ");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        System.out.print("Masukkan Nomor KTP: ");
        String ktpCari = input.nextLine().trim();

        if (ktpCari.equals("0"))
            return;

        Pelanggan ditemukan = null;
        for (Pelanggan p : LoginRentalKendaraan.daftarPelanggan) {
            if (p.getKtp().equals(ktpCari)) {
                ditemukan = p;
                break;
            }
        }

        if (ditemukan != null) {
            System.out.println("\n[DATA DITEMUKAN]");
            System.out.println("Nama Lengkap : " + ditemukan.getNama());
            System.out.println("Nomor KTP    : " + ditemukan.getKtp());
            System.out.println("No Telepon   : " + ditemukan.getNoTelepon());
        } else {
            System.out.println("\nData pelanggan tidak ditemukan.");
        }
    }

    // EPIC 4 - Task 1 (Alwi) : User Story 1, Staff melihat daftar kendaraan berstatus "Tersedia" saja. 
    private void menuLihatKendaraanTersedia(Scanner input) {
        System.out.println("=========================================================================");
        System.out.println("                     DAFTAR KENDARAAN TERSEDIA                           ");
        System.out.println("=========================================================================");

        boolean adaTersedia = false;

        System.out.printf("| %-10s | %-5s | %-12s | %-10s | %-15s |%n",
                "Plat No", "Jenis", "Harga/Hari", "Merk", "Info Tambahan");
        System.out.println("-------------------------------------------------------------------------");

        for (Kendaraan k : LoginRentalKendaraan.daftarKendaraan) {
            if (k.getStatus().equalsIgnoreCase("TERSEDIA")) {
                System.out.printf("| %-10s | %-5s | Rp %-8.0f | %-10s | %-15s |%n",
                        k.getPlatNomor(), k.getJenis(), k.getHargaSewa(), k.getMerk(), k.getInfoTambahan());
                adaTersedia = true;
            }
        }

        System.out.println("-------------------------------------------------------------------------");

        if (!adaTersedia) {
            System.out.println("Tidak ada kendaraan yang tersedia saat ini.");
        }

        System.out.print("Tekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    // EPIC 4 - Task 2 (Alwi) : User Story 2, Staff mencatat transaksi peminjaman (Validasi KTP, ID Transaksi dibuat otomatis, Status kendaraan).
    // - Mendukung opsi Asuransi (case tambahan Kelompok 4).
    private void menuProsesSewa(Scanner input) {
        System.out.println("========================================");
        System.out.println("          MENU PROSES SEWA            ");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        System.out.println("\n--- Kendaraan Tersedia ---");
        boolean adaTersedia = false;
        for (Kendaraan k : LoginRentalKendaraan.daftarKendaraan) {
            if (k.getStatus().equalsIgnoreCase("TERSEDIA")) {
                System.out.printf("- %s (%s %s) - Rp %.0f/hari%n",
                        k.getPlatNomor(), k.getMerk(), k.getJenis(), k.getHargaSewa());
                adaTersedia = true;
            }
        }
        if (!adaTersedia) {
            System.out.println("Tidak ada kendaraan tersedia.");
            return;
        }

        System.out.print("\nMasukkan Nomor KTP Pelanggan: ");
        String ktp = input.nextLine().trim();
        if (ktp.equals("0"))
            return;

        System.out.print("Masukkan Plat Nomor Kendaraan: ");
        String plat = input.nextLine().trim().toUpperCase();
        if (plat.equals("0"))
            return;

        System.out.print("Lama Sewa (hari): ");
        int lamaSewa;
        try {
            lamaSewa = Integer.parseInt(input.nextLine().trim());
            if (lamaSewa <= 0) {
                System.out.println("[ERROR] Lama sewa harus lebih dari 0!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Lama sewa harus berupa angka!");
            return;
        }

        System.out.print("Gunakan Asuransi? (y/n): ");
        boolean pakaiAsuransi = input.nextLine().trim().equalsIgnoreCase("y");

        try {
            Transaksi transaksi = peminjamanService.prosesPeminjaman(ktp, plat, lamaSewa, pakaiAsuransi);
            System.out.println("\n[SUKSES] Transaksi berhasil dibuat!");
            System.out.println("ID Transaksi : " + transaksi.getIdTransaksi());
            System.out.println("Pelanggan    : " + transaksi.getNamaPelanggan());
            System.out.println("Kendaraan    : " + transaksi.getPlatNomor());
            System.out.println(
                    "Estimasi Biaya: Rp " + String.format("%,.0f", transaksi.getTotalTagihan()).replace(",", "."));
            System.out.println("Status       : " + transaksi.getStatus());
            if (pakaiAsuransi) {
                System.out.println("Asuransi     : AKTIF (denda kerusakan digratiskan)");
            }

            DatabaseService.saveTransaksi(LoginRentalKendaraan.daftarTransaksi);

            Kendaraan kendaraanUpdated = cariKendaraan(plat);
            if (kendaraanUpdated != null) {
                DatabaseService.saveKendaraan(kendaraanUpdated);
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("\n" + e.getMessage());
        }

        System.out.print("\nTekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    // EPIC 4 - Task 3 (Alwi) : User Story 3, Staff memproses pengembalian kendaraan (Hitung biaya dasar, Denda telat).
    // Case tambahan Kelompok 4: input kondisi kerusakan 
    // Tampilkan struk tagihan akhir & ubah status kendaraan 
    private void menuPengembalian(Scanner input) {
        System.out.println("\n========================================");
        System.out.println("      MENU PENGEMBALIAN KENDARAAN       ");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        System.out.print("Masukkan ID Transaksi: ");
        String idTransaksi = input.nextLine().trim().toUpperCase();
        if (idTransaksi.equals("0"))
            return;

        Transaksi transaksi = null;
        for (Transaksi t : LoginRentalKendaraan.daftarTransaksi) {
            if (t.getIdTransaksi().equalsIgnoreCase(idTransaksi)) {
                transaksi = t;
                break;
            }
        }

        if (transaksi == null) {
            System.out.println("[ERROR] Transaksi tidak ditemukan!");
            return;
        }

        if (transaksi.getStatus().equalsIgnoreCase("SELESAI")) {
            System.out.println("[ERROR] Transaksi sudah selesai!");
            return;
        }

        Kendaraan kendaraan = cariKendaraan(transaksi.getPlatNomor());

        if (kendaraan == null) {
            System.out.println("[ERROR] Data kendaraan tidak ditemukan!");
            return;
        }

        System.out.println("Kendaraan ditemukan " + kendaraan.getJenis() + " (" + kendaraan.getPlatNomor() + ").");

        System.out.print("Durasi Keterlambatan (Hari, isi 0 jika tepat waktu): ");
        int hariTerlambat;
        try {
            hariTerlambat = Integer.parseInt(input.nextLine().trim());
            if (hariTerlambat < 0) {
                System.out.println("[ERROR] Keterlambatan tidak boleh negatif!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Harus berupa angka!");
            return;
        }

        double dendaPerHari = kendaraan.getJenis().equalsIgnoreCase("Mobil") ? 50000 : 20000;
        double biayaDasar = transaksi.getTotalTagihan();
        double dendaTerlambat = hariTerlambat * dendaPerHari;

        System.out.println("\n--- KONDISI KENDARAAN ---");
        System.out.println("0. Tidak Ada Kerusakan");
        System.out.println("1. Kerusakan RINGAN   (Rp 200.000)");
        System.out.println("2. Kerusakan SEDANG   (Rp 500.000)");
        System.out.println("3. Kerusakan BERAT    (Rp 1.000.000)");
        System.out.print("Pilih tingkat kerusakan: ");

        int pilihanKerusakan;
        try {
            pilihanKerusakan = Integer.parseInt(input.nextLine().trim());
            if (pilihanKerusakan < 0 || pilihanKerusakan > 3) {
                System.out.println("[ERROR] Pilihan tidak valid!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Harus berupa angka!");
            return;
        }

        DamageService damageService = new DamageService();
        TingkatKerusakan tingkatKerusakan = null;

        switch (pilihanKerusakan) {
            case 1 -> tingkatKerusakan = TingkatKerusakan.RINGAN;
            case 2 -> tingkatKerusakan = TingkatKerusakan.SEDANG;
            case 3 -> tingkatKerusakan = TingkatKerusakan.BERAT;
        }

        double dendaKerusakan = 0;
        if (tingkatKerusakan != null) {
            dendaKerusakan = damageService.hitungDendaKerusakan(
                    tingkatKerusakan,
                    transaksi.isMenggunakanAsuransi());

            if (dendaKerusakan == 0 && transaksi.isMenggunakanAsuransi()) {
                System.out.println("[INFO] Denda kerusakan digratiskan karena asuransi!");
            }
        }

        double totalAkhir = biayaDasar + dendaTerlambat + dendaKerusakan;

        transaksi.setStatus("SELESAI");
        transaksi.setHariTerlambat(hariTerlambat);
        transaksi.setDenda(dendaTerlambat);
        transaksi.setTotalTagihan(totalAkhir);
        transaksi.setTanggalKembali(new java.sql.Timestamp(System.currentTimeMillis()));

        kendaraan.setStatus("TERSEDIA");

        DatabaseService.saveTransaksi(LoginRentalKendaraan.daftarTransaksi);
        DatabaseService.saveKendaraan(kendaraan);

        System.out.println("\nMenghitung tagihan...");
        System.out.println("\n--- STRUK TAGIHAN AKHIR ---");
        System.out.println("ID Transaksi   : " + transaksi.getIdTransaksi());
        System.out.println("Pelanggan      : " + transaksi.getNamaPelanggan());
        System.out.println("Kendaraan      : " + kendaraan.getJenis() + " (" + transaksi.getPlatNomor() + ")");
        System.out.println("Biaya Dasar    : Rp " + String.format("%,.0f", biayaDasar).replace(",", ".")
                + " (" + transaksi.getDurasiSewa() + " Hari)");

        if (dendaTerlambat > 0) {
            System.out.println("Denda Telat    : Rp " + String.format("%,.0f", dendaTerlambat).replace(",", ".")
                    + " (" + hariTerlambat + " Hari x Rp " + String.format("%,.0f", dendaPerHari).replace(",", ".")
                    + " khusus " + kendaraan.getJenis() + ")");
        }

        if (tingkatKerusakan != null) {
            if (dendaKerusakan > 0) {
                System.out.println("Denda Kerusakan: Rp " + String.format("%,.0f", dendaKerusakan).replace(",", ".")
                        + " (" + tingkatKerusakan + ")");
            } else if (transaksi.isMenggunakanAsuransi()) {
                System.out.println("Denda Kerusakan: Rp 0 (Asuransi aktif - " + tingkatKerusakan + " digratiskan)");
            }
        }

        System.out.println("---------------------------------- +");
        System.out.println("TOTAL BAYAR    : Rp " + String.format("%,.0f", totalAkhir).replace(",", "."));
        System.out.println("----------------------------------");
        System.out.println("[SUKSES] Pengembalian berhasil. Status kendaraan kembali menjadi TERSEDIA.");

        System.out.print("\nTekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    private Kendaraan cariKendaraan(String platNomor) {
        for (Kendaraan k : LoginRentalKendaraan.daftarKendaraan) {
            if (k.getPlatNomor().equalsIgnoreCase(platNomor)) {
                return k;
            }
        }
        return null;
    }
}