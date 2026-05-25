/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author AKBAR
 */

import model.Pesawat;

public class Penerbangan {
    private String idPenerbangan;
    private Bandara asal;
    private Bandara tujuan;
    private Pesawat pesawat;
    private String gate;
    private String waktuBoarding;
    private double hargaDasar;
    private String waktuKeberangkatan;

    public Penerbangan() {}

    // Getter Setter
    public String getIdPenerbangan() {
        return idPenerbangan;
    }

    public void setIdPenerbangan(String idPenerbangan) {
        this.idPenerbangan = idPenerbangan;
    }

    public Bandara getAsal() {
        return asal;
    }

    public void setAsal(Bandara asal) {
        this.asal = asal;
    }

    public Bandara getTujuan() {
        return tujuan;
    }

    public void setTujuan(Bandara tujuan) {
        this.tujuan = tujuan;
    }

    public Pesawat getPesawat() {
        return pesawat;
    }

    public void setPesawat(Pesawat pesawat) {
        this.pesawat = pesawat;
    }

    public String getWaktuKeberangkatan() {
        return waktuKeberangkatan;
    }

    public void setWaktuKeberangkatan(String waktuKeberangkatan) {
        this.waktuKeberangkatan = waktuKeberangkatan;
    }
    
    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getWaktuBoarding() {
        return waktuBoarding;
    }

    public void setWaktuBoarding(String waktuBoarding) {
        this.waktuBoarding = waktuBoarding;
    }

    public double getHargaDasar() {
        return hargaDasar;
    }

    public void setHargaDasar(double hargaDasar) {
        this.hargaDasar = hargaDasar;
    }
}
