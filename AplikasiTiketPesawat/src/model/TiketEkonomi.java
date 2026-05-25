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
        if (beratBagasi > 20) {
            return (beratBagasi - 20) * 50000;
        }
        return 0;
    }
    
    @Override
    public double hitungTotalHarga() {
        double biayaBagasi = hitungBiayaBagasiTambahan(this.beratBagasi);
        this.totalHarga = this.penerbangan.getHargaDasar() + biayaBagasi;
        return this.totalHarga;
    }
}
