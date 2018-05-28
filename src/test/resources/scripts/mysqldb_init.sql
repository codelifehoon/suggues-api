CREATE DATABASE `sampleDB` /*!40100 DEFAULT CHARACTER SET utf8 */;



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

create table content_alarm
(
	content_alarm_no int auto_increment
		primary key,
	event_content_no int not null,
	user_no int not null,
	use_yn varchar(10) not null,
	create_no int not null,
	create_dt datetime not null,
	update_no int null,
	update_dt datetime null
)
	engine=InnoDB
;

create table content_comment
(
	content_comment_no int auto_increment
		primary key,
	comment_desc varchar(8000) null,
	comment_pw varchar(255) null,
	create_dt datetime null,
	create_no int null,
	event_content_no int null,
	stat varchar(255) null,
	update_dt datetime null,
	update_no int null,
	user_no int null
)
	engine=MyISAM
;

create index comment_desc
	on content_comment (comment_desc)
;

create table content_thumb_up
(
	content_thumbup_no int auto_increment
		primary key,
	create_dt datetime null,
	create_no int null,
	event_content_no int null,
	update_dt datetime null,
	update_no int null,
	use_yn varchar(255) null,
	user_no varchar(255) null
)
	engine=InnoDB
;

create table event_content
(
	event_content_no int auto_increment
		primary key,
	user_hash varchar(100) not null,
	title varchar(1000) not null,
	event_desc text not null,
	event_desc_text varchar(8000) null,
	event_desc_thumbnails text null,
	event_start datetime null,
	event_end datetime null,
	path varchar(1000) null,
	stat varchar(2) not null,
	create_dt datetime not null,
	create_no int not null,
	update_dt datetime null,
	update_no int null,
	repeat_kind varchar(5) not null,
	tags varchar(1000) null
)
	engine=MyISAM
;

create index event_desc_text
	on event_content (event_desc_text)
;

create table event_location
(
	event_location_no int auto_increment
		primary key,
	event_content_no int not null,
	longitude decimal(11,8) not null,
	latitude decimal(11,8) not null,
	address_dtls varchar(8000) null,
	address varchar(4000) null,
	stat varchar(2) null,
	create_dt datetime null,
	create_no int null,
	update_dt datetime null,
	update_no int null,
	use_yn varchar(255) null
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
	user_stat varchar(5) default '00' not null,
	user_hash varchar(100) not null,
	update_no int null,
	update_dt datetime null,
	constraint user_no_UNIQUE
	unique (user_no),
	constraint user_user_hash_uindex
	unique (user_hash)
)
	engine=InnoDB
;

create table content_activity
(
	content_activity_no int auto_increment
		primary key,
	activity_code varchar(2) null,
	activity_ref_no int null,
	activity_stat varchar(10) null,
	create_dt date null,
	create_no int null,
	update_dt date null
)
	engine=InnoDB
;

