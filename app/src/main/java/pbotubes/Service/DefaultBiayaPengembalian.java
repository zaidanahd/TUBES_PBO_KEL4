package pbotubes.Service; // ← lowercase!

import pbotubes.Model.Transaksi; // ← import spesifik

// EPIC 4 - Task 4.4 (Alwi) : implementasi default kalkulasi biaya pengembalian
public class DefaultBiayaPengembalian implements BiayaPengembalian {

    @Override
    public double hitungBiayaPengembalian(Transaksi transaksi, int hariTerlambat, double dendaPerHari) {
        double biayaNormal = transaksi.getTotalTagihan(); // ← PAKAI GETTER
        double denda = hariTerlambat * dendaPerHari;
        return biayaNormal + denda;
    }
}