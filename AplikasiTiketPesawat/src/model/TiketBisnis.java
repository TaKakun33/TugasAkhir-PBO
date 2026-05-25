/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author AKBAR
 */
public class TiketBisnis extends Tiket {

    public TiketBisnis() {
        this.kelasTiket = "BUSINESS CLASS";
    }

    @Override
    public double hitungBiayaBagasiTambahan(int beratBagasi) {
        if (kategoriPenumpang.equalsIgnoreCase("Bayi")) {
            if (beratBagasi > 10) {
                return (beratBagasi - 10) * 50000;
            }
        } else {
            if (beratBagasi > 30) {
                return (beratBagasi - 30) * 50000;
            }
        }
        return 0;
    }
    
    @Override
    public double hitungTotalHarga() {
        double biayaBagasi = hitungBiayaBagasiTambahan(this.beratBagasi);
        this.totalHarga = (this.penerbangan.getHargaDasar() * 1.5) + biayaBagasi;
        return this.totalHarga;
    }
}
