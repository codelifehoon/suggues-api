
CREATE DATABASE `suggestDB` /*!40100 DEFAULT CHARACTER SET utf8 */;

create table address
(
	user_no int not null
		primary key,
	add2 varchar(255) null,
	add_detail varchar(255) null,
	addr1 varchar(255) null
)
	engine=InnoDB
;

create table content_activity
(
	content_activity_no int auto_increment
		primary key,
	activity_code varchar(255) null,
	activity_ref_no int null,
	activity_stat varchar(255) null,
	create_dt datetime null,
	create_no int null,
	update_dt datetime null,
	update_no int null
)
	engine=InnoDB
;

create table content_alarm
(
	content_alarm_no int auto_increment
		primary key,
	create_dt datetime null,
	create_no int null,
	event_content_no int null,
	update_dt datetime null,
	update_no int null,
	use_yn varchar(255) null,
	user_no int null
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
	engine=InnoDB
;

create table content_provider
(
	seq int auto_increment
		primary key,
	contet_comb json null,
	create_dt datetime null,
	create_no int null,
	provider varchar(255) null,
	stat varchar(255) null,
	update_dt datetime null,
	update_no int null,
	provider_key varchar(255) null,
	provider_modifiedtime varchar(255) null
)
	engine=InnoDB
;

create index content_provider_index2
	on content_provider (stat, provider)
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
	user_no int null
)
	engine=InnoDB
;

create table event_content
(
	event_content_no int auto_increment
		primary key,
	create_dt datetime not null,
	create_no int null,
	event_desc text null,
	event_desc_text text null,
	event_desc_thumbnails varchar(10000) null,
	event_end datetime null,
	event_start datetime null,
	ref_content_key varchar(255) null,
	path varchar(1000) null,
	repeat_kind varchar(255) null,
	stat varchar(255) null,
	tags varchar(255) null,
	title varchar(255) null,
	update_dt datetime null,
	update_no int null,
	user_hash varchar(255) null
)
	engine=InnoDB
;

create index event_content_index3
	on event_content (ref_content_key)
;

create index event_content_index2
	on event_content (stat, event_start, event_end)
;

create table event_location
(
	event_location_no int auto_increment
		primary key,
	address varchar(255) null,
	address_dtls varchar(255) null,
	create_dt datetime null,
	create_no int null,
	event_content_no int null,
	latitude double null,
	longitude double null,
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

create table push_history
(
	push_history_no int auto_increment
		primary key,
	create_dt datetime null,
	create_no int null,
	event_content_no int null,
	push_payload varchar(255) null,
	push_result_content longtext null,
	push_status_code varchar(255) null,
	push_subscription_no int null,
	user_no int null
)
	engine=InnoDB
;

create table push_subscription
(
	push_subscription_no int auto_increment
		primary key,
	create_dt datetime null,
	create_no int null,
	endpoint longtext null,
	endpoint_hash varchar(255) null,
	update_dt datetime null,
	update_no int null,
	use_yn varchar(255) null,
	user_no int null
)
	engine=InnoDB
;

create table user
(
	user_no int auto_increment
		primary key,
	create_dt datetime null,
	create_no int null,
	update_dt datetime null,
	update_no int null,
	user_desc varchar(255) null,
	user_hash varchar(255) null,
	user_id varchar(255) null,
	user_nm varchar(255) null,
	user_photos varchar(255) null,
	user_provider varchar(255) null,
	user_stat varchar(255) null
)
	engine=InnoDB
;

