/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author AKBAR
 */
import dao.PenerbanganDAO;
import dao.TiketDAO;
import model.Penerbangan;
import model.Tiket;
import java.util.List;

public class TiketController {
    private PenerbanganDAO penerbanganDAO;
    private TiketDAO tiketDAO;

    public TiketController() {
        this.penerbanganDAO = new PenerbanganDAO();
        this.tiketDAO = new TiketDAO();
    }

    public List<Penerbangan> dapatkanDaftarPenerbangan() {
        return penerbanganDAO.getAllPenerbangan();
    }

    public boolean prosesPesanTiket(String nama, String ffNumber, Tiket tiketTerpilih) {
        return tiketDAO.simpanTransaksiTiket(nama, ffNumber, tiketTerpilih);
    }
}
