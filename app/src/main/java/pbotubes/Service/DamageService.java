package pbotubes.Service;
import pbotubes.Model.TingkatKerusakan;

// CASE TAMBAHAN - Kelompok 4 (Asuransi & Kerusakan) 
// pada Use Case Tambahan Kelompok 4: jika pelanggan memakai Asuransi,
// maka denda kerusakan otomatis digratiskan (return 0).
public class DamageService {


    // CASE TAMBAHAN - Kelompok 4
    public double hitungDendaKerusakan(
            TingkatKerusakan kerusakan,
            boolean menggunakanAsuransi) {

        if (menggunakanAsuransi) {
            return 0;
        }

        return kerusakan.getDenda();
    }
}