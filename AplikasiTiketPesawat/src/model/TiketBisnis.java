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
        if (beratBagasi > 30) {
            return (beratBagasi - 30) * 75000;
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
