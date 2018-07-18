-- Creazione utente
--

CREATE USER IF NOT EXISTS 'esame'@'localhost' IDENTIFIED WITH mysql_native_password AS 'segreta';

--
-- Database: `trasporto_merci`
--

DROP DATABASE IF EXISTS `spedizione_articoli` ;
CREATE DATABASE `spedizione_articoli`;
USE `spedizione_articoli`;

--
-- Attribuzione privilegi Utente su DB
--

GRANT ALL PRIVILEGES ON *.* TO 'esame'@'localhost' REQUIRE NONE WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;

-- --------------------------------------------------------

--
-- Struttura della tabella `articolo`
--

CREATE TABLE IF NOT EXISTS`articolo` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `codice` varchar(255) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  `peso` float(10,2) NOT NULL
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- Struttura della tabella `ordine`
--

CREATE TABLE IF NOT EXISTS`ordine` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `numero` varchar(255) NOT NULL,
  `data` date NOT NULL
) ENGINE=InnoDB;

-- --------------------------------------------------------


--
-- Struttura della tabella `tariffa_corriere`
--

CREATE TABLE IF NOT EXISTS`tariffa_corriere` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nome_corriere` varchar(255) NOT NULL,
  `nome_tariffa` varchar(255) NOT NULL,
  `peso_massimo` float(10,2) NOT NULL,
  `costo` float(10,2) NOT NULL
) ENGINE=InnoDB;

-- --------------------------------------------------------

--
-- Struttura della tabella `voce`
--

CREATE TABLE IF NOT EXISTS `voce` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `id_ordine` int(11) NOT NULL,
  `id_articolo` int(11) NOT NULL,
  `quantita` int(11) NOT NULL
) ENGINE=InnoDB;

--
-- Limiti per la tabella `merce_spedizione`
--
ALTER TABLE `voce`
  ADD FOREIGN KEY (`id_ordine`) REFERENCES `ordine` (`id`),
  ADD FOREIGN KEY (`id_articolo`) REFERENCES `articolo` (`id`);

-- --------------------------------------------------------


INSERT INTO `articolo` (`codice`, `descrizione`, `peso`) VALUES
('PNDRV8', 'Pen drive USB 8G no brand', '0.15'),
('DCP-J715E', 'Stampante Brother DCP J715 W', '5.3'),
('LNVCX1', 'Notebook Lenovo Carbon X1', '1.9'),
('HUP20', 'Smartphone Alcatel POP C3', '0.53'),
('BSHT1R', 'Ampli Combo valvolare BlackStar HT 1-R', '6');


INSERT INTO `tariffa_corriere` (`nome_corriere`, `nome_tariffa`, `peso_massimo`, `costo`) VALUES
('SDA','Economy','1','5.90'),
('SDA', 'Veloce', '5', '7.90'),
('SDA', 'Bigbox', '30', '12.90'),
('DHL', 'Veloce', '1.50', '6.50'),
('DHL', 'Assicurata', '7.5', '9.90'),
('DHL', 'XXL', '40', '13.90'),
('UPS', 'Economy', '0.7', '5.20'),
('UPS', 'Standard', '3', '6.90'),
('UPS', 'Jumbo', '25', '11.50');

INSERT INTO `ordine` (`numero`, `data`) VALUES 
('3bg4564', '2018-07-17'),
('df0653', '2018-07-30'),
('76kl54', '2018-06-12'),
('1221aqw', '2018-05-22'),
('234rf', '2018-02-14'),
('d3bn467', '2018-03-01'),
('12ccv21', '2018-05-22'),
('56cc21', '2018-01-07'),
('1ergt888', '2018-04-16');


INSERT INTO `voce` (`id_ordine`, `id_articolo`, `quantita`) VALUES
(1,3,2),
(2,2,1),
(2,3,1),
(3,3,1),
(3,4,1),
(4,1,2),
(4,4,1),
(5,4,2),
(5,1,1),
(6,1,3),
(7,2,7),
(8,5,1),
(8,3,1),
(9,5,6);

/*
*/