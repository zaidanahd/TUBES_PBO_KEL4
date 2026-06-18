package pbotubes;

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