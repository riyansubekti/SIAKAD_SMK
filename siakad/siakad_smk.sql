-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 01, 2020 at 06:23 AM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `siakad_smk`
--

-- --------------------------------------------------------

--
-- Table structure for table `guru`
--

CREATE TABLE `guru` (
  `id_guru` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `foto` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `guru`
--

INSERT INTO `guru` (`id_guru`, `username`, `nama`, `alamat`, `foto`) VALUES
(2, '2', 'Lika Malikal Mulqi', 'Tangerang', '/siakad/picture/6.jpeg'),
(4, '4', 'Lina Marlina', 'Jakarta Barat', '/siakad/picture/7.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('siswa','guru') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `password`, `role`) VALUES
('160101001', 'reza123', 'siswa'),
('160101003', 'faisal123', 'siswa'),
('2', 'lika123', 'guru'),
('4', 'lina123', 'guru'),
('555', 'oke123', 'siswa');

-- --------------------------------------------------------

--
-- Table structure for table `mapel`
--

CREATE TABLE `mapel` (
  `id_mapel` int(11) NOT NULL,
  `id_guru` int(11) NOT NULL,
  `nama_mapel` varchar(100) NOT NULL,
  `jurusan` varchar(100) NOT NULL,
  `kelas` varchar(100) NOT NULL,
  `waktu_pelajaran` varchar(100) NOT NULL,
  `hari` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mapel`
--

INSERT INTO `mapel` (`id_mapel`, `id_guru`, `nama_mapel`, `jurusan`, `kelas`, `waktu_pelajaran`, `hari`) VALUES
(1, 2, 'Bahasa Indonesia', 'RPL', '1-A', '09:00 - 11:30', 'Senin'),
(2, 2, 'Matematika', 'RPL', '1-A', '12:00 - 13:30', 'Senin'),
(3, 4, 'Matematika', 'Multimedia', '1-B', '09:00 - 11:30', 'Rabu'),
(4, 4, 'Bahasa Indonesia', 'Multimedia', '1-B', '12:00 - 13:30', 'Kamis'),
(5, 2, 'Matematika', 'RPL', '2-A', '08:00 - 09:10', 'Jumat');

-- --------------------------------------------------------

--
-- Table structure for table `nilai`
--

CREATE TABLE `nilai` (
  `id_nilai` int(11) NOT NULL,
  `id_mapel` int(11) NOT NULL,
  `id_siswa` int(11) NOT NULL,
  `tugas` int(3) DEFAULT NULL,
  `uts` int(3) DEFAULT NULL,
  `uas` int(3) DEFAULT NULL,
  `nilai` int(3) NOT NULL,
  `kkm` int(3) NOT NULL,
  `status` enum('Lulus','Tidak Lulus') DEFAULT NULL,
  `semester` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `nilai`
--

INSERT INTO `nilai` (`id_nilai`, `id_mapel`, `id_siswa`, `tugas`, `uts`, `uas`, `nilai`, `kkm`, `status`, `semester`) VALUES
(1, 1, 160101001, 60, 70, 80, 70, 60, 'Lulus', 1),
(2, 2, 160101001, 50, 60, 70, 60, 65, 'Tidak Lulus', 3),
(3, 3, 160101003, 44, 54, 64, 54, 60, 'Tidak Lulus', 2),
(5, 3, 160101003, 43, 33, 53, 43, 40, 'Lulus', 1),
(6, 3, 160101003, 43, 33, 53, 43, 40, 'Lulus', 1),
(7, 3, 160101001, 43, 33, 53, 43, 40, 'Lulus', 1);

-- --------------------------------------------------------

--
-- Table structure for table `pengumuman`
--

CREATE TABLE `pengumuman` (
  `id_pengumuman` int(11) NOT NULL,
  `judul` varchar(100) NOT NULL,
  `deskripsi` text NOT NULL,
  `penerima` enum('siswa','guru','semua') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pengumuman`
--

INSERT INTO `pengumuman` (`id_pengumuman`, `judul`, `deskripsi`, `penerima`) VALUES
(1, 'Pengumuman UAS Online', 'Dikarenakan adanya covid-19 UAS akan diadakan secara online menggunakan Google Classroom', 'siswa'),
(2, 'Pengumuman Untuk GURU', 'Dikarenakan adanya covid-19 semua guru diwajibkan menggunakan masker', 'guru');

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE `siswa` (
  `id_siswa` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kelas` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `jurusan` varchar(100) NOT NULL,
  `tagihan` varchar(100) NOT NULL,
  `foto` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `siswa`
--

INSERT INTO `siswa` (`id_siswa`, `username`, `nama`, `kelas`, `alamat`, `jurusan`, `tagihan`, `foto`) VALUES
(555, '555', 'Oke', '2-A', 'Few', 'Ojjd', '600', '/siakad/picture/555.jpeg'),
(160101001, '160101001', 'Reza Pahlevi', '1-A', 'Menteng', 'RPL', '0', '/siakad/picture/1.jpeg'),
(160101003, '160101003', 'Faisal Rahman', '1-B', 'Jakarta', 'Multimedia', '100000', '/siakad/picture/2.jpeg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `guru`
--
ALTER TABLE `guru`
  ADD PRIMARY KEY (`id_guru`),
  ADD KEY `username` (`username`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `mapel`
--
ALTER TABLE `mapel`
  ADD PRIMARY KEY (`id_mapel`),
  ADD KEY `id_guru` (`id_guru`);

--
-- Indexes for table `nilai`
--
ALTER TABLE `nilai`
  ADD PRIMARY KEY (`id_nilai`),
  ADD KEY `id_mapel` (`id_mapel`),
  ADD KEY `id_siswa` (`id_siswa`);

--
-- Indexes for table `pengumuman`
--
ALTER TABLE `pengumuman`
  ADD PRIMARY KEY (`id_pengumuman`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
  ADD PRIMARY KEY (`id_siswa`),
  ADD KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `guru`
--
ALTER TABLE `guru`
  MODIFY `id_guru` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `mapel`
--
ALTER TABLE `mapel`
  MODIFY `id_mapel` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `nilai`
--
ALTER TABLE `nilai`
  MODIFY `id_nilai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `siswa`
--
ALTER TABLE `siswa`
  MODIFY `id_siswa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=160101004;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `guru`
--
ALTER TABLE `guru`
  ADD CONSTRAINT `guru_ibfk_1` FOREIGN KEY (`username`) REFERENCES `login` (`username`);

--
-- Constraints for table `mapel`
--
ALTER TABLE `mapel`
  ADD CONSTRAINT `mapel_ibfk_1` FOREIGN KEY (`id_guru`) REFERENCES `guru` (`id_guru`);

--
-- Constraints for table `nilai`
--
ALTER TABLE `nilai`
  ADD CONSTRAINT `nilai_ibfk_1` FOREIGN KEY (`id_mapel`) REFERENCES `mapel` (`id_mapel`),
  ADD CONSTRAINT `nilai_ibfk_2` FOREIGN KEY (`id_siswa`) REFERENCES `siswa` (`id_siswa`);

--
-- Constraints for table `siswa`
--
ALTER TABLE `siswa`
  ADD CONSTRAINT `siswa_ibfk_1` FOREIGN KEY (`username`) REFERENCES `login` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
