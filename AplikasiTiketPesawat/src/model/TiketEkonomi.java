/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author AKBAR
 */
public class TiketEkonomi extends Tiket {

    public TiketEkonomi() {
        this.kelasTiket = "ECONOMY CLASS";
    }

    @Override
    public double hitungBiayaBagasiTambahan(int beratBagasi) {
        if (kategoriPenumpang.equalsIgnoreCase("Bayi")) {
            if (beratBagasi > 5) {
                return (beratBagasi - 5) * 50000;
            }
        } else {
            if (beratBagasi > 20) {
                return (beratBagasi - 20) * 50000;
            }
        }
        return 0;
    }
    
    @Override
    public double hitungTotalHarga() {
        double biayaBagasi = hitungBiayaBagasiTambahan(this.beratBagasi);
        double harga = this.penerbangan.getHargaDasar();
        
        if (kategoriPenumpang.equalsIgnoreCase("Anak")) {
            harga = harga * 0.88;
        } else if (kategoriPenumpang.equalsIgnoreCase("Bayi")) {
            harga = harga * 0.10;
        }
        
        this.totalHarga = harga + biayaBagasi;
        return this.totalHarga;
    }
}
