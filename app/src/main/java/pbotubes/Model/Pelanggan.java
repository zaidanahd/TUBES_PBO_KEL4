package pbotubes.Model;

public class Pelanggan {
    protected String ktp;
    protected String nama;
    protected String noTelepon;

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