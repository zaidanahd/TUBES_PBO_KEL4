package pbotubes.Presentation;
import pbotubes.Model.*;


import java.util.Scanner;


public class owner extends User {

    public owner(String username) {
        super(username);
    }

    @Override
    public void menu() {
        Scanner input = new Scanner(System.in);
        int pilihan = -1;

        do {
            System.out.println("\n===== DASHBOARD OWNER =====");
            System.out.println("Selamat datang, " + username);
            System.out.println("1. Lihat Laporan");
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
                    lihatLaporan(input);
                    break;
                case 0:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);
    }

    public void lihatLaporan(Scanner input) {
        System.out.println("=======================================================================================");
        System.out.printf("%55s\n", "LAPORAN RIWAYAT & PENDAPATAN");
        System.out.println("=======================================================================================");

        System.out.printf("| %-14s | %-18s | %-12s | %-12s | %-16s |\n",
                "ID Transaksi", "Pelanggan", "Kendaraan", "Status", "Total Tagihan");
        System.out.println("---------------------------------------------------------------------------------------");

        double totalPendapatan = 0;

        for (Transaksi t : LoginRentalKendaraan.daftarTransaksi) {
            String txtTagihan;
            if (t.getStatus().equalsIgnoreCase("BERJALAN")) {
                txtTagihan = "-";
            } else {
                totalPendapatan += t.getTotalTagihan();
                txtTagihan = String.format("%,.0f", t.getTotalTagihan()).replace(",", ".");
            }

            System.out.printf("| %-14s | %-18s | %-12s | %-12s | %-16s |\n",
                    t.getIdTransaksi(), t.getNamaPelanggan(), t.getPlatNomor(), t.getStatus(), txtTagihan);
        }

        String totalFormat = String.format("%,.0f", totalPendapatan).replace(",", ".");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("TOTAL PENDAPATAN (Hanya dari Transaksi Selesai) : Rp " + totalFormat);
        System.out.println("=================================================================================");
        System.out.print("Tekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }
}