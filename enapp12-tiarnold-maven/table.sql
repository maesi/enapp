CREATE DATABASE `enappwebshop`;

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(15) NOT NULL COMMENT 'have to be unique',
  `password` varchar(32) DEFAULT NULL COMMENT 'md5 hash',
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `email` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `customer`
--

INSERT INTO `customer` (`id`, `username`, `password`, `name`, `address`, `email`) VALUES
(1, 'hans', 'f2a0ffe83ec8d44f2be4b624b0f47dde', 'Hans Muster', 'Musterstrasse, 6000 Luzern', 'hans@muster.ch'),
(2, 'urs', '134edc12dcc61911b084b3e06e4ab16d', 'Urs Wyss', 'Luzernerstrasse 10, 3000 Bern', 'urs@bluewin.ch');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT 'contain surname and last name',
  `description` varchar(45) DEFAULT NULL,
  `mediapath` varchar(180) DEFAULT NULL COMMENT 'relative path to the media file',
  `unitprice` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Daten für Tabelle `product`
--

INSERT INTO `product` (`id`, `name`, `description`, `mediapath`, `unitprice`) VALUES
(1, 'Maven 3', 'Konfigurationsmanagement mit Java', '978-3-8266-9118-8', 30),
(2, 'Clean Code', 'Refacotring, Patterns, Testen und Technicken ', '978-3-8266-5548-7', 40),
(3, 'Software-Sanierung', 'Weiterntwicklung, Testen und Refacotring best', '978-3-8266-5072-7', 45),
(4, 'Enterprise JavaBeans 3.1', 'Einstieg, Umstieg, Praxis und Referenz', '978-3-8266-9066-2', 49);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `purchase`
--

CREATE TABLE IF NOT EXISTS `purchase` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customerid` int(10) unsigned NOT NULL,
  `datetime` datetime DEFAULT NULL COMMENT 'Date / Time of purchase',
  `status` varchar(15) DEFAULT NULL COMMENT 'state of purchase',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Daten für Tabelle `purchase`
--

INSERT INTO `purchase` (`id`, `customerid`, `datetime`, `status`) VALUES
(1, 1, '2012-10-01 10:20:30', 'shipped'),
(2, 1, '2012-10-09 16:34:08', 'closed');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `purchaseitem`
--

CREATE TABLE IF NOT EXISTS `purchaseitem` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `purchaseid` int(10) unsigned NOT NULL,
  `productid` int(10) unsigned NOT NULL,
  `quantity` decimal(10,0) DEFAULT NULL COMMENT 'by a mp3 shop, generally one',
  `unitprice` decimal(10,0) DEFAULT NULL,
  `lineamount` decimal(10,0) DEFAULT NULL COMMENT 'total cost per line',
  `description` varchar(90) DEFAULT NULL COMMENT 'line description f.e. a comment',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Daten für Tabelle `purchaseitem`
--

INSERT INTO `purchaseitem` (`id`, `purchaseid`, `productid`, `quantity`, `unitprice`, `lineamount`, `description`) VALUES
(1, 1, 2, 1, 40, 40, NULL),
(2, 1, 4, 2, 49, 98, NULL),
(3, 2, 1, 5, 30, 150, NULL);