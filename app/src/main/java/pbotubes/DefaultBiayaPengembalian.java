package pbotubes;

//Task 4.4 - implementasi default kalkulasi biaya pengembalian
public class DefaultBiayaPengembalian implements BiayaPengembalian {

    @Override
    public double hitungBiayaPengembalian(Transaksi transaksi, int hariTerlambat, double dendaPerHari) {
        double biayaNormal = transaksi.totalTagihan;
        double denda = hariTerlambat * dendaPerHari;
        return biayaNormal + denda;
    }
}