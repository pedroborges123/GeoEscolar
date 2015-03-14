-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: geoescolar
-- ------------------------------------------------------
-- Server version	5.6.22-log

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
-- Dumping data for table `adm`
--

LOCK TABLES `adm` WRITE;
/*!40000 ALTER TABLE `adm` DISABLE KEYS */;
INSERT INTO `adm` (`id`, `nome`, `email`, `senha`, `ultimoLogin`) VALUES (16,'adm','adm@adm.com','80177534a0c99a7e3645b52f2027a48b',NULL);
/*!40000 ALTER TABLE `adm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `crianca`
--

LOCK TABLES `crianca` WRITE;
/*!40000 ALTER TABLE `crianca` DISABLE KEYS */;
INSERT INTO `crianca` (`id`, `nome`, `idade`, `sexo`, `email`, `telefone`, `transporte`) VALUES (3,'Marcela','12','F','marcela@email.com','null',2),(4,'joao','7','M','joao@email.com','null',2);
/*!40000 ALTER TABLE `crianca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `crianca_localidade`
--

LOCK TABLES `crianca_localidade` WRITE;
/*!40000 ALTER TABLE `crianca_localidade` DISABLE KEYS */;
INSERT INTO `crianca_localidade` (`id`, `crianca`, `localidade`) VALUES (3,3,8),(6,3,1);
/*!40000 ALTER TABLE `crianca_localidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` (`id`, `tipoEndereco`, `CEP`, `logradouro`, `numero`, `complemento`, `localizacao`, `bairro`, `cidade`, `uf`) VALUES (3,NULL,'66535-323','rua joao',41,'perto de tal lugar',NULL,'jc','Vitória','ES'),(4,NULL,'25607-911','AV central',151,'aqui',NULL,'Laranjeiras','Serra','ES'),(7,NULL,'29090-471','rua talmarques',65,'perto de tal lugar',NULL,'jc','Vitória','ES'),(8,NULL,'29090700','rua bem ou mal',451,'atras da parede',NULL,'jabor','Vitória','ES');
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `faleconosco`
--

LOCK TABLES `faleconosco` WRITE;
/*!40000 ALTER TABLE `faleconosco` DISABLE KEYS */;
/*!40000 ALTER TABLE `faleconosco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `itinerario`
--

LOCK TABLES `itinerario` WRITE;
/*!40000 ALTER TABLE `itinerario` DISABLE KEYS */;
INSERT INTO `itinerario` (`id`, `turno`, `horaInicio`, `horaFim`, `transporteescolar`) VALUES (4,'Matutino','06:00','12:00',2),(5,'Vespertino','12:00','17:00',2),(6,'Noturno','17:00','22:00',2);
/*!40000 ALTER TABLE `itinerario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `itinerario_localizacao`
--

LOCK TABLES `itinerario_localizacao` WRITE;
/*!40000 ALTER TABLE `itinerario_localizacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `itinerario_localizacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `localidade`
--

LOCK TABLES `localidade` WRITE;
/*!40000 ALTER TABLE `localidade` DISABLE KEYS */;
INSERT INTO `localidade` (`id`, `tipoLocal`, `horaChegada`, `horaSaida`, `diasUtilizacao`, `endereco`, `descricao`) VALUES (1,'Escola','null','null','null',3,'Contec'),(2,'Escola','null','null','null',4,'UP'),(8,'Casa','11:50','7:30','semana toda',7,'Casa dos Pais'),(9,'Casa','11:50','7:30','dias pares',8,'Casa dos pais');
/*!40000 ALTER TABLE `localidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `localizacao`
--

LOCK TABLES `localizacao` WRITE;
/*!40000 ALTER TABLE `localizacao` DISABLE KEYS */;
INSERT INTO `localizacao` (`id`, `longitude`, `latitude`) VALUES (1,'-40.267029','-20.252986'),(2,'-40.272431','-20.251446'),(3,'-40.274273','-20.239304'),(4,'-40.274963','-20.233700'),(5,'-40.273300','-20.231074'),(6,'-40.270510','-20.232346'),(7,'-40.267638','-20.232686'),(8,'-40.267737','-20.230654'),(9,'-40.269706','-20.227631'),(10,'-40.265920','-20.223039'),(11,'-40.264142','-20.216317'),(12,'-40.258531','-20.210375'),(14,'-40.218841','-20.202975'),(16,'-40.217876','-20.197918');
/*!40000 ALTER TABLE `localizacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `mensagem`
--

LOCK TABLES `mensagem` WRITE;
/*!40000 ALTER TABLE `mensagem` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `motorista`
--

LOCK TABLES `motorista` WRITE;
/*!40000 ALTER TABLE `motorista` DISABLE KEYS */;
INSERT INTO `motorista` (`id`, `nome`, `telefone`, `email`, `senha`, `carteiraMotorista`, `codigoMotorista`) VALUES (2,'marcos','(131) 35846465','marcos@gmail.com','e10adc3949ba59abbe56e057f20f883e','53121',NULL);
/*!40000 ALTER TABLE `motorista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `registrolocalizacao`
--

LOCK TABLES `registrolocalizacao` WRITE;
/*!40000 ALTER TABLE `registrolocalizacao` DISABLE KEYS */;
INSERT INTO `registrolocalizacao` (`id`, `data`, `hora`, `itinerario`, `finalizado`, `localizacao`, `inicio`) VALUES (1,'14/03/2015','6:20',4,0,1,1),(2,'14/03/2015','6:25',4,0,2,0),(3,'14/03/2015','6:30',4,0,3,0),(4,'14/03/2015','6:35',4,0,4,0),(5,'14/03/2015','6:40',4,0,5,0),(6,'14/03/2015','6:45',4,0,6,0),(7,'14/03/2015','6:50',4,0,7,0),(8,'14/03/2015','6:55',4,0,8,0),(9,'14/03/2015','7:00',4,0,9,0),(10,'14/03/2015','7:05',4,0,10,0),(11,'14/03/2015','7:10',4,0,11,0),(12,'14/03/2015','7:15',4,0,12,0),(13,'14/03/2015','7:25',4,0,14,0),(14,'14/03/2015','7:30',4,1,16,0);
/*!40000 ALTER TABLE `registrolocalizacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `responsavel`
--

LOCK TABLES `responsavel` WRITE;
/*!40000 ALTER TABLE `responsavel` DISABLE KEYS */;
INSERT INTO `responsavel` (`id`, `nome`, `email`, `senha`, `tipoResponsavel`, `telefone`, `validado`, `ehprincipal`, `codigo`) VALUES (1,'Joao','joao@email.com','e10adc3949ba59abbe56e057f20f883e','Pai','(024) 65497977',0,1,NULL),(2,'marcia','marcia@email.com','683d5b4d5e86bdc94c072a3d9ff28292','Mae','(545) 64646454',0,1,NULL),(3,'marta','marta@email.com','6243c52372f5c47c240e7d543cf31e97','Mae','(654) 545646514',0,1,NULL),(4,'will','will@email.com','e10adc3949ba59abbe56e057f20f883e','Tios','(455) 47887877',0,1,NULL),(5,'Pedro Henrique Borges','pedroborges123@gmail.com','f856781c108e735165dbc9d9599bf520','Pai','(027) 993117916',0,1,NULL);
/*!40000 ALTER TABLE `responsavel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `responsavel_crianca`
--

LOCK TABLES `responsavel_crianca` WRITE;
/*!40000 ALTER TABLE `responsavel_crianca` DISABLE KEYS */;
INSERT INTO `responsavel_crianca` (`id`, `responsavel`, `crianca`) VALUES (2,3,3),(4,1,3),(5,5,4);
/*!40000 ALTER TABLE `responsavel_crianca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transporteescolar`
--

LOCK TABLES `transporteescolar` WRITE;
/*!40000 ALTER TABLE `transporteescolar` DISABLE KEYS */;
INSERT INTO `transporteescolar` (`id`, `placa`, `capacidade`, `registro`, `marca`, `idMotorista`, `codigoVan`) VALUES (2,'mos-4545',15,'sdfsdf','van',2,NULL);
/*!40000 ALTER TABLE `transporteescolar` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-14 18:19:47
