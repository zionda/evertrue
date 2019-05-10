CREATE TABLE `company_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `state` varchar(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `COMPOSITE` (`company_id`,`state`) /*!80000 INVISIBLE */,
  KEY `COMPANY_IDX` (`company_id`),
  CONSTRAINT `COMPANY_FK` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
