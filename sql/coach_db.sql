-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: coach_db
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `calendar`
--

DROP TABLE IF EXISTS `calendar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calendar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end` datetime(6) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `start` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar`
--

LOCK TABLES `calendar` WRITE;
/*!40000 ALTER TABLE `calendar` DISABLE KEYS */;
INSERT INTO `calendar` VALUES (1,'Appointment','Reunión con directiva.','2025-04-02 00:00:00.000000','Club','2025-04-01 00:00:00.000000','Evento'),(2,'Appointment','Entrenamiento primer equipo','2025-04-02 00:00:00.000000','Club','2025-04-01 00:00:00.000000','Evento');
/*!40000 ALTER TABLE `calendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match`
--

DROP TABLE IF EXISTS `match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `match` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `away_score` int(11) DEFAULT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `home_score` int(11) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `away_team_id` int(11) DEFAULT NULL,
  `home_team_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7p761185gapvfncwqw9l1xb1b` (`away_team_id`),
  KEY `FKihdnd0o874jfrnxx1gath5ou` (`home_team_id`),
  CONSTRAINT `FK7p761185gapvfncwqw9l1xb1b` FOREIGN KEY (`away_team_id`) REFERENCES `teams` (`id`),
  CONSTRAINT `FKihdnd0o874jfrnxx1gath5ou` FOREIGN KEY (`home_team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match`
--

LOCK TABLES `match` WRITE;
/*!40000 ALTER TABLE `match` DISABLE KEYS */;
INSERT INTO `match` VALUES (1,2,'2025-04-04 20:30:00.000000',4,NULL,NULL,15,5),(2,4,'2025-04-05 18:00:00.000000',7,NULL,NULL,14,1),(3,7,'2025-04-05 18:30:00.000000',3,NULL,NULL,13,10),(4,1,'2025-04-05 19:00:00.000000',4,NULL,NULL,7,12),(5,2,'2025-04-05 19:30:00.000000',4,NULL,NULL,4,9),(6,2,'2025-04-05 20:00:00.000000',5,NULL,NULL,2,11),(7,1,'2025-04-05 20:30:00.000000',2,NULL,NULL,16,8),(8,3,'2025-04-06 13:00:00.000000',2,NULL,NULL,3,6);
/*!40000 ALTER TABLE `match` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `players` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assists` int(11) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `contact_info` varchar(255) DEFAULT NULL,
  `goals` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `red_cards` int(11) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `yellow_cards` int(11) DEFAULT NULL,
  `team_id` int(11) DEFAULT NULL,
  `is_default` tinyint(1) DEFAULT 0,
  `apodo` varchar(255) DEFAULT NULL,
  `dorsal` int(11) DEFAULT NULL,
  `saves` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5nglidr00c4dyybl171v6kask` (`team_id`),
  CONSTRAINT `FK5nglidr00c4dyybl171v6kask` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=326 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES (2,1,'1995-08-22','giovannigonzalez@gmail.com',0,'Giovanni Miguel','GK',0,'González Vásquez',4,2,0,'Gio',12,55),(3,3,'1988-11-30','gustavoperez@gmail.com',1,'Gustavo','GK',1,'Pérez Segura',3,2,0,'Gus',22,55),(4,8,'1997-03-10','alexcervera@gmail.com',0,'Alejandro','GK',0,'Cervera Agudelo',0,2,0,'Álex Cervera',28,55),(5,5,'1992-07-14','cristianmolina@gmail.com',0,'Cristian Paul','DF',0,'Molina Pardo',1,2,0,'Cristian',4,0),(6,7,'1991-04-18','agustinplaza@gmail.com',4,'Agustín','DF',1,'Plaza Teijón',6,2,0,'Agustín Plaza',5,0),(7,4,'1993-09-25','lucasflores@gmail.com',0,'Lucas Tomás','DF',0,'Flores',0,2,0,'Lucas Flores',6,0),(9,5,'1996-02-20','lucasrocha@gmail.com',4,'Lucas','DF',0,'Rocha da Silva',0,2,0,'Lucas Rocha',49,0),(10,8,'1995-06-12','polsalas@gmail.com',0,'Pol','LW',0,'Salas Vidal',0,2,0,'Pol Salas',7,0),(11,15,'1989-10-08','franciscopaniagua@gmail.com',21,'Francisco','RW',0,'Paniagua Soler',1,2,0,'Paniagua',8,0),(12,10,'1990-07-22','lucianogauna@gmail.com',10,'Luciano Silvestre','RW',0,'Gauna',3,2,0,'Luciano Gauna',10,0),(13,3,'1994-04-30','carlessaladie@gmail.com',2,'Carles','RW',0,'Saladié Borràs',0,2,0,'Saladié',11,0),(14,10,'1993-11-03','victorperez@gmail.com',11,'Víctor','LW',0,'Pérez Morales',6,2,0,'Víctor Pérez',14,0),(15,9,'1992-08-17','eliasbeltran@gmail.com',8,'Elías','LW',1,'Beltrán Fernández',3,2,0,'Elías',18,0),(16,2,'1995-12-08','juanjose@gmail.com',2,'Juan José','PIV',0,'Moreno López',1,2,0,'Juan José',19,0),(17,3,'1990-01-15','diegosancho@gmail.com',5,'Diego','PIV',0,'Sancho Bonet',2,2,0,'Diego Sancho',20,0),(18,9,'1994-07-19','diegoquintela@gmail.com',13,'Diego','RW',0,'Quintela Carril',3,2,0,'Quintela',23,0),(19,1,'1996-05-28','pablomunoz@gmail.com',0,'Pablo','LW',0,'Muñoz Moya',1,2,0,'Pablo Muñoz',30,0),(20,4,'1987-09-27','juancarlos@gmail.com',7,'Juan Carlos','PIV',0,'Sánchez Colchón',7,2,0,'Juanqui',9,0),(21,1,'1995-03-15','paulopez@gmail.com',0,'Pau','GK',0,'López Riera',1,3,0,'Pau López',1,55),(23,2,'1993-11-30','didacplana@gmail.com',1,'Dídac','GK',1,'Plana Oltra',0,3,0,'Dídac',21,55),(24,2,'1996-02-20','miquelfeixas@gmail.com',0,'Miquel','GK',0,'Feixas De Jesús',1,3,0,'Miquel Feixas',26,55),(25,5,'1994-09-25','sergiviedma@gmail.com',0,'Sergi','DF',0,'Viedma Palma',0,3,0,'Sergi Viedma',5,0),(26,5,'1992-04-18','antonioperez@gmail.com',16,'Antonio','DF',0,'Pérez Ortega',5,3,0,'Antonio',6,0),(28,4,'1990-12-05','erickolim@gmail.com',0,'Erick','DF',0,'Olim de Mendoça',3,3,0,'Erick',17,0),(29,1,'1995-06-12','albertortas@gmail.com',0,'Albert','LW',0,'Ortas Net',0,3,0,'Ortas',2,0),(30,2,'1993-08-17','matheusrodrigues@gmail.com',2,'Matheus','RW',0,'Rodrigues Cézar da Silva',2,3,0,'Matheus',3,0),(31,6,'1991-05-28','dyegozuffo@gmail.com',5,'Dyego','RW',0,'Henrique Zuffo',6,3,0,'Dyego',7,0),(32,2,'1989-01-15','adolfofernandez@gmail.com',5,'Adolfo','LW',0,'Fernández Díaz',2,3,0,'Adolfo',8,0),(33,1,'1987-09-27','jeanguisel@gmail.com',6,'Jean Pierre','PIV',0,'Guisel Costa',1,3,0,'Pito',10,0),(34,4,'1994-04-30','juanjosecamacho@gmail.com',11,'Juan José','RW',0,'Camacho Pérez',4,3,0,'Catela',13,0),(35,1,'1996-07-19','ericmartel@gmail.com',6,'Eric','LW',0,'Martel García',1,3,0,'E. Martel',14,0),(36,1,'1997-12-08','sergiogonzalez@gmail.com',8,'Sergio','LW',0,'González Pérez',1,3,0,'Sergio González',16,0),(37,15,'1995-03-10','polcano@gmail.com',0,'Pol','PIV',0,'Cano Gil',0,3,0,'Pol Cano',18,0),(38,1,'1990-11-03','rafaelnogueira@gmail.com',11,'Rafael','PIV',0,'Nogueira da Silva',1,3,0,'Fits',19,0),(39,2,'1992-10-22','mamadoutoure@gmail.com',3,'Mamadou','RW',0,'Siragassy Toure',2,3,0,'Touré',20,0),(40,2,'1993-09-14','khalidstitou@gmail.com',3,'Khalid','LW',0,'Bouzid Stitou',2,3,0,'Khalid',22,0),(41,2,'1998-05-20','rubenrodo@gmail.com',0,'Rubén','LW',0,'Rodó Martín',0,3,0,'Rubén Rodó',24,0),(42,4,'1999-01-18','izandsequero@gmail.com',0,'Izan','RW',0,'Sequero Lozano',0,3,0,'Sequero',25,0),(43,6,'1990-01-01','Jesús Herrero Parrón@gmail.com',1,'Jesús','GK',14,'Herrero Parrón',5,4,0,'J. Herrero',1,55),(44,1,'1991-02-01','Jesús García Roldán@gmail.com',0,'Jesús','GK',0,'García Roldán',9,4,0,'Jesús García',27,55),(45,9,'1992-03-01','José Antonio Fernández Raya@gmail.com',0,'José Antonio','DF',7,'Fernández Raya',4,4,0,'Raya',8,0),(46,2,'1993-04-01','Rafael García Aguilera@gmail.com',10,'Rafael','DF',9,'García Aguilera',4,4,0,'Bebe',51,0),(47,2,'1994-05-01','Cecilio Morales Barbado@gmail.com',17,'Cecilio','RW',17,'Morales Barbado',4,4,0,'Cecilio',2,0),(48,4,'1995-06-01','Bruno Rocha Braga@gmail.com',13,'Bruno','RW',16,'Rocha Braga',5,4,0,'Chaguinha',5,0),(49,6,'1996-07-01','Raúl Gómez del Olmo@gmail.com',4,'Raúl','RW',8,'Gómez del Olmo',4,4,0,'Raúl Gómez',6,0),(50,8,'1997-08-01','Pablo Ordóñez Jiménez@gmail.com',4,'Pablo','RW',7,'Ordóñez Jiménez',1,4,0,'Pablo Ordóñez',7,0),(51,9,'1998-09-01','Javier Mínguez Rubio@gmail.com',9,'Javier','RW',0,'Mínguez Rubio',2,4,0,'J. Mínguez',10,0),(52,0,'1999-10-01','Eloy De Pablos De Diego@gmail.com',3,'Eloy','RW',4,'De Pablos De Diego',2,4,0,'Eloy',11,0),(53,0,'2000-11-01','Lucas Vinicius de Santana Brito@gmail.com',1,'Lucas Vinicius','RW',2,'de Santana Brito',2,4,0,'Lucão',12,0),(54,0,'2001-12-01','Tomas Drahovsky@gmail.com',19,'Tomas','RW',6,'Drahovsky',6,4,0,'Drahovsky',16,0),(55,0,'2002-01-01','Sergio Barona Segura@gmail.com',1,'Sergio','RW',0,'Barona Segura',0,4,0,'S. Barona',18,0),(56,1,'2003-02-01','Carlos Bartolomé García@gmail.com',6,'Carlos','RW',11,'Bartolomé García',6,4,0,'Carlos B.',20,0),(57,0,'2004-03-01','Mario Garrido Ruiz@gmail.com',0,'Mario','RW',0,'Garrido Ruiz',0,4,0,'Garrido',32,0),(58,0,'2005-04-01','Sergio Vizuete Rodríguez@gmail.com',0,'Sergio','RW',0,'Vizuete Rodríguez',0,4,0,'Rubio',33,0),(59,0,'2006-05-01','Jaime Martínez-Olivares Farje@gmail.com',0,'Jaime','RW',0,'Martínez-Olivares Farje',0,4,0,'Jaime',34,0),(60,0,'2007-06-01','Daniel Colón Martín@gmail.com',0,'Daniel','RW',0,'Colón Martín',0,4,0,'Colón',40,0),(61,0,'2008-07-01','Gonzalo de Miguel Martínez@gmail.com',1,'Gonzalo','PIV',3,'de Miguel Martínez',0,4,0,'Gonzalo',31,0),(62,0,'2009-08-01','David Segovia Santiago@gmail.com',0,'David','PIV',0,'Segovia Santiago',1,4,0,'Sego',38,0),(63,0,'1990-01-01','Carlos Barrón Redondo@gmail.com',0,'Carlos','GK',9,'Barrón Redondo',3,5,0,'C. Barrón',1,89),(64,0,'1991-02-01','Henrique Kasper Barbieri@gmail.com',1,'Henrique','GK',6,'Kasper Barbieri',0,5,0,'Henrique',2,89),(65,0,'1992-03-01','Luan Muller Barboza@gmail.com',2,'Luan','GK',8,'Muller Barboza',2,5,0,'Luan Muller',3,89),(66,0,'1993-04-01','Manuel Piqueras Dólera@gmail.com',6,'Manuel','DF',8,'Piqueras Dólera',6,5,0,'Piqueras',23,0),(67,0,'1994-05-01','Lucas Leandro Machado@gmail.com',3,'Lucas Leandro','DF',5,'Machado',3,5,0,'Machado',44,0),(68,0,'1995-06-01','Marcelo Augusto dos Santos@gmail.com',3,'Marcelo','DF',7,'Augusto dos Santos',6,5,0,'Marcelo',55,0),(69,0,'1996-07-01','Mario Rivillos Plaza@gmail.com',4,'Mario','RW',11,'Rivillos Plaza',5,5,0,'Rivillos',10,0),(70,0,'1997-08-01','David Peña Romero@gmail.com',6,'David','RW',7,'Peña Romero',2,5,0,'David Peña',11,0),(71,0,'1998-09-01','Joao Victor Alves Sena@gmail.com',9,'Joao Victor','RW',16,'Alves Sena',4,5,0,'Neguinho',17,0),(72,0,'1999-10-01','Ernesto Claudino Gris@gmail.com',6,'Ernesto','LW',11,'Claudino Gris',4,5,0,'Ernesto',27,0),(73,2,'2000-11-01','Fábio Augusto Carneiro dos Santos@gmail.com',11,'Fábio Augusto','LW',7,'Carneiro dos Santos',7,5,0,'Fabinho',28,0),(74,1,'2001-12-01','Mateus Lima Maia@gmail.com',6,'Mateus','LW',1,'Lima Maia',4,5,0,'Mateus Maia',77,0),(75,0,'2002-01-01','Bruno Gomes Bispo do Nascimento@gmail.com',11,'Bruno','PIV',17,'Gomes Bispo do Nascimento',5,5,0,'Bruno Gomes',9,0),(76,0,'2003-02-01','Nil Tent Meaurio@gmail.com',0,'Nil','PIV',0,'Tent Meaurio',0,5,0,'Nil Tent',30,0),(77,0,'2004-03-01','Jesús García del Pino Gordillo@gmail.com',8,'Jesús','PIV',0,'García del Pino Gordillo',6,5,0,'Gordillo',39,0),(78,0,'2005-04-01','Charles Henrique Ferreira da Silva@gmail.com',2,'Charles Henrique','PIV',2,'Ferreira da Silva',1,5,0,'Charuto',93,0),(79,0,'1990-01-01','José Miguel Oliver Solano@gmail.com',1,'José Miguel','GK',17,'Oliver Solano',6,6,0,'Chemi',1,89),(80,0,'1991-02-01','Ginés Gabarrón Rodríguez@gmail.com',0,'Ginés','GK',0,'Gabarrón Rodríguez',0,6,0,'Ginés',15,89),(81,0,'1992-03-01','José Antonio Guillamón Alcaraz@gmail.com',0,'José Antonio','GK',6,'Guillamón Alcaraz',1,6,0,'Chispi',22,89),(82,0,'1993-04-01','Francisco Tomaz Braga Nunes Cavalcante@gmail.com',3,'Francisco Tomaz','DF',18,'Braga Nunes Cavalcante',2,6,0,'Tomaz',8,0),(83,0,'1994-05-01','Jesús Izquierdo Carrillo@gmail.com',1,'Jesús','DF',1,'Izquierdo Carrillo',2,6,0,'Jesús Izquierdo',19,0),(84,0,'1995-06-01','Darío Gil Campoy@gmail.com',3,'Darío','DF',11,'Gil Campoy',5,6,0,'Darío',23,0),(85,0,'1996-07-01','Felipe Carrasco Sánchez@gmail.com',0,'Felipe','DF',0,'Carrasco Sánchez',0,6,0,'Felipe',25,0),(86,0,'1997-08-01','Souheil Mouhoudine@gmail.com',6,'Souheil','DF',4,'Mouhoudine',5,6,0,'Mouhoudine',93,0),(87,0,'1998-09-01','Adrián Rubio Varea@gmail.com',0,'Adrián','RW',0,'Rubio Varea',0,6,0,'Adri Rubio',2,0),(88,0,'1999-10-01','Carlos Alonso Font@gmail.com',0,'Carlos','RW',0,'Alonso Font',0,6,0,'Carlos Alonso',3,0),(89,0,'2000-11-01','Jhonatan Henrique Linhares@gmail.com',2,'Jhonatan Henrique','RW',1,'Linhares',4,6,0,'Linhares',7,0),(90,0,'2001-12-01','Francisco José Cortés Hernández@gmail.com',6,'Francisco José','RW',11,'Cortés Hernández',8,6,0,'Cortés',10,0),(91,0,'2002-01-01','Miguel Ángel Cano Mellado@gmail.com',2,'Miguel Ángel','RW',7,'Cano Mellado',4,6,0,'Mellado',13,0),(92,0,'2003-02-01','Gabriel Rodrigues Motta@gmail.com',9,'Gabriel Rodrigues','RW',8,'Motta',7,6,0,'Motta',14,0),(93,0,'2004-03-01','Gonzalo Castejón Fluixa@gmail.com',5,'Gonzalo','LW',8,'Castejón Fluixa',3,6,0,'Gon Castejón',17,0),(94,0,'2005-04-01','Pablo Ramírez González@gmail.com',15,'Pablo','PIV',16,'Ramírez González',3,6,0,'Pablo Ramírez',9,0),(95,0,'2006-05-01','Walter Fernandes da Costa Firmino@gmail.com',3,'Walter Fernandes','PIV',2,'da Costa Firmino',4,6,0,'Waltinho',11,0),(96,0,'2007-06-01','Muhammad Ossamanmusa@gmail.com',5,'Muhammad','PIV',5,'Ossamanmusa',3,6,0,'Ossamanmusa',20,0),(97,0,'1990-01-01','Juan José Angosto Hernández@gmail.com',0,'Juan José','GK',8,'Angosto Hernández',1,1,0,'Juanjo',12,40),(98,0,'1991-02-01','Álvaro Carrión Tejedor@gmail.com',0,'Álvaro','GK',0,'Carrión Tejedor',1,1,0,'A. Carrión',15,89),(99,0,'1992-03-01','Eduardo Filipe Sousa@gmail.com',0,'Eduardo Filipe','GK',15,'Sousa',1,1,0,'Edu',19,12),(100,0,'1993-04-01','Juan José López Pérez@gmail.com',0,'Juan José','GK',0,'López Pérez',0,1,0,'Juanjo López',38,0),(101,0,'1994-05-01','Marlon Oliveira Araújo@gmail.com',2,'Marlon','DF',9,'Oliveira Araújo',7,1,0,'Marlon',5,0),(102,0,'1995-06-01','Felipe Valerio Pascual@gmail.com',7,'Felipe','DF',7,'Valerio Pascual',5,1,0,'F. Valerio',7,0),(103,0,'1996-07-01','Ricardo Mayor Gonzálvez@gmail.com',9,'Ricardo','DF',11,'Mayor Gonzálvez',3,1,0,'Ricardo',8,0),(104,0,'1997-08-01','Bruno Días Azevedo@gmail.com',1,'Bruno','DF',0,'Días Azevedo',0,1,0,'Bruno',30,0),(105,0,'1998-09-01','Javier Martín Rodríguez-Cans@gmail.com',0,'Javier Martín','DF',0,'Rodríguez-Cans',0,1,0,'Javi Martín',35,0),(106,0,'1999-10-01','Ricardo Henrique Lemos Diniz@gmail.com',6,'Ricardo Henrique','LW',6,'Lemos Diniz',5,1,0,'Ricardinho',10,0),(107,0,'2000-11-01','Marcel De Mendoça Marques@gmail.com',5,'Marcel','LW',6,'De Mendoça Marques',3,1,0,'Marcel',11,0),(108,0,'2001-12-01','David Álvarez Munteis@gmail.com',9,'David','LW',11,'Álvarez Munteis',1,1,0,'D. Álvarez',16,0),(109,1,'2002-01-01','Juan Ángel Robles Calderón@gmail.com',0,'Juan Ángel','LW',0,'Robles Calderón',0,1,0,'Robles',28,0),(110,2,'2003-02-01','César Velasco López@gmail.com',5,'César','RW',5,'Velasco López',10,1,0,'César',29,0),(111,1,'2004-03-01','Fabricio Bastezini@gmail.com',9,'Fabricio','RW',16,'Bastezini',9,1,0,'Gadeia',31,0),(112,3,'2005-04-01','Carlos Villalobos Rodríguez@gmail.com',0,'Carlos','RW',0,'Villalobos Rodríguez',0,1,0,'Carlos Villalobos',66,0),(113,4,'2006-05-01','Esteban Cejudo Guerrero@gmail.com',6,'Esteban','PIV',9,'Cejudo Guerrero',5,1,0,'Esteban',4,0),(114,1,'2007-06-01','Rafael dos Santos@gmail.com',9,'Rafael','PIV',12,'dos Santos',7,1,0,'Rafa Santos',9,0),(115,5,'2008-07-01','Miguel Ángel Palacio Parada@gmail.com',0,'Miguel Ángel','PIV',0,'Palacio Parada',0,1,0,'Miguel Palacio',17,0),(116,6,'2009-08-01','Álvaro González Teruel@gmail.com',1,'Álvaro','PIV',0,'González Teruel',0,1,0,'Álvaro',23,0),(117,1,'1995-01-01','Carlos Eduardo Nesello Espindola@gmail.com',3,'Carlos Eduardo','GK',13,'Nesello Espindola',6,7,0,'Espindola',1,89),(118,7,'1996-02-01','Eduardo da Silva Ferreira Lima@gmail.com',0,'Eduardo','GK',10,'da Silva Ferreira Lima',4,7,0,'Dudú',28,89),(119,8,'1997-03-01','João Vítor Salla Vivan@gmail.com',1,'João Vítor','DF',8,'Salla Vivan',4,7,0,'João Salla',5,0),(120,9,'1998-04-01','Fernando Montardo Trindade@gmail.com',0,'Fernando','DF',0,'Montardo Trindade',0,7,0,'Fernando Trindade',7,0),(121,1,'1999-05-01','Carlos José Sanz Salcedo@gmail.com',1,'Carlos José','DF',15,'Sanz Salcedo',10,7,0,'Carlos Sanz',8,0),(123,6,'2001-07-01','Daniel López López@gmail.com',9,'Daniel','LW',10,'López López',7,7,0,'D. Zurdo',6,0),(124,7,'2002-08-01','Renato Lopes Furtado@gmail.com',11,'Renato','LW',5,'Lopes Furtado',10,7,0,'Renato',12,0),(125,8,'2003-09-01','Alejandro Lemine Luque@gmail.com',4,'Alejandro','LW',4,'Lemine Luque',5,7,0,'Lemine',15,0),(126,9,'2004-10-01','Joaquín Expósito López@gmail.com',0,'Joaquín','LW',0,'Expósito López',0,7,0,'Joaquín',19,0),(127,10,'2005-11-01','Miguel Moya Hoyo@gmail.com',6,'Miguel','LW',17,'Moya Hoyo',6,7,0,'Míchel',21,0),(128,0,'2006-12-01','Franklin Douglas Cavalcante@gmail.com',1,'Franklin Douglas','LW',3,'Cavalcante',2,7,0,'Franklin',23,0),(129,0,'2007-01-01','Giancarlos Antoniazzi@gmail.com',3,'Giancarlos','LW',7,'Antoniazzi',5,7,0,'Antoniazzi',55,0),(130,0,'2008-02-01','Carlos Rodríguez López@gmail.com',0,'Carlos','LW',0,'Rodríguez López',0,7,0,'Carlos R.',77,0),(131,0,'2009-03-01','Ángel Bingyoba Vuiti@gmail.com',0,'Ángel','PIV',0,'Bingyoba Vuiti',0,7,0,'A. Bingyoba',11,0),(132,0,'2010-04-01','Alan Brandi Cuaniscú@gmail.com',13,'Alan','PIV',6,'Brandi Cuaniscú',4,7,0,'Alan Brandi',14,0),(133,0,'2011-05-01','Matías Sebastián Rosa@gmail.com',18,'Matías Sebastián','PIV',13,'Rosa',5,7,0,'Mati Rosa',41,0),(134,0,'2012-06-01','Javier García Moreno@gmail.com',6,'Javier','LW',0,'García Moreno',5,7,0,'Chino',20,0),(136,0,'1990-01-01','Borja Puerta Sánchez@gmail.com',0,'Borja','GK',0,'Puerta Sánchez',23,8,0,'Borja Puerta',1,24),(137,0,'1991-02-01','Àlex Lluch Romeu@gmail.com',0,'Àlex','GK',0,'Lluch Romeu',2,8,0,'Àlex Lluch',15,24),(138,0,'1992-03-01','Adrián Walter Cabezas Altozano@gmail.com',0,'Adrián Walter','GK',0,'Cabezas Altozano',0,8,0,'Walter',26,24),(139,1,'1993-04-01','Marc Tolrà García@gmail.com',6,'Marc','DF',15,'Tolrà García',1,8,0,'Marc Tolrà',3,0),(140,0,'1994-05-01','Oriol Santos Martí@gmail.com',1,'Oriol','DF',0,'Santos Martí',1,8,0,'Uri Santos',4,0),(141,0,'1995-06-01','Bernat Povill Cerdeira@gmail.com',15,'Bernat','LW',18,'Povill Cerdeira',6,8,0,'Povill',2,0),(142,0,'1996-07-01','Marc Puigvert Esteve@gmail.com',8,'Marc','LW',19,'Puigvert Esteve',4,8,0,'Puigvert',7,0),(143,0,'1997-08-01','Víctor Sánchez Medina@gmail.com',0,'Víctor','LW',0,'Sánchez Medina',0,8,0,'V. Sánchez',8,0),(144,1,'1998-09-01','Victor Ramos Mora@gmail.com',3,'Victor','LW',0,'Ramos Mora',1,8,0,'Víctor Ramos',10,0),(145,0,'1999-10-01','Albert Cardona Bayer@gmail.com',7,'Albert','LW',3,'Cardona Bayer',4,8,0,'Cardona',12,0),(146,0,'2000-11-01','Sebastián Corso@gmail.com',0,'Sebastián','LW',4,'Corso',8,8,0,'Corso',17,0),(147,0,'2001-12-01','Nil Closas Dalmau@gmail.com',2,'Nil','LW',8,'Closas Dalmau',4,8,0,'Nil',20,0),(148,0,'2002-01-01','Alejandro Verdejo Correa@gmail.com',12,'Alejandro','LW',19,'Verdejo Correa',7,8,0,'Álex Verdejo',21,0),(149,0,'2003-02-01','Niel·lo Bertran Ferrer@gmail.com',1,'Niel·lo','RW',0,'Bertran Ferrer',1,8,0,'Niel·lo',23,0),(150,0,'2004-03-01','Víctor Pérez Pérez@gmail.com',2,'Víctor','RW',0,'Pérez Pérez',3,8,0,'Víctor Pérez',24,0),(151,0,'2005-04-01','Joan Vázquez González@gmail.com',0,'Joan','RW',0,'Vázquez González',0,8,0,'Joan',27,0),(152,0,'2006-05-01','Matías Leguizamón@gmail.com',0,'Matías','PIV',0,'Leguizamón',0,8,0,'Mati Leguizamon',9,0),(153,0,'2007-06-01','Leandro Ferreira Azevedo@gmail.com',8,'Leandro','PIV',6,'Ferreira Azevedo',5,8,0,'Leandrinho',11,0),(154,0,'1990-01-01','Asier Llamas Echeverria@gmail.com',1,'Asier','GK',0,'Llamas Echeverria',22,9,0,'Asier',1,24),(155,0,'1991-02-01','Alejandro Palazón González@gmail.com',0,'Alejandro','GK',0,'Palazón González',1,9,0,'Palazón',97,24),(156,0,'1992-03-01','Cicero Severino Da Silva Junior@gmail.com',6,'Cicero Severino','DF',0,'Da Silva Junior',9,9,0,'Juninho',3,0),(157,0,'1993-04-01','Antonio Escribano Gómez@gmail.com',3,'Antonio','DF',0,'Escribano Gómez',12,9,0,'Tony',4,0),(158,0,'1994-05-01','Roberto Martil Fernández@gmail.com',3,'Roberto','DF',0,'Martil Fernández',5,9,0,'R. Martil',8,0),(159,0,'1995-06-01','Ihor Korsun@gmail.com',5,'Ihor','DF',0,'Korsun',7,9,0,'Korsun',13,0),(160,0,'1996-07-01','Andrés Sebastián Geraghty@gmail.com',6,'Andrés Sebastián','RW',0,'Geraghty',12,9,0,'A. Geraghty',2,0),(161,0,'1997-08-01','Kokoro Harada@gmail.com',0,'Kokoro','RW',0,'Harada',0,9,0,'Kokoro',5,0),(162,0,'1998-09-01','Ion Cerviño Pueyo@gmail.com',8,'Ion','RW',1,'Cerviño Pueyo',8,9,0,'Ion Cerviño',7,0),(163,0,'1999-10-01','Daniel Saldise Martinicorena@gmail.com',11,'Daniel','RW',0,'Saldise Martinicorena',1,9,0,'D. Saldise',9,0),(164,0,'2000-11-01','Pablo García Lago@gmail.com',12,'Pablo','RW',0,'García Lago',2,9,0,'Pachu',10,0),(165,0,'2001-12-01','Gabriel Ferreira Santos@gmail.com',9,'Gabriel','RW',0,'Ferreira Santos',3,9,0,'Ligeiro',11,0),(166,0,'2002-01-01','Oier Martiarena Astiz@gmail.com',0,'Oier','RW',0,'Martiarena Astiz',0,9,0,'Oier',19,0),(167,0,'2003-02-01','Josu Mendive Zalba@gmail.com',0,'Josu','RW',0,'Mendive Zalba',0,9,0,'J. Mendive',22,0),(169,0,'1990-01-01','Antonio Navarro López@gmail.com',1,'Antonio','GK',0,'Navarro López',5,10,0,'Antonio Navarro',12,24),(170,0,'1991-02-01','Daniel López Juárez@gmail.com',0,'Daniel','GK',0,'López Juárez',2,10,0,'Dani Juárez',13,24),(171,0,'1992-03-01','Iker Hernán Mediavilla@gmail.com',0,'Iker Hernán','GK',0,'Mediavilla',1,10,0,'Iker',15,24),(172,0,'1993-04-01','Samuel Sánchez Migallón Fernández Pacheco@gmail.com',0,'Samuel','GK',0,'Sánchez Migallón Fernández Pacheco',0,10,0,'Samuel',15,24),(173,0,'1994-05-01','Javier Fernández-Pacheco Serrano@gmail.com',0,'Javier','GK',0,'Fernández-Pacheco Serrano',3,10,0,'Javi Bule',18,24),(174,0,'1995-06-01','Pedro Augusto Feitosa Beraldo@gmail.com',2,'Pedro Augusto','DF',0,'Feitosa Beraldo',4,10,0,'Pedro',16,0),(175,0,'1996-07-01','Deivid Pires da Silva Santos@gmail.com',0,'Deivid Pires','DF',0,'da Silva Santos',4,10,0,'Deivão',21,0),(176,0,'1997-08-01','Aarón Canales Avendaño@gmail.com',0,'Aarón','RW',0,'Canales Avendaño',0,10,0,'Aarón',4,0),(177,0,'1998-09-01','Nefi Gonçalves Castro@gmail.com',1,'Nefi Gonçalves','RW',0,'Castro',0,10,0,'Nefi Gonçalves',5,0),(178,0,'1999-10-01','Pedro Herreros Ballesteros@gmail.com',3,'Pedro','RW',0,'Herreros Ballesteros',5,10,0,'Pedro Herreros',6,0),(179,0,'2000-11-01','Javier Alonso Giménez@gmail.com',2,'Javier','RW',0,'Alonso Giménez',13,10,0,'Javi Alonso',7,0),(180,0,'2001-12-01','Pablo Robles Gastellut@gmail.com',1,'Pablo','RW',0,'Robles Gastellut',3,10,0,'Robles',8,0),(181,0,'2002-01-01','Antonio Sierra García@gmail.com',3,'Antonio','RW',0,'Sierra García',4,10,0,'Antoñito',10,0),(182,0,'2003-02-01','Álvaro López del Pino@gmail.com',5,'Álvaro','RW',1,'López del Pino',3,10,0,'Alvaro López',11,0),(183,0,'2004-03-01','Ezequiel Montero Jiménez@gmail.com',4,'Ezequiel','RW',0,'Montero Jiménez',6,10,0,'Zequi',17,0),(184,0,'2005-04-01','Humberto Ribeiro Resende@gmail.com',8,'Humberto','RW',1,'Ribeiro Resende',2,10,0,'Humberto',23,0),(185,0,'2006-05-01','Daniel Gabriel García@gmail.com',15,'Daniel Gabriel','PIV',0,'García',7,10,0,'Daniel',9,0),(186,0,'2007-06-01','Raúl Campos Paíno@gmail.com',4,'Raúl','PIV',0,'Campos Paíno',3,10,0,'Raúl Campos',30,0),(187,0,'2008-07-01','Juan Emilio Gil Camacho@gmail.com',7,'Juan Emilio','PIV',0,'Gil Camacho',8,10,0,'Juan Emilio',99,0),(188,0,'1990-01-01','Mikael Henrique Deon@gmail.com',0,'Mikael','GK',0,'Henrique Deon',1,11,0,'Mika',2,24),(189,0,'1991-02-01','Gabriel Díaz Pernas@gmail.com',0,'Gabriel','GK',0,'Díaz Pernas',0,11,0,'Gabriel',19,24),(190,0,'1992-03-01','Michal Kaluza@gmail.com',2,'Michal','GK',0,'Kaluza',23,11,0,'M. Kaluza',24,18),(191,0,'1993-04-01','Bernardo Jesús Araya Ponce@gmail.com',4,'Bernardo Jesús','DF',1,'Araya Ponce',7,11,0,'Bernardo Araya',4,0),(192,0,'1994-05-01','Jorge Quelle Gómez@gmail.com',0,'Jorge','DF',0,'Quelle Gómez',2,11,0,'Quelle',5,0),(193,0,'1995-06-01','Ismael Samartino Obarrio@gmail.com',6,'Ismael','DF',0,'Samartino Obarrio',8,11,0,'Isma',9,0),(194,0,'1996-07-01','Rikelme Alves Ribeira@gmail.com',8,'Rikelme','RW',0,'Alves Ribeira',3,11,0,'Rikelme',7,0),(195,0,'1997-08-01','Carlos Cayado López@gmail.com',2,'Carlos','RW',0,'Cayado López',1,11,0,'Charly',10,0),(196,0,'1998-09-01','Wagner Aparecido de Oliveira Junior@gmail.com',7,'Wagner Aparecido','RW',0,'de Oliveira Junior',2,11,0,'Junhinho',13,0),(197,0,'1999-10-01','Alberto Mirás Neira@gmail.com',4,'Alberto','RW',0,'Mirás Neira',5,11,0,'Alberto',14,0),(198,0,'2000-11-01','Marc Campàs Rigat@gmail.com',0,'Marc','LW',0,'Campàs Rigat',0,11,0,'Campàs',19,0),(199,0,'2001-12-01','Walter Gonçalves Da Costa@gmail.com',0,'Walter','LW',0,'Gonçalves Da Costa',0,11,0,'Walter',25,0),(200,0,'2002-01-01','Bruno Expósito García@gmail.com',0,'Bruno','LW',0,'Expósito García',0,11,0,'Bruno Expósito',27,0),(201,0,'2003-02-01','David Gomes De Pina@gmail.com',0,'David','LW',0,'Gomes De Pina',0,11,0,'David',27,0),(202,0,'2004-03-01','Christian Rivas García@gmail.com',6,'Christian','PIV',0,'Rivas García',3,11,0,'Pitero',6,0),(205,0,'1990-01-01','Víctor Areales Madueño@gmail.com',0,'Víctor','GK',0,'Areales Madueño',4,12,0,'Víctor Areales',1,18),(206,0,'1991-02-01','Fabio Alvira Pérez@gmail.com',0,'Fabio','GK',0,'Alvira Pérez',2,12,0,'Fabio',12,18),(207,0,'1992-03-01','Ioan Sergiu Sandru Grasu@gmail.com',0,'Ioan Sergiu','GK',0,'Sandru Grasu',0,12,0,'Chechu',30,18),(208,0,'1993-04-01','Emmanuel Alexis Alarcón@gmail.com',0,'Emmanuel Alexis','GK',0,'Alarcón',1,12,0,'Alexis',51,18),(209,0,'1994-05-01','Julio Damián Mareco Galeano@gmail.com',0,'Julio Damián','DF',0,'Mareco Galeano',2,12,0,'J. Mareco',3,0),(210,0,'1995-06-01','Mykola Mykytiuk@gmail.com',3,'Mykola','DF',0,'Mykytiuk',2,12,0,'Mykytiuk',6,0),(211,0,'1996-07-01','Pablo María Del Moral Jiménez@gmail.com',2,'Pablo María','DF',0,'Del Moral Jiménez',3,12,0,'Pablo Del Moral',14,0),(212,0,'1997-08-01','Murilo Duarte da Silva@gmail.com',8,'Murilo Duarte','DF',0,'da Silva',7,12,0,'Murilo Duarte',23,0),(213,0,'1998-09-01','Hugo Expósito Sicilia@gmail.com',1,'Hugo','LW',0,'Expósito Sicilia',0,12,0,'Hugo',2,0),(214,0,'1999-10-01','Alejandro Bernal Gascón@gmail.com',0,'Alejandro','LW',0,'Bernal Gascón',0,12,0,'Álex Bernal',4,0),(215,0,'2000-11-01','Lluc Parera Martínez@gmail.com',0,'Lluc','LW',0,'Parera Martínez',0,12,0,'Lluc',5,0),(216,0,'2001-12-01','Agustín del Rey@gmail.com',4,'Agustín','LW',0,'del Rey',0,12,0,'Titi',7,0),(217,0,'2002-01-01','Tiago Manuel Rodrigues Macedo@gmail.com',2,'Tiago Manuel','LW',0,'Rodrigues Macedo',6,12,0,'Tiago Macedo',8,0),(218,0,'2003-02-01','Arnaldo Enrique Báez Torres@gmail.com',6,'Arnaldo Enrique','LW',1,'Báez Torres',5,12,0,'Arnaldo Báez',10,0),(219,0,'2004-03-01','Javier Aranda Prendes@gmail.com',0,'Javier','LW',0,'Aranda Prendes',0,12,0,'Aranda',11,0),(220,0,'2005-04-01','Felipe Echavarría@gmail.com',9,'Felipe','LW',0,'Echavarría',8,12,0,'Echavarría',15,0),(221,0,'2006-05-01','Alberto Peinado López@gmail.com',0,'Alberto','LW',0,'Peinado López',0,12,0,'Peinado',18,0),(222,0,'2007-06-01','Enzo Gabriel Báez@gmail.com',0,'Enzo Gabriel','LW',0,'Báez',0,12,0,'Enzo Báez',19,0),(223,0,'2008-07-01','Guilherme Dos Santos@gmail.com',0,'Guilherme','LW',0,'Dos Santos',4,12,0,'Giu Santos',21,0),(224,0,'2009-08-01','Pablo Cendán Alén@gmail.com',0,'Pablo','PIV',0,'Cendán Alén',1,12,0,'Cendán',9,0),(225,0,'2010-09-01','Kauê Pereira de Oliveira@gmail.com',9,'Kauê','PIV',0,'Pereira de Oliveira',8,12,0,'Kauê',25,0),(226,0,'2011-10-01','Juan Antonio Moraleja Ruiz@gmail.com',11,'Juan Antonio','PIV',0,'Moraleja Ruiz',1,12,0,'Juanan',77,0),(227,0,'1990-01-01','Daniel Fernández Mena@gmail.com',0,'Daniel','GK',0,'Fernández Mena',0,13,0,'Dani',1,18),(228,0,'1991-02-01','Adrián García-Saavedra Pinés@gmail.com',0,'Adrián','GK',0,'García-Saavedra Pinés',0,13,0,'Adri',25,18),(229,0,'1992-03-01','Marcos Dioney Schmidt@gmail.com',1,'Marcos Dioney','GK',0,'Schmidt',4,13,0,'Marcao',29,18),(230,0,'1993-04-01','Alejandro González Pérez@gmail.com',0,'Alejandro','GK',0,'González Pérez',1,13,0,'Álex González',30,18),(231,0,'1994-05-01','Diego Torres Manzanero@gmail.com',0,'Diego','GK',0,'Torres Manzanero',7,13,0,'Diego',90,18),(232,0,'1995-06-01','Antonio Manuel Sánchez Tienda@gmail.com',2,'Antonio Manuel','DF',0,'Sánchez Tienda',3,13,0,'Boyis',5,0),(233,0,'1996-07-01','Manuel Urbano Cañete@gmail.com',5,'Manuel','DF',0,'Urbano Cañete',6,13,0,'Lolo',8,0),(234,0,'1997-08-01','David Duque González@gmail.com',1,'David','DF',0,'Duque González',1,13,0,'Nano',23,0),(235,0,'1998-09-01','José Mario Cuenca Miranda@gmail.com',1,'José Mario','LW',0,'Cuenca Miranda',3,13,0,'José Mario',2,0),(236,0,'1999-10-01','Alberto García Delgado@gmail.com',4,'Alberto','LW',0,'García Delgado',12,13,0,'Alberto García',6,0),(237,0,'2000-11-01','Antonio Fernando Aguilera Sancho@gmail.com',6,'Antonio Fernando','LW',0,'Aguilera Sancho',1,13,0,'Fernando',7,0),(238,0,'2001-12-01','Pol Pacheco González@gmail.com',15,'Pol','LW',1,'Pacheco González',7,13,0,'Pol Pacheco',10,0),(239,0,'2002-01-01','Eloy Rojas Gómez@gmail.com',3,'Eloy','LW',0,'Rojas Gómez',1,13,0,'Eloy Rojas',14,0),(240,5,'2003-02-01','Alberto Ferraz Barboza@gmail.com',2,'Alberto','LW',0,'Ferraz Barboza',6,13,0,'Bynho',17,0),(241,6,'2004-03-01','Carlos Gómez Jerez@gmail.com',6,'Carlos','LW',0,'Gómez Jerez',2,13,0,'Carlos',27,0),(242,7,'2005-04-01','Daniel Merino Cisternes@gmail.com',1,'Daniel','LW',0,'Merino Cisternes',0,13,0,'Merino',33,0),(243,8,'2006-05-01','Francisco Javier Solano León@gmail.com',2,'Francisco Javier','PIV',0,'Solano León',3,13,0,'Solano',9,0),(244,9,'2007-06-01','Jorge Carrasco García@gmail.com',6,'Jorge','PIV',0,'Carrasco García',6,13,0,'Carrasco',11,0),(245,10,'2008-07-01','Alberto Saura Castejón@gmail.com',3,'Alberto','PIV',0,'Saura Castejón',0,13,0,'Saura',19,0),(246,11,'1990-01-01','Adrián Pereira Oliveros@gmail.com',0,'Adrián','GK',0,'Pereira Oliveros',0,14,0,'Adrián Pereira',1,18),(247,12,'1991-02-01','Mario Gómez Hernádez@gmail.com',0,'Mario','GK',0,'Gómez Hernádez',0,14,0,'Mario Gómez',1,30),(248,13,'1992-03-01','Raúl Jiménez López@gmail.com',0,'Raúl','GK',1,'Jiménez López',23,14,0,'Raúl Jiménez',15,30),(249,14,'1993-04-01','Gabriel Mena Jorajuria@gmail.com',0,'Gabriel','GK',0,'Mena Jorajuria',0,14,0,'Gabriel',36,30),(250,15,'1994-05-01','David García Beltrán@gmail.com',5,'David','DF',0,'García Beltrán',6,14,0,'David',2,0),(251,16,'1995-06-01','Adrián Tapias Villalba@gmail.com',0,'Adrián','DF',0,'Tapias Villalba',0,14,0,'Tapias',4,0),(252,17,'1996-07-01','Sergio Sierra de los Santos@gmail.com',5,'Sergio','DF',1,'Sierra de los Santos',4,14,0,'Sepe',10,0),(253,18,'1997-08-01','Daniel Fernández García@gmail.com',0,'Daniel','DF',2,'Fernández García',3,14,0,'Dani Fernández',14,0),(254,19,'1998-09-01','João Batista Barreto Neto@gmail.com',4,'João Batista','RW',0,'Barreto Neto',5,14,0,'João Batista',6,0),(255,19,'1999-10-01','Lucas Martín Tripodi@gmail.com',16,'Lucas Martín','RW',1,'Tripodi',9,14,0,'L. Tripodi',8,0),(256,20,'2000-11-01','Alberto Lahuerta Galindo@gmail.com',7,'Alberto','RW',0,'Lahuerta Galindo',5,14,0,'Alberto Lahuerta',11,0),(257,15,'2001-12-01','Gabriel Henrique Vasques de Paula@gmail.com',4,'Gabriel Henrique','RW',1,'Vasques de Paula',7,14,0,'Gabriel Vasques',18,0),(258,14,'2002-01-01','Terry Prestjord@gmail.com',8,'Terry','RW',0,'Prestjord',5,14,0,'Terry',21,0),(259,16,'2003-02-01','Cristian Aparicio Gómez@gmail.com',0,'Cristian','PIV',0,'Aparicio Gómez',0,14,0,'Cristian Rubio',9,0),(260,9,'2004-03-01','Eric Pérez Veganzones@gmail.com',6,'Eric','PIV',0,'Pérez Veganzones',7,14,0,'Eric Pérez',23,0),(261,5,'2005-04-01','Iván de Uña Feliz@gmail.com',5,'Iván','PIV',0,'de Uña Feliz',5,14,0,'Malaguti',96,0),(286,7,'1994-05-14','david.palmas.mato@gmail.com',0,'David','GK',0,'Palmas Mato',3,15,0,'David Palmas',13,55),(287,6,'1993-07-23','vinicius.henrique.schutt@gmail.com',0,'Vinicius','GK',0,'Henrique Schütt',3,15,0,'Schütt',21,55),(288,2,'1992-12-05','evandro.rafagnin@gmail.com',0,'Evandro','GK',0,'Rafagnin',1,15,0,'Peixe',26,55),(289,11,'1991-03-09','agustin.luciano.raggiati@gmail.com',8,'Agustín Luciano','DF',2,'Raggiati',11,15,0,'Power',3,0),(290,12,'1990-04-18','david.montero.dominguez@gmail.com',0,'David','DF',0,'Montero Domínguez',1,15,0,'Montero',5,0),(291,13,'1994-06-12','izan.garcia.gonzalez@gmail.com',0,'Izan','DF',0,'García González',0,15,0,'Izan',28,0),(292,14,'1993-02-28','leandro.eduardo.altamirano@gmail.com',4,'Leandro Eduardo','RW',0,'Altamirano',4,15,0,'L. Altamirano',7,0),(293,16,'1992-11-17','diego.alexandre.favero@gmail.com',0,'Diego Alexandre','RW',1,'Fávero',4,15,0,'Diego Fávero',10,0),(294,17,'1994-01-24','alejandro.garcia.munoz@gmail.com',2,'Alejandro','RW',0,'García Muñoz',2,15,0,'Álex García',14,0),(295,19,'1991-09-11','douglas.santiago.da.silva@gmail.com',0,'Douglas Santiago','RW',1,'da Silva',6,15,0,'Douglas Silva',18,0),(296,1,'1992-04-16','david.pazos.fuentes@gmail.com',5,'David','RW',0,'Pazos Fuentes',6,15,0,'David Pazos',20,0),(297,3,'1993-08-04','thierry.henrique.petreca@gmail.com',2,'Thierry Henrique','RW',0,'Petreça',2,15,0,'Thierry',23,0),(298,5,'1994-09-01','mateo.vazquez.seoane@gmail.com',0,'Mateo','RW',0,'Vázquez Seoane',0,15,0,'Koeman',31,0),(299,7,'1990-11-25','ruben.lemos.marino@gmail.com',6,'Rubén','RW',1,'Lemos Mariño',7,15,0,'Rubi Lemos',77,0),(300,8,'1992-10-09','weverton.nascimento.morais@gmail.com',0,'Weverton Nascimento','PIV',0,'Morais',5,15,0,'Garrincha',9,0),(301,10,'1993-12-14','santiago.martin.rufino@gmail.com',0,'Santiago Martín','PIV',0,'Rufino',6,15,0,'Rufino',11,0),(302,1,'1991-02-22','adrian.rodriguez.mosteirin@gmail.com',12,'Adrián','PIV',0,'Rodríguez Mosteirín',14,15,0,'Pirata',17,0),(303,3,'1992-01-17','guilherme.dos.santos.cruz@gmail.com',0,'Guilherme Dos Santos','PIV',0,'Cruz',0,15,0,'Guilherme',97,0),(304,5,'1990-11-30','attos.cristhian.mendes@gmail.com',0,'Attos Cristhian','DF',0,'Mendes da Silva',4,15,0,'Attos',12,0),(305,7,'1994-03-19','matheus.caique.alencar@gmail.com',0,'Matheus Caíque','PIV',0,'De Alencar Souza',2,15,0,'Matheus Preá',25,0),(307,0,'1990-02-20','daniel.alvarez@gmail.com',0,'Daniel','GK',0,'Álvarez Sueldo',3,16,0,'Dani Álvarez',15,55),(308,0,'1992-09-10','ivan.bernad@gmail.com',0,'Iván','GK',0,'Bernad Calavia',3,16,0,'Iván Bernad',25,55),(309,0,'1993-12-05','david.marin@gmail.com',0,'David','GK',0,'Marín Carreras',0,16,0,'David Marín',28,89),(310,0,'1988-07-22','nacho.gomez@gmail.com',4,'Ignacio','DF',0,'Gómez Benitez',5,16,0,'Nacho Gómez',21,0),(311,0,'1991-04-11','carlos.garcia@gmail.com',3,'Carlos','DF',0,'García Naranjo',4,16,0,'Carlos García',22,0),(312,0,'1990-08-18','adri.ortego@gmail.com',9,'Adrián','DF',0,'Ortego López',4,16,0,'Adri Ortego',24,0),(313,0,'1987-01-30','oscar.villanueva@gmail.com',2,'Óscar','RW',0,'Villanueva Guerrero',0,16,0,'Óscar Villanueva',4,0),(314,0,'1995-03-10','jorge.espin@gmail.com',0,'Jorge','RW',0,'Espín Meseguer',3,16,0,'Espín',5,0),(315,0,'1993-05-12','juan.brasesco@gmail.com',0,'Juan Bautista','RW',1,'Brasesco',2,16,0,'Brasesco',6,0),(316,0,'1994-02-18','alberto.ines@gmail.com',4,'Alberto','RW',0,'Inés Medina',3,16,0,'Alberto Inés',7,0),(317,0,'1992-11-25','adrian.rivera@gmail.com',4,'Adrián','RW',0,'Rivera González',7,16,0,'Rivera',9,0),(318,0,'1993-09-29','izan.gonzalo@gmail.com',0,'Izan','RW',0,'Gonzalo Esteban',0,16,0,'Izan',16,0),(319,0,'1996-01-14','raul.lopez@gmail.com',1,'Raúl','RW',0,'López Rivas',1,16,0,'Raúl López',17,0),(320,0,'1995-06-23','miguel.lahoz@gmail.com',0,'Miguel','RW',0,'Lahoz Baquero',1,16,0,'Miguel',19,0),(321,0,'1994-10-05','juan.relanico@gmail.com',0,'Juan','RW',0,'Relancio Higueras',0,16,0,'Juanchi',20,0),(322,0,'1992-07-02','saul.vinals@gmail.com',0,'Saúl','RW',0,'Viñals Mallen',0,16,0,'Saúl',99,0),(323,0,'1990-12-17','fernando.torres@gmail.com',11,'Fernando','PIV',1,'Torres Moreno',9,16,0,'Nando Torres',3,0),(324,0,'1991-08-04','ricardo.felipe@gmail.com',4,'Ricardo','PIV',1,'Felipe Pérez',6,16,0,'Richi Felipe',8,0),(325,0,'1990-11-14','tomas.pescio@gmail.com',6,'Tomás','PIV',0,'Pescio',4,16,0,'Tomás Pescio',11,0);
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  `gf` int(11) DEFAULT NULL,
  `gc` int(11) DEFAULT NULL,
  `pg` int(11) DEFAULT NULL,
  `pe` int(11) DEFAULT NULL,
  `pp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
INSERT INTO `teams` VALUES (1,'Pozo Murcia',6,35,72,53,9,8,6),(2,'Servigroup Peñíscola',1,51,101,70,16,3,4),(3,'Barça',2,49,84,61,16,1,6),(4,'Movistar Inter',3,46,89,57,14,4,5),(5,'Islas Baleares Palma Futsal',4,46,81,65,13,7,3),(6,'Jimbee Cartagena Costa Cálida',5,38,63,51,10,8,5),(7,'Jaén Paraíso Interior',7,33,79,71,9,6,8),(8,'Industrias Santa Coloma',8,33,66,67,9,6,8),(9,'Osasuna Magna Xota',9,31,68,63,8,7,8),(10,'Quesos Hidalgo Manzanares FS',10,29,57,71,8,5,10),(11,'Burela FS',11,23,43,57,6,5,12),(12,'Córdoba Patrimonio Humanidad',12,23,56,78,6,5,12),(13,'Viña Albali Valdepeñas',13,23,57,80,6,5,12),(14,'ATP Ilum. Tudelano Ribera Navarra',14,21,65,76,6,3,14),(15,'Noia Portus Apostoli',15,19,40,60,4,7,12),(16,'WANAPIX AD Sala 10',16,10,49,90,2,4,17),(17,'Real Betis Futsal',1,52,121,72,16,4,5),(18,'Family Cash Alzira FS',2,50,93,60,15,5,5),(19,'Málaga Ciudad Redonda FS',3,30,38,30,9,3,4),(20,'O Parrulo Ferrol',4,28,35,33,8,4,4),(21,'Inagroup El Ejido Futsal',5,26,33,34,7,5,4),(22,'Levante UD FS',6,24,31,36,6,6,4),(23,'Entrerríos Zaragoza',8,20,27,40,5,5,6),(24,'Sala 5 Martorell',9,18,26,42,5,3,8),(25,'UD Ibiza Gasifred',10,16,24,45,4,4,8),(26,'Zambú CFS Pinatar',11,14,22,48,4,2,10),(27,'CD Melistar FS',12,12,20,50,3,3,10),(28,'CD Leganés',13,10,18,52,3,1,12),(29,'Unión África Ceutí',14,8,15,55,2,2,12),(30,'Sporting FS La Nucía',15,6,13,58,1,3,12),(31,'ADC Lugo Sala',16,4,10,60,1,1,14),(32,'Barça Atletic',7,4,10,60,1,1,14);
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams_tournaments`
--

DROP TABLE IF EXISTS `teams_tournaments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teams_tournaments` (
  `tournament_id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  KEY `FKn2bkst9dp1wdy7heg8nt2ao10` (`team_id`),
  KEY `FK24s810yw359703h577ys1iiw4` (`tournament_id`),
  CONSTRAINT `FK24s810yw359703h577ys1iiw4` FOREIGN KEY (`tournament_id`) REFERENCES `tournaments` (`id`),
  CONSTRAINT `FKn2bkst9dp1wdy7heg8nt2ao10` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams_tournaments`
--

LOCK TABLES `teams_tournaments` WRITE;
/*!40000 ALTER TABLE `teams_tournaments` DISABLE KEYS */;
INSERT INTO `teams_tournaments` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(2,17),(2,18),(2,19),(2,20),(2,21),(2,22),(2,23),(2,24),(2,25),(2,26),(2,27),(2,28),(2,29),(2,30),(2,31),(2,32);
/*!40000 ALTER TABLE `teams_tournaments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tournaments`
--

DROP TABLE IF EXISTS `tournaments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tournaments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `season` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tournaments`
--

LOCK TABLES `tournaments` WRITE;
/*!40000 ALTER TABLE `tournaments` DISABLE KEYS */;
INSERT INTO `tournaments` VALUES (1,'Spain','LNFS','1ª División','2024/2025'),(2,'Spain','LNFS','2ª División','2024/2025');
/*!40000 ALTER TABLE `tournaments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainings`
--

DROP TABLE IF EXISTS `trainings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trainings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `objective` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainings`
--

LOCK TABLES `trainings` WRITE;
/*!40000 ALTER TABLE `trainings` DISABLE KEYS */;
INSERT INTO `trainings` VALUES (1,'2025-04-10','niara','en el primer estudiodwfoajignoiugjd.fkgj dfgblzidfku ghseriothglruwqehkvjheso\nègwkñdnlkesijgefwhbtdshliod`rowerhjkbtsrjler','improve distance runned, velocity and tecnique');
/*!40000 ALTER TABLE `trainings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `team_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `users_teams_FK` (`team_id`),
  CONSTRAINT `users_teams_FK` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'alex@gmail.com','Alejandro','alex','Martínez','alex_coach',1),(2,'david@gmail.com','David','david','Rodríguez','david_coach',2),(3,'borja@gmail.com','Borja','borja','Martín','borja_coach',3),(4,'victor@gmail.com','Victor','victor','Ruiz','victor_coach',4),(5,'marina@gmail.com','Marina','marina','Moro','marina_coach',5),(6,'manuel@gmail.com','Manuel','manuel','Gómez','manuel_coach',6),(7,'jorge@gmail.com','Jorge','jorge','De la Vega','jorge_coach',7),(8,'nacho@gmail.com','Nacho','nacho','González','nacho_coach',8),(9,'aurelio@gmail.com','Aurelio','aurelio','Arribas','aurelio_coach',9),(10,'revilla@gmail.com','Revilla','revilla','Correjes','revilla_coach',10),(11,'iñaki@gmail.com','Iñaki','iñaki','Heras','iñaki_coach',11),(12,'marta@gmail.com','Marta','marta','Iglesias','marta_coach',12),(13,'maria@gmail.com','María','maria','Veracruz','maria_coach',13),(14,'jesus@gmail.com','Jesús','jesus','Gutierrez','jesus_coach',14),(15,'alberto@gmail.com','Alberto','alberto','Caramanzana','alberto_coach',15),(16,'manolo@gmail.com','Manolo','manolo','Álvarez','manolo_coach',16),(17,'juan.perez@example.com','Juan','juan','Pérez','juan_coach',17),(18,'carlos.gomez@example.com','Carlos','carlos','Gómez','carlos_coach',18),(19,'ana.martinez@example.com','Ana','ana','Martínez','ana_coach',19),(20,'luis.lopez@example.com','Luis','luis','Lopez','luis_coach',20),(21,'marta.fernandez@example.com','Marta','marta','Fernández','marta_coach',21),(22,'jose.rodriguez@example.com','José','jose','Rodríguez','jose_coach',22),(23,'laura.hernandez@example.com','Laura','laura','Hernández','laura_coach',23),(24,'javier.gonzalez@example.com','Javier','javier','González','javier_coach',24),(25,'raquel.martin@example.com','Raquel','raquel','Martín','raquel_coach',25),(26,'david.sanchez@example.com','David','david','Sánchez','david_coach',26),(27,'elena.garcia@example.com','Elena','elena','García','elena_coach',27),(28,'pedro.vazquez@example.com','Pedro','pedro','Vázquez','pedro_coach',28),(29,'isabel.jimenez@example.com','Isabel','isabel','Jiménez','isabel_coach',29),(30,'manuel.ruiz@example.com','Manuel','manuel','Ruiz','manuel_coach',30),(31,'beatriz.perez@example.com','Beatriz','beatriz','Pérez','bea_coach',31),(32,'fernando.castro@example.com','Fernando','fernando','Castro','fer_coach',32);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'coach_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-13 15:09:13
