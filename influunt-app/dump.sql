-- MySQL dump 10.13  Distrib 5.6.19, for osx10.9 (x86_64)
--
-- Host: localhost    Database: influuntdev
-- ------------------------------------------------------
-- Server version	5.6.19

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
-- Table structure for table `aneis`
--

DROP TABLE IF EXISTS `aneis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aneis` (
  `id` varchar(40) NOT NULL,
  `ativo` tinyint(1) NOT NULL DEFAULT '0',
  `descricao` varchar(255) DEFAULT NULL,
  `posicao` int(11) DEFAULT NULL,
  `numero_smee` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `quantidade_grupo_pedestre` int(11) DEFAULT NULL,
  `quantidade_grupo_veicular` int(11) DEFAULT NULL,
  `quantidade_detector_pedestre` int(11) DEFAULT NULL,
  `quantidade_detector_veicular` int(11) DEFAULT NULL,
  `controlador_id` bigint(20) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_aneis_controlador_id` (`controlador_id`),
  CONSTRAINT `fk_aneis_controlador_id` FOREIGN KEY (`controlador_id`) REFERENCES `controladores` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aneis`
--

LOCK TABLES `aneis` WRITE;
/*!40000 ALTER TABLE `aneis` DISABLE KEYS */;
INSERT INTO `aneis` VALUES ('0ddc8e58-0d88-4c2c-9cfc-2efea677bdf5',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,4,'2016-06-19 18:18:45.471000','2016-06-19 18:18:45.471000'),('112718da-1b28-47b4-9bb9-2aa8d6590588',1,'controlador 5 - anel 1',1,NULL,-19.9809899,-44.03494549999999,1,1,1,1,5,'2016-06-19 20:19:24.932000','2016-06-19 20:19:24.932000'),('13eae657-cc1c-4ef2-bd18-92f5bc098d1a',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,6,'2016-06-19 20:26:31.090000','2016-06-19 20:26:31.090000'),('2de5bfdd-1e54-47a0-bdc9-8efc528744a3',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,1,'2016-06-19 16:14:20.791000','2016-06-19 16:14:20.791000'),('31ad7971-8985-4fac-9579-489474d71005',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,1,'2016-06-19 16:14:20.790000','2016-06-19 16:14:20.790000'),('37101b9b-6b6f-4077-9c1b-a2cfc2f1bc2d',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,2,'2016-06-19 16:16:46.413000','2016-06-19 16:16:46.413000'),('37909475-ff5c-47e8-a264-2b828f01db81',1,'asda',1,NULL,-23.5484875099175,-46.66314125061035,1,1,1,1,1,'2016-06-19 16:14:20.763000','2016-06-19 16:14:20.763000'),('37a4906c-faf9-464b-8a6e-26c005bcea42',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,3,'2016-06-19 16:22:06.580000','2016-06-19 16:22:06.580000'),('52a3ecf1-e982-4052-9c1d-7dbed3f120b3',1,'controlador 6 - anel 2',2,NULL,-19.9513211,-43.921468600000026,1,1,1,1,6,'2016-06-19 20:26:31.076000','2016-06-19 20:26:31.076000'),('549f6113-1492-4a97-a1fc-63a9bd17f969',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,5,'2016-06-19 20:19:24.982000','2016-06-19 20:19:24.982000'),('58d7f543-782f-45d4-afba-5e4c5ef8f759',1,'controlador 5 - anel 2',2,NULL,-19.9809899,-44.03494549999999,1,1,1,1,5,'2016-06-19 20:19:24.955000','2016-06-19 20:19:24.955000'),('5a7748d5-bdd3-43e4-9402-bdc73b730e48',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,3,'2016-06-19 16:22:06.579000','2016-06-19 16:22:06.579000'),('678eeafd-f7c1-4c68-9645-15fbc72f8430',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,2,'2016-06-19 16:16:46.411000','2016-06-19 16:16:46.411000'),('75ea8571-6992-4e43-93b8-15056bc02037',1,'12321',1,'asdfas',-19.9809899,-44.03494549999999,1,1,1,1,10,'2016-06-19 21:43:03.316000','2016-06-19 21:47:56.484000'),('b092789f-d30f-46e0-8335-0a3460d8992c',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,4,'2016-06-19 18:18:45.473000','2016-06-19 18:18:45.473000'),('b306ecec-0b5b-4ad0-a0bb-edc03d922a08',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,6,'2016-06-19 20:26:31.092000','2016-06-19 20:26:31.092000'),('b77540a2-b9c6-4bc6-8544-0b4e7344dc73',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,10,'2016-06-19 21:47:56.495000','2016-06-19 21:47:56.495000'),('bacf4dd4-b207-4e82-82b5-895e78fd5e15',1,'dfds',1,NULL,-23.549864453561085,-46.66451454162598,1,1,1,1,3,'2016-06-19 16:22:06.555000','2016-06-19 16:22:06.555000'),('c55f51c5-2224-4611-8dac-a58bf4118e2e',1,'controlador 6 - anel 1',1,NULL,-19.9809899,-44.03494549999999,1,1,1,1,6,'2016-06-19 20:26:31.058000','2016-06-19 20:26:31.058000'),('c8288ea2-2e1c-49f3-8bcc-76d90c4b8696',1,'123',2,NULL,-23.55076929438831,-46.66451454162598,1,1,1,1,3,'2016-06-19 16:22:06.570000','2016-06-19 16:22:06.570000'),('ccfd13cd-964b-4014-937a-eef6046276dc',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,2,'2016-06-19 16:16:46.410000','2016-06-19 16:16:46.410000'),('cd145b8d-b59b-4478-97e9-a51036f30c69',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,10,'2016-06-19 21:47:56.490000','2016-06-19 21:47:56.490000'),('cfd25ec2-7060-4b27-9b88-c1fddc95281f',1,'asd',1,NULL,-19.9809899,-44.03494549999999,1,1,1,1,4,'2016-06-19 18:18:45.448000','2016-06-19 18:18:45.448000'),('eb18eb2e-8a02-44ca-83c9-fb26c3b38ac9',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,4,'2016-06-19 18:18:45.475000','2016-06-19 18:18:45.475000'),('eeb5b9c9-9034-4163-848f-800ad4270d86',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,5,'2016-06-19 20:19:24.980000','2016-06-19 20:19:24.980000'),('f5c4351e-d513-46eb-9544-9ca180d03d35',1,'asdsadsas',2,'sdf',-23.55088731664574,-46.662025451660156,1,1,1,1,10,'2016-06-19 21:43:03.336000','2016-06-19 21:47:56.497000'),('fba082e7-65ce-461e-8c0b-5df8a27812a2',1,'asd',1,NULL,-23.550729953612294,-46.664557456970215,2,2,2,2,2,'2016-06-19 16:16:46.386000','2016-06-19 16:16:46.386000'),('fd1e98cf-0144-4a90-80f5-79991c2780dd',0,NULL,NULL,NULL,NULL,NULL,0,0,0,0,1,'2016-06-19 16:14:20.793000','2016-06-19 16:14:20.793000');
/*!40000 ALTER TABLE `aneis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `areas`
--

DROP TABLE IF EXISTS `areas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `areas` (
  `id` varchar(40) NOT NULL,
  `descricao` int(11) DEFAULT NULL,
  `cidade_id` varchar(40) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_areas_cidade_id` (`cidade_id`),
  CONSTRAINT `fk_areas_cidade_id` FOREIGN KEY (`cidade_id`) REFERENCES `cidades` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areas`
--

LOCK TABLES `areas` WRITE;
/*!40000 ALTER TABLE `areas` DISABLE KEYS */;
INSERT INTO `areas` VALUES ('3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5',1,'584a3016-2739-4ea5-802b-9c2e702772cc','2016-06-19 16:10:56.113000','2016-06-19 16:10:56.113000'),('cdbb59a8-d4e1-4bcf-9cdf-062e0324d3e3',2,'584a3016-2739-4ea5-802b-9c2e702772cc','2016-06-19 16:11:29.799000','2016-06-19 16:11:29.799000'),('eed88053-1ef3-4669-933a-a970039dfabd',4,'fae14141-0407-4820-b25a-77a7373f38e7','2016-06-19 20:39:14.634000','2016-06-19 20:39:14.634000'),('f746b854-e9a4-4e4c-8031-b4f03ad344dd',3,'fae14141-0407-4820-b25a-77a7373f38e7','2016-06-19 20:38:49.213000','2016-06-19 20:38:49.213000');
/*!40000 ALTER TABLE `areas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cidades`
--

DROP TABLE IF EXISTS `cidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cidades` (
  `id` varchar(40) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cidades`
--

LOCK TABLES `cidades` WRITE;
/*!40000 ALTER TABLE `cidades` DISABLE KEYS */;
INSERT INTO `cidades` VALUES ('584a3016-2739-4ea5-802b-9c2e702772cc','Belo Horizonte','2016-06-19 16:10:28.050000','2016-06-19 16:10:28.050000'),('fae14141-0407-4820-b25a-77a7373f38e7','betim','2016-06-19 20:38:37.293000','2016-06-19 20:38:37.293000');
/*!40000 ALTER TABLE `cidades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracao_controladores`
--

DROP TABLE IF EXISTS `configuracao_controladores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuracao_controladores` (
  `id` varchar(40) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `limite_estagio` int(11) DEFAULT NULL,
  `limite_grupo_semaforico` int(11) DEFAULT NULL,
  `limite_anel` int(11) DEFAULT NULL,
  `limite_detector_pedestre` int(11) DEFAULT NULL,
  `limite_detector_veicular` int(11) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracao_controladores`
--

LOCK TABLES `configuracao_controladores` WRITE;
/*!40000 ALTER TABLE `configuracao_controladores` DISABLE KEYS */;
INSERT INTO `configuracao_controladores` VALUES ('2ae77195-ee3e-4f88-b4b2-5d49e70f6780','opa 1',4,4,4,4,8,'2016-06-19 16:11:54.649000','2016-06-19 16:11:54.649000');
/*!40000 ALTER TABLE `configuracao_controladores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `controladores`
--

DROP TABLE IF EXISTS `controladores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `controladores` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `numero_smee` varchar(255) NOT NULL,
  `numero_smeeconjugado1` varchar(255) DEFAULT NULL,
  `numero_smeeconjugado2` varchar(255) DEFAULT NULL,
  `numero_smeeconjugado3` varchar(255) DEFAULT NULL,
  `firmware` varchar(255) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `modelo_id` varchar(40) NOT NULL,
  `area_id` varchar(40) NOT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_controladores_modelo_id` (`modelo_id`),
  KEY `ix_controladores_area_id` (`area_id`),
  CONSTRAINT `fk_controladores_area_id` FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`),
  CONSTRAINT `fk_controladores_modelo_id` FOREIGN KEY (`modelo_id`) REFERENCES `modelo_controladores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `controladores`
--

LOCK TABLES `controladores` WRITE;
/*!40000 ALTER TABLE `controladores` DISABLE KEYS */;
INSERT INTO `controladores` VALUES (1,'opa 1','oa 1',NULL,NULL,NULL,'123',-23.55187083133668,-46.664085388183594,'77b632c9-565e-4fd4-b8df-a51115412518','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 16:12:41.250000','2016-06-19 16:14:20.760000'),(2,'sdfs','sfds',NULL,NULL,NULL,'123',-23.55037588609829,-46.66159629821777,'77b632c9-565e-4fd4-b8df-a51115412518','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 16:16:06.803000','2016-06-19 16:16:46.383000'),(3,'testes','tste',NULL,NULL,NULL,'12',-23.550100499594734,-46.66399955749512,'77b632c9-565e-4fd4-b8df-a51115412518','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 16:20:44.838000','2016-06-19 16:22:06.553000'),(4,'asdfa','asfda',NULL,NULL,NULL,'asdf',-19.978108132797374,-44.02361154556274,'77b632c9-565e-4fd4-b8df-a51115412518','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 16:23:29.462000','2016-06-19 18:18:45.445000'),(5,'controlador 5','controlador 5',NULL,NULL,NULL,'controlador 5',-19.9809899,-44.03494549999999,'77b632c9-565e-4fd4-b8df-a51115412518','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 20:16:26.590000','2016-06-19 20:19:24.923000'),(6,'controlador 6','controlador 6',NULL,NULL,NULL,'controlador 6',-19.9809899,-44.03494549999999,'77b632c9-565e-4fd4-b8df-a51115412518','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 20:25:05.308000','2016-06-19 20:26:31.057000'),(7,'controlador betim','asdfa',NULL,NULL,NULL,'asdf',-19.9416708,-43.9459415,'77b632c9-565e-4fd4-b8df-a51115412518','eed88053-1ef3-4669-933a-a970039dfabd','2016-06-19 20:41:19.741000','2016-06-19 20:45:27.233000'),(8,'test','test',NULL,NULL,NULL,'123',-19.9513211,-43.921468600000026,'125b88c3-f536-4f2f-a112-f284f7cf250e','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 21:09:42.050000','2016-06-19 21:16:30.009000'),(9,'asdfaa','sdf',NULL,NULL,NULL,'asdf',-23.55037588609829,-46.66593074798584,'125b88c3-f536-4f2f-a112-f284f7cf250e','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 21:19:55.252000','2016-06-19 21:19:55.252000'),(10,'teste','test',NULL,NULL,NULL,'asdf',-19.9513211,-43.921468600000026,'125b88c3-f536-4f2f-a112-f284f7cf250e','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 21:41:36.278000','2016-06-19 21:47:56.483000'),(11,'teste','test',NULL,NULL,NULL,'teste',-19.9513211,-43.921468600000026,'125b88c3-f536-4f2f-a112-f284f7cf250e','3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-20 10:54:39.158000','2016-06-20 14:54:46.823000');
/*!40000 ALTER TABLE `controladores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detectores`
--

DROP TABLE IF EXISTS `detectores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detectores` (
  `id` varchar(40) NOT NULL,
  `tipo` varchar(8) DEFAULT NULL,
  `anel_id` varchar(40) DEFAULT NULL,
  `controlador_id` bigint(20) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_detectores_anel_id` (`anel_id`),
  KEY `ix_detectores_controlador_id` (`controlador_id`),
  CONSTRAINT `fk_detectores_anel_id` FOREIGN KEY (`anel_id`) REFERENCES `aneis` (`id`),
  CONSTRAINT `fk_detectores_controlador_id` FOREIGN KEY (`controlador_id`) REFERENCES `controladores` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detectores`
--

LOCK TABLES `detectores` WRITE;
/*!40000 ALTER TABLE `detectores` DISABLE KEYS */;
INSERT INTO `detectores` VALUES ('0b7d7e97-36c6-47ca-80f9-9c35ec78fb46','PEDESTRE','112718da-1b28-47b4-9bb9-2aa8d6590588',5,'2016-06-19 20:19:24.938000','2016-06-19 20:19:24.938000'),('15354578-f95d-47bf-8f5d-f80063b0cc5f','VEICULAR','58d7f543-782f-45d4-afba-5e4c5ef8f759',5,'2016-06-19 20:19:24.958000','2016-06-19 20:19:24.958000'),('19bbc2b1-d40b-4679-bc1b-bd95eddeb33e','VEICULAR','bacf4dd4-b207-4e82-82b5-895e78fd5e15',3,'2016-06-19 16:22:06.559000','2016-06-19 16:22:06.559000'),('24830d74-54f0-4ee7-a274-96a3df38d9b3','VEICULAR','75ea8571-6992-4e43-93b8-15056bc02037',10,'2016-06-19 21:43:03.321000','2016-06-19 21:43:03.321000'),('2d991312-c58f-4a66-9916-518701eb6f18','VEICULAR','fba082e7-65ce-461e-8c0b-5df8a27812a2',2,'2016-06-19 16:16:46.392000','2016-06-19 16:16:46.392000'),('35e17ee9-0cf7-4979-a0bd-4d6407a44040','VEICULAR','fba082e7-65ce-461e-8c0b-5df8a27812a2',2,'2016-06-19 16:16:46.394000','2016-06-19 16:16:46.394000'),('43b25808-469e-4056-afdd-80eec6330d5c','PEDESTRE','bacf4dd4-b207-4e82-82b5-895e78fd5e15',3,'2016-06-19 16:22:06.557000','2016-06-19 16:22:06.557000'),('5072ff3c-e99b-4b60-8449-7842a1e07454','PEDESTRE','58d7f543-782f-45d4-afba-5e4c5ef8f759',5,'2016-06-19 20:19:24.957000','2016-06-19 20:19:24.957000'),('51fd36d1-20ad-4266-a42d-765aa9d4f121','PEDESTRE','37909475-ff5c-47e8-a264-2b828f01db81',1,'2016-06-19 16:14:20.765000','2016-06-19 16:14:20.765000'),('53996f7d-48cc-426b-9d7a-20bf88fe10a5','VEICULAR','37909475-ff5c-47e8-a264-2b828f01db81',1,'2016-06-19 16:14:20.767000','2016-06-19 16:14:20.767000'),('69f941a4-7c80-4a05-86c7-ea0587b69cd3','PEDESTRE','c55f51c5-2224-4611-8dac-a58bf4118e2e',6,'2016-06-19 20:26:31.059000','2016-06-19 20:26:31.059000'),('740cda4e-9de6-4ca8-b4bd-cca2a12753f0','PEDESTRE','cfd25ec2-7060-4b27-9b88-c1fddc95281f',4,'2016-06-19 18:18:45.450000','2016-06-19 18:18:45.450000'),('76172bff-3554-4f02-9856-e7be8721809c','VEICULAR','c55f51c5-2224-4611-8dac-a58bf4118e2e',6,'2016-06-19 20:26:31.060000','2016-06-19 20:26:31.060000'),('7762523d-eb14-4247-9e6f-02648e940235','PEDESTRE','52a3ecf1-e982-4052-9c1d-7dbed3f120b3',6,'2016-06-19 20:26:31.077000','2016-06-19 20:26:31.077000'),('83092fb6-8068-4f0a-b690-a76d0a46fd6d','VEICULAR','52a3ecf1-e982-4052-9c1d-7dbed3f120b3',6,'2016-06-19 20:26:31.078000','2016-06-19 20:26:31.078000'),('87c935b7-4e91-4664-9b9c-e469f0f4236f','PEDESTRE','fba082e7-65ce-461e-8c0b-5df8a27812a2',2,'2016-06-19 16:16:46.388000','2016-06-19 16:16:46.388000'),('920eaec4-5276-42e6-94f1-0bd681be173a','VEICULAR','f5c4351e-d513-46eb-9544-9ca180d03d35',10,'2016-06-19 21:43:03.340000','2016-06-19 21:43:03.340000'),('aacad826-4714-4f10-829a-66d8dcc983ff','PEDESTRE','75ea8571-6992-4e43-93b8-15056bc02037',10,'2016-06-19 21:43:03.318000','2016-06-19 21:43:03.318000'),('aec4b6f5-1f2a-4ee0-bf20-c8cc49cdd14c','PEDESTRE','fba082e7-65ce-461e-8c0b-5df8a27812a2',2,'2016-06-19 16:16:46.390000','2016-06-19 16:16:46.390000'),('b4e41615-1011-4be3-b5dc-802ff7527917','VEICULAR','112718da-1b28-47b4-9bb9-2aa8d6590588',5,'2016-06-19 20:19:24.940000','2016-06-19 20:19:24.940000'),('bdc79a96-1af7-458a-9c9e-a2e2a98c7583','PEDESTRE','c8288ea2-2e1c-49f3-8bcc-76d90c4b8696',3,'2016-06-19 16:22:06.571000','2016-06-19 16:22:06.571000'),('c23376cc-130c-4a95-b01c-0921c35be72e','PEDESTRE','f5c4351e-d513-46eb-9544-9ca180d03d35',10,'2016-06-19 21:43:03.338000','2016-06-19 21:43:03.338000'),('c731b600-2981-464e-8463-c6d26c181910','VEICULAR','c8288ea2-2e1c-49f3-8bcc-76d90c4b8696',3,'2016-06-19 16:22:06.572000','2016-06-19 16:22:06.572000'),('f001ff88-eabe-41a6-8747-6a36bcfe2c66','VEICULAR','cfd25ec2-7060-4b27-9b88-c1fddc95281f',4,'2016-06-19 18:18:45.451000','2016-06-19 18:18:45.451000');
/*!40000 ALTER TABLE `detectores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estagios`
--

DROP TABLE IF EXISTS `estagios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estagios` (
  `id` varchar(40) NOT NULL,
  `imagem_id` varchar(40) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `tempo_maximo_permanencia` int(11) DEFAULT NULL,
  `demanda_prioritaria` tinyint(1) DEFAULT '0',
  `movimento_id` varchar(40) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_estagios_imagem_id` (`imagem_id`),
  UNIQUE KEY `uq_estagios_movimento_id` (`movimento_id`),
  CONSTRAINT `fk_estagios_imagem_id` FOREIGN KEY (`imagem_id`) REFERENCES `imagens` (`id`),
  CONSTRAINT `fk_estagios_movimento_id` FOREIGN KEY (`movimento_id`) REFERENCES `movimentos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estagios`
--

LOCK TABLES `estagios` WRITE;
/*!40000 ALTER TABLE `estagios` DISABLE KEYS */;
INSERT INTO `estagios` VALUES ('25fcad32-fd8d-4243-babb-04194888e00e',NULL,NULL,NULL,0,'315132e0-cbcf-4e5d-9354-4e04fdb3eec2','2016-06-19 18:18:45.468000','2016-06-19 18:18:45.468000'),('35e2a0fb-f1f6-4db9-87d9-2ace067aef45',NULL,NULL,NULL,0,'b28fe944-c14e-4ef1-a031-500d459b9e84','2016-06-19 20:26:31.089000','2016-06-19 20:26:31.089000'),('3abb8a17-f499-4a19-b8ae-50ecc926447f',NULL,NULL,NULL,0,'33c5a6b1-c569-4b7c-abae-cf175a9cd4b9','2016-06-19 21:43:03.350000','2016-06-19 21:47:56.500000'),('3e4d189c-d8f7-4db4-a31e-0b5d11145276',NULL,NULL,NULL,0,'ab9a7106-749b-4e57-bfc5-0e6806d64e7d','2016-06-19 16:16:46.407000','2016-06-19 16:16:46.407000'),('5f612d82-f2e8-46e8-a599-88366de68fc9',NULL,NULL,NULL,0,'afd23df3-c7a5-4f15-8986-b71ad5d565f9','2016-06-19 16:22:06.577000','2016-06-19 16:22:06.577000'),('67c1e572-cb71-49b6-a426-a387d5ecd641',NULL,NULL,NULL,0,'5edc74f8-08d7-47d8-8706-d07ae7ee37ba','2016-06-19 16:22:06.566000','2016-06-19 16:22:06.566000'),('71689833-243b-4dd1-b65c-371d0e4b2255',NULL,NULL,NULL,0,'d413288b-49d8-4f39-b240-b89eb42e09a8','2016-06-19 16:22:06.579000','2016-06-19 16:22:06.579000'),('8d8e19d5-6853-4598-9a47-da5f9e5d9585',NULL,NULL,NULL,0,'c0e7aa68-2f89-44d2-9c31-ce2beaecc85f','2016-06-19 16:22:06.568000','2016-06-19 16:22:06.568000'),('a099f060-0c5a-4352-96c1-31cbbe9c1014',NULL,NULL,NULL,0,'097c532c-3c7f-4294-b3c7-41c9cca40a63','2016-06-19 18:18:45.462000','2016-06-19 18:18:45.462000'),('a0eb5342-04ea-4ba2-ab1e-ae5e93e518f8',NULL,NULL,NULL,0,'e3a160c7-6d11-49a3-996f-1676d20c1124','2016-06-19 20:19:24.953000','2016-06-19 20:19:24.953000'),('a3b5be80-4a6c-4252-83d2-aca26e7816ac',NULL,NULL,NULL,0,'b79c4a37-e585-4f6b-8790-a915d0173270','2016-06-19 20:19:24.978000','2016-06-19 20:19:24.978000'),('bc3a18ac-061b-45f7-9619-24bda2104730',NULL,NULL,NULL,0,'96034be4-14d1-4616-81b5-874564b4808d','2016-06-19 20:26:31.073000','2016-06-19 20:26:31.073000'),('c2902225-efcf-43b2-a587-e20fc02cb440',NULL,NULL,NULL,0,'e24ff439-82b1-4b71-bf8a-ff56e347ac61','2016-06-19 16:14:20.787000','2016-06-19 16:14:20.787000'),('c5215263-bad8-4a39-ba7d-ee1c9f6a60b6',NULL,NULL,NULL,0,'18d86583-b8cd-47a3-b992-226732e85437','2016-06-19 20:19:24.970000','2016-06-19 20:19:24.970000'),('c8aff7b4-ef2b-43fe-81a1-3e13b35f7e4f',NULL,NULL,NULL,0,'afac8d65-2c9e-45e4-82a6-ba24abd51332','2016-06-19 21:43:03.334000','2016-06-19 21:47:56.489000'),('c9b298b6-6cfa-4df8-91a9-03b7c0851d07',NULL,NULL,NULL,0,'c023e6b5-edef-4b13-958d-97f65d48fd25','2016-06-19 21:43:03.347000','2016-06-19 21:47:56.503000'),('d2a524a5-edbc-44af-b26a-96445e6342d7',NULL,NULL,NULL,0,'3ef8c486-7cee-4504-88cf-d06dcbde959c','2016-06-19 20:19:24.949000','2016-06-19 20:19:24.949000'),('e14df088-8937-4bee-b695-1d9e41c739f6',NULL,NULL,NULL,0,'8762d2a8-e2dc-49e2-ad8d-b219a9ef7ac3','2016-06-19 21:43:03.331000','2016-06-19 21:47:56.488000'),('ee135911-9de5-46ea-9384-668c41c5ba4a',NULL,NULL,NULL,0,'731f4e42-fa46-4b81-a593-66d2dbf6a6a8','2016-06-19 16:14:20.781000','2016-06-19 16:14:20.781000'),('ee9b9c0e-cdd4-4c57-908a-c28b3debe639',NULL,NULL,NULL,0,'ba4db181-3e37-4a4c-aad4-775d0562d852','2016-06-19 20:26:31.086000','2016-06-19 20:26:31.086000'),('f528f9b4-73d8-4404-9cf8-09be68be0a3a',NULL,NULL,NULL,0,'cd796d68-d92b-4a67-9967-8c38e015eac3','2016-06-19 16:16:46.401000','2016-06-19 16:16:46.401000'),('fc053a14-e0a4-4724-9e0b-bdec422bcf7c',NULL,NULL,NULL,0,'0eec330c-791e-4f2b-a367-1b48c1fe61e2','2016-06-19 20:26:31.075000','2016-06-19 20:26:31.075000');
/*!40000 ALTER TABLE `estagios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estagios_grupos_semaforicos`
--

DROP TABLE IF EXISTS `estagios_grupos_semaforicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estagios_grupos_semaforicos` (
  `id` varchar(40) NOT NULL,
  `ativo` tinyint(1) NOT NULL DEFAULT '0',
  `estagio_id` varchar(40) NOT NULL,
  `grupo_semaforico_id` varchar(40) NOT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_estagios_grupos_semaforicos_estagio_id` (`estagio_id`),
  KEY `ix_estagios_grupos_semaforicos_grupo_semaforico_id` (`grupo_semaforico_id`),
  CONSTRAINT `fk_estagios_grupos_semaforicos_estagio_id` FOREIGN KEY (`estagio_id`) REFERENCES `estagios` (`id`),
  CONSTRAINT `fk_estagios_grupos_semaforicos_grupo_semaforico_id` FOREIGN KEY (`grupo_semaforico_id`) REFERENCES `grupos_semaforicos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estagios_grupos_semaforicos`
--

LOCK TABLES `estagios_grupos_semaforicos` WRITE;
/*!40000 ALTER TABLE `estagios_grupos_semaforicos` DISABLE KEYS */;
/*!40000 ALTER TABLE `estagios_grupos_semaforicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fabricantes`
--

DROP TABLE IF EXISTS `fabricantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fabricantes` (
  `id` varchar(40) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fabricantes`
--

LOCK TABLES `fabricantes` WRITE;
/*!40000 ALTER TABLE `fabricantes` DISABLE KEYS */;
INSERT INTO `fabricantes` VALUES ('1748a734-e9b4-478d-9786-04a60a6a65d8','fabricante 2','2016-06-19 21:16:13.347000','2016-06-19 21:16:13.347000'),('c581e0ce-5243-4756-b8af-e87dfb636554','fab 1','2016-06-19 16:12:03.453000','2016-06-19 16:12:11.580000');
/*!40000 ALTER TABLE `fabricantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupos_semaforicos`
--

DROP TABLE IF EXISTS `grupos_semaforicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupos_semaforicos` (
  `id` varchar(40) NOT NULL,
  `tipo` varchar(8) DEFAULT NULL,
  `anel_id` varchar(40) DEFAULT NULL,
  `controlador_id` bigint(20) DEFAULT NULL,
  `grupo_conflito_id` varchar(40) DEFAULT NULL,
  `posicao` int(11) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_grupos_semaforicos_anel_id` (`anel_id`),
  KEY `ix_grupos_semaforicos_controlador_id` (`controlador_id`),
  KEY `ix_grupos_semaforicos_grupo_conflito_id` (`grupo_conflito_id`),
  CONSTRAINT `fk_grupos_semaforicos_anel_id` FOREIGN KEY (`anel_id`) REFERENCES `aneis` (`id`),
  CONSTRAINT `fk_grupos_semaforicos_controlador_id` FOREIGN KEY (`controlador_id`) REFERENCES `controladores` (`id`),
  CONSTRAINT `fk_grupos_semaforicos_grupo_conflito_id` FOREIGN KEY (`grupo_conflito_id`) REFERENCES `grupos_semaforicos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupos_semaforicos`
--

LOCK TABLES `grupos_semaforicos` WRITE;
/*!40000 ALTER TABLE `grupos_semaforicos` DISABLE KEYS */;
INSERT INTO `grupos_semaforicos` VALUES ('1ef7453f-9b18-453c-8c33-5561d283010b',NULL,'c55f51c5-2224-4611-8dac-a58bf4118e2e',6,NULL,2,'2016-06-19 20:26:31.068000','2016-06-19 20:26:31.068000'),('455167f7-870a-4848-a999-0b5daf648c85',NULL,'75ea8571-6992-4e43-93b8-15056bc02037',10,NULL,1,'2016-06-19 21:43:03.327000','2016-06-19 21:47:56.485000'),('4e0c1f44-60b4-43ad-b49c-d826585c11a5',NULL,'52a3ecf1-e982-4052-9c1d-7dbed3f120b3',6,NULL,3,'2016-06-19 20:26:31.080000','2016-06-19 20:26:31.080000'),('60ff11bb-6e3e-4fa7-b0ce-116f177a047f',NULL,'c55f51c5-2224-4611-8dac-a58bf4118e2e',6,NULL,1,'2016-06-19 20:26:31.066000','2016-06-19 20:26:31.066000'),('6fa71896-1f83-4f10-9f65-798acd9cba56',NULL,'58d7f543-782f-45d4-afba-5e4c5ef8f759',5,NULL,4,'2016-06-19 20:19:24.965000','2016-06-19 20:19:24.965000'),('6fa92bce-1cf5-422d-8755-77dec48aafdb',NULL,'112718da-1b28-47b4-9bb9-2aa8d6590588',5,NULL,2,'2016-06-19 20:19:24.945000','2016-06-19 20:19:24.945000'),('8540e605-6507-4759-b468-f7da6c1dad6d',NULL,'52a3ecf1-e982-4052-9c1d-7dbed3f120b3',6,NULL,4,'2016-06-19 20:26:31.081000','2016-06-19 20:26:31.081000'),('905c634b-b926-49c6-91da-a6e8ebdf587b',NULL,'112718da-1b28-47b4-9bb9-2aa8d6590588',5,NULL,1,'2016-06-19 20:19:24.942000','2016-06-19 20:19:24.942000'),('b6e430c8-d4f8-40f5-89ca-4a20db940d96',NULL,'f5c4351e-d513-46eb-9544-9ca180d03d35',10,NULL,3,'2016-06-19 21:43:03.342000','2016-06-19 21:47:56.498000'),('de897cfd-09bf-4fbe-8cba-eabdedeeb272',NULL,'75ea8571-6992-4e43-93b8-15056bc02037',10,NULL,2,'2016-06-19 21:43:03.328000','2016-06-19 21:47:56.486000'),('ef20aac5-26a3-41ff-b161-f55b03204e8d',NULL,'f5c4351e-d513-46eb-9544-9ca180d03d35',10,NULL,4,'2016-06-19 21:43:03.343000','2016-06-19 21:47:56.498000'),('efa6cf1d-8924-4969-9c5a-8e20564b2391',NULL,'58d7f543-782f-45d4-afba-5e4c5ef8f759',5,NULL,3,'2016-06-19 20:19:24.959000','2016-06-19 20:19:24.959000');
/*!40000 ALTER TABLE `grupos_semaforicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagens`
--

DROP TABLE IF EXISTS `imagens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imagens` (
  `id` varchar(40) NOT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagens`
--

LOCK TABLES `imagens` WRITE;
/*!40000 ALTER TABLE `imagens` DISABLE KEYS */;
INSERT INTO `imagens` VALUES ('05dde388-c1e8-48ca-8033-35f9fd423a2e','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 18:25:37.063000','2016-06-19 18:25:37.063000'),('09e46a9e-57a3-49c3-b01a-79b9600d449d','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 18:18:41.785000','2016-06-19 18:18:41.785000'),('13ec9703-cc91-4a5c-98c0-6ad34a594f3b','12963901_10154615163486840_8455352346796368737_n.jpg','image/jpeg','2016-06-19 20:18:34.345000','2016-06-19 20:18:34.345000'),('1bb5fef2-8f15-410b-9d60-19e2a4cf8692','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 18:25:37.060000','2016-06-19 18:25:37.060000'),('1d327dbb-024f-4332-b29b-d10c539a8f1f','Screen Shot 2016-06-18 at 12.44.55.png','image/png','2016-06-19 20:17:54.866000','2016-06-19 20:17:54.866000'),('1d830fc4-9d6b-4723-9e12-688c264b9ce0','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 16:13:00.852000','2016-06-19 16:13:00.852000'),('1dc29708-0233-44e2-aeae-db4bbedd1e01','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 18:12:54.227000','2016-06-19 18:12:54.227000'),('22dbf94b-c526-4d61-bf9e-b7d4f8dbe4d7','Screen Shot 2016-06-18 at 12.44.55.png','image/png','2016-06-19 18:13:47.284000','2016-06-19 18:13:47.284000'),('24d5bcd4-7129-49b6-986a-7284a7cda3da','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 18:18:41.781000','2016-06-19 18:18:41.781000'),('29037b0e-8b9a-4acd-b580-1121699dceae','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 16:21:03.409000','2016-06-19 16:21:03.409000'),('46b74419-52ed-4755-90f1-09ab2806551e','12963901_10154615163486840_8455352346796368737_n.jpg','image/jpeg','2016-06-19 20:25:42.724000','2016-06-19 20:25:42.724000'),('56f634df-151b-4747-959d-d8856dd659c9','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 20:18:34.347000','2016-06-19 20:18:34.347000'),('5b2c691c-a721-44ff-b05b-12fd9bee5bac','Screen Shot 2016-06-18 at 12.44.55.png','image/png','2016-06-19 18:25:46.713000','2016-06-19 18:25:46.713000'),('5c9ce63d-0d21-433d-87b6-56862f214a1d','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 18:15:04.492000','2016-06-19 18:15:04.492000'),('611560c5-8226-4438-8cb7-72178e53a09f','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 18:15:04.493000','2016-06-19 18:15:04.493000'),('61ef0eef-e3e9-4106-b37c-c09b40868e38','Screen Shot 2016-06-18 at 12.44.55.png','image/png','2016-06-19 21:42:37.728000','2016-06-19 21:42:37.728000'),('6b028124-d033-40a3-bdc4-a8f7f2dcdedb','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 18:25:41.335000','2016-06-19 18:25:41.335000'),('75e357ad-28f3-41df-8a46-2d162b722044','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 18:13:47.275000','2016-06-19 18:13:47.275000'),('768d594d-c57d-4fe7-a597-2ce59523a09b','Screen Shot 2016-06-18 at 12.44.55.png','image/png','2016-06-19 18:12:54.234000','2016-06-19 18:12:54.234000'),('7e86a499-e7b5-475b-bf44-ecaa8cdd401d','Screen Shot 2016-06-13 at 23.45.32.png','image/png','2016-06-19 20:17:54.950000','2016-06-19 20:17:54.950000'),('8125757b-7672-4242-91be-73760bc5f186','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 21:50:42.869000','2016-06-19 21:50:42.869000'),('878b390e-df32-472c-b954-3abdce6236b5','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 16:16:24.596000','2016-06-19 16:16:24.596000'),('88dcc8fc-f336-44c1-8523-e53fd3172054','12963901_10154615163486840_8455352346796368737_n.jpg','image/jpeg','2016-06-19 16:13:00.852000','2016-06-19 16:13:00.852000'),('9aa6fdd5-97aa-4b15-b22a-2fdafbc4d864','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 18:25:41.335000','2016-06-19 18:25:41.335000'),('a60e774e-7699-47de-8ce2-b6feed5feffc','Screen Shot 2016-06-13 at 23.45.32.png','image/png','2016-06-19 21:41:56.990000','2016-06-19 21:41:56.990000'),('ad5c0aa5-aa61-4661-8285-e50c1042d545','12963901_10154615163486840_8455352346796368737_n.jpg','image/jpeg','2016-06-19 16:16:24.597000','2016-06-19 16:16:24.597000'),('b0fb6d79-d085-425d-9121-cfb739d1879e','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 21:42:37.720000','2016-06-19 21:42:37.720000'),('b563484d-45a0-4156-aadf-c8284c834935','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 16:23:51.849000','2016-06-19 16:23:51.849000'),('b8b9274e-9b2f-4f98-8c9f-ae2b8cffe8ec','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 20:26:17.252000','2016-06-19 20:26:17.252000'),('ba472cb5-1ca4-43d4-b8ea-fd29d955b5d3','12963901_10154615163486840_8455352346796368737_n.jpg','image/jpeg','2016-06-19 16:21:03.403000','2016-06-19 16:21:03.403000'),('c814b1cd-d899-45ed-8b6d-b4fdd23504d9','Screen Shot 2016-06-13 at 23.45.32.png','image/png','2016-06-19 20:26:17.284000','2016-06-19 20:26:17.284000'),('d90eb827-9047-4a84-abe4-50742c1377ab','12963901_10154615163486840_8455352346796368737_n.jpg','image/jpeg','2016-06-19 16:14:47.649000','2016-06-19 16:14:47.649000'),('dea740a7-5562-4abd-b887-609c4cb2976d','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 18:12:18.299000','2016-06-19 18:12:18.299000'),('e62594d2-19c8-489f-8b76-65f9ca5ab797','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 21:41:56.936000','2016-06-19 21:41:56.936000'),('e972fc1d-139a-476d-acb2-3c8375f47981','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 20:25:42.723000','2016-06-19 20:25:42.723000'),('eaab7500-cc91-4c80-beed-d306c951a4a4','Screen Shot 2016-06-18 at 12.44.55.png','image/png','2016-06-19 16:21:15.792000','2016-06-19 16:21:15.792000'),('f196c8c7-1281-48eb-a323-b73e1d7c9bed','Screen Shot 2016-06-18 at 12.44.55.png','image/png','2016-06-19 18:12:18.299000','2016-06-19 18:12:18.299000'),('f1f2aef5-d5ec-46f6-b373-cdb4a0eac787','Screen Shot 2016-06-17 at 15.08.57.png','image/png','2016-06-19 16:21:15.787000','2016-06-19 16:21:15.787000'),('f6e2149f-2bf0-4665-9b16-c160dc654ae0','12321359_986438248070903_1173574894423312875_n.jpg','image/jpeg','2016-06-19 16:14:43.150000','2016-06-19 16:14:43.150000'),('f9f09eab-6a7d-4546-b75e-71b3862de470','Screen Shot 2016-06-13 at 23.45.32.png','image/png','2016-06-19 18:25:46.744000','2016-06-19 18:25:46.744000'),('fd2d02cf-82b6-4dfe-bfdb-a1be217addb1','Screen Shot 2016-06-18 at 12.44.55.png','image/png','2016-06-19 16:23:51.850000','2016-06-19 16:23:51.850000');
/*!40000 ALTER TABLE `imagens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `limite_area`
--

DROP TABLE IF EXISTS `limite_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `limite_area` (
  `id` varchar(40) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `area_id` varchar(40) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_limite_area_area_id` (`area_id`),
  CONSTRAINT `fk_limite_area_area_id` FOREIGN KEY (`area_id`) REFERENCES `areas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `limite_area`
--

LOCK TABLES `limite_area` WRITE;
/*!40000 ALTER TABLE `limite_area` DISABLE KEYS */;
INSERT INTO `limite_area` VALUES ('5a53316c-9130-470f-97a1-7c7e5ee97e78',22,22,'3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 16:10:56.116000','2016-06-19 16:10:56.116000'),('5c86d9cc-1918-432a-81a0-777066f81267',12,21,'3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 16:10:56.114000','2016-06-19 16:10:56.114000'),('7c7940c3-460d-460f-b9dd-474d51a8d9a5',12,21,'eed88053-1ef3-4669-933a-a970039dfabd','2016-06-19 20:39:14.635000','2016-06-19 20:39:14.635000'),('91c9fb91-d9d5-487e-b463-27dd2da81b25',21,12,'3d5da070-8dcb-4fed-a8b2-ca8aa8d3c5f5','2016-06-19 16:10:56.115000','2016-06-19 16:10:56.115000');
/*!40000 ALTER TABLE `limite_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modelo_controladores`
--

DROP TABLE IF EXISTS `modelo_controladores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modelo_controladores` (
  `id` varchar(40) NOT NULL,
  `fabricante_id` varchar(40) DEFAULT NULL,
  `configuracao_id` varchar(40) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_modelo_controladores_fabricante_id` (`fabricante_id`),
  KEY `ix_modelo_controladores_configuracao_id` (`configuracao_id`),
  CONSTRAINT `fk_modelo_controladores_configuracao_id` FOREIGN KEY (`configuracao_id`) REFERENCES `configuracao_controladores` (`id`),
  CONSTRAINT `fk_modelo_controladores_fabricante_id` FOREIGN KEY (`fabricante_id`) REFERENCES `fabricantes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modelo_controladores`
--

LOCK TABLES `modelo_controladores` WRITE;
/*!40000 ALTER TABLE `modelo_controladores` DISABLE KEYS */;
INSERT INTO `modelo_controladores` VALUES ('125b88c3-f536-4f2f-a112-f284f7cf250e','1748a734-e9b4-478d-9786-04a60a6a65d8','2ae77195-ee3e-4f88-b4b2-5d49e70f6780','fabricante 2','2016-06-19 21:16:13.349000','2016-06-19 21:16:13.349000'),('77b632c9-565e-4fd4-b8df-a51115412518','c581e0ce-5243-4756-b8af-e87dfb636554','2ae77195-ee3e-4f88-b4b2-5d49e70f6780','opa 1','2016-06-19 16:12:11.581000','2016-06-19 16:12:11.581000');
/*!40000 ALTER TABLE `modelo_controladores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimentos`
--

DROP TABLE IF EXISTS `movimentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimentos` (
  `id` varchar(40) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `imagem_id` varchar(40) DEFAULT NULL,
  `controlador_id` bigint(20) DEFAULT NULL,
  `anel_id` varchar(40) DEFAULT NULL,
  `data_criacao` datetime(6) NOT NULL,
  `data_atualizacao` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_movimentos_imagem_id` (`imagem_id`),
  KEY `ix_movimentos_controlador_id` (`controlador_id`),
  KEY `ix_movimentos_anel_id` (`anel_id`),
  CONSTRAINT `fk_movimentos_anel_id` FOREIGN KEY (`anel_id`) REFERENCES `aneis` (`id`),
  CONSTRAINT `fk_movimentos_controlador_id` FOREIGN KEY (`controlador_id`) REFERENCES `controladores` (`id`),
  CONSTRAINT `fk_movimentos_imagem_id` FOREIGN KEY (`imagem_id`) REFERENCES `imagens` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentos`
--

LOCK TABLES `movimentos` WRITE;
/*!40000 ALTER TABLE `movimentos` DISABLE KEYS */;
INSERT INTO `movimentos` VALUES ('097c532c-3c7f-4294-b3c7-41c9cca40a63',NULL,'09e46a9e-57a3-49c3-b01a-79b9600d449d',NULL,'cfd25ec2-7060-4b27-9b88-c1fddc95281f','2016-06-19 18:18:45.458000','2016-06-19 18:18:45.458000'),('0eec330c-791e-4f2b-a367-1b48c1fe61e2',NULL,'46b74419-52ed-4755-90f1-09ab2806551e',NULL,'c55f51c5-2224-4611-8dac-a58bf4118e2e','2016-06-19 20:26:31.074000','2016-06-19 20:26:31.074000'),('18d86583-b8cd-47a3-b992-226732e85437',NULL,'13ec9703-cc91-4a5c-98c0-6ad34a594f3b',NULL,'58d7f543-782f-45d4-afba-5e4c5ef8f759','2016-06-19 20:19:24.968000','2016-06-19 20:19:24.968000'),('315132e0-cbcf-4e5d-9354-4e04fdb3eec2',NULL,'24d5bcd4-7129-49b6-986a-7284a7cda3da',NULL,'cfd25ec2-7060-4b27-9b88-c1fddc95281f','2016-06-19 18:18:45.467000','2016-06-19 18:18:45.467000'),('33c5a6b1-c569-4b7c-abae-cf175a9cd4b9',NULL,'61ef0eef-e3e9-4106-b37c-c09b40868e38',NULL,'f5c4351e-d513-46eb-9544-9ca180d03d35','2016-06-19 21:43:03.348000','2016-06-19 21:47:56.499000'),('3ef8c486-7cee-4504-88cf-d06dcbde959c',NULL,'1d327dbb-024f-4332-b29b-d10c539a8f1f',NULL,'112718da-1b28-47b4-9bb9-2aa8d6590588','2016-06-19 20:19:24.946000','2016-06-19 20:19:24.946000'),('5edc74f8-08d7-47d8-8706-d07ae7ee37ba',NULL,'ba472cb5-1ca4-43d4-b8ea-fd29d955b5d3',NULL,'bacf4dd4-b207-4e82-82b5-895e78fd5e15','2016-06-19 16:22:06.564000','2016-06-19 16:22:06.564000'),('731f4e42-fa46-4b81-a593-66d2dbf6a6a8',NULL,'1d830fc4-9d6b-4723-9e12-688c264b9ce0',NULL,'37909475-ff5c-47e8-a264-2b828f01db81','2016-06-19 16:14:20.779000','2016-06-19 16:14:20.779000'),('8762d2a8-e2dc-49e2-ad8d-b219a9ef7ac3',NULL,'e62594d2-19c8-489f-8b76-65f9ca5ab797',NULL,'75ea8571-6992-4e43-93b8-15056bc02037','2016-06-19 21:43:03.330000','2016-06-19 21:47:56.487000'),('96034be4-14d1-4616-81b5-874564b4808d',NULL,'e972fc1d-139a-476d-acb2-3c8375f47981',NULL,'c55f51c5-2224-4611-8dac-a58bf4118e2e','2016-06-19 20:26:31.069000','2016-06-19 20:26:31.069000'),('ab9a7106-749b-4e57-bfc5-0e6806d64e7d',NULL,'ad5c0aa5-aa61-4661-8285-e50c1042d545',NULL,'fba082e7-65ce-461e-8c0b-5df8a27812a2','2016-06-19 16:16:46.406000','2016-06-19 16:16:46.406000'),('afac8d65-2c9e-45e4-82a6-ba24abd51332',NULL,'a60e774e-7699-47de-8ce2-b6feed5feffc',NULL,'75ea8571-6992-4e43-93b8-15056bc02037','2016-06-19 21:43:03.333000','2016-06-19 21:47:56.489000'),('afd23df3-c7a5-4f15-8986-b71ad5d565f9',NULL,'f1f2aef5-d5ec-46f6-b373-cdb4a0eac787',NULL,'c8288ea2-2e1c-49f3-8bcc-76d90c4b8696','2016-06-19 16:22:06.576000','2016-06-19 16:22:06.576000'),('b28fe944-c14e-4ef1-a031-500d459b9e84',NULL,'c814b1cd-d899-45ed-8b6d-b4fdd23504d9',NULL,'52a3ecf1-e982-4052-9c1d-7dbed3f120b3','2016-06-19 20:26:31.088000','2016-06-19 20:26:31.088000'),('b79c4a37-e585-4f6b-8790-a915d0173270',NULL,'56f634df-151b-4747-959d-d8856dd659c9',NULL,'58d7f543-782f-45d4-afba-5e4c5ef8f759','2016-06-19 20:19:24.972000','2016-06-19 20:19:24.972000'),('ba4db181-3e37-4a4c-aad4-775d0562d852',NULL,'b8b9274e-9b2f-4f98-8c9f-ae2b8cffe8ec',NULL,'52a3ecf1-e982-4052-9c1d-7dbed3f120b3','2016-06-19 20:26:31.083000','2016-06-19 20:26:31.083000'),('c023e6b5-edef-4b13-958d-97f65d48fd25',NULL,'b0fb6d79-d085-425d-9121-cfb739d1879e',NULL,'f5c4351e-d513-46eb-9544-9ca180d03d35','2016-06-19 21:43:03.345000','2016-06-19 21:47:56.502000'),('c0e7aa68-2f89-44d2-9c31-ce2beaecc85f',NULL,'29037b0e-8b9a-4acd-b580-1121699dceae',NULL,'bacf4dd4-b207-4e82-82b5-895e78fd5e15','2016-06-19 16:22:06.567000','2016-06-19 16:22:06.567000'),('cd796d68-d92b-4a67-9967-8c38e015eac3',NULL,'878b390e-df32-472c-b954-3abdce6236b5',NULL,'fba082e7-65ce-461e-8c0b-5df8a27812a2','2016-06-19 16:16:46.400000','2016-06-19 16:16:46.400000'),('d413288b-49d8-4f39-b240-b89eb42e09a8',NULL,'eaab7500-cc91-4c80-beed-d306c951a4a4',NULL,'c8288ea2-2e1c-49f3-8bcc-76d90c4b8696','2016-06-19 16:22:06.578000','2016-06-19 16:22:06.578000'),('e24ff439-82b1-4b71-bf8a-ff56e347ac61',NULL,'88dcc8fc-f336-44c1-8523-e53fd3172054',NULL,'37909475-ff5c-47e8-a264-2b828f01db81','2016-06-19 16:14:20.786000','2016-06-19 16:14:20.786000'),('e3a160c7-6d11-49a3-996f-1676d20c1124',NULL,'7e86a499-e7b5-475b-bf44-ecaa8cdd401d',NULL,'112718da-1b28-47b4-9bb9-2aa8d6590588','2016-06-19 20:19:24.951000','2016-06-19 20:19:24.951000');
/*!40000 ALTER TABLE `movimentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `play_evolutions`
--

DROP TABLE IF EXISTS `play_evolutions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `play_evolutions` (
  `id` int(11) NOT NULL,
  `hash` varchar(255) NOT NULL,
  `applied_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `apply_script` mediumtext,
  `revert_script` mediumtext,
  `state` varchar(255) DEFAULT NULL,
  `last_problem` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `play_evolutions`
--

LOCK TABLES `play_evolutions` WRITE;
/*!40000 ALTER TABLE `play_evolutions` DISABLE KEYS */;
INSERT INTO `play_evolutions` VALUES (1,'82812bbc2de5e487632a8648c5d9b54173fdf089','2016-06-19 19:10:15','create table aneis (\nid                            varchar(40) not null,\nativo                         tinyint(1) default 0 not null,\ndescricao                     varchar(255),\nposicao                       integer,\nnumero_smee                   varchar(255),\nlatitude                      double,\nlongitude                     double,\nquantidade_grupo_pedestre     integer,\nquantidade_grupo_veicular     integer,\nquantidade_detector_pedestre  integer,\nquantidade_detector_veicular  integer,\ncontrolador_id                bigint,\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_aneis primary key (id)\n);\n\ncreate table areas (\nid                            varchar(40) not null,\ndescricao                     integer,\ncidade_id                     varchar(40),\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_areas primary key (id)\n);\n\ncreate table cidades (\nid                            varchar(40) not null,\nnome                          varchar(255) not null,\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_cidades primary key (id)\n);\n\ncreate table configuracao_controladores (\nid                            varchar(40) not null,\ndescricao                     varchar(255),\nlimite_estagio                integer,\nlimite_grupo_semaforico       integer,\nlimite_anel                   integer,\nlimite_detector_pedestre      integer,\nlimite_detector_veicular      integer,\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_configuracao_controladores primary key (id)\n);\n\ncreate table controladores (\nid                            bigint auto_increment not null,\ndescricao                     varchar(255),\nnumero_smee                   varchar(255) not null,\nnumero_smeeconjugado1         varchar(255),\nnumero_smeeconjugado2         varchar(255),\nnumero_smeeconjugado3         varchar(255),\nfirmware                      varchar(255) not null,\nlatitude                      double not null,\nlongitude                     double not null,\nmodelo_id                     varchar(40) not null,\narea_id                       varchar(40) not null,\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_controladores primary key (id)\n);\n\ncreate table detectores (\nid                            varchar(40) not null,\ntipo                          varchar(8),\nanel_id                       varchar(40),\ncontrolador_id                bigint,\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint ck_detectores_tipo check (tipo in (\'VEICULAR\',\'PEDESTRE\')),\nconstraint pk_detectores primary key (id)\n);\n\ncreate table estagios (\nid                            varchar(40) not null,\nimagem_id                     varchar(40),\ndescricao                     varchar(255),\ntempo_maximo_permanencia      integer,\ndemanda_prioritaria           tinyint(1) default 0,\nmovimento_id                  varchar(40),\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint uq_estagios_imagem_id unique (imagem_id),\nconstraint uq_estagios_movimento_id unique (movimento_id),\nconstraint pk_estagios primary key (id)\n);\n\ncreate table estagios_grupos_semaforicos (\nid                            varchar(40) not null,\nativo                         tinyint(1) default 0 not null,\nestagio_id                    varchar(40) not null,\ngrupo_semaforico_id           varchar(40) not null,\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_estagios_grupos_semaforicos primary key (id)\n);\n\ncreate table fabricantes (\nid                            varchar(40) not null,\nnome                          varchar(255),\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_fabricantes primary key (id)\n);\n\ncreate table grupos_semaforicos (\nid                            varchar(40) not null,\ntipo                          varchar(8),\nanel_id                       varchar(40),\ncontrolador_id                bigint,\ngrupo_conflito_id             varchar(40),\nposicao                       integer,\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint ck_grupos_semaforicos_tipo check (tipo in (\'PEDESTRE\',\'VEICULAR\')),\nconstraint pk_grupos_semaforicos primary key (id)\n);\n\ncreate table imagens (\nid                            varchar(40) not null,\nfilename                      varchar(255),\ncontent_type                  varchar(255),\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_imagens primary key (id)\n);\n\ncreate table limite_area (\nid                            varchar(40) not null,\nlatitude                      double,\nlongitude                     double,\narea_id                       varchar(40),\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_limite_area primary key (id)\n);\n\ncreate table modelo_controladores (\nid                            varchar(40) not null,\nfabricante_id                 varchar(40),\nconfiguracao_id               varchar(40),\ndescricao                     varchar(255),\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint pk_modelo_controladores primary key (id)\n);\n\ncreate table movimentos (\nid                            varchar(40) not null,\ndescricao                     varchar(255),\nimagem_id                     varchar(40),\ncontrolador_id                bigint,\nanel_id                       varchar(40),\ndata_criacao                  datetime(6) not null,\ndata_atualizacao              datetime(6) not null,\nconstraint uq_movimentos_imagem_id unique (imagem_id),\nconstraint pk_movimentos primary key (id)\n);\n\nalter table aneis add constraint fk_aneis_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;\ncreate index ix_aneis_controlador_id on aneis (controlador_id);\n\nalter table areas add constraint fk_areas_cidade_id foreign key (cidade_id) references cidades (id) on delete restrict on update restrict;\ncreate index ix_areas_cidade_id on areas (cidade_id);\n\nalter table controladores add constraint fk_controladores_modelo_id foreign key (modelo_id) references modelo_controladores (id) on delete restrict on update restrict;\ncreate index ix_controladores_modelo_id on controladores (modelo_id);\n\nalter table controladores add constraint fk_controladores_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;\ncreate index ix_controladores_area_id on controladores (area_id);\n\nalter table detectores add constraint fk_detectores_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;\ncreate index ix_detectores_anel_id on detectores (anel_id);\n\nalter table detectores add constraint fk_detectores_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;\ncreate index ix_detectores_controlador_id on detectores (controlador_id);\n\nalter table estagios add constraint fk_estagios_imagem_id foreign key (imagem_id) references imagens (id) on delete restrict on update restrict;\n\nalter table estagios add constraint fk_estagios_movimento_id foreign key (movimento_id) references movimentos (id) on delete restrict on update restrict;\n\nalter table estagios_grupos_semaforicos add constraint fk_estagios_grupos_semaforicos_estagio_id foreign key (estagio_id) references estagios (id) on delete restrict on update restrict;\ncreate index ix_estagios_grupos_semaforicos_estagio_id on estagios_grupos_semaforicos (estagio_id);\n\nalter table estagios_grupos_semaforicos add constraint fk_estagios_grupos_semaforicos_grupo_semaforico_id foreign key (grupo_semaforico_id) references grupos_semaforicos (id) on delete restrict on update restrict;\ncreate index ix_estagios_grupos_semaforicos_grupo_semaforico_id on estagios_grupos_semaforicos (grupo_semaforico_id);\n\nalter table grupos_semaforicos add constraint fk_grupos_semaforicos_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;\ncreate index ix_grupos_semaforicos_anel_id on grupos_semaforicos (anel_id);\n\nalter table grupos_semaforicos add constraint fk_grupos_semaforicos_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;\ncreate index ix_grupos_semaforicos_controlador_id on grupos_semaforicos (controlador_id);\n\nalter table grupos_semaforicos add constraint fk_grupos_semaforicos_grupo_conflito_id foreign key (grupo_conflito_id) references grupos_semaforicos (id) on delete restrict on update restrict;\ncreate index ix_grupos_semaforicos_grupo_conflito_id on grupos_semaforicos (grupo_conflito_id);\n\nalter table limite_area add constraint fk_limite_area_area_id foreign key (area_id) references areas (id) on delete restrict on update restrict;\ncreate index ix_limite_area_area_id on limite_area (area_id);\n\nalter table modelo_controladores add constraint fk_modelo_controladores_fabricante_id foreign key (fabricante_id) references fabricantes (id) on delete restrict on update restrict;\ncreate index ix_modelo_controladores_fabricante_id on modelo_controladores (fabricante_id);\n\nalter table modelo_controladores add constraint fk_modelo_controladores_configuracao_id foreign key (configuracao_id) references configuracao_controladores (id) on delete restrict on update restrict;\ncreate index ix_modelo_controladores_configuracao_id on modelo_controladores (configuracao_id);\n\nalter table movimentos add constraint fk_movimentos_imagem_id foreign key (imagem_id) references imagens (id) on delete restrict on update restrict;\n\nalter table movimentos add constraint fk_movimentos_controlador_id foreign key (controlador_id) references controladores (id) on delete restrict on update restrict;\ncreate index ix_movimentos_controlador_id on movimentos (controlador_id);\n\nalter table movimentos add constraint fk_movimentos_anel_id foreign key (anel_id) references aneis (id) on delete restrict on update restrict;\ncreate index ix_movimentos_anel_id on movimentos (anel_id);','alter table aneis drop foreign key fk_aneis_controlador_id;\ndrop index ix_aneis_controlador_id on aneis;\n\nalter table areas drop foreign key fk_areas_cidade_id;\ndrop index ix_areas_cidade_id on areas;\n\nalter table controladores drop foreign key fk_controladores_modelo_id;\ndrop index ix_controladores_modelo_id on controladores;\n\nalter table controladores drop foreign key fk_controladores_area_id;\ndrop index ix_controladores_area_id on controladores;\n\nalter table detectores drop foreign key fk_detectores_anel_id;\ndrop index ix_detectores_anel_id on detectores;\n\nalter table detectores drop foreign key fk_detectores_controlador_id;\ndrop index ix_detectores_controlador_id on detectores;\n\nalter table estagios drop foreign key fk_estagios_imagem_id;\n\nalter table estagios drop foreign key fk_estagios_movimento_id;\n\nalter table estagios_grupos_semaforicos drop foreign key fk_estagios_grupos_semaforicos_estagio_id;\ndrop index ix_estagios_grupos_semaforicos_estagio_id on estagios_grupos_semaforicos;\n\nalter table estagios_grupos_semaforicos drop foreign key fk_estagios_grupos_semaforicos_grupo_semaforico_id;\ndrop index ix_estagios_grupos_semaforicos_grupo_semaforico_id on estagios_grupos_semaforicos;\n\nalter table grupos_semaforicos drop foreign key fk_grupos_semaforicos_anel_id;\ndrop index ix_grupos_semaforicos_anel_id on grupos_semaforicos;\n\nalter table grupos_semaforicos drop foreign key fk_grupos_semaforicos_controlador_id;\ndrop index ix_grupos_semaforicos_controlador_id on grupos_semaforicos;\n\nalter table grupos_semaforicos drop foreign key fk_grupos_semaforicos_grupo_conflito_id;\ndrop index ix_grupos_semaforicos_grupo_conflito_id on grupos_semaforicos;\n\nalter table limite_area drop foreign key fk_limite_area_area_id;\ndrop index ix_limite_area_area_id on limite_area;\n\nalter table modelo_controladores drop foreign key fk_modelo_controladores_fabricante_id;\ndrop index ix_modelo_controladores_fabricante_id on modelo_controladores;\n\nalter table modelo_controladores drop foreign key fk_modelo_controladores_configuracao_id;\ndrop index ix_modelo_controladores_configuracao_id on modelo_controladores;\n\nalter table movimentos drop foreign key fk_movimentos_imagem_id;\n\nalter table movimentos drop foreign key fk_movimentos_controlador_id;\ndrop index ix_movimentos_controlador_id on movimentos;\n\nalter table movimentos drop foreign key fk_movimentos_anel_id;\ndrop index ix_movimentos_anel_id on movimentos;\n\ndrop table if exists aneis;\n\ndrop table if exists areas;\n\ndrop table if exists cidades;\n\ndrop table if exists configuracao_controladores;\n\ndrop table if exists controladores;\n\ndrop table if exists detectores;\n\ndrop table if exists estagios;\n\ndrop table if exists estagios_grupos_semaforicos;\n\ndrop table if exists fabricantes;\n\ndrop table if exists grupos_semaforicos;\n\ndrop table if exists imagens;\n\ndrop table if exists limite_area;\n\ndrop table if exists modelo_controladores;\n\ndrop table if exists movimentos;','applied','');
/*!40000 ALTER TABLE `play_evolutions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-20 15:34:20
