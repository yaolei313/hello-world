CREATE TABLE `users` (
  `id` char(9) NOT NULL,
  `name` varchar(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `sex` char(1) DEFAULT 'M',
  `gravatarMail` varchar(64) DEFAULT NULL,
  `registerTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `study`.`users` (`id`, `name`, `email`, `sex`, `gravatarMail`, `registerTime`) VALUES ('l00190940', 'lvxuguang', 'lvxuguang@huawei.com', 'M', NULL, '2014-09-18 19:19:25');
INSERT INTO `study`.`users` (`id`, `name`, `email`, `sex`, `gravatarMail`, `registerTime`) VALUES ('y00196907', 'yaolei', 'yaolei313@gmail.com', 'M', NULL, '2014-09-18 19:19:25');
