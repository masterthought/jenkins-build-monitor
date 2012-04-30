# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table job (
  ID                        bigint not null,
  NAME                      varchar(255) not null,
  JOBGRP                    bigint,
  latest_build              varchar(255),
  URL                       varchar(255) not null,
  colour                    varchar(255),
  latest_build_url          varchar(255),
  active                    boolean,
  constraint pk_job primary key (ID))
;

create table job_group (
  ID                        bigint not null,
  JOB_GROUP_NAME            varchar(255) not null,
  group_name                varchar(255),
  constraint pk_job_group primary key (ID))
;

create table main_url (
  id                        bigint not null,
  url                       varchar(255),
  constraint pk_main_url primary key (id))
;

create sequence job_seq;

create sequence job_group_seq;

create sequence main_url_seq;

alter table job add constraint fk_job_jobGroup_1 foreign key (JOBGRP) references job_group (ID) on delete restrict on update restrict;
create index ix_job_jobGroup_1 on job (JOBGRP);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists job;

drop table if exists job_group;

drop table if exists main_url;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists job_seq;

drop sequence if exists job_group_seq;

drop sequence if exists main_url_seq;

