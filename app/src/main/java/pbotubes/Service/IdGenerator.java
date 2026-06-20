package pbotubes.Service;

//EPIC 4 - Task 2 (Alwi): class untuk generate ID transaksi
public class IdGenerator {
    private static int counter = 1;

    public static String generateTransaksiId() {
        String id = String.format("TRX-%03d", counter);
        counter++;
        return id;
    }

    // Reset counter (untuk testing)
    public static void resetCounter() {
        counter = 1;
    }
}