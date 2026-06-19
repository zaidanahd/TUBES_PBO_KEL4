package pbotubes.Model;

public class Kendaraan {
    protected String platNomor;
    protected String jenis;        
    protected double hargaSewa;   
    protected String merk;
    protected String infoTambahan; 
    protected String status;       

    public Kendaraan(String platNomor, String jenis, double hargaSewa, String merk, String infoTambahan) {
        this.platNomor    = platNomor;
        this.jenis        = jenis;
        this.hargaSewa    = hargaSewa;
        this.merk         = merk;
        this.infoTambahan = infoTambahan;
        this.status       = "TERSEDIA"; // Disesuaikan kapitalisasinya dengan UI screenshot
    }

    // Getter & Setter biar save
    public String getPlatNomor() {
        return platNomor;
    }

    public String getJenis() {
        return jenis;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public String getMerk() {
        return merk;
    }

    public String getInfoTambahan() {
        return infoTambahan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}