CREATE TABLE `career_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `people_id` int(11) NOT NULL,
  `position_id` int(11) NOT NULL,
  `company_location_id` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `POSITION_FK_idx` (`position_id`),
  KEY `COMPANY_LOCATION_FK_idx` (`company_location_id`),
  KEY `PEOPLE_FK_idx` (`people_id`),
  CONSTRAINT `COMPANY_LOCATION_FK` FOREIGN KEY (`company_location_id`) REFERENCES `company_locations` (`id`),
  CONSTRAINT `PEOPLE_FK` FOREIGN KEY (`people_id`) REFERENCES `people` (`id`),
  CONSTRAINT `POSITION_FK` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15098 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
