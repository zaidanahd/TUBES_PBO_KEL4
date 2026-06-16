package tubes;

public class Transaksi {

    String idTransaksi;

    String namaPelanggan;

    String platNomor;

    String status;

    double totalTagihan;

    public Transaksi(
            String idTransaksi,
            String namaPelanggan,
            String platNomor,
            String status,
            double totalTagihan){

        this.idTransaksi=idTransaksi;

        this.namaPelanggan=namaPelanggan;

        this.platNomor=platNomor;

        this.status=status;

        this.totalTagihan=totalTagihan;

    }

}
