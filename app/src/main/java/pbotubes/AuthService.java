package pbotubes;

// Task 1.3: Logika validasi login (maksimal 3 kali percobaan salah)
public class AuthService {

    private UserRepository userRepository;
    private static final int MAX_ATTEMPTS = 3;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Validasi login dengan batas 3 kali percobaan
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.isPasswordMatch(password)) {
            return user; // Login berhasil
        }

        return null; // Login gagal
    }

    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }
}