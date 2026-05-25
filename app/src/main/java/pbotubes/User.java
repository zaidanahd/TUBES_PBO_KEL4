package pbotubes;

// Task 1.1: Entitas User dengan atribut username, password, dan role
public class User {

    // Enum untuk membatasi role (lebih aman dari String biasa)
    public enum Role {
        ADMIN, STAFF, OWNER
    }

    private String username;
    private String password;
    private Role role;

    // Constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        // Konversi String ke enum (otomatis uppercase)
        this.role = Role.valueOf(role.toUpperCase());
    }

    // Getter
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }

    // Untuk tampilan (mengembalikan String)
    public String getRoleString() {
        return this.role.name();
    }

    // Cek password tanpa expose password langsung
    public boolean isPasswordMatch(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}