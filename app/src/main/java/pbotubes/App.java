package pbotubes.app;


class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}

public class App {
    // Di sini Scanner ditulis lengkap dengan paketnya: java.util.Scanner
    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);

    public static void main(String[] args) {
        boolean aplikasiBerjalan = true;

        while (aplikasiBerjalan) {
            System.out.println("==================================================");
            System.out.println("       SELAMAT DATANG DI RENTAL KENDARAAN        ");
            System.out.println("==================================================");

            int loginAttempt = 0;
            User userLogon = null;

            while (loginAttempt < 3 && userLogon == null) {
                System.out.print("Username : > ");
                String inputUser = scanner.nextLine();

                System.out.print("Password : > ");
                String inputPass = scanner.nextLine();

                userLogon = cekAutentikasi(inputUser, inputPass);

                if (userLogon != null) {
                    System.out.println("\n[SUKSES] Login berhasil sebagai " + userLogon.getRole() + ".");
                    System.out.println("Tekan ENTER untuk masuk ke Dashboard...");
                    scanner.nextLine();
                } else {
                    loginAttempt++;
                    if (loginAttempt < 3) {
                        System.out.println("\n[ERROR] Login Gagal! Username atau Password salah.");
                        System.out.println("Sisa percobaan Anda : " + (3 - loginAttempt) + "\n");
                    } else {
                        System.out.println("\n[ERROR] Anda gagal login 3 kali.");
                        System.out.println("Akses sistem ditutup!");
                        aplikasiBerjalan = false; 
                    }
                }
            }

            if (userLogon != null) {
                jalankanDashboard(userLogon);
            }
        }
    }

    private static User cekAutentikasi(String username, String password) {
        try {
            String pathFile = "app/src/main/resources/users.json";
            java.io.File file = new java.io.File(pathFile);
            
            if (!file.exists()) {
                pathFile = "src/main/resources/users.json";
                file = new java.io.File(pathFile);
            }

            if (!file.exists()) {
                System.out.println("\n[WARNING] Database 'users.json' tidak ditemukan!");
                return null;
            }

            // Di sini Files dan Paths ditulis lengkap paketnya
            String isiJson = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(pathFile)), java.nio.charset.StandardCharsets.UTF_8);

            String regex = "\\{\\s*\"username\"\\s*:\\s*\"" + username + "\"\\s*,\\s*\"password\"\\s*:\\s*\"" + password + "\"\\s*,\\s*\"role\"\\s*:\\s*\"([A-Z]+)\"\\s*\\}";
            
            // Di sini Pattern dan Matcher ditulis lengkap paketnya
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
            java.util.regex.Matcher matcher = pattern.matcher(isiJson);

            if (matcher.find()) {
                String roleDitemukan = matcher.group(1); 
                return new User(username, password, roleDitemukan);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal membaca database: " + e.getMessage());
        }
        return null; 
    }

    private static void jalankanDashboard(User user) {
        boolean sesiDashboard = true;

        while (sesiDashboard) {
            System.out.println("\n========================================");
            System.out.println("          DASHBOARD - " + user.getRole());
            System.out.println("========================================");
            System.out.println("Selamat Datang, " + user.getUsername() + "!");
            System.out.println("Silahkan pilih menu:");

            if (user.getRole().equals("ADMIN")) {
                System.out.println("1. Tambah Kendaraan Baru");
                System.out.println("2. Lihat Semua Kendaraan");
                System.out.println("3. Hapus Kendaraan");
            } else if (user.getRole().equals("STAFF")) {
                System.out.println("1. Daftar Pelanggan Baru");
                System.out.println("2. Cari Data Pelanggan");
                System.out.println("3. Cek Kendaraan Tersedia");
                System.out.println("4. Proses Peminjaman (Sewa)");
                System.out.println("5. Proses Pengembalian");
            } else if (user.getRole().equals("OWNER")) {
                System.out.println("1. Lihat Laporan Pendapatan & Riwayat");
            }
            System.out.println("0. Logout");
            System.out.print("\nPilihan Anda > ");

            int pilihan = ambilInputAngka();

            if (pilihan == 0) {
                System.out.println("\n[SUKSES] Logout berhasil! Kembali ke menu login.");
                sesiDashboard = false; 
            } else if (pilihan == -1) {
                System.out.println("[ERROR] Input harus berupa angka valid!");
            } else {
                System.out.println("\n[Sistem] Kamu memilih menu nomor " + pilihan + ".");
            }
        }
    }

    private static int ambilInputAngka() {
        try {
            int angka = scanner.nextInt();
            scanner.nextLine(); 
            return angka;
        } catch (Exception e) {
            scanner.nextLine(); 
            return -1; 
        }
    }
}