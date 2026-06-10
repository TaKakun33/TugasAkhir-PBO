# ✈️ Sistem Pemesanan Tiket Pesawat

Aplikasi desktop pemesanan tiket pesawat berbasis **Java Swing (GUI)** dengan koneksi ke database **MySQL**. Dibangun menggunakan arsitektur **MVC (Model-View-Controller)** dan mendukung alur pemesanan tiket lengkap dari pencarian jadwal hingga cetak boarding pass.

---

## 📸 Screenshots
 
### 1. Cari Penerbangan
<img width="743" height="538" alt="Screenshot_20260610_100713" src="https://github.com/user-attachments/assets/decef042-59e9-4182-865f-cd186d1bc040" />

### 2. Pilih Jadwal & Penumpang
<img width="743" height="538" alt="Screenshot_20260610_100743" src="https://github.com/user-attachments/assets/c4e87d9e-9ae5-467d-920b-a9da01b412be" />

### 3. Seat Map — Pilih Kursi
<img width="743" height="538" alt="Screenshot_20260610_100820" src="https://github.com/user-attachments/assets/3d778d1e-15d9-4295-93b8-eeddd725bfd2" />
 
### 4. Data Penumpang
<img width="743" height="538" alt="Screenshot_20260610_100912" src="https://github.com/user-attachments/assets/2c059cca-6343-4f8e-a3c8-cc66d4b0e935" />
 
### 5. Konfirmasi & Harga
<img width="743" height="538" alt="Screenshot_20260610_100928" src="https://github.com/user-attachments/assets/1a301412-0f02-4d7c-a5df-75a7042dc622" />

### 6. Boarding Pass
<img width="743" height="538" alt="Screenshot_20260610_101004" src="https://github.com/user-attachments/assets/d6a897dd-0a78-4ba0-b324-5326ef101c28" />
 
---

## 📋 Fitur Utama

- 🔍 **Cari Penerbangan** — Cari jadwal berdasarkan kota asal, tujuan, dan tanggal (data dinamis dari database)
- 💺 **Seat Map Interaktif** — Pilih kursi secara visual; kursi terisi dari database ditampilkan real-time
- 👥 **Multi Penumpang** — Dukung Dewasa, Anak, dan Bayi dalam satu transaksi
- 🎫 **Dua Kelas Tiket** — Economy Class dan Business Class dengan perhitungan harga otomatis
- 🧾 **Boarding Pass** — Tampilan boarding pass digital setelah pemesanan berhasil
- 💾 **Simpan ke Database** — Setiap transaksi disimpan ke MySQL dengan mekanisme rollback jika gagal

---

## 🏗️ Struktur Proyek

```
src/
├── model/
│   ├── Tiket.java            # Abstract class tiket
│   ├── TiketEkonomi.java     # Implementasi Economy Class
│   ├── TiketBisnis.java      # Implementasi Business Class
│   ├── Penerbangan.java      # Data penerbangan
│   ├── Bandara.java          # Data bandara
│   └── Pesawat.java          # Data pesawat & maskapai
├── dao/
│   ├── DatabaseConnection.java  # Koneksi MySQL (Singleton)
│   ├── PenerbanganDAO.java      # Query data penerbangan
│   └── TiketDAO.java            # Query simpan tiket & kursi terisi
├── controller/
│   └── TiketController.java     # Jembatan View ↔ DAO
└── view/
    ├── MainGUI.java             # GUI utama (JFrame NetBeans Form)
    └── MainCLI.java             # Versi CLI (terminal)
```

---

## 🗄️ Skema Database

```
db_tiket_pesawat
│
├── bandara        (kode_bandara PK, nama_bandara, kota, negara)
├── pesawat        (id_pesawat PK, nama_maskapai, jenis_pesawat, kapasitas)
├── penerbangan    (id_penerbangan PK, asal FK, tujuan FK, gate, waktu_keberangkatan,
│                   waktu_boarding, id_pesawat FK, harga_dasar)
├── penumpang      (id_penumpang PK, nama, ff_number)
└── tiket          (id_tiket PK, id_penumpang FK, id_penerbangan FK,
                    nomor_kursi, nomor_etkt, kelas_tiket, total_harga)
```

---

## 💰 Logika Harga

| Kelas | Penumpang | Harga |
|---|---|---|
| Economy | Dewasa | Harga Dasar |
| Economy | Anak | Harga Dasar × 88% |
| Economy | Bayi | Harga Dasar × 10% |
| Business | Semua | Harga Dasar × 150% |

**Bagasi tambahan** (per kg diatas batas gratis):

| Kelas | Batas Gratis | Biaya Tambahan |
|---|---|---|
| Economy | 20 kg (Bayi: 5 kg) | Rp 50.000/kg |
| Business | 30 kg (Bayi: 10 kg) | Rp 50.000/kg |
