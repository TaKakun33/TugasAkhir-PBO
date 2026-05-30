/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author AKBAR
 */

public class Bandara {
    private String kodeBandara;
    private String namaBandara;
    private String kota;
    private String negara;

    public Bandara() {}

    public Bandara(String kodeBandara, String namaBandara, String kota, String negara) {
        this.kodeBandara = kodeBandara;
        this.namaBandara = namaBandara;
        this.kota = kota;
        this.negara = negara;
    }

    // Getter Setter
    public String getKodeBandara() {
        return kodeBandara;
    }
    
    public void setKodeBandara(String kodeBandara) {
        this.kodeBandara = kodeBandara;
    }
    
    public String getNamaBandara() {
        return namaBandara;
    }
    
    public void setNamaBandara(String namaBandara) {
        this.namaBandara = namaBandara;
    }
    
    public String getKota() {
        return kota;
    }
    
    public void setKota(String kota) {
        this.kota = kota;
    }
    
    public String getNegara() {
        return negara;
    }
    
    public void setNegara(String negara) {
        this.negara = negara;
    }
}
