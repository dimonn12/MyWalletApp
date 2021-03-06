﻿drop table tblUserAccount;
drop sequence tblUserAccount_auto_id;
--DEFAULT nextval('tblUserAccount_auto_id')
CREATE SEQUENCE tblUserAccount_auto_id;

create table tblUserAccount(
	"id" bigserial NOT NULL  primary key,
	"login" varchar(64),
	"password" varchar(32),
	"firstname" varchar(32),
	"lastname" varchar(32),
	"salt" varchar(128),
	"deleted" boolean,
	"created" timestamptz,
	"modified" timestamptz)
