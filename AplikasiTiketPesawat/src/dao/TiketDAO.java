/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author LENOVO
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Tiket;

public class TiketDAO {
    public boolean simpanTransaksiTiket(String namaPenumpang, String ffNumber, Tiket tiket) {
        String insertPenumpang = "INSERT INTO penumpang (nama, ff_number) VALUES (?, ?)";
        String insertTiket = "INSERT INTO tiket (id_penumpang, id_penerbangan, nomor_kursi, nomor_etkt, kelas_tiket, total_harga) VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement psPenumpang = null;
        PreparedStatement psTiket = null;
        ResultSet rsGeneratedKeys = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            psPenumpang = conn.prepareStatement(insertPenumpang, Statement.RETURN_GENERATED_KEYS);
            psPenumpang.setString(1, namaPenumpang);
            psPenumpang.setString(2, ffNumber);
            int affectedRows = psPenumpang.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Gagal menambahkan data penumpang.");
            }

            rsGeneratedKeys = psPenumpang.getGeneratedKeys();
            int idPenumpangGenerated = 0;
            if (rsGeneratedKeys.next()) {
                idPenumpangGenerated = rsGeneratedKeys.getInt(1);
            }

            psTiket = conn.prepareStatement(insertTiket);
            psTiket.setInt(1, idPenumpangGenerated);
            psTiket.setString(2, tiket.getPenerbangan().getIdPenerbangan());
            psTiket.setString(3, tiket.getNomorKursi());
            psTiket.setString(4, tiket.getNomorEtkt());
            psTiket.setString(5, tiket.getKelasTiket());
            
            psTiket.setDouble(6, tiket.hitungTotalHarga());

            psTiket.executeUpdate();
            
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Transaksi pemesanan gagal, membatalkan perubahan... Error: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Gagal melakukan rollback: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (rsGeneratedKeys != null) rsGeneratedKeys.close();
                if (psPenumpang != null) psPenumpang.close();
                if (psTiket != null) psTiket.close();
            } catch (SQLException e) {
                System.out.println("Gagal menutup resource: " + e.getMessage());
            }
        }
        return false;
    }
}
