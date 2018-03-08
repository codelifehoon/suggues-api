

# CREATE DATABASE `sampleDB` /*!40100 DEFAULT CHARACTER SET utf8 */;

create table address
(
	user_no int not null
		primary key,
	addr1 varchar(45) null,
	add2 varchar(45) null,
	add_detail varchar(45) null,
	constraint user_no_UNIQUE
		unique (user_no)
)
engine=InnoDB
;

create table hobby
(
	hobby_seq int auto_increment
		primary key,
	hobby_desc varchar(255) null,
	hobby_kind varchar(255) null,
	user_no int null,
	constraint FKni1m5tv1j57o0iykyoi0dj7ex
		foreign key (user_no) references address (user_no)
)
engine=InnoDB
;

create index FKni1m5tv1j57o0iykyoi0dj7ex
	on hobby (user_no)
;
create table user
(
	user_no int auto_increment
		primary key,
	user_id varchar(45) not null,
	user_nm varchar(45) not null,
	user_desc varchar(2000) null,
	create_no int not null,
	create_dt datetime not null,
	user_provider varchar(100) default 'none' not null,
	user_photos varchar(1000) null,
	user_stat varchar(2) default '01' not null,
	user_hash varchar(100) not null,
	constraint user_no_UNIQUE
	unique (user_no)
)
	engine=InnoDB
;



