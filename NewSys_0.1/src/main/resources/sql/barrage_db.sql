-- MySQL dump 10.13  Distrib 5.7.22, for Win64 (x86_64)
--
-- Host: localhost    Database: barrage_db
-- ------------------------------------------------------
-- Server version	5.7.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `five_min_data`
--

DROP TABLE IF EXISTS `five_min_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `five_min_data` (
  `record_time` varchar(30) NOT NULL,
  `roomId` int(11) NOT NULL,
  `fans_num` int(11) NOT NULL,
  `barrage` int(11) NOT NULL,
  `gift_value` int(11) NOT NULL,
  PRIMARY KEY (`record_time`,`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gift`
--

DROP TABLE IF EXISTS `gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gift` (
  `_gid` int(11) NOT NULL,
  `_gname` varchar(20) NOT NULL,
  `_type` smallint(6) NOT NULL,
  `_rmb` float(10,2) NOT NULL,
  `_pc` float(10,2) NOT NULL,
  PRIMARY KEY (`_gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `live_day_data`
--

DROP TABLE IF EXISTS `live_day_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `live_day_data` (
  `record_time` varchar(20) NOT NULL,
  `roomId` int(11) NOT NULL,
  `fans_num` int(11) NOT NULL,
  `barrage` int(11) NOT NULL,
  `gift_value` int(11) NOT NULL,
  PRIMARY KEY (`record_time`,`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `live_month_data`
--

DROP TABLE IF EXISTS `live_month_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `live_month_data` (
  `record_time` varchar(20) NOT NULL,
  `roomId` int(11) NOT NULL,
  `fans_num` int(11) NOT NULL,
  `barrage_num` bigint(20) NOT NULL,
  `gift_value` bigint(20) NOT NULL,
  PRIMARY KEY (`record_time`,`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `live_week_data`
--

DROP TABLE IF EXISTS `live_week_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `live_week_data` (
  `record_time` varchar(20) NOT NULL,
  `roomId` int(11) NOT NULL,
  `fans_num` int(11) NOT NULL,
  `barrage` bigint(20) NOT NULL,
  `gift_value` bigint(20) NOT NULL,
  PRIMARY KEY (`record_time`,`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `liveroom`
--

DROP TABLE IF EXISTS `liveroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `liveroom` (
  `roomId` int(11) NOT NULL,
  `anchor` varchar(50) NOT NULL,
  `roomUrl` varchar(50) NOT NULL,
  `roomType` varchar(50) NOT NULL,
  PRIMARY KEY (`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `monitorbarrage`
--

DROP TABLE IF EXISTS `monitorbarrage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitorbarrage` (
  `nn` varchar(30) NOT NULL,
  `roomId` int(11) NOT NULL,
  `barrage` varchar(100) NOT NULL,
  `addTime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `one_hour_data`
--

DROP TABLE IF EXISTS `one_hour_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `one_hour_data` (
  `record_time` varchar(30) NOT NULL,
  `roomId` int(11) NOT NULL,
  `fans_num` int(11) NOT NULL,
  `barrage` int(11) NOT NULL,
  `gift_value` int(11) NOT NULL,
  PRIMARY KEY (`record_time`,`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sensitivewords`
--

DROP TABLE IF EXISTS `sensitivewords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensitivewords` (
  `word` varchar(20) NOT NULL,
  PRIMARY KEY (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `email` varchar(30) NOT NULL,
  `nickName` varchar(20) NOT NULL,
  `password` varchar(15) NOT NULL,
  `head_portrait` varchar(40) DEFAULT NULL,
  `sign_up_flag` tinyint(1) NOT NULL DEFAULT '0',
  `dyNickName1` varchar(40) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wordandusermiddle`
--

DROP TABLE IF EXISTS `wordandusermiddle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wordandusermiddle` (
  `m_word` varchar(20) NOT NULL,
  `m_email` varchar(30) NOT NULL,
  KEY `fk_word` (`m_word`),
  KEY `fk_email` (`m_email`),
  CONSTRAINT `fk_email` FOREIGN KEY (`m_email`) REFERENCES `user` (`email`),
  CONSTRAINT `fk_word` FOREIGN KEY (`m_word`) REFERENCES `sensitivewords` (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wordstatistics`
--

DROP TABLE IF EXISTS `wordstatistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wordstatistics` (
  `word` varchar(20) NOT NULL,
  `roomId` int(11) NOT NULL,
  `count` bigint(20) NOT NULL,
  PRIMARY KEY (`word`,`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-03 18:58:07
