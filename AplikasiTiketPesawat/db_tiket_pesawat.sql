-- CREATE DATABASE db_tiket_pesawat;
USE db_tiket_pesawat;

-- Tabel bandara
CREATE TABLE bandara (
    kode_bandara VARCHAR(3) PRIMARY KEY,
    nama_bandara VARCHAR(100) NOT NULL,
    kota VARCHAR(50) NOT NULL,
    negara VARCHAR(50) NOT NULL
);

-- Tabel pesawat
CREATE TABLE pesawat (
    id_pesawat INT AUTO_INCREMENT PRIMARY KEY,
    nama_maskapai VARCHAR(50) NOT NULL,
    jenis_pesawat VARCHAR(30) NOT NULL,
    kapasitas INT NOT NULL
);

-- Tabel penerbangan
CREATE TABLE penerbangan (
    id_penerbangan VARCHAR(10) PRIMARY KEY, 
    kode_bandara_asal VARCHAR(3) NOT NULL,
    kode_bandara_tujuan VARCHAR(3) NOT NULL,
    gate VARCHAR(5),                        
    waktu_boarding VARCHAR(20),             
    id_pesawat INT,
    harga_dasar DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (kode_bandara_asal) REFERENCES bandara(kode_bandara) ON DELETE RESTRICT,
    FOREIGN KEY (kode_bandara_tujuan) REFERENCES bandara(kode_bandara) ON DELETE RESTRICT,
    FOREIGN KEY (id_pesawat) REFERENCES pesawat(id_pesawat) ON DELETE SET NULL
);

-- Tabel penumpang
CREATE TABLE penumpang (
    id_penumpang INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    ff_number VARCHAR(50)
);

-- Tabel tiket (Tabel relasi)
CREATE TABLE tiket (
    id_tiket INT AUTO_INCREMENT PRIMARY KEY,
    id_penumpang INT,
    id_penerbangan VARCHAR(10),
    nomor_kursi VARCHAR(5),
    nomor_etkt VARCHAR(50),
    kelas_tiket VARCHAR(20),
    total_harga DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (id_penumpang) REFERENCES penumpang(id_penumpang),
    FOREIGN KEY (id_penerbangan) REFERENCES penerbangan(id_penerbangan)
);

-- dummy data bandara
INSERT INTO bandara (kode_bandara, nama_bandara, kota, negara) VALUES
('SYD', 'Kingsford Smith International Airport', 'Sydney', 'Australia'),
('DPS', 'I Gusti Ngurah Rai International Airport', 'Denpasar-Bali', 'Indonesia'),
('CGK', 'Soekarno-Hatta International Airport', 'Jakarta', 'Indonesia');

-- dummy data pesawat
INSERT INTO pesawat (nama_maskapai, jenis_pesawat, kapasitas) VALUES
('Garuda Indonesia', 'Boeing 737-800', 162),
('Garuda Indonesia', 'Airbus A330-300', 251);

-- dummy data penerbangan
INSERT INTO penerbangan (id_penerbangan, kode_bandara_asal, kode_bandara_tujuan, gate, waktu_boarding, id_pesawat, harga_dasar) VALUES
('GA715', 'SYD', 'DPS', '37', '09:15 05JAN', 1, 7500000.00),
('GA411', 'DPS', 'CGK', '29', '15:05 05JAN', 1, 2000000.00);