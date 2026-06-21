package pbotubes.Model;

// Entity Pelanggan: menyimpan data penyewa (Epic 3 - Zaidan).
public class Pelanggan {
    protected String ktp;
    protected String nama;
    protected String noTelepon;

    // EPIC 3 - Task 1 (Zaidan) : Constructor dipakai saat Staff mendaftarkan pelanggan baru.
    public Pelanggan(String ktp, String nama, String noTelepon) {
        this.ktp = ktp;
        this.nama = nama;
        this.noTelepon = noTelepon;
    }

    //tambahan biar save
    public String getKtp() { return ktp; }
    public String getNama() { return nama; }
    public String getNoTelepon() { return noTelepon; }
}