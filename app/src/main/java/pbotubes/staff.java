package pbotubes;

import java.util.Scanner;

public class staff extends User {

    private PeminjamanService peminjamanService;

    public staff(String username) {
        super(username);
        this.peminjamanService = new PeminjamanService();
    }

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
            if (p.ktp.equals(ktp)) {
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

        // ← PAKAI DatabaseService
        DatabaseService.savePelanggan(pelangganBaru);

        System.out.println("\n[SUKSES] Pelanggan " + nama + " (KTP: " + ktp + ") berhasil didaftarkan.");
    }

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
            if (p.ktp.equals(ktpCari)) {
                ditemukan = p;
                break;
            }
        }

        if (ditemukan != null) {
            System.out.println("\n[DATA DITEMUKAN]");
            System.out.println("Nama Lengkap : " + ditemukan.nama);
            System.out.println("Nomor KTP    : " + ditemukan.ktp);
            System.out.println("No Telepon   : " + ditemukan.noTelepon);
        } else {
            System.out.println("\nData pelanggan tidak ditemukan.");
        }
    }

    private void menuLihatKendaraanTersedia(Scanner input) {
        System.out.println("=========================================================================");
        System.out.println("                     DAFTAR KENDARAAN TERSEDIA                           ");
        System.out.println("=========================================================================");

        boolean adaTersedia = false;

        System.out.printf("| %-10s | %-5s | %-12s | %-10s | %-15s |%n",
                "Plat No", "Jenis", "Harga/Hari", "Merk", "Info Tambahan");
        System.out.println("-------------------------------------------------------------------------");

        for (Kendaraan k : LoginRentalKendaraan.daftarKendaraan) {
            if (k.status.equalsIgnoreCase("TERSEDIA")) {
                System.out.printf("| %-10s | %-5s | Rp %-8.0f | %-10s | %-15s |%n",
                        k.platNomor, k.jenis, k.hargaSewa, k.merk, k.infoTambahan);
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

    private void menuProsesSewa(Scanner input) {
        System.out.println("========================================");
        System.out.println("          MENU PROSES SEWA            ");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        System.out.println("\n--- Kendaraan Tersedia ---");
        boolean adaTersedia = false;
        for (Kendaraan k : LoginRentalKendaraan.daftarKendaraan) {
            if (k.status.equalsIgnoreCase("TERSEDIA")) {
                System.out.printf("- %s (%s %s) - Rp %.0f/hari%n",
                        k.platNomor, k.merk, k.jenis, k.hargaSewa);
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

        try {
            Transaksi transaksi = peminjamanService.prosesPeminjaman(ktp, plat, lamaSewa);
            System.out.println("\n[SUKSES] Transaksi berhasil dibuat!");
            System.out.println("ID Transaksi : " + transaksi.idTransaksi);
            System.out.println("Pelanggan    : " + transaksi.namaPelanggan);
            System.out.println("Kendaraan    : " + transaksi.platNomor);
            System.out
                    .println("Estimasi Biaya: Rp " + String.format("%,.0f", transaksi.totalTagihan).replace(",", "."));
            System.out.println("Status       : " + transaksi.status);

            // ← PAKAI DatabaseService
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
            if (t.idTransaksi.equalsIgnoreCase(idTransaksi)) {
                transaksi = t;
                break;
            }
        }

        if (transaksi == null) {
            System.out.println("[ERROR] Transaksi tidak ditemukan!");
            return;
        }

        if (transaksi.status.equalsIgnoreCase("SELESAI")) {
            System.out.println("[ERROR] Transaksi sudah selesai!");
            return;
        }

        Kendaraan kendaraan = cariKendaraan(transaksi.platNomor);

        if (kendaraan == null) {
            System.out.println("[ERROR] Data kendaraan tidak ditemukan!");
            return;
        }

        System.out.println("Kendaraan ditemukan " + kendaraan.jenis + " (" + kendaraan.platNomor + ").");

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

        double dendaPerHari = kendaraan.jenis.equalsIgnoreCase("Mobil") ? 50000 : 20000;
        double biayaDasar = transaksi.totalTagihan;
        double denda = hariTerlambat * dendaPerHari;
        double totalAkhir = biayaDasar + denda;

        transaksi.setStatus("SELESAI");
        transaksi.setHariTerlambat(hariTerlambat);
        transaksi.setDenda(denda);
        transaksi.setTotalTagihan(totalAkhir);

        kendaraan.setStatus("TERSEDIA");

        // ← PAKAI DatabaseService
        DatabaseService.saveTransaksi(LoginRentalKendaraan.daftarTransaksi);
        DatabaseService.saveKendaraan(kendaraan);

        System.out.println("\nMenghitung tagihan...");
        System.out.println("\n--- STRUK TAGIHAN AKHIR ---");
        System.out.println("ID Transaksi   : " + transaksi.idTransaksi);
        System.out.println("Pelanggan      : " + transaksi.namaPelanggan);
        System.out.println("Kendaraan      : " + kendaraan.jenis + " (" + transaksi.platNomor + ")");
        System.out.println("Biaya Dasar    : Rp " + String.format("%,.0f", biayaDasar).replace(",", ".")
                + " (" + transaksi.getDurasiSewa() + " Hari)");
        if (denda > 0) {
            System.out.println("Denda Telat    : Rp " + String.format("%,.0f", denda).replace(",", ".")
                    + " (" + hariTerlambat + " Hari x Rp " + String.format("%,.0f", dendaPerHari).replace(",", ".")
                    + " khusus " + kendaraan.jenis + ")");
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
            if (k.platNomor.equalsIgnoreCase(platNomor)) {
                return k;
            }
        }
        return null;
    }
}