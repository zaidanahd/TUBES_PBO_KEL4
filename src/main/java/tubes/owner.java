package tubes;

public class owner extends user {

    public owner(String username) {
        super(username);
    }

    @Override
    public void menu() {

        System.out.println("\n===== DASHBOARD OWNER =====");
        System.out.println("Selamat datang, " + username);
        System.out.println("1. Lihat Laporan");
        System.out.println("0. Logout");
    }
}

