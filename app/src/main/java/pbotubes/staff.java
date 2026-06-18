package pbotubes;

import java.util.Scanner;

public class staff extends user {

    public staff(String username) {
        super(username);
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
                    System.out.println("[INFO] Fitur Proses Sewa belum diimplementasikan.");
                    break;
                case 4:
                    System.out.println("[INFO] Fitur Pengembalian belum diimplementasikan.");
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

        if (ktp.equals("0")) return;

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

        if (ktpCari.equals("0")) return;

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
}