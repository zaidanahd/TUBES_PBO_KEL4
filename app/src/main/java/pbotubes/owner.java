package pbotubes;

import java.util.Scanner;

public class owner extends user {

    public owner(String username) {
        super(username);
    }

   @Override

    public void menu(){

    try (Scanner input = new Scanner(System.in)) {

    int pilihan;

    do{

        System.out.println("\n===== DASHBOARD OWNER =====");

        System.out.println("Selamat datang, " + username);

        System.out.println("1. Lihat Laporan");

        System.out.println("0. Logout");

        System.out.print("Pilihan Anda > ");

        pilihan = input.nextInt();
        input.nextLine();

        switch(pilihan){

            case 1:

                lihatLaporan(input);

                break;

            case 0:

                System.out.println("Logout berhasil.");

                break;

            default:

                System.out.println("Pilihan tidak valid!");

        }

    }

    while(pilihan!=0);

    }

    }

    public void lihatLaporan(Scanner input) {
    System.out.println("=======================================================================================");
    System.out.printf("%55s\n", "LAPORAN RIWAYAT & PENDAPATAN");
    System.out.println("=======================================================================================");

    System.out.println("=======================================================================================");

    System.out.printf(

    "| %-14s | %-18s | %-12s | %-12s | %-16s |\n",

    "ID Transaksi",

    "Pelanggan",

    "Kendaraan",

    "Status",

    "Total Tagihan"

    );

    System.out.println(
    "---------------------------------------------------------------------------------------");
    
    //total 
    double totalPendapatan=0;

    for (Transaksi t : App.daftarTransaksi) {

        String txtTagihan;

        if (t.status.equalsIgnoreCase("BERJALAN")) {
            txtTagihan = "-";
        } else {
            totalPendapatan += t.totalTagihan;
            txtTagihan = String.format("%,.0f", t.totalTagihan).replace(",", ".");
        }

        System.out.printf("| %-14s | %-18s | %-12s | %-12s | %-16s |\n",
                t.idTransaksi,
                t.namaPelanggan,
                t.platNomor,
                t.status,
                txtTagihan);
    }

    String totalFormat =

    String.format("%,.0f", totalPendapatan).replace(",", ".");

    System.out.println ("TOTAL PENDAPATAN : Rp " + totalFormat);

    System.out.println("---------------------------------------------------------------------------------");
    System.out.println("TOTAL PENDAPATAN (Hanya dari Transaksi Selesai) : Rp " + totalFormat);
    System.out.println("=================================================================================");
        
    System.out.print("Tekan ENTER untuk kembali ke menu utama...");
    input.nextLine();


    }


}

