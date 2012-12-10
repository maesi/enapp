	CREATE DATABASE IF NOT EXISTS `enappwebshop`;
	USE `enappwebshop`;

	DROP TABLE IF EXISTS `customer`;
	CREATE TABLE `customer` (
	  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	  `username` varchar(15) NOT NULL COMMENT 'have to be unique',
	  `navisionid` varchar(32) DEFAULT NULL COMMENT 'navisionid',
	  `password` varchar(32) DEFAULT NULL COMMENT 'md5 hash',
	  `name` varchar(45) DEFAULT NULL,
	  `address` varchar(45) DEFAULT NULL,
	  `email` varchar(90) DEFAULT NULL,
	  `fk_group` int(10) unsigned DEFAULT 1, 
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

	--
	-- Daten für Tabelle `customer`
	--

	INSERT INTO `customer` (`id`, `username`, `password`, `name`, `address`, `email`, `fk_group`) VALUES
	(1, 'hans', 'f2a0ffe83ec8d44f2be4b624b0f47dde', 'Hans Muster', 'Musterstrasse, 6000 Luzern', 'hans@muster.ch', 1),
	(2, 'urs', '134edc12dcc61911b084b3e06e4ab16d', 'Urs Wyss', 'Luzernerstrasse 10, 3000 Bern', 'urs@bluewin.ch', 2);

	DROP TABLE IF EXISTS `customergroup`;
	CREATE TABLE `customergroup` (
	  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	  `name` varchar(15) NOT NULL COMMENT 'have to be unique',
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB;
	INSERT INTO `customergroup` (`name`) VALUES
	('user'),
	('admin');

	DROP VIEW IF EXISTS `jaas_view`;
	CREATE VIEW `jaas_view` AS
	SELECT  c.`username`, c.`password`, g.`name`
	 FROM `customer` c
	 LEFT JOIN `customergroup` g ON g.`id` =  c.`fk_group`;



	-- --------------------------------------------------------

	--
	-- Tabellenstruktur für Tabelle `purchase`
	--

	DROP TABLE IF EXISTS `purchase`;
	CREATE TABLE `purchase` (
	  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	  `customerid` int(10) unsigned NOT NULL,
	  `datetime` datetime DEFAULT NULL COMMENT 'Date / Time of purchase',
	  `status` varchar(15) DEFAULT NULL COMMENT 'state of purchase',
	  `correlationid` varchar(20) DEFAULT NULL COMMENT 'correlationId',
	  `totalamount` decimal(10,0) DEFAULT NULL COMMENT 'total cost',
	  `paymentid` int(10) unsigned DEFAULT NULL,
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=300 ;

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

	DROP TABLE IF EXISTS `purchaseitem`;
	CREATE TABLE `purchaseitem` (
	  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	  `purchaseid` int(10) unsigned NOT NULL,
	  `productid` varchar(30) NOT NULL,
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