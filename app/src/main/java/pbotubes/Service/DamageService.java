package pbotubes.Service;
import pbotubes.Model.TingkatKerusakan;
public class DamageService {

    public double hitungDendaKerusakan(
            TingkatKerusakan kerusakan,
            boolean menggunakanAsuransi) {

        if (menggunakanAsuransi) {
            return 0;
        }

        return kerusakan.getDenda();
    }
}