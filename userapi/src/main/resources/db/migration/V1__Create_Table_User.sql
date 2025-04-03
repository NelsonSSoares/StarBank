CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `active` enum('ACTIVE', 'INACTIVE') NOT NULL,
  `agency` varchar(6) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `creation_date`  date  NOT NULL,
  `email` varchar(90) NOT NULL,
  `name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `modified_date` date NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
);

