package pbotubes.Service;
import pbotubes.Model.Transaksi;

// EPIC 4 - Task 4 (Alwi) : Interface kalkulasi biaya pengembalian (penerapan Abstraction/Interface. 
public interface BiayaPengembalian {
    double hitungBiayaPengembalian(Transaksi transaksi, int hariTerlambat, double dendaPerHari);
}
