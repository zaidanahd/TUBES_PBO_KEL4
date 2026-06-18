package pbotubes;

public class Transaksi {

    String idTransaksi;

    String namaPelanggan;

    String platNomor;

    String status;

    double totalTagihan;

    boolean menggunakanAsuransi;

    public Transaksi(
            String idTransaksi,
            String namaPelanggan,
            String platNomor,
            String status,
            double totalTagihan,
            boolean menggunakanAsuransi){

        this.idTransaksi=idTransaksi;

        this.namaPelanggan=namaPelanggan;

        this.platNomor=platNomor;

        this.status=status;

        this.totalTagihan=totalTagihan;

        this.menggunakanAsuransi=menggunakanAsuransi;

    }

}
