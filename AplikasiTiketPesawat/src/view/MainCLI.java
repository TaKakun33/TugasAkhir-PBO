package view;

import controller.TiketController;
import model.Penerbangan;
import model.Tiket;
import model.TiketBisnis;
import model.TiketEkonomi;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Bandara;

public class MainCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TiketController controller = new TiketController();

        System.out.println("=====================================");
        System.out.println("   SISTEM PEMESANAN TIKET PESAWAT    ");
        System.out.println("=====================================");

        // PENCARIAN RUTE
        System.out.print("Input Kota/Bandara Asal   : ");
        String asal = scanner.nextLine();
        System.out.print("Input Kota/Bandara Tujuan : ");
        String tujuan = scanner.nextLine();
        
        List<Penerbangan> rute = controller.cariJadwal(asal, tujuan);
        
        if (rute.isEmpty()) {
            System.out.println("\n[INFO] Maaf, penerbangan dari " + asal + " ke " + tujuan + " tidak tersedia.");
            return;
        }

        System.out.println("\n--- Jadwal Ditemukan ---");
        rute.forEach((p) -> {
            System.out.println(p.getIdPenerbangan() + " | Gate: " + p.getGate() + " | Harga Dasar: Rp" + p.getHargaDasar());
        });

        System.out.print("\nPilih ID Penerbangan      : ");
        String idPilih = scanner.nextLine();
        
        Penerbangan penerbanganPilihan = null;
        for (Penerbangan p : rute) {
            if (p.getIdPenerbangan().equalsIgnoreCase(idPilih)) {
                penerbanganPilihan = p; break;
            }
        }
        if (penerbanganPilihan == null) { System.out.println("Penerbangan tidak valid."); return; }

        // INPUT KELAS & JUMLAH PENUMPANG
        System.out.println("\nPilih Kelas Tiket:");
        System.out.println("  [1] Economy Class\n  [2] Business Class");
        System.out.print("Pilihan Anda (1/2)        : ");
        int pilihanKelas = scanner.nextInt();
        
        System.out.print("Jumlah Penumpang Dewasa   : ");
        int jmlDewasa = scanner.nextInt();
        System.out.print("Jumlah Penumpang Anak     : ");
        int jmlAnak = scanner.nextInt();
        System.out.print("Jumlah Penumpang Bayi     : ");
        int jmlBayi = scanner.nextInt();
        scanner.nextLine();

        int totalPenumpang = jmlDewasa + jmlAnak + jmlBayi;
        int totalButuhKursi = jmlDewasa + jmlAnak;

        // SEAT MAP
        List<String> kursiTerisi = controller.lihatKursiTerisi(idPilih);
        System.out.println("\n--- Denah Kursi Tersedia ---");
        System.out.println("([ X ] = Sudah Terisi)");
        String[] hurufKursi = {"A", "B", "C", "D", "E", "F"};
        
        if(pilihanKelas == 1) {
            for (int baris = 10; baris <= 30; baris++) {
                for (String huruf : hurufKursi) {
                    String kodeKursi = baris + huruf;
                    if (kursiTerisi.contains(kodeKursi)) {
                        System.out.print("[ X ] ");
                    } else {
                        System.out.print("[" + kodeKursi + "] ");
                    }
                    if (huruf.equals("C")) System.out.print("  ||  ");
                }
                System.out.println();
            }
        }
        
        else {
            for (int baris = 1; baris <= 9; baris++) {
                
                System.out.print(" "); 

                for (String huruf : hurufKursi) {
                    String kodeKursi = baris + huruf;
                    if (kursiTerisi.contains(kodeKursi)) {
                        System.out.print("[ X ] ");
                    } else {
                        System.out.print("[" + kodeKursi + "] ");
                    }
                    if (huruf.equals("C")) System.out.print("  ||  ");
                }
                System.out.println();
            }
        }

        // PENDATAAN PENUMPANG
        List<Tiket> keranjangTiket = new ArrayList<>();
        List<String> daftarFf = new ArrayList<>();
        double grandTotal = 0;

        for (int i = 1; i <= totalPenumpang; i++) {
            System.out.println("\n--- Data Penumpang ke-" + i + " ---");
            String kategori;
            if (i <= jmlDewasa) kategori = "Dewasa";
            else if (i <= jmlDewasa + jmlAnak) kategori = "Anak";
            else kategori = "Bayi";
            
            System.out.println("Kategori: " + kategori);
            System.out.print("Nama Penumpang       : ");
            String nama = scanner.nextLine();
            System.out.print("Nomor FF (Isi - jika tdk ada): ");
            String ff = scanner.nextLine();
            
            String kursi = "TANPA KURSI";
            int bagasi = 0;

            if (!kategori.equals("Bayi")) {
                boolean kursiValid = false;
                while (!kursiValid) {
                    System.out.print("Pilih Nomor Kursi    : ");
                    kursi = scanner.nextLine().toUpperCase();
                    // ^[1-9]     : Angka depan 1-9
                    // [0-9]?     : Angka kedua 0-9 (opsional, batas max 99)
                    // [A-F]$     : Huruf belakang wajib A sampai F
                    if (!kursi.matches("^[1-9][0-9]?[A-F]$")) {
                        System.out.println("  [Error] Format tidak valid! Gunakan angka (1-30) dan huruf (A-F).");
                        continue;
                    }
                    
                    if (kursiTerisi.contains(kursi)) {
                        System.out.println("  [Error] Kursi terisi! Pilih dari denah di atas.");
                    } else {
                        kursiTerisi.add(kursi); 
                        kursiValid = true;
                    }
                }
                System.out.print("Berat Bagasi (Kg)    : ");
                bagasi = scanner.nextInt();
                scanner.nextLine();
            }

            // Polimorfisme Instansiasi
            Tiket tiketBaru = (pilihanKelas == 2) ? new TiketBisnis() : new TiketEkonomi();
            tiketBaru.setKategoriPenumpang(kategori);
            tiketBaru.setNamaPenumpang(nama);
            tiketBaru.setPenerbangan(penerbanganPilihan);
            tiketBaru.setNomorKursi(kursi);
            tiketBaru.setBeratBagasi(bagasi);
            tiketBaru.setNomorEtkt("ETKT-" + System.currentTimeMillis() + i);
            tiketBaru.hitungTotalHarga();

            grandTotal += tiketBaru.getTotalHarga();
            keranjangTiket.add(tiketBaru);
            daftarFf.add(ff);
        }

        // KONFIRMASI PESANAN
        System.out.println("\n=====================================");
        System.out.println("GRAND TOTAL BAYAR : Rp " + String.format("%.0f", grandTotal));
        System.out.println("=====================================");
        System.out.print("Yakin ingin memesan? (Y/N): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("Y")) {
            System.out.println("\nMenyimpan data ke database.....");
            Thread loading = new Thread(() -> {
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
            });
            loading.start();
            try { loading.join(); } catch (Exception e) {}

            boolean semuaSukses = true;
            for (int i = 0; i < keranjangTiket.size(); i++) {
                Tiket t = keranjangTiket.get(i);
                boolean sukses = controller.prosesPesanTiket(t.getNamaPenumpang(), daftarFf.get(i), t);
                if (!sukses) semuaSukses = false;
            }

            if (semuaSukses) {
                System.out.println("Data telah tersimpan....\n");
                for (Tiket t : keranjangTiket) {
                    String kelas = t.getKelasTiket().toUpperCase(); 
                    String kelasPendek = kelas.replace(" CLASS", "");
                    String nama = t.getNamaPenumpang().toUpperCase();
                    
                    String asalPenerbangan = t.getPenerbangan().getAsal().getKota().toUpperCase();
                    String tujuanPenerbangan = t.getPenerbangan().getTujuan().getKota().toUpperCase();
                    String kodeAsal = t.getPenerbangan().getAsal().getKodeBandara();
                    String kodeTujuan = t.getPenerbangan().getTujuan().getKodeBandara();
                    
                    String flight = t.getPenerbangan().getIdPenerbangan();
                    String gate = t.getPenerbangan().getGate();
                    String seat = t.getNomorKursi();
                    
                    String rawBerangkat = t.getPenerbangan().getWaktuKeberangkatan(); 
                    String rawBoarding = t.getPenerbangan().getWaktuBoarding();       
                    
                    String tglBerangkat = "N/A";
                    String jamBerangkat = "N/A";
                    String jamBoarding = "N/A";
                 
                    if (rawBerangkat != null && rawBerangkat.length() >= 16) {
                        String[] namaBulan = {"", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
                        int bulan = Integer.parseInt(rawBerangkat.substring(5, 7));
                        tglBerangkat = rawBerangkat.substring(8, 10) + namaBulan[bulan];
                        jamBerangkat = rawBerangkat.substring(11, 16);
                    }
                    
                    if (rawBoarding != null && rawBoarding.length() >= 16) {
                        jamBoarding = rawBoarding.substring(11, 16);
                    }
                    
                    String seq = String.format("%04d", (int)(Math.random() * 1000)); 
                    String pcsWt = (t.getBeratBagasi() > 0 ? "1 / " : "0 / ") + t.getBeratBagasi();

                    System.out.println("=========================================================================================");
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", "BOARDING PASS " + kelas, kelasPendek);
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", "Name", "");
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", nama, nama);
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", "", "");
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", String.format("%-17s %-12s %-10s %-14s", "From", "Flight", "Date", "Time"), String.format("From %s To %s", kodeAsal, kodeTujuan));
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", String.format("%-17s %-12s %-10s %-14s", asalPenerbangan, flight, tglBerangkat, jamBerangkat), "Flight  Date    Time");                   
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", "To", String.format("%-7s %-7s %-11s", flight, tglBerangkat, jamBerangkat));           
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", tujuanPenerbangan, "");
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", "", "Zone   Seat   Seq No");
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", String.format("%-8s %-17s %-7s %-8s %-11s", "Gate", "Boarding Time", "Zone", "Seat", "Seq No"), String.format("F      %-6s %-11s", seat, seq));
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", String.format("%-8s %-17s %-7s %-8s %-11s", gate, jamBoarding, "F", seat, seq), "");
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", "", "");
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", "", "PCS/WT  " + pcsWt);
                    System.out.printf("|| %-55.55s | %-27.27s ||\n", "PLEASE BE AT THE GATE 35 MINUTES BEFORE DEPARTURE", "PAPER TICKET");
                    System.out.println("=========================================================================================\n");
                }
            } else {
                System.out.println("Terjadi kesalahan saat menyimpan sebagian/seluruh data.");
            }
        } else {
            System.out.println("\n[INFO] Transaksi dibatalkan. Kembali ke menu utama.");
        }
        
        scanner.close();
    }
}