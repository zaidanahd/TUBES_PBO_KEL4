package pbotubes.Model;

public class User {

    protected String username;

    public User(String username) {
        this.username = username;
    }

    public void menu() {
        System.out.println("Menu User");
    }
}