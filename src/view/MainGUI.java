/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.TiketController;
import dao.DatabaseConnection;
import model.Penerbangan;
import model.Tiket;
import model.TiketBisnis;
import model.TiketEkonomi;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 * MainGUI - Sistem Pemesanan Tiket Pesawat
 * NetBeans JFrame Form style.
 * @author Generated
 */
public class MainGUI extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(MainGUI.class.getName());

    // ===== STATE =====
    private TiketController controller;
    private Penerbangan penerbanganPilihan;
    private int pilihanKelas;
    private int jmlDewasa, jmlAnak, jmlBayi;
    private List<Tiket>  keranjangTiket = new ArrayList<>();
    private List<String> daftarFF       = new ArrayList<>();
    private List<String> kategoriList   = new ArrayList<>();
    private List<String> kursiTerisi    = new ArrayList<>();
    private Map<Integer, String> kursiDipilihPerPenumpang = new LinkedHashMap<>();
    private int penumpangKursiIndex = 0;
    private Map<String, Penerbangan> mapPenerbangan = new LinkedHashMap<>();
    private ButtonGroup bgJadwal;
    // Panel penumpang dinamis
    private List<javax.swing.JTextField> tfNamaList   = new ArrayList<>();
    private List<javax.swing.JTextField> tfFFList      = new ArrayList<>();
    private List<javax.swing.JSpinner>   spBagasiList  = new ArrayList<>();
    // Seat map dinamis
    private List<javax.swing.JButton> tombolKursiList = new ArrayList<>();

    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        initComponents();
        controller = new TiketController();
        // Isi combo asal & tujuan dari database
        String[] kota = getKotaFromDB();
        for (String k : kota) {
            jComboBoxAsal.addItem(k);
            jComboBoxTujuan.addItem(k);
        }
        // Set tujuan default ke item kedua jika ada
        if (jComboBoxTujuan.getItemCount() > 1) jComboBoxTujuan.setSelectedIndex(1);
    }

    // =========================================================
    //  GENERATED CODE BLOCK (initComponents)
    // =========================================================
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgKelas = new javax.swing.ButtonGroup();
        jTabbedPane = new javax.swing.JTabbedPane();

        // ---- TAB 1: CARI ----
        jPanelCari = new javax.swing.JPanel();
        jLabelJudul = new javax.swing.JLabel();
        jLabelAsal = new javax.swing.JLabel();
        jComboBoxAsal = new javax.swing.JComboBox<>();
        jLabelTujuan = new javax.swing.JLabel();
        jComboBoxTujuan = new javax.swing.JComboBox<>();
        jLabelTanggal = new javax.swing.JLabel();
        jTextFieldTanggal = new javax.swing.JTextField();
        jButtonCari = new javax.swing.JButton();

        // ---- TAB 2: JADWAL ----
        jPanelJadwal = new javax.swing.JPanel();
        jLabelPilihPenerbangan = new javax.swing.JLabel();
        jScrollPaneJadwal = new javax.swing.JScrollPane();
        jPanelJadwalList = new javax.swing.JPanel();
        jLabelPilihKelas = new javax.swing.JLabel();
        jRadioButtonEkonomi = new javax.swing.JRadioButton();
        jRadioButtonBisnis = new javax.swing.JRadioButton();
        jLabelJumlahPenumpang = new javax.swing.JLabel();
        jLabelDewasa = new javax.swing.JLabel();
        jSpinnerDewasa = new javax.swing.JSpinner();
        jLabelAnak = new javax.swing.JLabel();
        jSpinnerAnak = new javax.swing.JSpinner();
        jLabelBayi = new javax.swing.JLabel();
        jSpinnerBayi = new javax.swing.JSpinner();
        jButtonLanjutKursi = new javax.swing.JButton();

        // ---- TAB 3: KURSI ----
        jPanelKursi = new javax.swing.JPanel();
        jLabelInfoKursi = new javax.swing.JLabel();
        jPanelLegenda = new javax.swing.JPanel();
        jPanelLegendFree = new javax.swing.JPanel();
        jLabelLegendFree = new javax.swing.JLabel();
        jPanelLegendTaken = new javax.swing.JPanel();
        jLabelLegendTaken = new javax.swing.JLabel();
        jPanelLegendSel = new javax.swing.JPanel();
        jLabelLegendSel = new javax.swing.JLabel();
        jScrollPaneSeat = new javax.swing.JScrollPane();
        jPanelSeatMap = new javax.swing.JPanel();
        jButtonLanjutPenumpang = new javax.swing.JButton();

        // ---- TAB 4: PENUMPANG ----
        jPanelPenumpang = new javax.swing.JPanel();
        jScrollPanePenumpang = new javax.swing.JScrollPane();
        jPanelPenumpangContent = new javax.swing.JPanel();
        jButtonHitungHarga = new javax.swing.JButton();

        // ---- TAB 5: KONFIRMASI ----
        jPanelKonfirmasi = new javax.swing.JPanel();
        jScrollPaneRingkasan = new javax.swing.JScrollPane();
        jTextAreaRingkasan = new javax.swing.JTextArea();
        jLabelGrandTotal = new javax.swing.JLabel();
        jButtonBatal = new javax.swing.JButton();
        jButtonPesan = new javax.swing.JButton();

        // ---- TAB 6: BOARDING PASS ----
        jPanelBoarding = new javax.swing.JPanel();
        jLabelBoardingTitle = new javax.swing.JLabel();
        jScrollPaneBoarding = new javax.swing.JScrollPane();
        jPanelBoardingContent = new javax.swing.JPanel();
        jButtonSelesai = new javax.swing.JButton();

        // =========================================================
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistem Pemesanan Tiket Pesawat");

        // ---- Setup Tab Cari ----
        jLabelJudul.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 20));
        jLabelJudul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelJudul.setText("SISTEM PEMESANAN TIKET PESAWAT");

        jLabelAsal.setText("Kota/Bandara Asal :");
        jLabelTujuan.setText("Kota/Bandara Tujuan :");
        jLabelTanggal.setText("Tanggal (YYYY-MM-DD) :");

        jTextFieldTanggal.setText("2026-06-22");

        jButtonCari.setText("Cari Penerbangan");
        jButtonCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCariLayout = new javax.swing.GroupLayout(jPanelCari);
        jPanelCari.setLayout(jPanelCariLayout);
        jPanelCariLayout.setHorizontalGroup(
            jPanelCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCariLayout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addGroup(jPanelCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelJudul, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addGroup(jPanelCariLayout.createSequentialGroup()
                        .addGroup(jPanelCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAsal)
                            .addComponent(jLabelTujuan)
                            .addComponent(jLabelTanggal))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxAsal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxTujuan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldTanggal)))
                    .addComponent(jButtonCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanelCariLayout.setVerticalGroup(
            jPanelCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCariLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelJudul)
                .addGap(40, 40, 40)
                .addGroup(jPanelCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAsal)
                    .addComponent(jComboBoxAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanelCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTujuan)
                    .addComponent(jComboBoxTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanelCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTanggal)
                    .addComponent(jTextFieldTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jButtonCari)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // ---- Setup Tab Jadwal ----
        jLabelPilihPenerbangan.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 13));
        jLabelPilihPenerbangan.setForeground(new java.awt.Color(0, 70, 160));
        jLabelPilihPenerbangan.setText("Pilih Penerbangan :");

        jPanelJadwalList.setLayout(new javax.swing.BoxLayout(jPanelJadwalList, javax.swing.BoxLayout.Y_AXIS));
        jScrollPaneJadwal.setViewportView(jPanelJadwalList);

        jLabelPilihKelas.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 13));
        jLabelPilihKelas.setForeground(new java.awt.Color(0, 70, 160));
        jLabelPilihKelas.setText("Pilih Kelas Tiket :");

        bgKelas.add(jRadioButtonEkonomi);
        jRadioButtonEkonomi.setSelected(true);
        jRadioButtonEkonomi.setText("Economy Class (Bagasi gratis s/d 20kg)");

        bgKelas.add(jRadioButtonBisnis);
        jRadioButtonBisnis.setText("Business Class (Bagasi gratis s/d 30kg)");

        jLabelJumlahPenumpang.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 13));
        jLabelJumlahPenumpang.setForeground(new java.awt.Color(0, 70, 160));
        jLabelJumlahPenumpang.setText("Jumlah Penumpang :");

        jLabelDewasa.setText("Dewasa :");
        jLabelAnak.setText("Anak :");
        jLabelBayi.setText("Bayi :");

        jSpinnerDewasa.setModel(new javax.swing.SpinnerNumberModel(1, 0, 9, 1));
        jSpinnerDewasa.setPreferredSize(new java.awt.Dimension(55, 24));
        jSpinnerAnak.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9, 1));
        jSpinnerAnak.setPreferredSize(new java.awt.Dimension(55, 24));
        jSpinnerBayi.setModel(new javax.swing.SpinnerNumberModel(0, 0, 9, 1));
        jSpinnerBayi.setPreferredSize(new java.awt.Dimension(55, 24));

        jButtonLanjutKursi.setText("Lanjut - Pilih Kursi");
        jButtonLanjutKursi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLanjutKursiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelJadwalLayout = new javax.swing.GroupLayout(jPanelJadwal);
        jPanelJadwal.setLayout(jPanelJadwalLayout);
        jPanelJadwalLayout.setHorizontalGroup(
            jPanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJadwalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneJadwal)
                    .addComponent(jLabelPilihPenerbangan)
                    .addComponent(jLabelPilihKelas)
                    .addGroup(jPanelJadwalLayout.createSequentialGroup()
                        .addComponent(jRadioButtonEkonomi)
                        .addGap(24, 24, 24)
                        .addComponent(jRadioButtonBisnis))
                    .addComponent(jLabelJumlahPenumpang)
                    .addGroup(jPanelJadwalLayout.createSequentialGroup()
                        .addComponent(jLabelDewasa)
                        .addGap(6, 6, 6)
                        .addComponent(jSpinnerDewasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabelAnak)
                        .addGap(6, 6, 6)
                        .addComponent(jSpinnerAnak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabelBayi)
                        .addGap(6, 6, 6)
                        .addComponent(jSpinnerBayi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonLanjutKursi, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelJadwalLayout.setVerticalGroup(
            jPanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJadwalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPilihPenerbangan)
                .addGap(6, 6, 6)
                .addComponent(jScrollPaneJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabelPilihKelas)
                .addGap(6, 6, 6)
                .addGroup(jPanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonEkonomi)
                    .addComponent(jRadioButtonBisnis))
                .addGap(16, 16, 16)
                .addComponent(jLabelJumlahPenumpang)
                .addGap(8, 8, 8)
                .addGroup(jPanelJadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDewasa)
                    .addComponent(jSpinnerDewasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAnak)
                    .addComponent(jSpinnerAnak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBayi)
                    .addComponent(jSpinnerBayi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLanjutKursi)
                .addContainerGap())
        );

        // ---- Setup Tab Kursi ----
        jLabelInfoKursi.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 13));
        jLabelInfoKursi.setForeground(new java.awt.Color(0, 102, 204));
        jLabelInfoKursi.setText("Pilih kursi untuk Penumpang 1");

        // Legenda
        jPanelLegenda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 12, 0));

        jPanelLegendFree.setBackground(new java.awt.Color(210, 230, 255));
        jPanelLegendFree.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180)));
        jPanelLegendFree.setPreferredSize(new java.awt.Dimension(14, 14));
        jLabelLegendFree.setText("Tersedia");
        jLabelLegendFree.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 11));

        jPanelLegendTaken.setBackground(new java.awt.Color(255, 200, 200));
        jPanelLegendTaken.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180)));
        jPanelLegendTaken.setPreferredSize(new java.awt.Dimension(14, 14));
        jLabelLegendTaken.setText("Terisi");
        jLabelLegendTaken.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 11));

        jPanelLegendSel.setBackground(new java.awt.Color(0, 180, 80));
        jPanelLegendSel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180)));
        jPanelLegendSel.setPreferredSize(new java.awt.Dimension(14, 14));
        jLabelLegendSel.setText("Dipilih Anda");
        jLabelLegendSel.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 11));

        jPanelLegenda.add(jPanelLegendFree);
        jPanelLegenda.add(jLabelLegendFree);
        jPanelLegenda.add(jPanelLegendTaken);
        jPanelLegenda.add(jLabelLegendTaken);
        jPanelLegenda.add(jPanelLegendSel);
        jPanelLegenda.add(jLabelLegendSel);

        jPanelSeatMap.setLayout(new java.awt.GridBagLayout());
        jPanelSeatMap.setBackground(java.awt.Color.WHITE);
        jScrollPaneSeat.setViewportView(jPanelSeatMap);

        jButtonLanjutPenumpang.setText("Lanjut - Data Penumpang");
        jButtonLanjutPenumpang.setEnabled(false);
        jButtonLanjutPenumpang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLanjutPenumpangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelKursiLayout = new javax.swing.GroupLayout(jPanelKursi);
        jPanelKursi.setLayout(jPanelKursiLayout);
        jPanelKursiLayout.setHorizontalGroup(
            jPanelKursiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKursiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelKursiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelKursiLayout.createSequentialGroup()
                        .addComponent(jLabelInfoKursi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelLegenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPaneSeat)
                    .addComponent(jButtonLanjutPenumpang, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelKursiLayout.setVerticalGroup(
            jPanelKursiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKursiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelKursiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabelInfoKursi)
                    .addComponent(jPanelLegenda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPaneSeat, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(jButtonLanjutPenumpang)
                .addContainerGap())
        );

        // ---- Setup Tab Penumpang ----
        jPanelPenumpangContent.setLayout(new javax.swing.BoxLayout(jPanelPenumpangContent, javax.swing.BoxLayout.Y_AXIS));
        jPanelPenumpangContent.setBackground(java.awt.Color.WHITE);
        jScrollPanePenumpang.setViewportView(jPanelPenumpangContent);

        jButtonHitungHarga.setText("Hitung Harga dan Konfirmasi");
        jButtonHitungHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHitungHargaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelPenumpangLayout = new javax.swing.GroupLayout(jPanelPenumpang);
        jPanelPenumpang.setLayout(jPanelPenumpangLayout);
        jPanelPenumpangLayout.setHorizontalGroup(
            jPanelPenumpangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPenumpangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPenumpangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPanePenumpang)
                    .addComponent(jButtonHitungHarga, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelPenumpangLayout.setVerticalGroup(
            jPanelPenumpangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPenumpangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPanePenumpang, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(jButtonHitungHarga)
                .addContainerGap())
        );

        // ---- Setup Tab Konfirmasi ----
        jTextAreaRingkasan.setColumns(20);
        jTextAreaRingkasan.setRows(5);
        jTextAreaRingkasan.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        jTextAreaRingkasan.setEditable(false);
        jScrollPaneRingkasan.setViewportView(jTextAreaRingkasan);

        jLabelGrandTotal.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 15));
        jLabelGrandTotal.setForeground(new java.awt.Color(0, 70, 160));
        jLabelGrandTotal.setText("Grand Total: Rp 0");

        jButtonBatal.setText("Batalkan");
        jButtonBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBatalActionPerformed(evt);
            }
        });

        jButtonPesan.setText("Confirm dan Pesan");
        jButtonPesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPesanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelKonfirmasiLayout = new javax.swing.GroupLayout(jPanelKonfirmasi);
        jPanelKonfirmasi.setLayout(jPanelKonfirmasiLayout);
        jPanelKonfirmasiLayout.setHorizontalGroup(
            jPanelKonfirmasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKonfirmasiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelKonfirmasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneRingkasan)
                    .addGroup(jPanelKonfirmasiLayout.createSequentialGroup()
                        .addComponent(jLabelGrandTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBatal)
                        .addGap(8, 8, 8)
                        .addComponent(jButtonPesan)))
                .addContainerGap())
        );
        jPanelKonfirmasiLayout.setVerticalGroup(
            jPanelKonfirmasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelKonfirmasiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneRingkasan, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addGroup(jPanelKonfirmasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelGrandTotal)
                    .addComponent(jButtonBatal)
                    .addComponent(jButtonPesan))
                .addContainerGap())
        );

        // ---- Setup Tab Boarding Pass ----
        jLabelBoardingTitle.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 18));
        jLabelBoardingTitle.setForeground(new java.awt.Color(0, 70, 160));
        jLabelBoardingTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBoardingTitle.setText("Boarding Pass");

        jPanelBoardingContent.setLayout(new javax.swing.BoxLayout(jPanelBoardingContent, javax.swing.BoxLayout.Y_AXIS));
        jPanelBoardingContent.setBackground(java.awt.Color.WHITE);
        jScrollPaneBoarding.setViewportView(jPanelBoardingContent);

        jButtonSelesai.setText("Selesai / Pesan Lagi");
        jButtonSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelesaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBoardingLayout = new javax.swing.GroupLayout(jPanelBoarding);
        jPanelBoarding.setLayout(jPanelBoardingLayout);
        jPanelBoardingLayout.setHorizontalGroup(
            jPanelBoardingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBoardingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBoardingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelBoardingTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPaneBoarding)
                    .addComponent(jButtonSelesai, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelBoardingLayout.setVerticalGroup(
            jPanelBoardingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBoardingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBoardingTitle)
                .addGap(8, 8, 8)
                .addComponent(jScrollPaneBoarding, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(jButtonSelesai)
                .addContainerGap())
        );

        // ---- Tabbed Pane ----
        jTabbedPane.addTab("1. Cari Penerbangan", jPanelCari);
        jTabbedPane.addTab("2. Jadwal & Penumpang", jPanelJadwal);
        jTabbedPane.addTab("3. Pilih Kursi", jPanelKursi);
        jTabbedPane.addTab("4. Data Penumpang", jPanelPenumpang);
        jTabbedPane.addTab("5. Konfirmasi", jPanelKonfirmasi);
        jTabbedPane.addTab("6. Boarding Pass", jPanelBoarding);
        // Disable tab 2-6 di awal, user harus lewat urutan
        for (int i = 1; i < jTabbedPane.getTabCount(); i++) jTabbedPane.setEnabledAt(i, false);
        // Sembunyikan bar tab header - navigasi pakai tombol saja
        jTabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override protected int calculateTabAreaHeight(int p, int r, int m) { return 0; }
            @Override protected void paintTabArea(java.awt.Graphics g, int tp, int si) {}
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // =========================================================
    //  ACTION HANDLERS
    // =========================================================

    private void jButtonCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCariActionPerformed
        String asal   = (String) jComboBoxAsal.getSelectedItem();
        String tujuan = (String) jComboBoxTujuan.getSelectedItem();
        String tanggal = jTextFieldTanggal.getText().trim();

        if (asal == null || tujuan == null || asal.equals(tujuan)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Kota asal dan tujuan harus berbeda!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!tanggal.matches("\\d{4}-\\d{2}-\\d{2}")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Format tanggal harus YYYY-MM-DD", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Penerbangan> hasil = controller.cariJadwal(asal, tujuan, tanggal);
        if (hasil.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Tidak ada penerbangan " + asal + " → " + tujuan + " pada " + tanggal,
                "Tidak Ditemukan", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Rebuild daftar jadwal
        jPanelJadwalList.removeAll();
        mapPenerbangan.clear();
        bgJadwal = new ButtonGroup();

        for (Penerbangan p : hasil) {
            mapPenerbangan.put(p.getIdPenerbangan(), p);
            jPanelJadwalList.add(buildJadwalRow(p));
        }
        jPanelJadwalList.revalidate();
        jPanelJadwalList.repaint();

        // Buka tab jadwal
        jTabbedPane.setEnabledAt(1, true);
        jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_jButtonCariActionPerformed

    private void jButtonLanjutKursiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLanjutKursiActionPerformed
        if (bgJadwal == null || bgJadwal.getSelection() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Pilih penerbangan terlebih dahulu!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        penerbanganPilihan = mapPenerbangan.get(bgJadwal.getSelection().getActionCommand());
        pilihanKelas = jRadioButtonBisnis.isSelected() ? 2 : 1;
        jmlDewasa = (int) jSpinnerDewasa.getValue();
        jmlAnak   = (int) jSpinnerAnak.getValue();
        jmlBayi   = (int) jSpinnerBayi.getValue();

        if (jmlDewasa + jmlAnak + jmlBayi == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Jumlah penumpang minimal 1!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        kategoriList.clear();
        for (int i = 0; i < jmlDewasa; i++) kategoriList.add("Dewasa");
        for (int i = 0; i < jmlAnak;   i++) kategoriList.add("Anak");
        for (int i = 0; i < jmlBayi;   i++) kategoriList.add("Bayi");

        kursiDipilihPerPenumpang.clear();
        penumpangKursiIndex = 0;
        while (penumpangKursiIndex < kategoriList.size()
               && kategoriList.get(penumpangKursiIndex).equals("Bayi")) {
            penumpangKursiIndex++;
        }

        buildSeatMap();
        updateInfoKursi();

        jTabbedPane.setEnabledAt(2, true);
        jTabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_jButtonLanjutKursiActionPerformed

    private void jButtonLanjutPenumpangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLanjutPenumpangActionPerformed
        buildPenumpangForm();
        jTabbedPane.setEnabledAt(3, true);
        jTabbedPane.setSelectedIndex(3);
    }//GEN-LAST:event_jButtonLanjutPenumpangActionPerformed

    private void jButtonHitungHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHitungHargaActionPerformed
        for (int i = 0; i < tfNamaList.size(); i++) {
            if (tfNamaList.get(i).getText().trim().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Nama penumpang " + (i+1) + " belum diisi!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        keranjangTiket.clear();
        daftarFF.clear();
        double grandTotal = 0;
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-28s: %s%n", "Penerbangan", penerbanganPilihan.getIdPenerbangan()));
        sb.append(String.format("%-28s: %s -> %s%n", "Rute",
            penerbanganPilihan.getAsal().getKota(), penerbanganPilihan.getTujuan().getKota()));
        sb.append(String.format("%-28s: %s%n", "Kelas", pilihanKelas == 2 ? "Business Class" : "Economy Class"));
        sb.append("\n");
        sb.append(String.format("%-4s %-18s %-8s %-22s %-8s %s%n", "No", "Nama", "Kat", "Kursi", "Bagasi", "Harga"));
        sb.append("-".repeat(72) + "\n");

        for (int i = 0; i < kategoriList.size(); i++) {
            String kat   = kategoriList.get(i);
            String nama  = tfNamaList.get(i).getText().trim();
            String ff    = tfFFList.get(i).getText().trim();
            int bagasi   = (int) spBagasiList.get(i).getValue();
            String kursi = kat.equals("Bayi") ? null : kursiDipilihPerPenumpang.getOrDefault(i, null);
            String kursiTampil = (kursi == null) ? "(Bayi - tanpa kursi)" : kursi;

            Tiket t = pilihanKelas == 2 ? new TiketBisnis() : new TiketEkonomi();
            t.setKategoriPenumpang(kat);
            t.setNamaPenumpang(nama);
            t.setPenerbangan(penerbanganPilihan);
            t.setNomorKursi(kursi);
            t.setBeratBagasi(bagasi);
            t.setNomorEtkt("ETKT-" + System.currentTimeMillis() + i);
            t.hitungTotalHarga();

            grandTotal += t.getTotalHarga();
            keranjangTiket.add(t);
            daftarFF.add(ff);

            sb.append(String.format("%-4d %-18s %-8s %-22s %-8s %s%n",
                i+1, nama, kat, kursiTampil, bagasi + " kg", formatRp(t.getTotalHarga())));
        }

        sb.append("-".repeat(72) + "\n");
        sb.append(String.format("%-52s %s%n", "GRAND TOTAL:", formatRp(grandTotal)));

        jTextAreaRingkasan.setText(sb.toString());
        jLabelGrandTotal.setText("Grand Total: " + formatRp(grandTotal));

        jTabbedPane.setEnabledAt(4, true);
        jTabbedPane.setSelectedIndex(4);
    }//GEN-LAST:event_jButtonHitungHargaActionPerformed

    private void jButtonBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBatalActionPerformed
        int r = javax.swing.JOptionPane.showConfirmDialog(this, "Yakin ingin membatalkan pesanan?", "Batalkan",
            javax.swing.JOptionPane.YES_NO_OPTION);
        if (r == javax.swing.JOptionPane.YES_OPTION) {
            resetAll();
        }
    }//GEN-LAST:event_jButtonBatalActionPerformed

    private void jButtonPesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesanActionPerformed
        jButtonPesan.setEnabled(false);
        jButtonPesan.setText("Menyimpan...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                boolean ok = true;
                for (int i = 0; i < keranjangTiket.size(); i++) {
                    Tiket t = keranjangTiket.get(i);
                    if (!controller.prosesPesanTiket(t.getNamaPenumpang(), daftarFF.get(i), t))
                        ok = false;
                }
                return ok;
            }
            @Override
            protected void done() {
                try {
                    boolean sukses = get();
                    jButtonPesan.setEnabled(true);
                    jButtonPesan.setText("Confirm dan Pesan");
                    if (sukses) {
                        buildBoardingPasses();
                        jTabbedPane.setEnabledAt(5, true);
                        jTabbedPane.setSelectedIndex(5);
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(MainGUI.this,
                            "Gagal menyimpan. Cek koneksi database.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    jButtonPesan.setEnabled(true);
                    javax.swing.JOptionPane.showMessageDialog(MainGUI.this,
                        "Error: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }//GEN-LAST:event_jButtonPesanActionPerformed

    private void jButtonSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelesaiActionPerformed
        resetAll();
    }//GEN-LAST:event_jButtonSelesaiActionPerformed

    // =========================================================
    //  HELPER METHODS
    // =========================================================

    private javax.swing.JPanel buildJadwalRow(Penerbangan p) {
        javax.swing.JPanel row = new javax.swing.JPanel(new java.awt.BorderLayout(10, 0));
        row.setBackground(java.awt.Color.WHITE);
        row.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(180, 180, 180)),
            javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 56));

        javax.swing.JRadioButton rb = new javax.swing.JRadioButton();
        bgJadwal.add(rb);
        rb.setActionCommand(p.getIdPenerbangan());

        String waktu = p.getWaktuKeberangkatan();
        String jam  = (waktu != null && waktu.length() >= 16) ? waktu.substring(11, 16) : "-";
        String tgl  = (waktu != null && waktu.length() >= 10) ? waktu.substring(0, 10) : "-";

        javax.swing.JPanel info = new javax.swing.JPanel(new java.awt.GridLayout(2, 1, 0, 2));
        info.setBackground(java.awt.Color.WHITE);
        javax.swing.JLabel l1 = new javax.swing.JLabel(
            p.getIdPenerbangan() + "  |  " + p.getAsal().getKodeBandara()
            + " \u2192 " + p.getTujuan().getKodeBandara()
            + "  |  Gate " + p.getGate()
            + "  |  " + tgl + "  " + jam);
        javax.swing.JLabel l2 = new javax.swing.JLabel(
            p.getPesawat().getNamaMaskapai() + " \u00b7 " + p.getPesawat().getJenisPesawat()
            + "  |  Harga Dasar: " + formatRp(p.getHargaDasar()));
        l1.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 13));
        l2.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 11));
        l2.setForeground(new java.awt.Color(100, 100, 100));
        info.add(l1); info.add(l2);

        row.add(rb, java.awt.BorderLayout.WEST);
        row.add(info, java.awt.BorderLayout.CENTER);
        row.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { rb.setSelected(true); }
        });
        return row;
    }

    private void buildSeatMap() {
        kursiTerisi = controller.lihatKursiTerisi(penerbanganPilihan.getIdPenerbangan());
        tombolKursiList.clear();
        jPanelSeatMap.removeAll();

        String[] huruf = {"A", "B", "C", null, "D", "E", "F"};
        int startBaris = pilihanKelas == 2 ? 1 : 10;
        int endBaris   = pilihanKelas == 2 ? 9 : 30;

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(2, 2, 2, 2);

        int col = 0;
        for (String h : huruf) {
            gbc.gridx = col; gbc.gridy = 0;
            if (h == null) {
                jPanelSeatMap.add(new javax.swing.JLabel("  "), gbc);
            } else {
                javax.swing.JLabel lbl = new javax.swing.JLabel(h, javax.swing.SwingConstants.CENTER);
                lbl.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 12));
                lbl.setPreferredSize(new java.awt.Dimension(44, 18));
                jPanelSeatMap.add(lbl, gbc);
            }
            col++;
        }

        for (int baris = startBaris; baris <= endBaris; baris++) {
            col = 0;
            for (String h : huruf) {
                gbc.gridx = col; gbc.gridy = baris - startBaris + 1;
                if (h == null) {
                    jPanelSeatMap.add(new javax.swing.JLabel("  "), gbc);
                } else {
                    String kode = baris + h;
                    javax.swing.JButton btn = buildSeatBtn(kode);
                    tombolKursiList.add(btn);
                    jPanelSeatMap.add(btn, gbc);
                }
                col++;
            }
        }
        jPanelSeatMap.revalidate();
        jPanelSeatMap.repaint();
    }

    private javax.swing.JButton buildSeatBtn(String kode) {
        javax.swing.JButton btn = new javax.swing.JButton(kode);
        btn.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
        btn.setPreferredSize(new java.awt.Dimension(44, 32));
        btn.setFocusPainted(false);
        btn.setMargin(new java.awt.Insets(0, 0, 0, 0));

        if (kursiTerisi.contains(kode)) {
            btn.setBackground(new java.awt.Color(255, 200, 200));
            btn.setForeground(new java.awt.Color(200, 0, 0));
            btn.setEnabled(false);
        } else {
            btn.setBackground(new java.awt.Color(210, 230, 255));
            btn.setForeground(new java.awt.Color(0, 70, 160));
            btn.addActionListener(e -> onKursiDipilih(kode, btn));
        }
        return btn;
    }

    private void onKursiDipilih(String kode, javax.swing.JButton btn) {
        long butuhKursi = kategoriList.stream().filter(k -> !k.equals("Bayi")).count();
        if (kursiDipilihPerPenumpang.size() >= butuhKursi) return;

        kursiDipilihPerPenumpang.put(penumpangKursiIndex, kode);
        btn.setBackground(new java.awt.Color(0, 180, 80));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setEnabled(false);

        penumpangKursiIndex++;
        while (penumpangKursiIndex < kategoriList.size()
               && kategoriList.get(penumpangKursiIndex).equals("Bayi")) {
            penumpangKursiIndex++;
        }
        updateInfoKursi();

        if (kursiDipilihPerPenumpang.size() >= butuhKursi) {
            jButtonLanjutPenumpang.setEnabled(true);
            jLabelInfoKursi.setText("\u2714 Semua kursi sudah dipilih. Klik Lanjut.");
            jLabelInfoKursi.setForeground(new java.awt.Color(0, 140, 0));
            for (javax.swing.JButton b : tombolKursiList) {
                if (b.isEnabled()) {
                    b.setEnabled(false);
                    b.setBackground(new java.awt.Color(220, 220, 220));
                    b.setForeground(new java.awt.Color(150, 150, 150));
                }
            }
        }
    }

    private void updateInfoKursi() {
        long butuhKursi = kategoriList.stream().filter(k -> !k.equals("Bayi")).count();
        if (penumpangKursiIndex < kategoriList.size()) {
            jLabelInfoKursi.setText("Pilih kursi untuk Penumpang " + (penumpangKursiIndex + 1)
                + " (" + kategoriList.get(penumpangKursiIndex) + ")"
                + "  [" + kursiDipilihPerPenumpang.size() + "/" + butuhKursi + " terpilih]");
            jLabelInfoKursi.setForeground(new java.awt.Color(0, 102, 204));
        }
    }

    private void buildPenumpangForm() {
        jPanelPenumpangContent.removeAll();
        tfNamaList.clear(); tfFFList.clear(); spBagasiList.clear();

        for (int i = 0; i < kategoriList.size(); i++) {
            String kat = kategoriList.get(i);
            boolean isBayi = kat.equals("Bayi");
            String kursiInfo = isBayi ? "(tanpa kursi)"
                : "Kursi: " + kursiDipilihPerPenumpang.getOrDefault(i, "-");

            javax.swing.JPanel row = new javax.swing.JPanel(new java.awt.GridBagLayout());
            row.setBackground(java.awt.Color.WHITE);
            row.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(180, 180, 180)),
                javax.swing.BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
            row.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 120));

            java.awt.GridBagConstraints gc = new java.awt.GridBagConstraints();
            gc.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gc.insets = new java.awt.Insets(3, 6, 3, 6);
            gc.anchor = java.awt.GridBagConstraints.WEST;

            // Header
            gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 4;
            javax.swing.JLabel lblTitle = new javax.swing.JLabel(
                "Penumpang " + (i+1) + " \u2014 " + kat + "   " + kursiInfo);
            lblTitle.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 13));
            lblTitle.setForeground(new java.awt.Color(0, 70, 160));
            row.add(lblTitle, gc);
            gc.gridwidth = 1;

            // Nama
            javax.swing.JTextField tfNama = new javax.swing.JTextField(18);
            gc.gridy = 1; gc.gridx = 0; gc.weightx = 0;
            row.add(makeJLabel("Nama Penumpang :"), gc);
            gc.gridx = 1; gc.weightx = 0.5;
            row.add(tfNama, gc);

            // FF
            javax.swing.JTextField tfFF = new javax.swing.JTextField(12);
            gc.gridx = 2; gc.weightx = 0;
            row.add(makeJLabel("No. Frequent Flyer :"), gc);
            gc.gridx = 3; gc.weightx = 0.3;
            row.add(tfFF, gc);

            tfNamaList.add(tfNama); tfFFList.add(tfFF);

            // Bagasi
            javax.swing.JSpinner spBagasi = new javax.swing.JSpinner(
                new javax.swing.SpinnerNumberModel(0, 0, 200, 1));
            spBagasi.setPreferredSize(new java.awt.Dimension(70, 24));
            if (!isBayi) {
                gc.gridy = 2; gc.gridx = 0; gc.weightx = 0;
                row.add(makeJLabel("Berat Bagasi (Kg) :"), gc);
                gc.gridx = 1; gc.weightx = 0.3;
                row.add(spBagasi, gc);
            }
            spBagasiList.add(spBagasi);
            jPanelPenumpangContent.add(row);
        }
        jPanelPenumpangContent.revalidate();
        jPanelPenumpangContent.repaint();
    }

    private void buildBoardingPasses() {
        jPanelBoardingContent.removeAll();
        for (Tiket t : keranjangTiket) {
            jPanelBoardingContent.add(buildBoardingCard(t));
            jPanelBoardingContent.add(javax.swing.Box.createVerticalStrut(12));
        }
        jPanelBoardingContent.revalidate();
        jPanelBoardingContent.repaint();
    }

    private javax.swing.JPanel buildBoardingCard(Tiket t) {
        String rawB  = t.getPenerbangan().getWaktuKeberangkatan();
        String rawBd = t.getPenerbangan().getWaktuBoarding();
        String[] bln = {"","JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        String tgl = "N/A", jamB = "N/A", jamBd = "N/A";
        if (rawB  != null && rawB.length()  >= 16) { tgl = rawB.substring(8,10) + bln[Integer.parseInt(rawB.substring(5,7))]; jamB  = rawB.substring(11,16); }
        if (rawBd != null && rawBd.length() >= 16) { jamBd = rawBd.substring(11,16); }

        String nama    = t.getNamaPenumpang().toUpperCase();
        String asal    = t.getPenerbangan().getAsal().getKota().toUpperCase();
        String tujuan  = t.getPenerbangan().getTujuan().getKota().toUpperCase();
        String kodeA   = t.getPenerbangan().getAsal().getKodeBandara();
        String kodeT   = t.getPenerbangan().getTujuan().getKodeBandara();
        String flight  = t.getPenerbangan().getIdPenerbangan();
        String gate    = t.getPenerbangan().getGate();
        String seat    = t.getNomorKursi() != null ? t.getNomorKursi() : "-";
        String seq     = String.format("%04d", (int)(Math.random() * 10000));
        String pcswt   = (t.getBeratBagasi() > 0 ? "1 / " : "0 / ") + t.getBeratBagasi() + " kg";
        String kelas   = t.getKelasTiket().replace(" CLASS", "");

        javax.swing.JPanel card = new javax.swing.JPanel(new java.awt.BorderLayout());
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180,180,180), 1));
        card.setMaximumSize(new java.awt.Dimension(760, 165));

        javax.swing.JPanel left = new javax.swing.JPanel(new java.awt.GridBagLayout());
        left.setBackground(java.awt.Color.WHITE);
        left.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 12));
        java.awt.GridBagConstraints g = new java.awt.GridBagConstraints();
        g.fill = java.awt.GridBagConstraints.HORIZONTAL; g.insets = new java.awt.Insets(1,4,1,8);
        java.awt.Color cD = new java.awt.Color(20,20,20), cG = new java.awt.Color(100,100,100);

        g.gridx=0; g.gridy=0; g.gridwidth=4;
        javax.swing.JLabel lHead = new javax.swing.JLabel("BOARDING PASS   " + t.getKelasTiket());
        lHead.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 12));
        lHead.setForeground(new java.awt.Color(10,60,140));
        left.add(lHead, g);

        g.gridy=1; g.gridwidth=4;
        javax.swing.JLabel lNama = new javax.swing.JLabel(nama);
        lNama.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 15));
        lNama.setForeground(cD); left.add(lNama, g);
        g.gridwidth=1;

        String[] h1 = {"FROM","FLIGHT","DATE","TIME"};
        String[] v1 = {asal, flight, tgl, jamB};
        String[] h2 = {"TO","GATE","BOARDING","SEAT"};
        String[] v2 = {tujuan, gate, jamBd, seat};
        for (int i = 0; i < 4; i++) {
            g.gridy=2; g.gridx=i;
            javax.swing.JLabel lh = new javax.swing.JLabel(h1[i]);
            lh.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 9)); lh.setForeground(cG); left.add(lh, g);
            g.gridy=3;
            javax.swing.JLabel lv = new javax.swing.JLabel(v1[i]);
            lv.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 12)); lv.setForeground(cD); left.add(lv, g);
            g.gridy=4;
            javax.swing.JLabel lh2 = new javax.swing.JLabel(h2[i]);
            lh2.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 9)); lh2.setForeground(cG); left.add(lh2, g);
            g.gridy=5;
            javax.swing.JLabel lv2 = new javax.swing.JLabel(v2[i]);
            lv2.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 12)); lv2.setForeground(cD); left.add(lv2, g);
        }

        javax.swing.JPanel right = new javax.swing.JPanel(new java.awt.GridLayout(0,1,0,3));
        right.setBackground(new java.awt.Color(20,60,140));
        right.setBorder(javax.swing.BorderFactory.createEmptyBorder(12,14,12,14));
        right.setPreferredSize(new java.awt.Dimension(185, 0));
        java.awt.Color cW = java.awt.Color.WHITE;
        javax.swing.JLabel[] rl = {
            new javax.swing.JLabel(kelas), new javax.swing.JLabel(nama),
            new javax.swing.JLabel(kodeA + " \u2192 " + kodeT),
            new javax.swing.JLabel(flight + "  " + tgl + "  " + jamB),
            new javax.swing.JLabel("Seat: " + seat + "   Seq: " + seq),
            new javax.swing.JLabel("PCS/WT: " + pcswt),
            new javax.swing.JLabel("PAPER TICKET")
        };
        java.awt.Font[] rf = {
            new java.awt.Font("sansserif",java.awt.Font.BOLD,14),
            new java.awt.Font("sansserif",java.awt.Font.BOLD,11),
            new java.awt.Font("sansserif",java.awt.Font.PLAIN,10),
            new java.awt.Font("sansserif",java.awt.Font.PLAIN,10),
            new java.awt.Font("sansserif",java.awt.Font.BOLD,10),
            new java.awt.Font("sansserif",java.awt.Font.PLAIN,9),
            new java.awt.Font("sansserif",java.awt.Font.ITALIC,9)
        };
        for (int i = 0; i < rl.length; i++) { rl[i].setFont(rf[i]); rl[i].setForeground(cW); right.add(rl[i]); }

        javax.swing.JPanel footer = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 4));
        footer.setBackground(new java.awt.Color(245,248,255));
        javax.swing.JLabel wL = new javax.swing.JLabel("\u26a0  PLEASE BE AT THE GATE 35 MINUTES BEFORE DEPARTURE");
        wL.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 10));
        wL.setForeground(new java.awt.Color(140,70,0)); footer.add(wL);

        card.add(left, java.awt.BorderLayout.CENTER);
        card.add(right, java.awt.BorderLayout.EAST);
        card.add(footer, java.awt.BorderLayout.SOUTH);

        javax.swing.JPanel wrap = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 4));
        wrap.setBackground(java.awt.Color.WHITE); wrap.add(card); return wrap;
    }

    private void resetAll() {
        penerbanganPilihan = null;
        keranjangTiket.clear(); daftarFF.clear();
        kategoriList.clear(); kursiDipilihPerPenumpang.clear();
        penumpangKursiIndex = 0;
        // Reset tabs
        for (int i = 1; i < jTabbedPane.getTabCount(); i++) jTabbedPane.setEnabledAt(i, false);
        jTabbedPane.setSelectedIndex(0);
        jButtonLanjutPenumpang.setEnabled(false);
    }

    private String[] getKotaFromDB() {
        List<String> kota = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT kota FROM bandara ORDER BY kota");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) kota.add(rs.getString("kota"));
        } catch (SQLException e) {
            kota.addAll(Arrays.asList("Denpasar", "Jakarta", "Sydney"));
        }
        return kota.toArray(new String[0]);
    }

    private javax.swing.JLabel makeJLabel(String text) {
        javax.swing.JLabel l = new javax.swing.JLabel(text);
        l.setFont(new java.awt.Font("sansserif", java.awt.Font.PLAIN, 13));
        return l;
    }

    private String formatRp(double amount) {
        return "Rp " + NumberFormat.getNumberInstance(new Locale("id", "ID")).format((long) amount);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> new MainGUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgKelas;
    private javax.swing.JTabbedPane jTabbedPane;
    // Tab Cari
    private javax.swing.JPanel jPanelCari;
    private javax.swing.JLabel jLabelJudul;
    private javax.swing.JLabel jLabelAsal;
    private javax.swing.JComboBox<String> jComboBoxAsal;
    private javax.swing.JLabel jLabelTujuan;
    private javax.swing.JComboBox<String> jComboBoxTujuan;
    private javax.swing.JLabel jLabelTanggal;
    private javax.swing.JTextField jTextFieldTanggal;
    private javax.swing.JButton jButtonCari;
    // Tab Jadwal
    private javax.swing.JPanel jPanelJadwal;
    private javax.swing.JLabel jLabelPilihPenerbangan;
    private javax.swing.JScrollPane jScrollPaneJadwal;
    private javax.swing.JPanel jPanelJadwalList;
    private javax.swing.JLabel jLabelPilihKelas;
    private javax.swing.JRadioButton jRadioButtonEkonomi;
    private javax.swing.JRadioButton jRadioButtonBisnis;
    private javax.swing.JLabel jLabelJumlahPenumpang;
    private javax.swing.JLabel jLabelDewasa;
    private javax.swing.JSpinner jSpinnerDewasa;
    private javax.swing.JLabel jLabelAnak;
    private javax.swing.JSpinner jSpinnerAnak;
    private javax.swing.JLabel jLabelBayi;
    private javax.swing.JSpinner jSpinnerBayi;
    private javax.swing.JButton jButtonLanjutKursi;
    // Tab Kursi
    private javax.swing.JPanel jPanelKursi;
    private javax.swing.JLabel jLabelInfoKursi;
    private javax.swing.JPanel jPanelLegenda;
    private javax.swing.JPanel jPanelLegendFree;
    private javax.swing.JLabel jLabelLegendFree;
    private javax.swing.JPanel jPanelLegendTaken;
    private javax.swing.JLabel jLabelLegendTaken;
    private javax.swing.JPanel jPanelLegendSel;
    private javax.swing.JLabel jLabelLegendSel;
    private javax.swing.JScrollPane jScrollPaneSeat;
    private javax.swing.JPanel jPanelSeatMap;
    private javax.swing.JButton jButtonLanjutPenumpang;
    // Tab Penumpang
    private javax.swing.JPanel jPanelPenumpang;
    private javax.swing.JScrollPane jScrollPanePenumpang;
    private javax.swing.JPanel jPanelPenumpangContent;
    private javax.swing.JButton jButtonHitungHarga;
    // Tab Konfirmasi
    private javax.swing.JPanel jPanelKonfirmasi;
    private javax.swing.JScrollPane jScrollPaneRingkasan;
    private javax.swing.JTextArea jTextAreaRingkasan;
    private javax.swing.JLabel jLabelGrandTotal;
    private javax.swing.JButton jButtonBatal;
    private javax.swing.JButton jButtonPesan;
    // Tab Boarding
    private javax.swing.JPanel jPanelBoarding;
    private javax.swing.JLabel jLabelBoardingTitle;
    private javax.swing.JScrollPane jScrollPaneBoarding;
    private javax.swing.JPanel jPanelBoardingContent;
    private javax.swing.JButton jButtonSelesai;
    // End of variables declaration//GEN-END:variables
}
