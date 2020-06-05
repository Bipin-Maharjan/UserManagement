
CREATE DATABASE IF NOT EXISTS `ums` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ums`;


CREATE TABLE `history` (
  `history_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(30) NOT NULL,
  `remark` varchar(100) NOT NULL,
  `ip_address` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(80) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `role` varchar(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` bigint(10) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `question1` varchar(100) NOT NULL,
  `answer1` varchar(100) NOT NULL,
  `question2` varchar(100) NOT NULL,
  `answer2` varchar(100) NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `status` varchar(10) NOT NULL DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `history`
  ADD PRIMARY KEY (`history_id`),
  ADD KEY `username_fk` (`username`);

ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

ALTER TABLE `history`
  MODIFY `history_id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `history`
  ADD CONSTRAINT `username_fk` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
  
INSERT INTO `user` (`username`, `password`, `first_name`, `last_name`, `role`, `email`, `phone_number`, `date_of_birth`, `gender`, `question1`, `answer1`, `question2`, `answer2`, `profile_picture`, `bio`, `status`) VALUES
('admin', '240BE518FABD2724DDB6F04EEB1DA5967448D7E831C08C8FA822809F74C720A9', 'Admin', 'Manager', 'admin', 'admin@gmail.com', 9807654321, '2020-04-01', 'male', 'What was the name of your first pet?', 'No pet', 'What is your dream job?', 'IT', NULL, NULL, 'active');
  
COMMIT;
