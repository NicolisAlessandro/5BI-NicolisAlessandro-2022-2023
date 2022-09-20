-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Mag 18, 2022 alle 19:17
-- Versione del server: 8.0.27
-- Versione PHP: 7.3.31-1~deb10u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ardos`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `centraline`
--

CREATE TABLE `centraline` (
  `stazioni_nome` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `stazioni_localita` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `stazioni_comune` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `stazioni_provincia` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `stazioni_tipozona` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `stazioni_lat` decimal(11,9) DEFAULT NULL,
  `stazioni_lon` decimal(11,9) DEFAULT NULL,
  `stazioni_codseqst` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `centraline`
--

INSERT INTO `centraline` (`stazioni_nome`, `stazioni_localita`, `stazioni_comune`, `stazioni_provincia`, `stazioni_tipozona`, `stazioni_lat`, `stazioni_lon`, `stazioni_codseqst`) VALUES
('Conegliano', 'Conegliano', 'Conegliano', 'Treviso', 'BU', '45.889647380', '12.307062470', 500000016),
('Mansué', 'Mansue', 'Mansue\'', 'Treviso', 'BRU', '45.836901460', '12.510213070', 500000020),
('TV Via Lancieri', 'Treviso-via Lancieri N.', 'Treviso', 'Treviso', 'BU', '45.671799460', '12.237713410', 500000021),
('Belluno Città', 'Belluno Città', 'Belluno', 'Belluno', 'BU', '46.141927440', '12.217602690', 500000068),
('Area Feltrina', 'Feltre Via Colombo', 'Feltre', 'Belluno', 'BU', '46.031278530', '11.906616540', 500000069),
('Rovigo Borsea', 'Borsea', 'Rovigo', 'Rovigo', 'BU', '45.038811010', '11.790152260', 500000073),
('Adria', 'Adria', 'Rovigo', 'Rovigo', 'BU', NULL, NULL, 500000077),
('Rovigo Centro', 'Rovigo Centro', 'Rovigo', 'Rovigo', 'TU', '45.073835320', '11.782469280', 500000079),
('Badia Polesine-Villafora', 'M M 11 ROVIGO', 'Badia Polesine', 'Rovigo', 'BRU', '45.104017820', '11.552652070', 500000082),
('Quartiere Italia', 'VI - Quartiere Italia', 'Vicenza', 'Vicenza', 'BU', '45.559641010', '11.538744550', 500000099),
('Montebello', 'Montebello Nord', 'Montebello Vicentino', 'Vicenza', 'IS', '45.465477840', '11.386252740', 500000100),
('Schio', 'Schio', 'Schio', 'Vicenza', 'BU', '45.713533440', '11.367627440', 500000103),
('Bassano', 'Bassano del Grappa', 'Bassano Del Grappa', 'Vicenza', 'BU', '45.759431770', '11.735870760', 500000106),
('Legnago', 'Legnago', 'Legnago', 'Verona', 'BU', '45.182576220', '11.310522700', 500000116),
('San Bonifacio', 'S. Bonifacio', 'San Bonifacio', 'Verona', 'TU', '45.398866040', '11.285255590', 500000120),
('Verona - C.so Milano', 'Verona - C.so Milano', 'Verona', 'Verona', 'TU', '45.444403580', '10.963008280', 500000134),
('Sacca Fisola', 'VE S. Fisola', 'Venezia', 'Venezia', 'BU', '45.428375030', '12.312905240', 500000141),
('San Donà  di Piave', 'S.Dona Piave', 'San Dona\' Di Piave', 'Venezia', 'BU', '45.629084630', '12.590430820', 500000149),
('VE Parco Bissuola', 'Parco Bissuola', 'Venezia', 'Venezia', 'BU', '45.498192510', '12.261221650', 500000156),
('PD Mandria', 'PD Mandria', 'Padova', 'Padova', 'BU', '45.370939120', '11.840905010', 500000197),
('PD Granze', 'PD Granze', 'Padova', 'Padova', 'IS', '45.377546060', '11.940040140', 500000200),
('Pieve di Alpago', 'Pieve di Alpago', '(pieve D\'alpago Fino Al 22/02/2016)', 'Belluno', 'BS', '46.162507890', '12.361106520', 500015004),
('Chiampo', 'Chiampo, Via dei Laghi', 'Chiampo', 'Vicenza', 'IU', '45.536458660', '11.293695840', 500015029),
('Cima Ekar', 'Asiago', 'Asiago', 'Vicenza', 'BRU', '45.848696720', '11.569050520', 500015304),
('San Felice', 'VI - San Felice', 'Vicenza', 'Vicenza', 'TU', '45.545019350', '11.533256380', 500015645),
('Boscochiesanuova', 'Boscochiesanuova', 'Verona', 'Verona', 'BRU', '45.589218910', '11.036950430', 500015821),
('VE Via Tagliamento', 'ME V. Tagliamento', 'Venezia', 'Venezia', 'TU', '45.489585270', '12.217545330', 500021732),
('VE Via Beccaria', 'VE - Via Beccaria', 'Venezia', 'Venezia', 'TU', '45.474599100', '12.219749280', 500021765),
('PD Arcella', 'Arcella Guido Reni', 'Padova', 'Padova', 'TU', '45.432857110', '11.889717460', 500021785),
('Este', 'Este Stazie Bragadine', 'Este', 'Padova', 'IS', '45.227000030', '11.666226700', 500021960),
('Parco Colli Euganei', 'Cinto Euganeo', 'Cinto Euganeo', 'Padova', 'BRU', '45.289358920', '11.642357830', 500021975),
('Alta Padovana', 'Carrello - S.Giustina in Colle', 'Santa Giustina In Colle', 'Padova', 'BRU', '45.601528560', '11.903515820', 500022612),
('Ferrovieri', 'VI - Ferrovieri', 'Vicenza', 'Vicenza', 'BU', '45.532213000', '11.522191860', 500022715),
('VE Malcontenta', 'VE - Malcontenta', 'Venezia', 'Venezia', 'IS', '45.438274020', '12.205546640', 500023627),
('VR-Giarol Grande', 'VR-Giarol Grande', 'Verona', 'Verona', 'BS', NULL, NULL, 500031115),
('VE - Rio Novo', 'VE - Rio Novo', 'Venezia', 'Venezia', 'TU', NULL, NULL, 500032515);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `centraline`
--
ALTER TABLE `centraline`
  ADD PRIMARY KEY (`stazioni_codseqst`),
  ADD KEY `stazioni_codseqst` (`stazioni_codseqst`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
