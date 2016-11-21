/*
SQLyog 浼佷笟鐗�- MySQL GUI v8.14 
MySQL - 5.0.96-community-nt : Database - db_stockmanage
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_stockmanage` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_stockmanage`;

/*Table structure for table `t_export` */

DROP TABLE IF EXISTS `t_export`;

CREATE TABLE `t_export` (
  `id` int(11) NOT NULL auto_increment,
  `driver` varchar(20) default NULL,
  `carNum` varchar(20) default NULL,
  `carDes` varchar(20) default NULL,
  `isCheckOwner` boolean,
  `phoneNum` varchar(20) NOT NULL,
  `shipper` varchar(20) default NULL,
  `isPay` boolean,
  `payDate` varchar(20) default NULL,
  `isPayBackFee` boolean,
  `payBackFeeDate` varchar(20) default NULL,
  
  `expoDate` varchar(20) default NULL,
  `payDesc` varchar(20) default NULL,
  `contactNum` varchar(20) default NULL,
  `contactDesc` varchar(100) default NULL,
  `expoDesc` varchar(200) default NULL,
  
  `fromPlace` varchar(20) default NULL,
  `toPlace` varchar(20) default NULL,
  
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `t_export` */

insert  into `t_export`(`id`,`goodsId`,`expoPrice`,`expoDate`,`expoNum`,`expoDesc`) values (2,2,'1001','2014-04-01 12:12:12','100','java01'),(3,3,'1002','2014-04-02 12:12:12','200','java02'),(4,4,'1003','2014-04-03 12:12:12','300','java03'),(5,5,'1004','2014-04-04 12:12:12','400','java04'),(6,6,'1005','2014-04-05 12:12:12','500','java05'),(7,7,'1006','2014-04-06 12:12:12','600','java06'),(8,8,'1007','2014-04-07 12:12:12','120','java07'),(9,9,'1008','2014-04-08 12:12:12','130','java08'),(10,12,'10091','2014-04-10 00:00:00','10091','java091');

/*Table structure for table `t_goods` */

DROP TABLE IF EXISTS `t_exportItem`;

CREATE TABLE `t_exportItem` (
  `id` int(11) NOT NULL auto_increment,
  `exportid` int(11) NOT NULL,/*出库记录id,一个出库记录可能有多个出库item*/
  `goodsid` int(11) NOT NULL,/*货物id*/
  `goodsnum` int(11) default 0,/*货物数量*/
  `exportwarehouse` varchar(20) default NULL,/*出库仓库*/
  `providerid` int(11) NOT NULL,/*出库目标客户id*/
  PRIMARY KEY  (`id`),
  KEY `FK_t_exportId` (`exportid`),
  CONSTRAINT `FK_t_exportId` FOREIGN KEY (`exportid`) REFERENCES `t_export` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*Table structure for table `t_goods` */

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL auto_increment,
  `goodsId` int(20) default NULL,
  `goodsName` varchar(20) default NULL,
  `proId` int(20) default NULL,
  `typeId` int(20) default NULL,
  `goodsDesc` varchar(1000) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_t_goods` (`typeId`),
  KEY `FK_t1_goods` (`proId`),
  CONSTRAINT `FK_t1_goods` FOREIGN KEY (`proId`) REFERENCES `t_provider` (`id`),
  CONSTRAINT `FK_t_goods` FOREIGN KEY (`typeId`) REFERENCES `t_goodstype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `t_goods` */
insert  into `t_goods`(`id`,`goodsId`,`goodsName`,`proId`,`typeId`,`goodsDesc`) values (1,22,'java01',22,1,'java01'),(2,23,'java02',23,2,'java02'),(3,24,'java03',24,3,'java03');
/*Table structure for table `t_goodstype` */

DROP TABLE IF EXISTS `t_goodstype`;

CREATE TABLE `t_goodstype` (
  `id` int(11) NOT NULL auto_increment,
  `typeName` varchar(20) default NULL,
  `typeDesc` varchar(1000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `t_goodstype` */

insert  into `t_goodstype`(`id`,`typeName`,`typeDesc`) values (1,'璁＄畻鏈轰功绫�','鍏充簬璁＄畻鏈�'),(2,'璁＄畻鏈轰功绫�','鍏充簬璁＄畻鏈�2'),(3,'璁＄畻鏈轰功绫�','鍏充簬璁＄畻鏈�'),(4,'璁＄畻鏈轰功绫�','鍏充簬璁＄畻鏈�'),(5,'璁＄畻鏈轰功绫�','鍏充簬璁＄畻鏈�'),(7,'璁＄畻鏈轰功绫�','鍏充簬璁＄畻鏈�'),(8,'璁＄畻鏈轰功绫�','鍏充簬璁＄畻鏈�'),(9,'璁＄畻鏈轰功绫�','鍏充簬璁＄畻鏈�'),(10,'璁＄畻鏈轰功绫�','鍏充簬璁＄畻鏈�'),(11,'璁＄畻鏈轰功绫�0','鍏充簬璁＄畻鏈�0'),(12,'璁＄畻鏈轰功绫�1','鍏充簬璁＄畻鏈�1'),(13,'璁＄畻鏈轰功绫�2','鍏充簬璁＄畻鏈�2'),(14,'璁＄畻鏈轰功绫�3','鍏充簬璁＄畻鏈�3'),(15,'璁＄畻鏈轰功绫�4','鍏充簬璁＄畻鏈�4'),(16,'璁＄畻鏈轰功绫�5','鍏充簬璁＄畻鏈�5'),(17,'璁＄畻鏈轰功绫�6','鍏充簬璁＄畻鏈�6'),(18,'璁＄畻鏈轰功绫�7','鍏充簬璁＄畻鏈�7'),(20,'Java涔︾睄','鍏充簬java'),(21,'C++涔︾睄1','鍏充簬C++1'),(22,'C++涔︾睄123','1123');

/*Table structure for table `t_import` */

DROP TABLE IF EXISTS `t_import`;

CREATE TABLE `t_import` (
  `id` int(11) NOT NULL auto_increment,
  `goodsId` int(20) default NULL,
  `impoPrice` varchar(20) default NULL,
  `impoDate` datetime default NULL,
  `impoNum` varchar(20) default NULL,
  `impoDesc` varchar(1000) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_t_import` (`goodsId`),
  CONSTRAINT `FK_t_import` FOREIGN KEY (`goodsId`) REFERENCES `t_goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Table structure for table `t_import` */

DROP TABLE IF EXISTS `t_importcargo`;
/*候选键*/
CREATE TABLE `t_importcargo` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `goodsId` INT(11) DEFAULT NULL,
    `providerId` int(11) DEFAULT NULL,
    `impoDate` DATETIME DEFAULT NULL,
    `impoNum` INT(10) DEFAULT NULL,
    `impoWarehouseId` varchar(20) DEFAULT NULL,
	`impoDes` varchar(50) DEFAULT NULL,
	
    PRIMARY KEY (`id`),
    KEY `Cargo_t_import` (`goodsId`),
    KEY `Cargo_t_import1` (`providerId`),
    KEY `Cargo_t_import2` (`impoWarehouseId`),
    CONSTRAINT `Cargo_t_import` FOREIGN KEY (`goodsId`)
        REFERENCES `t_goodstype` (`id`),
    CONSTRAINT `Cargo_t_import1` FOREIGN KEY (`providerId`)
        REFERENCES `t_provider` (`id`)
   
)  ENGINE=INNODB AUTO_INCREMENT=10 DEFAULT CHARSET=UTF8;

/*Data for the table `t_importcargo` */

/*Table structure for table `t_warehouse` */

DROP TABLE IF EXISTS `t_warehouse`;

CREATE TABLE `t_warehouse` (
  `id` int(11) NOT NULL auto_increment,
  `hName` varchar(20) default NULL,
  `hAddress` varchar(20) default NULL,
  `hDes` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `t_dirver` */
DROP TABLE IF EXISTS `t_dirver`;

CREATE TABLE `t_dirver` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(10) default NULL,
  `phone` varchar(20) default NULL,
  `car` varchar(20) default NULL,
  `born` varchar(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `t_provider`;

CREATE TABLE `t_provider` (
  `id` int(11) NOT NULL auto_increment,
  `proId` int(11) default NULL,
  `proName` varchar(20) default NULL,
  `linkman` varchar(20) default NULL,
  `proPhone` varchar(20) default NULL,
  `proDesc` varchar(1000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `t_provider` */

insert  into `t_provider`(`id`,`proId`,`proName`,`linkman`,`proPhone`,`proDesc`) values (22,103,'鍏徃3','103@qq.com','103@qq.com','甯哥敤3'),(23,104,'鍏徃4','104@qq.com','104@qq.com','甯哥敤4'),(24,105,'鍏徃5','105@qq.com','105@qq.com','甯哥敤5'),(25,106,'鍏徃6','106@qq.com','106@qq.com','甯哥敤6'),(26,107,'鍏徃7','107@qq.com','107@qq.com','甯哥敤7'),(27,108,'鍏徃8','108@qq.com','108@qq.com','甯哥敤8'),(28,109,'鍏徃9','109@qq.com','109@qq.com','甯哥敤9'),(29,110,'鍏徃10','110@qq.com','110@qq.com','甯哥敤10'),(30,111,'AB鍏徃','123','11111','AB1'),(31,9111,'涓�1','闃挎柉椤�','1231','闃挎柉椤�');

/*Table structure for table `t_stock` */

DROP TABLE IF EXISTS `t_stock`;

CREATE TABLE `t_stock` (
  `id` int(11) NOT NULL auto_increment,
  `goodsId` int(20) default NULL,
  `providerId` varchar(20) default NULL,
  `wareHouse` varchar(20) default NULL,
  `storeNum` int(10) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_t_stock` (`goodsId`),
  CONSTRAINT `FK_t_stock` FOREIGN KEY (`goodsId`) REFERENCES `t_goodstype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Data for the table `t_stock` */

insert  into `t_stock`(`id`,`goodsId`,`stockNum`,`impoPrice`,`expoPrice`,`stockDesc`) values (2,1,'1001','1001','1011','java01'),(3,2,'1002','1002','1021','java02'),(5,4,'1004','1004','1041','java04'),(6,5,'1005','1005','1051','java05'),(7,6,'1006','1006','1061','java06'),(8,7,'1007','1007','1071','java07'),(9,8,'1008','1008','1081','java08'),(10,9,'1009','1009','1091','java09'),(11,10,'1010','1010','1111','java10'),(13,NULL,NULL,'4321','4321','a'),(14,NULL,NULL,'12','12','a'),(15,NULL,'12321','121','1212','s'),(23,12,'25','3333','23','qwer');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL auto_increment,
  `userName` varchar(20) default NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userName`,`password`) values (1,'java','123');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
