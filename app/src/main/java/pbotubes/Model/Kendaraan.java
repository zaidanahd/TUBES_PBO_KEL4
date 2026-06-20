package pbotubes.Model;

//Entity Kendaraan: menyimpan data Mobil/Motor, Dipakai di Epic 2 (Manajemen Inventaris) oleh Admin (Intan)
//dan dibaca-baca di Epic 4 (Transaksi) oleh Staff (Alwi).
public class Kendaraan {
    protected String platNomor;
    protected String jenis;        
    protected double hargaSewa;   
    protected String merk;
    protected String infoTambahan; 
    protected String status;   
    
    // EPIC 2 - Task 1 (Intan) :Constructor dipakai saat Admin menambah kendaraan baru.
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

    // EPIC 4 - Task 2 (Alwi)
    // Setter status dipanggil saat proses sewa 
    public void setStatus(String status) {
        this.status = status;
    }
}