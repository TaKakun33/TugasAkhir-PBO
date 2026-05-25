package view;

import controller.TiketController;
import model.Penerbangan;
import model.Tiket;
import model.TiketBisnis;
import model.TiketEkonomi;
import java.util.List;
import java.util.Scanner;

public class MainCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TiketController controller = new TiketController();

        System.out.println("=====================================");
        System.out.println("   SISTEM PEMESANAN TIKET PESAWAT    ");
        System.out.println("=====================================");

        List<Penerbangan> rute = controller.dapatkanDaftarPenerbangan();
        
        if (rute.isEmpty()) {
            System.out.println("Belum ada jadwal penerbangan di database.");
            return;
        }

        System.out.println("\nDaftar Rute Tersedia:");
        rute.forEach((p) -> {
            System.out.println(p.getIdPenerbangan() + " | " + 
                               p.getAsal().getKota() + " -> " + 
                               p.getTujuan().getKota() + " | Gate: " + 
                               p.getGate() + " | Harga Dasar: Rp" + p.getHargaDasar());
        });

        System.out.println("\n--- Masukkan Data Pemesanan ---");
        System.out.print("ID Penerbangan       : ");
        String idPilih = scanner.nextLine();
        
        // Cari rute
        Penerbangan penerbanganPilihan = null;
        for (Penerbangan p : rute) {
            if (p.getIdPenerbangan().equalsIgnoreCase(idPilih)) {
                penerbanganPilihan = p;
                break;
            }
        }

        if (penerbanganPilihan == null) {
            System.out.println("Gagal: Penerbangan dengan ID " + idPilih + " tidak ditemukan.");
            return; // Hentikan program jika ID salah
        }

        // Jika rute ditemukan, lanjutkan meminta input lainnya
        System.out.print("Nama Penumpang       : ");
        String nama = scanner.nextLine();
        System.out.print("Nomor Frequent Flyer : ");
        String ff = scanner.nextLine();
        System.out.print("Nomor Kursi          : ");
        String kursi = scanner.nextLine();
        
        System.out.println("Pilih Kelas Tiket:");
        System.out.println("  [1] Economy Class (Bagasi gratis s/d 20kg)");
        System.out.println("  [2] Business Class (Bagasi gratis s/d 30kg, Harga 150%)");
        System.out.print("   Pilihan Anda (1/2)   : ");
        int pilihanKelas = scanner.nextInt();
        
        System.out.print("Berat Bagasi (Kg)    : ");
        int bagasi = scanner.nextInt();

        // -------------------------------------------------------------
        // IMPLEMENTASI POLIMORFISME & LATE BINDING
        // -------------------------------------------------------------
        Tiket tiketBaru; 
        
        if (pilihanKelas == 2) {
            tiketBaru = new TiketBisnis(); // Wujud objek berubah menjadi Bisnis
        } else {
            tiketBaru = new TiketEkonomi(); // Wujud objek berubah menjadi Ekonomi
        }

        // Setel semua data ke dalam objek tiket
        tiketBaru.setPenerbangan(penerbanganPilihan);
        tiketBaru.setNomorKursi(kursi);
        tiketBaru.setBeratBagasi(bagasi);
        tiketBaru.setNomorEtkt("ETKT-" + System.currentTimeMillis()); // ETKT digenerate otomatis oleh sistem
        
        // Hitung total harga (akan otomatis menyesuaikan apakah dia Ekonomi/Bisnis)
        tiketBaru.hitungTotalHarga();

        System.out.println("\nMemproses pemesanan ke database...");
        Thread loadingThread = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Menyimpan data...");
                    Thread.sleep(800);
                }
            } catch (InterruptedException e) {
                System.out.println("Proses terganggu.");
            }
        });
        loadingThread.start();
        try { loadingThread.join(); } catch (InterruptedException e) {}

        // Simpan ke database
        boolean sukses = controller.prosesPesanTiket(nama, ff, tiketBaru);
        
        if (sukses) {
            System.out.println("\n=====================================");
            System.out.println("        BERHASIL CETAK TIKET         ");
            System.out.println("=====================================");
            System.out.println("Nama     : " + nama);
            System.out.println("Kelas    : " + tiketBaru.getKelasTiket());
            System.out.println("Rute     : " + penerbanganPilihan.getAsal().getKota() + " -> " + penerbanganPilihan.getTujuan().getKota());
            System.out.println("Kursi    : " + tiketBaru.getNomorKursi());
            System.out.println("E-Ticket : " + tiketBaru.getNomorEtkt());
            System.out.println("Bagasi   : " + tiketBaru.getBeratBagasi() + " Kg");
            System.out.println("-------------------------------------");
            System.out.println("TOTAL BAYAR : Rp " + tiketBaru.getTotalHarga());
            System.out.println("=====================================");
        } else {
            System.out.println("\n[GAGAL] Terjadi kesalahan saat menyimpan transaksi.");
        }
        
        scanner.close();
    }
}