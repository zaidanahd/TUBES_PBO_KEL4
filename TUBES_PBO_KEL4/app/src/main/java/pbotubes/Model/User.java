package pbotubes.Model;

public class User {

    protected String username;

    public User(String username) {
        this.username = username;
    }

    // EPIC 1 - Task 2 (Silvi) : Method menu() di-override oleh setiap subclass role (admin/staff/owner),
    // sehingga tiap role hanya melihat menu yang relevan dengan tugasnya.
    public void menu() {
        System.out.println("Menu User");
    }
}