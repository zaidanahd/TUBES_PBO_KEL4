package tubes;

public class admin extends user {

    public admin(String username) {
        super(username);
    }

    @Override
    public void menu() {

        System.out.println("\n===== DASHBOARD ADMIN =====");
        System.out.println("Selamat datang, " + username);
        System.out.println("1. Tambah Kendaraan");
        System.out.println("2. Lihat Kendaraan");
        System.out.println("3. Hapus Kendaraan");
        System.out.println("0. Logout");
    }
}