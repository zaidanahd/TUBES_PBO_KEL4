package pbotubes.Presentation;

import pbotubes.Model.*;
import pbotubes.Service.*;

import java.util.Scanner;

//Dashboard & menu untuk role ADMIN
//EPIC 2 (Intan) : Mencakup seluruh User Story Epic 2,  Manajemen Inventaris Kendaraan
public class admin extends User {

    public admin(String username) {
        super(username);
    }

    // EPIC 1 - Task 2 (Silvi) : Override menu() agar Admin hanya melihat menu Manajemen Kendaraan (Epic 2),
    // sesuai acceptance criteria pembatasan akses per role.

    @Override
    public void menu() {
        Scanner input = new Scanner(System.in);
        int pilihan = -1;

        do {
            System.out.println("\n===== DASHBOARD ADMIN =====");
            System.out.println("Selamat datang, " + username);
            System.out.println("1. Tambah Kendaraan");
            System.out.println("2. Lihat Kendaraan");
            System.out.println("3. Hapus Kendaraan");
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

    // EPIC 2 - Task 1 (Intan) : User Story 1,  Admin menambahkan data kendaraan baru (Mobil/Motor).
    private void menuTambahKendaraan(Scanner input) {
        System.out.println("========================================");
        System.out.println("       MENU TAMBAH KENDARAAN BARU");
        System.out.println("========================================");
        System.out.println("Pilih Jenis Kendaraan:");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda > ");

        int pilihanJenis;
        try {
            pilihanJenis = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Pilihan harus angka!");
            return;
        }

        if (pilihanJenis == 0)
            return;
        if (pilihanJenis != 1 && pilihanJenis != 2) {
            System.out.println("[ERROR] Pilihan jenis tidak valid.");
            return;
        }

        String jenis = (pilihanJenis == 1) ? "Mobil" : "Motor";

        System.out.print("Masukkan Plat Nomor      : ");
        String platNomor = input.nextLine().toUpperCase();

        // Cek duplikat plat nomor 
        for (Kendaraan k : LoginRentalKendaraan.daftarKendaraan) {
            if (k.getPlatNomor().equalsIgnoreCase(platNomor)) {
                System.out.println("[ERROR] Plat Nomor sudah terdaftar sebelumnya (harus unik).");
                return;
            }
        }

        System.out.print("Masukkan Harga Sewa/Hari : ");
        double hargaSewa;
        try {
            hargaSewa = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Harga harus angka!");
            return;
        }

        System.out.print("Masukkan Merk Kendaraan  : ");
        String merk = input.nextLine();

        String infoTambahan = "";
        if (jenis.equals("Mobil")) {
            System.out.print("Masukkan Jumlah Pintu    : ");
            int jumlahPintu = Integer.parseInt(input.nextLine());
            infoTambahan = jumlahPintu + " Pintu";
        } else {
            System.out.print("Masukkan Info Tambahan (Jenis Transmisi Manual/Matic): ");
            infoTambahan = input.nextLine();
        }

        Kendaraan kendaraanBaru = new Kendaraan(platNomor, jenis, hargaSewa, merk, infoTambahan);
        LoginRentalKendaraan.daftarKendaraan.add(kendaraanBaru);

        DatabaseService.saveKendaraan(kendaraanBaru);

        System.out.println("\n[SUKSES] " + jenis + " dengan plat " + platNomor
                + " berhasil ditambahkan ke dalam sistem dengan status TERSEDIA.");
        System.out.print("Tekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    // EPIC 2 - Task 2 (Intan) : User Story 2, Admin melihat seluruh daftar kendaraan beserta status terkini.
    private void menuLihatKendaraan(Scanner input) {
        System.out.println("=========================================================================");
        System.out.println("                        DAFTAR SELURUH KENDARAAN                         ");
        System.out.println("=========================================================================");

        if (LoginRentalKendaraan.daftarKendaraan.isEmpty()) {
            System.out.println("Data kendaraan masih kosong.");
            System.out.print("Tekan ENTER untuk kembali ke menu utama...");
            input.nextLine();
            return;
        }

        System.out.printf("| %-10s | %-5s | %-12s | %-10s | %-15s | %-10s |%n",
                "Plat No", "Jenis", "Harga/Hari", "Merk", "Info Tambahan", "Status");
        System.out.println("-------------------------------------------------------------------------");

        for (Kendaraan k : LoginRentalKendaraan.daftarKendaraan) {
            System.out.printf("| %-10s | %-5s | Rp %-8.0f | %-10s | %-15s | %-10s |%n",
                    k.getPlatNomor(), k.getJenis(), k.getHargaSewa(), k.getMerk(), k.getInfoTambahan(), k.getStatus());
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.print("Tekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    // EPIC 2 - Task 3 (Intan) : User Story 3, Admin menghapus data kendaraan.
    private void menuHapusKendaraan(Scanner input) {
        while (true) {
            System.out.println("========================================");
            System.out.println("          MENU HAPUS KENDARAAN          ");
            System.out.println("========================================");
            System.out.println("(ketik 0 untuk kembali)");
            System.out.print("Masukkan Plat Nomor yang ingin dihapus : ");
            String plat = input.nextLine().toUpperCase();

            if (plat.equals("0"))
                break;

            int indexDitemukan = -1;
            for (int i = 0; i < LoginRentalKendaraan.daftarKendaraan.size(); i++) {
                if (LoginRentalKendaraan.daftarKendaraan.get(i).getPlatNomor().equalsIgnoreCase(plat)) {
                    indexDitemukan = i;
                    break;
                }
            }

            if (indexDitemukan == -1) {
                System.out.println("[GAGAL] Kendaraan dengan Plat Nomor " + plat + " tidak ditemukan.\n");
            } else {
                Kendaraan k = LoginRentalKendaraan.daftarKendaraan.get(indexDitemukan);

                if (k.getStatus().equalsIgnoreCase("SEDANG DISEWA")) {
                    System.out.println("[GAGAL] Kendaraan masih berstatus SEDANG DISEWA, data tidak dapat dihapus!\n");
                } else {
                    LoginRentalKendaraan.daftarKendaraan.remove(indexDitemukan);

                    DatabaseService.deleteKendaraan(plat);

                    System.out.println("[SUKSES] Kendaraan " + plat + " berhasil dihapus dari sistem.");
                    System.out.print("Tekan ENTER untuk kembali ke menu utama...");
                    input.nextLine();
                    break;
                }
            }
        }
    }
}