package pbotubes;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Task 1.2: Sistem pembacaan akun dari file JSON
public class UserRepository {

    private List<User> daftarUser;
    private String pathFile;

    public UserRepository() {
        this.daftarUser = new ArrayList<>();
        this.pathFile = cariFileJson();
        loadData();
    }

    // Cari file JSON di beberapa lokasi
    private String cariFileJson() {
        String[] possiblePaths = {
                "app/src/main/resources/users.json",
                "src/main/resources/users.json",
                "users.json"
        };

        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                return path;
            }
        }
        return null;
    }

    // Baca dan parse file JSON
    private void loadData() {
        if (pathFile == null) {
            System.out.println("[WARNING] File users.json tidak ditemukan!");
            return;
        }

        try {
            String isiJson = new String(
                    Files.readAllBytes(Paths.get(pathFile)),
                    StandardCharsets.UTF_8);

            // Parse setiap object user dalam array JSON
            parseUsersFromJson(isiJson);

        } catch (Exception e) {
            System.out.println("[ERROR] Gagal membaca file: " + e.getMessage());
        }
    }

    // Parsing JSON manual (tanpa library eksternal)
    private void parseUsersFromJson(String json) {
        // Hapus whitespace untuk memudahkan regex
        String cleanJson = json.replaceAll("\\s+", "");

        // Pattern untuk menangkap setiap object user
        // Format: {"username":"xxx","password":"yyy","role":"ZZZ"}
        String patternString = "\\{\\\"username\\\":\\\"([^\\\"]+)\\\"\\,\\\"password\\\":\\\"([^\\\"]+)\\\"\\,\\\"role\\\":\\\"([A-Z]+)\\\"\\}";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(cleanJson);

        while (matcher.find()) {
            String username = matcher.group(1);
            String password = matcher.group(2);
            String role = matcher.group(3);

            daftarUser.add(new User(username, password, role));
        }
    }

    // Cari user berdasarkan username
    public User findByUsername(String username) {
        for (User user : daftarUser) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Getter untuk semua user
    public List<User> getAllUsers() {
        return daftarUser;
    }

    // Cek apakah file ditemukan
    public boolean isFileFound() {
        return pathFile != null;
    }
}