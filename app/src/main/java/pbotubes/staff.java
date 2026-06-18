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
        int pilihan;

        do {
            System.out.println("\n===== DASHBOARD STAFF =====");
            System.out.println("Selamat datang, " + username);
            System.out.println("1. Data Pelanggan (Daftar Pelanggan Baru)");
            System.out.println("2. Cari Data Pelanggan");
            System.out.println("3. Proses Sewa");
            System.out.println("4. Pengembalian");
            System.out.println("5. Lihat Kendaraan Tersedia"); // Task 4.1
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    menuDaftarPelanggan(input);
                    break;
                case 2:
                    menuCariPelanggan(input);
                    break;
                case 3:
                    menuProsesSewa(input); // Task 4.3
                    break;
                case 4:
                    menuPengembalian(input);
                    break;
                case 5:
                    menuLihatKendaraanTersedia(input); // Task 4.1
                    break;
                case 0:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    // 4.1: Menu untuk melihat kendaraan yang tersedia (status "TERSEDIA")
    private void menuLihatKendaraanTersedia(Scanner input) {
        System.out.println("=========================================================================");
        System.out.println("                     DAFTAR KENDARAAN TERSEDIA                           ");
        System.out.println("=========================================================================");

        boolean adaTersedia = false;

        System.out.printf("| %-10s | %-5s | %-12s | %-10s | %-15s |%n",
                "Plat No", "Jenis", "Harga/Hari", "Merk", "Info Tambahan");
        System.out.println("-------------------------------------------------------------------------");

        for (Kendaraan k : App.daftarKendaraan) {
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

    // Task 4.3: Menu proses sewa dengan logika peminjaman lengkap
    private void menuProsesSewa(Scanner input) {
        System.out.println("========================================");
        System.out.println("          MENU PROSES SEWA            ");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");

        // Tampilkan kendaraan tersedia dulu
        System.out.println("\n--- Kendaraan Tersedia ---");
        boolean adaTersedia = false;
        for (Kendaraan k : App.daftarKendaraan) {
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
            System.out.print("Tambah asuransi? (y/n): ");
            String jawab = input.nextLine();
            boolean asuransi = jawab.equalsIgnoreCase("y");
            
            Transaksi transaksi = peminjamanService.prosesPeminjaman(ktp, plat, lamaSewa, asuransi);
            System.out.println("\n[SUKSES] Transaksi berhasil dibuat!");
            System.out.println("ID Transaksi : " + transaksi.idTransaksi);
            System.out.println("Pelanggan    : " + transaksi.namaPelanggan);
            System.out.println("Kendaraan    : " + transaksi.platNomor);
            System.out
                    .println("Estimasi Biaya: Rp " + String.format("%,.0f", transaksi.totalTagihan).replace(",", "."));
            System.out.println("Status       : " + transaksi.status);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("\n" + e.getMessage());
        }

        System.out.print("\nTekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    // Method yang sudah ada (tidak diubah)
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

        for (Pelanggan p : App.daftarPelanggan) {
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
        App.daftarPelanggan.add(pelangganBaru);

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
        for (Pelanggan p : App.daftarPelanggan) {
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

    private void menuPengembalian(Scanner input) {
    System.out.println("========================================");
    System.out.println("         MENU PENGEMBALIAN");
    System.out.println("========================================");

    System.out.print("Masukkan ID Transaksi: ");
    String idTransaksi = input.nextLine();

    // cari transaksi
    Transaksi transaksi = null;

    for (Transaksi t : App.daftarTransaksi) {
        if (t.idTransaksi.equals(idTransaksi)) {
            transaksi = t;
            break;
        }
    }

    if (transaksi == null) {
        System.out.println("Transaksi tidak ditemukan!");
        return;
    }

    System.out.println("\nPilih Tingkat Kerusakan");
    System.out.println("1. RINGAN");
    System.out.println("2. SEDANG");
    System.out.println("3. BERAT");
    System.out.print("Pilihan: ");

    int pilihan = Integer.parseInt(input.nextLine());

    TingkatKerusakan kerusakan;

    switch (pilihan) {
        case 1:
            kerusakan = TingkatKerusakan.RINGAN;
            break;
        case 2:
            kerusakan = TingkatKerusakan.SEDANG;
            break;
        case 3:
            kerusakan = TingkatKerusakan.BERAT;
            break;
        default:
            System.out.println("Pilihan tidak valid!");
            return;
    }

    double total = peminjamanService.prosesPengembalian(transaksi, kerusakan);

    System.out.println("\nPengembalian berhasil!");
    System.out.println("Total Tagihan : Rp " +
            String.format("%,.0f", total).replace(",", "."));
}
}