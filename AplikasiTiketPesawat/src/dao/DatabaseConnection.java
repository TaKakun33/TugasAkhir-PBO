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
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db_tiket_pesawat";
    private static final String USER = "AplikasiTiketPesawat";
    private static final String PASSWORD = "12345678";
    
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                
                // System.out.println("Status: Koneksi baru ke database db_tiket_pesawat dibuka."); 
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Status: Driver MySQL tidak ditemukan!");
        } catch (SQLException e) {
            System.out.println("Status: Gagal terhubung ke database!");
            System.out.println("Detail Error: " + e.getMessage());
        }
        
        return connection;
    }

    // Fungsi utama (main) sementara untuk mengetes koneksi
    public static void main(String[] args) {
        getConnection();
    }
}
