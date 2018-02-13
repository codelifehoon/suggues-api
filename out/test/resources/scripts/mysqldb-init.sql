

CREATE DATABASE `sampleDB` /*!40100 DEFAULT CHARACTER SET utf8 */;



CREATE TABLE `sampleDB`.`user` (
  `user_no` INT NOT NULL,
  `user_id` VARCHAR(45) NOT NULL,
  `user_nm` VARCHAR(45) NOT NULL,
  `user_desc` BLOB NULL,
  `create_no` INT NOT NULL,
  `create_dt` DATETIME NOT NULL,
  PRIMARY KEY (`user_no`),
  UNIQUE INDEX `user_no_UNIQUE` (`user_no` ASC));

   CREATE TABLE `hobby` (
  `hobby_seq` int(11) NOT NULL AUTO_INCREMENT,
  `hobby_desc` varchar(255) DEFAULT NULL,
  `hobby_kind` varchar(255) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`hobby_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sampleDB`.`address` (

CREATE DATABASE `sampleDB` /*!40100 DEFAULT CHARACTER SET utf8 */;



CREATE TABLE `sampleDB`.`user` (
  `user_no` INT NOT NULL,
  `user_id` VARCHAR(45) NOT NULL,
  `user_nm` VARCHAR(45) NOT NULL,
  `user_desc` BLOB NULL,
  `create_no` INT NOT NULL,
  `create_dt` DATETIME NOT NULL,
  PRIMARY KEY (`user_no`),
  UNIQUE INDEX `user_no_UNIQUE` (`user_no` ASC));

   CREATE TABLE `hobby` (
  `hobby_seq` int(11) NOT NULL AUTO_INCREMENT,
  `hobby_desc` varchar(255) DEFAULT NULL,
  `hobby_kind` varchar(255) DEFAULT NULL,
  `user_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`hobby_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sampleDB`.`address` (
  `user_no` int(11) NOT NULL,
  `addr1` varchar(45) DEFAULT NULL,
  `add2` varchar(45) DEFAULT NULL,
  `add_detail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_no`),
  UNIQUE KEY `user_no_UNIQUE` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(1,'userid1','홍길동1','홍길동1-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(2,'userid2','홍길동2','홍길동2-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(3,'userid3','홍길동3','홍길동3-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(4,'userid4','홍길동4','홍길동4-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(5,'userid5','홍길동5','홍길동5-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(6,'userid6','홍길동6','홍길동6-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(7,'userid7','홍길동7','홍길동7-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(8,'userid8','홍길동8','홍길동8-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(9,'userid9','홍길동9','홍길동9-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(10,'userid10','홍길동10','홍길동10-사용자',0,sysdate());



INSERT INTO `sampleDB`.`address`VALUES(1,'주소1_1','주소1_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(2,'주소2_1','주소2_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(3,'주소3_1','주소3_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(4,'주소4_1','주소4_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(5,'주소5_1','주소5_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(6,'주소6_1','주소6_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(7,'주소7_1','주소7_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(8,'주소8_1','주소8_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(9,'주소9_1','주소9_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(10,'주소10_1','주소10_2','상세주소1');




INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(1,'01','01-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(1,'02','02-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(1,'03','03-상세');

INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(5,'01','01-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(5,'02','02-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(5,'03','03-상세');


INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(10,'01','01-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(10,'02','02-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(10,'03','03-상세');






  `user_no` int(11) NOT NULL,
  `addr1` varchar(45) DEFAULT NULL,
  `add2` varchar(45) DEFAULT NULL,
  `add_detail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_no`),
  UNIQUE KEY `user_no_UNIQUE` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(1,'userid1','홍길동1','홍길동1-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(2,'userid2','홍길동2','홍길동2-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(3,'userid3','홍길동3','홍길동3-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(4,'userid4','홍길동4','홍길동4-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(5,'userid5','홍길동5','홍길동5-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(6,'userid6','홍길동6','홍길동6-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(7,'userid7','홍길동7','홍길동7-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(8,'userid8','홍길동8','홍길동8-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(9,'userid9','홍길동9','홍길동9-사용자',0,sysdate());
INSERT INTO `sampleDB`.`user`  (`user_no`,`user_id`,`user_nm`,`user_desc`,`create_no`,`create_dt`) VALUES(10,'userid10','홍길동10','홍길동10-사용자',0,sysdate());



INSERT INTO `sampleDB`.`address`VALUES(1,'주소1_1','주소1_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(2,'주소2_1','주소2_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(3,'주소3_1','주소3_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(4,'주소4_1','주소4_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(5,'주소5_1','주소5_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(6,'주소6_1','주소6_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(7,'주소7_1','주소7_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(8,'주소8_1','주소8_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(9,'주소9_1','주소9_2','상세주소1');
INSERT INTO `sampleDB`.`address`VALUES(10,'주소10_1','주소10_2','상세주소1');




INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(1,'01','01-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(1,'02','02-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(1,'03','03-상세');

INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(5,'01','01-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(5,'02','02-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(5,'03','03-상세');


INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(10,'01','01-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(10,'02','02-상세');
INSERT INTO `sampleDB`.`hobby` (`user_no`,`hobby_kind`,`hobby_desc`) VALUES(10,'03','03-상세');





