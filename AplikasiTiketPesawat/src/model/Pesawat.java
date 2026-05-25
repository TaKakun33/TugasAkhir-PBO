/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author AKBAR
 */

public class Pesawat {
    private int idPesawat;
    private String namaMaskapai;
    private String jenisPesawat;
    private int kapasitas;

    public Pesawat() {}

    public Pesawat(int idPesawat, String namaMaskapai, String jenisPesawat, int kapasitas) {
        this.idPesawat = idPesawat;
        this.namaMaskapai = namaMaskapai;
        this.jenisPesawat = jenisPesawat;
        this.kapasitas = kapasitas;
    }

    // Getter Setter
    public int getIdPesawat() {
        return idPesawat;
    }
    
    public void setIdPesawat(int idPesawat) {
        this.idPesawat = idPesawat;
    }
    
    public String getNamaMaskapai() {
        return namaMaskapai;
    }
    
    public void setNamaMaskapai(String namaMaskapai) {
        this.namaMaskapai = namaMaskapai;
    }
    
    public String getJenisPesawat() {
        return jenisPesawat;
    }
    
    public void setJenisPesawat(String jenisPesawat) {
        this.jenisPesawat = jenisPesawat;
    }
    
    public int getKapasitas() {
        return kapasitas;
    }
    
    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }
}
