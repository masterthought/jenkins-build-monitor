# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table job (
  id                        bigint not null,
  name                      varchar(255),
  latest_build              varchar(255),
  url                       varchar(255),
  colour                    varchar(255),
  latest_build_url          varchar(255),
  active                    boolean,
  constraint pk_job primary key (id))
;

create table main_url (
  id                        bigint not null,
  url                       varchar(255),
  constraint pk_main_url primary key (id))
;

create sequence job_seq;

create sequence main_url_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists job;

drop table if exists main_url;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists job_seq;

drop sequence if exists main_url_seq;

