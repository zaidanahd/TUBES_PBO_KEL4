package pbotubes;

import java.util.Scanner;

// Task 1.4: Sistem navigasi Dashboard dinamis (Console UI)
public class Dashboard {

    private User user;
    private Scanner scanner;

    public Dashboard(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
    }

    public void tampilkan() {
        boolean sesiAktif = true;

        while (sesiAktif) {
            tampilkanHeader();
            tampilkanMenu();

            System.out.print("\nPilihan Anda > ");
            int pilihan = ambilInputAngka();

            if (pilihan == 0) {
                System.out.println("\n[SUKSES] Logout berhasil!");
                sesiAktif = false;
            } else if (pilihan == -1) {
                System.out.println("\n[ERROR] Input harus berupa angka!");
            } else {
                prosesPilihan(pilihan);
            }
        }
    }

    private void tampilkanHeader() {
        System.out.println("\n========================================");
        System.out.println("     DASHBOARD - " + user.getRoleString());
        System.out.println("========================================");
        System.out.println("Selamat Datang, " + user.getUsername() + "!");
    }

    // Menampilkan menu sesuai hak akses role
    private void tampilkanMenu() {
        System.out.println("\n--- MENU ---");

        switch (user.getRole()) {
            case ADMIN:
                System.out.println("1. Tambah Kendaraan Baru");
                System.out.println("2. Lihat Semua Kendaraan");
                System.out.println("3. Hapus Kendaraan");
                break;
            case STAFF:
                System.out.println("1. Daftar Pelanggan Baru");
                System.out.println("2. Cari Data Pelanggan");
                System.out.println("3. Cek Kendaraan Tersedia");
                System.out.println("4. Proses Peminjaman (Sewa)");
                System.out.println("5. Proses Pengembalian");
                break;
            case OWNER:
                System.out.println("1. Lihat Laporan Pendapatan & Riwayat");
                break;
        }

        System.out.println("0. Logout");
    }

    // Proses pilihan menu
    private void prosesPilihan(int pilihan) {
        System.out.println("\n[Sistem] Menu nomor " + pilihan + " dipilih.");

        boolean pilihanValid = false;

        switch (user.getRole()) {
            case ADMIN:
                pilihanValid = (pilihan >= 1 && pilihan <= 3);
                break;
            case STAFF:
                pilihanValid = (pilihan >= 1 && pilihan <= 5);
                break;
            case OWNER:
                pilihanValid = (pilihan == 1);
                break;
        }

        if (!pilihanValid) {
            System.out.println("[ERROR] Pilihan tidak tersedia untuk role " + user.getRoleString() + "!");
        } else {
            // Placeholder: nanti diisi logika bisnis masing-masing
            System.out.println("[INFO] Fitur sedang dalam pengembangan.");
        }
    }

    // Helper: Input angka dengan error handling
    private int ambilInputAngka() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
