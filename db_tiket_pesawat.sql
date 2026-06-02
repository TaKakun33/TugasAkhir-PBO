--  Inisialisasi Database
DROP DATABASE IF EXISTS db_tiket_pesawat;
CREATE DATABASE db_tiket_pesawat;
USE db_tiket_pesawat;

-- Membuat Tabel Bandara
CREATE TABLE bandara (
    kode_bandara VARCHAR(3) PRIMARY KEY,
    nama_bandara VARCHAR(100) NOT NULL,
    kota VARCHAR(50) NOT NULL,
    negara VARCHAR(50) NOT NULL
);

-- Membuat Tabel Pesawat
CREATE TABLE pesawat (
    id_pesawat INT AUTO_INCREMENT PRIMARY KEY,
    nama_maskapai VARCHAR(50) NOT NULL,
    jenis_pesawat VARCHAR(30) NOT NULL,
    kapasitas INT NOT NULL
);

-- Membuat Tabel Penerbangan
CREATE TABLE penerbangan (
    id_penerbangan VARCHAR(10) PRIMARY KEY, 
    kode_bandara_asal VARCHAR(3) NOT NULL,
    kode_bandara_tujuan VARCHAR(3) NOT NULL,
    gate VARCHAR(5),                        
    waktu_keberangkatan DATETIME NOT NULL,  
    waktu_boarding DATETIME NOT NULL,       
    id_pesawat INT,
    harga_dasar DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (kode_bandara_asal) REFERENCES bandara(kode_bandara) ON DELETE RESTRICT,
    FOREIGN KEY (kode_bandara_tujuan) REFERENCES bandara(kode_bandara) ON DELETE RESTRICT,
    FOREIGN KEY (id_pesawat) REFERENCES pesawat(id_pesawat) ON DELETE SET NULL
);

-- Membuat Tabel Penumpang
CREATE TABLE penumpang (
    id_penumpang INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    ff_number VARCHAR(50)
);

-- Membuat Tabel Tiket (Relasi)
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

-- Insert 10 Data Bandara (Lengkap dengan Kota)
INSERT INTO bandara (kode_bandara, nama_bandara, kota, negara) VALUES
('SYD', 'Kingsford Smith International Airport', 'Sydney', 'Australia'),
('DPS', 'I Gusti Ngurah Rai International Airport', 'Denpasar', 'Indonesia'),
('CGK', 'Soekarno-Hatta International Airport', 'Jakarta', 'Indonesia'),
('SUB', 'Juanda International Airport', 'Surabaya', 'Indonesia'),
('KNO', 'Kualanamu International Airport', 'Medan', 'Indonesia'),
('YIA', 'Yogyakarta International Airport', 'Yogyakarta', 'Indonesia'),
('BPN', 'Sepinggan International Airport', 'Balikpapan', 'Indonesia'),
('UPG', 'Sultan Hasanuddin International Airport', 'Makassar', 'Indonesia'),
('SIN', 'Changi Airport', 'Singapore', 'Singapore'),
('KUL', 'Kuala Lumpur International Airport', 'Kuala Lumpur', 'Malaysia');

-- Insert Data Pesawat
INSERT INTO pesawat (nama_maskapai, jenis_pesawat, kapasitas) VALUES
('Garuda Indonesia', 'Boeing 737-800', 162),       -- ID 1
('Garuda Indonesia', 'Airbus A330-300', 251),      -- ID 2
('Citilink', 'Airbus A320', 180),                  -- ID 3
('AirAsia', 'Airbus A320', 180),                   -- ID 4
('Singapore Airlines', 'Boeing 777-300ER', 264);   -- ID 5

-- Insert Data Penerbangan (Langsung & Bulak-Balik)
INSERT INTO penerbangan (id_penerbangan, kode_bandara_asal, kode_bandara_tujuan, gate, waktu_keberangkatan, waktu_boarding, id_pesawat, harga_dasar) VALUES

-- Original Data (Sydney -> Denpasar -> Jakarta)
('GA715', 'SYD', 'DPS', '37', '2026-06-22 10:15:00', DATE_SUB('2026-06-22 10:15:00', INTERVAL 1 HOUR), 1, 7500000.00),
('GA411', 'DPS', 'CGK', '29', '2026-06-22 16:05:00', DATE_SUB('2026-06-22 16:05:00', INTERVAL 1 HOUR), 1, 2000000.00),

-- Rute 1: Jakarta (CGK) <-> Surabaya (SUB) [3x Bulak Balik]
('GA101', 'CGK', 'SUB', '1A', '2026-06-25 08:00:00', DATE_SUB('2026-06-25 08:00:00', INTERVAL 1 HOUR), 1, 1200000.00),
('QG201', 'CGK', 'SUB', '2B', '2026-06-25 13:30:00', DATE_SUB('2026-06-25 13:30:00', INTERVAL 1 HOUR), 3, 950000.00),
('GA103', 'CGK', 'SUB', '1C', '2026-06-25 19:00:00', DATE_SUB('2026-06-25 19:00:00', INTERVAL 1 HOUR), 2, 1250000.00),
('GA102', 'SUB', 'CGK', '4',  '2026-06-26 09:00:00', DATE_SUB('2026-06-26 09:00:00', INTERVAL 1 HOUR), 1, 1200000.00),
('QG202', 'SUB', 'CGK', '5',  '2026-06-26 14:30:00', DATE_SUB('2026-06-26 14:30:00', INTERVAL 1 HOUR), 3, 950000.00),
('GA104', 'SUB', 'CGK', '6',  '2026-06-26 20:00:00', DATE_SUB('2026-06-26 20:00:00', INTERVAL 1 HOUR), 2, 1250000.00),

-- Rute 2: Denpasar (DPS) <-> Singapore (SIN) [2x Bulak Balik]
('SQ901', 'DPS', 'SIN', '12', '2026-06-25 10:00:00', DATE_SUB('2026-06-25 10:00:00', INTERVAL 1 HOUR), 5, 3500000.00),
('QZ501', 'DPS', 'SIN', '14', '2026-06-25 16:00:00', DATE_SUB('2026-06-25 16:00:00', INTERVAL 1 HOUR), 4, 1800000.00),
('SQ902', 'SIN', 'DPS', 'F5', '2026-06-28 11:30:00', DATE_SUB('2026-06-28 11:30:00', INTERVAL 1 HOUR), 5, 3500000.00),
('QZ502', 'SIN', 'DPS', 'G2', '2026-06-28 17:45:00', DATE_SUB('2026-06-28 17:45:00', INTERVAL 1 HOUR), 4, 1850000.00),

-- Rute 3: Jakarta (CGK) <-> Medan (KNO) [2x Bulak Balik]
('GA201', 'CGK', 'KNO', '3A', '2026-06-25 07:15:00', DATE_SUB('2026-06-25 07:15:00', INTERVAL 1 HOUR), 1, 1600000.00),
('QG301', 'CGK', 'KNO', '3B', '2026-06-25 14:00:00', DATE_SUB('2026-06-25 14:00:00', INTERVAL 1 HOUR), 3, 1300000.00),
('GA202', 'KNO', 'CGK', '1',  '2026-06-27 10:30:00', DATE_SUB('2026-06-27 10:30:00', INTERVAL 1 HOUR), 1, 1600000.00),
('QG302', 'KNO', 'CGK', '2',  '2026-06-27 17:15:00', DATE_SUB('2026-06-27 17:15:00', INTERVAL 1 HOUR), 3, 1300000.00);

-- Insert Data Penumpang
INSERT INTO penumpang (nama, ff_number) VALUES
('Budi Santoso', 'GA-12345'),
('Siti Aminah', 'QG-98765'),
('Andi Pratama', NULL),
('Rina Wijaya', 'SQ-55432'),
('Joko Susilo', NULL);

-- Insert Data Tiket (Tiap penerbangan di atas PASTI memiliki minimal 1 tiket terjual)
INSERT INTO tiket (id_penumpang, id_penerbangan, nomor_kursi, nomor_etkt, kelas_tiket, total_harga) VALUES
(1, 'GA715', '12A', 'ETK-0001', 'Economy', 7500000.00),
(2, 'GA411', '14C', 'ETK-0002', 'Economy', 2000000.00),
(3, 'GA101', '05B', 'ETK-0003', 'Economy', 1200000.00),
(4, 'QG201', '10A', 'ETK-0004', 'Economy', 950000.00),
(5, 'GA103', '02F', 'ETK-0005', 'Business', 3500000.00),
(1, 'GA102', '11C', 'ETK-0006', 'Economy', 1200000.00),
(2, 'QG202', '15D', 'ETK-0007', 'Economy', 950000.00),
(3, 'GA104', '08A', 'ETK-0008', 'Economy', 1250000.00),
(4, 'SQ901', '22K', 'ETK-0009', 'Economy', 3500000.00),
(5, 'QZ501', '18B', 'ETK-0010', 'Economy', 1800000.00),
(1, 'SQ902', '21J', 'ETK-0011', 'Economy', 3500000.00),
(2, 'QZ502', '09C', 'ETK-0012', 'Economy', 1850000.00),
(3, 'GA201', '04A', 'ETK-0013', 'Business', 4200000.00),
(4, 'QG301', '12F', 'ETK-0014', 'Economy', 1300000.00),
(5, 'GA202', '06C', 'ETK-0015', 'Economy', 1600000.00),
(1, 'QG302', '14A', 'ETK-0016', 'Economy', 1300000.00);