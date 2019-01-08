CREATE DATABASE `springbatch` /*!40100 DEFAULT CHARACTER SET utf8 */;

DROP TABLE IF EXISTS `springbatch`.`user`;
CREATE TABLE  `springbatch`.`user` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(45) NOT NULL default '',
  `salary` decimal(10,2) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

SET @@global.time_zone = '+00:00';

SET @@session.time_zone = '+00:00';
