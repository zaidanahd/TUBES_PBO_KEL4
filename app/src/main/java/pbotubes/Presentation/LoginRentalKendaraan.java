package pbotubes.Presentation;
import pbotubes.Model.*;
import pbotubes.Service.*;
import java.util.ArrayList;
import java.util.Scanner;

//Main class
//EPIC 1 (Silvi) : Mencakup User Story 1, Otentikasi (Login) berbasis role, termasuk batas maksimal 3 kali percobaan login.
public class LoginRentalKendaraan {
    public static ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();
    public static ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
    public static ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Init database (buat tabel & user default)
        DatabaseInitService dbInit = new DatabaseInitService();
        dbInit.initialize();

        // Load data dari MySQL ke ArrayList
        daftarKendaraan = DatabaseService.loadKendaraan();
        daftarPelanggan = DatabaseService.loadPelanggan();
        daftarTransaksi = DatabaseService.loadTransaksi();

        // Akun fallback (kalau DB kosong)
        String[][] akun = {
                { "admin", "123", "ADMIN" },
                { "staff", "123", "STAFF" },
                { "owner", "123", "OWNER" }
        };

        // EPIC 1 - Task 1 (Silvi) : User Story 1, Login dengan username & password, maksimal 3 kali percobaan.
        // Jika cocok ,  arahkan ke menu sesuai role (Admin/Staff/Owner) via Polymorphism.
        int percobaan = 0;
        boolean login = false;

        while (percobaan < 3 && !login) {
            System.out.print("Username : ");
            String username = input.nextLine();
            System.out.print("Password : ");
            String password = input.nextLine();

            User user = DatabaseService.login(username, password);

            if (user == null) {
                // Fallback ke akun hardcoded
                for (int i = 0; i < akun.length; i++) {
                    if (username.equals(akun[i][0]) && password.equals(akun[i][1])) {
                        login = true;
                        user = switch (akun[i][2]) {
                            case "ADMIN" -> new admin(username);
                            case "STAFF" -> new staff(username);
                            case "OWNER" -> new owner(username);
                            default -> null;
                        };
                        break;
                    }
                }
            } else {
                login = true;
            }

            if (login && user != null) {
                System.out.println("\nLogin berhasil!");
                user.menu();
            } else {
                percobaan++;
                System.out.println("Login gagal!");
                if (percobaan == 3) {
                    System.out.println("Akses ditolak!");
                }
            }
        }

        input.close();
    }
}