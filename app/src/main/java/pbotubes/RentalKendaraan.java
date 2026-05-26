import java.util.ArrayList;
import java.util.Scanner;

public class RentalKendaraan {
    static ArrayList<Kendaraan> daftarKendaraan = new ArrayList<Kendaraan>();
    static Scanner input = new Scanner(System.in);
    static void menuTambahKendaraan() {
        System.out.println("========================================");
        System.out.println("      MENU TAMBAH KENDARAAN BARU");
        System.out.println("========================================");
        System.out.println("Pilih Jenis Kendaraan:");
        System.out.println("1. Mobil");
        System.out.println("2. Motor");
        System.out.println("0. Kembali");
        System.out.print("Pilihan Anda > ");
        int pilihanJenis = input.nextInt();
        input.nextLine();

        if (pilihanJenis == 0) {
            return;
        }
        if (pilihanJenis != 1 && pilihanJenis != 2) {
            System.out.println("[ERROR] Pilihan jenis tidak valid.");
            return;
        }

        String jenis;
        if (pilihanJenis == 1) {
            jenis = "Mobil";
        } else {
            jenis = "Motor";
        }

        System.out.print("Masukkan Plat Nomor    : ");
        String platNomor = input.nextLine().toUpperCase();

        boolean platSudahAda = false; // buat cek platnya udah ada apa belum
        for (int i = 0; i < daftarKendaraan.size(); i++) {
            if (daftarKendaraan.get(i).platNomor.equalsIgnoreCase(platNomor)) {
                platSudahAda = true;
                break;
            }
        }

        if (platSudahAda) {
            System.out.println("[ERROR] Plat Nomor " + platNomor + " sudah terdaftar.");
            return;
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
            System.out.println("Pilih Jenis Transmisi:");
            System.out.println("1. Manual"); // dibuat pilihan agar tidak ada jawaban selain manual dan matic
            System.out.println("2. Matic");
            System.out.print("Pilihan Anda > ");
            int pilihanTransmisi = input.nextInt();
            input.nextLine();

            if (pilihanTransmisi == 1) {
                infoTambahan = "Manual";
            } else if (pilihanTransmisi == 2) {
                infoTambahan = "Matic";
            } else {
                System.out.println("[ERROR] Pilihan transmisi tidak valid.");
                return;
            }
        }

        Kendaraan kendaraanBaru = new Kendaraan(platNomor, jenis, hargaSewa, merk, infoTambahan);
        daftarKendaraan.add(kendaraanBaru);

        System.out.println("[SUKSES] " + jenis + " dengan plat " + platNomor
                + " berhasil ditambahkan ke dalam sistem dengan status TERSEDIA.");
        System.out.print("Tekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    static void menuLihatKendaraan() { // menu 2 admin
        System.out.println("========================================");
        System.out.println("       DAFTAR SELURUH KENDARAAN");
        System.out.println("========================================");

        if (daftarKendaraan.size() == 0) {
            System.out.println("Data kendaraan masih kosong.");
            System.out.print("Tekan ENTER untuk kembali...");
            input.nextLine();
            return;
        }

        System.out.printf("%-15s %-7s %-12s %-12s %-12s %-14s%n",
                "Plat Nomor", "Jenis", "Merk", "Harga/Hari",
                "Info Tambahan", "Status");
        System.out.println("------------------------------------------------------------------------");

        for (int i = 0; i < daftarKendaraan.size(); i++) {
            Kendaraan k = daftarKendaraan.get(i);
            System.out.printf("%-15s %-7s %-12s %-12.0f %-12s %-14s%n",
                    k.platNomor,
                    k.jenis,
                    k.merk,
                    k.hargaSewa,
                    k.infoTambahan,
                    k.status);
        }

        System.out.print("\nTekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }

    static void menuHapusKendaraan() { // menu 3 admin
        System.out.println("========================================");
        System.out.println("          MENU HAPUS KENDARAAN");
        System.out.println("========================================");
        System.out.println("(ketik 0 untuk kembali)");
        System.out.print("Masukkan Plat Nomor yang ingin dihapus : ");
        String plat = input.nextLine().toUpperCase();

        if (plat.equals("0")) {
            return;
        }

        int indexDitemukan = -1; // -1 artinya plat tidak ditemukan
        for (int i = 0; i < daftarKendaraan.size(); i++) { // perulangan buat nyari kendaraannya
            if (daftarKendaraan.get(i).platNomor.equalsIgnoreCase(plat)) {
                indexDitemukan = i;
                break;
            }
        }

        if (indexDitemukan == -1) { // kalo misalnya plat ga ditemuin
            System.out.println("[GAGAL] Kendaraan dengan Plat Nomor " + plat
                    + " tidak ditemukan di sistem.");

        } else if (daftarKendaraan.get(indexDitemukan).status.equals("Sedang Disewa")) { // kalo kendaraannya masih di sewain trs di tolak
            System.out.println("[GAGAL] Kendaraan masih berstatus SEDANG DISEWA, " +
                    "data tidak dapat dihapus!");

            System.out.print("Masukkan Plat Nomor yang ingin dihapus (ketik 0 untuk keluar): ");
            String plat2 = input.nextLine().toUpperCase(); // input ulang

            if (!plat2.equals("0")) {
                int index2 = -1;
                for (int i = 0; i < daftarKendaraan.size(); i++) {
                    if (daftarKendaraan.get(i).platNomor.equalsIgnoreCase(plat2)) {
                        index2 = i;
                        break;
                    }
                }

                if (index2 == -1) {
                    System.out.println("[GAGAL] Kendaraan tidak ditemukan.");
                } else if (daftarKendaraan.get(index2).status.equals("Sedang Disewa")) {
                    System.out.println("[GAGAL] Kendaraan masih berstatus SEDANG DISEWA, " +
                            "data tidak dapat dihapus!");
                } else {
                    daftarKendaraan.remove(index2);
                    System.out.println("[SUKSES] Kendaraan " + plat2 + " berhasil dihapus dari sistem.");
                }
            }

        } else { // kalo kendaraannya ga di sewain
            daftarKendaraan.remove(indexDitemukan);
            System.out.println("[SUKSES] Kendaraan " + plat + " berhasil dihapus dari sistem.");
        }

        System.out.print("Tekan ENTER untuk kembali ke menu utama...");
        input.nextLine();
    }
}