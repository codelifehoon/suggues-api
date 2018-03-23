CREATE DATABASE `sampleDB` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `address` (
	`user_no` int(11) NOT NULL,
	`addr1` varchar(45) DEFAULT NULL,
	`add2` varchar(45) DEFAULT NULL,
	`add_detail` varchar(45) DEFAULT NULL,
	PRIMARY KEY (`user_no`),
	UNIQUE KEY `user_no_UNIQUE` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `content_alarm` (
	`content_alarm_no` int(11) NOT NULL AUTO_INCREMENT,
	`event_content_no` int(11) NOT NULL,
	`user_no` int(11) NOT NULL,
	`use_yn` varchar(10) NOT NULL,
	`create_no` int(11) NOT NULL,
	`create_dt` datetime NOT NULL,
	`update_no` int(11) DEFAULT NULL,
	`update_dt` datetime DEFAULT NULL,
	PRIMARY KEY (`content_alarm_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8

CREATE TABLE `content_thumb_up` (
	`content_thumbup_no` int(11) NOT NULL AUTO_INCREMENT,
	`create_dt` datetime DEFAULT NULL,
	`create_no` int(11) DEFAULT NULL,
	`event_content_no` int(11) DEFAULT NULL,
	`update_dt` datetime DEFAULT NULL,
	`update_no` int(11) DEFAULT NULL,
	`use_yn` varchar(255) DEFAULT NULL,
	`user_no` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`content_thumbup_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `content_thumbup` (
	`content_thumbup_no` int(11) NOT NULL AUTO_INCREMENT,
	`event_content_no` int(11) NOT NULL,
	`user_no` int(11) NOT NULL,
	`use_yn` varchar(10) NOT NULL,
	`create_no` int(11) NOT NULL,
	`create_dt` datetime NOT NULL,
	`update_no` int(11) DEFAULT NULL,
	`update_dt` datetime DEFAULT NULL,
	PRIMARY KEY (`content_thumbup_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `event_content` (
	`event_content_no` int(11) NOT NULL AUTO_INCREMENT,
	`user_hash` varchar(100) NOT NULL,
	`title` varchar(1000) NOT NULL,
	`event_desc` varchar(8000) NOT NULL,
	`event_start` datetime DEFAULT NULL,
	`event_end` datetime DEFAULT NULL,
	`repeat_kind` varchar(5) NOT NULL,
	`path` varchar(1000) DEFAULT NULL,
	`tags` varchar(1000) DEFAULT NULL,
	`stat` varchar(2) NOT NULL,
	`create_dt` datetime NOT NULL,
	`create_no` int(11) NOT NULL,
	`update_dt` datetime DEFAULT NULL,
	`update_no` int(11) DEFAULT NULL,
	PRIMARY KEY (`event_content_no`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8

CREATE TABLE `event_location` (
	`event_location_no` int(11) NOT NULL AUTO_INCREMENT,
	`event_content_no` int(11) NOT NULL,
	`longitude` decimal(11,8) NOT NULL,
	`latitude` decimal(11,8) NOT NULL,
	`address_dtls` varchar(8000) DEFAULT NULL,
	`address` varchar(4000) DEFAULT NULL,
	`stat` varchar(2) DEFAULT NULL,
	`create_dt` datetime DEFAULT NULL,
	`create_no` int(11) DEFAULT NULL,
	`update_dt` datetime DEFAULT NULL,
	`update_no` int(11) DEFAULT NULL,
	`use_yn` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`event_location_no`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8

CREATE TABLE `hobby` (
	`hobby_seq` int(11) NOT NULL AUTO_INCREMENT,
	`hobby_desc` varchar(255) DEFAULT NULL,
	`hobby_kind` varchar(255) DEFAULT NULL,
	`user_no` int(11) DEFAULT NULL,
	PRIMARY KEY (`hobby_seq`),
	KEY `FKni1m5tv1j57o0iykyoi0dj7ex` (`user_no`),
	CONSTRAINT `FKni1m5tv1j57o0iykyoi0dj7ex` FOREIGN KEY (`user_no`) REFERENCES `address` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `user` (
	`user_no` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` varchar(45) NOT NULL,
	`user_nm` varchar(45) NOT NULL,
	`user_desc` varchar(2000) DEFAULT NULL,
	`create_no` int(11) NOT NULL,
	`create_dt` datetime NOT NULL,
	`user_provider` varchar(100) NOT NULL DEFAULT 'none',
	`user_photos` varchar(1000) DEFAULT NULL,
	`user_stat` varchar(5) NOT NULL DEFAULT '00',
	`user_hash` varchar(100) NOT NULL,
	`update_no` int(11) DEFAULT NULL,
	`update_dt` datetime DEFAULT NULL,
	PRIMARY KEY (`user_no`),
	UNIQUE KEY `user_no_UNIQUE` (`user_no`),
	UNIQUE KEY `user_user_hash_uindex` (`user_hash`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8


