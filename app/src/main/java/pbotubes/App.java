package pbotubes;

import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inisialisasi komponen
        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);

        boolean aplikasiBerjalan = true;

        while (aplikasiBerjalan) {
            tampilkanHeader("SELAMAT DATANG DI RENTAL KENDARAAN");

            // Cek apakah file database ada
            if (!userRepository.isFileFound()) {
                System.out.println("[ERROR] Database tidak ditemukan. Program ditutup.");
                break;
            }

            // Proses login
            User userLogon = prosesLogin(authService);

            if (userLogon != null) {
                // Login berhasil → tampilkan dashboard
                Dashboard dashboard = new Dashboard(userLogon);
                dashboard.tampilkan();
            } else {
                // Gagal login 3 kali → tutup aplikasi
                aplikasiBerjalan = false;
            }
        }

        scanner.close();
        System.out.println("\nProgram selesai. Terima kasih!");
        System.out.println ("Test"); 
    }

    // Task 1.3: Proses login dengan max 3 attempts
    private static User prosesLogin(AuthService authService) {
        int attempt = 0;
        int maxAttempts = authService.getMaxAttempts();
        User userLogon = null;

        while (attempt < maxAttempts && userLogon == null) {
            System.out.println("\n--- LOGIN ---");
            System.out.println("Percobaan ke-" + (attempt + 1) + " dari " + maxAttempts);

            System.out.print("Username : > ");
            String username = scanner.nextLine().trim();

            System.out.print("Password : > ");
            String password = scanner.nextLine().trim();

            // Validasi ke AuthService
            userLogon = authService.login(username, password);

            if (userLogon != null) {
                System.out.println("\n[SUKSES] Login berhasil sebagai " + userLogon.getRoleString() + "!");
                System.out.println("Tekan ENTER untuk melanjutkan...");
                scanner.nextLine();
            } else {
                attempt++;
                if (attempt < maxAttempts) {
                    System.out.println("\n[ERROR] Login gagal! Username atau Password salah.");
                    System.out.println("Sisa percobaan: " + (maxAttempts - attempt));
                } else {
                    System.out.println("\n[ERROR] Anda gagal login " + maxAttempts + " kali.");
                    System.out.println("Akses sistem ditutup!");
                }
            }
        }

        return userLogon;
    }

    private static void tampilkanHeader(String judul) {
        System.out.println("\n==================================================");
        System.out.println("       " + judul);
        System.out.println("==================================================");
    }

}