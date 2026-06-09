public class BusinessReportGenerator {
    private TransactionRepository transactionRepository;

    public BusinessReportGenerator(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void printReport() {
        System.out.println("\n=== LAPORAN RIWAYAT & PENDAPATAN ===\n");
        System.out.printf("%-12s | %-15s | %-12s | %-10s | %-15s%n","ID Transaksi", "Pelanggan", "Kendaraan", "Status", "Total Tagihan");
        System.out.println("--------------------------------------------------------------------------------");

        long totalIncome = 0;

        for (Transaction t : transactionRepository.findAll()) {
            String customerName = t.getCustomer().getName();
            String licensePlate = t.getVehicle().getLicensePlate();
            String status = (t.getStatus() == TransactionStatus.COMPLETED) ? "SELESAI" : "BERJALAN";

            String totalBillDisplay = "-";
            if (t.getStatus() == TransactionStatus.COMPLETED) {
                long bill = t.calculateTotalBill();
                totalBillDisplay = String.format("Rp %,d", bill);
                totalIncome += bill;
            }

            System.out.printf("%-12s | %-15s | %-12s | %-10s | %-15s%n",
                    t.getTransactionId(), customerName, licensePlate, status, totalBillDisplay);
        }

        System.out.println("\nTOTAL PENDAPATAN (Hanya dari Transaksi Selesai): " + String.format("Rp %,d", totalIncome));
        System.out.println("\nTekan ENTER untuk kembali ke menu utama...");
    }
}


