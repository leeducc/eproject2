-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: eproject2tt
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (8,'60000001','2024-10-01 08:14:00','2024-10-01 16:33:00','Explanation Required'),(9,'60000002','2024-10-01 08:00:00','2024-10-01 16:13:00',NULL),(10,'60000003','2024-10-01 09:36:00','2024-10-01 18:03:00',NULL),(11,'60000004','2024-10-01 08:01:00','2024-10-01 16:01:00',NULL),(12,'60000005','2024-10-01 08:08:00','2024-10-01 16:11:00',NULL),(13,'60000006','2024-10-01 08:03:00','2024-10-01 16:21:00',NULL),(26,'60000001','2024-10-02 08:06:00','2024-10-02 16:13:00','Explanation Required'),(27,'60000002','2024-10-02 08:09:00','2024-10-02 16:46:00',NULL),(28,'60000003','2024-10-02 08:03:00','2024-10-02 16:29:00',NULL),(29,'60000004','2024-10-02 08:01:00','2024-10-02 16:10:00',NULL),(30,'60000005','2024-10-02 08:06:00','2024-10-02 16:22:00',NULL),(31,'60000006','2024-10-02 08:08:00','2024-10-02 16:43:00',NULL),(32,'60000001','2024-10-02 07:10:00','2024-10-02 15:10:00','Explanation Required'),(33,'60000002','2024-10-02 15:01:00','2024-10-02 23:01:00',NULL),(34,'60000003','2024-10-02 09:10:00','2024-10-02 18:10:00',NULL),(35,'60000004',NULL,NULL,NULL),(36,'60000005','2024-10-02 07:10:00','2024-10-02 15:10:00',NULL),(37,'60000006','2024-10-02 15:10:00','2024-10-02 23:10:00',NULL),(38,'60000001','2024-10-03 07:00:00','2024-10-03 15:00:00','On Time'),(39,'60000002','2024-10-03 15:00:00','2024-10-03 23:00:00',NULL),(40,'60000003','2024-10-03 09:00:00','2024-10-03 18:00:00',NULL),(41,'60000004',NULL,NULL,NULL),(42,'60000005','2024-10-03 06:00:00','2024-10-03 13:09:00',NULL),(43,'60000006','2024-10-03 15:27:00','2024-10-03 23:27:00',NULL),(44,'60000001','2024-10-03 07:00:00','2024-10-03 15:00:00','On Time'),(45,'60000002','2024-10-03 15:00:00','2024-10-03 23:00:00',NULL),(46,'60000003','2024-10-03 09:00:00','2024-10-03 18:00:00',NULL),(47,'60000004',NULL,NULL,NULL),(48,'60000005','2024-10-03 08:17:00','2024-10-03 16:17:00',NULL),(49,'60000006','2024-10-03 15:00:00','2024-10-03 23:00:00',NULL),(50,'60000001','2024-10-03 07:00:00','2024-10-03 15:00:00','On Time'),(51,'60000002','2024-10-03 15:21:00','2024-10-03 23:21:00',NULL),(52,'60000003','2024-10-03 09:00:00','2024-10-03 18:00:00',NULL),(53,'60000004',NULL,NULL,NULL),(54,'60000005','2024-10-03 08:06:00','2024-10-03 16:06:00',NULL),(55,'60000006','2024-10-03 15:00:00','2024-10-03 23:00:00',NULL),(145,'60000001','2024-09-01 07:15:00','2024-09-01 15:00:00','Late'),(146,'60000001','2024-09-02 07:00:00','2024-09-02 15:00:00','On Time'),(147,'60000001','2024-09-03 08:00:00','2024-09-03 14:30:00','Early Leave'),(148,'60000001','2024-09-04 09:05:00','2024-09-04 18:00:00','Late'),(149,'60000001','2024-09-05 00:00:00','2024-10-28 10:29:21','No Shift'),(150,'60000001','2024-09-06 08:45:00','2024-09-06 15:45:00','On Time'),(151,'60000001','2024-09-07 07:00:00','2024-09-07 15:00:00','On Time'),(152,'60000001','2024-09-08 09:30:00','2024-09-08 18:00:00','Late'),(153,'60000001','2024-09-09 07:00:00','2024-09-09 15:00:00','On Time'),(154,'60000001','2024-09-10 08:00:00','2024-09-10 15:00:00','Early Leave'),(155,'60000001','2024-09-11 09:00:00','2024-09-11 18:00:00','On Time'),(156,'60000001','2024-09-12 00:00:00','2024-10-28 10:29:21','No Shift'),(157,'60000001','2024-09-13 07:00:00','2024-09-13 15:00:00','On Time'),(158,'60000001','2024-09-14 08:30:00','2024-09-14 15:00:00','Late'),(159,'60000001','2024-09-15 07:00:00','2024-09-15 15:00:00','On Time'),(160,'60000001','2024-09-16 09:00:00','2024-09-16 17:00:00','On Time'),(161,'60000001','2024-09-17 07:00:00','2024-09-17 15:00:00','On Time'),(162,'60000001','2024-09-18 07:00:00','2024-09-18 14:30:00','Early Leave'),(163,'60000001','2024-09-19 07:00:00','2024-09-19 15:30:00','On Time'),(164,'60000001','2024-09-20 08:15:00','2024-09-20 15:00:00','Late'),(165,'60000001','2024-09-21 07:00:00','2024-09-21 15:00:00','On Time'),(166,'60000001','2024-09-22 00:00:00','2024-10-28 10:29:21','No Shift'),(167,'60000001','2024-09-23 07:00:00','2024-09-23 15:00:00','On Time'),(168,'60000001','2024-09-24 07:00:00','2024-09-24 15:00:00','On Time'),(169,'60000001','2024-09-25 07:00:00','2024-09-25 15:00:00','On Time'),(170,'60000001','2024-09-26 07:00:00','2024-09-26 14:00:00','Early Leave'),(171,'60000001','2024-09-27 09:00:00','2024-09-27 18:00:00','Late'),(172,'60000001','2024-09-28 07:00:00','2024-09-28 15:00:00','On Time'),(173,'60000001','2024-09-29 08:45:00','2024-09-29 15:00:00','Late'),(174,'60000001','2024-09-30 07:00:00','2024-09-30 15:00:00','On Time'),(175,'60000001','2024-10-28 10:29:20','2024-10-28 10:29:21',NULL);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (6,'Table A11','Nước cam',4,25000),(7,'Table A11','Matcha',4,20000),(8,'Table A12','Nước cam',1,25000),(9,'Table A12','Trà Đào',1,25000),(10,'Table A12','Cà phê sữa',1,35000),(11,'Table A22','Nước cam',1,25000),(12,'Table A22','Matcha',1,20000),(16,'Table A31','Nước ngọt',1,20000),(17,'Table A31','Trà Đào',1,25000);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bill_detail`
--

LOCK TABLES `bill_detail` WRITE;
/*!40000 ALTER TABLE `bill_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bill_order`
--

LOCK TABLES `bill_order` WRITE;
/*!40000 ALTER TABLE `bill_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cashier`
--

LOCK TABLES `cashier` WRITE;
/*!40000 ALTER TABLE `cashier` DISABLE KEYS */;
INSERT INTO `cashier` VALUES (1,'9999','$2a$10$3UHdjzv.D7WrQTBABUueGuKEcC2pHiPabhejCrldOZy2COgGDzVRK');
/*!40000 ALTER TABLE `cashier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Cà phê'),(2,'Nước ngọt'),(3,'Nước hoa quả'),(4,'Trà'),(5,'Đồ ăn vặt '),(6,'Đồ ăn');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `delivery_order`
--

LOCK TABLES `delivery_order` WRITE;
/*!40000 ALTER TABLE `delivery_order` DISABLE KEYS */;
INSERT INTO `delivery_order` VALUES (2,'4077596714','10002323','2024-10-17 07:24:41','Chưa Nhập Hàng'),(3,'4045665207','10002323','2024-10-17 07:26:13','Chưa Nhập Hàng'),(4,'4064601519','10002323','2024-10-17 07:28:27','Chưa Nhập Hàng'),(5,'4000613108','10002323','2024-10-17 07:29:17','Chưa Nhập Hàng'),(6,'4031831390','10002323','2024-10-17 07:30:13','Chưa Nhập Hàng'),(7,'4069783484','10002323','2024-10-17 10:39:27','Chưa Nhập Hàng'),(8,'458975342','10002323','2024-10-24 00:00:00','Chưa Nhập Hàng'),(9,'855224633','10002323','2024-10-24 00:00:00','Chưa Nhập Hàng'),(10,'458618309','10002323','2024-10-24 00:00:00','Chưa Nhập Hàng'),(11,'778694950','10002323','2024-10-24 00:00:00','Chưa Nhập Hàng');
/*!40000 ALTER TABLE `delivery_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `delivery_order_items`
--

LOCK TABLES `delivery_order_items` WRITE;
/*!40000 ALTER TABLE `delivery_order_items` DISABLE KEYS */;
INSERT INTO `delivery_order_items` VALUES (2,'4077596714','CP001',1.00),(3,'4077596714','CP002',2.00),(4,'4045665207','CP001',1.00),(5,'4045665207','CP002',2.00),(6,'4064601519','CP001',1.00),(7,'4064601519','CP002',2.00),(8,'4000613108','CP001',1.00),(9,'4000613108','CP002',2.00),(10,'4031831390','CP001',1.00),(11,'4031831390','CP003',2.00),(12,'4069783484','CP001',1.00),(13,'4069783484','CP002',1.00),(14,'458975342','CP001',2.00),(15,'458975342','CP003',2.00),(16,'855224633','CP001',2.00),(17,'855224633','CP002',2.00),(18,'458618309','CP001',10.00),(19,'458618309','CP003',10.00),(20,'778694950','CP001',2.00),(21,'778694950','CP003',2.00);
/*!40000 ALTER TABLE `delivery_order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (1,1,'Autumn Sale',10.00,'2024-09-01','2024-12-31'),(2,2,'Holiday Special',12.50,'2024-10-01','2024-12-31'),(3,3,'Flash Discount',15.00,'2024-09-01','2024-11-30'),(4,4,'Back to School',10.50,'2024-08-15','2024-11-15'),(5,5,'Winter Deal',14.00,'2024-10-15','2025-01-15');
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `import_order`
--

LOCK TABLES `import_order` WRITE;
/*!40000 ALTER TABLE `import_order` DISABLE KEYS */;
INSERT INTO `import_order` VALUES (1,1,'2024-10-21 01:57:10',2300.00),(2,1,'2024-10-21 01:57:40',1150.00),(3,1,'2024-10-21 02:02:28',1150.00),(4,1,'2024-10-21 02:02:45',2300.00),(5,1,'2024-10-21 02:03:30',1150.00),(6,1,'2024-10-21 03:51:17',1150.00);
/*!40000 ALTER TABLE `import_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `import_order_detail`
--

LOCK TABLES `import_order_detail` WRITE;
/*!40000 ALTER TABLE `import_order_detail` DISABLE KEYS */;
INSERT INTO `import_order_detail` VALUES (1,1,1,10.00,9.00),(2,1,2,5.00,5.00),(3,1,1,10.00,9.00),(4,1,2,5.00,5.00),(5,2,1,10.00,9.00),(6,2,2,5.00,5.00),(7,3,1,10.00,9.00),(8,3,2,5.00,5.00),(9,4,1,10.00,9.00),(10,4,2,5.00,5.00),(11,4,1,10.00,9.00),(12,4,2,5.00,5.00),(13,5,1,10.00,9.00),(14,5,2,5.00,5.00),(15,6,1,10.00,9.00),(16,6,2,5.00,5.00);
/*!40000 ALTER TABLE `import_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment_method`
--

LOCK TABLES `payment_method` WRITE;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
INSERT INTO `payment_method` VALUES (1,'CASH'),(2,'QR'),(3,'CARD');
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'/images/products/cafe_den.jpg','Cà phê đen',30000.00,1),(2,'/images/products/cafe_sua.jpg','Cà phê sữa',35000.00,1),(3,'/images/products/nuoc_cam.jpg','Nước cam',25000.00,3),(4,'/images/products/nuoc_ngot.jpg','Nước ngọt',20000.00,2),(5,'/images/products/tra_sua.jpg','Trà Sữa',15000.00,4),(6,'/images/products/matcha.jpg','Matcha',20000.00,4),(7,'/images/products/tra_dao.jpg','Trà Đào',25000.00,4),(8,'/images/products/tra_dau.jpg','Trà Dâu',20000.00,4),(9,'/images/products/tra_vai.jpg','Trà Vải',15000.00,4),(10,'/images/products/cacao.jpg','CaCao',20000.00,5),(11,'/images/products/tra_xoai.jpg','Trà Xoài',15000.00,4),(29,'/images/products/1.jpg','9999',9999.00,2),(30,'/images/products/3.jpg','999',0.00,6);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Cửa hàng trưởng',50000.00,4000000.00),(2,'Cửa hàng phó',40000.00,3000000.00),(3,'Nhân viên fulltime',26000.00,2000000.00),(4,'Nhân viên parttime',24000.00,0.00);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `salary`
--

LOCK TABLES `salary` WRITE;
/*!40000 ALTER TABLE `salary` DISABLE KEYS */;
/*!40000 ALTER TABLE `salary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shift`
--

LOCK TABLES `shift` WRITE;
/*!40000 ALTER TABLE `shift` DISABLE KEYS */;
INSERT INTO `shift` VALUES (1,'Ca sáng','07:00:00','15:00:00'),(2,'Ca chiều','15:00:00','23:00:00'),(3,'Ca hành chính','09:00:00','18:00:00'),(4,'Off','00:00:00','00:00:00');
/*!40000 ALTER TABLE `shift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shift_assignment`
--

LOCK TABLES `shift_assignment` WRITE;
/*!40000 ALTER TABLE `shift_assignment` DISABLE KEYS */;
INSERT INTO `shift_assignment` VALUES (1,'60000002',4,'2024-09-30'),(2,'60000002',3,'2024-10-03'),(3,'60000002',3,'2024-10-04'),(4,'60000002',4,'2024-10-06'),(5,'60000002',3,'2024-10-02'),(6,'60000002',2,'2024-10-01'),(7,'60000002',2,'2024-10-05'),(8,'60000004',2,'2024-09-30'),(16,'60000001',2,'2024-10-03'),(20,'60000001',2,'2024-10-01'),(26,'60000005',2,'2024-10-02'),(43,'60000002',4,'2024-09-30'),(44,'60000002',3,'2024-10-03'),(45,'60000002',3,'2024-10-04'),(46,'60000002',4,'2024-10-06'),(47,'60000002',3,'2024-10-02'),(48,'60000002',2,'2024-10-01'),(49,'60000002',2,'2024-10-05'),(50,'60000004',2,'2024-09-30'),(51,'60000004',2,'2024-10-03'),(52,'60000004',3,'2024-10-04'),(53,'60000004',3,'2024-10-06'),(54,'60000004',1,'2024-10-02'),(55,'60000004',1,'2024-10-01'),(56,'60000004',4,'2024-10-05'),(57,'60000001',3,'2024-09-30'),(58,'60000001',2,'2024-10-03'),(59,'60000001',3,'2024-10-04'),(60,'60000001',3,'2024-10-06'),(61,'60000001',2,'2024-10-02'),(62,'60000001',2,'2024-10-01'),(63,'60000001',3,'2024-10-05'),(64,'60000005',1,'2024-09-30'),(65,'60000005',1,'2024-10-03'),(66,'60000005',4,'2024-10-04'),(67,'60000005',2,'2024-10-06'),(68,'60000005',2,'2024-10-02'),(69,'60000005',1,'2024-10-01'),(70,'60000005',3,'2024-10-05'),(71,'60000003',1,'2024-09-30'),(72,'60000003',1,'2024-10-03'),(73,'60000003',2,'2024-10-04'),(74,'60000003',2,'2024-10-06'),(75,'60000003',1,'2024-10-02'),(76,'60000003',1,'2024-10-01'),(77,'60000003',2,'2024-10-05'),(78,'60000006',1,'2024-09-30'),(79,'60000006',1,'2024-10-03'),(80,'60000006',1,'2024-10-04'),(81,'60000006',1,'2024-10-06'),(82,'60000006',1,'2024-10-02'),(83,'60000006',1,'2024-10-01'),(84,'60000006',2,'2024-10-05');
/*!40000 ALTER TABLE `shift_assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (4,'60000001','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Nguyễn Văn A','0123456789',1,'nguyenvana@example.com','male'),(5,'60000002','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Trần Thị B','0987654321',2,'tranthib@example.com','female'),(6,'60000003','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Lê Văn C','0123987654',3,'levanc@example.com','male'),(7,'60000004','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Phạm Thị D','0981234567',3,'phamthid@example.com','female'),(8,'60000005','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Ngô Văn E','0123456780',3,'ngovane@example.com','male'),(9,'60000006','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Bùi Thị F','0987654322',4,'buithif@example.com','female');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `status_table`
--

LOCK TABLES `status_table` WRITE;
/*!40000 ALTER TABLE `status_table` DISABLE KEYS */;
INSERT INTO `status_table` VALUES (1,'USING'),(2,'CLEANING'),(3,'AVAILABLE');
/*!40000 ALTER TABLE `status_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `store_info`
--

LOCK TABLES `store_info` WRITE;
/*!40000 ALTER TABLE `store_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `store_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'10002323','Cà Phê Trung Nguyên','0123456891','caphe@trungnguyen.com'),(2,'10002324','Bánh Mỳ Nguyễn Sơn','0987654321','info@nguyenson.com'),(3,'10002325','Hoa Quả Hội An','0912345678','support@hoaquahoiann.com'),(4,'10783948','Bánh Ngọt Nam Dương','09217628','leminhduc1212001@gmail.com'),(5,'10297940','Cốc nhựa','0991232813','cocnhua@contact.vn');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `supplies`
--

LOCK TABLES `supplies` WRITE;
/*!40000 ALTER TABLE `supplies` DISABLE KEYS */;
INSERT INTO `supplies` VALUES (1,'CP001','Cà Phê Arabica','kg',300000.00,82.00,'10002323'),(2,'CP002','Cà Phê Robusta','kg',280000.00,55.00,'10002323'),(3,'CP003','Cà Phê Gói','g',20000.00,100.00,'10002323'),(4,'BM001','Bánh Mỳ Pháp','cái',15000.00,50.00,'10002324'),(5,'BM002','Bánh Mỳ Sandwich','cái',20000.00,30.00,'10002324'),(6,'BM003','Bánh Mỳ Bơ','cái',18000.00,25.00,'10002324'),(7,'HQ001','Bưởi Năm Roi','kg',20000.00,20.00,'10002325'),(8,'HQ002','Dưa Hấu','kg',15000.00,25.00,'10002325'),(9,'HQ003','Nho','kg',60000.00,15.00,'10002325'),(10,'CP004','Cà phê G7','G1',50000.00,0.00,'10002323'),(11,'00000','Coffe','G1',50000.00,0.00,'10002323'),(12,'CN001','Cốc nhựa size M','CAI',500.00,0.00,'10297940'),(13,'CN002','Cốc nhựa size L','CAI',700.00,0.00,'10297940');
/*!40000 ALTER TABLE `supplies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tables`
--

LOCK TABLES `tables` WRITE;
/*!40000 ALTER TABLE `tables` DISABLE KEYS */;
INSERT INTO `tables` VALUES (1,'Table A01',3),(2,'Table A02',3),(3,'Table A03',3),(4,'Table A04',3),(5,'Table A05',3),(6,'Table A06',2),(7,'Table A07',3),(8,'Table A08',3),(9,'Table A09',3),(10,'Table A10',3),(11,'Table A11',1),(12,'Table A12',1),(13,'Table A13',3),(14,'Table A14',3),(15,'Table A15',3),(16,'Table A16',3),(17,'Table A17',3),(18,'Table A18',3),(19,'Table A19',3),(20,'Table A20',3),(21,'Table A21',1),(22,'Table A22',1),(23,'Table A23',3),(24,'Table A24',3),(25,'Table A25',3),(26,'Table A26',3),(27,'Table A27',3),(28,'Table A28',3),(29,'Table A29',3),(30,'Table A30',3),(31,'Table A31',1),(32,'Table A32',3),(33,'Table A33',3),(34,'Table A34',3);
/*!40000 ALTER TABLE `tables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-31  3:02:33
