package pbotubes.Model;

// EPIC 4 - Task 3 (Alwi) :  Dipakai saat proses pengembalian untuk menentukan denda kerusakan kendaraan.
// pendukung case tambahan Kelompok 4 (Asuransi & Kerusakan).
public enum TingkatKerusakan {
    RINGAN(200000),
    SEDANG(500000),
    BERAT(1000000);

    private final double denda;

    TingkatKerusakan(double denda) {
        this.denda = denda;
    }

    public double getDenda() {
        return denda;
    }
}