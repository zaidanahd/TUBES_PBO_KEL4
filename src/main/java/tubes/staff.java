package tubes;

public class staff extends user {

    public staff(String username) {
        super(username);
    }

    @Override
    public void menu() {

        System.out.println("\n===== DASHBOARD STAFF =====");
        System.out.println("Selamat datang, " + username);
        System.out.println("1. Data Pelanggan");
        System.out.println("2. Proses Sewa");
        System.out.println("3. Pengembalian");
        System.out.println("0. Logout");
    }

} 

