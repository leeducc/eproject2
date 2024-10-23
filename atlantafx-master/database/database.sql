-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: eproject2
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` varchar(255) DEFAULT NULL,
  `check_in` datetime DEFAULT NULL,
  `check_out` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `staff_id` (`staff_id`),
  CONSTRAINT `attendance_fk_staff_id` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (50,'60000001','2024-09-02 08:00:00','2024-09-02 16:00:00'),(51,'60000001','2024-09-03 08:00:00','2024-09-03 16:00:00'),(52,'60000001','2024-09-04 08:00:00','2024-09-04 16:00:00'),(53,'60000001','2024-09-05 08:00:00','2024-09-05 16:00:00'),(54,'60000001','2024-09-06 08:00:00','2024-09-06 16:00:00'),(55,'60000001','2024-09-07 08:00:00','2024-09-07 16:00:00'),(56,'60000001','2024-09-09 08:00:00','2024-09-09 16:00:00'),(57,'60000001','2024-09-10 08:00:00','2024-09-10 16:00:00'),(58,'60000001','2024-09-11 08:00:00','2024-09-11 16:00:00'),(59,'60000001','2024-09-12 08:00:00','2024-09-12 16:00:00'),(60,'60000001','2024-09-13 08:00:00','2024-09-13 16:00:00'),(61,'60000001','2024-09-14 08:00:00','2024-09-14 16:00:00'),(62,'60000001','2024-09-16 08:00:00','2024-09-16 16:00:00'),(63,'60000001','2024-09-17 08:00:00','2024-09-17 16:00:00'),(64,'60000001','2024-09-18 08:00:00','2024-09-18 16:00:00'),(65,'60000001','2024-09-19 08:00:00','2024-09-19 16:00:00'),(66,'60000001','2024-09-20 08:00:00','2024-09-20 16:00:00'),(67,'60000001','2024-09-21 08:00:00','2024-09-21 16:00:00'),(68,'60000001','2024-09-23 08:00:00','2024-09-23 16:00:00'),(69,'60000001','2024-09-24 08:00:00','2024-09-24 16:00:00'),(70,'60000001','2024-09-25 08:00:00','2024-09-25 16:00:00'),(71,'60000001','2024-09-26 08:00:00','2024-09-26 16:00:00'),(72,'60000001','2024-09-27 08:00:00','2024-09-27 16:00:00'),(73,'60000001','2024-09-28 08:00:00','2024-09-28 16:00:00'),(74,'60000001','2024-10-21 04:35:48','2024-10-21 04:35:49'),(75,'60000001','2024-10-21 10:28:13','2024-10-21 10:28:15'),(76,'60000001','2024-10-21 10:30:21','2024-10-21 10:30:23'),(77,'60000001','2024-10-21 10:49:11','2024-10-21 10:49:12');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cashier`
--

DROP TABLE IF EXISTS `cashier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cashier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cashier`
--

LOCK TABLES `cashier` WRITE;
/*!40000 ALTER TABLE `cashier` DISABLE KEYS */;
INSERT INTO `cashier` VALUES (1,'9999','41c991eb6a66242c0454191244278183ce58cf4a6bcd372f799e4b9cc01886af');
/*!40000 ALTER TABLE `cashier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_order`
--

DROP TABLE IF EXISTS `delivery_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_number` varchar(20) NOT NULL,
  `supplier_id` varchar(10) NOT NULL,
  `order_date` datetime NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Chưa Nhập Hàng',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_order_number` (`order_number`),
  KEY `fk_supplier` (`supplier_id`),
  CONSTRAINT `fk_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`suppliers_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_order`
--

LOCK TABLES `delivery_order` WRITE;
/*!40000 ALTER TABLE `delivery_order` DISABLE KEYS */;
INSERT INTO `delivery_order` VALUES (2,'4077596714','10002323','2024-10-17 07:24:41','Chưa Nhập Hàng'),(3,'4045665207','10002323','2024-10-17 07:26:13','Chưa Nhập Hàng'),(4,'4064601519','10002323','2024-10-17 07:28:27','Chưa Nhập Hàng'),(5,'4000613108','10002323','2024-10-17 07:29:17','Chưa Nhập Hàng'),(6,'4031831390','10002323','2024-10-17 07:30:13','Chưa Nhập Hàng'),(7,'4069783484','10002323','2024-10-17 10:39:27','Chưa Nhập Hàng'),(8,'458975342','10002323','2024-10-24 00:00:00','Chưa Nhập Hàng'),(9,'855224633','10002323','2024-10-24 00:00:00','Chưa Nhập Hàng'),(10,'458618309','10002323','2024-10-24 00:00:00','Chưa Nhập Hàng'),(11,'778694950','10002323','2024-10-24 00:00:00','Chưa Nhập Hàng');
/*!40000 ALTER TABLE `delivery_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_order_items`
--

DROP TABLE IF EXISTS `delivery_order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_number` varchar(20) NOT NULL,
  `supply_code` varchar(8) NOT NULL,
  `quantity` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_delivery_order` (`order_number`),
  KEY `fk_supply` (`supply_code`),
  CONSTRAINT `fk_delivery_order` FOREIGN KEY (`order_number`) REFERENCES `delivery_order` (`order_number`),
  CONSTRAINT `fk_supply` FOREIGN KEY (`supply_code`) REFERENCES `supplies` (`supply_code`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_order_items`
--

LOCK TABLES `delivery_order_items` WRITE;
/*!40000 ALTER TABLE `delivery_order_items` DISABLE KEYS */;
INSERT INTO `delivery_order_items` VALUES (2,'4077596714','CP001',1.00),(3,'4077596714','CP002',2.00),(4,'4045665207','CP001',1.00),(5,'4045665207','CP002',2.00),(6,'4064601519','CP001',1.00),(7,'4064601519','CP002',2.00),(8,'4000613108','CP001',1.00),(9,'4000613108','CP002',2.00),(10,'4031831390','CP001',1.00),(11,'4031831390','CP003',2.00),(12,'4069783484','CP001',1.00),(13,'4069783484','CP002',1.00),(14,'458975342','CP001',2.00),(15,'458975342','CP003',2.00),(16,'855224633','CP001',2.00),(17,'855224633','CP002',2.00),(18,'458618309','CP001',10.00),(19,'458618309','CP003',10.00),(20,'778694950','CP001',2.00),(21,'778694950','CP003',2.00);
/*!40000 ALTER TABLE `delivery_order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_order`
--

DROP TABLE IF EXISTS `import_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `import_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` int NOT NULL,
  `delivery_date` timestamp NOT NULL,
  `total_value` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `supplier_id` (`supplier_id`),
  CONSTRAINT `import_order_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_order`
--

LOCK TABLES `import_order` WRITE;
/*!40000 ALTER TABLE `import_order` DISABLE KEYS */;
INSERT INTO `import_order` VALUES (1,1,'2024-10-21 01:57:10',2300.00),(2,1,'2024-10-21 01:57:40',1150.00),(3,1,'2024-10-21 02:02:28',1150.00),(4,1,'2024-10-21 02:02:45',2300.00),(5,1,'2024-10-21 02:03:30',1150.00),(6,1,'2024-10-21 03:51:17',1150.00);
/*!40000 ALTER TABLE `import_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_order_detail`
--

DROP TABLE IF EXISTS `import_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `import_order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `import_order_id` int NOT NULL,
  `supply_id` int NOT NULL,
  `ordered_quantity` decimal(10,2) NOT NULL,
  `received_quantity` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `import_order_id` (`import_order_id`),
  KEY `supply_id` (`supply_id`),
  CONSTRAINT `import_order_detail_ibfk_1` FOREIGN KEY (`import_order_id`) REFERENCES `import_order` (`id`),
  CONSTRAINT `import_order_detail_ibfk_2` FOREIGN KEY (`supply_id`) REFERENCES `supplies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_order_detail`
--

LOCK TABLES `import_order_detail` WRITE;
/*!40000 ALTER TABLE `import_order_detail` DISABLE KEYS */;
INSERT INTO `import_order_detail` VALUES (1,1,1,10.00,9.00),(2,1,2,5.00,5.00),(3,1,1,10.00,9.00),(4,1,2,5.00,5.00),(5,2,1,10.00,9.00),(6,2,2,5.00,5.00),(7,3,1,10.00,9.00),(8,3,2,5.00,5.00),(9,4,1,10.00,9.00),(10,4,2,5.00,5.00),(11,4,1,10.00,9.00),(12,4,2,5.00,5.00),(13,5,1,10.00,9.00),(14,5,2,5.00,5.00),(15,6,1,10.00,9.00),(16,6,2,5.00,5.00);
/*!40000 ALTER TABLE `import_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `table_id` int DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `voucher_id` int DEFAULT NULL,
  `total_value` decimal(10,2) DEFAULT '0.00',
  `payment_method_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `table_id` (`table_id`),
  KEY `voucher_id` (`voucher_id`),
  KEY `payment_method_id` (`payment_method_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `tables` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`),
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `method` enum('cash','qr','card') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_method`
--

LOCK TABLES `payment_method` WRITE;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_discount`
--

DROP TABLE IF EXISTS `product_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_discount` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `discount` decimal(5,2) NOT NULL,
  `discount_start` datetime NOT NULL,
  `discount_end` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_discount_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_discount`
--

LOCK TABLES `product_discount` WRITE;
/*!40000 ALTER TABLE `product_discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image_link` varchar(255) DEFAULT NULL,
  `category` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'Cửa hàng phó'),(1,'Cửa hàng trưởng'),(3,'Nhân viên fulltime'),(4,'Nhân viên parttime');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salary`
--

DROP TABLE IF EXISTS `salary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL,
  `daily_rate` decimal(10,2) NOT NULL,
  `days_attended` int NOT NULL,
  `bonus` decimal(10,2) DEFAULT '0.00',
  `deductions` decimal(10,2) DEFAULT '0.00',
  `payment_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_salary` decimal(10,2) GENERATED ALWAYS AS ((((`daily_rate` * `days_attended`) + `bonus`) - `deductions`)) STORED,
  PRIMARY KEY (`id`),
  KEY `staff_id` (`staff_id`),
  CONSTRAINT `salary_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary`
--

LOCK TABLES `salary` WRITE;
/*!40000 ALTER TABLE `salary` DISABLE KEYS */;
/*!40000 ALTER TABLE `salary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shift`
--

DROP TABLE IF EXISTS `shift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shift` (
  `shift_id` int NOT NULL AUTO_INCREMENT,
  `shift_name` varchar(255) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`shift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shift`
--

LOCK TABLES `shift` WRITE;
/*!40000 ALTER TABLE `shift` DISABLE KEYS */;
/*!40000 ALTER TABLE `shift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `contact_number` varchar(15) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` enum('male','female','other') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_role` (`role_id`),
  KEY `idx_staff_id` (`staff_id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (4,'60000001','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Nguyễn Văn A','0123456789',1,'nguyenvana@example.com','male'),(5,'60000002','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Trần Thị B','0987654321',2,'tranthib@example.com','female'),(6,'60000003','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Lê Văn C','0123987654',3,'levanc@example.com','male'),(7,'60000004','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Phạm Thị D','0981234567',3,'phamthid@example.com','female'),(8,'60000005','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Ngô Văn E','0123456780',3,'ngovane@example.com','male'),(9,'60000006','$2a$10$3wUX4YW5gOsIBCmZ.q61HOOJGcpQHR4mh18kprJJHH4ul5KDv4ufa','Bùi Thị F','0987654322',4,'buithif@example.com','female');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `suppliers_id` varchar(10) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `contact_number` varchar(15) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_suppliers_id` (`suppliers_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'10002323','Cà Phê Trung Nguyên','0123456891','caphe@trungnguyen.com'),(2,'10002324','Bánh Mỳ Nguyễn Sơn','0987654321','info@nguyenson.com'),(3,'10002325','Hoa Quả Hội An','0912345678','support@hoaquahoiann.com'),(4,'10783948','Bánh Ngọt Nam Dương','09217628','leminhduc1212001@gmail.com'),(5,'10297940','Cốc nhựa','0991232813','cocnhua@contact.vn');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplies`
--

DROP TABLE IF EXISTS `supplies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supply_code` varchar(8) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `quantity` decimal(10,2) NOT NULL,
  `suppliers_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_supply_code` (`supply_code`),
  KEY `supplies_ibfk_1` (`suppliers_id`),
  CONSTRAINT `supplies_ibfk_1` FOREIGN KEY (`suppliers_id`) REFERENCES `suppliers` (`suppliers_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplies`
--

LOCK TABLES `supplies` WRITE;
/*!40000 ALTER TABLE `supplies` DISABLE KEYS */;
INSERT INTO `supplies` VALUES (1,'CP001','Cà Phê Arabica','kg',300000.00,82.00,'10002323'),(2,'CP002','Cà Phê Robusta','kg',280000.00,55.00,'10002323'),(3,'CP003','Cà Phê Gói','g',20000.00,100.00,'10002323'),(4,'BM001','Bánh Mỳ Pháp','cái',15000.00,50.00,'10002324'),(5,'BM002','Bánh Mỳ Sandwich','cái',20000.00,30.00,'10002324'),(6,'BM003','Bánh Mỳ Bơ','cái',18000.00,25.00,'10002324'),(7,'HQ001','Bưởi Năm Roi','kg',20000.00,20.00,'10002325'),(8,'HQ002','Dưa Hấu','kg',15000.00,25.00,'10002325'),(9,'HQ003','Nho','kg',60000.00,15.00,'10002325'),(10,'CP004','Cà phê G7','G1',50000.00,0.00,'10002323'),(11,'00000','Coffe','G1',50000.00,0.00,'10002323'),(12,'CN001','Cốc nhựa size M','CAI',500.00,0.00,'10297940'),(13,'CN002','Cốc nhựa size L','CAI',700.00,0.00,'10297940');
/*!40000 ALTER TABLE `supplies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tables`
--

DROP TABLE IF EXISTS `tables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tables` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `status` enum('available','cleaning','occupied') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tables`
--

LOCK TABLES `tables` WRITE;
/*!40000 ALTER TABLE `tables` DISABLE KEYS */;
/*!40000 ALTER TABLE `tables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `discount` decimal(5,2) NOT NULL,
  `valid_from` datetime NOT NULL,
  `valid_until` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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

-- Dump completed on 2024-10-22  9:25:36
