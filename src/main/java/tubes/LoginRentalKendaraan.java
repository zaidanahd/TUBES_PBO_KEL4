package tubes;

import java.util.Scanner;

public class LoginRentalKendaraan {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String[][] akun = {
                {"admin", "123", "ADMIN"},
                {"staff", "123", "STAFF"},
                {"owner", "123", "OWNER"}
        };

        int percobaan = 0;
        boolean login = false;

        while (percobaan < 3 && !login) {

            System.out.print("Username : ");
            String username = input.nextLine();

            System.out.print("Password : ");
            String password = input.nextLine();

            for (int i = 0; i < akun.length; i++) {

                if (username.equals(akun[i][0]) &&
                    password.equals(akun[i][1])) {

                    login = true;

                    user user = null;

                    if (akun[i][2].equals("ADMIN")) {

                        user = new admin(username);

                    } else if (akun[i][2].equals("STAFF")) {

                        user = new staff(username);

                    } else if (akun[i][2].equals("OWNER")) {

                        user = new owner(username);
                    }

                    System.out.println("\nLogin berhasil!");
                    user.menu();
                }
            }

            if (!login) {

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
