create database study;

grant all privileges on study.* to 'study'@'%' identified by 'study';

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` char(9) NOT NULL,
  `email` varchar(64) NOT NULL,
  `nickname` varchar(32) NOT NULL,
  `sex` char(1) DEFAULT 'M',
  `gravatar_mail` varchar(64) DEFAULT NULL,
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY(`username`),
  UNIQUE KEY(`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `study`.`users` (`username`, `email`, `nickname`, `sex`, `gravatar_mail`, `register_time`) VALUES 
('l00190940', 'lvxuguang@huawei.com', '老吕', 'M', NULL, '2014-09-18 19:19:25'),
('y00196907', 'yaolei313@gmail.com', '李白', 'M', NULL, '2014-08-08 19:19:25');


drop table if exists posts;
create table posts (
    id int not null auto_increment,
    title varchar(32) not null,
    primary key(id)
);