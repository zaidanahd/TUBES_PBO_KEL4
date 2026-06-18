package pbotubes;

import java.util.Scanner;

public class admin extends user {

    public admin(String username) {
        super(username);
    }

    @Override
    public void menu() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        // Loop menu agar admin tidak langsung log out setelah memilih satu menu
        do {
            System.out.println("\n===== DASHBOARD ADMIN =====");
            System.out.println("Selamat datang, " + username);
            System.out.println("1. Tambah Kendaraan");
            System.out.println("2. Lihat Kendaraan");
            System.out.println("3. Hapus Kendaraan");
            System.out.println("0. Logout");
            System.out.print("Pilihan Anda > ");
            pilihan = input.nextInt();
            input.nextLine(); // clear buffer

            switch (pilihan) {
                case 1:
                    menuTambahKendaraan(input);
                    break;
                case 2:
                    menuLihatKendaraan(input);
                    break;
                case 3:
                    menuHapusKendaraan(input);
                    break;
                case 0:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("[ERROR] Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    //menu untuk menambahkan kendaraan
    private void menuTambahKendaraan(Scanner input) {
        System.out.println("========================================");
        System.out.println("       MENU TAMBAH KENDARAAN BARU");
        System.out.println("========================================");
        System.out.println("Pilih Jenis Kendaraan:");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda > ");
        int pilihanJenis = input.nextInt();
        input.nextLine();

        if (pilihanJenis == 0) return;
        if (pilihanJenis != 1 && pilihanJenis != 2) {
            System.out.println("[ERROR] Pilihan jenis tidak valid.");
            return;
        }

        String jenis = (pilihanJenis == 1) ? "Mobil" : "Motor";

        System.out.print("Masukkan Plat Nomor      : ");
        String platNomor = input.nextLine().toUpperCase();

        // Cek keunikan plat nomor dari arraylist global di LoginRentalKendaraan
        for (Kendaraan k : App.daftarKendaraan) {
            if (k.platNomor.equalsIgnoreCase(platNomor)) {
                System.out.println("[ERROR] Plat Nomor sudah terdaftar sebelumnya (harus unik).");
                return;
            }
        }

        System.out.print("Masukkan Harga Sewa/Hari : ");
        double hargaSewa = input.nextDouble();
        input.nextLine();

        System.out.print("Masukkan Merk Kendaraan  : ");
        String merk = input.nextLine();

        String infoTambahan = "";
        if (jenis.equals("Mobil")) {
            System.out.print("Masukkan Jumlah Pintu    : ");
            int jumlahPintu = input.nextInt();
            input.nextLine();
            infoTambahan = jumlahPintu + " Pintu";
        } else {
            System.out.print("Masukkan Info Tambahan (Jenis Transmisi Manual/Matic): ");
            infoTambahan = input.nextLine();
        }

        Kendaraan kendaraanBaru = new Kendaraan(platNomor, jenis, hargaSewa, merk, infoTambahan);
        App.daftarKendaraan.add(kendaraanBaru);

        System.out.println("\n[SUKSES] " + jenis + " dengan plat " + platNomor 
                + " berhasil ditambahkan ke dalam sistem dengan status TERSEDIA.");
        System.out.print("Tekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    // menu untuk melihat daftar kendaraan yang sudah terdaftar di sistem
    private void menuLihatKendaraan(Scanner input) {
        System.out.println("=========================================================================");
        System.out.println("                        DAFTAR SELURUH KENDARAAN                         ");
        System.out.println("=========================================================================");

        if (App.daftarKendaraan.isEmpty()) {
            System.out.println("Data kendaraan masih kosong.");
            System.out.print("Tekan ENTER untuk kembali ke menu utama...");
            input.nextLine();
            return;
        }

        System.out.printf("| %-10s | %-5s | %-12s | %-10s | %-15s | %-10s |%n", 
                "Plat No", "Jenis", "Harga/Hari", "Merk", "Info Tambahan", "Status");
        System.out.println("-------------------------------------------------------------------------");

        for (Kendaraan k : App.daftarKendaraan) {
            System.out.printf("| %-10s | %-5s | Rp %-8.0f | %-10s | %-15s | %-10s |%n", 
                    k.platNomor, k.jenis, k.hargaSewa, k.merk, k.infoTambahan, k.status);
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.print("Tekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    // menu untuk menghapus kendaraan
    private void menuHapusKendaraan(Scanner input) {
        while (true) {
            System.out.println("========================================");
            System.out.println("          MENU HAPUS KENDARAAN          ");
            System.out.println("========================================");
            System.out.println("(ketik 0 untuk kembali)");
            System.out.print("Masukkan Plat Nomor yang ingin dihapus : ");
            String plat = input.nextLine().toUpperCase();

            if (plat.equals("0")) break;

            int indexDitemukan = -1;
            for (int i = 0; i < App.daftarKendaraan.size(); i++) {
                if (App.daftarKendaraan.get(i).platNomor.equalsIgnoreCase(plat)) {
                    indexDitemukan = i;
                    break;
                }
            }

            if (indexDitemukan == -1) {
                System.out.println("[GAGAL] Kendaraan dengan Plat Nomor " + plat + " tidak ditemukan.\n");
                // Loop berlanjut untuk meminta input kembali sesuai UI
            } else {
                Kendaraan k = App.daftarKendaraan.get(indexDitemukan);
                
                // Pengecekan jika status sedang disewa (Case-insensitive)
                if (k.status.equalsIgnoreCase("SEDANG DISEWA")) {
                    System.out.println("[GAGAL] Kendaraan masih berstatus SEDANG DISEWA, data tidak dapat dihapus!\n");
                    // Loop berlanjut untuk meminta input kembali sesuai instruksi screenshot 3
                } else {
                    App.daftarKendaraan.remove(indexDitemukan);
                    System.out.println("[SUKSES] Kendaraan " + plat + " berhasil dihapus dari sistem.");
                    System.out.print("Tekan ENTER untuk kembali ke menu utama...");
                    input.nextLine();
                    break; // Keluar kembali ke menu dashboard admin
                }
            }
        }
    }
}