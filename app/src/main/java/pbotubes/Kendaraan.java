public class Kendaraan {
    String platNomor;
    String jenis;        
    double hargaSewa;   
    String merk;
    String infoTambahan; 
    String status;       

    Kendaraan(String platNomor, String jenis, double hargaSewa,String merk, String infoTambahan) {
        this.platNomor    = platNomor;
        this.jenis        = jenis;
        this.hargaSewa    = hargaSewa;
        this.merk         = merk;
        this.infoTambahan = infoTambahan;
        this.status       = "Tersedia"; 
    }
}