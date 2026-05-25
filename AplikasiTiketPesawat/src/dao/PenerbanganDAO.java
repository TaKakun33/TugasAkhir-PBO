/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author AKBAR
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Bandara;
import model.Penerbangan;
import model.Pesawat;

public class PenerbanganDAO {
    public List<Penerbangan> getAllPenerbangan() {
        List<Penerbangan> daftarPenerbangan = new ArrayList<>();
        
        // Query SQL JOIN
        String query = "SELECT p.id_penerbangan, p.gate, p.waktu_keberangkatan, p.waktu_boarding, p.harga_dasar, " +
                       "b1.kode_bandara AS kode_asal, b1.nama_bandara AS nama_asal, b1.kota AS kota_asal, b1.negara AS negara_asal, " +
                       "b2.kode_bandara AS kode_tujuan, b2.nama_bandara AS nama_tujuan, b2.kota AS kota_tujuan, b2.negara AS negara_tujuan, " +
                       "pes.id_pesawat, pes.nama_maskapai, pes.jenis_pesawat, pes.kapasitas " +
                       "FROM penerbangan p " +
                       "JOIN bandara b1 ON p.kode_bandara_asal = b1.kode_bandara " +
                       "JOIN bandara b2 ON p.kode_bandara_tujuan = b2.kode_bandara " +
                       "JOIN pesawat pes ON p.id_pesawat = pes.id_pesawat";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bandara asal = new Bandara(
                    rs.getString("kode_asal"),
                    rs.getString("nama_asal"),
                    rs.getString("kota_asal"),
                    rs.getString("negara_asal")
                );

                Bandara tujuan = new Bandara(
                    rs.getString("kode_tujuan"),
                    rs.getString("nama_tujuan"),
                    rs.getString("kota_tujuan"),
                    rs.getString("negara_tujuan")
                );

                Pesawat pesawat = new Pesawat(
                    rs.getInt("id_pesawat"),
                    rs.getString("nama_maskapai"),
                    rs.getString("jenis_pesawat"),
                    rs.getInt("kapasitas")
                );

                Penerbangan p = new Penerbangan();
                p.setIdPenerbangan(rs.getString("id_penerbangan"));
                p.setGate(rs.getString("gate"));
                p.setWaktuKeberangkatan(rs.getString("waktu_keberangkatan"));
                p.setWaktuBoarding(rs.getString("waktu_boarding"));
                p.setHargaDasar(rs.getDouble("harga_dasar"));
                p.setAsal(asal);
                p.setTujuan(tujuan);
                p.setPesawat(pesawat);

                daftarPenerbangan.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error saat membaca data penerbangan: " + e.getMessage());
        }
        return daftarPenerbangan;
    }
    
    public List<Penerbangan> cariPenerbangan(String kotaAsal, String kotaTujuan) {
        List<Penerbangan> daftar = new ArrayList<>();
        for (Penerbangan p : getAllPenerbangan()) {
            if (p.getAsal().getKota().equalsIgnoreCase(kotaAsal) && 
                p.getTujuan().getKota().equalsIgnoreCase(kotaTujuan)) {
                daftar.add(p);
            }
        }
        return daftar;
    }

    public boolean isRuteTersedia(String kodeAsal, String kodeTujuan) {
        String query = "SELECT COUNT(*) FROM penerbangan WHERE kode_bandara_asal = ? AND kode_bandara_tujuan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, kodeAsal);
            ps.setString(2, kodeTujuan);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saat verifikasi rute: " + e.getMessage());
        }
        return false;
    }
}
