/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author AKBAR
 */
public abstract class Tiket {
    protected int idTiket;
    protected int idPenumpang;
    protected Penerbangan penerbangan;
    protected String nomorKursi;
    protected String nomorEtkt;
    protected String kelasTiket;
    protected double totalHarga;
    protected int beratBagasi;

    public Tiket() {}
    public abstract double hitungBiayaBagasiTambahan(int beratBagasi);
    public abstract double hitungTotalHarga();

    // Metod
//    public abstract double hitungBiayaBagasiTambahan(int beratBagasi);

    // Getter dan Setter
    public int getIdTiket() {
        return idTiket;
    }
    
    public void setIdTiket(int idTiket) {
        this.idTiket = idTiket;
    }
    
    public int getIdPenumpang() {
        return idPenumpang;
    }
    
    public void setIdPenumpang(int idPenumpang) {
        this.idPenumpang = idPenumpang;
    }
    
    public Penerbangan getPenerbangan() {
        return penerbangan;
    }
    
    public void setPenerbangan(Penerbangan penerbangan) {
        this.penerbangan = penerbangan;
    }
    
    public String getNomorKursi() {
        return nomorKursi;
    }
    
    public void setNomorKursi(String nomorKursi) {
        this.nomorKursi = nomorKursi;
    }
    
    public String getNomorEtkt() {
        return nomorEtkt;
    }
    
    public void setNomorEtkt(String nomorEtkt) {
        this.nomorEtkt = nomorEtkt;
    }
    
    public String getKelasTiket() {
        return kelasTiket;
    }
    
    public void setKelasTiket(String kelasTiket) {
        this.kelasTiket = kelasTiket;
    }
    
    public double getTotalHarga() {
        return totalHarga;
    }
    
    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }
    
    public int getBeratBagasi() {
        return beratBagasi;
    }
    
    public void setBeratBagasi(int beratBagasi) {
        this.beratBagasi = beratBagasi;
    }
}
