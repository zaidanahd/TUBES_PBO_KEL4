package tubes;

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
}