package view;

import controller.TiketController;
import dao.DatabaseConnection;
import model.Penerbangan;
import model.Tiket;
import model.TiketBisnis;
import model.TiketEkonomi;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

/**
 * MainGUI - Sistem Pemesanan Tiket Pesawat
 * Tema: Default Windows (putih/abu), mudah diedit di NetBeans.
 * Letakkan di package view, jalankan main().
 */
public class MainGUI extends JFrame {

    // ===== WARNA & FONT (tema default putih/abu seperti di foto) =====
    private static final Color C_BG        = UIManager.getColor("Panel.background") != null
                                              ? UIManager.getColor("Panel.background")
                                              : new Color(240, 240, 240);
    private static final Color C_WHITE     = Color.WHITE;
    private static final Color C_BORDER    = new Color(180, 180, 180);
    private static final Color C_TEXT      = new Color(30, 30, 30);
    private static final Color C_MUTED     = new Color(100, 100, 100);
    private static final Color C_HEADER_BG = new Color(230, 230, 230);
    private static final Color C_BLUE      = new Color(0, 102, 204);        // aksen biru
    private static final Color C_BLUE_DARK = new Color(0, 70, 160);
    private static final Color C_GREEN     = new Color(0, 140, 0);
    private static final Color C_RED       = new Color(200, 0, 0);
    private static final Color C_SEAT_FREE = new Color(210, 230, 255);      // biru muda
    private static final Color C_SEAT_TAKEN= new Color(255, 200, 200);      // merah muda
    private static final Color C_SEAT_SEL  = new Color(0, 180, 80);         // hijau

    private static final Font FONT_TITLE  = new Font("Dialog", Font.BOLD, 18);
    private static final Font FONT_HEADER = new Font("Dialog", Font.BOLD, 13);
    private static final Font FONT_BODY   = new Font("Dialog", Font.PLAIN, 13);
    private static final Font FONT_SMALL  = new Font("Dialog", Font.PLAIN, 11);
    private static final Font FONT_MONO   = new Font("Monospaced", Font.PLAIN, 12);
    private static final Font FONT_LABEL  = new Font("Dialog", Font.BOLD, 12);

    // ===== STATE =====
    private CardLayout cardLayout;
    private JPanel mainPanel;
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

    // ===== KOMPONEN =====
    // Panel 1
    private JComboBox<String> cbAsal, cbTujuan;
    private JTextField tfTanggal;
    // Panel 2
    private JPanel pnlJadwal;
    private ButtonGroup bgJadwal;
    private Map<String, Penerbangan> mapPenerbangan = new LinkedHashMap<>();
    private JRadioButton rbEkonomi, rbBisnis;
    private JSpinner spDewasa, spAnak, spBayi;
    // Panel 3
    private JPanel pnlSeatMap;
    private JLabel lblInfoKursi;
    private JButton btnKonfirmasiKursi;
    private List<JButton> tombolKursiList = new ArrayList<>();
    // Panel 4
    private List<JTextField> tfNamaList   = new ArrayList<>();
    private List<JTextField> tfFFList     = new ArrayList<>();
    private List<JSpinner>   spBagasiList = new ArrayList<>();
    // Panel 5
    private JTextArea taRingkasan;
    private JLabel lblGrandTotal;
    private JButton btnPesan, btnBatal;
    // Panel 6
    private JPanel pnlBoardingPasses;

    // ==============================================================
    //  KONSTRUKTOR
    // ==============================================================
    public MainGUI() {
        controller = new TiketController();
        setTitle("Sistem Pemesanan Tiket Pesawat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);

        mainPanel.add(buildPanelCari(),        "CARI");
        mainPanel.add(buildPanelJadwal(),      "JADWAL");
        mainPanel.add(buildPanelKursi(),       "KURSI");
        mainPanel.add(buildPanelPenumpang(),   "PENUMPANG");
        mainPanel.add(buildPanelKonfirmasi(),  "KONFIRMASI");
        mainPanel.add(buildPanelBoarding(),    "BOARDING");

        add(mainPanel);
        cardLayout.show(mainPanel, "CARI");
        setVisible(true);
    }

    // ==============================================================
    //  PANEL 1 – PENCARIAN
    // ==============================================================
    private JPanel buildPanelCari() {
        JPanel panel = new JPanel(new BorderLayout());

        // Judul
        JLabel lblJudul = new JLabel("SISTEM PEMESANAN TIKET PESAWAT", SwingConstants.CENTER);
        lblJudul.setFont(FONT_TITLE);
        lblJudul.setBorder(new EmptyBorder(20, 0, 16, 0));
        panel.add(lblJudul, BorderLayout.NORTH);

        // Form tengah
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(10, 80, 10, 80));
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill      = GridBagConstraints.HORIZONTAL;
        gc.insets    = new Insets(8, 8, 8, 8);
        gc.weightx   = 0.0;
        gc.anchor    = GridBagConstraints.WEST;

        cbAsal   = new JComboBox<>(getKotaFromDB());
        cbTujuan = new JComboBox<>(getKotaFromDB());
        tfTanggal = new JTextField("2026-06-22");
        cbAsal.setFont(FONT_BODY);
        cbTujuan.setFont(FONT_BODY);
        tfTanggal.setFont(FONT_BODY);

        int row = 0;
        addFormRow(form, gc, row++, "Kota/Bandara Asal :",    cbAsal);
        addFormRow(form, gc, row++, "Kota/Bandara Tujuan :",  cbTujuan);
        addFormRow(form, gc, row++, "Tanggal (YYYY-MM-DD) :", tfTanggal);

        panel.add(form, BorderLayout.CENTER);

        // Tombol
        JButton btnCari = new JButton("Cari Penerbangan");
        btnCari.setFont(FONT_HEADER);
        btnCari.addActionListener(e -> doCariPenerbangan());
        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 16));
        botPanel.add(btnCari);
        panel.add(botPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ==============================================================
    //  PANEL 2 – JADWAL & PENUMPANG
    // ==============================================================
    private JPanel buildPanelJadwal() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(new EmptyBorder(12, 16, 12, 16));

        // Header
        panel.add(headerBar("Jadwal Penerbangan", "CARI"), BorderLayout.NORTH);

        // Tengah – list jadwal + kelas + penumpang
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // --- Sub: Pilih Penerbangan ---
        center.add(sectionLabel("Pilih Penerbangan :"));
        center.add(Box.createVerticalStrut(6));

        pnlJadwal = new JPanel();
        pnlJadwal.setLayout(new BoxLayout(pnlJadwal, BoxLayout.Y_AXIS));
        pnlJadwal.setBackground(C_WHITE);

        JScrollPane scrollJadwal = new JScrollPane(pnlJadwal);
        scrollJadwal.setPreferredSize(new Dimension(0, 140));
        scrollJadwal.setBorder(new LineBorder(C_BORDER));
        scrollJadwal.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(scrollJadwal);
        center.add(Box.createVerticalStrut(16));

        // --- Sub: Kelas Tiket ---
        center.add(sectionLabel("Pilih Kelas Tiket :"));
        center.add(Box.createVerticalStrut(6));
        JPanel kelasPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        rbEkonomi = new JRadioButton("Economy Class (Bagasi gratis s/d 20kg)");
        rbBisnis  = new JRadioButton("Business Class (Bagasi gratis s/d 30kg)");
        rbEkonomi.setFont(FONT_BODY);
        rbBisnis.setFont(FONT_BODY);
        ButtonGroup bgKelas = new ButtonGroup();
        bgKelas.add(rbEkonomi);
        bgKelas.add(rbBisnis);
        rbEkonomi.setSelected(true);
        kelasPanel.add(rbEkonomi);
        kelasPanel.add(rbBisnis);
        kelasPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(kelasPanel);
        center.add(Box.createVerticalStrut(16));

        // --- Sub: Jumlah Penumpang ---
        center.add(sectionLabel("Jumlah Penumpang :"));
        center.add(Box.createVerticalStrut(6));

        spDewasa = new JSpinner(new SpinnerNumberModel(1, 0, 9, 1));
        spAnak   = new JSpinner(new SpinnerNumberModel(0, 0, 9, 1));
        spBayi   = new JSpinner(new SpinnerNumberModel(0, 0, 9, 1));
        spDewasa.setFont(FONT_BODY);
        spAnak.setFont(FONT_BODY);
        spBayi.setFont(FONT_BODY);

        JPanel paxPanel = new JPanel(new GridLayout(1, 6, 8, 0));
        paxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        paxPanel.add(new JLabel("Dewasa :"));
        paxPanel.add(spDewasa);
        paxPanel.add(new JLabel("  Anak :"));
        paxPanel.add(spAnak);
        paxPanel.add(new JLabel("  Bayi :"));
        paxPanel.add(spBayi);
        for (Component c : paxPanel.getComponents()) {
            if (c instanceof JLabel) ((JLabel) c).setFont(FONT_BODY);
        }
        center.add(paxPanel);

        JScrollPane centerScroll = new JScrollPane(center);
        centerScroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(centerScroll, BorderLayout.CENTER);

        // Tombol lanjut
        JButton btnLanjut = new JButton("Lanjut → Pilih Kursi");
        btnLanjut.setFont(FONT_HEADER);
        btnLanjut.addActionListener(e -> doLanjutKePilihKursi());
        JPanel bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bot.add(btnLanjut);
        panel.add(bot, BorderLayout.SOUTH);

        return panel;
    }

    // ==============================================================
    //  PANEL 3 – PILIH KURSI
    // ==============================================================
    private JPanel buildPanelKursi() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(new EmptyBorder(12, 16, 12, 16));

        // Header
        panel.add(headerBar("Pilih Kursi", "JADWAL"), BorderLayout.NORTH);

        // Info penumpang aktif
        lblInfoKursi = new JLabel("Pilih kursi untuk Penumpang 1");
        lblInfoKursi.setFont(FONT_HEADER);
        lblInfoKursi.setForeground(C_BLUE);
        lblInfoKursi.setBorder(new EmptyBorder(4, 0, 4, 0));

        // Legenda
        JPanel legenda = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 4));
        legenda.add(legendBox(C_SEAT_FREE, "Tersedia"));
        legenda.add(legendBox(C_SEAT_TAKEN, "Terisi"));
        legenda.add(legendBox(C_SEAT_SEL,   "Dipilih Anda"));

        JPanel topInfo = new JPanel(new BorderLayout());
        topInfo.add(lblInfoKursi, BorderLayout.WEST);
        topInfo.add(legenda, BorderLayout.EAST);
        panel.add(topInfo, BorderLayout.AFTER_LINE_ENDS); // trick: pakai wrap

        // Gabung header info di CENTER atas
        JPanel centerWrapper = new JPanel(new BorderLayout(0, 8));
        centerWrapper.add(topInfo, BorderLayout.NORTH);

        pnlSeatMap = new JPanel(new GridBagLayout());
        pnlSeatMap.setBackground(C_WHITE);
        pnlSeatMap.setBorder(new LineBorder(C_BORDER));

        JScrollPane seatScroll = new JScrollPane(pnlSeatMap);
        seatScroll.setBorder(new LineBorder(C_BORDER));
        centerWrapper.add(seatScroll, BorderLayout.CENTER);
        panel.add(centerWrapper, BorderLayout.CENTER);

        // Tombol lanjut
        btnKonfirmasiKursi = new JButton("Lanjut → Data Penumpang");
        btnKonfirmasiKursi.setFont(FONT_HEADER);
        btnKonfirmasiKursi.setEnabled(false);
        btnKonfirmasiKursi.addActionListener(e -> doLanjutKePenumpang());
        JPanel bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bot.add(btnKonfirmasiKursi);
        panel.add(bot, BorderLayout.SOUTH);

        return panel;
    }

    // ==============================================================
    //  PANEL 4 – DATA PENUMPANG
    // ==============================================================
    private JPanel buildPanelPenumpang() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(new EmptyBorder(12, 16, 12, 16));

        panel.add(headerBar("Data Penumpang", "KURSI"), BorderLayout.NORTH);

        // Content diisi dinamis
        JPanel content = new JPanel();
        content.setName("pnlPenumpangContent");
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(C_WHITE);

        JScrollPane sc = new JScrollPane(content);
        sc.setBorder(new LineBorder(C_BORDER));
        panel.add(sc, BorderLayout.CENTER);

        JButton btnLanjut = new JButton("Hitung Harga & Konfirmasi");
        btnLanjut.setFont(FONT_HEADER);
        btnLanjut.addActionListener(e -> doHitungHarga());
        JPanel bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bot.add(btnLanjut);
        panel.add(bot, BorderLayout.SOUTH);

        return panel;
    }

    // ==============================================================
    //  PANEL 5 – KONFIRMASI
    // ==============================================================
    private JPanel buildPanelKonfirmasi() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(new EmptyBorder(12, 16, 12, 16));

        panel.add(headerBar("Konfirmasi Pesanan", "PENUMPANG"), BorderLayout.NORTH);

        taRingkasan = new JTextArea();
        taRingkasan.setFont(FONT_MONO);
        taRingkasan.setEditable(false);
        taRingkasan.setBackground(C_WHITE);
        taRingkasan.setBorder(new EmptyBorder(8, 10, 8, 10));
        JScrollPane sc = new JScrollPane(taRingkasan);
        sc.setBorder(new LineBorder(C_BORDER));
        panel.add(sc, BorderLayout.CENTER);

        lblGrandTotal = new JLabel("Grand Total: Rp 0");
        lblGrandTotal.setFont(new Font("Dialog", Font.BOLD, 15));
        lblGrandTotal.setForeground(C_BLUE_DARK);

        btnBatal = new JButton("Batalkan");
        btnPesan = new JButton("Confirm & Pesan");
        btnBatal.setFont(FONT_BODY);
        btnPesan.setFont(FONT_HEADER);
        btnBatal.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this, "Yakin ingin membatalkan?", "Batalkan",
                JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) { resetAll(); cardLayout.show(mainPanel, "CARI"); }
        });
        btnPesan.addActionListener(e -> doSimpanTiket());

        JPanel bot = new JPanel(new BorderLayout());
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnRow.add(btnBatal);
        btnRow.add(btnPesan);
        bot.add(lblGrandTotal, BorderLayout.WEST);
        bot.add(btnRow, BorderLayout.EAST);
        bot.setBorder(new EmptyBorder(8, 0, 0, 0));
        panel.add(bot, BorderLayout.SOUTH);

        return panel;
    }

    // ==============================================================
    //  PANEL 6 – BOARDING PASS
    // ==============================================================
    private JPanel buildPanelBoarding() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel ttl = new JLabel("✈  Boarding Pass", SwingConstants.CENTER);
        ttl.setFont(FONT_TITLE);
        ttl.setForeground(C_BLUE_DARK);
        ttl.setBorder(new EmptyBorder(0, 0, 8, 0));
        panel.add(ttl, BorderLayout.NORTH);

        pnlBoardingPasses = new JPanel();
        pnlBoardingPasses.setLayout(new BoxLayout(pnlBoardingPasses, BoxLayout.Y_AXIS));
        pnlBoardingPasses.setBackground(C_WHITE);
        JScrollPane sc = new JScrollPane(pnlBoardingPasses);
        sc.setBorder(new LineBorder(C_BORDER));
        panel.add(sc, BorderLayout.CENTER);

        JButton btnSelesai = new JButton("Selesai / Pesan Lagi");
        btnSelesai.setFont(FONT_HEADER);
        btnSelesai.addActionListener(e -> { resetAll(); cardLayout.show(mainPanel, "CARI"); });
        JPanel bot = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bot.add(btnSelesai);
        panel.add(bot, BorderLayout.SOUTH);

        return panel;
    }

    // ==============================================================
    //  LOGIKA – CARI PENERBANGAN
    // ==============================================================
    private void doCariPenerbangan() {
        String asal   = (String) cbAsal.getSelectedItem();
        String tujuan = (String) cbTujuan.getSelectedItem();
        String tanggal = tfTanggal.getText().trim();

        if (asal == null || tujuan == null || asal.equals(tujuan)) {
            JOptionPane.showMessageDialog(this, "Kota asal dan tujuan harus berbeda!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!tanggal.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Format tanggal harus YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Penerbangan> hasil = controller.cariJadwal(asal, tujuan, tanggal);
        if (hasil.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Tidak ada penerbangan " + asal + " → " + tujuan + " pada " + tanggal,
                "Tidak Ditemukan", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        pnlJadwal.removeAll();
        mapPenerbangan.clear();
        bgJadwal = new ButtonGroup();

        for (Penerbangan p : hasil) {
            mapPenerbangan.put(p.getIdPenerbangan(), p);
            pnlJadwal.add(buildJadwalRow(p));
        }
        pnlJadwal.revalidate();
        pnlJadwal.repaint();
        cardLayout.show(mainPanel, "JADWAL");
    }

    private JPanel buildJadwalRow(Penerbangan p) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(C_WHITE);
        row.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, C_BORDER),
            new EmptyBorder(8, 10, 8, 10)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));

        JRadioButton rb = new JRadioButton();
        bgJadwal.add(rb);
        rb.setActionCommand(p.getIdPenerbangan());

        String waktu = p.getWaktuKeberangkatan();
        String jam  = waktu != null && waktu.length() >= 16 ? waktu.substring(11, 16) : "-";
        String tgl  = waktu != null && waktu.length() >= 10 ? waktu.substring(0, 10) : "-";

        JPanel info = new JPanel(new GridLayout(2, 1, 0, 2));
        info.setBackground(C_WHITE);
        JLabel l1 = new JLabel(p.getIdPenerbangan() + "  |  " + p.getAsal().getKodeBandara()
            + " → " + p.getTujuan().getKodeBandara()
            + "  |  Gate " + p.getGate()
            + "  |  " + tgl + "  " + jam);
        JLabel l2 = new JLabel(p.getPesawat().getNamaMaskapai() + " · " + p.getPesawat().getJenisPesawat()
            + "  |  Harga Dasar: " + formatRp(p.getHargaDasar()));
        l1.setFont(FONT_HEADER);
        l2.setFont(FONT_SMALL);
        l2.setForeground(C_MUTED);
        info.add(l1);
        info.add(l2);

        row.add(rb, BorderLayout.WEST);
        row.add(info, BorderLayout.CENTER);

        row.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { rb.setSelected(true); }
        });
        return row;
    }

    // ==============================================================
    //  LOGIKA – LANJUT KE PILIH KURSI
    // ==============================================================
    private void doLanjutKePilihKursi() {
        if (bgJadwal == null || bgJadwal.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Pilih penerbangan terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        penerbanganPilihan = mapPenerbangan.get(bgJadwal.getSelection().getActionCommand());
        pilihanKelas = rbBisnis.isSelected() ? 2 : 1;
        jmlDewasa = (int) spDewasa.getValue();
        jmlAnak   = (int) spAnak.getValue();
        jmlBayi   = (int) spBayi.getValue();

        if (jmlDewasa + jmlAnak + jmlBayi == 0) {
            JOptionPane.showMessageDialog(this, "Jumlah penumpang minimal 1!", "Error", JOptionPane.ERROR_MESSAGE);
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
        cardLayout.show(mainPanel, "KURSI");
    }

    private void buildSeatMap() {
        kursiTerisi = controller.lihatKursiTerisi(penerbanganPilihan.getIdPenerbangan());
        tombolKursiList.clear();
        pnlSeatMap.removeAll();

        String[] huruf     = {"A", "B", "C", null, "D", "E", "F"};
        int startBaris = pilihanKelas == 2 ? 1  : 10;
        int endBaris   = pilihanKelas == 2 ? 9  : 30;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // Header kolom huruf
        int col = 0;
        for (String h : huruf) {
            gbc.gridx = col; gbc.gridy = 0;
            if (h == null) {
                pnlSeatMap.add(new JLabel("  "), gbc); // lorong
            } else {
                JLabel lbl = new JLabel(h, SwingConstants.CENTER);
                lbl.setFont(FONT_LABEL);
                lbl.setPreferredSize(new Dimension(44, 18));
                pnlSeatMap.add(lbl, gbc);
            }
            col++;
        }

        for (int baris = startBaris; baris <= endBaris; baris++) {
            col = 0;
            for (String h : huruf) {
                gbc.gridx = col; gbc.gridy = baris - startBaris + 1;
                if (h == null) {
                    pnlSeatMap.add(new JLabel("  "), gbc);
                } else {
                    String kode = baris + h;
                    JButton btn = buildSeatBtn(kode);
                    tombolKursiList.add(btn);
                    pnlSeatMap.add(btn, gbc);
                }
                col++;
            }
        }
        pnlSeatMap.revalidate();
        pnlSeatMap.repaint();
    }

    private JButton buildSeatBtn(String kode) {
        JButton btn = new JButton(kode);
        btn.setFont(new Font("Dialog", Font.PLAIN, 10));
        btn.setPreferredSize(new Dimension(44, 32));
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0, 0, 0, 0));

        if (kursiTerisi.contains(kode)) {
            btn.setBackground(C_SEAT_TAKEN);
            btn.setForeground(C_RED);
            btn.setEnabled(false);
            btn.setToolTipText("Sudah terisi");
        } else {
            btn.setBackground(C_SEAT_FREE);
            btn.setForeground(C_BLUE_DARK);
            btn.addActionListener(e -> onKursiDipilih(kode, btn));
        }
        return btn;
    }

    private void onKursiDipilih(String kode, JButton btn) {
        if (kursiDipilihPerPenumpang.containsValue(kode)) {
            JOptionPane.showMessageDialog(this, "Kursi " + kode + " sudah dipilih penumpang lain!", "Info", JOptionPane.WARNING_MESSAGE);
            return;
        }
        kursiDipilihPerPenumpang.put(penumpangKursiIndex, kode);
        btn.setBackground(C_SEAT_SEL);
        btn.setForeground(Color.WHITE);
        btn.setEnabled(false);

        penumpangKursiIndex++;
        while (penumpangKursiIndex < kategoriList.size()
               && kategoriList.get(penumpangKursiIndex).equals("Bayi")) {
            penumpangKursiIndex++;
        }
        updateInfoKursi();

        long butuhKursi = kategoriList.stream().filter(k -> !k.equals("Bayi")).count();
        if (kursiDipilihPerPenumpang.size() >= butuhKursi) {
            btnKonfirmasiKursi.setEnabled(true);
            lblInfoKursi.setText("✔ Semua kursi sudah dipilih. Klik Lanjut.");
            lblInfoKursi.setForeground(C_GREEN);
        }
    }

    private void updateInfoKursi() {
        long butuhKursi = kategoriList.stream().filter(k -> !k.equals("Bayi")).count();
        if (penumpangKursiIndex < kategoriList.size()) {
            lblInfoKursi.setText("Pilih kursi untuk Penumpang " + (penumpangKursiIndex + 1)
                + " (" + kategoriList.get(penumpangKursiIndex) + ")"
                + "  [" + kursiDipilihPerPenumpang.size() + "/" + butuhKursi + " terpilih]");
            lblInfoKursi.setForeground(C_BLUE);
        }
    }

    // ==============================================================
    //  LOGIKA – LANJUT KE DATA PENUMPANG
    // ==============================================================
    private void doLanjutKePenumpang() {
        // Ambil panel penumpang (index 3 dalam cardLayout)
        JPanel penumpangPanel = (JPanel) mainPanel.getComponent(3);
        JScrollPane sc = (JScrollPane) penumpangPanel.getComponent(1);
        JPanel content = (JPanel) sc.getViewport().getView();
        content.removeAll();

        tfNamaList.clear();
        tfFFList.clear();
        spBagasiList.clear();

        for (int i = 0; i < kategoriList.size(); i++) {
            String kat    = kategoriList.get(i);
            boolean isBayi = kat.equals("Bayi");
            String kursiInfo = isBayi ? "(tanpa kursi)"
                : "Kursi: " + kursiDipilihPerPenumpang.getOrDefault(i, "-");

            // Sub-panel tiap penumpang
            JPanel rowPanel = new JPanel(new GridBagLayout());
            rowPanel.setBackground(C_WHITE);
            rowPanel.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, C_BORDER),
                new EmptyBorder(10, 14, 10, 14)
            ));
            rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

            GridBagConstraints gc = new GridBagConstraints();
            gc.fill = GridBagConstraints.HORIZONTAL;
            gc.insets = new Insets(4, 6, 4, 6);
            gc.anchor = GridBagConstraints.WEST;

            // Judul baris
            gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 4;
            JLabel lblTitle = new JLabel("Penumpang " + (i+1) + " — " + kat + "   " + kursiInfo);
            lblTitle.setFont(FONT_HEADER);
            lblTitle.setForeground(C_BLUE_DARK);
            rowPanel.add(lblTitle, gc);
            gc.gridwidth = 1;

            // Nama
            JTextField tfNama = new JTextField(18);
            tfNama.setFont(FONT_BODY);
            gc.gridy = 1; gc.gridx = 0; gc.weightx = 0;
            rowPanel.add(makeLabel("Nama Penumpang :"), gc);
            gc.gridx = 1; gc.weightx = 0.5;
            rowPanel.add(tfNama, gc);

            // FF
            JTextField tfFF = new JTextField(12);
            tfFF.setFont(FONT_BODY);
            gc.gridx = 2; gc.weightx = 0;
            rowPanel.add(makeLabel("No. Frequent Flyer :"), gc);
            gc.gridx = 3; gc.weightx = 0.3;
            rowPanel.add(tfFF, gc);

            tfNamaList.add(tfNama);
            tfFFList.add(tfFF);

            // Bagasi
            JSpinner spBagasi = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
            spBagasi.setFont(FONT_BODY);
            if (!isBayi) {
                gc.gridy = 2; gc.gridx = 0; gc.weightx = 0;
                rowPanel.add(makeLabel("Berat Bagasi (Kg) :"), gc);
                gc.gridx = 1; gc.weightx = 0.5;
                rowPanel.add(spBagasi, gc);
            }
            spBagasiList.add(spBagasi);

            content.add(rowPanel);
        }

        content.add(Box.createVerticalGlue());
        content.revalidate();
        content.repaint();
        cardLayout.show(mainPanel, "PENUMPANG");
    }

    // ==============================================================
    //  LOGIKA – HITUNG HARGA
    // ==============================================================
    private void doHitungHarga() {
        for (int i = 0; i < tfNamaList.size(); i++) {
            if (tfNamaList.get(i).getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama penumpang " + (i+1) + " belum diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        keranjangTiket.clear();
        daftarFF.clear();
        double grandTotal = 0;
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-28s: %s%n", "Penerbangan", penerbanganPilihan.getIdPenerbangan()));
        sb.append(String.format("%-28s: %s → %s%n", "Rute",
            penerbanganPilihan.getAsal().getKota(), penerbanganPilihan.getTujuan().getKota()));
        sb.append(String.format("%-28s: %s%n", "Kelas", pilihanKelas == 2 ? "Business Class" : "Economy Class"));
        sb.append("\n");
        sb.append(String.format("%-4s %-18s %-8s %-10s %-8s %s%n", "No", "Nama", "Kat", "Kursi", "Bagasi", "Harga"));
        sb.append("-".repeat(70) + "\n");

        for (int i = 0; i < kategoriList.size(); i++) {
            String kat   = kategoriList.get(i);
            String nama  = tfNamaList.get(i).getText().trim();
            String ff    = tfFFList.get(i).getText().trim();
            int bagasi   = (int) spBagasiList.get(i).getValue();
            String kursi = kat.equals("Bayi") ? "TANPA KURSI"
                : kursiDipilihPerPenumpang.getOrDefault(i, "-");

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

            sb.append(String.format("%-4d %-18s %-8s %-10s %-8s %s%n",
                i+1, nama, kat, kursi, bagasi + " kg", formatRp(t.getTotalHarga())));
        }

        sb.append("-".repeat(70) + "\n");
        sb.append(String.format("%-50s %s%n", "GRAND TOTAL:", formatRp(grandTotal)));

        taRingkasan.setText(sb.toString());
        lblGrandTotal.setText("Grand Total: " + formatRp(grandTotal));
        cardLayout.show(mainPanel, "KONFIRMASI");
    }

    // ==============================================================
    //  LOGIKA – SIMPAN TIKET
    // ==============================================================
    private void doSimpanTiket() {
        btnPesan.setEnabled(false);
        btnPesan.setText("Menyimpan...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
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
                    btnPesan.setEnabled(true);
                    btnPesan.setText("Confirm & Pesan");
                    if (sukses) {
                        tampilkanBoardingPass();
                        cardLayout.show(mainPanel, "BOARDING");
                    } else {
                        JOptionPane.showMessageDialog(MainGUI.this,
                            "Gagal menyimpan. Cek koneksi database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    btnPesan.setEnabled(true);
                    JOptionPane.showMessageDialog(MainGUI.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    // ==============================================================
    //  BOARDING PASS
    // ==============================================================
    private void tampilkanBoardingPass() {
        pnlBoardingPasses.removeAll();
        for (Tiket t : keranjangTiket) {
            pnlBoardingPasses.add(buildBoardingPassCard(t));
            pnlBoardingPasses.add(Box.createVerticalStrut(12));
        }
        pnlBoardingPasses.revalidate();
        pnlBoardingPasses.repaint();
    }

    private JPanel buildBoardingPassCard(Tiket t) {
        String rawB = t.getPenerbangan().getWaktuKeberangkatan();
        String rawBd = t.getPenerbangan().getWaktuBoarding();
        String[] bln = {"","JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        String tgl = "N/A", jamB = "N/A", jamBd = "N/A";
        if (rawB != null && rawB.length() >= 16) {
            tgl  = rawB.substring(8, 10) + bln[Integer.parseInt(rawB.substring(5, 7))];
            jamB = rawB.substring(11, 16);
        }
        if (rawBd != null && rawBd.length() >= 16) jamBd = rawBd.substring(11, 16);

        String kelas    = t.getKelasTiket().replace(" CLASS", "");
        String nama     = t.getNamaPenumpang().toUpperCase();
        String asalKota = t.getPenerbangan().getAsal().getKota().toUpperCase();
        String tujKota  = t.getPenerbangan().getTujuan().getKota().toUpperCase();
        String kodeA    = t.getPenerbangan().getAsal().getKodeBandara();
        String kodeT    = t.getPenerbangan().getTujuan().getKodeBandara();
        String flight   = t.getPenerbangan().getIdPenerbangan();
        String gate     = t.getPenerbangan().getGate();
        String seat     = t.getNomorKursi();
        String seq      = String.format("%04d", (int)(Math.random() * 10000));
        String pcswt    = (t.getBeratBagasi() > 0 ? "1 / " : "0 / ") + t.getBeratBagasi() + " kg";

        // Kartu boarding pass
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(180, 180, 180), 1),
            new EmptyBorder(0, 0, 0, 0)
        ));
        card.setMaximumSize(new Dimension(780, 170));

        // Bagian kiri
        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(Color.WHITE);
        left.setBorder(new EmptyBorder(12, 16, 12, 12));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(2, 4, 1, 8);
        Color cDark = new Color(20, 20, 20);
        Color cGray = new Color(110, 110, 110);

        g.gridx = 0; g.gridy = 0; g.gridwidth = 4;
        JLabel lHead = new JLabel("BOARDING PASS   " + t.getKelasTiket());
        lHead.setFont(new Font("Dialog", Font.BOLD, 12));
        lHead.setForeground(new Color(10, 60, 140));
        left.add(lHead, g);

        g.gridy = 1; g.gridwidth = 4;
        JLabel lNama = new JLabel(nama);
        lNama.setFont(new Font("Dialog", Font.BOLD, 15));
        lNama.setForeground(cDark);
        left.add(lNama, g);

        g.gridwidth = 1;
        String[] hdrs = {"FROM", "FLIGHT", "DATE", "TIME"};
        String[] vals = {asalKota, flight, tgl, jamB};
        for (int i = 0; i < 4; i++) {
            g.gridy = 2; g.gridx = i;
            JLabel lh = new JLabel(hdrs[i]); lh.setFont(new Font("Dialog", Font.PLAIN, 9)); lh.setForeground(cGray);
            left.add(lh, g);
            g.gridy = 3;
            JLabel lv = new JLabel(vals[i]); lv.setFont(new Font("Dialog", Font.BOLD, 12)); lv.setForeground(cDark);
            left.add(lv, g);
        }
        String[] hdrs2 = {"TO", "GATE", "BOARDING", "SEAT"};
        String[] vals2 = {tujKota, gate, jamBd, seat};
        for (int i = 0; i < 4; i++) {
            g.gridy = 4; g.gridx = i;
            JLabel lh = new JLabel(hdrs2[i]); lh.setFont(new Font("Dialog", Font.PLAIN, 9)); lh.setForeground(cGray);
            left.add(lh, g);
            g.gridy = 5;
            JLabel lv = new JLabel(vals2[i]); lv.setFont(new Font("Dialog", Font.BOLD, 12)); lv.setForeground(cDark);
            left.add(lv, g);
        }

        // Bagian kanan
        JPanel right = new JPanel(new GridLayout(0, 1, 0, 3));
        right.setBackground(new Color(20, 60, 140));
        right.setBorder(new EmptyBorder(12, 14, 12, 14));
        right.setPreferredSize(new Dimension(190, 0));
        Color cW = Color.WHITE;
        right.add(wLabel(kelas, new Font("Dialog", Font.BOLD, 14), cW));
        right.add(wLabel(nama, new Font("Dialog", Font.BOLD, 11), cW));
        right.add(wLabel(kodeA + " → " + kodeT, new Font("Dialog", Font.PLAIN, 10), cW));
        right.add(wLabel(flight + "  " + tgl + "  " + jamB, new Font("Dialog", Font.PLAIN, 10), cW));
        right.add(wLabel("Seat: " + seat + "   Seq: " + seq, new Font("Dialog", Font.BOLD, 10), cW));
        right.add(wLabel("PCS/WT: " + pcswt, new Font("Dialog", Font.PLAIN, 9), cW));
        right.add(wLabel("PAPER TICKET", new Font("Dialog", Font.ITALIC, 9), cW));

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
        footer.setBackground(new Color(245, 248, 255));
        JLabel warnLbl = new JLabel("⚠  PLEASE BE AT THE GATE 35 MINUTES BEFORE DEPARTURE");
        warnLbl.setFont(new Font("Dialog", Font.BOLD, 10));
        warnLbl.setForeground(new Color(140, 70, 0));
        footer.add(warnLbl);

        card.add(left, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);
        card.add(footer, BorderLayout.SOUTH);

        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
        wrap.setBackground(C_WHITE);
        wrap.add(card);
        return wrap;
    }

    // ==============================================================
    //  DATABASE
    // ==============================================================
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

    // ==============================================================
    //  RESET
    // ==============================================================
    private void resetAll() {
        penerbanganPilihan = null;
        keranjangTiket.clear();
        daftarFF.clear();
        kategoriList.clear();
        kursiDipilihPerPenumpang.clear();
        penumpangKursiIndex = 0;
    }

    // ==============================================================
    //  UI HELPERS
    // ==============================================================
    private JPanel headerBar(String title, String backTarget) {
        JPanel bar = new JPanel(new BorderLayout(8, 0));
        bar.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, C_BORDER),
            new EmptyBorder(0, 0, 8, 0)
        ));
        bar.setOpaque(false);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(FONT_TITLE);
        JButton btnBack = new JButton("← Kembali");
        btnBack.setFont(FONT_SMALL);
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, backTarget));
        bar.add(btnBack, BorderLayout.WEST);
        bar.add(lblTitle, BorderLayout.CENTER);
        return bar;
    }

    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_HEADER);
        l.setForeground(C_BLUE_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BODY);
        return l;
    }

    private JLabel wLabel(String text, Font f, Color c) {
        JLabel l = new JLabel(text);
        l.setFont(f);
        l.setForeground(c);
        return l;
    }

    private JPanel legendBox(Color c, String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        p.setOpaque(false);
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(13, 13));
        box.setBackground(c);
        box.setBorder(new LineBorder(C_BORDER, 1));
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SMALL);
        p.add(box);
        p.add(lbl);
        return p;
    }

    private void addFormRow(JPanel form, GridBagConstraints gc, int row, String labelText, JComponent field) {
        gc.gridy = row; gc.gridx = 0; gc.weightx = 0;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(FONT_BODY);
        form.add(lbl, gc);
        gc.gridx = 1; gc.weightx = 1.0;
        form.add(field, gc);
    }

    private String formatRp(double amount) {
        return "Rp " + NumberFormat.getNumberInstance(new Locale("id", "ID")).format((long) amount);
    }

    // ==============================================================
    //  MAIN
    // ==============================================================
    public static void main(String[] args) {
        // Gunakan Look & Feel sistem (Windows = tema abu default, persis seperti foto)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(MainGUI::new);
    }
}